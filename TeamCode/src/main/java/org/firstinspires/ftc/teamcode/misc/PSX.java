package org.firstinspires.ftc.teamcode.misc;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

//@TeleOp
public class PSX extends OpMode {
    String type;

    @Override
    public void init() {
        telemetry.addLine("Press some letters or shapes to find out whether you got scammed");
        switch (gamepad1.type) {
            case SONY_PS4_SUPPORTED_BY_KERNEL:
            case SONY_PS4:      type = "DualShock 4";         break;
            case XBOX_360:      type = "Xbox 360 Controller"; break;
            case LOGITECH_F310: type = "Logitech F310";       break;
            case UNKNOWN:       type = "idk";                 break;
        }
    }

    double counter = 0;

    @Override
    public void loop() {
        telemetry.addData("Reported type", type);
        if (counter>0)
            if (++counter>3000) requestOpModeStop();
        else {
            if (gamepad1.cross || gamepad1.circle || gamepad1.square || gamepad1.triangle) {
                telemetry.addLine("It's a girl! (DS4 controller type)");
                counter++;
            }
            if (gamepad1.a || gamepad1.b || gamepad1.x || gamepad1.y) {
                telemetry.addLine("It's a boy! (Xbox controller type)");
                counter++;
            }
        }
        telemetry.update();
    }
}