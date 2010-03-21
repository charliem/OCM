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

import java.util.Locale;

public class JavaMany2Many 
{
	private String name;
	private String nameUpperCase;
	
	private String columnFamily;
	private String columnFamilyUpperCase;
	
	private String field;
	private String fieldUpperCase;
	
	private String entityName;

	
  	public String getName() 
  	{
		return name;
	}
  
  	public void setName(String name) 
  	{
		
		this.nameUpperCase = name.substring(0,1);
		this.nameUpperCase = nameUpperCase.toUpperCase(Locale.UK);
		
		String rest =  name.substring(1);
		
		//Store both versions of the name
		this.name = name;
		this.nameUpperCase = nameUpperCase + rest;
	}
  
  	public String getNameUpperCase() 
  	{
		return nameUpperCase;
	}
  
  	public String toString() { return "Many 2 Many: " + getName() + " Destination: " + columnFamily + "." + field;}

	public String getColumnFamily() {
		return columnFamily;
	}

	public void setColumnFamily(String columnFamily) 
	{	
		this.columnFamilyUpperCase = columnFamily.substring(0,1);
		this.columnFamilyUpperCase = columnFamilyUpperCase.toUpperCase(Locale.UK);
		
		String rest =  columnFamily.substring(1);
		
		//Store both versions of the name
		this.columnFamily = columnFamily;
		this.columnFamilyUpperCase = columnFamilyUpperCase + rest;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) 
	{
		this.fieldUpperCase = field.substring(0,1);
		this.fieldUpperCase = fieldUpperCase.toUpperCase(Locale.UK);
		
		String rest =  field.substring(1);
		
		//Store both versions of the name
		this.field = field;
		this.fieldUpperCase = fieldUpperCase + rest;
	}


	public String getColumnFamilyUpperCase() {
		return columnFamilyUpperCase;
	}

	public String getFieldUpperCase() {
		return fieldUpperCase;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	
	
}
