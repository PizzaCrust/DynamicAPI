package net.dynamicapi.entity;

import net.dynamicapi.util.Position;

import java.util.UUID;

/**
 * Represents a base entity in the world
 */
public interface Entity {
    boolean eject();

    String getCustomName();

    int getEntityIdentification();

    float getFallDistance();

    Position getPosition();

    int getMaxFireTicks();

    int getTicksLived();

    UUID getUniqueID();

    boolean isDead();

    boolean isCustomNameVisible();

    boolean isOnGround();

    boolean isGlowing();

    boolean isValid();

    void setCustomName(String name);

    void setCustomNameVisible(boolean flag);

    void setFallDistance(float distance);

    void setGlowing(boolean flag);

    void setTicksLived(int value);

    void teleport(Entity destination);

    void teleport(Position position);
}
