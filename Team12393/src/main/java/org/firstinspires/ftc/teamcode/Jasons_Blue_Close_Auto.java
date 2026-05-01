package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
///NOT FINISHED
@Autonomous
public class Jasons_Blue_Close_Auto extends LinearOpMode {
    private DcMotorEx flm, frm, blm, brm, reversed_outtake_Motor, outtake_Motor, intake_Motor;
    //     private CRServo Is, rs;, trigger;
    private Servo trigger;

    private ElapsedTime runtime = new ElapsedTime();
    // Calculate the COUNTS_PER_INCH for your specific drive train.
    // Go to your motor vendor website to determine your motor's COUNTS_PER_MOTOR_REV
    // For external drive gearing, set DRIVE_GEAR_REDUCTION as needed.
    // For example, use a value of 2.0 for a 12-tooth spur gear driving a 24-tooth spur gear.
    // This is gearing DOWN for less speed and more torque.
    // For gearing UP, use a gear ratio less than 1.0. Note this will affect the direction of wheel rotation.
    static final double     COUNTS_PER_MOTOR_REV    = 537.6;    // eg: GoBilda 5203 Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // No External Gearing.
    static final double     WHEEL_DIAMETER_INCHES   = 3.77953 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = ((COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * Math.PI));

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

        flm = hardwareMap.get(DcMotorEx.class, "flm");
        frm = hardwareMap.get(DcMotorEx.class, "frm");
        blm = hardwareMap.get(DcMotorEx.class, "blm");
        brm = hardwareMap.get(DcMotorEx.class, "brm");

        flm.setDirection(DcMotorSimple.Direction.REVERSE);
        blm.setDirection(DcMotorSimple.Direction.REVERSE);

        flm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        brm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        blm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        flm.setTargetPosition(0);
        frm.setTargetPosition(0);
        blm.setTargetPosition(0);
        brm.setTargetPosition(0);

        encoderReset();

        cylinder = hardwareMap.get(DcMotorEx.class, "rotateMotor");
        cylinder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        cylinder.setTargetPosition(0);
        cylinder.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        cylinder.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        cylinder.setPower(.5);
        cylinder.setPositionPIDFCoefficients(20);

