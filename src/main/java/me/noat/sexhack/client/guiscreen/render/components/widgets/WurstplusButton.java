package me.noat.sexhack.client.guiscreen.render.components.widgets;


import me.noat.sexhack.SexHack;
import me.noat.sexhack.client.guiscreen.render.WurstplusDraw;
import me.noat.sexhack.client.guiscreen.render.components.WurstplusAbstractWidget;
import me.noat.sexhack.client.guiscreen.render.components.WurstplusFrame;
import me.noat.sexhack.client.guiscreen.render.components.WurstplusModuleButton;
import me.noat.sexhack.client.guiscreen.settings.Setting;
import me.noat.sexhack.client.util.WurstplusRenderUtil;

import java.awt.*;


public
class WurstplusButton extends WurstplusAbstractWidget {
    private final WurstplusFrame frame;
    private final WurstplusModuleButton master;
    private final Setting setting;

    private final String button_name;
    private final int border_size = 0;
    private int x;
    private int y;
    private int width;
    private int height;
    private int save_y;
    private boolean can;

    public
    WurstplusButton(WurstplusFrame frame, WurstplusModuleButton master, String tag, int update_postion) {
        this.frame = frame;
        this.master = master;
        this.setting = SexHack.get_setting_manager().get_setting_with_tag(master.get_module(), tag);

        this.x = master.get_x();
        this.y = update_postion;

        this.save_y = this.y;

        this.width = master.get_width();
        WurstplusDraw font = new WurstplusDraw(1);
        this.height = font.get_string_height();

        this.button_name = this.setting.get_name();

        this.can = true;
    }

    public
    Setting get_setting() {
        return this.setting;
    }

    @Override
    public
    void does_can(boolean value) {
        this.can = value;
    }

    @Override
    public
    int get_x() {
        return this.x;
    }

    @Override
    public
    void set_x(int x) {
        this.x = x;
    }

    @Override
    public
    int get_y() {
        return this.y;
    }

    @Override
    public
    void set_y(int y) {
        this.y = y;
    }

    @Override
    public
    int get_width() {
        return this.width;
    }

    @Override
    public
    void set_width(int width) {
        this.width = width;
    }

    @Override
    public
    int get_height() {
        return this.height;
    }

    @Override
    public
    void set_height(int height) {
        this.height = height;
    }

    public
    int get_save_y() {
        return this.save_y;
    }

    @Override
    public
    boolean motion_pass(int mx, int my) {
        return motion(mx, my);
    }

    public
    boolean motion(int mx, int my) {
        return mx >= get_x() && my >= get_save_y() && mx <= get_x() + get_width() && my <= get_save_y() + get_height();
    }

    public
    boolean can() {
        return this.can;
    }

    @Override
    public
    void mouse(int mx, int my, int mouse) {
        if (mouse == 0) {
            if (motion(mx, my) && this.master.is_open() && can()) {
                this.frame.does_can(false);

                this.setting.set_value(!(this.setting.getValue(true)));
            }
        }
    }

    @Override
    public
    void render(int master_y, int separe, int absolute_x, int absolute_y) {
        set_width(this.master.get_width() - separe);

        this.save_y = this.y + master_y;

        int ns_r = SexHack.click_gui.theme_widget_name_r;
        int ns_g = SexHack.click_gui.theme_widget_name_g;
        int ns_b = SexHack.click_gui.theme_widget_name_b;
        int ns_a = SexHack.click_gui.theme_widget_name_a;

        int bg_r = SexHack.click_gui.theme_widget_background_r;
        int bg_g = SexHack.click_gui.theme_widget_background_g;
        int bg_b = SexHack.click_gui.theme_widget_background_b;
        int bg_a = SexHack.click_gui.theme_widget_background_a;

        int bd_r = SexHack.click_gui.theme_widget_border_r;
        int bd_g = SexHack.click_gui.theme_widget_border_g;
        int bd_b = SexHack.click_gui.theme_widget_border_b;

        if (this.setting.getValue(true)) { // filling in the button box if button is enabledw
            WurstplusRenderUtil.drawRectangleCorrectly(this.get_x() + this.get_width() - 10, this.save_y, 8, 8, new Color(0, 0, 0, 255).hashCode());
            WurstplusRenderUtil.drawRectangleCorrectly(this.get_x() + this.get_width() - 9, this.save_y + 1, 6, 6, new Color(0, 255, 0, 255).hashCode());
        } else {
            WurstplusRenderUtil.drawRectangleCorrectly(this.get_x() + this.get_width() - 10, this.save_y, 8, 8, new Color(0, 0, 0, 255).hashCode());
            WurstplusRenderUtil.drawRectangleCorrectly(this.get_x() + this.get_width() - 9, this.save_y + 1, 6, 6, new Color(46, 46, 46, 255).hashCode());
        }
        WurstplusDraw.draw_string(this.button_name, this.x + 2, this.save_y, ns_r, ns_g, ns_b, ns_a);
    }
}