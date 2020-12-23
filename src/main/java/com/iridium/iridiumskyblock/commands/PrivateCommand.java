package com.iridium.iridiumskyblock.commands;

import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.Island;
import com.iridium.iridiumskyblock.User;
import com.iridium.iridiumskyblock.Utils;
import java.util.Collections;
import java.util.List;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PrivateCommand extends Command {

    public PrivateCommand() {
        super(Collections.singletonList("private"), "Stop players visiting your island", "", true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        User user = User.getUser(p);
        if (user.getIsland() != null) {
            if (user.bypassing || user.getIsland().getPermissions(user.role).islandprivate) {
                user.getIsland().visit = false;
                int visitorCount = 0;
                for (Player visitor : user.getIsland().getPlayersOnIsland()) {
                    if (user.getIsland().equals(User.getUser(visitor).getIsland()) || User.getUser(visitor).bypassing || user.getIsland().isCoop(User.getUser(visitor).getIsland()) || visitor.hasPermission("iridiumskyblock.visitbypass"))
                        continue;

                    user.getIsland().spawnPlayer(visitor);
                    visitor.sendMessage(Utils.color(IridiumSkyblock.messages.expelledIslandLocked
                            .replace("%prefix%", IridiumSkyblock.configuration.prefix)
                            .replace("%player%", p.getName())));
                    visitorCount++;
                }
                p.sendMessage(Utils.color(IridiumSkyblock.messages.islandNowPrivate
                        .replace("%prefix%", IridiumSkyblock.configuration.prefix)
                        .replace("%amount%", visitorCount + "")));
            } else {
                sender.sendMessage(Utils.color(IridiumSkyblock.messages.noPermission.replace("%prefix%", IridiumSkyblock.configuration.prefix)));
            }
        } else {
            sender.sendMessage(Utils.color(IridiumSkyblock.messages.noIsland.replace("%prefix%", IridiumSkyblock.configuration.prefix)));
        }
    }

    @Override
    public void admin(CommandSender sender, String[] args, Island island) {
        if (island != null) {
            island.visit = false;
            sender.sendMessage(Utils.color(IridiumSkyblock.messages.islandNowPrivate.replace("%prefix%", IridiumSkyblock.configuration.prefix)));
        } else {
            sender.sendMessage(Utils.color(IridiumSkyblock.messages.noIsland.replace("%prefix%", IridiumSkyblock.configuration.prefix)));
        }
    }

    @Override
    public List<String> TabComplete(CommandSender cs, org.bukkit.command.Command cmd, String s, String[] args) {
        return null;
    }
}
