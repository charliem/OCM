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

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import com.kissintellignetsystems.ocm.compiler.parser.ASTColumnFamily;
import com.kissintellignetsystems.ocm.compiler.parser.ASTMany2Many;

public class Validator 
{
	public boolean validate(List<ASTColumnFamily> columnFamilies)
	{
		//We only validate the Many 2 Many Columns so far
		return validateMany2ManyColumns(columnFamilies);
	}
	
	private boolean validateMany2ManyColumns(List<ASTColumnFamily> columnFamilies)
	{
		//In the Form Column.Family to Column.Family
		Hashtable<String,String> relationships = new Hashtable<String, String>();
		
		
		for(int count=0; count < columnFamilies.size(); count++)
		{
			//Get the Column Family
			ASTColumnFamily family = columnFamilies.get(count);
		
			for(int count2=1; count2< family.jjtGetNumChildren(); count2++)
			{
				//Get the column family			
				Object familyObj = family.jjtGetChild(count2);
				
				if(familyObj instanceof ASTMany2Many)
				{
					//Get the column family
					ASTMany2Many parserFamily = (ASTMany2Many)familyObj;
					
					//Add the relation ship
					relationships.put(family.getName() + "." + parserFamily.getName(), parserFamily.getColumnFamily() + "." + parserFamily.getField());
			
				}
			}
		}
		
		//Validate each relationship exists
		Enumeration<String> enumeration = relationships.keys();
		
		boolean result = true;
		
		while(enumeration.hasMoreElements())
		{
			String key = enumeration.nextElement();
			
			String target = relationships.get(key);
			
			if(!relationships.containsKey(target))
			{
				System.out.println("ERROR: Unable to locate reciprocal Many2Many " + target + " for " + key);
				
				result = false;
			}
		}
		return result;
	}
}
