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

    boolean transportRunning;
    public void toggle_transport() {
        transportRunning = !transportRunning;
        config.transport.setPower(transportRunning ? 1 : 0);
    }
    public void toggle_transport(boolean running) {
        this.transportRunning = running;
        config.transport.setPower(running ? 1 : 0);
    }
}
