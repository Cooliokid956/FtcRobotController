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

    public void toggle_turbo(boolean turbo) { this.turbo = turbo; }
    public void toggle_turbo() { this.turbo = !this.turbo; }

    public void toggle_direction(boolean forwards) { this.swapDir = forwards; }
    public void toggle_direction() { this.swapDir = !this.swapDir; }

    public void drive(Gamepad gamepad, Telemetry telemetry) {
        double
                magnitude = turbo ? 1 : .5,
                forward = -gamepad.left_stick_y,
                strafe = Math.abs(gamepad.left_stick_x) > 0.04
                                ? gamepad.left_stick_x
                                : 0,
                turn = gamepad.right_stick_x * 0.5,
                max;

        forward *= magnitude; strafe *= magnitude;
        if (swapDir) { forward *= -1; strafe *= -1; }

        if (gamepad. backWasPressed()) toggle_turbo();
        if (gamepad.startWasPressed()) toggle_direction();

        max = Math.max(Math.abs((forward + turn + strafe)),
              Math.max(Math.abs((forward + turn - strafe)),
              Math.max(Math.abs((forward - turn - strafe)),
              Math.max(Math.abs((forward - turn + strafe)), 1))));
        config.flMotor.setPower((forward + turn + strafe) / max);
        config.blMotor.setPower((forward + turn - strafe) / max);
        config.frMotor.setPower((forward - turn - strafe) / max);
        config.brMotor.setPower((forward - turn + strafe) / max);

        telemetry.addData("y", forward);
        telemetry.addData("x", strafe);
        telemetry.addData("turn", turn);
        telemetry.addLine();
        telemetry.addData(":", "%4.2f-%4.2f : :", config.flMotor.getPower(), config.frMotor.getPower());
        telemetry.addData(":", "|-------------| : :");
        telemetry.addData(":", "|-------------| : :");
        telemetry.addData(":", "%4.2f-%4.2f : :", config.blMotor.getPower(), config.brMotor.getPower());
    }
}
