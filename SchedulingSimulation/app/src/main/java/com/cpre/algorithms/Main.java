package com.cpre.algorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
/**
 * author Ming
 */
public class Main 
{

	public static void main(String[] args) throws NumberFormatException, IOException 
	{
		int c;
		int p;
		int d;
		int idCounter = 0;
		List<Task> taskList = new ArrayList<Task>();
		int[] rmsIDs, rmsStartTime, rmsEndTime, rmsInterval;
		int[] edfIDs, edfStartTime, edfEndTime, edfInterval;
		int[] llcIDs, llcStartTime, llcEndTime, llcInterval;
		
		
		while (true)
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Task Scheduler"  + " (Press y to add tasks, press n to finish entering tasks.)");
			String in = br.readLine();
			if (in.equals("y"))
			{
				idCounter ++;
				System.out.print("c: ");
				c = Integer.parseInt(br.readLine());
				System.out.print("p: ");
				p = Integer.parseInt(br.readLine());
				System.out.print("d: ");
				d = Integer.parseInt(br.readLine());
				Task t = new Task(idCounter, c, p, d);
				taskList.add(t);
				System.out.println();
				System.out.println("T" + idCounter + "(" + c + ", " + p + ", " + d + ") is added" );
			}
			else if (in.equals("n"))
			{
				System.out.println();
				System.out.println(idCounter + " tasks in total");
				System.out.println();
				break;
			}
			System.out.println();
		}
		
		
		RMS rmsScheduler = new RMS(taskList);
		rmsIDs = rmsScheduler.getIDAry();
		rmsStartTime = rmsScheduler.getStartTimeAry();
		rmsEndTime = rmsScheduler.getEndTimeAry();
		rmsInterval = rmsScheduler.getIntervalAry();
		
		EDF edfScheduler = new EDF(taskList);
		edfIDs = edfScheduler.getIDAry();
		edfStartTime = edfScheduler.getStartTimeAry();
		edfEndTime = edfScheduler.getEndTimeAry();
		edfInterval = edfScheduler.getIntervalAry();
		
		LLC llcScheduler = new LLC(taskList);
		llcIDs = llcScheduler.getIDAry();
		llcStartTime = llcScheduler.getStartTimeAry();
		llcEndTime = llcScheduler.getEndTimeAry();
		llcInterval = llcScheduler.getIntervalAry();
		
		System.out.println("RMS:");
		for (int i = 0; i < rmsIDs.length; i++)
		{
			int id = rmsIDs[i];
			if (id == 0)
				System.out.print("idle ");
			else
				System.out.print("T" + rmsIDs[i] + " ");
		}
		System.out.println();
		
		System.out.println("EDF:");
		for (int i = 0; i < edfIDs.length; i++)
		{
			int id = edfIDs[i];
			if (id == 0)
				System.out.print("idle ");
			else
				System.out.print("T" + edfIDs[i] + " ");
		}
		System.out.println();
		
		System.out.println("LLF:");
		for (int i = 0; i < llcIDs.length; i++)
		{
			int id = llcIDs[i];
			if (id == 0)
				System.out.print("idle ");
			else
				System.out.print("T" + llcIDs[i] + " ");
		}
		System.out.println();
		
			
		

	}

}

