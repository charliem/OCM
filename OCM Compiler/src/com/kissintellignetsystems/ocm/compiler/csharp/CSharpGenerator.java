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

package com.kissintellignetsystems.ocm.compiler.csharp;

import java.io.File;
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

import com.kissintellignetsystems.ocm.compiler.Generator;
import com.kissintellignetsystems.ocm.compiler.parser.ASTSuperFamily;
import com.kissintellignetsystems.ocm.compiler.parser.ASTDynamicSuperFamily;
import com.kissintellignetsystems.ocm.compiler.parser.ASTField;
import com.kissintellignetsystems.ocm.compiler.parser.ASTColumnFamily;
import com.kissintellignetsystems.ocm.compiler.parser.ASTOCMSpec;


public class CSharpGenerator extends Generator
{	
	@Override
	public void generate(List<ASTColumnFamily> columnFamilies, String namespace, String directory, String keyspace) 
	{
		try
		{
			VelocityEngine ve = new VelocityEngine();
	        
			Properties props = new Properties();
			
	        //Get Velocity to find the template on the class path
			props.setProperty(ve.RESOURCE_LOADER, "classpath");
			props.setProperty("classpath." + VelocityEngine.RESOURCE_LOADER + ".class",
                   ClasspathResourceLoader.class.getName());
	        
	        ve.init(props);

	        //Load the CSharp Table Template
	        Template template = ve.getTemplate("Templates" + File.separatorChar +  "CSharpTable.vm");
			
			for(int count=0; count < columnFamilies.size(); count++)
			{
				//Get the Column Family
				ASTColumnFamily columnFamily = columnFamilies.get(count);
				
				//Generate the table
				generateCassandraColumnFamily(template, columnFamily, namespace, directory);
			}
		}
		catch(Exception exp)
		{
			System.out.println("An exception occured during C Sharp code generation: " + exp.getLocalizedMessage());
		}
	}
	
	private void generateCassandraColumnFamily(Template template, ASTColumnFamily family, String namespace, String directory)
	{
		//Get the class name
		String className = family.getEntityName();
		
		if(className.equals(""))
		{
			//Use the table name
			className = family.getNameUpperCase();
		}
				
		//Get the key field
		ASTField parserKeyField = (ASTField)family.jjtGetChild(0);
		
		//Convert the key field
		CSharpField keyField = toCSharpField(parserKeyField);
		
		
		//Create the list of the column families
		ArrayList<CSharpColumnFamily> columnFamilies = new ArrayList<CSharpColumnFamily>();
		ArrayList<CSharpDynamicColumn> dynamicColumnFamilies = new ArrayList<CSharpDynamicColumn>();
		
		
		//Output each fields declaration
		for(int count=1; count<family.jjtGetNumChildren(); count++)
		{
			//Get the column family			
			Object familyObj = family.jjtGetChild(count);
			
			if(familyObj instanceof ASTSuperFamily)
			{
				//Get the column family
				ASTSuperFamily parserFamily = (ASTSuperFamily)familyObj;
			
				//Create a new CSharp Column Family
				CSharpColumnFamily superFamily = new CSharpColumnFamily();
				superFamily.setName(parserFamily.getName());
				
				//Add the family 
				columnFamilies.add(superFamily);
				
				for(int count2=0; count2 < parserFamily.jjtGetNumChildren(); count2++)
				{
					//Get the field
					ASTField parserField = (ASTField) parserFamily.jjtGetChild(count2);
					
					//Get the field
					CSharpField field = toCSharpField(parserField);
					
					//Add the field to the family
					superFamily.addField(field);
				}	
			}
			
			else if(familyObj instanceof ASTDynamicSuperFamily)
			{
				//Get the column family
				ASTDynamicSuperFamily parserFamily = (ASTDynamicSuperFamily)familyObj;
			
				//Create a new CSharp Dynamic Column Family
				CSharpDynamicColumn dynamicFamily = new CSharpDynamicColumn();
				
				//Store the family data
				dynamicFamily.setName(parserFamily.getName());
				dynamicFamily.setType(parserFamily.getType());
				
				//Add the family 
				dynamicColumnFamilies.add(dynamicFamily);
			}
		}
		
		//Create the new Velocity Context
        VelocityContext context = new VelocityContext();
		
        //Add the table name
		context.put("tableName", family.getName());
        
		//Add the name space and class name 
		context.put("namespace", namespace);
		context.put("className", className);
		
		//Add the key field
		context.put("keyField", keyField);
		
		//Add the column families
		context.put("families", columnFamilies);
		context.put("dynamicFamilies", dynamicColumnFamilies);
		
		try
		{
			//Open the output file
			FileWriter writer = new FileWriter(directory + className + ".java");
			
			//Merge the template and the context
			template.merge(context, writer);
			
			//Close the table file
			writer.close();
		}
		catch (IOException exception)
		{
			System.out.print("There was an IO problem outputing the HBase Table: " + family.getName() + "\n\n");			
		}
	}
	
	private CSharpField toCSharpField(ASTField parserField)
	{
		//Create the CSharp field
		CSharpField field = new CSharpField();
		
		//Set the basic field info
		field.setName(parserField.getName());
		field.setType(parserField.getType());
		
		//Set the annotations
		Iterator<Entry<String,String>> iterator = parserField.getAnnotationEnteries().iterator();
	    while (iterator.hasNext())
	    {
	    	Entry<String, String> entry = iterator.next();
	    	
	    	//Add each annotation
	    	field.addAnnotation(entry.getKey() , entry.getValue());
	    }

		
		//Set the type specific fields
		if(parserField.getType().equals("Boolean"))
		{
			field.setTypeName("bool");
			field.setToStringValue("toBool");
			field.setFromStringValue("fromBool");
		}
		
		else if(parserField.getType().equals("Byte"))
		{
			field.setTypeName("byte");
			field.setToStringValue("toByte");
			field.setFromStringValue("fromByte");
		}
		
		else if(parserField.getType().equals("ByteStream"))
		{
			field.setTypeName("byte[]");
			field.setToStringValue("toByteArray");
			field.setFromStringValue("fromByteArray");
		}
		
		else if(parserField.getType().equals("Double"))
		{
			field.setTypeName("double");
			field.setToStringValue("toDouble");
			field.setFromStringValue("fromDouble");
		}

		else if(parserField.getType().equals("Float"))
		{
			field.setTypeName("float");
			field.setToStringValue("toFloat");
			field.setFromStringValue("fromFloat");
		}
		
		else if(parserField.getType().equals("Int"))
		{
			field.setTypeName("int");	
			field.setToStringValue("toInt");
			field.setFromStringValue("fromInt");
		}
		
		else if(parserField.getType().equals("Long"))
		{
			field.setTypeName("long");
			field.setToStringValue("toLong");
			field.setFromStringValue("fromLon");
		}
		
		else if(parserField.getType().equals("String"))
		{
			field.setTypeName("String");	
			field.setToStringValue("toString");
			field.setFromStringValue("fromString");
		}
		
		
		//Return the new field
		return field;
	}
}
