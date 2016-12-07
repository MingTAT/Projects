package com.rts.scheduling;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.io.File;

public class TestScheduling {

	public static void main(String[] args) {

		ProjectLogger.getLogger().SetLogLevel(Level.OFF);
		Scheduling scheduling = new Scheduling();
		
		float executionTime_A = 0.0f;
		float executionTime_EDF = 0.0f;
		float executionTime_LLF = 0.0f;
		
		int nSamples =0;
		
		try {
			
			
			File folder = new File(".");
			File[] listOfFiles = folder.listFiles();

			for (int i = 0; i < listOfFiles.length; i++) {
				File file = listOfFiles[i];
				if (file.isFile() && file.getName().endsWith(".txt")) {
					nSamples++;
					
					FileReader filereader = new FileReader(file.getName());

					BufferedReader bf = new BufferedReader(filereader);
					
					//System.out.println(file.getName());
					try {
						scheduling.clear();
						scheduling.setNumberOfProcessors(Integer.parseInt(bf.readLine()));
						int numOfTasks = Integer.parseInt(bf.readLine());

						String line;
						int taskNumber = 0;

						while ((line = bf.readLine()) != null) {
							String[] taskSetTokens = line.split(" ");
							int taskID = taskNumber++;
							if (taskSetTokens.length == 3) {
								int startTime = Integer.parseInt(taskSetTokens[0]); // 0--- start time
								int computationTime = Integer.parseInt(taskSetTokens[1]); // 1--- computaiontime
								int deadLine = Integer.parseInt(taskSetTokens[2]); // 2--- deadLines

								scheduling.addTask(new Task(taskID, startTime, computationTime, deadLine));
							} else {
								int startTime = Integer.parseInt(taskSetTokens[0]); // 0--- starttime
								int computationTime = Integer.parseInt(taskSetTokens[1]); // 1--- computaiontime
								int deadLine = Integer.parseInt(taskSetTokens[2]); // 2---deadLines
								int period = Integer.parseInt(taskSetTokens[3]); // 3--- period

								scheduling.addTask(new Task(taskID, startTime, computationTime, deadLine, period));
							}
						}
						
						boolean bfirst = true;
						for (int nTimes = 0; nTimes < 1000; nTimes++) {
							// run the algorithm
							if(bfirst)
								System.out.println(file.getName());
							
							if (scheduling.run(Scheduling.SCHEDULING_TYPE.ALGO_EDF)) {
								if(bfirst)
								{
								//scheduling.displayScheduledTasks();
								System.out.println("Algorithm EDF is Successful");
								}
							} else {
								if(bfirst)
								System.out.println("Algorithm EDF is not successful");
							}

							executionTime_EDF += scheduling.executedTimeMillis();
							//System.out.println("Executed Time:" + scheduling.executedTimeMillis() + " ms\n");

							// run the algorithm
							if (scheduling.run(Scheduling.SCHEDULING_TYPE.ALGO_LLF)) {
								if(bfirst){
								//scheduling.displayScheduledTasks();
								System.out.println("Algorithm LLF is Successful");
								}
							} else {
								if(bfirst)
								System.out.println("Algorithm LLF is not successful");
							}

							executionTime_LLF += scheduling.executedTimeMillis();
							//System.out.println("Executed Time:" + scheduling.executedTimeMillis() + " ms\n");

							// run the algorithm
							if (scheduling.run(Scheduling.SCHEDULING_TYPE.ALGO_A)) {
								if(bfirst){
								//scheduling.displayScheduledTasks();
								System.out.println("Algorithm A is Successful");
								}
							} else {
								if(bfirst)
								System.out.println("Algorithm A is not successful");
							}

							executionTime_A += scheduling.executedTimeMillis();
							//System.out.println("Executed Time:" + scheduling.executedTimeMillis() + " ms\n");
							
							bfirst = false;

						}

						System.out.println("Algorithm A :" + (executionTime_A / 1000) + " ms");
						System.out.println("Algorithm EDF :" + (executionTime_EDF / 1000) + " ms");
						System.out.println("Algorithm LLF :" + (executionTime_LLF / 1000) + " ms");

						
						if(filereader != null)
							filereader.close();
						if(bf != null)
							bf.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}			
			}
			System.out.println("executionTime_A "+(executionTime_A/(1000*nSamples)));
			System.out.println("executionTime_EDF "+(executionTime_EDF/(1000*nSamples)));
			System.out.println("executionTime_LLF "+(executionTime_LLF/(1000*nSamples)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
