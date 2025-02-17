package org.firstinspires.ftc.teamcode.config;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class IntakeConfig {
    public Servo intakeSwivel;
    public CRServo intake;

    public void init(HardwareMap hwMap) {
        intakeSwivel = hwMap.get(Servo.class, "intakeSwivel");
        intakeSwivel.setPosition(0);

        intake = hwMap.get(CRServo.class, "intake");
        intake.setDirection(DcMotorSimple.Direction.REVERSE);
        intake.setPower(0);
    }
}
