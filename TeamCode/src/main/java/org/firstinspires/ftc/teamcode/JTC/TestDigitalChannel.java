package org.firstinspires.ftc.teamcode.JTC;

import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TestDigitalChannel extends TestItem {
    DigitalChannel digitalChannel;
    double min;
    double max;
    public TestDigitalChannel(String description, DigitalChannel digitalChannel, double min, double max) {
        super(description);
        this.digitalChannel = digitalChannel;
        this.min = min;
        this.max = max;
    }

    public void run(boolean on, Telemetry telemetry) {
        telemetry.addData("Detects: ", !digitalChannel.getState());
    }
}
