package org.hcraid.com.scheduledmessages;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Message implements Serializable {

	private static final long serialVersionUID = 3411922673107618491L;
	private int id;
	private ArrayList<String> message;
	private long removalDate;
	
	public Message(int id, String message, int hoursUntilRemoval){
		
		this.message = new ArrayList<String>();
		
		if(hoursUntilRemoval <= 0){
			removalDate = 0;
		}else{	
			removalDate = System.currentTimeMillis() + (hoursUntilRemoval * 3600000);
		}
		this.message.add(message);
		this.id = id;
	}
	
	public String toString(int index){
		return message.get(index);
	}
	
	public ArrayList<String> getDetails(){
	
		ArrayList<String> list = new ArrayList<String>();
	
			list.add("Message Contents:");
		for(String u : message){
			list.add(u);
		}
			list.add("Removal Date: " + removalDate());
			return list;
		
	}
	
	
	public ArrayList<String> getMessage(){
		return message;
	}
	
	public int getId(){
		return id;
	}
	
	public boolean shouldRemove(){
		
		if(removalDate == 0){
			return false;
		}else if(System.currentTimeMillis() >= removalDate){
			return true;
		}
		return false;
	}

	public String removalDate(){
		
		if(removalDate <= 0){
			return "Never";
		}
		
		final SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM d, yyyy h:mm a");
		return sdf.format(new Date(removalDate));
	}
	
}
