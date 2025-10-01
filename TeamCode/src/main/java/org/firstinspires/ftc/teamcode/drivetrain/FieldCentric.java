package org.firstinspires.ftc.teamcode.drivetrain;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Robot;

@TeleOp(name = "FieldCentric", group = "OpModes")
public class FieldCentric extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    DcMotorEx flm;
    DcMotorEx frm;
    DcMotorEx blm;
    DcMotorEx brm;
    IMU imu;

    public void runOpMode() {
        Robot robot = new Robot();
        robot.init(hardwareMap, this);
        imu = robot.getImu();
        flm = hardwareMap.get(DcMotorEx.class, "fl");
        frm = hardwareMap.get(DcMotorEx.class, "fr");
        blm = hardwareMap.get(DcMotorEx.class, "bl");
        brm = hardwareMap.get(DcMotorEx.class, "br");

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();
        runtime.reset();

//        while(opModeIsActive()) {
//            double y = -gamepad1.left_stick_y;
//            double x = gamepad1.left_stick_x;
//            double rx = gamepad1.right_stick_x;
//
//            double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
//            if(gamepad1.aWasReleased()) {
//                imu.resetYaw();
//            }
//            double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
//            double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);
//
//            double frontLeftPower = rotY + rotX + rx;
//            //double frontRightPower = rotY - rotX - rx;
//            double frontRightPower = rotY - rotX + rx;
//            // double backLeftPower = rotY - rotX + rx;
//            double backLeftPower = rotY - rotX - rx;
//            double backRightPower = rotY + rotX - rx;
//
//            double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
//            if(denominator > 1.0) {
//                frontLeftPower /= denominator;
//                frontRightPower /= denominator;
//                backLeftPower /= denominator;
//                backRightPower /= denominator;
//            }
        while(opModeIsActive()) {
            robot.drive(true);
            //code to move mechanisms
        }
        //}
    }
}
