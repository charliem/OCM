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

namespace OHM
{
	public class OHMRow
	{
		private byte[] rowID;
		
		private Dictionary<byte[], OHMCell> columns;
		
		public OHMRow(byte[] rowID)
		{
			//Store the row ID
			this.rowID = rowID;

			//Initialise the columns dictionary
			columns = new Dictionary<byte[], OHMCell>(20);
		}

		public OHMRow(byte[] rowID, Dictionary<byte[], OHMCell> columns)
		{
			//Store the params
			this.rowID = rowID;
			this.columns = columns;
		}

		public byte[] RowID
		{
			get { return rowID; }
			set { rowID = value; }
		}

		public Dictionary<byte[], OHMCell> Columns
		{
			get { return columns; }
			set { columns = value; }
		}
	}
}
