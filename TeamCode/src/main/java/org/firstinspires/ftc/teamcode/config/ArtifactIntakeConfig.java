package org.firstinspires.ftc.teamcode.config;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ArtifactIntakeConfig {
    public CRServo intake;

    public void init(HardwareMap hwMap) {
        intake = hwMap.get(CRServo.class, "intake");
//        intake.setDirection(DcMotorSimple.Direction.REVERSE);
        intake.setPower(0);
    }
}
