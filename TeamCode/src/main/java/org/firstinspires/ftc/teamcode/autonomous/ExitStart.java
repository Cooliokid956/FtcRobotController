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
        if (1.5 >= getRuntime()) {
            gp.left_stick_y = -1.0f;
        } else if (3 >= getRuntime()) {
            gp.left_stick_y = -0.0f;
        }
        drive.drive(gp, telemetry);
    }
}

