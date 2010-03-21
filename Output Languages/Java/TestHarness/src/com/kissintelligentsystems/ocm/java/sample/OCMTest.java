package com.kissintelligentsystems.ocm.java.sample;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import me.prettyprint.cassandra.service.CassandraClient;
import me.prettyprint.cassandra.service.CassandraClientPool;
import me.prettyprint.cassandra.service.CassandraClientPoolFactory;
import me.prettyprint.cassandra.service.Keyspace;
import me.prettyprint.cassandra.service.PoolExhaustedException;

import org.apache.cassandra.service.ColumnParent;
import org.apache.cassandra.service.SlicePredicate;
import org.apache.cassandra.service.SliceRange;
import org.apache.cassandra.service.SuperColumn;

import com.kissintelligentsystems.ocm.java.OCMConnection;
import com.kissintelligentsystems.ocm.java.sample.generated.Account;
import com.kissintelligentsystems.ocm.java.sample.generated.User;
import com.kissintelligentsystems.ocm.java.sample.generated.User.UserScanner;

public class OCMTest
{
	public static void main(String[] args) throws IllegalStateException, PoolExhaustedException, Exception 
	{
		CassandraClientPool pool = CassandraClientPoolFactory.INSTANCE.get();
		CassandraClient client = pool.borrowClient("localhost", 9160);
	
		
		
		OCMConnection connection = new OCMConnection();
		
		connection.connect("localhost", 9160, "keyspace1");

		
		
		
			
		try 
		{
			Keyspace keyspace = client.getKeyspace("keyspace1");
			
			SlicePredicate pred = new SlicePredicate(null, new SliceRange(new byte[0], new byte[0], false, 1000));
			
			Map<String, List<SuperColumn>> results = keyspace.getSuperRangeSlice(new ColumnParent("Users", null), pred, "charlie1", "charlie5", 100);
			
			Iterator<Entry<String, List<SuperColumn>>> iterator = results.entrySet().iterator();
			
			while(iterator.hasNext())
			{
				Entry<String, List<SuperColumn>> entry = iterator.next();
				
				System.out.println("\n Row: " + entry.getKey());
				
			}
			
			UserScanner scanner = User.getScanner(connection);
			
			scanner.start(-1, "charlie2");
			
			System.out.println("\nScanner Output:");
			
			User scannerOutput = null;
			
			do		
			{
				//Get the next record from the scanner
				scannerOutput = scanner.tryGetNext();
				
				if(scannerOutput != null)
					System.out.println("\tUserID: " + scannerOutput.getUserID() + " email: " + scannerOutput.getEmail());
				
			}
			while(scannerOutput != null);
			
		} 
		catch(Exception exp)
		{
			System.out.println("Error: " + exp.getLocalizedMessage());
			exp.printStackTrace();
		}
		
		
		finally 
		{
			// return client to pool. do it in a finally block to make sure it's executed
			pool.releaseClient(client);
		}

		

		
		//Test the Sample User Column
		User user = new User(connection, "charlie");
		//SampleUserColumnFamily user = new SampleUserColumnFamily(connection, "charlie");
		
		
		
		//SampleUserColumnFamily user = SampleUserColumnFamily.tryGetFromEmail("charlie@kissintelligentsystems.com", connection);
		
		/*
		
		user.setFirstName("Charlie");
		
		
		
		user.setEmail("charlie@kissintelligentsystems.com");
		
		user.setPassword("Test_Password");
		user.setEnabled(true);
		user.setSalt("Test_SALT");

		
		user.getRoles().addColumn("Test1", "This is a test");
		user.getRoles().addColumn("Test2", "This is a another test");
		
		user.save();
		
		
		
		//Create the dummy account
		
	
		
		
		Account account = new Account(connection, "00002");

		account.setName("Charlies Super Cool Test Account");
		account.getUsers().add(user);

		account.save();
		
		*/
		
		
		//Delete an entire account
	//	Account account = new Account(connection, "00001");
	//	account.delete();
		
		
		
		//user.save();
		
		
		
		

		//Delete the user first
		//user.delete();
		
		//user.setEmail("charlie@kissintelligentsystems.com");
		
		//user.setEnabled(false);
		
		//user.save();
	
		
		user.loadAll();
		
		/*
		//Delete account
		DummyAccounts delAccount = user.getAccounts().get("Charlies 2nd Account");
		user.getAccounts().delete(delAccount);
		
		user.save();
		*/
		
		
		System.out.println("UserID: " + user.getUserID());
		
		System.out.println("info:firstName " + user.getFirstName());
		System.out.println("info:email " + user.getEmail());

		System.out.println("auth:password " + user.getPassword());
		System.out.println("auth:salt " + user.getSalt());		
		//System.out.println("auth:enabled " + Boolean.toString(user.getEnabled()));
		
		System.out.println("\nroles:");
		
		Enumeration<String> enumeration = user.getRoles().getColumns().keys();
		
		while(enumeration.hasMoreElements())
		{
			String name = enumeration.nextElement();
			
			String value = user.getRoles().getColumn(name);
			
			System.out.println("\t" + name + " = " + value);
		}
		
		System.out.println("\n\naccounts:");
		
		Enumeration<String> accounts = user.getAccounts().getColumns().keys();
		
		while(accounts.hasMoreElements())
		{
			String name = accounts.nextElement();
			
			Account value = user.getAccounts().get(name);
			
			System.out.println("\t" + name + " = " + value.getKey());
		}
		
	}

	private static byte[] bytes(String value)
	{
		try
		{
			return value.getBytes("UTF8");
		} catch (UnsupportedEncodingException e)
		{
			return new byte[0];
		}
	}
	
	private static String string(byte[] bytes)
	{
		try
		{
			return new String(bytes, "UTF8");
		} catch (UnsupportedEncodingException e)
		{
			return "";
		}
	
	}

}
