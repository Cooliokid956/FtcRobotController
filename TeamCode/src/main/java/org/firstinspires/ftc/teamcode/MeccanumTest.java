package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.config.MeccanumConfig;

@TeleOp
public class MeccanumTest extends OpMode {
    MeccanumConfig robot = new MeccanumConfig();

    public void init() {
        robot.init(hardwareMap);
    }

    public int bti(boolean bool) {
        return bool ? 1 : 0;
    }

    @Override
    public void loop() {
        robot.flMotor.setPower(gamepad1.x?1:0);
        robot.blMotor.setPower(gamepad1.a?1:0);
        robot.frMotor.setPower(gamepad1.y?1:0);
        robot.brMotor.setPower(gamepad1.b?1:0);
    }
}