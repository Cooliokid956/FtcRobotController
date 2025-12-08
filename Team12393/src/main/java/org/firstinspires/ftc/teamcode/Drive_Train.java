package org.firstinspires.ftc.teamcode;
import static java.lang.Math.*;


import android.hardware.Sensor;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.VoltageSensor;

@TeleOp
public class Drive_Train extends OpMode {
    private DcMotorEx frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor, reversed_outtake_Motor, outtake_Motor, intake_Motor;
    private CRServo Is;

    @Override
    public void init() {
        //Webcam -> webcam
        //ControlHub -> Motors -> 1:Hex 40:1 OuttakeMotor1, 2:GoBilda 5202/3/4? frm 3: GoBilda 5202/3/4? brm
        //ExpansionHub -> Motors -> 0: GoBilda 5202/3/4? blm 1: GoBilda 5202/3/4? flm 2: Hex Motor 40:1 IntakeMotor 3: Go bilda 5202/3/4 outtakeMotor2
        reversed_outtake_Motor = hardwareMap.get(DcMotorEx.class, "OuttakeMotor2");
        outtake_Motor = hardwareMap.get(DcMotorEx.class, "OuttakeMotor1");
        intake_Motor = hardwareMap.get(DcMotorEx.class, "IntakeMotor" );
        Is = hardwareMap.get(CRServo.class, "Is");
        outtake_Motor.setDirection(DcMotorSimple.Direction.REVERSE);
        intake_Motor.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeftMotor = hardwareMap.get(DcMotorEx.class, "flm");
        frontRightMotor = hardwareMap.get(DcMotorEx.class, "frm");
        backLeftMotor = hardwareMap.get(DcMotorEx.class, "blm");
        backRightMotor = hardwareMap.get(DcMotorEx.class, "brm");

        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void loop() {
        double
            vel = outtake_Motor.getVelocity(),
            vel2 = reversed_outtake_Motor.getVelocity();

        Is.setPower(((vel >= 1500) && (vel2 >= 1500)) || (gamepad1.right_trigger >= 0.25)
                ? 1
                : (gamepad1.left_bumper ? -1 : 0));
        //Is.setPower((gamepad1.right_trigger >= 0.25) ? -1 : (gamepad1.left_bumper ? 1 :0));
        reversed_outtake_Motor.setPower(gamepad1.x ? .8 : gamepad1.a ? -1 : 0);
        outtake_Motor.setPower(gamepad1.x  ? 1 : gamepad1.a ? -1 : 0);

        intake_Motor.setPower(gamepad1.right_bumper ? .8 : gamepad1.left_bumper ? -.8 : 0);

        double
            y = gamepad1.left_stick_y,
            x = gamepad1.left_stick_x,
            turn = gamepad1.right_stick_x,
            frontLeftPower = y + x - turn,
            frontRightPower = y + x + turn,
            backLeftPower = y - x - turn,
            backRightPower = y - x + turn;

        double maxRawPower = max(max(max(abs(frontLeftPower), abs(backLeftPower)),
                max(abs(backRightPower), abs(frontRightPower))), 1);
        frontLeftMotor.setPower(frontLeftPower / maxRawPower);
        frontRightMotor.setPower(frontRightPower / maxRawPower);
        backLeftMotor.setPower(backLeftPower / maxRawPower);
        backRightMotor.setPower(backRightPower / maxRawPower);
        telemetry.addData("Front Left Motor: ", frontLeftPower);
        telemetry.addData("Front Right Motor: ",frontRightPower);
        telemetry.addData("BackLeftMotor",backLeftPower);
        telemetry.addData("backRightPower",backRightPower);
    }
}

