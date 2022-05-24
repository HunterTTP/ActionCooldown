package me.hunterttp.actioncooldown;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class CommandListener implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {

            if(Arrays.asList(args).contains("test")){

                sender.sendMessage("Test!");

            }

        }

        // If the player (or console) uses our command correct, we can return true
        return true;

    }

}
