package org.firstinspires.ftc.teamcode.visionSoftware;

import android.util.Size;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.concurrent.TimeUnit;

public final class cam {
    public static VisionPortal getPortal(HardwareMap hw, String webcamName, AprilTagProcessor aprilTagPro) {
        return new VisionPortal.Builder()
                .setCameraResolution(new Size(1280, 720))
                .setCamera(hw.get(WebcamName.class, webcamName))
                .addProcessor(aprilTagPro)
                .enableLiveView(true)
                .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
                .enableLiveView(false)/// NOTE: I"M NOT USING CAMERA CURRENTLY switch it to true when using camera
                .build();
    }
    public static AprilTagProcessor getAprilTagProcessor() {
        AprilTagProcessor pro =  new AprilTagProcessor.Builder()
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setDrawTagOutline(true)
                .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
                .setLensIntrinsics(556.853, 556.853, 668.055, 426.931)//for microsoft hd camera
                .build();
        pro.setDecimation(1.0f);
        return pro;
    }
    public static void aprilTagOptimization(VisionPortal visionPortal) {
        if(visionPortal.getCameraState() == VisionPortal.CameraState.CAMERA_DEVICE_READY) {
            ExposureControl exposure = visionPortal.getCameraControl(ExposureControl.class);
            GainControl gain = visionPortal.getCameraControl(GainControl.class);
            exposure.setMode(ExposureControl.Mode.Manual);
            exposure.setExposure(10, TimeUnit.MILLISECONDS);
            gain.setGain(250);
        }

    }

}
