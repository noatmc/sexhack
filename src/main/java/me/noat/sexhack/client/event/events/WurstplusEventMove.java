package me.noat.sexhack.client.event.events;

import me.noat.sexhack.client.event.WurstplusEventCancellable;
import net.minecraft.entity.MoverType;

// External.

public
class WurstplusEventMove extends WurstplusEventCancellable {

    public double x, y, z;
    private MoverType move_type;

    public
    WurstplusEventMove(MoverType type, double x, double y, double z) {
        this.move_type = type;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public
    MoverType get_move_type() {
        return this.move_type;
    }

    public
    void set_move_type(MoverType type) {
        this.move_type = type;
    }

    public
    double get_x() {
        return this.x;
    }

    public
    void set_x(double x) {
        this.x = x;
    }

    public
    double get_y() {
        return this.y;
    }

    public
    void set_y(double y) {
        this.y = y;
    }

    public
    double get_z() {
        return this.z;
    }

    public
    void set_z(double z) {
        this.z = z;
    }
}
