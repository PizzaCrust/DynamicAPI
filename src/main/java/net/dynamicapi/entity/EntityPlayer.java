package net.dynamicapi.entity;

import net.dynamicapi.command.CommandSender;

/**
 * Represents a player on the server
 */
public interface EntityPlayer extends CommandSender, Entity {
    void addTotalXP(int xp);

    void addXP(int x);

    void addXPLevels(int x);

    String getDisplayName();

    float getFlySpeed();

    int getFoodLevel();

    String getPlayerListName();

    int getTotalXP();

    float getXP();

    int getXPLevels();

    void setTotalXP(int xp);

    void setXP(float x);

    void setXPLevel(int x);
}
