/*
 * Copyright (c) 2008-2016, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hazelcast.simulator.utils;

import org.junit.Test;

import java.util.Random;

import static com.hazelcast.simulator.utils.GeneratorUtils.generateByteArray;
import static com.hazelcast.simulator.utils.GeneratorUtils.generateString;
import static com.hazelcast.simulator.utils.GeneratorUtils.generateStrings;
import static com.hazelcast.simulator.utils.ReflectionUtils.invokePrivateConstructor;
import static com.hazelcast.simulator.utils.TestUtils.assertEqualsStringFormat;

public class GeneratorUtilsTest {

    @Test
    public void testConstructor() throws Exception {
        invokePrivateConstructor(GeneratorUtils.class);
    }

    @Test
    public void testGenerateStrings() {
        String[] strings = generateStrings(3, 7);

        assertEqualsStringFormat("Expected to get %d strings, but was %d", 3, strings.length);
        for (String actual : strings) {
            assertEqualsStringFormat("Expected generated string length to be %d, but was %d", 7, actual.length());
        }
    }

    @Test
    public void testGenerateStringZeroLength() {
        String actual = generateString(0);

        assertEqualsStringFormat("Expected generated string length to be %d, but was %d", 0, actual.length());
    }

    @Test
    public void testGenerateString() {
        String actual = generateString(42);

        assertEqualsStringFormat("Expected generated string length to be %d, but was %d", 42, actual.length());
    }

    @Test
    public void testGenerateByteArray() {
        byte[] actual = generateByteArray(new Random(), 23);

        assertEqualsStringFormat("Expected generated byte array length to be %d, but was %d", 23, actual.length);
    }
}
