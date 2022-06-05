package me.hunterttp.actioncooldown;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import static java.lang.Integer.parseInt;

public class CommandListener implements CommandExecutor {

    private static final MainClass mainClass = MainClass.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {

            if (args.length < 1){

                sender.sendMessage("ActionCooldown is installed!");

            }else if(args.length == 1 && mainClass.getConfig().contains(args[0])) {

                sender.sendMessage(args[0] + " is currently set to " + mainClass.getConfig().getString(args[0]));

            }else if(args.length == 2 && args[1] != null && mainClass.getConfig().contains(args[0])) {

                updateConfig(sender, args);

            }else{

                sender.sendMessage("Command not recognized");

            }

        }

        return true;

    }

    public void updateConfig(CommandSender sender, String[] args){

        String setting = args[0];

        if(args[1].equalsIgnoreCase("true") || args[1].equalsIgnoreCase("false")) {

            boolean newValue = Boolean.parseBoolean(args[1]);
            mainClass.getConfig().set(setting, newValue);
            mainClass.saveConfig();
            sender.sendMessage(setting + " has been set to " + newValue);

        }else if(args[1].matches("[0-9]+")){

            int newValue = parseInt(args[1]);
            mainClass.getConfig().set(setting, newValue);
            mainClass.saveConfig();
            sender.sendMessage(setting + " has been set to " + newValue);

        }else{

            //need to update this to handle adding entities to exemptions in config.yml
            String newValue = args[1];
            mainClass.getConfig().set(setting, newValue);
            mainClass.saveConfig();
            sender.sendMessage(setting + " has been set to " + newValue);

        }

    }

}
