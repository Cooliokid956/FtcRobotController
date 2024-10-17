package org.firstinspires.ftc.teamcode.component;

import org.firstinspires.ftc.teamcode.config.SuperArmConfig;

public class SuperArm {
    public SuperArmConfig config = new SuperArmConfig();
    int pos;

    public void move(int u) {
        pos += u;
    }

    public void update() {
        config.armL.setTargetPosition(pos);
        config.armR.setTargetPosition(pos);
    }
}
