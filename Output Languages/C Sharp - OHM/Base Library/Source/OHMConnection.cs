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

using System;
using System.Collections.Generic;

namespace OHM
{
	public interface OHMConnection
	{
		//Attempts to connects to the DB
		bool connect(String address, int port);
		
		//Delete an entire row
		void deleteRow(byte[] table, byte[] row);
		
		//Get an individual cell
		OHMCell getCell(byte[] table, byte[] row, byte[] column);

		//Get an entire row
		OHMRow getRow(byte[] table, byte[] row);

		void batchRowMutation(byte[] table, OHMRowMutation mutation);

		//Opens a new scanner at the specified key
		int openScanner(byte[] table, byte[] key);
		
		//Opens a new scanner at the specified key only returning the listed columns
		int openScanner(byte[] table, byte[] key, List<byte[]> columnNames);

		//Opens a new ncanner with a the specified start and stop keys
		int openScannerWithStop(byte[] table, byte[] startKey, byte[] stopKey);

		//Opens a new ncanner with a the specified start and stop keys only returning the listed columns
		int openScannerWithStop(byte[] table, byte[] startKey, byte[] stopKey, List<byte[]> columnNames);

		//Returns the next item from the scanner with the specified scannerID
		OHMRow scannerGet(int scannerID);

		//Closes the scanner the specified scannerID
		void scannerClose(int scannerID);

		//Closes the DB's connection
		void closeConnection();

		//Returns the current status of the connection
		bool IsConnected { get; }
	}
}
