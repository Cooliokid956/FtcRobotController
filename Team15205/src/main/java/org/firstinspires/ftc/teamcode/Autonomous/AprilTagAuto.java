package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;
@Disabled
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
    private AprilTagProcessor aprilTagProcesor;
    private VisionPortal visionPortal;
    List<AprilTagDetection> currentDetections;
    DcMotorEx flm;
    DcMotorEx frm;
    DcMotorEx blm;
    DcMotorEx brm;
    public void runOpMode() {
        telemetry.speak("six and seven");
        Robot robot = new Robot();
        robot.init(hardwareMap, this);
        Nav navigation = null;
        while (opModeIsActive()) {
            flm = hardwareMap.get(DcMotorEx.class, "fl");
            frm = hardwareMap.get(DcMotorEx.class, "fr");
            blm = hardwareMap.get(DcMotorEx.class, "bl");
            brm = hardwareMap.get(DcMotorEx.class, "br");

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
            WebcamName webcamName = hardwareMap.get(WebcamName.class, "webcam");
            aprilTagProcesor = AprilTagProcessor.easyCreateWithDefaults();
            visionPortal = VisionPortal.easyCreateWithDefaults(webcamName, aprilTagProcesor);
            telemetry.addData("Status", "Initialized");
            Tag tag = null;
            Tag side = null;
            AprilTagDetection navTag;
            telemetry.update();
            waitForStart();
            runtime.reset();
            //first and foremost move the bot to either red or blue
            while(opModeIsActive()) {
                currentDetections = aprilTagProcesor.getDetections();
                for(AprilTagDetection detection : currentDetections) {
                    switch(detection.id) {
                        case 20:
                            if(side == null) side = Tag.BLUE;
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
                            if(side == null) side = Tag.RED;
                            break;
                        default:
                    }
                }
                ////depending on the use change this
                if(side == Tag.RED) {
                    navigation = Nav.RED;
                } else if (side == Tag.BLUE) {
                    navigation = Nav.BLUE;
                }
                if(tag == null && side == null && currentDetections.isEmpty() && navigation == null) {
                    //code to find tag and reposition according to the next pose
                    //turns around slowly
                    //temporarily without rotating camera
                    robot.setMotorSpeed(0.3, -0.3, 0.3,-0.3 );
                    /*
                        With rotating motor however
                        need to set a variable that keeps track of yaw based on the servo's position(Either using a continuos
                        motor or one that keeps track of degrees that has limits but needs logic.)
                        and use that to estimate the robots coordinates on the field and turn it
                     */
                } else if(navigation == Nav.BLUE) {
                    // code for doing what you want on blue side
                    /*
                    For right now I want to move backwards along the line so need to orient with tag
                     */
                } else if(navigation == Nav.RED) {
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
        }
    }
    // find yaw offset of april tag
    // move the bots heading via imu to match the yaw of april tag from within +/- 10 degrees
    //move from heading of robot to specific coordinates in relation to april tag
    //if april tag isn't found rotate the robot slowly until it is
}
