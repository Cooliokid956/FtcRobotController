package org.firstinspires.ftc.teamcode.component;

import org.firstinspires.ftc.teamcode.config.IntakeConfig;

public class Intake {
    public IntakeConfig config = new IntakeConfig();

    public void deploy(boolean deploy) { config.intakeSwivel.setPosition(deploy ? 1 : 0); }
    public void toggleDeploy() { deploy(config.intakeSwivel.getPosition() == .2); }
}
