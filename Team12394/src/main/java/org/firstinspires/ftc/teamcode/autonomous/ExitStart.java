package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.old.y22powerplay.config.HDriveConfig;

//@Autonomous
public class ExitStart extends OpMode {
    MecanumDrive drive = new MecanumDrive();

//    ElapsedTime time;
    Gamepad gp = new Gamepad();

    @Override
    public void init() {
        drive.config.init(hardwareMap);
    }

    @Override
    public void start() {
        resetRuntime();
    }

    @Override
    public void loop() {
        gp.left_stick_y = getRuntime() < 1.5 ? 1.f : 0.f;
        drive.drive(gp, telemetry);
    }
}

