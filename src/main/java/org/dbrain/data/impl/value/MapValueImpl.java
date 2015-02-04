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

package org.dbrain.data.impl.value;

import org.dbrain.data.DataCoercionException;
import org.dbrain.data.Value;
import org.dbrain.data.json.JsonBridge;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Implementation of the Value.Map.
 */
public class MapValueImpl implements Value.Map {

    private final HashMap<String, Value> delegate;

    public MapValueImpl( HashMap<String, Value> delegate ) {
        this.delegate = delegate;
    }

    public MapValueImpl() {
        this( new HashMap<>() );
    }

    public Object getObject() {
        java.util.Map<String, Object> result = new HashMap<>( size() );
        delegate.forEach( ( s, value ) -> result.put( s, value.getObject() ) );
        return result;
    }

    @Override
    public Object getObject( String fieldName ) {
        return get( fieldName ).getObject();
    }

    @Override
    public Boolean getBoolean() {
        throw new DataCoercionException( "Cannot cast map to boolean." );
    }

    @Override
    public Byte getByte() {
        throw new DataCoercionException( "Cannot cast map to byte." );
    }

    @Override
    public Short getShort() {
        throw new DataCoercionException( "Cannot cast map to short." );
    }

    @Override
    public Integer getInt() {
        throw new DataCoercionException( "Cannot cast map to integer." );
    }

    @Override
    public Long getLong() {
        throw new DataCoercionException( "Cannot cast map to long." );
    }

    @Override
    public Float getFloat() {
        throw new DataCoercionException( "Cannot cast map to float." );
    }

    @Override
    public Double getDouble() {
        throw new DataCoercionException( "Cannot cast map to double." );
    }

    @Override
    public String getString() {
        throw new DataCoercionException( "Cannot cast map to string." );
    }

    @Override
    public MapValueImpl getMap() {
        return this;
    }

    @Override
    public Value.List getList() {
        throw new DataCoercionException( "Cannot cast map to list." );
    }

    @Override
    public boolean isNull() {
        return false;
    }

    @Override
    public int size() {
        return delegate.size();
    }

    @Override
    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    @Override
    public Value get( Object key ) {
        return Value.of( delegate.get( key ) );
    }

    @Override
    public boolean containsKey( Object key ) {
        return delegate.containsKey( key );
    }

    @Override
    public Value put( String key, Value value ) {
        return delegate.put( key, Value.of( value ) );
    }

    @Override
    public void putAll( java.util.Map<? extends String, ? extends Value> m ) {
        for ( Map.Entry<? extends String, ? extends Value> e : m.entrySet() ) {
            put( e.getKey(), e.getValue() );
        }
    }

    @Override
    public Value remove( Object key ) {
        return delegate.remove( key );
    }

    @Override
    public void clear() {
        delegate.clear();
    }

    @Override
    public boolean containsValue( Object value ) {
        return delegate.containsValue( Value.of( value ) );
    }

    @Override
    public Set<String> keySet() {
        return delegate.keySet();
    }

    @Override
    public Collection<Value> values() {
        return delegate.values();
    }

    @Override
    public Set<Entry<String, Value>> entrySet() {
        return delegate.entrySet();
    }

    @Override
    public Value getOrDefault( Object key, Value defaultValue ) {
        Value result = get( key );
        return result.isNull() ? Value.of( defaultValue ) : result;
    }

    @Override
    public Value putIfAbsent( String key, Value value ) {
        if ( !containsKey( key ) ) {
            return put( key, Value.of( value ));
        } else {
            return get( key );
        }
    }

    @Override
    public boolean remove( Object key, Object value ) {
        return delegate.remove( key, Value.of( value ) );
    }

    @Override
    public boolean replace( String key, Value oldValue, Value newValue ) {
        return delegate.replace( key, Value.of( oldValue ) , Value.of( newValue ) );
    }

    @Override
    public Value replace( String key, Value value ) {
        return delegate.replace( key, Value.of( value ) );
    }

    @Override
    public Value computeIfAbsent( String key, Function<? super String, ? extends Value> mappingFunction ) {
        return delegate.computeIfAbsent( key, mappingFunction );
    }

    @Override
    public Value computeIfPresent( String key,
                                   BiFunction<? super String, ? super Value, ? extends Value> remappingFunction ) {
        return delegate.computeIfPresent( key, remappingFunction );
    }

    @Override
    public Value compute( String key, BiFunction<? super String, ? super Value, ? extends Value> remappingFunction ) {
        return delegate.compute( key, remappingFunction );
    }

    @Override
    public Value merge( String key,
                        Value value,
                        BiFunction<? super Value, ? super Value, ? extends Value> remappingFunction ) {
        return delegate.merge( key, value, remappingFunction );
    }

    @Override
    public void forEach( BiConsumer<? super String, ? super Value> action ) {
        delegate.forEach( action );
    }

    @Override
    public void replaceAll( BiFunction<? super String, ? super Value, ? extends Value> function ) {
        delegate.replaceAll( function );
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        return delegate.equals( o );

    }

    @Override
    public int hashCode() {
        return delegate.hashCode();
    }

    @Override
    public String toString() {
        return JsonBridge.get().writeToString( this );
    }

}