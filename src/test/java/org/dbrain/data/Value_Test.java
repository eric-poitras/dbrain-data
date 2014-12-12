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

package org.dbrain.data;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Test Value methods that are not tested otherwise.
 */
public class Value_Test {

    /**
     * Test the isNull method.
     * @throws Exception
     */
    @Test
    public void testIsNull() throws Exception {

        Value v;

        v = Value.of( "" );
        Assert.assertFalse( v.isNull() );

        v = Value.of( 0.0D );
        Assert.assertFalse( v.isNull() );

        v = Value.of( Boolean.TRUE );
        Assert.assertFalse( v.isNull() );

        v = Value.newList();
        Assert.assertFalse( v.isNull() );

        v = Value.newMap();
        Assert.assertFalse( v.isNull() );

        v = Value.of( (Boolean) null );
        Assert.assertTrue( v.isNull() );

        v = Value.of( (Double) null );
        Assert.assertTrue( v.isNull() );

        v = Value.of( (String) null );
        Assert.assertTrue( v.isNull() );

        v = Value.ofJson( "null" );
        Assert.assertTrue( v.isNull() );

    }
}
