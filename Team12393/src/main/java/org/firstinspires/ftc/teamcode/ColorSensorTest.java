package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@Autonomous
public class ColorSensorTest extends OpMode {
    ColorSensor color;
    DcMotorEx motor;

    @Override
    public void init() {
        color = hardwareMap.get(ColorSensor.class, "color");
        motor = hardwareMap.get(DcMotorEx.class,"aMotor");

    }

    @Override
    public void loop() {
        int green = color.green();
        int red = color.red();
        int blue = color.blue();
        int light = color.alpha();

        if(green < 300 && red < 150 && blue > 100 && light > 100) {
            motor.setPower(1);
        } else {
            motor.setPower(0);
        }

        telemetry.addData("Green", green);
        telemetry.addData("red",red);
        telemetry.addData("blue",blue);
        telemetry.addData("alpha",light);

    }
}
