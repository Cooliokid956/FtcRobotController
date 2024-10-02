package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.drive.MecanumDrive;

@TeleOp
public class robot2024 extends OpMode {
    MecanumDrive drive = new MecanumDrive();

    @Override
    public void init() {
        drive.config.init(hardwareMap);
    }

    @Override
    public void start() {
        // insert start code here
    }   

    @Override
    public void loop() {
        drive.drive(gamepad1, telemetry);
    }
}
