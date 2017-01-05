package com.cpre.algorithms;

import java.util.ArrayList;
import java.util.List;
/**
 * author Ming
 */
public class LLC 
{
	private List<Task> taskList;
	private List<Interval> intervalList;
	private int simuTime;
	private int[] idAry;
	private int[] startTimeAry;
	private int[] endTimeAry;
	private int[] intervalAry;
	private int curInterval = -1;
	
	public LLC(List<Task> taskList)
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
			System.out.println("This task set is not schedulable.");
	}
	
	public boolean isFeasible(List<Task> taskList)
	{
		double U = 0;
		for (int i = 0; i < taskList.size(); i++)
			U += (double) taskList.get(i).getC() / taskList.get(i).getP();
		
		if (U <= 1)
			return true;
		
		return false;
	}
	
	public void createInterval(List<Task> taskList)
	{
		for (Task t : taskList)
		{
			int n = simuTime / t.getP();
			for (int i = 0; i < n; i++)
			{
				Interval itv = new Interval(t.getID(), t.getC(), (i + 1) * t.getP(), (i + 1) * t.getD(), i * t.getP(), i);
				intervalList.add(itv);
			}
		}
	}
	
	public void addInterval(int curTime, int index)
	{
		curInterval = index;
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
			if (intervalList.get(i).getP() - intervalList.get(i).getC() < min)
			{
				min = intervalList.get(i).getP() - intervalList.get(i).getC();
				index = i;
			}
		}
		
		if (curInterval == -1)
			curInterval = index;
		else if (index == -1)
			return index;
		else if (intervalList.get(curInterval).getP() - intervalList.get(curInterval).getC() 
				== intervalList.get(index).getP() - intervalList.get(index).getC()
				&& intervalList.get(curInterval).getC() > 0)
			index = curInterval;
			
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
}

