package org.firstinspires.ftc.teamcode.old.y24intothedeep.component;

import org.firstinspires.ftc.teamcode.old.y24intothedeep.config.IntakeConfig;

public class Intake {
    public IntakeConfig config = new IntakeConfig();
    boolean deployed;

    public void deploy(boolean deploy) {
        deployed = deploy;
        config.intakeSwivel.setPosition(deploy ? .55 : 0);
    }
    public void toggleDeploy() { deploy(!deployed); }
}
