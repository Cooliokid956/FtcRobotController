package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.visionSoftware.cam;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;
@Autonomous(name = "AprilTag", group = "OpModes")
public class AprilTagAuto extends LinearOpMode {
    public enum Tag {
        GPP,
        PGP,
        PPG,
        BLUE,
        RED
    }
    public enum Nav {
        RED,
        BLUE
    }
    private ElapsedTime runtime = new ElapsedTime();
    private VisionPortal visionPortal;
    List<AprilTagDetection> currentDetections;
    DcMotorEx flm;
    DcMotorEx frm;
    DcMotorEx blm;

    // Adjust these numbers to suit your robot.
    final double DESIRED_DISTANCE = 12.0; //  this is how close the camera should get to the target (inches)

    //  Set the GAIN constants to control the relationship between the measured position error, and how much power is
    //  applied to the drive motors to correct the error.
    //  Drive = Error * Gain    Make these values smaller for smoother control, or larger for a more aggressive response.
    final double SPEED_GAIN  =  0.02  ;   //  Forward Speed Control "Gain". e.g. Ramp up to 50% power at a 25 inch error.   (0.50 / 25.0)
    final double STRAFE_GAIN =  0.015 ;   //  Strafe Speed Control "Gain".  e.g. Ramp up to 37% power at a 25 degree Yaw error.   (0.375 / 25.0)
    final double TURN_GAIN   =  0.01  ;   //  Turn Control "Gain".  e.g. Ramp up to 25% power at a 25 degree error. (0.25 / 25.0)

    final double MAX_AUTO_SPEED = 0.5;   //  Clip the approach speed to this max value (adjust for your robot)
    final double MAX_AUTO_STRAFE= 0.5;   //  Clip the strafing speed to this max value (adjust for your robot)
    final double MAX_AUTO_TURN  = 0.3;   //  Clip the turn speed to this max value (adjust for your robot)
    DcMotorEx brm;
    public void runOpMode() {
        telemetry.speak("six and seven");
        Robot robot = new Robot();
        robot.init(hardwareMap, this);
        Nav navigation = null;
        double  drive           = 0;        // Desired forward power/speed (-1 to +1)
        double  strafe          = 0;        // Desired strafe power/speed (-1 to +1)
        double  turn            = 0;        // Desired turning power/speed (-1 to +1)
        AprilTagProcessor aprilPro = cam.getAprilTagProcessor();
        visionPortal = cam.getPortal(hardwareMap, "webcam", aprilPro);
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();
        visionPortal.resumeLiveView();
        runtime.reset();
        cam.aprilTagOptimization(visionPortal);

//old
//        flm.setDirection(DcMotor.Direction.REVERSE);
//        blm.setDirection(DcMotor.Direction.REVERSE);
//        frm.setDirection(DcMotor.Direction.FORWARD);
//        brm.setDirection(DcMotor.Direction.REVERSE);
            //new| have to turn bot around
//            flm.setDirection(DcMotor.Direction.REVERSE);//Motor flipped for some reason
//            blm.setDirection(DcMotor.Direction.FORWARD);
//            frm.setDirection(DcMotor.Direction.REVERSE);//Motors flipped; have to keep consistent
//            brm.setDirection(DcMotor.Direction.REVERSE);
            Tag tag = null;
            Tag side = null;
            AprilTagDetection navTag = null;
            //first and foremost move the bot to either red or blue
            while(opModeIsActive()) {
                currentDetections = aprilPro.getDetections();
                if(currentDetections != null) {
                    for (AprilTagDetection detection : currentDetections) {
                        if (detection == null || detection.ftcPose == null) {
                            continue;
                        }
                        switch (detection.id) {
                            case 20:
                                if (side == null) side = Tag.BLUE;
                                navTag = detection;
                                break;
                            case 21:
                                tag = Tag.GPP;
                                break;
                            case 22:
                                tag = Tag.PGP;
                                break;
                            case 23:
                                tag = Tag.PPG;
                                break;
                            case 24:
                               // if (side == null) side = Tag.RED;
                                break;
                            default:
                        }
                    }
                }
                ////depending on the use change this
                if (side == Tag.BLUE) {
                    navigation = Nav.BLUE;
                }
                else if(side == Tag.RED) {
                    navigation = Nav.RED;
                }
                if(navigation == null) {
                    //code to find tag and reposition according to the next pose
                    //turns around slowly
                    //temporarily without rotating camera
                    robot.setMotorSpeed(-0.3, 0.3, 0.3,-0.3 );
                    sleep(10);
                    /*
                        With rotating motor however
                        need to set a variable that keeps track of yaw based on the servo's position(Either using a continuos
                        motor or one that keeps track of degrees that has limits but needs logic.)
                        and use that to estimate the robots coordinates on the field and turn it
                     */
                } else if(navigation == Nav.BLUE) {
                    /*
                    robot.setMotorSpeed(0, 0, 0, 0);
                    double  rangeError      = (navTag.ftcPose.range - DESIRED_DISTANCE);
                    double  headingError    = navTag.ftcPose.bearing;
                    double  yawError        = navTag.ftcPose.yaw;

                    // Use the speed and turn "gains" to calculate how we want the robot to move.
                    drive  = Range.clip(rangeError * SPEED_GAIN, -MAX_AUTO_SPEED, MAX_AUTO_SPEED);
                    turn   = Range.clip(headingError * TURN_GAIN, -MAX_AUTO_TURN, MAX_AUTO_TURN) ;
                    strafe = Range.clip(-yawError * STRAFE_GAIN, -MAX_AUTO_STRAFE, MAX_AUTO_STRAFE);
                    telemetry.addData("Auto","Drive %5.2f, Strafe %5.2f, Turn %5.2f ", drive, strafe, turn);
                    robot.drive(drive, strafe, turn);
                    sleep(10);
                    /*

                     */
                     robot.faceTag(navTag);
                    // code for doing what you want on blue side
                    /*
                    For right now I want to move backwards along the line so need to orient with tag
                     */
                } else if(navigation == Nav.RED) {
                    double  rangeError      = (navTag.ftcPose.range - DESIRED_DISTANCE);
                    double  headingError    = navTag.ftcPose.bearing;
                    double  yawError        = navTag.ftcPose.yaw;

                    // Use the speed and turn "gains" to calculate how we want the robot to move.
                    drive  = Range.clip(rangeError * SPEED_GAIN, -MAX_AUTO_SPEED, MAX_AUTO_SPEED);
                    turn   = Range.clip(headingError * TURN_GAIN, -MAX_AUTO_TURN, MAX_AUTO_TURN) ;
                    strafe = Range.clip(-yawError * STRAFE_GAIN, -MAX_AUTO_STRAFE, MAX_AUTO_STRAFE);
                    telemetry.addData("Auto","Drive %5.2f, Strafe %5.2f, Turn %5.2f ", drive, strafe, turn);
                    robot.drive(drive, strafe, turn);
                    sleep(10);
                    //code for doing what you want on red side
                    /*
                     For right now I want to move backwards along the line so need to orient with tag
                     */
                } // can alter these and change the sequence with linear OpMode and using both the red and blue april tags for complex navigations
                //try to use encoder movement for precision or roadrunner to save time when build is finished
                //when you make sure that the robot is oriented, at the right distance, and has three balls
                //try to have scoring code outside of main logic for compatibility/general use

                //currentDetections.clear();
            }
        telemetry.update();
    }
    // find yaw offset of april tag
    // move the bots heading via imu to match the yaw of april tag from within +/- 10 degrees
    //move from heading of robot to specific coordinates in relation to april tag
    //if april tag isn't found rotate the robot slowly until it is
}
