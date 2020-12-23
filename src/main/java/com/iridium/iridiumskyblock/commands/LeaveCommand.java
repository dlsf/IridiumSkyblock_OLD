package com.iridium.iridiumskyblock.commands;

import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.Island;
import com.iridium.iridiumskyblock.Role;
import com.iridium.iridiumskyblock.User;
import com.iridium.iridiumskyblock.Utils;
import java.util.Collections;
import java.util.List;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LeaveCommand extends Command {

    public LeaveCommand() {
        super(Collections.singletonList("leave"), "Leave an island", "", true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        User user = User.getUser(p);
        if (user.getIsland() != null) {
            if (user.role.equals(Role.Owner)) {
                sender.sendMessage(Utils.color(IridiumSkyblock.messages.cantLeaveIfOwner.replace("%prefix%", IridiumSkyblock.configuration.prefix)));
            } else {
                user.getIsland().removeUser(user);
                sender.sendMessage(Utils.color(IridiumSkyblock.messages.leftIsland.replace("%prefix%", IridiumSkyblock.configuration.prefix)));
            }
        } else {
            sender.sendMessage(Utils.color(IridiumSkyblock.messages.noIsland.replace("%prefix%", IridiumSkyblock.configuration.prefix)));
        }
    }

    @Override
    public void admin(CommandSender sender, String[] args, Island island) {
        execute(sender, args);
    }

    @Override
    public List<String> TabComplete(CommandSender cs, org.bukkit.command.Command cmd, String s, String[] args) {
        return null;
    }
}
