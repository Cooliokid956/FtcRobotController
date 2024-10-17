package org.firstinspires.ftc.teamcode.config;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class SuperArmConfig {
    public DcMotor armL;
    public DcMotor armR;

    public void init(HardwareMap hwMap) {
        armL = hwMap.get(DcMotor.class, "armL");
        armL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armL.setTargetPosition(0);
        armL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        armR = hwMap.get(DcMotor.class, "armL");
        armR.setDirection(DcMotor.Direction.REVERSE);
        armR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armR.setTargetPosition(0);
        armR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
}
