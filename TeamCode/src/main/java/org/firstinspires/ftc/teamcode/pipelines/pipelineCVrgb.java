package org.firstinspires.ftc.teamcode.pipelines;

//import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.Arrays;

public class pipelineCVrgb extends OpenCvPipeline {

//    Telemetry telemetry;

//    public pipelineCVrgb(Telemetry telemetry) {this.telemetry = telemetry;}

    public ArrayList<Double> hueAvg = new ArrayList<>(Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0));

    public Scalar selectedColor = new Scalar(0, 255, 0);
    public Scalar selectedColorR = new Scalar(255, 0, 0);
    public Scalar selectedColorG = new Scalar(0, 255, 0);
    public Scalar selectedColorB = new Scalar(0, 0, 255);

    public Scalar colors;

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
        rect = new Rect(input.cols()/2-10, input.rows()/2-50, 40, 50);
        findRectangle(input);
        drawRectangles(input);

//        telemetry.update();
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

        colors = getColors(input, rect);


/*        telemetry.addLine(String.valueOf(colors.val[0]));
        telemetry.addLine(String.valueOf(colors.val[1]));
        telemetry.addLine(String.valueOf(colors.val[2]));
*/
    }

    protected Scalar getColors(Mat input, Rect rect) {
        Mat submat = input.submat(rect);
        Scalar color = Core.mean(submat);
        return color;
    }

    public void drawRectangles(Mat input) {
        colorDetected = "red";
        selectedColor = selectedColorR;
        if (colors.val[0]<colors.val[1]) {
            colorDetected= "green";
            selectedColor = selectedColorG;
            if (colors.val[1]<colors.val[2]) {
                colorDetected= "blue";
                selectedColor = selectedColorB;
            }
        }
        Imgproc.rectangle(input, rect, selectedColor);
    }
}