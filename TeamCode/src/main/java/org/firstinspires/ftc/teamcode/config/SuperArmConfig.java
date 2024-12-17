package org.firstinspires.ftc.teamcode.config;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class SuperArmConfig {
    public DcMotor armL;
    public DcMotor armR;
    public DcMotor slide;

    public void init(HardwareMap hwMap) {
        armL = hwMap.get(DcMotor.class, "armL");
        armL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armL.setTargetPosition(0);
        armL.setPower(.5);
        armL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        armR = hwMap.get(DcMotor.class, "armR");
        armR.setDirection(DcMotor.Direction.REVERSE);
        armR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armR.setTargetPosition(0);
        armR.setPower(.5);
        armR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        slide = hwMap.get(DcMotor.class, "slide");
        slide.setDirection(DcMotor.Direction.REVERSE);
//        slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        slide.setTargetPosition(0);
//        slide.setPower(1);
//        slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // temp
        slide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        slide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
}
