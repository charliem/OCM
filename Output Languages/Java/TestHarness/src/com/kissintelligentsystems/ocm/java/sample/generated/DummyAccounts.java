package com.kissintelligentsystems.ocm.java.sample.generated;

import java.util.Random;

import com.kissintelligentsystems.ocm.java.OCMConnection;


public class DummyAccounts extends com.kissintelligentsystems.ocm.java.BaseTable
{
	String accountID;
	
	public DummyAccounts (OCMConnection connection, String accountID)
	{
		this.accountID = accountID;
	}
	
	@Override
	public void delete() throws Exception
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadAll() throws Exception
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void newKey(Random arg0) throws Exception
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save() throws Exception
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getKey()
	{
		return accountID;
	}

}
