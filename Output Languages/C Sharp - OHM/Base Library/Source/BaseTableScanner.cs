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

using Apache.Hadoop.Hbase.Thrift;

using System;
using System.Collections.Generic;

namespace OHM
{
	public class BaseTableScanner
	{
		private int scannerID;

		protected OHMConnection connection;
		
		private byte[] tableName;
		
		
		public BaseTableScanner(OHMConnection connection, byte[] tableName)
		{
			//Store the params
			this.connection = connection;
			this.tableName = tableName;
		}

		protected void open(byte[] key)
		{
			//Open the scanner
			scannerID = connection.openScanner(tableName, key);
		}

		protected void open(byte[] key, List<String> colNames)
		{
			List<byte[]> data = new List<byte[]>(colNames.Count);
			
			foreach(String name in colNames)
			{
				//Convert the column name
				data.Add(BaseTable.fromString(name));
			}

			//Open the scanner
			scannerID = connection.openScanner(tableName, key, data);
		}


		protected void openWithStop(byte[] startKey, byte[] stopKey)
		{
			//Open the scanner
			scannerID = connection.openScannerWithStop(tableName, startKey, stopKey);
		}

		protected void openWithStop(byte[] startKey, byte[] stopKey, List<String> colNames)
		{
			List<byte[]> data = new List<byte[]>(colNames.Count);
			
			foreach(String name in colNames)
			{
				//Convert the column name
				data.Add(BaseTable.fromString(name));
			}

			//Open the scanner
			scannerID = connection.openScannerWithStop(tableName, startKey, stopKey, data);
		}


		

		protected bool tryGetNext(out OHMRow result)
		{
			try
			{
				//Try to get the entry
				result = connection.scannerGet(scannerID);

				return true;
			}
			catch(NotFound exp)
			{
				//Set some default values
				result = null;
				
				return false;				
			}
		}

		public virtual void close()
		{
			//Close the scanner
			connection.scannerClose(scannerID);
		}
	}
}
