package org.hcraid.com.scheduledmessages;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{

	public static SchedulerData data;
	
	public static Main m;

	public static String directory;

	public static boolean random;
	public static int delay;
	
	public void onEnable(){
		
		m = this;
		directory = getDataFolder().getAbsolutePath() + File.separator;
		
		File f = new File(directory);
		
		log("Directory: " + directory);
		
		if(!f.exists()){
			f.mkdir();
			log("Directory didn't exist, made it.");
		}else{
			log("Directory exists.");
		}
		
		data = SaveData.loadFile();
		getServer().getPluginCommand("sch").setExecutor(new SchedulerCommand());				
		
		if(data == null){
			data = new SchedulerData(false, 240);
		}
		
		data.beginTask();
		
		log("Scheduler Enabled!");
		log("Total Messages: " + data.getMessages().size());
		log("Seconds Delay Between Message: " + data.getDelay());
		log("Random Messages Enabled: " + data.isRandom());
	}
	
	public void onDisable(){
		SaveData.saveFile(data);
	}

	public static void log(String string) {
		System.out.println("[Scheduler] " + string);
	}
	
	public void addRemovedMessageToFile(ArrayList<String> lines){
		
		File f = new File(directory + "Removed Messages Log.txt");
		
		final SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM d, yyyy h:mm a");
		String date = sdf.format(new Date(System.currentTimeMillis()));
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));
			bw.newLine();
			bw.write("====== " + date + " ======");
			for(String u : lines){
				bw.newLine();
				bw.write(u);
			}
			bw.newLine();
			bw.write("====== " + date + " ======");
			bw.newLine();
			bw.close();
		} catch (IOException e) {
			log("Failed to write out the removed line from the scheduler.");
			e.printStackTrace();
		}
		
	}

}
