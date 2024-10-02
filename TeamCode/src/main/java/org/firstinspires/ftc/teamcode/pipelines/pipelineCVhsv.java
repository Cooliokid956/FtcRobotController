package org.firstinspires.ftc.teamcode.pipelines;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.Arrays;

public class pipelineCVhsv extends OpenCvPipeline {

    Telemetry telemetry;

    public pipelineCVhsv(Telemetry telemetry) {this.telemetry = telemetry;}

    public ArrayList<Double> hueAvg = new ArrayList<>(Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0));

    public Scalar selectedColor = new Scalar(0, 255, 0);
    public Scalar selectedColorR = new Scalar(255, 0, 0);
    public Scalar selectedColorG = new Scalar(0, 255, 0);
    public Scalar selectedColorB = new Scalar(0, 0, 255);

    public Rect rect;
    public String colorDetected = "nothing yet";
    public double absDiff(double value, double compValue){
        return Math.abs(value-compValue);
    }
    public double hueRect;

    Mat hsvMat = new Mat();
    Mat spareMat = new Mat();
    Mat outMat = new Mat();

    @Override
    public Mat processFrame(Mat input) {
        rect = new Rect(input.cols()/2-20, input.rows()/2-20, 40, 50);
        findRectangle(input);
        drawRectangles(input);

        telemetry.update();
        Imgproc.cvtColor(input, hsvMat, Imgproc.COLOR_RGB2HSV);

        Core.extractChannel(hsvMat, spareMat, 1);
        Core.bitwise_not(spareMat,outMat);
        Core.add(spareMat,outMat,spareMat);
        outMat = new Mat();
        Core.insertChannel(spareMat,hsvMat,1);
        Core.insertChannel(spareMat,hsvMat,2);


        Imgproc.cvtColor(hsvMat, outMat, Imgproc.COLOR_HSV2RGB);
        return outMat;
    }

    void findRectangle(Mat input) {
        Imgproc.cvtColor(input, hsvMat, Imgproc.COLOR_RGB2HSV);

        hueRect = getAvgHue(hsvMat, rect);

        if (hueRect>150) hueRect -= 180;
        hueAvg.remove(0);
        hueAvg.add(hueRect);

        hueRect=(hueAvg.get(0)+ hueAvg.get(1)+ hueAvg.get(2)+ hueAvg.get(3)+ hueAvg.get(4))/5.0;

        telemetry.addLine(String.valueOf(hueRect));
        telemetry.addLine(" ");
        telemetry.addLine(String.valueOf(hueAvg));
        telemetry.addLine(" ");
//        telemetry.addLine(" ");
//        telemetry.addLine(" ");
//        telemetry.addLine(" ");


    }

    protected double getAvgHue(Mat input, Rect rect) {
        Mat submat = input.submat(rect);
        Scalar color = Core.mean(submat);
        return color.val[0];
    }

    public void drawRectangles(Mat input) {
        double difference = absDiff(hueRect,0);
        colorDetected= "red";
        selectedColor = selectedColorR;
        if (difference>absDiff(hueRect,60)) {
            difference = absDiff(hueRect,60);
            colorDetected= "green";
            selectedColor = selectedColorG;
        }
        if (difference>absDiff(hueRect,120)) {
            colorDetected= "blue";
            selectedColor = selectedColorB;
        }
        Imgproc.rectangle(input, rect, selectedColor);
    }
}