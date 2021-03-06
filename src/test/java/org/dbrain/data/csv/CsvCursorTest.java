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

import org.junit.Assert;
import org.junit.Test;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test the CsvCursor class.
 */
public class CsvCursorTest {

  @Test
  public void test_cursor_with_named_columns() throws Exception {
    Reader r = new InputStreamReader(getClass().getResourceAsStream("/TestNamedColumn.csv"), "UTF-8");

    CsvCursor csvCursor = new CsvCursor(r, ',');

    assertThat(csvCursor.bof()).isTrue();
    assertThat(csvCursor.eof()).isFalse();

    assertThat(csvCursor.next()).isTrue();

    assertThat(csvCursor.getObject(0)).isEqualTo("Eric1");
    assertThat(csvCursor.getObject("Col1")).isEqualTo("Eric1");

    Assert.assertEquals("1", csvCursor.getObject(1));
    Assert.assertEquals((Byte) ((byte) 1), csvCursor.getByte(1));
    Assert.assertEquals((Short) ((short) 1), csvCursor.getShort(1));
    Assert.assertEquals((Integer) ((int) 1), csvCursor.getInt(1));
    Assert.assertEquals((Long) 1L, csvCursor.getLong(1));
    Assert.assertEquals(new Float(1f), csvCursor.getFloat(1));
    Assert.assertEquals(new Double(1d), csvCursor.getDouble(1));
    Assert.assertEquals("1", csvCursor.getString(1));

    Assert.assertEquals("1", csvCursor.getAs(1, Function.identity()));
    Assert.assertEquals((Byte) ((byte) 1), csvCursor.getByteAs(1, Function.identity()));
    Assert.assertEquals((Short) ((short) 1), csvCursor.getShortAs(1, Function.identity()));
    Assert.assertEquals((Integer) 1, csvCursor.getIntAs(1, Function.identity()));
    Assert.assertEquals((Long) 1L, csvCursor.getLongAs(1, Function.identity()));
    Assert.assertEquals(new Float(1f), csvCursor.getFloatAs(1, Function.identity()));
    Assert.assertEquals(new Double(1d), csvCursor.getDoubleAs(1, Function.identity()));
    Assert.assertEquals("1", csvCursor.getStringAs(1, Function.identity()));

    Assert.assertEquals("1", csvCursor.getObject("Col2"));
    Assert.assertEquals((Byte) ((byte) 1), csvCursor.getByte("Col2"));
    Assert.assertEquals((Short) ((short) 1), csvCursor.getShort("Col2"));
    Assert.assertEquals((Integer) ((int) 1), csvCursor.getInt("Col2"));
    Assert.assertEquals((Long) 1L, csvCursor.getLong("Col2"));
    Assert.assertEquals(new Float(1f), csvCursor.getFloat("Col2"));
    Assert.assertEquals(new Double(1d), csvCursor.getDouble("Col2"));
    Assert.assertEquals("1", csvCursor.getString("Col2"));

    Assert.assertEquals("1", csvCursor.getStringAs("Col2", Function.identity()));
    Assert.assertEquals((Byte) ((byte) 1), csvCursor.getByteAs("Col2", Function.identity()));
    Assert.assertEquals((Short) ((short) 1), csvCursor.getShortAs("Col2", Function.identity()));
    Assert.assertEquals((Integer) ((int) 1), csvCursor.getIntAs("Col2", Function.identity()));
    Assert.assertEquals((Long) 1L, csvCursor.getLongAs("Col2", Function.identity()));
    Assert.assertEquals(new Float(1f), csvCursor.getFloatAs("Col2", Function.identity()));
    Assert.assertEquals(new Double(1d), csvCursor.getDoubleAs("Col2", Function.identity()));
    Assert.assertEquals("1", csvCursor.getStringAs("Col2", Function.identity()));

    Assert.assertEquals("2012-01-01", csvCursor.getObject(2));
    Assert.assertEquals("2012-01-01", csvCursor.getObject("Col3"));

    Assert.assertTrue(csvCursor.next());
    Assert.assertEquals("Eric2", csvCursor.getObject(0));
    Assert.assertEquals("2", csvCursor.getObject(1));
    Assert.assertEquals("2012-01-02", csvCursor.getObject(2));

    Assert.assertFalse(csvCursor.next());

    csvCursor.close();


  }
}
