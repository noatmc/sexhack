package me.noat.sexhack.client.hacks.render;

import me.noat.sexhack.client.event.events.WurstplusEventRender;
import me.noat.sexhack.client.event.events.WurstplusEventRenderEntityModel;
import me.noat.sexhack.client.guiscreen.settings.Setting;
import me.noat.sexhack.client.hacks.Module;
import me.noat.sexhack.client.hacks.WurstplusCategory;
import me.noat.sexhack.client.util.WurstplusEntityUtil;
import me.noat.sexhack.client.util.WurstplusRenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public
class WurstplusChams extends Module {

    final Setting mode = create("Mode", "ChamsMode", "Outline", combobox("Outline", "Wireframe"));
    final Setting players = create("Players", "ChamsPlayers", true);
    final Setting mobs = create("Mobs", "ChamsMobs", true);
    final Setting self = create("Self", "ChamsSelf", true);
    final Setting items = create("Items", "ChamsItems", true);
    final Setting xporbs = create("Xp Orbs", "ChamsXPO", true);
    final Setting xpbottles = create("Xp Bottles", "ChamsBottles", true);
    final Setting pearl = create("Pearls", "ChamsPearls", true);
    final Setting top = create("Top", "ChamsTop", true);
    final Setting scale = create("Factor", "ChamsFactor", 0, -1f, 1f);
    final Setting r = create("R", "ChamsR", 255, 0, 255);
    final Setting g = create("G", "ChamsG", 255, 0, 255);
    final Setting b = create("B", "ChamsB", 255, 0, 255);
    final Setting a = create("A", "ChamsA", 100, 0, 255);
    final Setting box_a = create("Box A", "ChamsABox", 100, 0, 255);
    final Setting width = create("Width", "ChamsWdith", 2, 0.5, 5);
    final Setting rainbow_mode = create("Rainbow", "ChamsRainbow", false);
    final Setting sat = create("Satiation", "ChamsSatiation", 0.8, 0, 1);
    final Setting brightness = create("Brightness", "ChamsBrightness", 0.8, 0, 1);

    public
    WurstplusChams() {
        super(WurstplusCategory.WURSTPLUS_RENDER);

        this.name = "Chams";
        this.tag = "Chams";
        this.description = "see even less (now with epic colours)";
    }

    @Override
    public
    void update() {
        if (rainbow_mode.getValue(true)) {
            cycle_rainbow();
        }
    }

    public
    void cycle_rainbow() {

        float[] tick_color = {
                (System.currentTimeMillis() % (360 * 32)) / (360f * 32)
        };

        int color_rgb_o = Color.HSBtoRGB(tick_color[0], sat.getValue(1), brightness.getValue(1));

        r.set_value((color_rgb_o >> 16) & 0xFF);
        g.set_value((color_rgb_o >> 8) & 0xFF);
        b.set_value(color_rgb_o & 0xFF);

    }

