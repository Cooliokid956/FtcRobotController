package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.component.Intake;
import org.firstinspires.ftc.teamcode.component.SuperArm;
import org.firstinspires.ftc.teamcode.drive.MecanumDrive;

@TeleOp
public class robot2024 extends OpMode {
    // Drive
    MecanumDrive drive  = new MecanumDrive();

    // Components
    SuperArm     arm    = new SuperArm    ();
    Intake       intake = new Intake      ();

    Gamepad prevgp = new Gamepad();

    @Override
    public void init() {
        drive .config.init(hardwareMap);
        arm   .config.init(hardwareMap);
        intake.config.init(hardwareMap);

        prevgp.copy(gamepad1);
    }

    @Override
    public void start() {
        // insert start code here
    }   

    @Override
    public void loop() {
        drive.drive(gamepad1, telemetry);
        arm.moveArm((int)(gamepad1.right_stick_y * 15.f));
        telemetry.addData("lt", gamepad1.left_trigger);
        telemetry.addData("rt", gamepad1.right_trigger);
        arm.moveSlide((int)(gamepad1.right_trigger * 2.f - gamepad1.left_trigger * 2.f));
        arm.update(gamepad1);

        intake.config.intake.setPower(
            gamepad1.cross
            ? 1
            : (
                gamepad1.circle
                ? -1
                : 0
            )
        );
        if (gamepad1.triangle && gamepad1.triangle != prevgp.triangle) {
            intake.toggleDeploy();
        }
        if (gamepad1.dpad_right) {
            intake.config.intakeSwivel.setPosition(intake.config.intakeSwivel.getPosition()+0.01);
        }
        telemetry.addData("swivel", intake.config.intakeSwivel.getPosition());
        prevgp.copy(gamepad1);
    }
}
