package org.firstinspires.ftc.teamcode.old.y22powerplay;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.old.y22powerplay.config.HDriveConfig;

//@TeleOp
public class HDriveOpModeOld extends OpMode {
    HDriveConfig robot = new HDriveConfig();

    DcMotor lift;
    boolean mode;
    boolean backHeld;
    double magnitude;
    double middlePower;
    double forwardPower;
    double turn;
    double max;

    public int bti(boolean bool) {
        return bool ? 1 : 0;
    }

    @Override
    public void init() {

        robot.init(hardwareMap);
        mode = true;
        backHeld = false;
        magnitude = 1;
        middlePower = gamepad1.left_stick_x*bti(mode)+gamepad1.right_stick_x*bti(!mode);
        forwardPower = gamepad1.left_stick_y;
        turn = gamepad1.right_stick_x*bti(mode)+gamepad1.left_stick_x*bti(!mode);
        max = Math.max(Math.abs(Math.max(forwardPower + turn, forwardPower - turn)), 1.0);

    }

    @Override
    public void loop() {
        magnitude = 1;
        middlePower = gamepad1.left_stick_x*bti(mode)+gamepad1.right_stick_x*bti(!mode);
        forwardPower = gamepad1.left_stick_y;
        turn = gamepad1.right_stick_x*bti(mode)+gamepad1.left_stick_x*bti(!mode);
        max = Math.max(Math.abs(Math.max(forwardPower + turn, forwardPower - turn)), 1.0);

        if (gamepad1.back && !backHeld){
            mode = !mode;
            backHeld = true;
        } else if (!gamepad1.back){
            backHeld = false;
        }

        robot.lMotor.setPower((forwardPower - turn) / max * magnitude);
        robot.rMotor.setPower((forwardPower + turn) / max * magnitude);
        robot.cMotor.setPower((middlePower) * magnitude);



        telemetry.addLine("y: " + forwardPower);
        telemetry.addLine("x:" + turn);
        telemetry.addLine("right x:" + middlePower);
    }
}