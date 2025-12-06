package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.motors.NeveRest20Gearmotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp

public class RunMotor  extends OpMode {
    private DcMotorEx aMotor;
    private Servo servo;
    private int targetPosition;

    @Override
    public void init() {
        aMotor = hardwareMap.get(DcMotorEx.class,"Motor");
        servo = hardwareMap.get(Servo.class,"Servo");
        servo.scaleRange(0.0,1.0);
        aMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        aMotor.setTargetPosition(0);
        aMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);



    }




    @Override
    public void loop() {
        aMotor.setPower(.2);
        aMotor.setTargetPosition(targetPosition);
        if(gamepad1.dpad_up)
        {
            aMotor.setPower(1);
            targetPosition = 930;

        } else if (gamepad1.dpad_left) {
            targetPosition = 0;
        } else if (gamepad1.dpad_down) {
            targetPosition--;
        }
        if(gamepad1.a)
        {
            servo.setPosition(1);
        }
        else if(gamepad1.b)
        {
            servo.setPosition(0);
        }
        else
        {
            servo.setPosition(0.5);
        }
        telemetry.addData("Position", aMotor.getTargetPosition());
        telemetry.addData("Current", aMotor.getCurrentPosition());
        telemetry.addData("speed", aMotor.getVelocity());
    }
}