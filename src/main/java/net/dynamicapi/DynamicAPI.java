package net.dynamicapi;

import javassist.CtClass;
import javassist.CtMethod;
import net.blockframe.BlockFramework;
import net.blockframe.internal.Injection;
import net.blockframe.map.MappingsRegistry;
import net.dynamicapi.command.CommandSender;
import net.dynamicapi.command.DynamicCommand;
import net.dynamicapi.command.defaults.CommandAPIVersion;
import net.dynamicapi.command.defaults.CommandAbout;
import net.dynamicapi.command.defaults.CommandHelp;
import net.dynamicapi.entity.EntityPlayer;
import net.dynamicapi.event.handle.EventManager;
import net.dynamicapi.event.nms.WorldEventListener;
import net.dynamicapi.impl.ImplementedPlayer;
import net.dynamicapi.plugin.PluginLoader;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandHandler;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The entry point from BlockFramework to DynamicAPI.
 */
public class DynamicAPI {
    /**
     * The DynamicAPI logger
     */
    public static final Logger LOGGER = LogManager.getLogger("DynamicAPI");

    public static void registerCommand(final DynamicCommand command) {
        ICommand mcCommand = new ICommand() {
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
            public void execute(MinecraftServer minecraftServer, final ICommandSender iCommandSender, String[] strings) throws CommandException {
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
                        iCommandSender.addChatMessage(new TextComponentString(message));
                    }

                    @Override
                    public ICommandSender getHandle() {
                        return iCommandSender;
                    }
                };
                command.execute(sender, strings);
            }

            @Override
            public boolean checkPermission(MinecraftServer minecraftServer, final ICommandSender iCommandSender) {
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
                        iCommandSender.addChatMessage(new TextComponentString(message));
                    }

                    @Override
                    public ICommandSender getHandle() {
                        return iCommandSender;
                    }
                };
                return command.permission(sender);
            }

            @Override
            public List<String> getTabCompletionOptions(MinecraftServer minecraftServer, ICommandSender iCommandSender, String[] strings, BlockPos blockPos) {
                return null;
            }

            @Override
            public boolean isUsernameIndex(String[] strings, int i) {
                return false;
            }

            @Override
            public int compareTo(ICommand o) {
                return 0;
            }
        };
        MinecraftServer minecraftServer = Injection.server;
        CommandHandler handler = (CommandHandler) minecraftServer.getCommandManager();
        handler.registerCommand(mcCommand);
    }

    public static void blockFrame() {
        LOGGER.info("[DynamicAPI] Patching SpecialSource errors...");
        try {
            CtClass playerImplementation = BlockFramework.classPath.getCtClass("net.dynamicapi.impl.ImplementedPlayer");
            for (CtMethod method : playerImplementation.getDeclaredMethods()) {
                String getNameObfName = "h_";
                String getDisplayObfName = "i_";
                String getUnformattedTextObfName = "c";
                if (method.getName().equals("getName")) {
                    method.setBody("return this.vanilla." + getNameObfName + "();");
                }
                if (method.getName().equals("getDisplayName")) {
                    method.setBody("return this.vanilla." + getDisplayObfName + "()." + getUnformattedTextObfName + "();");
                }
            }
            CtClass worldEventListener = BlockFramework.classPath.getCtClass("net.dynamicapi.event.nms.WorldEventListener");
            for (CtMethod method : worldEventListener.getDeclaredMethods()) {
                String addListener = "a";
                if (method.getName().equals("add")) {
                    method.setBody("$1." + addListener + "(new net.dynamicapi.event.nms.WorldEventListener());");
                }
            }
            playerImplementation.toClass();
            worldEventListener.toClass();
        } catch (Exception e) {
            LOGGER.error("[DynamicAPI] Failed to patch SpecialSource errors, API may not work correctly!");
            e.printStackTrace();
        }
        LOGGER.info("[DynamicAPI] Registering internal assets...");
        DynamicAPI.registerCommand(new CommandAPIVersion());
        DynamicAPI.registerCommand(new CommandAbout());
        DynamicAPI.registerCommand(new CommandHelp());
        for (WorldServer server : Injection.server.worldServers) {
            WorldEventListener.add(server);
        }
        LOGGER.info("[DynamicAPI] Starting event manager...");
        EventManager.init();
        LOGGER.info("[DynamicAPI] Searching for dynamic plugins...");
        File[] dynamicFiles = Injection.PLUGINS_DIR.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".dynamic");
            }
        });
        LOGGER.info("[DynamicAPI] Detected " + dynamicFiles.length + " dynamic plugins.");
        for (File file : dynamicFiles) {
            LOGGER.info("Loading file " + file.getName() + "...");
            try {
                PluginLoader.loadFile(file);
            } catch (Exception e) {
                LOGGER.error("Failed to load file " + file.getName());
                e.printStackTrace();
            }
        }
        LOGGER.info("[DynamicAPI] DynamicAPI has finished loading all dynamic plugins.");
    }

    public static EntityPlayer getPlayerFromName(String name) {
        return new ImplementedPlayer(Injection.server.getPlayerList().getPlayerByUsername(name));
    }

    public static EntityPlayer getPlayerFromUUID(UUID uuid) {
        return new ImplementedPlayer(Injection.server.getPlayerList().getPlayerByUUID(uuid));
    }

    public static EntityPlayer[] getOnlinePlayers() {
        ArrayList<EntityPlayer> players = new ArrayList<>();
        String[] onlineUsernames = Injection.server.getPlayerList().getAllUsernames();
        for (String username : onlineUsernames) {
            players.add(DynamicAPI.getPlayerFromName(username));
        }
        return players.toArray(new EntityPlayer[players.size()]);
    }
}
