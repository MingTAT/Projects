package com.cpre.algorithms;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * author Ming
 */
public class RMS 
{
	private List<Task> taskList;
	private List<Interval> intervalList;
	private int simuTime;
	private int[] idAry;
	private int[] startTimeAry;
	private int[] endTimeAry;
	private int[] intervalAry;
 	
	
	public RMS(List<Task> taskList)
	{
		this.taskList = taskList;
		execute(taskList);
	}
	
	public void execute(List<Task> taskList)
	{
		List<Integer> indexsOfReady = new ArrayList<Integer>();
		if (isFeasible(taskList))
		{
			simuTime = simuTime(taskList, 0, taskList.size());
			idAry = new int[simuTime];
			startTimeAry = new int[simuTime];
			endTimeAry = new int[simuTime];
			intervalAry = new int[simuTime];
			intervalList = new ArrayList<Interval>();

			createInterval(this.taskList);
			
			for (int i = 0; i < simuTime; i++)
			{
				indexsOfReady = getIndexOfReadyInterval(i, intervalList);
				int indexToSchedule = getIndexOfNextInterval(i, indexsOfReady);
				addInterval(i, indexToSchedule);
			}	
		}
		else
			System.out.println("This task set is not schedulable in RMS.");
	}
	
	public boolean isFeasible(List<Task> taskList)
	{
		List<Integer> t = new ArrayList<Integer>();
		sort(taskList);
		t.add(0);
		
		for(Task i : taskList)
			t.set(0, t.get(0) + i.getC());
		
		int i = 1;
		while(true)
		{
			t.add(0);
			for (int j = 0; j < taskList.size(); j++)
				t.set(i, t.get(i) + taskList.get(j).getC() * (int) Math.ceil((double) t.get(i - 1) / taskList.get(j).getP()));
			
			if (t.get(i) > taskList.get(taskList.size() -1).getP())
				return false;
			
			else if (t.get(i) == t.get(i - 1))
				return true;
			
			i++;
		}	
	}
	
	
	public void createInterval(List<Task> taskList)
	{
		for (Task t : taskList)
		{
			int n = simuTime / t.getP();
			for (int i = 0; i < n; i++)
			{
				Interval itv = new Interval(t.getID(), t.getC(), t.getP(), t.getD(), i * t.getP(), i);
				intervalList.add(itv);
			}
		}
	}
	
	public void addInterval(int curTime, int index)
	{
		if (index == -1)
		{
			idAry[curTime] = 0;
			startTimeAry[curTime] = 0;
			endTimeAry[curTime] = 0;
			intervalAry[curTime] = 0;
		}
		else
		{
			idAry[curTime] = intervalList.get(index).getID();
			startTimeAry[curTime] = curTime;
			endTimeAry[curTime] = curTime + 1;
			intervalAry[curTime] = intervalList.get(index).getInterval();
			intervalList.get(index).exe();
		}
	}
	
	
	public List<Integer> getIndexOfReadyInterval(int curTime, List<Interval> intervalList)
	{
		List<Integer> indexOfReadyInterval = new ArrayList<Integer>();
		for (int i = 0; i < intervalList.size(); i++)
		{
			if (intervalList.get(i).getArrT() <= curTime && intervalList.get(i).getC() > 0)
				indexOfReadyInterval.add(i);
		}
		return indexOfReadyInterval;
	}
	
	public int getIndexOfNextInterval(int curTime, List<Integer> indexs)
	{
		int index = -1;
		int min = Integer.MAX_VALUE;
		for (int i : indexs)
		{
			if (intervalList.get(i).getP() < min)
			{
				min = intervalList.get(i).getP();
				index = i;
			}
		}
		
		return index;
	}
	
	public int[] getIDAry()
	{
		return idAry;
	}
	
	public int[] getStartTimeAry()
	{
		return startTimeAry;
	}
	
	public int[] getEndTimeAry()
	{
		return endTimeAry;
	}
	
	public int[] getIntervalAry()
	{
		return intervalAry;
	}
	
// --------------help method-------------------------
	
	
	private int simuTime(List<Task> taskList, int start, int end)
	{
		if (end - start == 1)
			return lcm(taskList.get(start).getP(), taskList.get(end - 1).getP());
		
		return lcm(taskList.get(start).getP(), simuTime(taskList, start + 1, end));
	}
	
	private int lcm(int x, int y)
	{
		if (x == 0 || y == 0)
			return 0;
		
		return (x * y) / gcd(x, y);
	}
	
	private int gcd(int x, int y)
	{
		int z = 0;
		while (y != 0)
		{
			z = x % y;
			x = y;
			y = z;
		}
		
		return x;
	}
	
	private void sort(List<Task> taskList)
	{
		for (int i = 1; i < taskList.size(); i++)
		{
			Task t = taskList.get(i);
			for (int j = i - 1; j >= 0 && t.getP() < taskList.get(j).getP(); j--)
			{
				Collections.swap(taskList, j, j+1);
			}
		}
	}
}

