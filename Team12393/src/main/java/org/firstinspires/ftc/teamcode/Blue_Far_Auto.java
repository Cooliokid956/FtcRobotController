package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.opMode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.VoltageSensor;

@Autonomous
public class Blue_Far_Auto extends LinearOpMode {
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
        /*frontLeftMotor.setVelocity(2250 / 10);
        frontRightMotor.setVelocity(2250 / 10);
        backLeftMotor.setVelocity(2250 / 10);
        backRightMotor.setVelocity(2250 / 10);*/


        //Auto starts here
        waitForStart();
        /* frontLeftMotor.setVelocity(2250 / 10);
        frontRightMotor.setVelocity(2250 / 10);
        backLeftMotor.setVelocity(2250 / 10);
        backRightMotor.setVelocity(2250 / 10);
        frontLeftMotor.setTargetPosition(256);
        frontRightMotor.setTargetPosition(256);
        backLeftMotor.setTargetPosition(256);
        backRightMotor.setTargetPosition(2056); */
        moveBackward();
        sleep(525);
        pause();
        turnRight();
        sleep(225);
        pause();
        rev();
        sleep(1500);
        launch();
        sleep(2500);
        pause();
        turnLeft();
        sleep(225);
        pause();
        moveBackward();
        sleep(450);
        turnLeft();
        sleep(450);





        sleep(1000);
        pause();
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);

    }

    private void moveForward() {
        frontLeftMotor.setPower(-0.5);
        frontRightMotor.setPower(-0.5);
        backLeftMotor.setPower(-0.5);
        backRightMotor.setPower(-0.5);
    }

    private void moveBackward() {
        frontLeftMotor.setPower(0.5);
        frontRightMotor.setPower(0.5);
        backLeftMotor.setPower(0.5);
        backRightMotor.setPower(0.5);
    }

    private void moveLeft() {
        frontLeftMotor.setPower(-0.5);
        frontRightMotor.setPower(-0.5);
        backLeftMotor.setPower(0.5);
        backRightMotor.setPower(0.5);
    }

    private void moveRight() {
        frontLeftMotor.setPower(0.5);
        frontRightMotor.setPower(0.5);
        backLeftMotor.setPower(-0.5);
        backRightMotor.setPower(-0.5);
    }

    private void turnRight()
    {
        frontLeftMotor.setPower(0.5);
        frontRightMotor.setPower(-0.5);
        backLeftMotor.setPower(0.5);
        backRightMotor.setPower(-0.5);
    }
    private void turnLeft()
    {
        frontLeftMotor.setPower(-0.5);
        frontRightMotor.setPower(0.5);
        backLeftMotor.setPower(-0.5);
        backRightMotor.setPower(0.5);
    }
    private void pause()
    {
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);
        sleep(200);
    }
    private void rev()
    {
        outtake_Motor.setPower(1);
        reversed_outtake_Motor.setPower(1);
    }
    private void launch()
    {
        outtake_Motor.setPower(1);
        reversed_outtake_Motor.setPower(1);
        Is.setPower(1);
    }

}
