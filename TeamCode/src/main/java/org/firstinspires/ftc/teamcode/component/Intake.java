package org.firstinspires.ftc.teamcode.component;

import org.firstinspires.ftc.teamcode.config.IntakeConfig;

public class Intake {
    public IntakeConfig config = new IntakeConfig();

    boolean spinning;

    public void spin() {
        spinning = true;
    }

    public void deploy(boolean deploy) {
        config.intakeSwivel.setPosition(deploy ? .7 : 0);
    }
    public void toggleDeploy() {
        deploy(config.intakeSwivel.getPosition() == 0);
    }

    public void update() {
        config.intake.setPower(spinning ? 1 : 0);
        spinning = false;
    }
}
