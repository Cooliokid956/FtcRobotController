package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.config.HDriveConfig;

//@Autonomous
public class AutoTimeBased extends OpMode {
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

        if (4 >= getRuntime()) {
            robot.lMotor.setPower(1.0);
            robot.rMotor.setPower(1.0);
        } else if (7.5 >= getRuntime()) {
            robot.lMotor.setPower(1.0);
            robot.rMotor.setPower(0.8);
        } else if (14 >= getRuntime()) {
            robot.lMotor.setPower(0);
            robot.rMotor.setPower(0);
        } else if (15 >= getRuntime()) {
            robot.lMotor.setPower(1);
            robot.rMotor.setPower(1);
        } else if (15 >= getRuntime()) {
            robot.lMotor.setPower(1);
            robot.rMotor.setPower(1);
        } else if (15 >= getRuntime()) {
            robot.lMotor.setPower(1);
            robot.rMotor.setPower(1);
        } else if (15 >= getRuntime()) {
            robot.lMotor.setPower(1);
            robot.rMotor.setPower(1);
        }
    }
}
