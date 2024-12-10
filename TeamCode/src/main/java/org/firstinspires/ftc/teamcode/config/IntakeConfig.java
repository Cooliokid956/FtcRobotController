package org.firstinspires.ftc.teamcode.config;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class IntakeConfig {
    public Servo intakeSwivel;
    public Servo intake;

    public void init(HardwareMap hwMap) {
        intakeSwivel = hwMap.get(Servo.class, "intakeSwivel");
        intakeSwivel.setPosition(0);

        intake = hwMap.get(Servo.class, "intake");
        intake.setPosition(0);
    }
}
