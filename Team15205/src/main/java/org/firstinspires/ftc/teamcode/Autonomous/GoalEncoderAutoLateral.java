package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Robot;


@Autonomous(name = "GoalEncoderLateral", group = "OpModes")
public class GoalEncoderAutoLateral extends LinearOpMode {
//    public enum Seq{
//        ONE,
//        TWO,
//        THREE
//    }
    private ElapsedTime runtime = new ElapsedTime();

    DcMotorEx flm;
    DcMotorEx frm;
    DcMotorEx blm;
    DcMotorEx brm;
//    // Calculate the COUNTS_PER_INCH for your specific drive train.
//    // Go to your motor vendor website to determine your motor's COUNTS_PER_MOTOR_REV
//    // For external drive gearing, set DRIVE_GEAR_REDUCTION as needed.
//    // For example, use a value of 2.0 for a 12-tooth spur gear driving a 24-tooth spur gear.
//    // This is gearing DOWN for less speed and more torque.
//    // For gearing UP, use a gear ratio less than 1.0. Note this will affect the direction of wheel rotation.
//    static final double     COUNTS_PER_MOTOR_REV    = 537.7 ;    // eg: TETRIX Motor Encoder
//    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // No External Gearing.
//    static final double     WHEEL_DIAMETER_INCHES   = 3.77953 ;     // For figuring circumference
//    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
//            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 0.6;
    static final double STRAFE_SPEED = 1.0;
    static final double     TURN_SPEED              = 0.5;
    Robot robot = new Robot();
//
    public void runOpMode() {
        telemetry.speak("six and seven");
        robot.init(hardwareMap, this);
        flm = hardwareMap.get(DcMotorEx.class, "fl");
        frm = hardwareMap.get(DcMotorEx.class, "fr");
        blm = hardwareMap.get(DcMotorEx.class, "bl");
        brm = hardwareMap.get(DcMotorEx.class, "br");
        telemetry.addData("Status", "Initialized");
        telemetry.addData("IF RED TURN ROBOT BACKWARDS", "");
        telemetry.addData("Starting at", "frontLeft: %7d frontRight: %7d \nbackLeft: %7d backRight: %7d", flm.getCurrentPosition(), frm.getCurrentPosition(), blm.getCurrentPosition(), brm.getCurrentPosition());
        telemetry.addData("FOR BLUE ALLIANCE POSITION BACKWARDS", "");
        telemetry.update();
        waitForStart();
        runtime.reset();
        //Autonomous Path Here
        /*
        robot.encoderDrive(DRIVE_SPEED, 12, -12, -12, 12, 4.0);//move right
        sleep(350);
        robot.encoderDrive(DRIVE_SPEED, 12, 12, 12, 12, 4.0);//forward
        sleep(350);
        robot.encoderDrive(TURN_SPEED, 12, -12, 12, -12, 4.0);//turn right
        sleep(350);
        robot.encoderDrive(TURN_SPEED,-12, 12, -12, 12, 4.0);//turn left
        */
        robot.encoderDrive(STRAFE_SPEED, -48, 48, -48, 48, 20.0);//move right
        /*
        implement camera to scan the obelisk
        go forward set amount of distance to desired ball pattern
        go left or right depending on the color of our team
        turn certain amount of pre programmed rotation based on IMU
        turn on shooting motors in a sequence style and score
        //also set a default path that should work no matter what
         */

        robot.encoderDrive(DRIVE_SPEED, 12, 12, 12, 12, 5);
        robot.turnRobotTo(90, AngleUnit.DEGREES);

        telemetry.addData("Path", "Complete");
        telemetry.update();
          // pause to display final telemetry message.
    }
}