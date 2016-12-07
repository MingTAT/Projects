package com.rts.scheduling;

public class Task {

	private int startTime, computationTime, deadLine, period;
	private int taskID;
	
	public Task(int taskID, int startTime, int computationTime, int deadLine)
	{
		this.taskID = taskID;
		this.startTime = startTime;
		this.computationTime = computationTime;
		this.deadLine = deadLine;
		this.period = deadLine;
	}
	
	public Task(int taskID, int startTime, int computationTime, int deadLine, int period)
	{
		this.taskID = taskID;
		this.startTime = startTime;
		this.computationTime = computationTime;
		this.deadLine = deadLine;
		this.period = period;
	}
	
	public void setStartTime(int startTime)
	{
		this.startTime = startTime;
	}
	
	public void setDeadLine(int deadLine)
	{
		this.deadLine = deadLine;
	}
	
	public void setComputationTime(int computationTime)
	{
		this.computationTime = computationTime;
	}
	
	public void setPeriod(int period)
	{
		this.period = period;
	}
	
	public int getStartTime()
	{
		return startTime;
	}
	
	public int getDeadLine()
	{
		return deadLine;
	}
	
	public int getComputationTime()
	{
		return computationTime;
	}
	
	public int getPeriod()
	{
		return  period;
	}
	
	public int getTaskID()
	{
		return taskID;
	}
	
}
