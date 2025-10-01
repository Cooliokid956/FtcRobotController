package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.component.SuperArm;
import org.firstinspires.ftc.teamcode.drive.MecanumDrive;

@TeleOp(name = "TOOTY'25 (Decode)", group = "Competitive")
public class RobotDecode extends OpMode {
    MecanumDrive drive  = new MecanumDrive(); // Drive

    @Override
    public void init() { drive.config.init(hardwareMap); }

    @Override
    public void init_loop() { drive.manual.print(telemetry); }

    @Override
    public void loop() {
        gamepad1.left_stick_x = 0; // Consequence of regular old tank (4x4) drive
        drive.drive(gamepad1, telemetry);
        telemetry.addData("lt", gamepad1.left_trigger);
        telemetry.addData("rt", gamepad1.right_trigger);

        // -  awaiting further structures to program  - //
    }
}
