package com.rts.scheduling;

import java.io.IOException;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/*
 *-----------singleton pattern class----------------
 * Please create a folder called log in the working directory
 * This file is to log the information, about algorithm success, begin and failure
 * Simple log message can be given, most importantly given an option to set the level of logging
 * LEVEL flag will result below behaviors
 * FINE, FINER level will log detailed information
 * Other levels would log minimal information
 * OFF would turn off the log
 */
public class ProjectLogger {
	private static ProjectLogger projLoggerInstance= new ProjectLogger();
	
	private static Logger logger;  
	FileHandler fileHandler; 
	
	/*
	 * This flag set will control the level of logging
	 * FileHandling and Console Handling are enable or disable
	 */
	public void SetLogLevel(Level log_level)
	{
		fileHandler.setLevel(log_level);
		logger.setLevel(log_level);
	}

	/*
	 * Singleton object is given to the outside classes through this getter method
	 */
	public static ProjectLogger getLogger()
	{
		return projLoggerInstance;
	}
	
	private ProjectLogger() 
	{
		logger= Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		logger.setLevel(Level.ALL);
		
		SimpleFormatter formatter = new SimpleFormatter();
		try {
			fileHandler = new FileHandler("log/log.txt");
			fileHandler.setFormatter(formatter);
			fileHandler.setLevel(Level.ALL);
			logger.addHandler(fileHandler);
			
		} catch (SecurityException | IOException e) {
			
			e.printStackTrace();
		}
	}
	
	/*
	 * AlgorithmBeginLog will take input as Number of Processors, Number of tasks,
	 * Algorithm Name, Processors information to log
	 * Begin of the algorithm to log 
	 */
	public void alogrithmBeginLog(Level log_level, String algorithmName, List<Processor> processors, List<Task> tasks)
	{
		if(log_level == Level.FINE || log_level == Level.FINER)
		{
			StringBuilder logMessage = new StringBuilder(" ");
			logMessage.append("Algorithm Name:  ").append(algorithmName).append("\n")
				.append("Num Of Processors: ").append(processors.size()).append("\n")
				.append("Num of tasks").append(tasks.size());
		
			for(Task task: tasks)
			{
				logMessage.append("Task ID: ").append(task.getTaskID()).append("\n");
			}
		
			logger.log(log_level, logMessage.toString());
		}
		else
		{
			logger.log(log_level, "Begin of the algorithm "+algorithmName);
		}
	}
	
	/*
	 * On success of the task execution, This method is used to log the information
	 * 
	 */
	public void alogrithmEndLog(Level log_level, String algorithmName, List<Processor> processors)
	{
		if(log_level == Level.FINE || log_level == Level.FINER)
		{
		StringBuilder logMessage = new StringBuilder(" ");
		logMessage.append("Algorithm Name:  ").append(algorithmName).append("\n")
				.append("Num Of Processors: ").append(processors.size()).append("\n")
				.append("scheduled tasks on processors\n");
		int processorSize = 0;
		for(Processor processor: processors)
		{
			logMessage.append("processor: ").append(++processorSize);
			for(Task task: processor.getScheduledTasks())
			logMessage.append("-T").append(task.getTaskID()).append("["+task.getStartTime()+",")
			.append(task.getComputationTime()+","+task.getDeadLine()+"]");
			
			logMessage.append("\n");
		}
		;
		logger.log(log_level, logMessage.toString());
		}
		else
		{
			logger.log(log_level, "Successful -End of  "+algorithmName);
		}
	}
	
	/*
	 * If algorithm fails, the information is logged
	 * 
	 */
	
	public void alogrithmFailLog(Level log_level, String algorithmName, Task task)
	{
		if(log_level == Level.FINE || log_level == Level.FINER)
		{
		StringBuilder logMessage = new StringBuilder(" ");
		logMessage = logMessage.append("Algorithm Name:  ").append(algorithmName).append("\n")
				.append("Task failed to schedule -");
		
		logMessage.append("-T").append(task.getTaskID()).append("["+task.getStartTime()+",")
		.append(task.getComputationTime()+","+task.getDeadLine()+"]\n");
		
		logger.log(log_level, logMessage.toString());
	}
	else
	{
		logger.log(log_level, "Failed algorithm "+algorithmName);
	}
	}
	
	/*
	 * logs the simple text, accept level and msg as string
	 */
	public void simpleText(Level log_level, String msg)
	{
		logger.log(log_level, msg);
	}
	
}
