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

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Map.Entry;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.kissintellignetsystems.ocm.compiler.parser.ASTColumnFamily;
import com.kissintellignetsystems.ocm.compiler.parser.ASTField;
import com.kissintellignetsystems.ocm.compiler.parser.ASTSuperFamily;

public class ConfGenerator 
{
	public void generate(List<ASTColumnFamily> columnFamilies, String keyspace, String directory)
	{
		ArrayList<CassandraColumnFamilyInfo> families = new ArrayList<CassandraColumnFamilyInfo>(50);
		
		
		
		Iterator<ASTColumnFamily> columns = columnFamilies.iterator();
		
		while(columns.hasNext())
		{
			ASTColumnFamily family = columns.next();
			
			//Create the entry for the Super Column
			CassandraColumnFamilyInfo entry = new CassandraColumnFamilyInfo();
			
			entry.setSuperCol(true);
			entry.setName(family.getName());
			
			String compareWith = family.getCompareWith();
			
			if(compareWith.length() == 0)
				compareWith = "UTF8Type";
				
			entry.setCompareWith(compareWith);
			
			String compareSubcolumnsWith = family.getCompareSubcolumnsWith();
			
			if(compareSubcolumnsWith.length() == 0)
				compareSubcolumnsWith = "UTF8Type";
				
			entry.setCompareSubColumnsWith(compareSubcolumnsWith);
			
			entry.setComment(family.getComment());
			
			//Add the entry
			families.add(entry);
			
			
			//Output each fields declaration
			for(int count=1; count<family.jjtGetNumChildren(); count++)
			{
				//Get the column family			
				Object familyObj = family.jjtGetChild(count);
				
				if(familyObj instanceof ASTSuperFamily)
				{
					//Get the column family
					ASTSuperFamily parserFamily = (ASTSuperFamily)familyObj;

					
					for(int count2=0; count2 < parserFamily.jjtGetNumChildren(); count2++)
					{
						//Get the field
						ASTField parserField = (ASTField) parserFamily.jjtGetChild(count2);
						
						Iterator<Entry<String,String>> annontationIterator = parserField.getAnnotationEnteries().iterator();
						
						boolean isIndexed = false;
						
						while(annontationIterator.hasNext())
						{
							Entry<String,String> annon = annontationIterator.next();
							
							if(annon.getKey().equals("Indexed"))
							{
								isIndexed = true;								
							}
						}
						
						
						if(isIndexed)
						{
							//Create the entry for the Column Family
							CassandraColumnFamilyInfo indexEntry = new CassandraColumnFamilyInfo();
								
							indexEntry.setName(family.getName() + "By" + parserField.getNameUpperCase());
							indexEntry.setComment("OCM Index Column Fmaily for " + family.getName() + " By " + parserField.getNameUpperCase() + " (Contents Automatically Updated)");
							indexEntry.setCompareWith("UTF8Type");
							
							indexEntry.setSuperCol(false);
							
							//Add the entry
							families.add(indexEntry);
						}
					}	
				}
			}
	        try 
	        {
			
			//Now we have all the info we need lets output the Config XMl File
			VelocityEngine ve = new VelocityEngine();
	        
			Properties props = new Properties();
			
	        //Get Velocity to find the template on the class path
			props.setProperty(ve.RESOURCE_LOADER, "classpath");
			props.setProperty("classpath." + VelocityEngine.RESOURCE_LOADER + ".class", ClasspathResourceLoader.class.getName());
	        
			ve.init(props);

	        //Load the CSharp Table Template
	        Template template = ve.getTemplate("Storage-Conf.xml.vm");
	        
			//Create the new Velocity Context
	        VelocityContext context = new VelocityContext();
			
	        context.put("keyspace", keyspace);
	        context.put("columnFamilies", families);
			
			try
			{
				//Open the output file
				FileWriter writer = new FileWriter(directory + "storage-conf.xml");
				
				//Merge the template and the context
				template.merge(context, writer);
				
				//Close the table file
				writer.close();
			}
			catch (IOException exception)
			{
				System.out.print("There was an IO problem outputing the Cassandra Storage Config File\n\n");			
			}
	        
	        
	        } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
