package org.firstinspires.ftc.teamcode.lib;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Manual {
    String text;
    public Manual(String... lines) {
        for (String line: lines) {
            text += line; text += "\n";
        }
    }

    public void print(Telemetry telemetry) {
        telemetry.addLine(text);
    }
}
