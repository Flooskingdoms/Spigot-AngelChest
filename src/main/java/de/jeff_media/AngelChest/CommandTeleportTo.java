package de.jeff_media.AngelChest;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandTeleportTo implements CommandExecutor {

	final Main plugin;

	public CommandTeleportTo(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String alias, String[] args) {
		Player affectedPlayer = null;
		if(!command.getName().equalsIgnoreCase("actp")) return false;

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

		if(!sender.hasPermission("angelchest.tp")) {
			sender.sendMessage(plugin.getCommand("actp").getPermissionMessage());
			return true;
		}

        AngelChest ac = AngelChestCommandUtils.argIdx2AngelChest(plugin, p, affectedPlayer, args);
        if(ac == null) {
            return true;
        }

        AngelChestCommandUtils.teleportPlayerToChest(plugin, p, ac, sender, args);

		return true;
	}

}
