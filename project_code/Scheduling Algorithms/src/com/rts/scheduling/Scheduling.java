/*
 * File Name: AlgoA.java, Developed By: Suresh Vadlakonda, Invented by: Dr.Andrei.
 * This is an improvement over LLF on Non Preemptive multi-processor scheduling algorithm.
 */

package com.rts.scheduling;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;


public class Scheduling {

	public static enum SCHEDULING_TYPE {ALGO_EDF, ALGO_LLF, ALGO_A};
	
	//private int numOfTasks, numOfProcessors;
	private List <Processor> processorList;
	private List <Task> taskList;
	private long beginTime, endTime;
	private SCHEDULING_TYPE algo_Name;
	private Level log_level = Level.FINE;
	
	//constructor takes number of processors and number of input task set
	Scheduling()
	{
		
	}
	
	public void setNumberOfProcessors(int numOfProcessors) {
		
		processorList = new ArrayList<Processor>();
		
		for (int i = 0; i < numOfProcessors; i++) {
			processorList.add(new Processor());
		}
	}
	
	public void setTaskList(List<Task> taskList)
	{
		if(taskList != null)
		this.taskList = taskList;
	}
	
	//this method will be called in order to run the algorithms.
	public boolean run(SCHEDULING_TYPE schedule_type)
	{
	
		beginTime = System.nanoTime();
		boolean result = false;
		algo_Name = schedule_type;
		
		//restart the processors
		for (int i = 0; i < processorList.size(); i++) {
			processorList.get(i).restart();
		}
		
		//copy the list elements to new list
		List<Task> newTaskList = new ArrayList<Task>();
		
		for(Task task: taskList)
		{
			newTaskList.add(task);
		}

		ProjectLogger.getLogger().alogrithmBeginLog(log_level, schedule_type.toString(), processorList, taskList);
		switch(schedule_type)
		{
		case ALGO_EDF:
			result = runEDF(newTaskList);
			break;
		
		case ALGO_LLF:
			result = runLLF(newTaskList);
			break;
			
		case ALGO_A:
			result = runA(newTaskList);
			break;
		
		default: System.out.println("invalid algorithm selected");
		}
		endTime = System.nanoTime();
		
		ProjectLogger.getLogger().alogrithmEndLog(log_level,schedule_type.toString(),processorList);
		return result;
	}
	
	
	public void clear()
	{
		if(processorList != null)
			processorList.clear();
		if(taskList != null)
			taskList.clear();
		beginTime = 0;
		endTime = 0;
	}
	
	public void addTask(Task task)
	{
		if(taskList == null)
			taskList = new ArrayList<Task>();
		
		taskList.add(task);
	}
	
	//Implementation of Algorithm starts from here
	
