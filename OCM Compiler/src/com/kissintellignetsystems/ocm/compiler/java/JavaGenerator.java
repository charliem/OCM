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


package com.kissintellignetsystems.ocm.compiler.java;

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
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.kissintellignetsystems.ocm.compiler.Generator;
import com.kissintellignetsystems.ocm.compiler.parser.ASTColumnFamily;
import com.kissintellignetsystems.ocm.compiler.parser.ASTDynamicSuperFamily;
import com.kissintellignetsystems.ocm.compiler.parser.ASTField;
import com.kissintellignetsystems.ocm.compiler.parser.ASTMany2Many;
import com.kissintellignetsystems.ocm.compiler.parser.ASTSuperFamily;


public class JavaGenerator extends Generator
{	
	@Override
	public void generate(List<ASTColumnFamily> columnFamilies, String namespace,  String keyspace, String directory) 
	{
		try
		{
			VelocityEngine ve = new VelocityEngine();
	        
			Properties props = new Properties();
			
	        //Get Velocity to find the template on the class path
			props.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
			props.setProperty("classpath." + VelocityEngine.RESOURCE_LOADER + ".class",
                   ClasspathResourceLoader.class.getName());
	        
	        ve.init(props);

	        //Load the CSharp Table Template
	        Template template = ve.getTemplate("JavaColumnFamily.vm");
			
			for(int count=0; count < columnFamilies.size(); count++)
			{
				//Generate the each Column Family's code
				generateCassandraColumnFamily(template, columnFamilies, count, namespace, directory);
			}
		}
		catch(Exception exp)
		{
			System.out.println("An exception occured during Java code generation: " + exp.getLocalizedMessage());
		}
	}
	
	private void generateCassandraColumnFamily(Template template, List<ASTColumnFamily> columnFamilies, int currentFamily, String packageStr, String directory)
	{
		ASTColumnFamily family = columnFamilies.get(currentFamily);
		
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
		JavaField keyField = toJavaField(parserKeyField);
		
		
		//Create the list of the column families
		ArrayList<JavaColumnFamily> superFamilies = new ArrayList<JavaColumnFamily>();
		ArrayList<JavaDynamicColumn> dynamicSuperFamilies = new ArrayList<JavaDynamicColumn>();
		ArrayList<JavaMany2Many> many2ManySuperFamilies = new ArrayList<JavaMany2Many>();

		
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
				JavaColumnFamily superFamily = new JavaColumnFamily();
				superFamily.setName(parserFamily.getName());
				
				//Add the family 
				superFamilies.add(superFamily);
				
				for(int count2=0; count2 < parserFamily.jjtGetNumChildren(); count2++)
				{
					//Get the field
					ASTField parserField = (ASTField) parserFamily.jjtGetChild(count2);
					
					//Get the field
					JavaField field = toJavaField(parserField);
					
					//Add the field to the family
					superFamily.addField(field);
				}	
			}
			
			else if(familyObj instanceof ASTDynamicSuperFamily)
			{
				//Get the column family
				ASTDynamicSuperFamily parserFamily = (ASTDynamicSuperFamily)familyObj;
			
				//Create a new CSharp Dynamic Column Family
				JavaDynamicColumn dynamicFamily = new JavaDynamicColumn();
				
				//Store the family data
				dynamicFamily.setName(parserFamily.getName());
				dynamicFamily.setType(parserFamily.getType());
				
				//Add the family 
				dynamicSuperFamilies.add(dynamicFamily);
			}
			
			else if(familyObj instanceof ASTMany2Many)
			{
				//Get the column family
				ASTMany2Many parserFamily = (ASTMany2Many)familyObj;
			
				//Create a new CSharp Dynamic Column Family
				JavaMany2Many many2Many = new JavaMany2Many();
				
				//Store the relationship data
				many2Many.setName(parserFamily.getName());
				many2Many.setColumnFamily(parserFamily.getColumnFamily());
				many2Many.setField(parserFamily.getField());

				for(int count2=0; count2 < columnFamilies.size(); count2++)
				{
					ASTColumnFamily foriegnFamily = columnFamilies.get(count2);

					if(foriegnFamily.getName().equals(many2Many.getColumnFamily()))
					{
						//Store the entity name
						many2Many.setEntityName(foriegnFamily.getEntityName());
					}
				}
				
				//Add the family 
				many2ManySuperFamilies.add(many2Many);
			}
		}
		
		//Create the new Velocity Context
        VelocityContext context = new VelocityContext();
		
        //Add the table name
		context.put("columnFamilyName", family.getName());
        
		//Add the package and class name 
		context.put("package", packageStr);
		context.put("className", className);
		
		//Add the key field
		context.put("keyField", keyField);
		
		//Add the column families
		context.put("families", superFamilies);
		context.put("dynamicFamilies", dynamicSuperFamilies);
		context.put("many2ManyFamilies", many2ManySuperFamilies);

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
			System.out.print("There was an IO problem outputing the Cassandra Column Family: " + family.getName() + "\n\n");			
		}
	}
	
	private JavaField toJavaField(ASTField parserField)
	{
		//Create the CSharp field
		JavaField field = new JavaField();
		
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
			field.setTypeName("boolean");
			field.setToStringValue("toBoolean");
			field.setFromStringValue("fromBoolean");
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
			field.setFromStringValue("fromLong");
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
