package net.dynamicapi.command.defaults;

import net.dynamicapi.command.CommandSender;
import net.dynamicapi.command.DynamicCommand;
import net.dynamicapi.util.TextFormatting;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a command that shows API version information.
 */
public class CommandAPIVersion implements DynamicCommand {
    @Override
    public String name() {
        return "apiversion";
    }

    @Override
    public String usage() {
        return "/apiversion";
    }

    @Override
    public List<String> aliases() {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("apiv");
        return arrayList;
    }

    @Override
    public boolean permission(CommandSender sender) {
        return true;
    }

    @Override
    public void execute(CommandSender commandSender, String[] arguments) {
        commandSender.sendChatMessage(TextFormatting.GOLD + "DynamicAPI for 1.9.1-R0.0-SNAPSHOT");
        commandSender.sendChatMessage(TextFormatting.BLUE + "Build Stream: " + TextFormatting.RESET + " STABLE");
        commandSender.sendChatMessage(TextFormatting.RED + "Please report all bugs, if you don't; you will have a buggy version as stable!");
    }
}
