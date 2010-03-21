/*
	Copyright 2010 KISS Intelligent Systems Limited
	
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

package com.kissintellignetsystems.ocm.compiler;

import java.io.File;
import java.util.ArrayList;

import com.kissintellignetsystems.ocm.compiler.csharp.CSharpGenerator;
import com.kissintellignetsystems.ocm.compiler.java.JavaGenerator;
import com.kissintellignetsystems.ocm.compiler.parser.ASTColumnFamily;
import com.kissintellignetsystems.ocm.compiler.parser.ASTOCMSpec;
import com.kissintellignetsystems.ocm.compiler.parser.OCMParser;

public class Compiler 
{
	
	public static void main(String[] args) throws Exception 
	{
		//Create a new compiler
		Compiler compiler = new Compiler();
		
		//Run the compiler
		compiler.compile(args);		
	}
	
	public void compile(String[] args) throws Exception
	{
		System.out.println("OCM Compiler - The compiler for the Object Cassandra Mapper\n");
		
		File[] sourceFiles;
		
		boolean initialisedParser = false;
		
		ArrayList<ASTColumnFamily> columnFamilies;
		
		//Check if the file name includes a wild card
		if(args[0].contains("*"))
		{
			String parentPath = null;
			String fileName = null;
			
			if(args[0].contains(File.separator))
			{
				int lastSepChar = args[0].lastIndexOf(File.separatorChar);
				fileName = args[0].substring(lastSepChar + 1);
				
				parentPath = args[0].substring(0, lastSepChar);
			}
			
			else
			{
				//Use the whole path
				fileName = args[0];
				
				//Use the current directory
				parentPath = System.getProperty("user.dir");
			}
						
			File dir = new File(parentPath);
			sourceFiles = dir.listFiles(new WildCardFileFilter(fileName));
		}
		else
		{
			sourceFiles = new File[1];
			
			//Add the single file to the array of source files
			sourceFiles[0] = new File(args[0]);
		}
		
		//Create an array list to hold all the Column Families we find
		columnFamilies = new ArrayList<ASTColumnFamily>(sourceFiles.length * 3);
			
		//Parse each source file and store each column family
		for(int count=0; count < sourceFiles.length; count++)
		{
			//Open the current input source file
			java.io.Reader r = new java.io.FileReader(sourceFiles[count]);
			
			if(!initialisedParser)
			{
				//Create the parser with a dummy stream
				new OCMParser(r);
				
				//Make sure we don't reinitialise
				initialisedParser = true;
			}
			else
			{
				//Parse the new stream to the parser
				OCMParser.ReInit(r);
			}
			
			//Parse the data
			ASTOCMSpec parseTree = (ASTOCMSpec)(OCMParser.parse());
			
			
			System.out.println("Parsing: " + sourceFiles[count]);
			System.out.println("\tSyntax Tree Parsed");
			parseTree.dump("\t  ");
			System.out.println("\n\n");

			for(int count2=0; count2 < parseTree.jjtGetNumChildren(); count2++)
			{
				//Add each Column Family to the list of column families
				columnFamilies.add((ASTColumnFamily) parseTree.jjtGetChild(count2));
			}
		}
			
		
		Validator validator = new Validator();
		
		//Validate the Abstract Syntax Tree
		if(!validator.validate(columnFamilies))
		{
			System.out.println("\nCompliation aborted due to validation Errors.");
			
			return;			
		}


		if(args[2].equalsIgnoreCase("CSharp"))
		{
			CSharpGenerator generator = new CSharpGenerator();

			if(args.length == 5)
			{
				//Generate the C Sharp code from the AST
				generator.generate(columnFamilies, args[3], args[1], args[4]);
			}

			else if(args.length == 4)
			{
				//Generate the C Sharp code from the AST
				generator.generate(columnFamilies, args[3], args[1], "");
			}		
		}
		
		else if(args[2].equalsIgnoreCase("Java"))
		{
			JavaGenerator generator = new JavaGenerator();

			if(args.length == 5)
			{
				//Generate the Java code from the AST
				generator.generate(columnFamilies, args[3], args[1], args[4]);
			}

			else if(args.length == 4)
			{
				//Generate the Java code from the AST
				generator.generate(columnFamilies, args[3], args[1], "");
			}		
		}
		
		
		//Generate the storage-conf XML file from the OCM Parse Tree
		ConfGenerator confGenerator = new ConfGenerator();
		
		if(args.length >= 5)
		{
			confGenerator.generate(columnFamilies, args[1], args[4]);
		}
		else
		{
			confGenerator.generate(columnFamilies, args[1], "");
		}
	}
	
}
