package org.firstinspires.ftc.teamcode.JTC.drivetrain;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.JTC.Robot;

@TeleOp(name = "FieldCentric", group = "OpModes (JTC)")
public class FieldCentric extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    DcMotorEx flm;
    DcMotorEx frm;
    DcMotorEx blm;
    DcMotorEx brm;
    IMU imu;

    public void runOpMode() {
        Robot robot = new Robot();
        robot.init(hardwareMap);
        imu = robot.getImu();
        flm = hardwareMap.get(DcMotorEx.class, "");
        frm = hardwareMap.get(DcMotorEx.class, "");
        blm = hardwareMap.get(DcMotorEx.class, "");
        brm = hardwareMap.get(DcMotorEx.class, "");

        flm.setDirection(DcMotor.Direction.REVERSE);
        blm.setDirection(DcMotor.Direction.REVERSE);
        frm.setDirection(DcMotor.Direction.FORWARD);
        brm.setDirection(DcMotor.Direction.FORWARD);
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();
        runtime.reset();

        while(opModeIsActive()) {
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;

            double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
            if(gamepad1.aWasReleased()) {
                imu.resetYaw();
            }
            double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
            double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

            double frontLeftPower = rotY + rotX + rx;
            double frontRightPower = rotY - rotX - rx;
            double backLeftPower = rotY - rotX + rx;
            double backRightPower = rotY + rotX - rx;

            double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
            if(denominator > 1.0) {
                frontLeftPower /= denominator;
                frontRightPower /= denominator;
                backLeftPower /= denominator;
                backRightPower /= denominator;
            }

            robot.setMotorSpeed (frontLeftPower, backLeftPower, frontRightPower, backRightPower);
            telemetry.addData("Bot Heading:", botHeading);
            telemetry.addData("Gamepad(Left V, Left H, Right H):",y + " " + x + " " + rx);
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Front left/Right", "%4.2f, %4.2f", frontLeftPower, frontRightPower);
            telemetry.addData("Back  left/Right", "%4.2f, %4.2f", backLeftPower, backRightPower);
            telemetry.update();

        }
    }
}
