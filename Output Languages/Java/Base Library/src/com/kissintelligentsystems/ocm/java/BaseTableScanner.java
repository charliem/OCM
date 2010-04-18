package com.kissintelligentsystems.ocm.java;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.NotFoundException;
import org.apache.cassandra.thrift.SlicePredicate;
import org.apache.cassandra.thrift.SliceRange;
import org.apache.cassandra.thrift.SuperColumn;

import me.prettyprint.cassandra.service.Keyspace;


public class BaseTableScanner
{	
	protected OCMConnection connection;

	private String columnFamily;
	
	private RowData[] currentRows;
	
	private boolean throwAwayFirstResult = false;
	private boolean reachedEnd = false;
	
	private static int batchSize = 100;
	
	private String startKey;
	private String stopKey;
	
	private int totalLimit;
	
	private int nextRow = 0;
	private int rowsAvalible = 0;
	private int rowsReturned = 0;
	
	public BaseTableScanner(OCMConnection connection, String columnFamily)
	{
		//Store the params
		this.connection = connection;
		this.columnFamily = columnFamily;

	}
	
	protected void startWithStop(int limit, String startKey, String stopKey)
	{
		//Store the params
		this.totalLimit = limit;
		this.startKey = startKey;
		this.stopKey = stopKey;

		//Reset the row info
		rowsAvalible = 0;
		rowsReturned = 0;
		throwAwayFirstResult = false;	
		reachedEnd = false;
	}
	
	protected RowData nextRow() throws IllegalArgumentException, NotFoundException, Exception
	{
		if(rowsReturned >= totalLimit && totalLimit != -1)
		{
			//We have reached our limit
			return null;
		}
		
		if(rowsAvalible == 0)
		{
			if(reachedEnd)
			{
				//We were already at the end of the range
				return null;
			}
			
			getRows();
			
			if(rowsAvalible == 0)
			{
				//We couldn't get any more
				return null;
			}
		}
		
		//There must be a spare record available
		RowData row = currentRows[nextRow];
		
		//Advance the current row
		nextRow++;
		rowsReturned++;
		rowsAvalible--;
		
		return row;		
	}
	
	private void getRows() throws Exception
	{
		Keyspace keyspace = connection.borrowKeySpace();
		
		SlicePredicate pred = new SlicePredicate();
		pred.setSlice_range(new SliceRange(new byte[0], new byte[0], false, 1000));
		
		int currentBatchSize = totalLimit - rowsReturned;
		
		if(currentBatchSize > batchSize || totalLimit == -1)
		{
			currentBatchSize = batchSize;
		}
		
		//Perform the range query
		Map<String, List<SuperColumn>> results = keyspace.getSuperRangeSlice(new ColumnParent(columnFamily), pred, startKey, stopKey, currentBatchSize);
		
		
		currentRows = new RowData[results.size()];	
		
		Iterator<String> iterator = results.keySet().iterator();
		
		int count = 0;
		
		while(iterator.hasNext())
		{		
			
			String key = iterator.next();
			
			//Check if we can output this result
			if(!throwAwayFirstResult || !key.equals(startKey))
			{
				//Convert the row and store it for subsequent get requests
				currentRows[count] = new RowData(key, results.get(key));
					
				count++;
			}		
		}
		
		//Check if we Got a Full batch
		if(results.size() < batchSize)
			reachedEnd = true;
				
		
		//Enable the throw away flag
		throwAwayFirstResult = true;
		
		//Reset the row info
		rowsAvalible = count;
		nextRow = 0;
		
		if(rowsAvalible > 0)
		{
			//Update the start key to be the last key we returned so future requests start after the last
			startKey = currentRows[rowsAvalible - 1].getKey();
		}
		
		//Return the key space to the pool
		connection.returnKeySpace(keyspace);
	}
	
	protected class RowData
	{
		private String key;
		private List<SuperColumn> columns;
		
		public RowData(String key, List<SuperColumn> columns)
		{
			//Store the params
			this.key = key;
			this.columns = columns;
		}

		public String getKey()
		{
			return key;
		}

		public List<SuperColumn> getColumns()
		{
			return columns;
		}		
	}
}
