package org.firstinspires.ftc.teamcode.pipelines;

//import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.Arrays;

public class pipelineCV2023 extends OpenCvPipeline {

//    Telemetry telemetry;

//    public pipelineCVrgb(Telemetry telemetry) {this.telemetry = telemetry;}

    public ArrayList<Double> hueAvg = new ArrayList<>(Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0));

    Scalar selectedColorR = new Scalar(255, 0, 0);
    Scalar selectedColorB = new Scalar(0, 0, 255);
    Scalar colorL, colorC, colorR;
    Rect l, c, r;

    public String colorest = "geen";
    public int ci;
    public double absDiff(double v1, double v2){
        return Math.abs(v1-v2);
    }
    public double hueRect;

    @Override
    public Mat processFrame(Mat input) {
        int w = input.cols()/6;
        int h = input.rows()/12;
        int offsetX = (int)(input.cols()*.455);
        int offsetY = input.rows()/16;
        int x = input.cols()/2-w/2;
        int y = (int) (input.rows()*.67-h/2);
        l = new Rect((int) (input.cols()/2-w*.5/2-offsetX), y+offsetY, (int) (w*.5), h);
        c = new Rect(x, y, w, h);
        r = new Rect((int) (input.cols()/2-w*.5/2+offsetX), y+offsetY, (int) (w*.5), h);
        findRectangle(input);
        drawRectangles(input);

//        telemetry.update();
        return input;
    }

    void findRectangle(Mat input) {

        colorL = getColors(input, l);
        colorC = getColors(input, c);
        colorR = getColors(input, r);

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
    final int threshold = 180;
    public void drawRectangles(Mat input) {
        colorest = "none";
        ci = colorL.val[0]+colorC.val[0]+colorR.val[0]>colorL.val[2]+colorC.val[2]+colorR.val[2]?0:2;
        Scalar selectedColor = ci == 0 ? selectedColorR : selectedColorB;

        double last = threshold;
        if (colorL.val[ci] > last) {
            colorest = "left";
            last = colorL.val[ci];
        }
        if (colorC.val[ci] > last) {
            colorest = "center";
            last = colorC.val[ci];
        }
        if (colorR.val[ci] > last) {
            colorest = "right";
        }
//        selectedColor = selectedColorR;
//        if (colorC.val[0]<colorC.val[1]) {
//            colorest = "green";
//            selectedColor = selectedColorG;
//            if (colorC.val[1]<colorC.val[2]) {
//                colorest = "blue";
//                selectedColor = selectedColorB;
//            }
//        }
        Imgproc.rectangle(input, l, colorest == "left" ? selectedColor : colorL);
        Imgproc.rectangle(input, c, colorest == "center" ? selectedColor : colorC);
        Imgproc.rectangle(input, r, colorest == "right" ? selectedColor : colorR);
    }
}