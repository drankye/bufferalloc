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

public class CheckedOffHeapObject implements ObjectType {

  private static int CURR;
	private final static int int_offset = (CURR = 0);
	private final static int long_offset = (CURR += 4);
	private final static int byte_offset = (CURR += 8);
	private static int SIZE = (CURR += 1);
	private long address;
	private int pos;
    private int limit;

	public CheckedOffHeapObject(int element) {
		address=unsafe.allocateMemory(SIZE * element);
        limit = SIZE * element;
	}

	@Override
	public void setInt(int value) {
		setInt(pos + int_offset, value);
		
	}

	@Override
	public void setLong(long value) {
		setLong(pos + long_offset, value);

	}

	@Override
	public void setByte(byte value) {
		setByte(pos + byte_offset, value);
	}

	@Override
	public int getInt() {
		return getInt(pos + int_offset);
	}

	@Override
	public long getLong() {
		return getLong(pos + long_offset);
	}

	@Override
	public byte getByte() {
		return getByte(pos + byte_offset);
	}


    private int identifyIndex(int index) {
        return  index*SIZE;
    }

    @Override
    public void navigate(int index) {
        pos = identifyIndex(index);
    }

    private long getLong(int i) {
        return getLong(ix(checkIndex(i, (1 << 3))));
    }

    private int getInt(int i) {
        return getInt(ix(checkIndex(i, (1 << 2))));
    }

    private byte getByte(int i) {
        return getByte(ix(checkIndex(i)));
    }

    private long getLong(long a) {
        long x = unsafe.getLong(a);
        return x;
    }

    private int getInt(long a) {
        int x = unsafe.getInt(a);
        return x;
    }

    private byte getByte(long a) {
        byte x = unsafe.getByte(a);
        return x;
    }

    private void setInt(int i, int value) {
        setInt(ix(checkIndex(i, (1 << 2))), value);
    }

    private void setByte(int i, byte value) {
        setByte(ix(checkIndex(i)), value);
    }

    private void setLong(int i, long value) {
        setLong(ix(checkIndex(i, (1 << 3))), value);
    }

    private void setLong(long a, long value) {
        unsafe.putLong(a, value);
    }

    private void setInt(long a, int value) {
        unsafe.putInt(a, value);
    }

    private void setByte(long a, byte value) {
        unsafe.putByte(a, value);
    }

    private long ix(int i) {
        return address + (i << 0);
    }

    private int checkIndex(int i, int nb) {
        if ((i < 0) || (nb > limit - i)) {
            throw new IndexOutOfBoundsException();
        }
        return i;
    }

    private int checkIndex(int i) {
        if ((i < 0) || (i >= limit)) {
            throw new IndexOutOfBoundsException();
        }
        return i;
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
}
