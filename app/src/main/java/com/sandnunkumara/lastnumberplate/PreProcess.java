package com.sandnunkumara.lastnumberplate;

import android.util.Log;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chathuranga Sandun on 6/12/2016.
 */
public class PreProcess {

    private final String TAG = "preprocesser";


    final int ADAPTIVE_THRESH_BLOCK_SIZE =19;
    final int ADAPTIVE_THRESH_WEIGHT = 9;

    static Mat s2;

    public PreProcess() {


    }


    public Mat[] getPreProcess(Mat imgOriginalScene){
        Mat imgGrayscale = extractValue(imgOriginalScene);

        Mat imgMaxContrastGrayscale = maximizeContrast(imgGrayscale);

        Mat imgBlurred=new Mat(imgOriginalScene.height(), imgOriginalScene.width(), CvType.CV_8U, new Scalar(4));
        Imgproc.GaussianBlur(imgMaxContrastGrayscale,imgBlurred,new Size(9,9),1); //5*5,0

        Mat imgThresh = new Mat(imgOriginalScene.height(), imgOriginalScene.width(), CvType.CV_8U, new Scalar(4));
        Imgproc.adaptiveThreshold(imgBlurred,imgThresh,255,Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C,Imgproc.THRESH_BINARY_INV,ADAPTIVE_THRESH_BLOCK_SIZE,ADAPTIVE_THRESH_WEIGHT);

        Mat[] mats = {imgBlurred, imgThresh};


        s2 = mats[1];
        return mats;
    }


    private Mat  extractValue(Mat imgOriginalScene){
        Mat imgHSV= new Mat(imgOriginalScene.height(), imgOriginalScene.width(), CvType.CV_8U, new Scalar(4));
        List<Mat> arrayListOfHSVImages= new ArrayList<>(3);
        Mat imgValue = new Mat(imgOriginalScene.height(), imgOriginalScene.width(), CvType.CV_8U, new Scalar(4));

        Imgproc.cvtColor(imgOriginalScene,imgHSV, Imgproc.COLOR_BGR2HSV);//COLOR_RGB2GRAY
        Core.split(imgHSV,arrayListOfHSVImages);
        Log.i(TAG, "extractValue: "+arrayListOfHSVImages.get(2));//put2 is error

        return arrayListOfHSVImages.get(2);
    }

    private Mat maximizeContrast(Mat imgGrayscale){

;

        Mat imgTopHat=new Mat(imgGrayscale.height(), imgGrayscale.width(), CvType.CV_8U, new Scalar(4));
        Mat imgBlackHat = new Mat(imgGrayscale.height(), imgGrayscale.width(), CvType.CV_8U, new Scalar(4));
        Mat imgGrayscalePlusTopHat=new Mat(imgGrayscale.height(), imgGrayscale.width(), CvType.CV_8U, new Scalar(4));
        Mat imgGrayscalePlusTopHatMinusBlackHat = new Mat(imgGrayscale.height(), imgGrayscale.width(), CvType.CV_8U, new Scalar(4));

        Mat structuringElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3,3));

        Imgproc.morphologyEx(imgGrayscale,imgTopHat,Imgproc.MORPH_TOPHAT,structuringElement);
        Imgproc.morphologyEx(imgGrayscale,imgBlackHat,Imgproc.MORPH_BLACKHAT,structuringElement);

        Core.add   (imgGrayscale , imgTopHat,imgGrayscalePlusTopHat); //TODO : change
        Core.subtract(imgGrayscalePlusTopHat,imgBlackHat,imgGrayscalePlusTopHatMinusBlackHat);


        return imgGrayscalePlusTopHatMinusBlackHat;



    }





}
