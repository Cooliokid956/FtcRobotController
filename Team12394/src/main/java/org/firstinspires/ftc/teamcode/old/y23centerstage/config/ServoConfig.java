package org.firstinspires.ftc.teamcode.old.y23centerstage.config;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ServoConfig {
    private Servo servo;

    public void init(HardwareMap hwMap) {
        servo = hwMap.get(Servo.class, "Servy");
    }

    public void setServoPosition(double pos) {
        servo.setPosition(pos);
    }

    public void getPosition() {
        servo.getPosition();
    }
}
