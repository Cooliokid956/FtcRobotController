package org.firstinspires.ftc.teamcode.config;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MecanumConfig {
    public DcMotor flMotor, frMotor, blMotor, brMotor;

    public void init(HardwareMap hwMap) {
        flMotor = hwMap.get(DcMotor.class, "flMotor");
        frMotor = hwMap.get(DcMotor.class, "frMotor");
        blMotor = hwMap.get(DcMotor.class, "blMotor");
        brMotor = hwMap.get(DcMotor.class, "brMotor");
        flMotor.setDirection(DcMotorSimple.Direction.REVERSE); // Comment this out for Their robot
        blMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

//    public void driveInit(double magnitude) {
//        double lrPower = gamepad1.left_stick_x;
//        double fwdPower = gamepad1.left_stick_y;
//        double turn = gamepad1.right_stick_x;
//        double max = Math.max(fwdPower + turn, fwdPower - turn);
//        lMotor.setPower((fwdPower + turn) / max * magnitude);
//        rMotor.setPower((fwdPower - turn) / max * magnitude);
//        cMotor.setPower((lrPower) * magnitude);
//
//
//    }
}
