package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.component.SuperArm;
import org.firstinspires.ftc.teamcode.drive.MecanumDrive;

@TeleOp
public class RobotDecode extends OpMode {
    // Drive
    MecanumDrive drive  = new MecanumDrive();

    @Override
    public void init() {
        drive.config.init(hardwareMap);
    }

    @Override
    public void init_loop() {
        drive.manual.print(telemetry);
    }

    @Override
    public void loop() {
        drive.drive(gamepad1, telemetry);
        telemetry.addData("lt", gamepad1.left_trigger);
        telemetry.addData("rt", gamepad1.right_trigger);
    }
}
