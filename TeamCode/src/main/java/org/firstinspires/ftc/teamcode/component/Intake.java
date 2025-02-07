package org.firstinspires.ftc.teamcode.component;

import org.firstinspires.ftc.teamcode.config.IntakeConfig;

public class Intake {
    public IntakeConfig config = new IntakeConfig();

    boolean spinning;
    public void spin() {
        spinning = true;
    }

    public void swivelSetup() {
        config.intakeSwivel.setPosition(.7);
    }

    public void update() {
        config.intake.setPower(spinning ? 1 : 0);
        spinning = false;
    }
}
