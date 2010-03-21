/*
	Copyright 2009 KISS Intelligent Systems Limited
	
	   Licensed under the Apache License, Version 2.0 (the "License");
	   you may not use this file except in compliance with the License.
	   You may obtain a copy of the License at
	
	       http://www.apache.org/licenses/LICENSE-2.0
	
	   Unless required by applicable law or agreed to in writing, software
	   distributed under the License is distributed on an "AS IS" BASIS,
	   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	   See the License for the specific language governing permissions and
	   limitations under the License.
	   
*/

using System;
using System.Collections.Generic;
using System.Text;

using Apache.Hadoop.Hbase.Thrift;

namespace OHM
{

	public abstract class BaseTable
	{
		
		public BaseTable()
		{
		}

		public abstract void save();

		public abstract void delete();

		public abstract void loadAll();

		
		protected String generateNewKey(OHMConnection connection, byte[] tableName, Random rand)
		{
			String key = null;
			OHMRow result = null;
			
			bool found = false;

			while(!found)
			{			
				//Generate throw random 5 digit numbers
				int no1 = rand.Next(10000, 99999);
				int no2 = rand.Next(10000, 99999);
				int no3 = rand.Next(10000, 99999);

				key = no1.ToString() + no2.ToString() + no3.ToString();

				Console.WriteLine("New Key: " + key);
				
				try
				{
					 result = connection.getRow(tableName, fromString(key));
				}
				catch(Exception exp)
				{

				}

				if(result == null || result.Columns.Count == 0)
				{
					//The key is not already in use
					found = true;
				}
					
			}

			return key;
		}

		
		
		public static byte[] fromBool(bool value)
		{
			//Use Bit Conveters Little Endian conversion
			return BitConverter.GetBytes(value);
		}

		public static byte[] fromByte(byte value)
		{
			//Just put it straight in to the array
			return new byte[]{value};
		}

		public static byte[] fromByteStream(byte[] value)
		{
			//Just user the array directly
			return value;
		}
		
		public static byte[] fromDouble(double value)
		{
			//Use Bit Conveters Little Endian conversion
			return BitConverter.GetBytes(value);
		}

		public static byte[] fromFloat(float value)
		{
			//Use Bit Conveters Little Endian conversion
			return BitConverter.GetBytes(value);
		}
		
		public static byte[] fromInt(int value)
		{
			//Use Bit Conveters Little Endian conversion
			return BitConverter.GetBytes(value);
		}

		public static byte[] fromLong(long value)
		{
			//Use Bit Conveters Little Endian conversion
			return BitConverter.GetBytes(value);
		}

		public static byte[] fromString(String value)
		{
			//Use the UTF8 encoding
			return Encoding.UTF8.GetBytes(value);
		}

		public static byte[] fromUInt(uint value)
		{
			//Use Bit Conveters Little Endian conversion
			return BitConverter.GetBytes(value);
		}

		public static byte[] fromULong(ulong value)
		{
			//Use Bit Conveters Little Endian conversion
			return BitConverter.GetBytes(value);
		}

		
		public static bool toBool(byte[] data)
		{
			//Use Bit Conveters Little Endian conversion
			return BitConverter.ToBoolean(data, 0);
		}

		public static byte toByte(byte[] data)
		{
			if(data.Length > 0)
				//Just use the first value from the array
				return data[0];

			else
				return 0;
		}

		public static byte[] toByteStream(byte[] data)
		{
			//Just use the array directly
			return data;
		}

		public static double toDouble(byte[] data)
		{
			//Use Bit Conveters Little Endian conversion
			return BitConverter.ToDouble(data, 0);
		}

		public static float toFloat(byte[] data)
		{
			//Use Bit Conveters Little Endian conversion
			return BitConverter.ToSingle(data, 0);
		}
		
		public static int toInt(byte[] data)
		{
			//Use Bit Conveters Little Endian conversion
			return BitConverter.ToInt32(data, 0);
		}

		public static long toLong(byte[] data)
		{
			//Use Bit Conveters Little Endian conversion
			return BitConverter.ToInt64(data, 0);
		}
		
		public static String toString(byte[] data)
		{
			//Use the UTF8 encoding
			return Encoding.UTF8.GetString(data);
		}
		
		public static uint toUInt(byte[] data)
		{
			//Use Bit Conveters Little Endian conversion
			return BitConverter.ToUInt32(data, 0);
		}

		public static ulong toULong(byte[] data)
		{
			//Use Bit Conveters Little Endian conversion
			return BitConverter.ToUInt64(data, 0);
		}

		
	}
}