    @Override
    public
    void render(WurstplusEventRender event) {
        if (items.getValue(true)) {
            int i = 0;
            for (final Entity entity : mc.world.loadedEntityList) {
                if (entity instanceof EntityItem && mc.player.getDistanceSq(entity) < 2500.0) {
                    final Vec3d interp = WurstplusEntityUtil.getInterpolatedRenderPos(entity, mc.getRenderPartialTicks());
                    final AxisAlignedBB bb = new AxisAlignedBB(entity.getEntityBoundingBox().minX - 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().minY - 0.0 - entity.posY + interp.y, entity.getEntityBoundingBox().minZ - 0.05 - entity.posZ + interp.z, entity.getEntityBoundingBox().maxX + 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().maxY + 0.1 - entity.posY + interp.y, entity.getEntityBoundingBox().maxZ + 0.05 - entity.posZ + interp.z);
                    GlStateManager.pushMatrix();
                    GlStateManager.enableBlend();
                    GlStateManager.disableDepth();
                    GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
                    GlStateManager.disableTexture2D();
                    GlStateManager.depthMask(false);
                    GL11.glEnable(2848);
                    GL11.glHint(3154, 4354);
                    GL11.glLineWidth(1.0f);
                    RenderGlobal.renderFilledBox(bb.grow(scale.getValue(1)), r.getValue(1) / 255.0f, g.getValue(1) / 255.0f, b.getValue(1) / 255.0f, box_a.getValue(1) / 255.0f);
                    GL11.glDisable(2848);
                    GlStateManager.depthMask(true);
                    GlStateManager.enableDepth();
                    GlStateManager.enableTexture2D();
                    GlStateManager.disableBlend();
                    GlStateManager.popMatrix();
                    WurstplusRenderUtil.drawBlockOutline(bb.grow(scale.getValue(1)), new Color(r.getValue(1), g.getValue(1), b.getValue(1), a.getValue(1)), 1);
                    if (++i >= 50) {
                        break;
                    }
                }
            }
        }

        if (xporbs.getValue(true)) {
            int i = 0;
            for (final Entity entity : mc.world.loadedEntityList) {
                if (entity instanceof EntityXPOrb && mc.player.getDistanceSq(entity) < 2500.0) {
                    final Vec3d interp = WurstplusEntityUtil.getInterpolatedRenderPos(entity, mc.getRenderPartialTicks());
                    final AxisAlignedBB bb = new AxisAlignedBB(entity.getEntityBoundingBox().minX - 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().minY - 0.0 - entity.posY + interp.y, entity.getEntityBoundingBox().minZ - 0.05 - entity.posZ + interp.z, entity.getEntityBoundingBox().maxX + 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().maxY + 0.1 - entity.posY + interp.y, entity.getEntityBoundingBox().maxZ + 0.05 - entity.posZ + interp.z);
                    GlStateManager.pushMatrix();
                    GlStateManager.enableBlend();
                    GlStateManager.disableDepth();
                    GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
                    GlStateManager.disableTexture2D();
                    GlStateManager.depthMask(false);
                    GL11.glEnable(2848);
                    GL11.glHint(3154, 4354);
                    GL11.glLineWidth(1.0f);
                    RenderGlobal.renderFilledBox(bb.grow(scale.getValue(1)), r.getValue(1) / 255.0f, g.getValue(1) / 255.0f, b.getValue(1) / 255.0f, box_a.getValue(1) / 255.0f);
                    GL11.glDisable(2848);
                    GlStateManager.depthMask(true);
                    GlStateManager.enableDepth();
                    GlStateManager.enableTexture2D();
                    GlStateManager.disableBlend();
                    GlStateManager.popMatrix();
                    WurstplusRenderUtil.drawBlockOutline(bb.grow(scale.getValue(1)), new Color(r.getValue(1), g.getValue(1), b.getValue(1), a.getValue(1)), 1);
                    if (++i >= 50) {
                        break;
                    }
                }
            }
        }

        if (pearl.getValue(true)) {
            int i = 0;
            for (final Entity entity : mc.world.loadedEntityList) {
                if (entity instanceof EntityEnderPearl && mc.player.getDistanceSq(entity) < 2500.0) {
                    final Vec3d interp = WurstplusEntityUtil.getInterpolatedRenderPos(entity, mc.getRenderPartialTicks());
                    final AxisAlignedBB bb = new AxisAlignedBB(entity.getEntityBoundingBox().minX - 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().minY - 0.0 - entity.posY + interp.y, entity.getEntityBoundingBox().minZ - 0.05 - entity.posZ + interp.z, entity.getEntityBoundingBox().maxX + 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().maxY + 0.1 - entity.posY + interp.y, entity.getEntityBoundingBox().maxZ + 0.05 - entity.posZ + interp.z);
                    GlStateManager.pushMatrix();
                    GlStateManager.enableBlend();
                    GlStateManager.disableDepth();
                    GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
                    GlStateManager.disableTexture2D();
                    GlStateManager.depthMask(false);
                    GL11.glEnable(2848);
                    GL11.glHint(3154, 4354);
                    GL11.glLineWidth(1.0f);
                    RenderGlobal.renderFilledBox(bb.grow(scale.getValue(1)), r.getValue(1) / 255.0f, g.getValue(1) / 255.0f, b.getValue(1) / 255.0f, box_a.getValue(1) / 255.0f);
                    GL11.glDisable(2848);
                    GlStateManager.depthMask(true);
                    GlStateManager.enableDepth();
                    GlStateManager.enableTexture2D();
                    GlStateManager.disableBlend();
                    GlStateManager.popMatrix();
                    WurstplusRenderUtil.drawBlockOutline(bb.grow(scale.getValue(1)), new Color(r.getValue(1), g.getValue(1), b.getValue(1), a.getValue(1)), 1);
                    if (++i >= 50) {
                        break;
                    }
                }
            }
        }

        if (xpbottles.getValue(true)) {
            int i = 0;
            for (final Entity entity : mc.world.loadedEntityList) {
                if (entity instanceof EntityExpBottle && mc.player.getDistanceSq(entity) < 2500.0) {
                    final Vec3d interp = WurstplusEntityUtil.getInterpolatedRenderPos(entity, mc.getRenderPartialTicks());
                    final AxisAlignedBB bb = new AxisAlignedBB(entity.getEntityBoundingBox().minX - 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().minY - 0.0 - entity.posY + interp.y, entity.getEntityBoundingBox().minZ - 0.05 - entity.posZ + interp.z, entity.getEntityBoundingBox().maxX + 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().maxY + 0.1 - entity.posY + interp.y, entity.getEntityBoundingBox().maxZ + 0.05 - entity.posZ + interp.z);
                    GlStateManager.pushMatrix();
                    GlStateManager.enableBlend();
                    GlStateManager.disableDepth();
                    GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
                    GlStateManager.disableTexture2D();
                    GlStateManager.depthMask(false);
                    GL11.glEnable(2848);
                    GL11.glHint(3154, 4354);
                    GL11.glLineWidth(1.0f);
                    RenderGlobal.renderFilledBox(bb.grow(scale.getValue(1)), r.getValue(1) / 255.0f, g.getValue(1) / 255.0f, b.getValue(1) / 255.0f, box_a.getValue(1) / 255.0f);
                    GL11.glDisable(2848);
                    GlStateManager.depthMask(true);
                    GlStateManager.enableDepth();
                    GlStateManager.enableTexture2D();
                    GlStateManager.disableBlend();
                    GlStateManager.popMatrix();
                    WurstplusRenderUtil.drawBlockOutline(bb.grow(scale.getValue(1)), new Color(r.getValue(1), g.getValue(1), b.getValue(1), a.getValue(1)), 1);
                    if (++i >= 50) {
                        break;
                    }
                }
            }
        }

    }

