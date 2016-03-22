package net.dynamicapi.command;

import net.minecraft.command.ICommandSender;

/**
 * Represents a minecraft command sender interpretation by the API.
 */
public interface CommandSender {
    /**
     * Retrieves the sender's name.
     * @return the name
     */
    String getName();

    /**
     * Retrieves the unformatted version of the sender's display name.
     * @return the display name
     */
    String getDisplayName();

    /**
     * Sends a chat message to the sender.
     * @param message the message
     */
    void sendChatMessage(String message);

    /**
     * Gets the handle of the object.
     * @return handle
     */
    ICommandSender getHandle();
}
