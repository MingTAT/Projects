package com.rts.scheduling;

import java.util.ArrayList;
import java.util.List;

public class Processor {
	private List <Task>scheduledTasks;
	private int currentTime =0;
	
	public Processor()
	{
		scheduledTasks = new ArrayList<Task>();
	}
	
	public boolean scheduleTask(Task task)
	{
		boolean isSchedulable =true;
		
		if (0 <= task.getDeadLine() - (currentTime + task.getComputationTime())) {
			currentTime += task.getComputationTime();
			scheduledTasks.add(task);
		}
		else
			isSchedulable =false;
		
		return isSchedulable;	
			
	}
	
	
	public int getCurrentTime()
	{
		return currentTime;
	}
	
	public void idleTask()
	{
		currentTime++;
	}
	
	public Task getLastScheduledTask()
	{
		return scheduledTasks.get(scheduledTasks.size() -1 );
	}
	
	public void removeLastscheduledTask()
	{
		currentTime -=scheduledTasks.get(scheduledTasks.size() - 1).getComputationTime();
		scheduledTasks.remove(scheduledTasks.size() -1 );
	}
	
	public List<Task> getScheduledTasks()
	{
		return scheduledTasks;
	}
	
	public void restart()
	{
		scheduledTasks.clear();
		currentTime = 0;
	}
}
