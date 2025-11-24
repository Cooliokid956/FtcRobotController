package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

@Autonomous(name = "AprilDetection", group = "auto")
public class AprilDetection extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private AprilTagProcessor aprilTagProcessor;
    private VisionPortal visionPortal;
    List<AprilTagDetection> currentDetections;
    AprilTagDetection tag;
    public void runOpMode() {
        WebcamName webcamName = hardwareMap.get(WebcamName.class, "webcam");
        aprilTagProcessor = new AprilTagProcessor.Builder()
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setDrawTagOutline(true)
                .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
                .setLensIntrinsics(556.853 , 556.853,668.055, 426.931)
                .build();
        visionPortal = new VisionPortal.Builder()
                .setCamera(webcamName)
                .addProcessor(aprilTagProcessor)
                .enableLiveView(true)
                .build();
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();
        visionPortal.resumeLiveView();
        runtime.reset();
        while(opModeIsActive()) {
            currentDetections = aprilTagProcessor.getDetections();
            for(AprilTagDetection detection : currentDetections) {
                switch(detection.id) {
                    case 20://blue goal
                        tag = detection;//set to what you want it to be
                    case 21://GPP

                    case 22://PGP

                    case 23://PPG

                    case 24://red goal
                        //tag = detection;
                }
            }
        }
    }
}
