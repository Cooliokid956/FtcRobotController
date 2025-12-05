package org.firstinspires.ftc.teamcode.config;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ArtifactIntakeConfig {
    public DcMotor intake, transport;
    public DcMotorEx flyL, flyR;
    public Servo ramp;

    public void init(HardwareMap hwMap) {
        intake = hwMap.get(DcMotor.class, "intake");

        transport = hwMap.get(DcMotor.class, "transport");
        transport.setDirection(DcMotorSimple.Direction.REVERSE);

        flyL = hwMap.get(DcMotorEx.class, "flyL");
        flyL.setDirection(DcMotorSimple.Direction.REVERSE);
        flyR = hwMap.get(DcMotorEx.class, "flyR");

        ramp = hwMap.get(Servo.class, "ramp");
    }
}