	private boolean runA(List<Task> taskList)
	{
		int [][]tor = null;
		
		boolean isSuccessful = false;
		
		//sort the given input tasks according slack time or laxities
		sortTasksOnLLF(taskList);
		Task task = nextSchedulableTask(taskList);
		while (task != null) {
			
			for (int i = 0; i < processorList.size(); i++)
			{
				if (!(task == null)) {
				// start time of a task is not arrived at the processor, then add an idle task to execute
				while(task.getStartTime()> processorList.get(i).getCurrentTime())
					processorList.get(i).idleTask();
				
					
						isSuccessful = processorList.get(i).scheduleTask(task);
						
						if(!isSuccessful)
						{
							//if this fails, every other process will also fail on their deadline to schedule the task.
							isSuccessful =processorList.get(getNextProcessor()).scheduleTask(task);
							
							
							if(!isSuccessful)
							{
								if(tor == null)
								{
								tor = new int[this.taskList.size()][this.taskList.size()];
								//Implement the TOR functionality here.
								//fill with negative number when tor chain does not exist between tasks.
								for(int row = 0; row<tor.length; row++)
									for(int column= 0; column<tor.length; column++)
									{
										if(this.taskList.get(column).getDeadLine()<this.taskList.get(row).getDeadLine())
										{
											// j is task2, i is task 1, fill the array with x values.
											tor[row][column] = this.taskList.get(row).getDeadLine() - this.taskList.get(row).getComputationTime() - this.taskList.get(column).getComputationTime();
										}
										else
										 tor[row][column] = -1;// means tor does not exits
									}
								}
							}
							//if a process can't schedule the task try to schedule that task on all other processors.
							//using tor, if tor also fails, can't schedule the tasks using Algorithm A.
							for(int n =0; n< processorList.size() && !isSuccessful; n++)
							{
								Task lastTask = processorList.get(n).getLastScheduledTask();
								int x = tor[lastTask.getTaskID()][task.getTaskID()];
								
								int time = processorList.get(n).getCurrentTime() - lastTask.getComputationTime();
								//tor is possible switch the tasks
								if(x >= time)
								{
									//switching and scheduling the tor tasks
									processorList.get(n).removeLastscheduledTask();
									isSuccessful = processorList.get(n).scheduleTask(task);
									isSuccessful = processorList.get(n).scheduleTask(lastTask);
								}
								else
									isSuccessful = false;
							}
							
							if(!isSuccessful)
							{
								//implement TOR functionality here, so that if there exist any chain exits
								/*System.out.println("Not Schedulable Task: T"+(task.getTaskID()+1)+"[ "+
												task.getStartTime()+","+task.getComputationTime()+","+task.getDeadLine()+"]");*/
							
								//taskList.clear();
								ProjectLogger.getLogger().alogrithmFailLog(log_level, algo_Name.toString(), task);
								
								return isSuccessful;
							}
						}
						
							task = nextSchedulableTask(taskList);
					}
				}
		}
		return isSuccessful;
	}
	
	
	private boolean runLLF(List<Task> taskList)
	{
		boolean isSuccessful = false;
		
		//sort the given input tasks according slack time or laxities
		sortTasksOnLLF(taskList);
		
		
		Task task = nextSchedulableTask(taskList);
		while (task != null) {
			for (int i = 0; i < processorList.size(); i++)
			{
				if (!(task == null)) {
				// start time of a task is not arrived at the processor, then add an idle task to execute
				while(task.getStartTime()> processorList.get(i).getCurrentTime())
					processorList.get(i).idleTask();
				
					
						isSuccessful = processorList.get(i).scheduleTask(task);
						
						if(!isSuccessful)
						{
							//if this fails, every other process will also fail on their deadline to schedule the task.
							isSuccessful =processorList.get(getNextProcessor()).scheduleTask(task);
							
							if(!isSuccessful)
							{
								/*System.out.println("Not Schedulable Task: T"+(task.getTaskID()+1)+"[ "+
												task.getStartTime()+","+task.getComputationTime()+","+task.getDeadLine()+"]");*/
							
								//taskList.clear();
								ProjectLogger.getLogger().alogrithmFailLog(log_level, algo_Name.toString(), task);
								return isSuccessful;
							}
						}
						
							task = nextSchedulableTask(taskList);
					}
				}
		}
		return isSuccessful;
	}
	
	// sort the tasks according to the slack time or laxities = deadline - computation time (d-c)
	// improvement has been done by taking care of start time comparison when both tasks d-c are equal
	private void sortTasksOnLLF(List<Task> taskList)
	{
		// we consider all tasks for sorting based on their laxities
		for(int i =0; i<taskList.size()-1; i++)
		{
			int minDeadLine = i;
			boolean bSwap = false;
			for(int j =i+1; j<taskList.size(); j++)
			{
				if((taskList.get(minDeadLine).getDeadLine() - taskList.get(minDeadLine).getComputationTime()) > (taskList.get(j).getDeadLine()-taskList.get(j).getComputationTime()))
				{
					minDeadLine = j;
					bSwap = true;
				}
				else if((taskList.get(minDeadLine).getDeadLine() - taskList.get(minDeadLine).getComputationTime()) == (taskList.get(j).getDeadLine()-taskList.get(j).getComputationTime()))
				{
					if(taskList.get(minDeadLine).getStartTime()> taskList.get(j).getStartTime())
					{
						minDeadLine = j;
						bSwap = true;
					}
				}
			}
			
			//swap the tasks according to the Laxities
			if(bSwap)
			{
				Task tempTask = taskList.get(i);
				taskList.set(i, taskList.get(minDeadLine));
				taskList.set(minDeadLine, tempTask);
			}
		}
	}
	
	
	/*
	 * Implementation of Algorithm starts from here.
	 * 1. sort the given tasks on earliest deadline first
	 * 2. give the tasks to processors depending on priority.
	 * 3. check whether a task is EDF schedulable or not, return false if it is not schedulable otherwise true.
	 */
	
