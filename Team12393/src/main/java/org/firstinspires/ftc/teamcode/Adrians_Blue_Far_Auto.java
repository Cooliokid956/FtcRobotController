package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class Adrians_Blue_Far_Auto extends LinearOpMode {
    private DcMotorEx frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor, reversed_outtake_Motor, outtake_Motor, intake_Motor;
    private CRServo Is;

    @Override
    public void runOpMode() {
        reversed_outtake_Motor = hardwareMap.get(DcMotorEx.class, "OuttakeMotor2");
        outtake_Motor = hardwareMap.get(DcMotorEx.class, "OuttakeMotor1");
        intake_Motor = hardwareMap.get(DcMotorEx.class, "IntakeMotor");
        Is = hardwareMap.get(CRServo.class, "Is");
        outtake_Motor.setDirection(DcMotorSimple.Direction.REVERSE);
        intake_Motor.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeftMotor = hardwareMap.get(DcMotorEx.class, "flm");
        frontRightMotor = hardwareMap.get(DcMotorEx.class, "frm");
        backLeftMotor = hardwareMap.get(DcMotorEx.class, "blm");
        backRightMotor = hardwareMap.get(DcMotorEx.class, "brm");

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Auto starts here
        waitForStart();
        moveBackward(1000);
        pause(200);
        turnRight(1000);
        pause(200);
        rev(1500);
        launch(2500);
        turnLeft(1000);
        pause(200);
        moveBackward(1000);
        pause(200);
        turnLeft(1000);
        pause(200);





        sleep(1000);
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);

    }

    private void moveForward(int x) {
        setTargetPositions(
                x, x,
                x, x
        );
    }

    private void moveBackward(int x) {
        setTargetPositions(
                -x,-x,
                -x,-x
        );
    }

    private void moveLeft(int x) {
        setTargetPositions(
                -x, x,
                 x,-x
        );
    }

    private void moveRight(int x) {
        setTargetPositions(
                x,-x,
               -x, x
        );
    }

    private void turnRight(int x)
    {
        setTargetPositions(
                x,-x,
                x,-x
        );
    }
    private void turnLeft(int x)
    {
        setTargetPositions(
                -x, x,
                -x, x
        );
    }

    private void resetEncoders()
    {
        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    private void setTargetPositions(int fl, int fr, int bl, int br)
    {
        frontLeftMotor.setTargetPosition(fl);
        frontRightMotor.setTargetPosition(fr);
        backLeftMotor.setTargetPosition(bl);
        backRightMotor.setTargetPosition(br);
        resetEncoders();
    }
    private void pause(int ms)
    {
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);
        sleep(ms);
    }
    private void rev(int ms)
    {
        outtake_Motor.setPower(1);
        reversed_outtake_Motor.setPower(1);
        pause(ms);
    }
    private void launch(int ms)
    {
        Is.setPower(-1);
        pause(ms);
    }
}
