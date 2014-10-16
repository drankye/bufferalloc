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


import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class InPlaceCheckedOffHeapObject implements ObjectType {

  private static int CURR;
	private final static int int_offset = (CURR = 0);
	private final static int long_offset = (CURR += 4);
	private final static int byte_offset = (CURR += 8);
	private static int SIZE = (CURR += 1);
	private long address;
	private long pos;
    private int limit;

	public InPlaceCheckedOffHeapObject(int element) {
		address=unsafe.allocateMemory(SIZE * element);
        limit = SIZE * element;
	}

	@Override
	public void setInt(int value) {
        long a = pos + int_offset;
        if (a < 0 || a >= pos + limit)
            throw new IndexOutOfBoundsException();

		unsafe.putInt(a, value);
		
	}

	private long identifyIndex(int index) {
		return  address + (index*SIZE * 1L);
	}

	@Override
	public void setLong(long value) {
        long a = pos + long_offset;
        if (a < 0 || a >= pos + limit)
            throw new IndexOutOfBoundsException();

		unsafe.putLong(a, value);

	}

	@Override
	public void setByte(byte value) {
        long a = pos + byte_offset;
        if (a < 0 || a >= pos + limit)
            throw new IndexOutOfBoundsException();

		unsafe.putByte(a, value);
	}

	@Override
	public int getInt() {
        long a = pos + int_offset;
        if (a < 0 || a >= pos + limit)
            throw new IndexOutOfBoundsException();

		return unsafe.getInt(a);
	}

	@Override
	public long getLong() {
        long a = pos + long_offset;
        if (a < 0 || a >= pos + limit)
            throw new IndexOutOfBoundsException();

		return unsafe.getLong(a);
	}

	@Override
	public byte getByte() {
        long a = pos + byte_offset;
        if (a < 0 || a >= pos + limit)
            throw new IndexOutOfBoundsException();

		return unsafe.getByte(a);
	}

	private static Unsafe unsafe;
	static {
		try {
			Field field = Unsafe.class.getDeclaredField("theUnsafe");
			field.setAccessible(true);
			unsafe = (Unsafe) field.get(null);			
		} catch (Exception e) {			
		}
	}
	@Override
	public void navigate(int index) {
		pos = identifyIndex(index);
	}

}
