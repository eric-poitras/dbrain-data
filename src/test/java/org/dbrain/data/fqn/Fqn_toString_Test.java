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

package org.dbrain.data.fqn;

import org.dbrain.data.Fqn;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by epoitras on 03/10/14.
 */
public class Fqn_toString_Test {

    @Test
    public void testToString() throws Exception {
        Fqn d;

        d = new Fqn( Arrays.asList( "test" ) );
        Assert.assertEquals( "test", d.toString() );

        d = new Fqn( Arrays.asList( "test", "" ) );
        Assert.assertEquals( "test.''", d.toString() );

        d = new Fqn( Arrays.asList( "" ) );
        Assert.assertEquals( "''", d.toString() );

        d = new Fqn( Arrays.asList( "", "test", "test*", "test'", "test." ) );
        Assert.assertEquals( "''.test.'test*'.'test'''.'test.'", d.toString() );

    }
}
