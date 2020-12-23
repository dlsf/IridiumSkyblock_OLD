package com.iridium.iridiumskyblock.commands;

import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.Island;
import com.iridium.iridiumskyblock.User;
import com.iridium.iridiumskyblock.Utils;
import java.util.Collections;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ResetCrystalsCommand extends Command {

    public ResetCrystalsCommand() {
        super(Collections.singletonList("resetcrystals"), "resets the player's crystals", "iridiumskyblock.resetcrystals", false);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 2) {
            sender.sendMessage("/is resetcrystals <player>");
            return;
        }
        Player player = Bukkit.getPlayer(args[1]);
        if (player != null) {
            Island island = User.getUser(player).getIsland();
            if (island != null) {
                island.crystals = 0;
                sender.sendMessage(Utils.color(IridiumSkyblock.messages.resetCrystals.replace("%crystals%", args[1]).replace("%player%", player.getName()).replace("%prefix%", IridiumSkyblock.configuration.prefix)));
            } else {
                sender.sendMessage(Utils.color(IridiumSkyblock.messages.playerNoIsland.replace("%prefix%", IridiumSkyblock.configuration.prefix)));
            }
        } else {
            sender.sendMessage(Utils.color(IridiumSkyblock.messages.playerOffline.replace("%prefix%", IridiumSkyblock.configuration.prefix)));
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
