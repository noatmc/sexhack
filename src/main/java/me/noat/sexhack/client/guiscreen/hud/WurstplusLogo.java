package me.noat.sexhack.client.guiscreen.hud;

import me.noat.sexhack.client.guiscreen.render.pinnables.WurstplusPinnable;
import me.noat.sexhack.client.util.WurstplusTextureHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public
class WurstplusLogo extends WurstplusPinnable {

    final ResourceLocation r = new ResourceLocation("custom/wurst.png");

    public
    WurstplusLogo() {
        super("Logo", "Logo", 1, 0, 0);
    }

    @Override
    public
    void render() {

        GL11.glPushMatrix();
        GL11.glTranslatef(this.get_x(), this.get_y(), 0.0F);
        WurstplusTextureHelper.drawTexture(r, this.get_x(), this.get_y(), 100, 25);
        GL11.glPopMatrix();

        this.set_width(100);
        this.set_height(25);
    }


}