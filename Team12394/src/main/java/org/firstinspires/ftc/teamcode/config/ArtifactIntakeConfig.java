package org.firstinspires.ftc.teamcode.config;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ArtifactIntakeConfig {
    public DcMotor intake;
    public DcMotor transport;

    public void init(HardwareMap hwMap) {
        intake = hwMap.get(DcMotor.class, "intake");
//        intake.setDirection(DcMotorSimple.Direction.REVERSE);
        intake.setPower(0);

        transport = hwMap.get(DcMotor.class, "transport");
//        transport.setDirection(DcMotorSimple.Direction.REVERSE);
    }
}
