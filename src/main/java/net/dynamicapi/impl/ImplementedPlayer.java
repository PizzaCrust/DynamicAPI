package net.dynamicapi.impl;

import net.dynamicapi.entity.EntityPlayer;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.text.TextComponentString;

/**
 * Represents a implemented player.
 */
public class ImplementedPlayer extends ImplementedEntity implements EntityPlayer{
    private final net.minecraft.entity.player.EntityPlayerMP vanilla;

    public ImplementedPlayer(net.minecraft.entity.player.EntityPlayerMP player) {
        super(player);
        this.vanilla = player;
    }

    @Override
    public void addTotalXP(int xp) {
        vanilla.experienceTotal = vanilla.experienceTotal + xp;
    }

    @Override
    public void addXP(int x) {
        vanilla.experience = vanilla.experience + x;
    }

    @Override
    public void addXPLevels(int x) {
        vanilla.experienceLevel = vanilla.experienceLevel + x;
    }

    @Override
    public String getName() {
        return vanilla.getName();
    }

    @Override
    public String getDisplayName() {
        return vanilla.getDisplayName().getUnformattedText();
    }

    @Override
    public void sendChatMessage(String message) {
        vanilla.addChatMessage(new TextComponentString(message));
    }

    @Override
    public ICommandSender getHandle() {
        return vanilla;
    }

    @Override
    public float getFlySpeed() {
        return vanilla.capabilities.getFlySpeed();
    }

    @Override
    public int getFoodLevel() {
        return vanilla.getFoodStats().getFoodLevel();
    }

    @Override
    public String getPlayerListName() {
        return vanilla.getTabListDisplayName().getUnformattedText();
    }

    @Override
    public int getTotalXP() {
        return vanilla.experienceTotal;
    }

    @Override
    public float getXP() {
        return vanilla.experience;
    }

    @Override
    public int getXPLevels() {
        return vanilla.experienceLevel;
    }


    @Override
    public void setTotalXP(int xp) {
        vanilla.experienceTotal = xp;
    }

    @Override
    public void setXP(float x) {
        vanilla.experience = x;
    }

    @Override
    public void setXPLevel(int x) {
        vanilla.experienceLevel = x;
    }
}
