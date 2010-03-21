package com.kissintelligentsystems.ocm.java;

import me.prettyprint.cassandra.service.CassandraClient;
import me.prettyprint.cassandra.service.CassandraClientPool;
import me.prettyprint.cassandra.service.CassandraClientPoolFactory;
import me.prettyprint.cassandra.service.Keyspace;

import org.apache.cassandra.service.NotFoundException;
import org.apache.thrift.TException;

public class OCMConnection
{
	private CassandraClient client;
	
	private final CassandraClientPool pool;

	//Flags if we are currently connected
	private boolean isConnected;
	
	//The Cassandra Key space
	private String keySpaceName;
	
	private String[] connectionStr;
	
	public OCMConnection()
	{
		//Get the Cassandra Pool
		pool = CassandraClientPoolFactory.INSTANCE.get();
	}

	public void connect(String address, int port, String keySpaceName)
	{
		//Call the main connection method with one end point
		connect(new String[]{address + ":" + port}, keySpaceName);
	}
	
	public void connect(String[] connectionStr, String keySpaceName)
	{		
		//Store the connection params
		this.connectionStr = connectionStr;
		this.keySpaceName = keySpaceName;
	}
	

	public Keyspace borrowKeySpace() throws IllegalArgumentException, NotFoundException, TException, Exception
	{
		return pool.borrowClient(connectionStr).getKeyspace(keySpaceName);
	}

	public void returnKeySpace(Keyspace keySpace) throws Exception
	{
		pool.releaseKeyspace(keySpace);
	}
}
