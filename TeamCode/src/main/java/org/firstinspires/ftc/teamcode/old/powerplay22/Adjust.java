package org.firstinspires.ftc.teamcode.old.powerplay22;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.config.LiftConfig;

//@TeleOp
public class Adjust extends OpMode {

    LiftConfig lift = new LiftConfig();

    @Override
    public void init() {
        lift.init(hardwareMap);
        lift.lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lift.lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop() {
        lift.lift.setPower(-gamepad1.left_stick_y);
        telemetry.addLine("Tick "+String.valueOf(lift.lift.getCurrentPosition()));
    }
}
