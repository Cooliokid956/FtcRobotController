package org.firstinspires.ftc.teamcode.component;

import androidx.core.math.MathUtils;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.config.SuperArmConfig;


public class SuperArm {
    public SuperArmConfig config = new SuperArmConfig();
    int armPos;
    int armMin = 0;
    int armMax = 420;

    public void moveArm(int u) {
        armPos += u;
    }

    int slidePos;
    int slideMin = 0;
    int slideMax = 420;

    public void moveSlide(int u) {
        slidePos += u;
    }

    public void update(Gamepad gp) {
//        armPos = MathUtils.clamp(armPos, armMin, armMax);
        config.armL.setTargetPosition(armPos);
        config.armR.setTargetPosition(armPos);

//        slidePos = MathUtils.clamp(slidePos, slideMin, slideMax);
//        config.slide.setTargetPosition(slidePos);
        // temp
        config.slide.setPower(gp.right_trigger-gp.left_trigger);
    }
}
