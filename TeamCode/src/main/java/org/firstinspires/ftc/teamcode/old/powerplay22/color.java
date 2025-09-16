package org.firstinspires.ftc.teamcode.old.powerplay22;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;

@TeleOp
public class color extends OpMode {

    ColorRangeSensor sensor;

    @Override
    public void init() {
        sensor = hardwareMap.get(ColorRangeSensor.class, "imu");
    }

    @Override
    public void loop() {
        telemetry.addData("Red", sensor.red());
        telemetry.addData("Green", sensor.green());
        telemetry.addData("Blue", sensor.blue());
        sensor.enableLed(false);
    }
}
