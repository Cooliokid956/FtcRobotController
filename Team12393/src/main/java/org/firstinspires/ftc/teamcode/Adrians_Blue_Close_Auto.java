package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous
public class Adrians_Blue_Close_Auto extends LinearOpMode {
    private DcMotorEx frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor, reversed_outtake_Motor, outtake_Motor, intake_Motor;
    //     private CRServo Is, rs;, trigger;
    private Servo trigger;

    DcMotorEx cylinder;

    int backLeftPosition = 0;
    int backRightPosition = 0;
    int frontLeftPosition = 0;
    int frontRightPosition = 0;

    int cylinderPosition = 0;
    int
            cylOffsetTicks = 48,
            cylChamberTicks = 96,
            cylTicks;
    boolean
            cylShoot, // (In, Out)
            lTrigDown,
            rTrigDown;

    @Override
    public void runOpMode() {
        // Webcam -> webcam
        // ControlHub -> Motors -> 1:Hex 40:1 OuttakeMotor1, 2:GoBilda 5202/3/4? frm 3: GoBilda 5202/3/4? brm
        // ExpansionHub -> Motors -> 0: GoBilda 5202/3/4? blm 1: GoBilda 5202/3/4? flm 2: Hex Motor 40:1 IntakeMotor 3: Go bilda 5202/3/4 outtakeMotor2
        reversed_outtake_Motor = hardwareMap.get(DcMotorEx.class, "OuttakeMotor2");
        outtake_Motor = hardwareMap.get(DcMotorEx.class, "OuttakeMotor1");
        intake_Motor = hardwareMap.get(DcMotorEx.class, "IntakeMotor" );

        trigger = hardwareMap.get(Servo.class,"trigger");
        trigger.setDirection(Servo.Direction.REVERSE);
        outtake_Motor.setDirection(DcMotorSimple.Direction.REVERSE);

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

        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeftMotor.setTargetPosition(0);
        frontRightMotor.setTargetPosition(0);
        backLeftMotor.setTargetPosition(0);
        backRightMotor.setTargetPosition(0);

        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontLeftMotor.setPower(0.5);
        frontRightMotor.setPower(0.5);
        backLeftMotor.setPower(0.5);
        backRightMotor.setPower(0.5);

        cylinder = hardwareMap.get(DcMotorEx.class, "rotateMotor");
        cylinder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        cylinder.setTargetPosition(0);
        cylinder.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        cylinder.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        cylinder.setPower(.5);
        cylinder.setPositionPIDFCoefficients(20);

        //Auto starts here
        waitForStart();
        moveForward(3500);
        pause(1500);
        rev(750);
        launch();
        loadNext();
        rev(750);
        launch();
        loadNext();
        rev(750);
        launch();
        goToIntake();

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

    private void getToPosition(DcMotor x)
    {
        while(x.isBusy())
        {
            telemetry.addData("Front Left Position", frontLeftPosition);
            telemetry.addData("Front Right Position", frontRightPosition);
            telemetry.addData("Back Left Position", backLeftPosition);
            telemetry.addData("Back Right Position", backRightPosition);
            telemetry.update();
        }
    }

    private void setTargetPositions(int fl, int fr, int bl, int br)
    {
        frontLeftMotor.setTargetPosition(frontLeftPosition += fl);
        frontRightMotor.setTargetPosition(frontRightPosition += fr);
        backLeftMotor.setTargetPosition(backLeftPosition += bl);
        backRightMotor.setTargetPosition(backRightPosition += br);
        getToPosition(frontLeftMotor);
    }
    private void pause(int ms)
    {
        sleep(ms);
    }
    private void rev(int ms)
    {
        outtake_Motor.setPower(1);
        reversed_outtake_Motor.setPower(1);
        pause(ms);
        outtake_Motor.setPower(0);
        reversed_outtake_Motor.setPower(0);
    }
    private void launch()
    {
        getToPosition(cylinder);
        getToPosition(cylinder);
        trigger.setPosition(-1);
        pause(1000);
        trigger.setPosition(1);
        pause(500);
    }

    private void loadNext()
    {
        cylinder.setTargetPosition(cylinderPosition += 96);
        getToPosition(cylinder);
    }

    private void goToIntake()
    {
        cylinder.setTargetPosition(cylinderPosition += 48);
        getToPosition(cylinder);
    }
}