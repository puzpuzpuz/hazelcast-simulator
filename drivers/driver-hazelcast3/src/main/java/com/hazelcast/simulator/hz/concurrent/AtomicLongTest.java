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

package com.hazelcast.simulator.hz.concurrent;

import com.hazelcast.core.IAtomicLong;
import com.hazelcast.core.Pipelining;
import com.hazelcast.simulator.hz.HazelcastTest;
import com.hazelcast.simulator.test.BaseThreadState;
import com.hazelcast.simulator.test.annotations.Setup;
import com.hazelcast.simulator.test.annotations.Teardown;
import com.hazelcast.simulator.test.annotations.TimeStep;

import java.util.concurrent.Executor;

public class AtomicLongTest extends HazelcastTest {

    // properties
    public int countersLength = 1000;
    public int pipelineDepth = 100;
    public int pipelineIterations = 100;

    private IAtomicLong[] counters;
    private final Executor callerRuns = Runnable::run;

    @Setup
    public void setup() {
        counters = new IAtomicLong[countersLength];
        for (int i = 0; i < countersLength; i++) {
            counters[i] = targetInstance.getAtomicLong("" + i);
            counters[i].get();
        }
    }

    @TimeStep(prob = -1)
    public long get(ThreadState state) {
        return state.randomCounter().get();
    }

    @TimeStep(prob = 0.1)
    public long inc(ThreadState state) {
        return state.randomCounter().incrementAndGet();
    }

    public class ThreadState extends BaseThreadState {
        private Pipelining<Long> pipeline;
        private int i;

        private IAtomicLong randomCounter() {
            int index = randomInt(counters.length);
            return counters[index];
        }
    }

    @Teardown
    public void teardown() {
        for (IAtomicLong counter : counters) {
            counter.destroy();
        }
    }
}
