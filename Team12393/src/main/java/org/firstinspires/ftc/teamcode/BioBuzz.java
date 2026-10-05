package org.firstinspires.ftc.teamcode;

import static java.lang.Math.abs;
import static java.lang.Math.max;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class BioBuzz extends OpMode {

    private DcMotorEx backRightMotor;
    private DcMotorEx backLeftMotor;
    private DcMotorEx frontRightMotor;
    private DcMotorEx frontLeftMotor;

    @Override
    public void init() {
        frontLeftMotor = hardwareMap.get(DcMotorEx.class, "frontLeft");
        frontRightMotor = hardwareMap.get(DcMotorEx.class, "frontRight");
        backLeftMotor = hardwareMap.get(DcMotorEx.class, "backLeft");
        backRightMotor = hardwareMap.get(DcMotorEx.class, "backRight");

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void loop() {
        double
                y = gamepad1.left_stick_y,
                x = gamepad1.left_stick_x,
                turn = -gamepad1.right_stick_x,
                frontLeftPower = y + x - turn,
                frontRightPower = y - x + turn,
                backLeftPower = y - x - turn,
                backRightPower = y + x + turn;

        double maxRawPower = Math.max(1.0, max(max(abs(frontLeftPower), abs(backLeftPower)),
                max(abs(backRightPower), abs(frontRightPower))));

        frontLeftMotor.setPower(frontLeftPower / maxRawPower);
        frontRightMotor.setPower(frontRightPower / maxRawPower);
        backLeftMotor.setPower(backLeftPower / maxRawPower);
        backRightMotor.setPower(backRightPower / maxRawPower);

        telemetry.addData("Front Left Motor: ", frontLeftPower);
        telemetry.addData("Front Right Motor: ", frontRightPower);
        telemetry.addData("BackLeftMotor", backLeftPower);
        telemetry.addData("backRightPower", backRightPower);
    }
}
