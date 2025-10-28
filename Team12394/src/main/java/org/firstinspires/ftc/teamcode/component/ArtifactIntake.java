package org.firstinspires.ftc.teamcode.component;

import org.firstinspires.ftc.teamcode.config.ArtifactIntakeConfig;

public class ArtifactIntake {
    public ArtifactIntakeConfig config = new ArtifactIntakeConfig();

    boolean intakeRunning;
    public void toggle_intake() {
        intakeRunning = !intakeRunning;
        config.intake.setPower(intakeRunning ? 1 : 0);
    }
    public void toggle_intake(boolean running) {
        this.intakeRunning = running;
        config.intake.setPower(running ? 1 : 0);
    }

    boolean flywheelRunning;
    public void toggle_flywheel() {
        flywheelRunning = !flywheelRunning;
        config.flywheel.setPower(flywheelRunning ? 1 : 0);
    }
    public void toggle_flywheel(boolean running) {
        this.flywheelRunning = running;
        config.flywheel.setPower(running ? 1 : 0);
    }
}
