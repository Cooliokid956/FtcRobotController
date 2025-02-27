package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.config.ServoConfig;

//@TeleOp
public class ServoOpMode extends OpMode {
    ServoConfig robot = new ServoConfig();
    final double startPosition = 0;

    public void init() {
        robot.init(hardwareMap);
    }

    public void start() {
        robot.setServoPosition(0);
        resetRuntime();
    }

    public void loop() {
        if(getRuntime() > 5.0) {
            robot.setServoPosition(0);
        }
        else if(gamepad1.a) {
            robot.setServoPosition(.5);
        }
        else if(gamepad1.b) {
            robot.setServoPosition(1);
        }
    }
}