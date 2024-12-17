package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.config.MeccanumConfig;

public class MecanumDrive {
    public MeccanumConfig config = new MeccanumConfig();

    boolean modeMag = true;
    boolean backHeld;
    boolean modeDir = true;
    boolean startHeld;

    public void drive(Gamepad gamepad, Telemetry telemetry) {
        double magnitude = modeMag ? 0.5 : 1;
        double lrPower = Math.abs(gamepad.left_stick_x) >= 0.04 ? gamepad.left_stick_x : 0;
        double fwdPower = -gamepad.left_stick_y;
        double turn = gamepad.right_stick_x;
        double max = Math.max(Math.abs(fwdPower) + Math.abs(turn), 1.0);

        if (gamepad.back && !backHeld) {
            modeMag = !modeMag;
            backHeld = true;
        } else if (!gamepad.back) {
            backHeld = false;
        }
        if (gamepad.start && !startHeld) {
            modeDir = !modeDir;
            startHeld = true;
        } else if (!gamepad.start) {
            startHeld = false;
        }
        if (modeDir) {
            fwdPower = -fwdPower;
            lrPower = -lrPower;
        }

        config.flMotor.setPower((fwdPower + turn + lrPower) / max * magnitude);
        config.blMotor.setPower((fwdPower + turn - lrPower) / max * magnitude);
        config.frMotor.setPower((fwdPower - turn - lrPower) / max * magnitude);
        config.brMotor.setPower((fwdPower - turn + lrPower) / max * magnitude);
        telemetry.addLine("y: " + fwdPower);
        telemetry.addLine("x:" + turn);
        telemetry.addLine("right x:" + lrPower);
        telemetry.addLine(config.flMotor.getPower() + "--" + config.frMotor.getPower());
        telemetry.addLine("|--|");
        telemetry.addLine(config.blMotor.getPower() + "--" + config.brMotor.getPower());
    }
}
