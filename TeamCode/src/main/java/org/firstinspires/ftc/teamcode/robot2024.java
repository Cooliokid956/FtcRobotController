package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.component.Intake;
import org.firstinspires.ftc.teamcode.component.SuperArm;
import org.firstinspires.ftc.teamcode.drive.MecanumDrive;

@TeleOp
public class robot2024 extends OpMode {
    MecanumDrive drive  = new MecanumDrive();
    SuperArm     arm    = new SuperArm    ();
    Intake       intake = new Intake      ();

    @Override
    public void init() {
        drive.config.init(hardwareMap);
        arm  .config.init(hardwareMap);
    }

    @Override
    public void start() {
        // insert start code here
    }   

    @Override
    public void loop() {
        drive.drive(gamepad1, telemetry);
        arm.moveArm((int)(gamepad1.right_stick_y * 5.f));
        arm.moveSlide((int)(gamepad1.right_trigger * 2.f - gamepad1.left_trigger * 2.f));
        arm.update();
        intake.update();
    }
}
