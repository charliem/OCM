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
	
	public class DynamicColumnFamily<T>
	{


		
		private Dictionary<String, T> columns;

		private Dictionary<String, String> updatedColumns;

		private List<String> deletedColumns;

		
		public DynamicColumnFamily()
		{
			//Create the columns dictionary
			columns = new Dictionary<String,T>();

			//Create the list of updated columns
			updatedColumns = new Dictionary<String,String>();

			//Create the deleted columns list
			deletedColumns = new List<String>();

			if(columns== null)
				Console.WriteLine("Columns Null");
		}


		public void addColumn(String name, T newValue)
		{
			//Add the new column
			columns.Add(name, newValue);

			//Add the new column to the list of updated columns
			if(!updatedColumns.ContainsKey(name))
			{
				//Add the column to the updatedColumns list
				updatedColumns.Add(name, name);
			}
		}

		public void deleteColumn(String name)
		{
			//Add the column to the list
			deletedColumns.Add(name);
			
			if(updatedColumns.ContainsKey(name))
			{
				//Remove any pending updates for this column
				updatedColumns.Remove(name);
			}
		}
		
		public void updateColumn(String name, T newValue)
		{
			//Store the new value
			columns[name] = newValue;

			//Add the column to the updated columns list
			if(!updatedColumns.ContainsKey(name))
			{
				//Add the column to the updatedColumns list
				updatedColumns.Add(name, name);
			}
		}

		//Called by the OHM Hbase Table wrapper, when an update has been applied
		public void clearPendingUpdates()
		{
			//Clear all updated columns
			updatedColumns.Clear();

			//Clear all deleted columns
			deletedColumns.Clear();
		}
		
		//Provides access to the list of columns and theire values, user should not add new columns directly
		public Dictionary<String, T> Columns
		{
			get
			{
			if(this.columns== null)
				Console.WriteLine("Columns Null 2");
				
				return this.columns;
			}
		}

		public System.Collections.Generic.IEnumerable<String> DeletedColumns
		{
			get
			{
				return deletedColumns;
			}
		}
		
		
		public System.Collections.Generic.IEnumerable<String> UpdatedColumns
		{
			get
			{
				return updatedColumns.Values;
			}
		}
	}
}