    @Override
    public
    void on_render_model(final WurstplusEventRenderEntityModel event) {
        if (event.stage != 0 || event.entity == null || !self.getValue(true) && event.entity.equals(mc.player) || !players.getValue(true) && event.entity instanceof EntityPlayer || !mobs.getValue(true) && event.entity instanceof EntityMob) {
            return;
        }
        final Color color = new Color(r.getValue(1), g.getValue(1), b.getValue(1), a.getValue(1));
        final boolean fancyGraphics = mc.gameSettings.fancyGraphics;
        mc.gameSettings.fancyGraphics = false;
        final float gamma = mc.gameSettings.gammaSetting;
        mc.gameSettings.gammaSetting = 10000.0f;
        if (top.getValue(true)) {
            event.modelBase.render(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
        }
        if (mode.in("outline")) {
            WurstplusRenderUtil.renderOne(width.getValue(1));
            event.modelBase.render(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
            GlStateManager.glLineWidth((float) width.getValue(1));
            WurstplusRenderUtil.renderTwo();
            event.modelBase.render(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
            GlStateManager.glLineWidth((float) width.getValue(1));
            WurstplusRenderUtil.renderThree();
            WurstplusRenderUtil.renderFour(color);
            event.modelBase.render(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
            GlStateManager.glLineWidth((float) width.getValue(1));
            WurstplusRenderUtil.renderFive();
        } else {
            GL11.glPushMatrix();
            GL11.glPushAttrib(1048575);
            GL11.glPolygonMode(1028, 6913);
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glDisable(2929);
            GL11.glEnable(2848);
            GL11.glEnable(3042);
            GlStateManager.blendFunc(770, 771);
            GlStateManager.color((float) color.getRed(), (float) color.getGreen(), (float) color.getBlue(), (float) color.getAlpha());
            GlStateManager.glLineWidth((float) width.getValue(1));
            event.modelBase.render(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
            GL11.glPopAttrib();
            GL11.glPopMatrix();
        }
        if (!top.getValue(true)) {
            event.modelBase.render(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
        }
        try {
            mc.gameSettings.fancyGraphics = fancyGraphics;
            mc.gameSettings.gammaSetting = gamma;
        } catch (Exception ignore) {
        }
        event.cancel();
    }

}
