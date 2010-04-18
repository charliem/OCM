package com.kissintelligentsystems.ocm.java;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Random;

import me.prettyprint.cassandra.service.Keyspace;

import org.apache.thrift.TException;

public abstract class BaseTable 
{
	public abstract String getKey();
	
	public abstract void newKey(Random rand) throws Exception;
	
	public abstract void save() throws Exception;

	public abstract void delete() throws Exception;

	public abstract void loadAll() throws Exception;

	
	protected String generateNewKey(OCMConnection connection, byte[] tableName, Random rand) throws IllegalArgumentException, org.apache.cassandra.thrift.NotFoundException, TException, Exception
	{
		Keyspace keyspace = null;
		String key = null;
		try
		{
			keyspace = connection.borrowKeySpace();
			

						
			//Generate a new random 5 digit numbers
			int no1 = rand.nextInt();
			int no2 = rand.nextInt();
			int no3 = rand.nextInt();
			
			key = Integer.toString(no1) +  Integer.toString(no2) + Integer.toString(no3);	
		}
		finally
		{
			connection.returnKeySpace(keyspace);
		}
		
		return key;
	}
	
	
	public static byte[] fromBoolean(boolean value)
	{	
		byte[] result = new byte[1];
		
		if(value)
			result[0] = 1; 
		
		else
			result[0] = 0; 
		
		return result;
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
		ByteBuffer bb = ByteBuffer.allocate(4); 
		bb.putDouble(value); 
		return bb.array();
	}

	public static byte[] fromFloat(float value)
	{
		ByteBuffer bb = ByteBuffer.allocate(4); 
		bb.putFloat(value); 
		return bb.array();
	}
	
	public static byte[] fromInt(int value)
	{
		ByteBuffer bb = ByteBuffer.allocate(4); 
		bb.putInt(value); 
		return bb.array();
	}

	public static byte[] fromLong(long value)
	{
		ByteBuffer bb = ByteBuffer.allocate(4); 
		bb.putLong(value); 
		return bb.array();
	}

	public static byte[] fromString(String value)
	{
		try
		{
			//Use the UTF8 encoding
			return value.getBytes("UTF8");
			
		} catch (UnsupportedEncodingException e)
		{
			return new byte[0];
		}
	}

	
	public static boolean toBoolean(byte[] data)
	{
		if(data.length == 1)
		{
			if(data[0] == 1)
				return true;
			
			else
				return false;
		}	
		
		return false;
	}

	public static byte toByte(byte[] data)
	{
		if(data.length > 0)
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
		ByteBuffer bb = ByteBuffer.wrap(data);
		return bb.getDouble();
	}

	public static float toFloat(byte[] data)
	{
		ByteBuffer bb = ByteBuffer.wrap(data);
		return bb.getFloat();
	}
	
	public static int toInt(byte[] data)
	{
		ByteBuffer bb = ByteBuffer.wrap(data);
		return bb.getInt();
	}

	public static long toLong(byte[] data)
	{
		ByteBuffer bb = ByteBuffer.wrap(data);
		return bb.getLong();
	}
	
	public static String toString(byte[] data)
	{
		try
		{
			//Use the UTF8 encoding

			return new String(data, "UTF8");
		}
		catch (UnsupportedEncodingException e)
		{
			return null;
		}
	}
}
