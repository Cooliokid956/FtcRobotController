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
    public void init() {
        drive .config.init(hardwareMap);
        intake.config.init(hardwareMap);
    }

    @Override
    public void init_loop() {
        drive .manual.print(telemetry);
        intake.manual.print(telemetry);
    }

    @Override
    public void loop() {
        gamepad1.left_stick_x = 0; // Consequence of no Mecanum wheels; regular old tank (4x4) drive
        drive.drive(gamepad1, telemetry);
        telemetry.addData("lt", gamepad1.left_trigger);
        telemetry.addData("rt", gamepad1.right_trigger);

        intake.toggle_intake(gamepad1.left_trigger > 0.8);
        intake.revT = gamepad1.right_bumper;
        intake.toggle_transport(
                gamepad1.left_bumper
                || gamepad1.right_bumper
                || intake.flywheel_critical()
        );
        intake.toggle_flywheel(gamepad1.right_trigger > 0.8);
        intake.update();

        telemetry.addData("Flywheel", intake.config.flyL.getVelocity());
        if (intake.flywheel_critical()) telemetry.addLine("CRITICAL!");
    }
}
