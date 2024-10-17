package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.component.SuperArm;
import org.firstinspires.ftc.teamcode.drive.MecanumDrive;

@TeleOp
public class robot2024 extends OpMode {
    MecanumDrive drive = new MecanumDrive();
    SuperArm     arm   = new SuperArm();

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
        arm.move((int)(gamepad1.right_stick_y * 100.f));
        arm.update();
    }
}
