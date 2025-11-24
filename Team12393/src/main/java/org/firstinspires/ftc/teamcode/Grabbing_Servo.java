package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class Grabbing_Servo extends OpMode {
private Servo servo;
    @Override
    public void init() {
        servo = hardwareMap.get(Servo.class, "servo1");
        servo.setPosition(0.0);

    }

    @Override
    public void loop() {
        if (gamepad1.a)
        {
            servo.setPosition(1.5);
        }

    }
}
