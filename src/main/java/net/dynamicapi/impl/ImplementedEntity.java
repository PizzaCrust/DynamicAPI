package net.dynamicapi.impl;

import net.dynamicapi.entity.Entity;
import net.dynamicapi.util.Position;

import java.util.UUID;

/**
 * Represents a implemented entity.
 */
public class ImplementedEntity implements Entity {
    private net.minecraft.entity.Entity vanillaEntity;

    public ImplementedEntity(net.minecraft.entity.Entity vanillaEntity) {
        this.vanillaEntity = vanillaEntity;
    }

    @Override
    public boolean eject() {
        if (!vanillaEntity.isBeingRidden()) {
            return false;
        }
        if (!vanillaEntity.isRiding()) {
            return false;
        }
        vanillaEntity.dismountRidingEntity();
        return true;
    }

    @Override
    public String getCustomName() {
        return vanillaEntity.getCustomNameTag();
    }

    @Override
    public int getEntityIdentification() {
        return vanillaEntity.getEntityId();
    }

    @Override
    public float getFallDistance() {
        return vanillaEntity.fallDistance;
    }

    @Override
    public Position getPosition() {
        return new Position(vanillaEntity.getPosition().getX(), vanillaEntity.getPosition().getY(), vanillaEntity.getPosition().getZ());
    }

    @Override
    public int getMaxFireTicks() {
        return vanillaEntity.fireResistance;
    }

    @Override
    public int getTicksLived() {
        return vanillaEntity.ticksExisted;
    }

    @Override
    public UUID getUniqueID() {
        return vanillaEntity.getUniqueID();
    }

    @Override
    public boolean isDead() {
        return vanillaEntity.isDead;
    }

    @Override
    public boolean isCustomNameVisible() {
        return vanillaEntity.getAlwaysRenderNameTag();
    }

    @Override
    public boolean isOnGround() {
        return vanillaEntity.onGround;
    }

    @Override
    public boolean isGlowing() {
        return vanillaEntity.isGlowing();
    }

    @Override
    public boolean isValid() {
        return vanillaEntity.isEntityAlive();
    }

    @Override
    public void setCustomName(String name) {
        vanillaEntity.setCustomNameTag(name);
    }

    @Override
    public void setCustomNameVisible(boolean flag) {
        vanillaEntity.setAlwaysRenderNameTag(flag);
    }

    @Override
    public void setFallDistance(float distance) {
        vanillaEntity.fallDistance = distance;
    }

    @Override
    public void setGlowing(boolean flag) {
        vanillaEntity.setGlowing(flag);
    }

    @Override
    public void setTicksLived(int value) {
        vanillaEntity.ticksExisted = value;
    }

    @Override
    public void teleport(Entity destination) {
        Position position = destination.getPosition();
        teleport(position);
    }

    @Override
    public void teleport(Position position) {
        double x = position.getX();
        double y = position.getY();
        double z = position.getZ();
        vanillaEntity.setPosition(x, y, z);
    }
}
