package net.dynamicapi.command.defaults;

import net.blockframe.internal.Injection;
import net.dynamicapi.command.CommandSender;
import net.dynamicapi.command.DynamicCommand;
import net.dynamicapi.util.TextFormatting;
import net.minecraft.command.ICommand;

import java.util.ArrayList;
import java.util.List;

/**
 * A dynamic help command to fix all glitches with the vanilla help.
 */
public class CommandHelp implements DynamicCommand
{
    @Override
    public String name() {
        return "help";
    }

    @Override
    public String usage() {
        return "/help";
    }

    @Override
    public List<String> aliases() {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("?");
        return arrayList;
    }

    @Override
    public boolean permission(CommandSender sender) {
        return true;
    }

    @Override
    public void execute(CommandSender commandSender, String[] arguments) {
        ICommand[] abstractCommands = Injection.server.getCommandManager().getPossibleCommands(commandSender.getHandle()).toArray(new ICommand[Injection.server.getCommandManager().getPossibleCommands(commandSender.getHandle()).size()]);
        commandSender.sendChatMessage(TextFormatting.GOLD + "Commands available for you:");
        for (ICommand command : abstractCommands) {
            commandSender.sendChatMessage(command.getCommandName() + " -> " + command.getCommandUsage(commandSender.getHandle()));
        }
    }
}
