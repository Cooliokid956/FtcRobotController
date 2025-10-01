package org.firstinspires.ftc.teamcode.old.y22powerplay.config;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class HDriveConfig {
    public DcMotor lMotor, rMotor, cMotor;
    public Servo claw;
    public ColorRangeSensor sensor;

    public void init(HardwareMap hwMap) {
        lMotor = hwMap.get(DcMotor.class, "leftMotor");
        rMotor = hwMap.get(DcMotor.class, "rightMotor");
        cMotor = hwMap.get(DcMotor.class, "middleMotor");
        claw = hwMap.get(Servo.class, "claw");
        sensor = hwMap.get(ColorRangeSensor.class, "imu");
        lMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        cMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        lMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        cMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        cMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        cMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        claw.scaleRange(.415, .48); // closed - open
//        sensor.
        //claw.setDirection(Servo.Direction.REVERSE);
    }

    public void driveInit(double magnitude) {
        double middlePower = gamepad1.left_stick_x;
        double forwardPower = gamepad1.left_stick_y;
        double turn = gamepad1.right_stick_x;
        double max = Math.max(forwardPower + turn, forwardPower - turn);
        lMotor.setPower((forwardPower + turn) / max * magnitude);
        rMotor.setPower((forwardPower - turn) / max * magnitude);
        cMotor.setPower((middlePower) * magnitude);


    }
}
