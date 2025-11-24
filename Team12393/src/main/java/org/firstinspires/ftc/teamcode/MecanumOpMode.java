package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class MecanumOpMode extends OpMode {

    DriveTrain mecanumDrive = new DriveTrain() {
        @Override
        public void init() {

        }

        @Override
        public void loop() {

        }
    };

    @Override
    public void init() {
        mecanumDrive.init(hardwareMap);
    }

    @Override
    public void loop() {
        mecanumDrive.drive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
    }
}
