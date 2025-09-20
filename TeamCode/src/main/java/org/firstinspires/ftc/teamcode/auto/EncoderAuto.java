package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Robot;


@Autonomous(name = "Encoder", group = "OpModes")
public class EncoderAuto extends LinearOpMode {
    public enum Seq{
        ONE,
        TWO,
        THREE
    }
    private ElapsedTime runtime = new ElapsedTime();

    DcMotorEx flm;
    DcMotorEx frm;
    DcMotorEx blm;
    DcMotorEx brm;
    // Calculate the COUNTS_PER_INCH for your specific drive train.
    // Go to your motor vendor website to determine your motor's COUNTS_PER_MOTOR_REV
    // For external drive gearing, set DRIVE_GEAR_REDUCTION as needed.
    // For example, use a value of 2.0 for a 12-tooth spur gear driving a 24-tooth spur gear.
    // This is gearing DOWN for less speed and more torque.
    // For gearing UP, use a gear ratio less than 1.0. Note this will affect the direction of wheel rotation.
    static final double     COUNTS_PER_MOTOR_REV    = 537.7 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // No External Gearing.
    static final double     WHEEL_DIAMETER_INCHES   = 3.77953 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 0.6;
    static final double     TURN_SPEED              = 0.5;


    public void runOpMode() {
        telemetry.speak("six and seven");
        Robot robot = new Robot();
        robot.init(hardwareMap);

        while (opModeIsActive()) {
            flm = hardwareMap.get(DcMotorEx.class, "");
            frm = hardwareMap.get(DcMotorEx.class, "");
            blm = hardwareMap.get(DcMotorEx.class, "");
            brm = hardwareMap.get(DcMotorEx.class, "");

            flm.setDirection(DcMotor.Direction.REVERSE);
            blm.setDirection(DcMotor.Direction.REVERSE);
            frm.setDirection(DcMotor.Direction.FORWARD);
            brm.setDirection(DcMotor.Direction.REVERSE);

            flm.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            frm.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            blm.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            brm.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

            flm.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            frm.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            blm.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            brm.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

            telemetry.addData("Status", "Initialized");
            telemetry.addData("Starting at", "frontLeft: %7d frontRight: %7d \nbackLeft: %7d backRight: %7d", flm.getCurrentPosition(), frm.getCurrentPosition(), blm.getCurrentPosition(), brm.getCurrentPosition());
            telemetry.update();

            waitForStart();
            runtime.reset();
            //Autonomous Path Here
            encoderDrive(DRIVE_SPEED, 12, 12, 12, 12, 4.0);
            encoderDrive(TURN_SPEED, 12, 0, 0, -12, 4.0);
            encoderDrive(DRIVE_SPEED, 24, 24, 24, 24, 4.0);
            encoderDrive(DRIVE_SPEED, 0, 12, -12, 0, 4.0);
            telemetry.addData("Path", "Complete");
            telemetry.update();
            sleep(1000);  // pause to display final telemetry message.
        }
    }
    public void encoderDrive(double speed, double frontLeftInches, double frontRightInches, double backLeftInches, double backRightInches, double timeout) {
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
            flm.setPower(Math.abs(speed));
            frm.setPower(Math.abs(speed));
            blm.setPower(Math.abs(speed));
            brm.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while(opModeIsActive() && (runtime.seconds() < timeout) && (flm.isBusy() || frm.isBusy() || blm.isBusy() || brm.isBusy())) {
                //Display to driver
                telemetry.addData("Running to", "frontLeft: %7d frontRight: %7d \nbackLeft: %7d backRight: %7d", newFrontLeftTarget, newFrontRightTarget, newBackLeftTarget, newBackRightTarget);
                telemetry.addData("Currently at ", "frontLeft: %7d frontRight: %7d \nbackLeft: %7d backRight: %7d", flm.getCurrentPosition(), frm.getCurrentPosition(), blm.getCurrentPosition(), brm.getCurrentPosition());
                telemetry.update();
            }
            flm.setPower(0);
            frm.setPower(0);
            blm.setPower(0);
            brm.setPower(0);
            flm.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            frm.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            blm.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            brm.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            sleep(250);
        }
    }

}