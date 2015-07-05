package org.hcraid.com.scheduledmessages;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SchedulerCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command c, String l,
			String[] args) {
		
		if(c.getName().equalsIgnoreCase("sch")){
			
			if(!s.hasPermission("HcRaid.MOD")){
				s.sendMessage(ChatColor.RED + "You do not have access to this command.");
				return true;
			}
			
			String a1 = "";
			String a2 = "";
			String a3 = "";
			
			if(args.length > 0){
				a1 = args[0];
				if(args.length > 1){
					a2 = args[1];
					if(args.length > 2){
						a3 = args[2];
					}
				}
			}
			
			if(a1 == ""){
				displayOpts(s);
				return true;
			}
			
			if(a1.equalsIgnoreCase("am")){
				
				if(a2 == "" || a3 == ""){
					displayOpts(s);
					return true;
				}
				
				int hours = 0;
				
				try{
					hours = Integer.parseInt(a2);
				}catch(NumberFormatException e){
					s.sendMessage(ChatColor.RED + "Error: Number of hours cannot be a string, number expected.");
					return true;
				}
				
				String msg = a3 + " ";
				
				for(int i = 3; i < args.length; i++){
					msg += args[i] + " ";
				}
				
				if(Main.data == null){
					s.sendMessage("null");
				}
				
				Main.data.addMessage(msg, hours);
				
				s.sendMessage(ChatColor.GREEN + "Added new message to scheduler.");
			
			}else if(a1.equalsIgnoreCase("at")){
				
				if(a2 == "" || a3 == ""){
					displayOpts(s);
					return true;
				}
				
				
				int id = 0;
				
				try{
					id = Integer.parseInt(a2);
				}catch(NumberFormatException e){
					s.sendMessage(ChatColor.RED + "Error: Id cannot be a string, number expected.");
					return true;
				}
				
				String msg = a3 + " ";
				
				for(int i = 3; i < args.length; i++){
					msg += args[i] + " ";
				}
				
				boolean flag = Main.data.addToMessage(id, msg);
				
				if(flag){
					s.sendMessage(ChatColor.GREEN + "Added text to existing message.");
				}else{
					s.sendMessage(ChatColor.RED + "Failed to add text to existing message, message ID was invalid.");
				}
				
			}else if(a1.equalsIgnoreCase("rm")){
				
				if(a2 == ""){
					displayOpts(s);
					return true;
				}
				
				int id = 0;
				
				try{
					id = Integer.parseInt(a2);
				}catch(NumberFormatException e){
					s.sendMessage(ChatColor.RED + "Error: Id cannot be a string, number expected.");
					return true;
				}
				
				boolean found = Main.data.removeMessage(id);
				
				if(found){
					s.sendMessage(ChatColor.GREEN + "Removed message under Id: " + id );
				}else{
					s.sendMessage(ChatColor.RED + "Failed to remove message under Id: " + id + ", perhaps there isn't one of this ID.");
				}
				
			}else if(a1.equalsIgnoreCase("lm")){
				
				if(Main.data.getMessages().isEmpty()){
					s.sendMessage(ChatColor.RED + "There are no messages being displayed.");
					return true;
				}
				
				s.sendMessage(ChatColor.YELLOW + "Start.");
				for(Message m : Main.data.getMessages()){
					s.sendMessage(ChatColor.GRAY + "ID: " + m.getId() + ", " + ChatColor.RESET + "MSG ... ");
					for(String u : m.getMessage()){
						s.sendMessage(" > " + ChatColor.translateAlternateColorCodes('&', u));
					}
					s.sendMessage(ChatColor.GRAY + " Removal Date: " + m.removalDate());
				}
				s.sendMessage(ChatColor.YELLOW + "End.");
				
			}else if(a1.equalsIgnoreCase("random")){
				
				Main.data.toggleRandom();
				
				s.sendMessage(ChatColor.YELLOW + "Random messages enabled: " + Main.data.isRandom());
				
			}else if(a1.equalsIgnoreCase("time")){
				
				if(a2 == ""){
					displayOpts(s);
					return true;
				}
				
				int seconds = 0;
				
				try{
					seconds = Integer.parseInt(a2);
				}catch(NumberFormatException e){
					s.sendMessage(ChatColor.RED + "Error: seconds delay cannot be a string, number expected.");
					return true;
				}
				
				Main.data.setDelay(seconds);
				
				s.sendMessage(ChatColor.GREEN + "Set delay to " + seconds + " seconds.");
				
			}else if(a1.equalsIgnoreCase("reload")){
				
				Main.data.reload();
				
				s.sendMessage(ChatColor.YELLOW + "Reloaded Scheduler.");
				
			}else{
				displayOpts(s);
			}
			
		}
		
		return false;
	}

	private void displayOpts(CommandSender s) {
		
		s.sendMessage(SchedulerData.PREFIX);
		s.sendMessage(ChatColor.GRAY + "/sch am <hours before removal> <message> - Add a message that will be removed after so many hours, specifiy" +
				" 0 if you don't want the message to be removed at all. Here's some figures...");
		s.sendMessage(ChatColor.DARK_GRAY + "1w = 168, 1m = 672, 2w = 336.");
		s.sendMessage(ChatColor.GRAY + "/sch at <id> <msg>- Add to an existing message, will present as another bullet point when annouced.");
		s.sendMessage(ChatColor.GRAY + "/sch rm <id> - Remove the message with this id, to see message ID's do /sch lm");
		s.sendMessage(ChatColor.GRAY + "/sch lm - List all messages on the scheduler currently.");
		s.sendMessage(ChatColor.GRAY + "/sch time <seconds> - Set the seconds between messages.");
		s.sendMessage(ChatColor.GRAY + "/sch random - Toggle whether messages are random. Random Enabled: " + Main.data.isRandom());
		s.sendMessage(ChatColor.GRAY + "/sch reload - Reload scheduler.");
		s.sendMessage(SchedulerData.END_PREFIX);
		
	}

}
