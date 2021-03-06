package me.noat.sexhack.client.hacks.movement;

import me.noat.sexhack.client.event.events.WurstplusEventMotionUpdate;
import me.noat.sexhack.client.guiscreen.settings.Setting;
import me.noat.sexhack.client.hacks.Module;
import me.noat.sexhack.client.hacks.WurstplusCategory;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;

public
class WurstPlusAnchor extends Module {

    // Written by NathanW, thanks to my friend Ian for some hole shit.

    public static boolean AnchorING;
    final Setting Pitch = create("Pitch", "AnchorPitch", 60, 0, 90);
    final Setting Pull = create("Pull", "AnchorPull", true);
    private final ArrayList <BlockPos> holes = new ArrayList <>();
    int holeblocks;
    @EventHandler
    private final Listener <WurstplusEventMotionUpdate> OnClientTick = new Listener <>(event -> {
        if (mc.player.rotationPitch >= Pitch.getValue(60)) {

            if (isBlockHole(getPlayerPos().down(1)) || isBlockHole(getPlayerPos().down(2)) ||
                    isBlockHole(getPlayerPos().down(3)) || isBlockHole(getPlayerPos().down(4))) {
                AnchorING = true;

                if (!Pull.getValue(true)) {
                    mc.player.motionX = 0.0;
                    mc.player.motionZ = 0.0;
                } else {
                    Vec3d center = GetCenter(mc.player.posX, mc.player.posY, mc.player.posZ);
                    double XDiff = Math.abs(center.x - mc.player.posX);
                    double ZDiff = Math.abs(center.z - mc.player.posZ);

                    if (XDiff <= 0.1 && ZDiff <= 0.1) {
                        center = Vec3d.ZERO;
                    } else {
                        double MotionX = center.x - mc.player.posX;
                        double MotionZ = center.z - mc.player.posZ;

                        mc.player.motionX = MotionX / 2;
                        mc.player.motionZ = MotionZ / 2;
                    }
                }
            } else AnchorING = false;
        }
    });

    public
    WurstPlusAnchor() {
        super(WurstplusCategory.WURSTPLUS_MOVEMENT);

        this.name = "Anchor";
        this.tag = "WurstPlusAnchor";
        this.description = "Stops all movement if player is above a hole";
    }

    public
    boolean isBlockHole(BlockPos blockpos) {
        holeblocks = 0;
        if (mc.world.getBlockState(blockpos.add(0, 3, 0)).getBlock() == Blocks.AIR) ++holeblocks;

        if (mc.world.getBlockState(blockpos.add(0, 2, 0)).getBlock() == Blocks.AIR) ++holeblocks;

        if (mc.world.getBlockState(blockpos.add(0, 1, 0)).getBlock() == Blocks.AIR) ++holeblocks;

        if (mc.world.getBlockState(blockpos.add(0, 0, 0)).getBlock() == Blocks.AIR) ++holeblocks;

        if (mc.world.getBlockState(blockpos.add(0, -1, 0)).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(blockpos.add(0, -1, 0)).getBlock() == Blocks.BEDROCK)
            ++holeblocks;

        if (mc.world.getBlockState(blockpos.add(1, 0, 0)).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(blockpos.add(1, 0, 0)).getBlock() == Blocks.BEDROCK)
            ++holeblocks;

        if (mc.world.getBlockState(blockpos.add(-1, 0, 0)).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(blockpos.add(-1, 0, 0)).getBlock() == Blocks.BEDROCK)
            ++holeblocks;

        if (mc.world.getBlockState(blockpos.add(0, 0, 1)).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(blockpos.add(0, 0, 1)).getBlock() == Blocks.BEDROCK)
            ++holeblocks;

        if (mc.world.getBlockState(blockpos.add(0, 0, -1)).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(blockpos.add(0, 0, -1)).getBlock() == Blocks.BEDROCK)
            ++holeblocks;

        return holeblocks >= 9;
    }

    public
    Vec3d GetCenter(double posX, double posY, double posZ) {
        double x = Math.floor(posX) + 0.5D;
        double y = Math.floor(posY);
        double z = Math.floor(posZ) + 0.5D;

        return new Vec3d(x, y, z);
    }

    public
    void onDisable() {
        AnchorING = false;
        holeblocks = 0;
    }

    public
    BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));
    }

}