package org.firstinspires.ftc.teamcode.old.y23centerstage;

import androidx.core.math.MathUtils;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

//@TeleOp
public class ClawTest extends OpMode {
    Servo claw;
    float pos = .453f;
    //.38
    @Override
    public void init() {
        claw = hardwareMap.get(Servo.class, "servoClaw");
    }

    @Override
    public void loop() {
        pos = MathUtils.clamp(pos + gamepad1.left_stick_y/300f, 0f, 1f);
        claw.setPosition(pos);
        telemetry.addData("target",pos);
        telemetry.addData("reported",claw.getPosition());
    }
}
