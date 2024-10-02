package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class PSX extends OpMode {

    @Override
    public void init() {
        telemetry.addLine("Press some letters or shapes to find out whether you got scammed");
    }

    @Override
    public void loop() {
        double counter = 0;

        if (counter>0){
            counter++;
            if (counter>3000){
                requestOpModeStop();
            }
        } else {
            if (gamepad1.cross || gamepad1.circle || gamepad1.square || gamepad1.triangle) {
                telemetry.addLine("It's a girl! (DS4 controller type)");
                counter++;
            }
            if (gamepad1.a || gamepad1.b || gamepad1.x || gamepad1.y) {
                telemetry.addLine("It's a boy! (Xbox controller type)");
                counter++;
            }
        }
    }
}
