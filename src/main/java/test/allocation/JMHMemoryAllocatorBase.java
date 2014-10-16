package test.allocation;/*
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

import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;

import java.util.concurrent.TimeUnit;

public class JMHMemoryAllocatorBase {

    public static final int NELEMENTS = 50000000;

    private int nElements;
    private ObjectType objectType;
    private int[] idx;
    private int result;

    public void testSetup(String allocatorType, int nElements) {

        System.out.println("testing objects[" + nElements + "] with allocatorType:" + allocatorType);

        this.nElements = nElements;

        this.idx = new int[nElements];
        for (int i = 0; i < nElements; i++) {
            idx[i] = (int) (Math.random() * nElements);
        }

        this.objectType = Allocator.allocate(Allocator.Type.valueOf(allocatorType), nElements);

        write(objectType, nElements); // read tests need this
    }

    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void testRead() {
        result = read(objectType, nElements);
        //System.out.println(String.format("Read %16s", resRead));
    }

    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void testWrite() {
        result = write(objectType, nElements);
        //System.out.println(String.format("Write %16s", resWrite));
    }

    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void testRandomRead() {
        result = randomRead(objectType, nElements, idx);
        //System.out.println(String.format("RandRead %16s", rresRead));
    }

    public static int write(ObjectType t, int items) {
        int index = 0;
        for (; index < items; index++) {
            t.navigate(index);
            t.setByte((byte) index);
            t.setInt(index);
            t.setLong(index);
        }
        return index;
    }

    public static int read(ObjectType t, int items) {
        int sum = 0;

        for (int index = 0; index < items; index++) {
            t.navigate(index);

            /* consume ie use all read values to avoid dead code elimination */
            sum += t.getByte() + t.getInt() + (int) t.getLong();
        }
        return sum;
    }

    public static int randomRead(ObjectType t, int items, final int[] idx) {
        int sum = 0;

        for (int index = 0; index < items; index++) {
            t.navigate(idx[index]);

            /* consume ie use all read values to avoid dead code elimination */
            sum += t.getByte() + t.getInt() + (int) t.getLong();
        }
        return sum;
    }
}

