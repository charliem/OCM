// Main.cs created with MonoDevelop
// User: charlie at 16:21 23/08/2008
//
// To change standard headers go to Edit->Preferences->Coding->Standard Headers
//
using System;

namespace OHM.Example1
{
	class MainClass
	{
		public static void Main(string[] args)
		{
			Console.WriteLine("OHM - Object Hbase Mapper Sample\n");

			Console.WriteLine();

			//Initialise and run the OHM test
			OHMTest ohm = new OHMTest();
			ohm.test();
		}
	}
}