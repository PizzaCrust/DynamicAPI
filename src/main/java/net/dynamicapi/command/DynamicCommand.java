package net.dynamicapi.command;

import java.util.List;

/**
 * Represents a minecraft command interpretation by the API.
 */
public interface DynamicCommand {
    /**
     * Retrieves the name of the command.
     * @return the name
     */
    String name();

    /**
     * Retrieves the usage of the command.
     * @return the usage
     */
    String usage();

    /**
     * Retrieves the aliases which can be used to execute the command.
     * @return the aliases
     */
    List<String> aliases();

    /**
     * Retrieves if the sender can execute the command.
     * @param sender the sender of the command
     * @return the permission bool
     */
    boolean permission(CommandSender sender);

    /**
     * Executed when a situation of a command is executed.
     * @param commandSender the sender of the command
     * @param arguments the arguments the sender put
     */
    void execute(CommandSender commandSender, String[] arguments);
}
