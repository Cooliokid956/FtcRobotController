package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.config.MecanumConfig;
import org.firstinspires.ftc.teamcode.lib.Manual;

public class MecanumDrive {
    // Manual
    public Manual manual = new Manual(
            "Drive:",
            "Left Stick( X: Strafe, Y: Fwd/Back )",
            "Right Stick( X: Turn )",
            "Back/Share: Turbo, Start/Options: Flip direction");
    public MecanumConfig config = new MecanumConfig();

    boolean turbo = false, swapDir = true;

    public void toggle_turbo(boolean turbo) {
        this.turbo = turbo;
    }
    public void toggle_turbo() {
        this.turbo = !this.turbo;
    }

    public void toggle_direction(boolean forwards) {
        this.swapDir = forwards;
    }
    public void toggle_direction() {
        this.swapDir = !this.swapDir;
    }

    public void drive(Gamepad gamepad, Telemetry telemetry) {
        double
                magnitude = turbo ? 1 : .5,
                lrPower =
                        Math.abs(gamepad.left_stick_x) > 0.04
                                ? gamepad.left_stick_x
                                : 0,
                fwdPower = -gamepad.left_stick_y,
                turn = gamepad.right_stick_x,
                max;

        fwdPower *= magnitude; lrPower *= magnitude;

        if (gamepad. backWasPressed()) toggle_turbo();
        if (gamepad.startWasPressed()) toggle_direction();

        if (swapDir) {
            fwdPower *= -1; lrPower *= -1;
        }

        max = Math.max(Math.abs((fwdPower + turn + lrPower)),
              Math.max(Math.abs((fwdPower + turn - lrPower)),
              Math.max(Math.abs((fwdPower - turn - lrPower)),
              Math.max(Math.abs((fwdPower - turn + lrPower)), 1))));
        config.flMotor.setPower((fwdPower + turn + lrPower) / max);
        config.blMotor.setPower((fwdPower + turn - lrPower) / max);
        config.frMotor.setPower((fwdPower - turn - lrPower) / max);
        config.brMotor.setPower((fwdPower - turn + lrPower) / max);

        telemetry.addData("y", fwdPower);
        telemetry.addData("x", lrPower);
        telemetry.addData("turn", turn);
        telemetry.addLine();
        telemetry.addData(":", "%4.2f-%4.2f : :", config.flMotor.getPower(), config.frMotor.getPower());
        telemetry.addData(":", "|-------------| : :");
        telemetry.addData(":", "|-------------| : :");
        telemetry.addData(":", "%4.2f-%4.2f : :", config.blMotor.getPower(), config.brMotor.getPower());
    }
}
