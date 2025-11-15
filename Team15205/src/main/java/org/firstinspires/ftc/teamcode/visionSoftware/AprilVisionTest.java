package org.firstinspires.ftc.teamcode.visionSoftware;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.Camera;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraControls;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.CameraControl;
import org.firstinspires.ftc.teamcode.Autonomous.AprilTagAuto;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import java.util.List;

@Autonomous(name = "AprilTest", group = "OpModes")
@Disabled
public class AprilVisionTest extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private AprilTagProcessor aprilTagProcesor;
    private VisionPortal visionPortal;
    List<AprilTagDetection> currentDetections;

    public void runOpMode() {
        boolean navigation = false;
        WebcamName webcamName = hardwareMap.get(WebcamName.class, "webcam");
        aprilTagProcesor = new AprilTagProcessor.Builder()
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setDrawTagOutline(true)
                .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
                .setLensIntrinsics(556.853, 556.853, 668.055, 426.931)
                .build();
        visionPortal = new VisionPortal.Builder()
                .setCameraResolution(new Size(1280, 720))
                .setCamera(webcamName)
                .addProcessor(aprilTagProcesor)
                .enableLiveView(true)
                .build();

        if(visionPortal.getCameraState() == VisionPortal.CameraState.CAMERA_DEVICE_READY) {
            CameraControl webcamControl = visionPortal.getCameraControl(CameraControl.class);

        }


        telemetry.addData("Status", "Initialized");
        AprilTagAuto.Tag tag = null;
        AprilTagAuto.Tag side = null;
        AprilTagDetection navTag;
        telemetry.update();
        waitForStart();
        visionPortal.resumeLiveView();
        runtime.reset();
        //first and foremost move the bot to either red or blue
        while(opModeIsActive()) {
            currentDetections = aprilTagProcesor.getDetections();
            for(AprilTagDetection detection : currentDetections) {
                switch(detection.id) {
                    case 20:
                        if(side == null) side = AprilTagAuto.Tag.BLUE;
                        navTag = detection;
                        break;
                    case 21:
                        tag = AprilTagAuto.Tag.GPP;
                        break;
                    case 22:
                        tag = AprilTagAuto.Tag.PGP;
                        break;
                    case 23:
                        tag = AprilTagAuto.Tag.PPG;
                        break;
                    case 24:
                        if(side == null) side = AprilTagAuto.Tag.RED;
                        navTag = detection;
                        break;
                    default:
                }
            }
            if(side == AprilTagAuto.Tag.RED || side == AprilTagAuto.Tag.BLUE) {
                navigation = true;
            }
        }
    }
}


