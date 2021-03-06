package me.noat.sexhack.client.guiscreen.hud;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.noat.sexhack.SexHack;
import me.noat.sexhack.client.guiscreen.render.pinnables.WurstplusPinnable;
import me.noat.sexhack.client.util.WurstplusFriendUtil;
import net.minecraft.entity.player.EntityPlayer;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

public
class WurstplusPlayerList extends WurstplusPinnable {

    final DecimalFormat df_health = new DecimalFormat("#.#");

    public
    WurstplusPlayerList() {
        super("Player List", "PlayerList", 1, 0, 0);
    }

    public static
    <K, V extends Comparable <? super V>> Map <K, V> sortByValue(Map <K, V> map) {
        List <Map.Entry <K, V>> list =
                new LinkedList <>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());

        Map <K, V> result = new LinkedHashMap <>();
        for (Map.Entry <K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    @Override
    public
    void render() {

        int counter = 12;

        int nl_r = SexHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").getValue(1);
        int nl_g = SexHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").getValue(1);
        int nl_b = SexHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").getValue(1);
        int nl_a = SexHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").getValue(1);

        df_health.setRoundingMode(RoundingMode.HALF_UP);

        List <EntityPlayer> entity_list = mc.world.playerEntities;
        Map <String, Integer> players = new HashMap <>();

        for (EntityPlayer player : entity_list) {

            StringBuilder sb_health = new StringBuilder();

            if (player == mc.player) continue;

            String posString = (player.posY > mc.player.posY ? ChatFormatting.DARK_GREEN + "+" : (player.posY == mc.player.posY ? " " : ChatFormatting.DARK_RED + "-"));

            float hp_raw = player.getHealth() + player.getAbsorptionAmount();
            // this is clever, gj kami
            String hp = df_health.format(hp_raw);
            sb_health.append('\u00A7');
            if (hp_raw >= 20) {
                sb_health.append("a");
            } else if (hp_raw >= 10) {
                sb_health.append("e");
            } else if (hp_raw >= 5) {
                sb_health.append("6");
            } else {
                sb_health.append("c");
            }

            sb_health.append(hp);

            players.put(posString + " " + sb_health + " " + ((WurstplusFriendUtil.isFriend(player.getName()) ? ChatFormatting.GREEN : ChatFormatting.RESET)) + player.getName(), (int) mc.player.getDistance(player));

        }

        if (players.isEmpty()) return;

        players = sortByValue(players);

        int max = SexHack.get_setting_manager().get_setting_with_tag("HUD", "HUDMaxPlayers").getValue(1);
        int count = 0;

        for (Map.Entry <String, Integer> player : players.entrySet()) {

            if (max < count) return;

            count++;
            counter += 12;

            String line = player.getKey() + " " + player.getValue();
            create_line(line, this.docking(1, line), counter, nl_r, nl_g, nl_b, nl_a);
        }

        this.set_width(24);
        this.set_height(counter + 2);

    }

}