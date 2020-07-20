package de.jeff_media.AngelChest;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandUnlock implements CommandExecutor {
	
	Main plugin;
	
	public CommandUnlock(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
		Player affectedPlayer = null;
		if(!command.getName().equalsIgnoreCase("unlock")) return false;
		
		if(!sender.hasPermission("angelchest.protect")) {
			sender.sendMessage(plugin.getCommand("unlock").getPermissionMessage());
			return true;
		}

		if(!(sender instanceof Player) && args.length==0) {
			sender.sendMessage(plugin.messages.MSG_PLAYERSONLY);
			return true;
		}

		if(args.length>1 && sender.hasPermission("angelchest.others")) {

			Player p = Bukkit.getPlayer(args[1]);
			if(p==null) {
				sender.sendMessage(ChatColor.RED+"Could not find player "+args[1]);
				return true;
			}

			affectedPlayer = Bukkit.getPlayer(args[1]);
		}
		
		Player p = (Player) sender;
		if(affectedPlayer==null) affectedPlayer=p;
		
		if(args.length > 0) {
			if(args[0].equals("all")) {
				AngelChestCommandUtils.unlockAllChests(plugin, p);
				return true;
			}
		}
		
		AngelChest ac = AngelChestCommandUtils.argIdx2AngelChest(plugin, p, affectedPlayer, args);
		if(ac == null) {
			return true;
		}

		AngelChestCommandUtils.unlockSingleChest(plugin, p, affectedPlayer, ac);
		return true;
	}
}