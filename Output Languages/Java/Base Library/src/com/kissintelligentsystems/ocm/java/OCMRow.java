package com.kissintelligentsystems.ocm.java;

import java.util.Dictionary;
import java.util.Hashtable;

public class OCMRow
{
	private byte[] rowID;
	
	private Dictionary<byte[], OCMCell> columns;
	
	public OCMRow(byte[] rowID)
	{
		//Store the row ID
		this.rowID = rowID;

		//Initialise the columns dictionary
		columns = new Hashtable<byte[], OCMCell>(20);
	}

	public OCMRow(byte[] rowID, Dictionary<byte[], OCMCell> columns)
	{
		//Store the params
		this.rowID = rowID;
		this.columns = columns;
	}

	
	public byte[] getRowID()
	{
		return rowID;
	}

	public void setRowID(byte[] rowID)
	{
		this.rowID = rowID;
	}

	public Dictionary<byte[], OCMCell> getColumns()
	{
		return columns;
	}

	public void setColumns(Dictionary<byte[], OCMCell> columns)
	{
		this.columns = columns;
	}


}
