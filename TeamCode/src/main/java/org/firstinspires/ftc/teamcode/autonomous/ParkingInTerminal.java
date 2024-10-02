package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.config.HDriveConfig;

@Autonomous
public class ParkingInTerminal extends OpMode {
    HDriveConfig robot = new HDriveConfig();

//    ElapsedTime time;
    boolean timeStart = false;

    @Override
    public void init() {
        robot.init(hardwareMap);
    }

    @Override
    public void loop() {
        if (!timeStart) {
//            time = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
            resetRuntime();
            timeStart = true;
        }

        if (1.5 >= getRuntime()) {
            robot.cMotor.setPower(1.0);
        } else if (3 >= getRuntime()) {
            robot.cMotor.setPower(0.0);
        }
    }
}

