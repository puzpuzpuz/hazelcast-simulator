/*
 * Copyright (c) 2008-2017, Hazelcast, Inc. All Rights Reserved.
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
package com.hazelcast.simulator.tests.synthetic;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.IdentifiedDataSerializable;
import com.hazelcast.spi.impl.operationservice.BackupOperation;
import com.hazelcast.spi.impl.operationservice.Operation;
import com.hazelcast.spi.impl.operationservice.PartitionAwareOperation;

import java.io.IOException;
import java.util.concurrent.locks.LockSupport;

public class SyntheticBackupOperation extends Operation
      implements BackupOperation, PartitionAwareOperation, IdentifiedDataSerializable {

   private long delayNs;

   public SyntheticBackupOperation() {
   }

   public SyntheticBackupOperation(long delayNs) {
      this.delayNs = delayNs;
   }

   @Override
   public String getServiceName() {
      return null;
   }

   @Override
   public void run() throws Exception {
      LockSupport.parkNanos(delayNs);
   }

   @Override
   public int getFactoryId() {
      return SyntheticSerializableFactory.ID;
   }

   @Override
   public int getClassId() {
      return SyntheticSerializableFactory.BACKUP_OPERATION;
   }

   @Override
   protected void writeInternal(ObjectDataOutput out) throws IOException {
      super.writeInternal(out);
      out.writeLong(delayNs);
   }

   @Override
   protected void readInternal(ObjectDataInput in) throws IOException {
      super.readInternal(in);
      delayNs = in.readLong();
   }
}