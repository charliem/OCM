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

public class CassandraColumnFamilyInfo 
{
	private boolean superCol;
	
	private String name;
	private String compareWith;
	private String comment;
	
	private String compareSubColumnsWith;

	
	
	public boolean isSuperCol() {
		return superCol;
	}

	public void setSuperCol(boolean superCol) {
		this.superCol = superCol;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompareWith() {
		return compareWith;
	}

	public void setCompareWith(String compareWith) {
		this.compareWith = compareWith;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCompareSubColumnsWith() {
		return compareSubColumnsWith;
	}

	public void setCompareSubColumnsWith(String compareSubcolumnsWith) {
		this.compareSubColumnsWith = compareSubcolumnsWith;
	}
}