	private boolean runEDF(List<Task> taskList)
	{
		
		boolean isSuccessful = false;
		
		//sort the given input tasks according to dead lines
		sortTasksOnDeadLines(taskList);
		
		Task task = nextSchedulableTask(taskList);
		while (task != null) {
			
			for (int i = 0; i < processorList.size(); i++)
			{
				if (!(task == null)) {
				// start time of a task is not arrived at the processor, then add an idle task to execute
				while(task.getStartTime()> processorList.get(i).getCurrentTime())
					processorList.get(i).idleTask();
				
					
						isSuccessful = processorList.get(i).scheduleTask(task);
						
						if(!isSuccessful)
						{
							//if this fails, every other process will also fail on their deadline to schedule the task.
							isSuccessful =processorList.get(getNextProcessor()).scheduleTask(task);
							
							if(!isSuccessful)
							{
								/*System.out.println("Not Schedulable Task: T"+(task.getTaskID()+1)+"[ "+
												task.getStartTime()+","+task.getComputationTime()+","+task.getDeadLine()+"]");*/
							
								//taskList.clear();
								ProjectLogger.getLogger().alogrithmFailLog(log_level, algo_Name.toString(), task);
								return isSuccessful;
							}
						}
						
							task = nextSchedulableTask(taskList);
					}
				}
		}
		return isSuccessful;
	}
	
	//improvement has been done by taking care of start time comparison when both tasks deadlines are equal
	private void sortTasksOnDeadLines(List<Task> taskList)
	{
		for(int i =0; i<taskList.size()-1; i++)
		{
			int minDeadLine = i;
			boolean bSwap = false;
			for(int j =i+1; j<taskList.size(); j++)
			{
				if(taskList.get(minDeadLine).getDeadLine() > taskList.get(j).getDeadLine())
				{
					minDeadLine = j;
					bSwap = true;
				}
				else if(taskList.get(minDeadLine).getDeadLine() == taskList.get(j).getDeadLine())
				{
					if(taskList.get(minDeadLine).getStartTime()> taskList.get(j).getStartTime())
					{
						minDeadLine = j;
						bSwap = true;
					}
				}
			}
			
			//swap the tasks according to the Laxities
			if(bSwap)
			{
				Task tempTask = taskList.get(i);
				taskList.set(i, taskList.get(minDeadLine));
				taskList.set(minDeadLine, tempTask);
			}
		}
	}

	
	//depending on the time line checks which processor has min current time line and assign task to that processor.
	private int getNextProcessor()
	{
		int minProcIndex = 0;
		
		for(int index =1; index<processorList.size(); index++)
		{
			if(processorList.get(minProcIndex).getCurrentTime() > processorList.get(index).getCurrentTime())
			{
				minProcIndex = index;
			}
		}
		return minProcIndex;
	}
	
	// it gives the next schedulable task based LLF priority. when a task is returned then that task will be removed from the list.
	private Task nextSchedulableTask(List<Task> taskList)
	{
		Task task = null;
		if(taskList.size() !=0)
		{
			task = taskList.get(0);
			taskList.remove(0);
		}
	return task;			
	}
	
	//gives the execution time of algorithm in milli seconds
	public float executedTimeMillis()
	{
		return ((float)(endTime - beginTime))/1000000;
	}
	
	public void displayScheduledTasks()
	{
		for(int i = 0; i< processorList.size(); i++)
		{
			System.out.print("Processor "+(i+1)+"-");
			int taskIndex = 0;
			List <Task> tempTaskList = processorList.get(i).getScheduledTasks();
			while(taskIndex < tempTaskList.size())
			{
				System.out.print("T"+(tempTaskList.get(taskIndex).getTaskID()+1)+"["+
						tempTaskList.get(taskIndex).getStartTime()+","+
						tempTaskList.get(taskIndex).getComputationTime()+","+
						tempTaskList.get(taskIndex).getDeadLine()+"]");
				
				taskIndex++;
			}
			System.out.println();
		}
	}
}