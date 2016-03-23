package net.dynamicapi.event;

import net.dynamicapi.entity.EntityPlayer;

/**
 * An event when a player joins a world.
 */
public final class PlayerJoinEvent implements Event {
    private EntityPlayer player;

    public PlayerJoinEvent(EntityPlayer player) {
        this.player = player;
    }

    public EntityPlayer getPlayer() {
        return player;
    }
}
