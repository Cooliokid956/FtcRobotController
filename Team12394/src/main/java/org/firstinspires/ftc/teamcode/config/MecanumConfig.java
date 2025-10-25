package org.firstinspires.ftc.teamcode.config;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MecanumConfig {
    public DcMotor flMotor, frMotor, blMotor, brMotor;

    public void init(HardwareMap hwMap) {
        flMotor = hwMap.get(DcMotor.class, "flMotor");
        frMotor = hwMap.get(DcMotor.class, "frMotor");
        blMotor = hwMap.get(DcMotor.class, "blMotor");
        brMotor = hwMap.get(DcMotor.class, "brMotor");
//        flMotor.setDirection(DcMotorSimple.Direction.REVERSE); // Comment this out for Their robot
                                                                // Ours too apparently
        blMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }
}
