package me.noat.sexhack.client.hacks.dev;

import com.mojang.authlib.GameProfile;
import me.noat.sexhack.client.hacks.Module;
import me.noat.sexhack.client.hacks.WurstplusCategory;
import net.minecraft.client.entity.EntityOtherPlayerMP;

import java.util.UUID;

public class WurstplusFakePlayer extends Module {

    private EntityOtherPlayerMP fake_player;

    public WurstplusFakePlayer() {
        super(WurstplusCategory.WURSTPLUS_BETA);

        this.name = "Fake Player";
        this.tag = "FakePlayer";
        this.description = "hahahaaha what a noob its in beta ahahahahaha";
    }

    @Override
    protected void enable() {

        fake_player = new EntityOtherPlayerMP(mc.world, new GameProfile(UUID.fromString("a07208c2-01e5-4eac-a3cf-a5f5ef2a4700"), "travis"));
        fake_player.copyLocationAndAnglesFrom(mc.player);
        fake_player.rotationYawHead = mc.player.rotationYawHead;
        mc.world.addEntityToWorld(-100, fake_player);

    }

    @Override
    protected void disable() {
        try {
            mc.world.removeEntity(fake_player);
        } catch (Exception ignored) {
        }
    }

}