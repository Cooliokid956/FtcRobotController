package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.drive.MecanumDrive;

@Autonomous
public class auto2024right extends OpMode {
    MecanumDrive drive = new MecanumDrive();
    Gamepad fakeGamepad = new Gamepad();

    @Override
    public void init() {
        drive.config.init(hardwareMap);
        resetRuntime();
    }

    @Override
    public void loop() {
        fakeGamepad.left_stick_x = (getRuntime() < 2) ? 1.f : 0.f;
        drive.drive(fakeGamepad, telemetry);
    }
}
