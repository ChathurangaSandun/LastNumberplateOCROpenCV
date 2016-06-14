package com.sandnunkumara.lastnumberplate;


import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.lang.Math.*;
import java.util.List;

/**
 * Created by Chathuranga Sandun on 6/13/2016.
 */
public class DetectChars {


    final int MIN_PIXEL_AREA = 80;
    final int MIN_PIXEL_WIDTH = 2;
    final int MIN_PIXEL_HEIGHT = 8;
    final double MIN_ASPECT_RATIO = 0.25;
    final double MAX_ASPECT_RATIO = 1.0;


    // constants for comparing two chars
    final double MIN_DIAG_SIZE_MULTIPLE_AWAY = 0.3;
    final double  MAX_DIAG_SIZE_MULTIPLE_AWAY = 5.0;
    final double MAX_CHANGE_IN_AREA = 0.5;

    final double MAX_CHANGE_IN_WIDTH = 0.8;
    final double MAX_CHANGE_IN_HEIGHT = 0.2;

    final double MAX_ANGLE_BETWEEN_CHARS = 12.0;

    final int MIN_NUMBER_OF_MATCHING_CHARS = 3;





    static Mat s6 = new Mat();



    boolean checkIfPossibleChar(PossibleChar possibleChar){
        if(possibleChar.getBoundingRect().area() > MIN_PIXEL_AREA  && possibleChar.getBoundingRect().width >MIN_PIXEL_WIDTH
                && possibleChar.getBoundingRect().height >MIN_PIXEL_HEIGHT && MIN_ASPECT_RATIO<possibleChar.getDblAspectRatio()
                && possibleChar.getDblAspectRatio() < MAX_ASPECT_RATIO){

            return true;
        }else{
            return  false;
        }



    }

    public ArrayList<ArrayList<PossibleChar>> findListOfListsOfMatchingChars(ArrayList<PossibleChar> listOfPossibleChars) {


        ArrayList<ArrayList<PossibleChar>> listOfListsOfMatchingChars = new ArrayList<>();


        for (PossibleChar possibleChar:listOfPossibleChars) {
            ArrayList<PossibleChar> listOfMatchingChars = findListOfMatchingChars(possibleChar, listOfPossibleChars);


                listOfMatchingChars.add(possibleChar); //logic bit change





            if( listOfMatchingChars.size() < MIN_NUMBER_OF_MATCHING_CHARS){
                continue;
            }

            listOfListsOfMatchingChars.add(listOfMatchingChars);

            ArrayList<PossibleChar> listOfPossibleCharsWithCurrentMatchesRemoved = listOfPossibleChars;


            listOfPossibleCharsWithCurrentMatchesRemoved.removeAll(listOfMatchingChars);

            ArrayList<ArrayList<PossibleChar>> recursiveListOfListsOfMatchingChars = findListOfListsOfMatchingChars(listOfPossibleCharsWithCurrentMatchesRemoved);

            for (ArrayList<PossibleChar> recursiveListOfMatchingChars:recursiveListOfListsOfMatchingChars
                 ) {
                listOfListsOfMatchingChars.add(recursiveListOfMatchingChars);
            }

            break;

        }



        return listOfListsOfMatchingChars;


    }

    private ArrayList<PossibleChar> findListOfMatchingChars(PossibleChar possibleChar, ArrayList<PossibleChar> listOfChars) {


        ArrayList<PossibleChar> listOfMatchingChars = new ArrayList<>();




        for (PossibleChar possibleMatchingChar:listOfChars
             ) {
            if(possibleChar == possibleMatchingChar){
                continue;
            }

            double dblDistanceBetweenChars = distanceBetweenChars(possibleChar, possibleMatchingChar);
            double dblAngleBetweenChars = angleBetweenChars(possibleChar, possibleMatchingChar);



            double dblChangeInArea = (double)Math.abs(possibleMatchingChar.getBoundingRect().area() - possibleChar.getBoundingRect().area()) / (double)possibleChar.getBoundingRect().area();
            double dblChangeInWidth = (double)Math.abs(possibleMatchingChar.getBoundingRect().width - possibleChar.getBoundingRect().width) / (double)possibleChar.getBoundingRect().width;
            double dblChangeInHeight = (double)Math.abs(possibleMatchingChar.getBoundingRect().height - possibleChar.getBoundingRect().height) / (double)possibleChar.getBoundingRect().height;

            if (dblDistanceBetweenChars < (possibleChar.dblDiagonalSize * MAX_DIAG_SIZE_MULTIPLE_AWAY) &&
                    dblAngleBetweenChars < MAX_ANGLE_BETWEEN_CHARS &&
                    dblChangeInArea < MAX_CHANGE_IN_AREA &&
                    dblChangeInWidth < MAX_CHANGE_IN_WIDTH &&
                    dblChangeInHeight < MAX_CHANGE_IN_HEIGHT) {

                listOfMatchingChars.add(possibleMatchingChar);   // if the chars are a match, add the current char to vector of matching chars

            }
        }


        return listOfMatchingChars;
    }

    public double angleBetweenChars(PossibleChar firstChar, PossibleChar secondChar) {
//trigonometry
        double dblAdj = Math.abs(firstChar.getIntCenterX() - secondChar.getIntCenterX());
        double dblOpp = Math.abs(firstChar.getIntCenterY()- secondChar.getIntCenterY());

        double dblAngleInRad = Math.atan(dblOpp / dblAdj);
        double dblAngleInDeg = dblAngleInRad * (180.0 / Math.PI);

        return dblAngleInDeg;

    }


    public double distanceBetweenChars(PossibleChar firstChar, PossibleChar secondChar) {
        //Pythagorean
        int X= Math.abs(firstChar.getIntCenterX() - secondChar.getIntCenterX());
        int Y = Math.abs(firstChar.getIntCenterY() - secondChar.getIntCenterY());

        double distance = Math.sqrt(Math.pow(X,2)+Math.pow(Y,2));

        return distance;
    }

    public ArrayList<PossiblePlate> detectCharsInPlates(ArrayList<PossiblePlate> listOfPossiblePlates) {

        int intPlateCounter = 0;

        Mat imgContours = new Mat();


        ArrayList<ArrayList<Point>>  contours = new ArrayList<>();

        if(listOfPossiblePlates.isEmpty()){
            return listOfPossiblePlates;
        }


        for (PossiblePlate possiblePlate:listOfPossiblePlates
             ) {
            Mat[] preProcess = new PreProcess().getPreProcess(possiblePlate.getImgPlate());

            possiblePlate.setImgGrayscale(preProcess[0]);
            possiblePlate.setImgThresh(preProcess[1]);

            s6 = possiblePlate.getImgGrayscale();




        }








        return listOfPossiblePlates;
    }
}
