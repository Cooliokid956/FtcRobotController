package org.firstinspires.ftc.teamcode.old.y22powerplay;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.old.y22powerplay.config.HDriveConfig;

//@TeleOp
public class nAME extends OpMode {
    HDriveConfig robot = new HDriveConfig();

    public void init() {
        robot.init(hardwareMap);
    }

    @Override
    public void loop() {
        robot.lMotor.setPower(0.01);
        robot.rMotor.setPower(0.01);
        robot.cMotor.setPower(0.01);
    }
}