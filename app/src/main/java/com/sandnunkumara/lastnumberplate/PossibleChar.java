package com.sandnunkumara.lastnumberplate;

import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

/**
 * Created by Chathuranga Sandun on 6/13/2016.
 */
public class PossibleChar implements Comparable {

    MatOfPoint contour;
    int intCenterX;
    int intCenterY;
    double dblDiagonalSize;
    double dblAspectRatio;
    Rect boundingRect;



    public Rect getBoundingRect() {
        return boundingRect;
    }

    public void setBoundingRect(Rect boundingRect) {
        this.boundingRect = boundingRect;
    }

    public PossibleChar(MatOfPoint contour) {
        this.contour = contour;

         boundingRect = Imgproc.boundingRect(contour);

        intCenterX = (boundingRect.x + boundingRect.x + boundingRect.width) / 2;
        intCenterY = (boundingRect.y + boundingRect.y + boundingRect.height) / 2;

        dblDiagonalSize = Math.sqrt(Math.pow(boundingRect.width, 2) + Math.pow(boundingRect.height, 2));
        dblAspectRatio = (float)boundingRect.width / (float)boundingRect.height;
    }


    public MatOfPoint getContour() {
        return contour;
    }

    public void setContour(MatOfPoint contour) {
        this.contour = contour;
    }

    public int getIntCenterX() {
        return intCenterX;
    }

    public void setIntCenterX(int intCenterX) {
        this.intCenterX = intCenterX;
    }

    public int getIntCenterY() {
        return intCenterY;
    }

    public void setIntCenterY(int intCenterY) {
        this.intCenterY = intCenterY;
    }

    public double getDblDiagonalSize() {
        return dblDiagonalSize;
    }

    public void setDblDiagonalSize(double dblDiagonalSize) {
        this.dblDiagonalSize = dblDiagonalSize;
    }

    public double getDblAspectRatio() {
        return dblAspectRatio;
    }

    public void setDblAspectRatio(double dblAspectRatio) {
        this.dblAspectRatio = dblAspectRatio;
    }

    @Override
    public int compareTo(Object another) {
        int intCenterX = ((PossibleChar) another).getIntCenterX();
        return this.getIntCenterX()-intCenterX;
    }
}
