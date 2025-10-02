package org.firstinspires.ftc.teamcode.component;

import org.firstinspires.ftc.teamcode.config.ArtifactIntakeConfig;

public class ArtifactIntake {
    public ArtifactIntakeConfig config = new ArtifactIntakeConfig();

    boolean running;
    public void toggle() {
        running = !running;
        config.intake.setPower(running ? 1 : 0);
    }
    public void toggle(boolean running) {
        this.running = running;
        config.intake.setPower(running ? 1 : 0);
    }
}
