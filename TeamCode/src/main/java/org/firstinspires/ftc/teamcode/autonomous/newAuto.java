package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.component.Intake;
import org.firstinspires.ftc.teamcode.component.SuperArm;
import org.firstinspires.ftc.teamcode.drive.MecanumDrive;

@Autonomous
public class newAuto extends OpMode {
    // Drive
    MecanumDrive drive  = new MecanumDrive();

    // Components
    SuperArm arm  = new SuperArm    ();
    Intake intake = new Intake      ();

    Gamepad gp = new Gamepad();

    @Override
    public void init() {
        resetRuntime();
    }

    @Override
    public void loop() {
        double t = getRuntime();
        if (t < 0.2) {
//            gp.
        }

        drive.drive(gp, telemetry);
    }
}
