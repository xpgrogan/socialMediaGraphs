/*
  Xavier Grogan
  CSCI 433
  Assignment 3
  Due: 3-28-16
  
  In keeping with School of Engineering honor code, I hereby state, that this is my own work
  and I did not plagiarize.
  
  Compiled with Eclipse
*/

import java.io.FileNotFoundException;
import java.util.*;


public class Main 
{
	public static void main(String[] args) throws FileNotFoundException
	{
		Scanner scan = new Scanner(System.in);
		String filename;
		String fileAnswer;
		int runCount = 0;
		Boolean filePresent = true;
		Graph g = new Graph();
		
		while(filePresent)
		{
			System.out.println("Please enter the file name(including path).");
			if(runCount != 0)
				scan.nextLine();
			filename = scan.nextLine();
			
			g.readGraph(filename);
			
			System.out.println("Do you have another file? Enter y for yes or n for no.");
			fileAnswer = scan.next();
			if(fileAnswer.equals("y"))
				filePresent = true;
			else
				filePresent = false;
			runCount++;
		}
	}
}
