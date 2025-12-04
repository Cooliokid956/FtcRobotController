package org.firstinspires.ftc.teamcode.component;

import org.firstinspires.ftc.teamcode.config.ArtifactIntakeConfig;

public class ArtifactIntake {
    public ArtifactIntakeConfig config = new ArtifactIntakeConfig();

    boolean intake, transport, flywheel;

    void _ti(boolean on) { intake = on; config.intake.setPower(on ? -1 : 0); }
    public void toggle_intake() { _ti(!intake); }
    public void toggle_intake(boolean running) { _ti(running); }

    void _tt(boolean on) { transport = on; config.transport.setPower(on ? 1 : 0); }
    public void toggle_transport() { _tt(!transport); }
    public void toggle_transport(boolean running) { _tt(running); }

    void _tf(boolean on) { flywheel = on; }
    public void toggle_flywheel() { _tf(!flywheel); }
    public void toggle_flywheel(boolean running) { _tf(running); }
    void _sfp(double power) { config.flyL.setPower(power); config.flyR.setPower(power); }
    double TARGET_VEL = 1000;
    public boolean flywheel_critical() {
        return (config.flyL.getVelocity() > TARGET_VEL && config.flyR.getVelocity() > TARGET_VEL);
    }
    double FLYWHEEL_POWER = 0.58;
    public void update_flywheel() {
        _sfp(FLYWHEEL_POWER
            * (flywheel
                ? (flywheel_critical()
                    ? 0.7
                    : 1.0)
                : 0)
        );
    }
}
