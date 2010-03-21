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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class CSharpColumnFamily 
{
	private String name;
	private String nameUpperCase;

	private List<CSharpField> fields;
	
	
	public CSharpColumnFamily()
	{
		//Create  the list to hold all the fields
		fields = new ArrayList<CSharpField>();
	}
	
	public void addField(CSharpField field)
	{
		//Add the field
		fields.add(field);
	}
	
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
	  
	public String toString()
	{ 
		return "Column Family: " + getName(); 
	}

	public Iterator<CSharpField> getFields()
	{
		return fields.iterator();
	}
  
}
