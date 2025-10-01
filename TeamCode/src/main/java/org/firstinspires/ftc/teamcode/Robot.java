package org.firstinspires.ftc.teamcode;

import static com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType.getMotorType;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class Robot {
    private DcMotorEx flm;
    private DcMotorEx frm;
    private DcMotorEx blm;
    private DcMotorEx brm;
    private ElapsedTime runtime = new ElapsedTime();
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

    private double ticksPerRotation;
    private IMU imu;
    LinearOpMode opMode;
    public void init(HardwareMap hwMap, /*Optional<LinearOpMode> opMode*/LinearOpMode opMode) {
        flm = hwMap.get(DcMotorEx.class, "fl");
        flm.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        //this.opMode = opMode.orElse(null);
        this.opMode = opMode;
        frm = hwMap.get(DcMotorEx.class, "fr");
        frm.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        blm = hwMap.get(DcMotorEx.class, "bl");
        blm.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        brm = hwMap.get(DcMotorEx.class, "br");
        brm.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

//        flm.setDirection(DcMotor.Direction.REVERSE);
//        blm.setDirection(DcMotor.Direction.REVERSE);
//        frm.setDirection(DcMotor.Direction.FORWARD);
//        brm.setDirection(DcMotor.Direction.REVERSE);
        //new| have to turn bot around
        flm.setDirection(DcMotor.Direction.REVERSE);//Motor flipped for some reason
        blm.setDirection(DcMotor.Direction.FORWARD);
        frm.setDirection(DcMotor.Direction.REVERSE);//Motors flipped; have to keep consistent
        brm.setDirection(DcMotor.Direction.REVERSE);

        ticksPerRotation = flm.getMotorType().getTicksPerRev();

        imu = hwMap.get(IMU.class, "imu");
        RevHubOrientationOnRobot RevOrientation = new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.UP, RevHubOrientationOnRobot.UsbFacingDirection.FORWARD);
        imu.initialize(new IMU.Parameters(RevOrientation));

    }
    public void drive(boolean fieldCentric) {
        opMode.telemetry.speak("six and seven");
        double max;
        double frontLeftPower;
        double frontRightPower;
        double backLeftPower;
        double backRightPower;
        double speedMultiplier;
        //while(opMode.opModeIsActive()) {
        double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
        double y = Math.abs(opMode.gamepad1.left_stick_y) > 0.05 ? -opMode.gamepad1.left_stick_y:0;//Makes it easier to drive lateral
        double x = Math.abs(opMode.gamepad1.left_stick_x) > 0.05 ? opMode.gamepad1.left_stick_x:0;//Makes it easier to drive straight
        double rx = opMode.gamepad1.right_stick_x;
        if(fieldCentric) {
            if(opMode.gamepad1.aWasReleased()) {
                imu.resetYaw();
                botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
            }
            double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
            double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);
            frontLeftPower = rotY + rotX + rx;
            //double frontRightPower = rotY - rotX - rx;
            frontRightPower = rotY - rotX + rx;
            // double backLeftPower = rotY - rotX + rx;
            backLeftPower = rotY - rotX - rx;
            backRightPower = rotY + rotX - rx;

            //max =  Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1); works but over-normalizes
        } else {

            double axial = y;
            double lateral = x;
            double yaw = rx;
            frontLeftPower = axial + lateral + yaw;
//            double frontRightPower = axial - lateral - yaw;
            frontRightPower = axial - lateral + yaw;
//            double backLeftPower = axial - lateral - yaw;
            backLeftPower = axial - lateral - yaw;
            backRightPower = axial + lateral - yaw;
        }
        max = Math.max(1.0, Math.abs(frontLeftPower));
        max = Math.max(max, Math.abs(frontRightPower));
        max = Math.max(max, Math.abs(backLeftPower));
        max = Math.max(max, Math.abs(backRightPower));
        speedMultiplier = 1.0 - (opMode.gamepad1.right_trigger * 0.7);
        if(max > 1.0) {
            frontLeftPower /= max;
            frontRightPower /= max;
            backLeftPower /= max;
            backRightPower /= max;
        }
        frontLeftPower *= speedMultiplier;
        frontRightPower *= speedMultiplier;
        backLeftPower *= speedMultiplier;
        backRightPower *= speedMultiplier;
        // Uncomment the following code to test your motor directions.
        // Each button should make the corresponding motor run FORWARD.
        //   1) First get all the motors to take to correct positions on the robot
        //      by adjusting your Robot Configuration if necessary.
        //   2) Then make sure they run in the correct direction by modifying the
        //      the setDirection() calls above.
        // Once the correct motors move in the correct direction re-comment this code.
