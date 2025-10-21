package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.component.ArtifactIntake;
import org.firstinspires.ftc.teamcode.drive.MecanumDrive;

@TeleOp(name = "TOOTY'25 (Decode)", group = "Competitive")
public class RobotDecode extends OpMode {
    MecanumDrive drive = new MecanumDrive();
    ArtifactIntake intake = new ArtifactIntake();

    @Override
    public void init() { drive.config.init(hardwareMap); }

    @Override
    public void init_loop() { drive.manual.print(telemetry); }

    @Override
    public void loop() {
        gamepad1.left_stick_x = 0; // Consequence of no Mecanum wheels; regular old tank (4x4) drive
        drive.drive(gamepad1, telemetry);
        telemetry.addData("lt", gamepad1.left_trigger);
        telemetry.addData("rt", gamepad1.right_trigger);

        intake.toggle_intake(gamepad1.cross);
        intake.toggle_flywheel(gamepad1.circle);
    }
}
