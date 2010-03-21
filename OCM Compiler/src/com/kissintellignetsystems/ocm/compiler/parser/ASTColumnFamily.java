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

package com.kissintellignetsystems.ocm.compiler.parser;

import java.util.Locale;


public class ASTColumnFamily extends SimpleNode {
	
	private String name;
	private String nameUpperCase;
	
	private String entityName = "";
	
	private String comment = "";
	private String compareWith = "";
	private String compareSubcolumnsWith = "";
	
  public ASTColumnFamily(int id) {
    super(id);
  }

  public ASTColumnFamily(OCMParser p, int id) {
    super(p, id);
  }

public String getName() {
	return name;
}

public void setName(String name) {
	
	this.nameUpperCase = name.substring(0,1);
	this.nameUpperCase = nameUpperCase.toUpperCase(Locale.UK);
	
	String rest =  name.substring(1);
	
	//Store both versions of the name
	this.name = name;
	this.nameUpperCase = nameUpperCase + rest;
}

public String getNameUpperCase() {
	return nameUpperCase;
}

public void setAnnotation(String anName, String anValue)
{
	//Remove the outside quotes
	String value = anValue.substring(1, anValue.length() - 1);
	
	if(anName.equals("EntityName"))
	{
		//Make sure the entity names first letter is upper case
		entityName = value.substring(0,1).toUpperCase(Locale.UK) + value.substring(1);
	}
	
	else if(anName.equals("Comment"))
	{
		comment = value;
	}
	
	else if(anName.equals("CompareWith"))
	{
		compareWith = value;
	}
	
	else if(anName.equals("CompareSubcolumnsWith"))
	{
		compareSubcolumnsWith = value;
	}
}

public String toString() { return "\nColumn Family: " + getName() + " Entity Name: " + entityName;}
public String toString(String prefix) { return prefix + toString(); }

public String getEntityName() {
	return entityName;
}

public String getComment() {
	return comment;
}

public String getCompareWith() {
	return compareWith;
}

public String getCompareSubcolumnsWith() {
	return compareSubcolumnsWith;
}

  
}
