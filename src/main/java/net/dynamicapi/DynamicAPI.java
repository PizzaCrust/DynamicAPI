package net.dynamicapi;

import net.blockframe.internal.Injection;
import net.dynamicapi.command.CommandSender;
import net.dynamicapi.command.DynamicCommand;
import net.dynamicapi.command.defaults.CommandAPIVersion;
import net.dynamicapi.command.defaults.CommandAbout;
import net.dynamicapi.command.defaults.CommandHelp;
import net.minecraft.command.AbstractCommand;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandHandler;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.StringTextComponent;
import net.minecraft.util.BlockPosition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * The entry point from BlockFramework to DynamicAPI.
 */
public class DynamicAPI {
    /**
     * The DynamicAPI logger
     */
    public static final Logger LOGGER = LogManager.getLogger("DynamicAPI");

    public static void registerCommand(final DynamicCommand command) {
        AbstractCommand mcCommand = new AbstractCommand() {
            @Override
            public String getCommandName() {
                return command.name();
            }

            @Override
            public String getCommandUsage(ICommandSender iCommandSender) {
                return command.usage();
            }

            @Override
            public List<String> getCommandAliases() {
                return command.aliases();
            }

            @Override
            public void onExecution(MinecraftServer minecraftServer, final ICommandSender iCommandSender, String[] strings) throws CommandException {
                CommandSender sender = new CommandSender() {
                    @Override
                    public String getName() {
                        return iCommandSender.getName();
                    }

                    @Override
                    public String getDisplayName() {
                        return iCommandSender.getDisplayName().getUnformattedText();
                    }

                    @Override
                    public void sendChatMessage(String message) {
                        iCommandSender.sendTextComponent(new StringTextComponent(message));
                    }

                    @Override
                    public ICommandSender getHandle() {
                        return iCommandSender;
                    }
                };
                command.execute(sender, strings);
            }

            @Override
            public boolean isSenderPermitted(MinecraftServer minecraftServer, final ICommandSender iCommandSender) {
                CommandSender sender = new CommandSender() {
                    @Override
                    public String getName() {
                        return iCommandSender.getName();
                    }

                    @Override
                    public String getDisplayName() {
                        return iCommandSender.getDisplayName().getUnformattedText();
                    }

                    @Override
                    public void sendChatMessage(String message) {
                        iCommandSender.sendTextComponent(new StringTextComponent(message));
                    }

                    @Override
                    public ICommandSender getHandle() {
                        return iCommandSender;
                    }
                };
                return command.permission(sender);
            }

            @Override
            public List<String> getTabCompletetionOptions(MinecraftServer minecraftServer, ICommandSender iCommandSender, String[] strings, BlockPosition blockPosition) {
                return null;
            }

            @Override
            public boolean isUsernameIndex(String[] strings, int i) {
                return false;
            }

            @Override
            public int compareTo(AbstractCommand o) {
                return 0;
            }
        };
        MinecraftServer minecraftServer = Injection.server;
        CommandHandler handler = (CommandHandler) minecraftServer.N();
        handler.registerCommand(mcCommand);
    }

    public static void blockFrame() {
        LOGGER.info("[DynamicAPI] Registering internal assets...");
        DynamicAPI.registerCommand(new CommandAPIVersion());
        DynamicAPI.registerCommand(new CommandAbout());
        DynamicAPI.registerCommand(new CommandHelp());
    }
}
