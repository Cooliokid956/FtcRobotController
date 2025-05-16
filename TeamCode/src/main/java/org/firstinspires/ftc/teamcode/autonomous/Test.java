package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.drive.MecanumDrive;

public class Test extends OpMode {
    MecanumDrive drive = new MecanumDrive();

    Gamepad pad = new Gamepad();
    @Override
    public void init() {
        drive.config.init(hardwareMap);
    }

    @Override
    public void loop() {


        drive.drive(pad, telemetry);
    }
}
