package me.noat.sexhack.client.hacks.render;

import me.noat.sexhack.client.event.events.WurstplusEventRender;
import me.noat.sexhack.client.guiscreen.settings.Setting;
import me.noat.sexhack.client.hacks.Module;
import me.noat.sexhack.client.hacks.WurstplusCategory;
import me.noat.sexhack.client.util.WurstplusCrystalUtil;
import me.noat.sexhack.client.util.WurstplusEntityUtil;
import me.noat.sexhack.client.util.WurstplusFriendUtil;
import me.noat.turok.draw.RenderHelp;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

import java.util.HashSet;
import java.util.Set;

public
class WurstplusFuckedDetector extends Module {

    public final Set <BlockPos> fucked_players = new HashSet <>();
    final Setting draw_own = create("Draw Own", "FuckedDrawOwn", false);
    final Setting draw_friends = create("Draw Friends", "FuckedDrawFriends", false);

    final Setting render_mode = create("Render Mode", "FuckedRenderMode", "Pretty", combobox("Pretty", "Solid", "Outline"));
    final Setting r = create("R", "FuckedR", 255, 0, 255);
    final Setting g = create("G", "FuckedG", 255, 0, 255);
    final Setting b = create("B", "FuckedB", 255, 0, 255);
    final Setting a = create("A", "FuckedA", 100, 0, 255);

    private boolean solid;
    private boolean outline;

    public
    WurstplusFuckedDetector() {
        super(WurstplusCategory.WURSTPLUS_RENDER);

        this.name = "Fucked Detector";
        this.tag = "FuckedDetector";
        this.description = "see if people are hecked";
    }

    @Override
    protected
    void enable() {
        fucked_players.clear();
    }

    @Override
    public
    void update() {
        if (mc.world == null) return;
        set_fucked_players();
    }

    public
    void set_fucked_players() {

        fucked_players.clear();

        for (EntityPlayer player : mc.world.playerEntities) {

            if (WurstplusEntityUtil.isLiving(player) || player.getHealth() <= 0) continue;
            // if (!player.onGround) continue;

            if (is_fucked(player)) {

                if (WurstplusFriendUtil.isFriend(player.getName()) && !draw_friends.getValue(true)) continue;
                if (player == mc.player && !draw_own.getValue(true)) continue;

                fucked_players.add(new BlockPos(player.posX, player.posY, player.posZ));

            }

        }

    }

    public
    boolean is_fucked(EntityPlayer player) {

        BlockPos pos = new BlockPos(player.posX, player.posY - 1, player.posZ);

        if (WurstplusCrystalUtil.canPlaceCrystal(pos.south()) || (WurstplusCrystalUtil.canPlaceCrystal(pos.south().south()) && mc.world.getBlockState(pos.add(0, 1, 1)).getBlock() == Blocks.AIR)) {
            return true;
        }

        if (WurstplusCrystalUtil.canPlaceCrystal(pos.east()) || (WurstplusCrystalUtil.canPlaceCrystal(pos.east().east()) && mc.world.getBlockState(pos.add(1, 1, 0)).getBlock() == Blocks.AIR)) {
            return true;
        }

        if (WurstplusCrystalUtil.canPlaceCrystal(pos.west()) || (WurstplusCrystalUtil.canPlaceCrystal(pos.west().west()) && mc.world.getBlockState(pos.add(-1, 1, 0)).getBlock() == Blocks.AIR)) {
            return true;
        }

        return WurstplusCrystalUtil.canPlaceCrystal(pos.north()) || (WurstplusCrystalUtil.canPlaceCrystal(pos.north().north()) && mc.world.getBlockState(pos.add(0, 1, -1)).getBlock() == Blocks.AIR);

    }

    @Override
    public
    void render(WurstplusEventRender event) {

        if (render_mode.in("Pretty")) {
            outline = true;
            solid = true;
        }

        if (render_mode.in("Solid")) {
            outline = false;
            solid = true;
        }

        if (render_mode.in("Outline")) {
            outline = true;
            solid = false;
        }

        for (BlockPos render_block : fucked_players) {

            if (render_block == null) return;

            if (solid) {
                RenderHelp.prepare("quads");
                RenderHelp.draw_cube(RenderHelp.get_buffer_build(),
                        render_block.getX(), render_block.getY(), render_block.getZ(),
                        1, 1, 1,
                        r.getValue(1), g.getValue(1), b.getValue(1), a.getValue(1),
                        "all"
                );
                RenderHelp.release();
            }

            if (outline) {
                RenderHelp.prepare("lines");
                RenderHelp.draw_cube_line(RenderHelp.get_buffer_build(),
                        render_block.getX(), render_block.getY(), render_block.getZ(),
                        1, 1, 1,
                        r.getValue(1), g.getValue(1), b.getValue(1), a.getValue(1),
                        "all"
                );
                RenderHelp.release();
            }

        }

    }

}
