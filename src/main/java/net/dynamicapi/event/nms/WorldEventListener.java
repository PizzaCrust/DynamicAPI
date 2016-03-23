package net.dynamicapi.event.nms;

import net.dynamicapi.event.PlayerJoinEvent;
import net.dynamicapi.event.handle.EventManager;
import net.dynamicapi.impl.ImplementedPlayer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldEventListener;
import net.minecraft.world.World;

/**
 * A NMS event listener to call Dynamic events.
 */
public class WorldEventListener implements IWorldEventListener{
    @Override
    public void notifyBlockUpdate(World world, BlockPos blockPos, IBlockState iBlockState, IBlockState iBlockState1, int i) {

    }

    @Override
    public void notifyLightSet(BlockPos blockPos) {

    }

    @Override
    public void markBlockRangeForRenderUpdate(int i, int i1, int i2, int i3, int i4, int i5) {

    }

    @Override
    public void playSoundToAllNearExcept(EntityPlayer entityPlayer, SoundEvent soundEvent, SoundCategory soundCategory, double v, double v1, double v2, float v3, float v4) {

    }

    @Override
    public void playRecord(SoundEvent soundEvent, BlockPos blockPos) {

    }

    @Override
    public void spawnParticle(int i, boolean b, double v, double v1, double v2, double v3, double v4, double v5, int... ints) {

    }

    @Override
    public void onEntityAdded(Entity entity) {
        if (entity instanceof EntityPlayer) {
            EntityPlayerMP player = (EntityPlayerMP) entity;
            PlayerJoinEvent event = new PlayerJoinEvent(new ImplementedPlayer(player));
            try {
                EventManager.getManager().callEvent(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onEntityRemoved(Entity entity) {

    }

    @Override
    public void broadcastSound(int i, BlockPos blockPos, int i1) {

    }

    @Override
    public void playAuxSFX(EntityPlayer entityPlayer, int i, BlockPos blockPos, int i1) {

    }

    @Override
    public void sendBlockBreakProgress(int i, BlockPos blockPos, int i1) {

    }

    public static void add(World world) {
        world.addEventListener(new WorldEventListener());
    }
}
