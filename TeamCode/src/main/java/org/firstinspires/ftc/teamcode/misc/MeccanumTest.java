package org.firstinspires.ftc.teamcode.misc;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.config.MecanumConfig;

//@TeleOp
public class MeccanumTest extends OpMode {
    MecanumConfig robot = new MecanumConfig();

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