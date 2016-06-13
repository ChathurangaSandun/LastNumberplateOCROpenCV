package com.sandnunkumara.lastnumberplate;

import android.util.Log;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Created by Chathuranga Sandun on 6/12/2016.
 */
public class DetectPlates {

    static Mat s2b,s3;

    double PLATE_WIDTH_PADDING_FACTOR = 1.3;
    double PLATE_HEIGHT_PADDING_FACTOR = 1.5;



    public void detectPlatesInScene(Mat imgOriginalScene){

        //std::vector<PossiblePlate> vectorOfPossiblePlates;
        //
        // this will be the return value




        Mat imgGrayscaleScene ;
        Mat imgThreshScene;

        Mat imgContours = new Mat ( imgOriginalScene.size(), CvType.CV_8UC3, new Scalar(4));

        Mat [] bluedthresh = new PreProcess().getPreProcess(imgOriginalScene);





/////////////////////////////////////////////////////step 2////////////////////////////////////////////////////////////////
        ArrayList<PossibleChar> listOfPossibleCharsInScene = findPossibleCharsInScene(bluedthresh[1]);



        //showing s2b -  wtih posible
        //Mat imgTopHat=new Mat(imgOriginalScene.height(), imgOriginalScene.width(), CvType.CV_8U, new Scalar(4));

        int numConn = 0;
        List<MatOfPoint> contours = new ArrayList<>();
        for (PossibleChar possibleChar: listOfPossibleCharsInScene) {
              contours.add(numConn,possibleChar.getContour());
        }


        Imgproc.drawContours(imgContours,contours,-1,new Scalar(255,255,255));
        s2b = imgContours;

/////////////////////////////////////////////////////step 2 - listoflistmatchingchartacters()////////////////////////////////////////////////////////////////


        ArrayList<ArrayList<PossibleChar>> listOfListsOfMatchingCharsInScene = new DetectChars().findListOfListsOfMatchingChars(listOfPossibleCharsInScene);

        Log.i("step 3", "detectPlatesInScene: size "+listOfListsOfMatchingCharsInScene.size());

        Mat imgContoursS3 = new Mat ( imgOriginalScene.size(), CvType.CV_8UC3, new Scalar(4));

        for (ArrayList<PossibleChar> listOfMatchingChars:
             listOfListsOfMatchingCharsInScene) {
            Random random = new Random();
            int blue = (random.nextInt(255)) ;
            int green = (random.nextInt(255)) ;
            int red = (random.nextInt(255)) ;


            ArrayList<MatOfPoint> contoursS3 = new ArrayList<>();

            for (PossibleChar matchingChar : listOfMatchingChars
                 ) {

                contoursS3.add(matchingChar.getContour());
                
            }


            Imgproc.drawContours(imgContoursS3,contoursS3,-1,new Scalar(blue,green,red));


        }

        s3 = imgContoursS3;


        ///////////////////////////////////////////step4 extract plate //////////////////////////////////////////////



        for (ArrayList<PossibleChar> listOfMatchingChars:
                listOfListsOfMatchingCharsInScene) {
            extractPlate(imgOriginalScene,listOfMatchingChars);
            Log.i("step 4", "char segment");

        }

        















    }

    private void extractPlate(Mat imgOriginalScene, ArrayList<PossibleChar> listOfMatchingChars) {
        PossiblePlate possiblePlate = new PossiblePlate();





        for (PossibleChar possibleChar:listOfMatchingChars
                ) {
            Log.i("step 4", "unsorted "+possibleChar.getIntCenterX());
        }

        Collections.sort(listOfMatchingChars);


        for (PossibleChar possibleChar:listOfMatchingChars
                ) {
            Log.i("step 4", "sorted "+possibleChar.getIntCenterX());
        }


        double fltPlateCenterX = (listOfMatchingChars.get(0).intCenterX + listOfMatchingChars.get(listOfMatchingChars.size() - 1).intCenterX) / 2.0;
        double fltPlateCenterY = (listOfMatchingChars.get(0).intCenterY + listOfMatchingChars.get(listOfMatchingChars.size() - 1).intCenterY) / 2.0;

//ptPlateCenter = fltPlateCenterX, fltPlateCenterY//TODO Define Center

        int intPlateWidth = (int)((listOfMatchingChars.get(listOfMatchingChars.size() - 1).getBoundingRect().x + listOfMatchingChars.get(listOfMatchingChars.size() - 1).getBoundingRect().width - listOfMatchingChars.get(0).getBoundingRect().x) * PLATE_WIDTH_PADDING_FACTOR);

        double intTotalOfCharHeights = 0;

        for (PossibleChar matchingChar:listOfMatchingChars
             ) {
            intTotalOfCharHeights += matchingChar.getBoundingRect().height;
        }

        double fltAverageCharHeight = (double)intTotalOfCharHeights /listOfMatchingChars.size();

        int intPlateHeight = (int)(fltAverageCharHeight * PLATE_HEIGHT_PADDING_FACTOR);





















    }


    public ArrayList<PossibleChar> findPossibleCharsInScene(Mat imgThresh){

        ArrayList<PossibleChar> listOfPossibleChars = new ArrayList<>();


        Mat imgContours = new Mat ( imgThresh.size(), CvType.CV_8UC3, new Scalar(3));
        int intCountOfPossibleChars = 0;

        Mat imgThreshCopy = imgThresh.clone();

        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();

        Imgproc.findContours(imgThreshCopy,contours,new Mat(),Imgproc.RETR_LIST,Imgproc.CHAIN_APPROX_SIMPLE);

        Log.i("step 2", "findPossibleCharsInScene: "+contours.size());

        for ( int i = 0; i < contours.size(); i++) {
            Log.i("step 2", "findPossibleCharsInScene: "+i);
            //showing perpose
            Imgproc.drawContours(imgContours,contours,i,new Scalar(255,255,255));
            //

            PossibleChar possibleChar = new PossibleChar(contours.get(i));//------------------------------------------------------------------------------


            if(new DetectChars().checkIfPossibleChar(possibleChar)){

                listOfPossibleChars.add(intCountOfPossibleChars,possibleChar);
                intCountOfPossibleChars++;
            }


        }

        Log.i("step 2", "findPossibleCharsInScene: contours "+ intCountOfPossibleChars);
        Log.i("step 2", "findPossibleCharsInScene: intCountOfPossibleChars"+ intCountOfPossibleChars);


        return listOfPossibleChars;




    }
}
