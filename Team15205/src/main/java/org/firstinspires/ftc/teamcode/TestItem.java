package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

abstract public class TestItem {
    //You can right click and press generate to make a variables getter and setter commands
    private String description;
    protected TestItem(String description){
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
    abstract public void run(boolean on, Telemetry telemetry);
}
