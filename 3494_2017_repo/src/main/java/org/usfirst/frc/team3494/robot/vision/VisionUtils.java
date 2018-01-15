package org.usfirst.frc.team3494.robot.vision;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

public class VisionUtils {
    public static Mat rotate(Mat source, double angle) {
        // Step rotate_image
        Point src_center = new Point(source.cols() / 2.0F, source.rows() / 2.0F);
        Mat rot_mat = Imgproc.getRotationMatrix2D(src_center, 180, 1.0);
        Mat dst = new Mat();
        Imgproc.warpAffine(source, dst, rot_mat, source.size());
        return dst;
    }
}
