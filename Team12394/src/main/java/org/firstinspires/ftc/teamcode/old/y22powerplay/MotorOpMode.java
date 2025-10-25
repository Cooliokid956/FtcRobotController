package org.firstinspires.ftc.teamcode.old.y22powerplay;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

//@TeleOp
public class MotorOpMode extends OpMode {
    ProgrammingBoard1 robot = new ProgrammingBoard1();

    public void init() {
        robot.init(hardwareMap);
    }

    @Override
    public void loop() {
        robot.setPower(0.7);
    }
}