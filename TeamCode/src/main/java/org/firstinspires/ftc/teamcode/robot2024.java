package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

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

    @Override
    public void init() {
        drive .config.init(hardwareMap);
        arm   .config.init(hardwareMap);
        intake.config.init(hardwareMap);
    }

    @Override
    public void start() {
        // insert start code here
    }   

    @Override
    public void loop() {
        drive.drive(gamepad1, telemetry);
        arm.moveArm((int)(gamepad1.right_stick_y * 5.f));
        telemetry.addData("lt", gamepad1.left_trigger);
        telemetry.addData("rt", gamepad1.right_trigger);
        arm.moveSlide((int)(gamepad1.right_trigger * 2.f - gamepad1.left_trigger * 2.f));
        arm.update(gamepad1);


        if (gamepad1.a) {
            intake.spin();
            telemetry.addData("intake", "spin");
        }
        intake.update();
    }
}
