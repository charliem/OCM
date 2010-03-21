// OHMTest.cs created with MonoDevelop
// User: charlie at 09:29 24/08/2008
//
// To change standard headers go to Edit->Preferences->Coding->Standard Headers
//

using System;
using System.Collections.Generic;

using OHM;
using OHM.Hbase;

namespace OHM.Example1
{	
	public class OHMTest
	{
		
		public OHMTest()
		{
		}

		public void test()
		{			
			//Create a new connection and connect
			HbaseConnection connection = new HbaseConnection();

			//Connect to the HBase DB
			if(!connection.connect("localhost", 9090))
			{
				throw new Exception("Could Not Connect to HBase Thrift Server");
			}
			
			//Create a new user
			User user = new User(connection, "charlie");

			//Get just the auth info
			user.loadAuth();

			//Display the first name
			Console.WriteLine("First Name: " + user.FirstName);

			//Display the output
			displayUser(user);

			//Get all the data from the row
			user.loadAll();

			//Display the output
			displayUser(user);

			//Change some details
			user.FirstName = "Bob";
			user.LastName = "The Tester";
			user.Enabled = false;
			user.Email = "charlie@example.org";

			//Display the output
			displayUser(user);

			//Save the changes
			user.save();

			User user2;

			Console.WriteLine("Getting by Email");
			
			if(User.tryGetFromEmail("charlie@example.org", connection, out user2))
			{
				//Get all user info again
				user2.loadAll();
	
				//Display the output
				displayUser(user2);
	
				//Change the details back
				user2.FirstName = "Charlie";
				user2.LastName = "Mason";
	
				//Save the details back
				user2.save();
			}
			else
			{
				Console.WriteLine("User Not Found");
			}

			connection.closeConnection();
		}

		private void displayUser(User user)
		{
			Console.WriteLine("User Values:");
			
			Console.WriteLine("\tUser ID: " + user.UserID);
			
			Console.WriteLine("\tPassword: " + user.Password);
			Console.WriteLine("\tSalt: " + user.Salt);
			Console.WriteLine("\tEnabled " + user.Enabled);

						
			Console.WriteLine("\tFirst Name: " + user.FirstName);
			Console.WriteLine("\tLast Name: " + user.LastName);
			Console.WriteLine("\tEmail: " + user.Email);
			Console.WriteLine();
		}
	}
}
