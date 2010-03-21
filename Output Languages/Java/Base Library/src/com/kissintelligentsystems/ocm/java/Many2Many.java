package com.kissintelligentsystems.ocm.java;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class Many2Many<T extends BaseTable>
{
	
	private Hashtable<String, T> columns;

	private Hashtable<String, T> updatedColumns;

	private List<String> deletedColumns;

	
	public Many2Many()
	{
		//Create the columns dictionary
		columns = new Hashtable<String,T>();

		//Create the list of updated columns
		updatedColumns = new Hashtable<String,T>();

		//Create the deleted columns list
		deletedColumns = new ArrayList<String>();
	}


	public void add(T newValue)
	{
		//Add the new column
		columns.put(newValue.getKey(), newValue);

		//Add the new column to the list of updated columns
		if(!updatedColumns.containsKey(newValue.getKey()))
		{
			//Add the column to the updatedColumns list
			updatedColumns.put(newValue.getKey(), newValue);
		}
	}

	public void delete(T key)
	{
		//Add the column to the list
		deletedColumns.add(key.getKey());
		
		//Delete the column from the local list
		columns.remove(key.getKey());
		
		if(updatedColumns.containsKey(key.getKey()))
		{
			//Remove any pending updates for this column
			updatedColumns.remove(key.getKey());
		}
	}
	
	public void update(T newValue)
	{
		String name = newValue.getKey();
		
		//Store the new value
		columns.put(name, newValue);

		//Add the column to the updated columns list
		if(!updatedColumns.containsKey(name))
		{
			//Add the column to the updatedColumns list
			updatedColumns.put(name, newValue);
		}
	}

	public T get(String name)
	{
		//Just get the column by its name
		return columns.get(name);		
	}
	
	//Called by the OCM Cassandra Table wrapper, when an update has been applied
	public void clearPendingUpdates()
	{
		//Clear all updated columns
		updatedColumns.clear();

		//Clear all deleted columns
		deletedColumns.clear();
	}
	
	//Provides access to the list of columns and there values, user should not add new columns directly
	public Dictionary<String, T> getColumns()
	{
		return columns;
	}

	public List<String> getDeletedColumns()
	{
		return deletedColumns;		
	}
	
	
	public Dictionary<String, T> getUpdatedColumns()
	{
		return updatedColumns;
	}
}
