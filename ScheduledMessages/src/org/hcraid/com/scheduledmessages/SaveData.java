package org.hcraid.com.scheduledmessages;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SaveData {

	public static boolean saveFile(SchedulerData data){
	      try
	      	{
	         FileOutputStream fileOut =
	         new FileOutputStream(Main.directory + "Scheduler.ser");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(data);
	         out.close();
	         fileOut.close();
	         Main.log("Succesfully saved 'Scheduler' file.");
	         return true;
	      }catch(IOException i){
	    	  Main.log("An error occured while trying to serialize the object 'Scheduler'.");
	          i.printStackTrace();
	          return false;
	      	}	
	}
	
	public static SchedulerData loadFile(){
		SchedulerData data = null;
		
	      try
	      {
	    	  if(!new File(Main.directory + "Scheduler.ser").exists()){
	    		  Main.log("Creating new Scheduler data.");
	    		  return new SchedulerData(false, 240);
	    	  }
	    	  
	         FileInputStream fileIn = new FileInputStream(Main.directory + "Scheduler.ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         Main.log("Loading file from directory - " + Main.directory + "Scheduler.ser");
	         data = (SchedulerData) in.readObject();
	         in.close();
	         fileIn.close();
	         Main.log("Succesfully loaded 'Scheduler' file.");
	         
	         if(data == null){
	        	 Main.log("Data was null! Creating a new Scheduler object.");
	    		  return new SchedulerData(false, 240);
	         }
	         
	         return data;
	      }catch(Exception i){
	    	  i.printStackTrace();
	         return null;
	      }
	}

}
