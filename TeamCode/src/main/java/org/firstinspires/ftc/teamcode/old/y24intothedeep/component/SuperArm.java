package org.firstinspires.ftc.teamcode.old.y24intothedeep.component;

import androidx.core.math.MathUtils;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.old.y24intothedeep.config.SuperArmConfig;

public class SuperArm {
    public SuperArmConfig config = new SuperArmConfig();
    public int armPos;
    boolean armStopped;
    int armMin = 0;
    int armMax = 420;


    public void moveArm(int u) {
        if (u == 0 && !armStopped) {
            armPos = config.arm.getCurrentPosition();
        }
        armStopped = u == 0;
        armPos += u;
    }

    public int slidePos;
    boolean slideStopped;
    int slideMin = 0;
    int slideMax = 1850;

    public void moveSlide(int u) {
        if (u == 0 && !slideStopped) {
            slidePos = config.slide.getCurrentPosition();
        }
        slideStopped = u == 0;
        slidePos += u;
    }

    public void update(Gamepad gp) {
//        armPos = MathUtils.clamp(armPos, armMin, armMax);
        config.arm.setTargetPosition(armPos);

        slidePos = MathUtils.clamp(slidePos, slideMin, slideMax);
        config.slide.setTargetPosition(slidePos);
        // temp
//        config.slide.setPower(gp.right_trigger-gp.left_trigger);
    }
}
