package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.config.MeccanumConfig;
import org.firstinspires.ftc.teamcode.lib.Manual;

public class MecanumDrive {
    // Manual
    public Manual manual = new Manual(""+
            "Drive:\n" +
            "Left Stick( X: Strafe, Y: Fwd/Back )\n" +
            "Right Stick( X: Turn )\n" +
            "Back/Share: Turbo, Start/Options: Flip direction");
    public MeccanumConfig config = new MeccanumConfig();

    boolean turbo = false;
    boolean swapDir = true;
    boolean backHeld, startHeld;

    public void drive(Gamepad gamepad, Telemetry telemetry) {
        double magnitude = turbo ? 1 : .5;
        double lrPower = (Math.abs(gamepad.left_stick_x) > 0.04 ? gamepad.left_stick_x : 0) * magnitude;
        double fwdPower = -gamepad.left_stick_y * magnitude;
        double turn = gamepad.right_stick_x;
        double max = 1.0;

        if (gamepad.back && !backHeld) {
            turbo = !turbo;
            backHeld = true;
        } else if (!gamepad.back) {
            backHeld = false;
        }
        if (gamepad.start && !startHeld) {
            swapDir = !swapDir;
            startHeld = true;
        } else if (!gamepad.start) {
            startHeld = false;
        }
        if (swapDir) {
            fwdPower = -fwdPower;
            lrPower = -lrPower;
        }

        max = Math.max(Math.abs((fwdPower + turn + lrPower)), max);
        max = Math.max(Math.abs((fwdPower + turn - lrPower)), max);
        max = Math.max(Math.abs((fwdPower - turn - lrPower)), max);
        max = Math.max(Math.abs((fwdPower - turn + lrPower)), max);
        config.flMotor.setPower((fwdPower + turn + lrPower) / max);
        config.blMotor.setPower((fwdPower + turn - lrPower) / max);
        config.frMotor.setPower((fwdPower - turn - lrPower) / max);
        config.brMotor.setPower((fwdPower - turn + lrPower) / max);
        telemetry.addLine("y: " + fwdPower);
        telemetry.addLine("x:" + turn);
        telemetry.addLine("right x:" + lrPower);
        telemetry.addLine(config.flMotor.getPower() + "--" + config.frMotor.getPower());
        telemetry.addLine("|--|");
        telemetry.addLine(config.blMotor.getPower() + "--" + config.brMotor.getPower());
    }
}
