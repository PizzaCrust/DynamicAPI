package net.dynamicapi.command.defaults;

import net.dynamicapi.command.CommandSender;
import net.dynamicapi.command.DynamicCommand;
import net.dynamicapi.util.TextFormatting;

import java.util.ArrayList;
import java.util.List;

/**
 * A improved about command for BlockFramework using DynamicAPI.
 */
public class CommandAbout implements DynamicCommand {
    @Override
    public String name() {
        return "about";
    }

    @Override
    public String usage() {
        return "/about";
    }

    @Override
    public List<String> aliases() {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("abt");
        arrayList.add("abut");
        return arrayList;
    }

    @Override
    public boolean permission(CommandSender sender) {
        return true;
    }

    @Override
    public void execute(CommandSender commandSender, String[] arguments) {
        commandSender.sendChatMessage(TextFormatting.BLUE + "This server is running: " + TextFormatting.RED + "BlockFramework 1.9.1-SNAPSHOT");
    }
}
