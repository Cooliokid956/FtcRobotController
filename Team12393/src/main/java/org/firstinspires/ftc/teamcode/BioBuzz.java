package org.firstinspires.ftc.teamcode;

import static java.lang.Math.abs;
import static java.lang.Math.max;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class BioBuzz extends OpMode {
    private boolean liftUp = false;
    private boolean openBasket = false;
    private boolean lastLeftBumper = false;
    private boolean lastgamepadY = false;
    private DcMotorEx backRightMotor;
    private DcMotorEx backLeftMotor;
    private DcMotorEx frontRightMotor;
    private DcMotorEx frontLeftMotor;
    private DcMotorEx intakeMotor;
    private DcMotorEx launchMotor;
    private DcMotorEx liftMotor;
    private DcMotorEx shooterIntakeMotor;
    private CRServo leftServo;
    private CRServo rightServo;

    private Servo basketServo;


    @Override
    public void init() {
        frontLeftMotor = hardwareMap.get(DcMotorEx.class, "frontLeft");
        frontRightMotor = hardwareMap.get(DcMotorEx.class, "frontRight");
        backLeftMotor = hardwareMap.get(DcMotorEx.class, "backLeft");
        backRightMotor = hardwareMap.get(DcMotorEx.class, "backRight");
        intakeMotor = hardwareMap.get(DcMotorEx.class, "im");
        liftMotor = hardwareMap.get(DcMotorEx.class,"lm");
        shooterIntakeMotor = hardwareMap.get(DcMotorEx.class, "sim");
        launchMotor = hardwareMap.get(DcMotorEx.class,"launchm");

        leftServo = hardwareMap.get(CRServo.class, "ls");
        rightServo = hardwareMap.get(CRServo.class, "rs");
        basketServo = hardwareMap.get(Servo.class, "bs");

        leftServo.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        liftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        shooterIntakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        launchMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    @Override
    public void loop() {

        //Intake System with the servo and Intake Motor
        if (gamepad1.right_bumper){
            leftServo.setPower(1);
            rightServo.setPower(1);
            intakeMotor.setPower(1);
        } else {
            leftServo.setPower(0);
            rightServo.setPower(0);
            intakeMotor.setPower(0);
        }

        //Lift Mechanism
        boolean leftBumper = gamepad1.left_bumper;

        if (leftBumper && !lastLeftBumper) {
            liftUp = !liftUp;
        }
        lastLeftBumper = leftBumper;

        boolean gamepadY = gamepad1.y;

        if (gamepadY && !lastgamepadY) {
            openBasket = !openBasket;
        }
        lastgamepadY = gamepadY;

        basketServo.setPosition(openBasket ? 0 : .5);

        liftMotor.setTargetPosition(liftUp ? 680 : 0);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotor.setPower(.3);


        //Shooter Mechanism
        shooterIntakeMotor.setPower(gamepad1.right_trigger >= 0.25 ? 1 : 0);
        launchMotor.setPower(gamepad1.left_trigger >= 0.25 ? 1 : 0);

        double
                y = gamepad1.left_stick_y,
                x = -gamepad1.left_stick_x,
                turn = gamepad1.right_stick_x,
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
        telemetry.addData("Lift Position", liftMotor.getCurrentPosition());
        telemetry.addData("going up", liftUp);
        telemetry.addData("open basket", gamepad1.y);
        telemetry.addData("shooting", launchMotor.getPower());
    }
}
