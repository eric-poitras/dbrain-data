/*
 * Copyright [2014] [Eric Poitras]
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package org.dbrain.data.csv;


import org.dbrain.data.tabular.ForwardTabularCursor;
import org.dbrain.data.text.ReaderCursor;

import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Tabular reader class to read CSV datasources.
 */
public class CsvCursor implements ForwardTabularCursor, AutoCloseable {

    private int                  separator;
    private int                  quote;
    private ReaderCursor         cursor;
    private Map<String, Integer> fieldsMap;
    private List<String>         fieldValues;
    private boolean bof = true;

    /**
     * Create a new cursor to read tabular data from a text stream.<p>
     *
     * Allow to specify the fields name.
     */
    public CsvCursor( Reader reader, int separator, int stringQuote, String[] fieldNames ) {
        this.cursor = new ReaderCursor( reader );
        this.separator = separator;
        this.quote = stringQuote;
        setFieldNames( fieldNames );
    }

    /**
     * Create a new cursor to read tabular data from a text stream.
     */
    public CsvCursor( Reader reader, int separator, int stringQuote ) {
        this.cursor = new ReaderCursor( reader );
        this.separator = separator;
        this.quote = stringQuote;
        List<String> fieldNames = readLine();
        setFieldNames( fieldNames.toArray( new String[fieldNames.size()] ) );
    }

    /**
     * Create a new cursor to read tabular data from a text stream.
     *
     * Use no quote for string fields.
     */
    public CsvCursor( Reader reader, int separator ) {
        this( reader, separator, -1 );
    }

    /**
     * The character represent a end of file as returned by cursor.
     */
    private boolean isEOF( int c ) {
        return c < 0;
    }

    /**
     * Return true if the specified character is an end of line character.
     */
    private boolean isEOL( int c ) {
        return c == 13 || c == 10 || isEOF( c );
    }

    /**
     * Return true if the specified character is an end of column character.
     */
    private boolean isEOC( int c ) {
        return c == separator || isEOL( c );
    }

    /**
     * Return true if the specified character is a space character.
     */
    private boolean isLineSpace( int c ) {
        return Character.isWhitespace( c ) && !isEOL( c );
    }

    /**
     * Read spaces.
     */
    private String readSpace() {
        StringBuilder sb = new StringBuilder();
        for ( int cur = cursor.current(); isLineSpace( cur ); cur = cursor.next() ) {
            sb.appendCodePoint( cur );
        }
        return sb.toString();
    }

    /**
     * Read a quoted string from the stream;
     */
    private String readQuotedString() {

        // Read initial quote
        if ( cursor.read() != quote ) {
            throw cursor.error( "Invalid quoted string." );
        }

        // Read string content
        StringBuilder sb = new StringBuilder();
        for ( int cur = cursor.current(); !isEOL( cur ); cur = cursor.next() ) {
            if ( cur == quote ) {
                cur = cursor.next();
                if ( cur != quote ) {
                    break;
                }
            }
            sb.appendCodePoint( cur );
        }

        return sb.toString();
    }

    /**
     * Read unquoted string from the stream.
     *
     * @return The string getValue
     */
    private String readUnquotedString() {
        StringBuilder sb = new StringBuilder();

        for ( int cur = cursor.current(); !isEOC( cur ); cur = cursor.next() ) {
            sb.append( (char) cur );
        }

        return sb.toString();
    }

    /**
     * Read a string from the stream;
     */
    private String readString() {
        String spaces = readSpace();
        return ( cursor.current() == quote ) ? readQuotedString() : spaces + readUnquotedString();
    }

    /**
     * Read a line of data and return it as a list of string.
     *
     * @return a list of string.
     */
    private List<String> readLine() {
        List<String> result = new ArrayList<>();

        while ( !isEOL( cursor.current() ) ) {
            result.add( readString() );
            if ( isEOC( cursor.current() ) && !isEOL( cursor.current() ) ) {
                cursor.read();
            }
        }

        // Skip all EOL characters
        for ( int cur = cursor.current(); isEOL( cur ) && !isEOF( cur ); cur = cursor.next() ) ;

        return result;
    }

    /**
     * Read the fields name from the first line of data.
     */
    private void setFieldNames( String[] fieldNames ) {
        fieldsMap = new HashMap<>( fieldNames.length );
        int i = 0;
        for ( String name : fieldNames ) {
            fieldsMap.put( name.trim(), i );
            i++;
        }
    }

    public boolean bof() {
        return bof;
    }

    /**
     * Retrieve the End of File status.
     *
     * @return True if stream at End of File.
     */
    public boolean eof() {
        return bof ? false : fieldValues == null;
    }

    /**
     * Read the next line of data.
     */
    public boolean next() {
        if ( bof ) {
            bof = false;
        }
        if ( isEOF( cursor.current() ) ) {
            fieldValues = null;
        } else {
            fieldValues = readLine();
        }
        return fieldValues != null;
    }

    /**
     * Read the field value.
     *
     * @param index Index of the field to read.
     *
     * @return The field value, as string.
     */
    public Object getObject( int index ) {
        if ( fieldValues == null ) {
            throw new IllegalStateException();
        }
        if ( index < 0 ) {
            throw new IndexOutOfBoundsException();
        }
        return index < fieldValues.size() ? fieldValues.get( index ) : null;

    }

    /**
     * Get the value of the field or null.
     *
     * @param name Field name
     *
     * @return The getValue or null if getValue out of bound for this row.
     */
    public Object getObject( String name ) {
        if ( fieldValues == null ) {
            throw new IllegalStateException();
        }
        // Check that names are defined
        if ( fieldsMap == null ) {
            throw new IllegalArgumentException();
        }

        // Get field index
        Integer idx = fieldsMap.get( name );
        if ( idx == null ) {
            throw new IllegalArgumentException();
        }

        return idx < fieldValues.size() ? fieldValues.get( idx ) : null;
    }

    /**
     * Close the underlying cursor.
     */
    @Override
    public void close() throws Exception {
        if ( cursor == null ) {
            throw new IllegalStateException();
        }
        cursor.close();
        cursor = null;
    }
}
