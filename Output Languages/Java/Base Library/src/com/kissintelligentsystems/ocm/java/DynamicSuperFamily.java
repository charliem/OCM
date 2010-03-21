package com.kissintelligentsystems.ocm.java;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class DynamicSuperFamily<T>
{
	
	private Hashtable<String, T> columns;

	private Hashtable<String, String> updatedColumns;

	private List<String> deletedColumns;

	
	public DynamicSuperFamily()
	{
		//Create the columns dictionary
		columns = new Hashtable<String,T>();

		//Create the list of updated columns
		updatedColumns = new Hashtable<String,String>();

		//Create the deleted columns list
		deletedColumns = new ArrayList<String>();
	}


	public void addColumn(String name, T newValue)
	{
		//Add the new column
		columns.put(name, newValue);

		//Add the new column to the list of updated columns
		if(!updatedColumns.containsKey(name))
		{
			//Add the column to the updatedColumns list
			updatedColumns.put(name, name);
		}
	}

	public void deleteColumn(String name)
	{
		//Add the column to the list
		deletedColumns.add(name);
		
		//Delete the column from the local list
		columns.remove(name);
		
		if(updatedColumns.containsKey(name))
		{
			//Remove any pending updates for this column
			updatedColumns.remove(name);
		}
	}
	
	public void updateColumn(String name, T newValue)
	{
		//Store the new value
		columns.put(name, newValue);

		//Add the column to the updated columns list
		if(!updatedColumns.containsKey(name))
		{
			//Add the column to the updatedColumns list
			updatedColumns.put(name, name);
		}
	}

	public T getColumn(String name)
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
	
	
	public Dictionary<String, String> getUpdatedColumns()
	{
		return updatedColumns;
	}
}
