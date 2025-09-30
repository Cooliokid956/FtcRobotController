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
    Robot robot = new Robot();

    public void runOpMode() {
        telemetry.speak("six and seven");
        robot.init(hardwareMap, this);

        while (opModeIsActive()) {
            flm = hardwareMap.get(DcMotorEx.class, "fl");
            frm = hardwareMap.get(DcMotorEx.class, "fr");
            blm = hardwareMap.get(DcMotorEx.class, "bl");
            brm = hardwareMap.get(DcMotorEx.class, "br");
            telemetry.addData("Status", "Initialized");
            telemetry.addData("Starting at", "frontLeft: %7d frontRight: %7d \nbackLeft: %7d backRight: %7d", flm.getCurrentPosition(), frm.getCurrentPosition(), blm.getCurrentPosition(), brm.getCurrentPosition());
            telemetry.update();
            waitForStart();
            runtime.reset();
            //Autonomous Path Here
            robot.encoderDrive(DRIVE_SPEED, 12, 12, 12, 12, 4.0);//forward
            robot.encoderDrive(TURN_SPEED, 12, 0, 0, -12, 4.0);//turn right
            robot.encoderDrive(DRIVE_SPEED, 24, 24, 24, 24, 4.0);//forward
            robot.encoderDrive(DRIVE_SPEED, 0, 12, -12, 0, 4.0);//turn lefts
            telemetry.addData("Path", "Complete");
            telemetry.update();
            sleep(1000);  // pause to display final telemetry message.
        }
    }
}