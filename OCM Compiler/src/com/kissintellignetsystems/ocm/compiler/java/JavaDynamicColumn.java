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

public class JavaDynamicColumn 
{
	private String type;
	
	private String name;
	private String nameUpperCase;
	
	private String typeName;
	private String fromStringValue;
	private String toStringValue;

	public String getFromStringValue() 
	{
		return fromStringValue;
	}

	private void setFromStringValue(String fromStringValue) 
	{
		this.fromStringValue = fromStringValue;
	}

	public String getToStringValue() 
	{
		return toStringValue;
	}

	private void setToStringValue(String toStringValue) 
	{
		this.toStringValue = toStringValue;
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
		this.name = nameUpperCase.toLowerCase(Locale.UK) + rest;
		this.nameUpperCase = nameUpperCase + rest;
	}
  
  	public String getNameUpperCase() 
  	{
		return nameUpperCase;
	}
  
  	public String toString() { return "Dynamic Column Family: " + getName() + " Type: " + getType();}

	public String getType() 
	{
		return type;
	}

	public void setType(String type) 
	{
		this.type = type;
		
		updateTypeInfo();
	}
	
	public String getTypeName() 
	{
		return typeName;
	}
	
	private void setTypeName(String typeName) 
	{
		this.typeName = typeName;
	}
	
	private void updateTypeInfo()
	{
	
		if(type.equals("Boolean"))
		{
			setTypeName("bool");
			setToStringValue("toBool");
			setFromStringValue("fromBool");
		}
		
		else if(type.equals("Byte"))
		{
			setTypeName("byte");
			setToStringValue("toByte");
			setFromStringValue("fromByte");
		}
		
		else if(type.equals("ByteStream"))
		{
			setTypeName("byte[]");
			setToStringValue("toByteArray");
			setFromStringValue("fromByteArray");
		}
		
		else if(type.equals("Double"))
		{
			setTypeName("double");
			setToStringValue("toDouble");
			setFromStringValue("fromDouble");
		}
	
		else if(type.equals("Float"))
		{
			setTypeName("float");
			setToStringValue("toFloat");
			setFromStringValue("fromFloat");
		}
		
		else if(type.equals("Int"))
		{
			setTypeName("int");	
			setToStringValue("toInt");
			setFromStringValue("fromInt");
		}
		
		else if(type.equals("Long"))
		{
			setTypeName("long");
			setToStringValue("toLong");
			setFromStringValue("fromLong");
		}
		
		else if(type.equals("String"))
		{
			setTypeName("String");	
			setToStringValue("toString");
			setFromStringValue("fromString");
		}
		
		else if(type.equals("UInt"))
		{
			setTypeName("uint");	
			setToStringValue("toUInt");
			setFromStringValue("fromUInt");
		}
		
		else if(type.equals("ULong"))
		{
			setTypeName("ulong");
			setToStringValue("toULong");
			setFromStringValue("fromULong");
		}
	}

}
