package com.cpre.algorithms;

import java.io.Serializable;
/**
 * author Ming
 */
public class Task implements Serializable
{
	private int id;
	private int c;
	private int p;
	private int d;
	
	
	public Task(int id, int c, int p, int d)
	{
		this.id = id;
		this.c = c;
		this.p = p;
		this.d = d;
	}
	
	public int getID()
	{
		return id;
	}
	
	public int getC()
	{
		return c;
	}
	
	public int getP()
	{
		return p;
	}
	
	public int getD()
	{
		return d;
	}
}

