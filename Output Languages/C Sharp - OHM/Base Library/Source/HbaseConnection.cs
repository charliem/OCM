/*
	Copyright 2009 KISS Intelligent Systems Limited
	
	   Licensed under the Apache License, Version 2.0 (the "License");
	   you may not use this file except in compliance with the License.
	   You may obtain a copy of the License at
	
	       http://www.apache.org/licenses/LICENSE-2.0
	
	   Unless required by applicable law or agreed to in writing, software
	   distributed under the License is distributed on an "AS IS" BASIS,
	   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	   See the License for the specific language governing permissions and
	   limitations under the License.
	   
*/

using Apache.Hadoop.Hbase.Thrift;

using OHM;

using System;
using System.Collections.Generic;

using Thrift.Protocol;
using Thrift.Transport;

namespace OHM.Hbase
{
	public class HbaseConnection : OHMConnection
	{
		//Thrift data
		private TTransport transport;
		private TProtocol protocol;

		//The HBase Thrift Client
		private Apache.Hadoop.Hbase.Thrift.Hbase.Client client;

		//Flags if we are currently connected
		private bool isConnected;
		
		public HbaseConnection()
		{
		}

		public bool connect(String address, int port)
		{
			if(IsConnected)
			{
				//Don't bother trying to connect as we are already connected
				return true;
			}
			
			//Create a new thrift socket
			transport = new TSocket(address, port);

			try
			{
				//Attmept to open the transport
				transport.Open();
			}
			catch (Exception exp)
			{				
				isConnected = false;
				
				return false;
			}

			//Create the protocol
			protocol = new TBinaryProtocol(transport);

			client = new Apache.Hadoop.Hbase.Thrift.Hbase.Client(protocol);

			//Flag the new connection status
			isConnected = true;
						
			return true;
		}

		public void closeConnection()
		{
			//Flag that we are closing the conenction
			isConnected = false;
			
			try
			{
				//Attmept to close the transport
				transport.Close();
			}
			catch (Exception exp)
			{
				return;
			}
		}
	
	
		//Delete an entire row
		public void deleteRow(byte[] table, byte[] row)
		{
			client.deleteAllRow(table, row);
		}
		
	
		//Get an individual cell
		public OHMCell getCell(byte[] table, byte[] row, byte[] column)
		{
			TCell tCell = client.get(table, row, column);

			if(tCell != null)
				return new OHMCell(tCell.value, tCell.timestamp);

			return null;                         
		}

		//Get an entire row
		public OHMRow getRow(byte[] table, byte[] row)
		{
			return convertRowResult(client.getRow(table, row));
		}

		private OHMRow convertRowResult(TRowResult result)
		{
			if(result == null)
				return null;

			//Create the columns
			Dictionary<byte[], OHMCell> columns = new Dictionary<byte[], OHMCell>(result.columns.Count);

			foreach(KeyValuePair<byte[], TCell> column in result.columns)
			{
				columns.Add(column.Key, new OHMCell(column.Value.value, column.Value.timestamp));
			}

			//Create and return the row
			return new OHMRow(result.row, columns);
		}

		public void batchRowMutation(byte[] table, OHMRowMutation rowMutation)
		{
			int changeTotal = rowMutation.Changes.Count;

			if(changeTotal == 0)
				//There are no changes so don't bother sending them to HBase
				return;
			
			List<Mutation> changes = new List<Mutation>(changeTotal);

			//Get the first node
			LinkedListNode<OHMCellMutation> currentNode = rowMutation.Changes.First;

			while(currentNode != null)
			{
				Mutation mutation = new Mutation();

				//Store the column
				mutation.column = currentNode.Value.column;
				
				if(!mutation.isDelete)
				{
					mutation.value = currentNode.Value.cell.Value;
					mutation.isDelete = false;
				}
				else
				{
					mutation.isDelete = true;
				}

				//Add the mutation to the list of changes
				changes.Add(mutation);

				//Advance to the next
				currentNode = currentNode.Next;
			}
			
			client.mutateRow(table, rowMutation.RowID, changes);
		}

		//Opens a new scanner at the specified key
		public int openScanner(byte[] table, byte[] key)
		{
			return client.scannerOpen(table, key, new List<byte[]>()); 
		}
		
		//Opens a new scanner at the specified key only returning the listed columns
		public int openScanner(byte[] table, byte[] key, List<byte[]> columnNames)
		{
			return client.scannerOpen(table, key, columnNames); 
		}

		//Opens a new ncanner with a the specified start and stop keys
		public int openScannerWithStop(byte[] table, byte[] startKey, byte[] stopKey)
		{
			return client.scannerOpenWithStop(table, startKey, stopKey, new List<byte[]>()); 
		}

		//Opens a new ncanner with a the specified start and stop keys only returning the listed columns
		public int openScannerWithStop(byte[] table, byte[] startKey, byte[] stopKey, List<byte[]> columnNames)
		{
			return client.scannerOpenWithStop(table, startKey, stopKey, columnNames);
		}

		//Returns the next item from the scanner with the specified scannerID
		public OHMRow scannerGet(int scannerID)
		{
			return convertRowResult(client.scannerGet(scannerID));
		}

		//Closes the scanner the specified scannerID
		public void scannerClose(int scannerID)
		{
			client.scannerClose(scannerID);
		}

		public bool IsConnected
		{
			get { return isConnected; }
		}
	}
	
}
