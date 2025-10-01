package org.firstinspires.ftc.teamcode.old.y22powerplay.config;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class LiftConfig {
    public DcMotor lift;

    public void init(HardwareMap hwMap) {
        lift = hwMap.get(DcMotor.class, "liftMotor");
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setTargetPosition(0);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
}
