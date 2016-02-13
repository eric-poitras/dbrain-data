/*
 * Copyright [2016] [Eric Poitras]
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

import org.junit.Assert;
import org.junit.Test;

import java.util.function.Function;

import static org.junit.Assert.*;

/**
 * Created by epoitras on 2/12/16.
 */
public class FunctionRegistry_Test {

    @Test
    public void testGet() throws Exception {
        FunctionRegistry<String> test = FunctionRegistry.<String>newBuilder().add( String.class, (s) -> s + "result" ).build();

        Function<String, String> f = test.get( String.class );
        assertEquals( f.apply( "test" ), "testresult" );
    }


}