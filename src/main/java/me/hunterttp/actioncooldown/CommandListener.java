package me.hunterttp.actioncooldown;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.Objects;

public class CommandListener implements CommandExecutor {

    private static final MainClass mainClass = MainClass.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {

            if (args.length < 1){

                sender.sendMessage("ActionCooldown is installed!");

            }else if(Objects.equals(args[0], "notify-chat-on-remaining-uses")) {

                if (args.length == 2 && args[1] != null) {

                    mainClass.getConfig().set("notify-chat-on-remaining-uses", Boolean.parseBoolean(args[1]));
                    mainClass.saveConfig();
                    sender.sendMessage(args[0] + " has been set to " + args[1]);

                } else {

                    sender.sendMessage(args[0] + " is currently set to " + mainClass.getConfig().getBoolean("notify-chat-on-remaining-uses"));

                }

            }else{

                sender.sendMessage("Command not recognized");

            }

        }

        // If the player (or console) uses our command correct, we can return true
        return true;

    }

}