        //Auto starts here
        waitForStart();
        //               speed                         distance in inches of motors             timeout in seconds
        encoderDrive(1.0, 5, 5, 5, 5, 5);
        sleep(1000);
        flm.setPower(0);
        frm.setPower(0);
        blm.setPower(0);
        brm.setPower(0);
    }

    private void getToPosition(DcMotor x, double timeoutS)
    {
        ElapsedTime timer = new ElapsedTime();
        timer.reset();
        while(x.isBusy() && opModeIsActive() && timer.seconds() < timeoutS)
        {
            telemetry.addData("Front Left Position", frontLeftPosition);
            telemetry.addData("Front Right Position", frontRightPosition);
            telemetry.addData("Back Left Position", backLeftPosition);
            telemetry.addData("Back Right Position", backRightPosition);
            telemetry.update();
        }
    }
    public void encoderDrive(double speed, int frontLeftInches, int frontRightInches, int backLeftInches, int backRightInches, double timeout) {
        //if motors acting wonky refer to their direction

        ///encoderReset(); Put this method at the start of autonomous
        int DECELL_TICKS = 500;
        int newFrontLeftTarget;
        int newFrontRightTarget;
        int newBackLeftTarget;
        int newBackRightTarget;
        if(opModeIsActive()) {
            //Determines the target position
            newFrontLeftTarget = flm.getCurrentPosition() + (int)(frontLeftInches * COUNTS_PER_INCH);
            newFrontRightTarget = frm.getCurrentPosition() + (int)(frontRightInches * COUNTS_PER_INCH);
            newBackLeftTarget = blm.getCurrentPosition() + (int)(backLeftInches * COUNTS_PER_INCH);
            newBackRightTarget = brm.getCurrentPosition() + (int)(backRightInches * COUNTS_PER_INCH);
            double currentSpeed;
            flm.setTargetPosition(newFrontLeftTarget);
            frm.setTargetPosition(newFrontRightTarget);
            blm.setTargetPosition(newBackLeftTarget);
            brm.setTargetPosition(newBackRightTarget);
            //runs to target
            flm.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
            frm.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
            blm.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
            brm.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
            runtime.reset();//resets time and provides power
            flm.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
            frm.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
            blm.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
            brm.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while(opModeIsActive() && (runtime.seconds() < timeout) && (flm.isBusy() || frm.isBusy() || blm.isBusy() || brm.isBusy())) {
                //Display to driver
                int remainingTicks = Math.max(Math.max(Math.max(Math.abs(newFrontLeftTarget - flm.getCurrentPosition()), Math.abs(newFrontRightTarget - frm.getCurrentPosition())), Math.abs(newBackLeftTarget - blm.getCurrentPosition())), Math.abs(newBackRightTarget - brm.getCurrentPosition()));
                double rampUpSpeed = Math.min(speed, runtime.seconds() * 2);
                double rampDownSpeed = speed;
                if(remainingTicks < DECELL_TICKS) {
                    rampDownSpeed = speed * ((double)remainingTicks/ DECELL_TICKS);
                    rampDownSpeed = Math.max(rampDownSpeed, 0.15);
                }
                currentSpeed = Math.min(rampUpSpeed, rampDownSpeed);
                flm.setPower(currentSpeed);
                frm.setPower(currentSpeed);
                blm.setPower(currentSpeed);
                brm.setPower(currentSpeed);
                telemetry.addData("Running to", "frontLeft: %7d frontRight: %7d \nbackLeft: %7d backRight: %7d", newFrontLeftTarget, newFrontRightTarget, newBackLeftTarget, newBackRightTarget);
                telemetry.addData("Currently at ", "frontLeft: %7d frontRight: %7d \nbackLeft: %7d backRight: %7d", flm.getCurrentPosition(), frm.getCurrentPosition(), blm.getCurrentPosition(), brm.getCurrentPosition());
                telemetry.addData("Total Difference should be: ", "frontLeft: %7d frontRight: %7d \nbackLeft: %7d backRight: %7d", frontLeftInches, frontRightInches, backLeftInches, backRightInches);
                telemetry.update();
            }
            //turns off the encoders and removes targets
            sleep(250);
        }
    }

    void encoderReset() {
                flm.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
                frm.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
                blm.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
                brm.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

                flm.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
                frm.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
                blm.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
                brm.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
    }
    private void setTargetPositions(int fl, int fr, int bl, int br)
    {
        flm.setTargetPosition(frontLeftPosition += fl);
        frm.setTargetPosition(frontRightPosition += fr);
        blm.setTargetPosition(backLeftPosition += bl);
        brm.setTargetPosition(backRightPosition += br);
        getToPosition(flm, 5);
    }
    private void pause(int ms)
    {
        sleep(ms);
    }

    private void stopRevving() {
        outtake_Motor.setPower(0);
        reversed_outtake_Motor.setPower(0);
    }
    private void rev(int ms, String s)
    {
        double power = 0;
        if(s.equals("short")) {power = .43;}
        else {power = .60;}
        outtake_Motor.setPower(power);
        reversed_outtake_Motor.setPower(power);
        pause(ms);
    }
    private void launch()
    {
        getToPosition(cylinder,5 );
        getToPosition(cylinder,5 );
        trigger.setPosition(0);
        pause(1000);
        trigger.setPosition(1);
        pause(500);
        getToPosition(cylinder,5 );
        pause(200);
    }

    private void loadNext()
    {
        cylinder.setTargetPosition(cylinderPosition += 96);
        getToPosition(cylinder,5);
        pause(200);
    }

    private void goToIntake()
    {
        cylinder.setTargetPosition(cylinderPosition += 48);
        getToPosition(cylinder,5);
    }
}