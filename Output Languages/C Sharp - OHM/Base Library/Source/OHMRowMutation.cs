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
	public class OHMRowMutation
	{
		public byte[] rowID;
		
		private LinkedList<OHMCellMutation> changes;
		
		public OHMRowMutation(byte[] rowID)
		{
			//Store the rowID
			this.rowID = rowID;

			//Initialise the changes liked list
			changes = new LinkedList<OHMCellMutation>();
		}

		public byte[] RowID
		{
			get { return rowID; }
		}

		public LinkedList<OHMCellMutation> Changes
		{
			get { return changes; }
		}

		public void addUpdate(byte[] column, OHMCell cell)
		{
			changes.AddLast(new OHMCellMutation(column, cell));
		}

		public void addDelete(byte[] column)
		{
			changes.AddLast(new OHMCellMutation(column));
		}
	}

	public class OHMCellMutation
	{
		public OHMCell cell;
				
		public byte[] column;
		
		public bool toDelete;

		public OHMCellMutation(byte[] column)
		{
			//Store the column
			this.column = column;

			//This is called only when we want to delete the cell
			toDelete = true;
		}

		public OHMCellMutation(byte[] column, OHMCell cell)
		{
			//Store the params
			this.column = column;
			this.cell = cell;

			//Make sure we don't delete this
			toDelete = false;
		}
	}
}
