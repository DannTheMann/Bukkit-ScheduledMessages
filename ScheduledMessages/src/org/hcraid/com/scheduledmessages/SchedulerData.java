package org.hcraid.com.scheduledmessages;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class SchedulerData implements Serializable{

	private static final long serialVersionUID = 6344662197369527542L;
	public static final String PREFIX = ChatColor.YELLOW + "╓──────────────"
			+ ChatColor.AQUA +"Annoucement"
			+ ChatColor.YELLOW + "────────────╖";
	public static final String END_PREFIX = ChatColor.YELLOW + "╚─────────────────────────────────╜";
	
	@SuppressWarnings("unused")
	private transient BukkitTask scheduler;
	private ArrayList<Message> messages;
	private boolean random;
	private int secondDelay;
	private int position;
	
	public SchedulerData(boolean rand, int delay){
		this.random = rand;
		this.secondDelay = delay;
		messages = new ArrayList<Message>();
		
		beginTask();
	}

	private Message getRandomMessage(){
		return messages.get(new Random().nextInt(messages.size()));
	}
	
	public void beginTask(){
		
		scheduler = new BukkitRunnable() {
			
			@Override
			public void run() {
				
				messageAll();
				
			}
		}.runTaskTimer(Main.m, secondDelay * 20, secondDelay * 20);
		
	}

	protected void messageAll() {
		
		if(messages.isEmpty()){
			Main.log("No messages to print!");
			return;
		}
		
		Message m = null;

		if (position >= messages.size() && !random) {
			position = 0;

			m = messages.get(position);

			position++;

		} else {
			m = getRandomMessage();
		}

			Bukkit.broadcastMessage(PREFIX);
			for(String u : m.getMessage()){
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
						"     ➔ " + u));
			}
			Bukkit.broadcastMessage(END_PREFIX);
			
			if(m.shouldRemove()){
				removeMessage(m.getId());
			}
		
		
	}

	public void addMessage(String msg, int hours) {
		
		messages.add(new Message(nextId(), msg, hours));
		
	}
	
	private int nextId() {
		
		boolean flag = false;
		
		for(int i = 0; i < messages.size(); i++){
		
			for(Message m : messages){
				
				if(m.getId() == i){
					flag = true;
				}
				
			}
			
			if(flag){
				continue;
			}else{
				return i;
			}
			
		}
		
		return messages.size();
	}

	public boolean removeMessage(int id){
		
		Message canDo =null;
		
		for(Message m : messages){
			if(m.getId() == id){
				canDo = m;
				break;
			}
		}

		if(canDo != null){		
			Main.m.addRemovedMessageToFile(canDo.getDetails());
		}
		
		messages.remove(canDo);
		
		return canDo != null;
		
	}
	
	public ArrayList<Message> getMessages(){
		return messages;
	}

	public void toggleRandom() {
		random = !random;
	}
	
	public boolean isRandom(){
		return random;
	}

	public void setDelay(int seconds) {
		
		if(seconds <= 0 || seconds >= 900){
			seconds = 240;
		}
		
		this.secondDelay = seconds;
	}

	public int getDelay() {
		return secondDelay;
	}

	public void reload() {
		scheduler.cancel();
		
		beginTask();
	}

	public boolean addToMessage(int id, String msg) {
		
		Message canDo =null;
		
		for(Message m : messages){
			if(m.getId() == id){
				canDo = m;
				break;
			}
		}
		
		if(canDo != null){
			canDo.getMessage().add(msg);
		}
		
		return canDo != null;
		
	}
	
}
