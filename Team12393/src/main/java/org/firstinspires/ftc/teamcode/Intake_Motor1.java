package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake_Motor1 extends OpMode implements Intake_one {
    private DcMotorEx Main_Hand;
    private DcMotorEx Part_Hand;
    private Servo Switch_1;

    public void init(HardwareMap hwMap) {
        Main_Hand = hwMap.get(DcMotorEx.class,"MainM");
        Part_Hand = hwMap.get(DcMotorEx.class,"PartM");
        Switch_1 = hwMap.get(Servo.class,"Ms");
    }

    @Override
    public void init() {
        Part_Hand = hardwareMap.get(DcMotorEx.class,"MainM");
        Switch_1 = hardwareMap.get(Servo.class,"Ms");
    }

    @Override
    public void loop() {
        telemetry.addData("Part_Hand.getCurrentPosition())",Part_Hand.getVelocity());
        if (gamepad1.a)
        {
            Main_Hand.setPower(1);
        }
        else
        {
            Main_Hand.setPower(0);
        }
        if(gamepad1.b)
        {
            Switch_1.setDirection(Servo.Direction.FORWARD);        }
        else if(gamepad1.x){
        Switch_1.setDirection(Servo.Direction.REVERSE);}
        else{

        }
    }
}
