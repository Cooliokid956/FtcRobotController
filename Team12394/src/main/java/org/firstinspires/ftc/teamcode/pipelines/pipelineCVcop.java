package org.firstinspires.ftc.teamcode.pipelines;/*package org.firstinspires.ftc.teamcode.pipelines;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.Arrays;

public class pipelineCVcop extends OpenCvPipeline {

    Telemetry telemetry;

    public pipelineCVcop(Telemetry telemetry) {
        this.telemetry = telemetry;
    }

    public ArrayList<Double> hueAvg1 = new ArrayList<>(Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0));
    public ArrayList<Double> hueAvg2 = new ArrayList<>(Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0));
    public ArrayList<Double> hueAvg3 = new ArrayList<>(Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0));

    public Scalar nonSelectedColor = new Scalar(0, 255, 0);
    public Scalar selectedColor = new Scalar(0, 0, 255);

    private Rect[] createRectGrid(double x, double y, double w, double h, int ) =
    public Rect rect11 = new Rect(100, 42, 40, 40);
    public Rect rect12 = new Rect(160, 42, 40, 40);
    public Rect rect13 = new Rect(210, 42, 40, 40);
    public Rect rect21 = new Rect(100, 42, 40, 40);
    public Rect rect22 = new Rect(160, 42, 40, 40);
    public Rect rect23 = new Rect(210, 42, 40, 40);
    public Rect rect31 = new Rect(100, 42, 40, 40);
    public Rect rect32 = new Rect(160, 42, 40, 40);
    public Rect rect33 = new Rect(210, 42, 40, 40);

    public double hue = 180;
    public int selectedRect = 0;

    public double absDiff(double value, double compValue){
        return Math.abs(value-compValue);
    }

    Mat hsvMat = new Mat();

    @Override
    public Mat processFrame(Mat input) {

        rect1 = new Rect(input.cols()/2-80, input.rows()/2-20, 40, 40);
        rect2 = new Rect(input.cols()/2-20, input.rows()/2-20, 40, 40);
        rect3 = new Rect(input.cols()/2+40, input.rows()/2-20, 40, 40);
        selectedRect = findRectangle(input);
        drawRectangles(input);
        telemetry.update();

        return input;
    }

    int findRectangle(Mat input) {
        Imgproc.cvtColor(input, hsvMat, Imgproc.COLOR_RGB2HSV);

        double hueRect1 = getAvgSaturation(hsvMat, rect1);
        double hueRect2 = getAvgSaturation(hsvMat, rect2);
        double hueRect3 = getAvgSaturation(hsvMat, rect3);

        hueAvg1.remove(0);
        hueAvg1.add(hueRect1);
        hueAvg2.remove(0);
        hueAvg2.add(hueRect2);
        hueAvg3.remove(0);
        hueAvg3.add(hueRect3);

        hueRect1=(hueAvg1.get(0)+hueAvg1.get(1)+hueAvg1.get(2)+hueAvg1.get(3)+hueAvg1.get(4))/5.0;
        hueRect2=(hueAvg2.get(0)+hueAvg2.get(1)+hueAvg2.get(2)+hueAvg2.get(3)+hueAvg2.get(4))/5.0;
        hueRect3=(hueAvg3.get(0)+hueAvg3.get(1)+hueAvg3.get(2)+hueAvg3.get(3)+hueAvg3.get(4))/5.0;

        telemetry.addLine(String.valueOf(hueRect1));
        telemetry.addLine(String.valueOf(hueRect2));
        telemetry.addLine(String.valueOf(hueRect3));


        // assume one with the least hue (most red) is our TSE
        if ((absDiff((hueRect1), hue) < absDiff(hueRect2, hue)) && (absDiff(hueRect1, hue) < absDiff(hueRect3, hue))) {
            return 1;
        } else if ((absDiff(hueRect1, hue) > absDiff(hueRect2, hue)) && (absDiff(hueRect2, hue) < absDiff(hueRect3, hue))) {
            return 2;
        }
        return 3; //It is either 3 or we can't tell
    }

    protected double getAvgSaturation(Mat input, Rect rect) {
        Mat submat = input.submat(rect);
        Scalar color = Core.mean(submat);
        return color.val[0];
    }

    public void drawRectangles(Mat input) {
        Imgproc.rectangle(input, rect1, nonSelectedColor);
        Imgproc.rectangle(input, rect2, nonSelectedColor);
        Imgproc.rectangle(input, rect3, nonSelectedColor);
        switch (selectedRect) {
            case 1:
                Imgproc.rectangle(input, rect1, selectedColor);
                break;
            case 2:
                Imgproc.rectangle(input, rect2, selectedColor);
                break;
            case 3:
                Imgproc.rectangle(input, rect3, selectedColor);
                break;
        }
    }
}*/