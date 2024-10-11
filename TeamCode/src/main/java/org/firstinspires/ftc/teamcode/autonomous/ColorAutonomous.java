package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.config.HDriveConfig;

//@Autonomous
public class ColorAutonomous extends OpMode {
    //OpenCvWebcam webcam;
    HDriveConfig robot = new HDriveConfig();

    private int state = 1;
    //public int selectRect = 0;

    public void init() {
        robot.init(hardwareMap);
    }

    public void start() {
        resetRuntime();
    }

    public void loop() {
        if (getRuntime() < 2) {
            robot.rMotor.setPower(.5);
            robot.lMotor.setPower(.5);

        }
            else if (getRuntime() < 4){
                robot.rMotor.setPower(0);
                robot.lMotor.setPower(0);
            }

        }
    }

