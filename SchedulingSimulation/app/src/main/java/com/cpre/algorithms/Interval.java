package com.cpre.algorithms;
/**
 * author Ming
 */
public class Interval 
{
	private int id;
	private int c;
	private int p;
	private int d;
	private int arrT;
	private int interval;
	
	public Interval(int id, int c, int p, int d, int arrT, int interval)
	{
		this.id = id;
		this.c = c;
		this.p = p;
		this.d = d;
		this.arrT = arrT;
		this.interval = interval;
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
	
	public int getArrT()
	{
		return arrT;
	}
	
	public int getInterval()
	{
		return interval;
	}
	
	public void exe()
	{
		c -= 1;
	}
}

