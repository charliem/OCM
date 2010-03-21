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


public class ASTMany2Many extends SimpleNode {
	
	private String name;
	private String nameUpperCase;
	
	private String columnFamily;
	private String field;
	

public ASTMany2Many(int id) {
    super(id);
  }

  public ASTMany2Many(OCMParser p, int id) {
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
		this.name = nameUpperCase.toLowerCase(Locale.UK) + rest;
		this.nameUpperCase = nameUpperCase + rest;
	}
  
  public String getNameUpperCase() {
		return nameUpperCase;
	}
  
public String toString() { return "Many 2 Many: " + getName() + " Destination: " + columnFamily + "." + field;}
public String toString(String prefix) { return prefix + toString(); }

public String getColumnFamily() {
	return columnFamily;
}

public void setColumnFamily(String columnFamily) {
	this.columnFamily = columnFamily;
}

public String getField() {
	return field;
}

public void setField(String field) {
	this.field = field;
}




  
}
