package com.kissintelligentsystems.ocm.java;

public class OCMCell
{
	private byte[] value;
    
	private long timestamp;
	

	public byte[] getValue()
	{
		return value;
	}

	public void setValue(byte[] value)
	{
		this.value = value;
	}

	public long getTimestamp()
	{
		return timestamp;
	}

	public void setTimestamp(long timestamp)
	{
		this.timestamp = timestamp;
	}

	
	
}
