package com.sandnunkumara.lastnumberplate;

import org.opencv.core.Mat;
import org.opencv.core.RotatedRect;

/**
 * Created by Chathuranga Sandun on 6/13/2016.
 */
public class PossiblePlate {

    Mat imgPlate;

    Mat imgGrayscale;

    Mat imgThresh;


    RotatedRect rrLocationOfPlateInScene;

    String strChars;


    public PossiblePlate() {
    }


    public Mat getImgPlate() {
        return imgPlate;
    }

    public void setImgPlate(Mat imgPlate) {
        this.imgPlate = imgPlate;
    }

    public Mat getImgGrayscale() {
        return imgGrayscale;
    }

    public void setImgGrayscale(Mat imgGrayscale) {
        this.imgGrayscale = imgGrayscale;
    }

    public Mat getImgThresh() {
        return imgThresh;
    }

    public void setImgThresh(Mat imgThresh) {
        this.imgThresh = imgThresh;
    }

    public RotatedRect getRrLocationOfPlateInScene() {
        return rrLocationOfPlateInScene;
    }

    public void setRrLocationOfPlateInScene(RotatedRect rrLocationOfPlateInScene) {
        this.rrLocationOfPlateInScene = rrLocationOfPlateInScene;
    }

    public String getStrChars() {
        return strChars;
    }

    public void setStrChars(String strChars) {
        this.strChars = strChars;
    }
}