//            frontLeftPower  = gamepad1.x ? 1.0 : 0.0;  // X gamepad
//            backLeftPower   = gamepad1.a ? 1.0 : 0.0;  // A gamepad
//            frontRightPower = gamepad1.y ? 1.0 : 0.0;  // Y gamepad
//            backRightPower  = gamepad1.b ? 1.0 : 0.0;  // B gamepad

        setMotorSpeed(frontLeftPower, frontRightPower, backLeftPower, backRightPower);
        opMode.telemetry.addData("Bot Heading:", botHeading);
        opMode.telemetry.addData("Gamepad(Left V, Left H, Right H):",y + " " + x + " " + rx);
        opMode.telemetry.addData("Status", "Run Time: " + runtime.toString());
        opMode.telemetry.addData("Speed Multiplier: ", speedMultiplier);
        opMode.telemetry.addData("Front left/Right", "%4.2f, %4.2f", frontLeftPower, frontRightPower);
        opMode.telemetry.addData("Back  left/Right", "%4.2f, %4.2f", backLeftPower, backRightPower);
        opMode.telemetry.update();

        //}
    }
    public void encoderDrive(double speed, double frontLeftInches, double frontRightInches, double backLeftInches, double backRightInches, double timeout) {
        encoderReset();
        int newFrontLeftTarget;
        int newFrontRightTarget;
        int newBackLeftTarget;
        int newBackRightTarget;

        if(opMode.opModeIsActive()) {
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
            while(opMode.opModeIsActive() && (runtime.seconds() < timeout) && (flm.isBusy() || frm.isBusy() || blm.isBusy() || brm.isBusy())) {
                //Display to driver
                opMode.telemetry.addData("Running to", "frontLeft: %7d frontRight: %7d \nbackLeft: %7d backRight: %7d", newFrontLeftTarget, newFrontRightTarget, newBackLeftTarget, newBackRightTarget);
                opMode.telemetry.addData("Currently at ", "frontLeft: %7d frontRight: %7d \nbackLeft: %7d backRight: %7d", flm.getCurrentPosition(), frm.getCurrentPosition(), blm.getCurrentPosition(), brm.getCurrentPosition());
                opMode.telemetry.update();
            }
            flm.setPower(0);
            frm.setPower(0);
            blm.setPower(0);
            brm.setPower(0);
            flm.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            frm.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            blm.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            brm.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            opMode.sleep(250);
        }
    }

    public IMU getImu() {
        return imu;
    }
    public void setMotorSpeed(double flp, double frp, double blp, double brp) {
        flm.setPower(flp);
        frm.setPower(frp);
        blm.setPower(blp);
        brm.setPower(brp);
    }
    public double getTicksPerRotation() {
        return ticksPerRotation;
    }

    public double[] getMotorRotations() {
        return new double[] {
                flm.getCurrentPosition()/ticksPerRotation,
                frm.getCurrentPosition()/ticksPerRotation,
                blm.getCurrentPosition()/ticksPerRotation,
                brm.getCurrentPosition()/ticksPerRotation
        };
    }
    public double getHeading(AngleUnit angleUnit) {
        return imu.getRobotYawPitchRollAngles().getYaw();
    }
    public ArrayList<TestItem> getTests() {
        ArrayList<TestItem> tests = new ArrayList<>();
        tests.add(new TestMotor("Front Left Motor", 0.5, flm));
        tests.add(new TestMotor("Front Right Motor", 0.5, frm));
        tests.add(new TestMotor("Back Left Motor", 0.5, blm));
        tests.add(new TestMotor("Back Right Motor", 0.5, brm));
        return tests;
    }

    public void encoderReset() {
        flm.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        frm.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        blm.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        brm.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        flm.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        frm.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        blm.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        brm.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
    }
}
