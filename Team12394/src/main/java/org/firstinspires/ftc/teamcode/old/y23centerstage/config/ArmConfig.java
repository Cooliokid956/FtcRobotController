package org.firstinspires.ftc.teamcode.old.y23centerstage.config;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ArmConfig {
    public DcMotor jointBase;
    public Servo jointMid1;
    public Servo jointMid2;
    public Servo clawMini;
    public Servo claw;

    public void init(HardwareMap hwMap) {
        jointBase = hwMap.get(DcMotor.class, "motorA");

//        jointBase.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        jointBase.setTargetPosition(0);
//        jointBase.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        jointBase.setDirection(DcMotorSimple.Direction.REVERSE);
        jointBase.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        jointBase.setTargetPosition(0);
        jointBase.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        jointBase.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        jointMid1 = hwMap.get(Servo.class, "servoB1");
        jointMid2 = hwMap.get(Servo.class, "servoB2");
        jointMid1.setDirection(Servo.Direction.REVERSE);

        clawMini = hwMap.get(Servo.class, "servoClawMini");
        claw = hwMap.get(Servo.class, "servoClaw");
        //claw.scaleRange(.38f,.453f);
    }
}
