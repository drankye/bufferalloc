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


public final class HeapAllocatedObject implements ObjectType {

        private final HeapValue[] values;
	private HeapValue cur = null;


	public HeapAllocatedObject(int element)
	{
		values = new HeapValue[element];
		for(int x=0; x < element; x++)
		{
			values[x] = new HeapValue();
		}
	}
	
	
	@Override
	public void setInt(int value) {
		cur.id = value;
	}

	@Override
	public void setLong(long value) {
		cur.longValue = value;
		
	}

	@Override
	public void setByte(byte value) {
		cur.type = value;
		
	}

	@Override
	public int getInt() {
		return cur.id;
	}

	@Override
	public long getLong() {
		return cur.longValue;
	}

	@Override
	public byte getByte() {
		return cur.type;
	}
	
	public static final class HeapValue
	{
		public int  id;
		public long longValue;
		public byte type;
	}

	@Override
	public void navigate(int index) {
	    cur = values[index];
	}

}
