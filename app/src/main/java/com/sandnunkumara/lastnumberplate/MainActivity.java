package com.sandnunkumara.lastnumberplate;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("opencv_java3");

    }

    private ImageView imgView;
    private Bitmap inputImage; // make bitmap from image resource


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //inputImage = BitmapFactory.decodeResource(getResources(), R.drawable.test);
        setContentView(R.layout.activity_main);

        imgView = (ImageView) this.findViewById(R.id.imageView);


        inputImage = BitmapFactory.decodeResource(getResources(), R.drawable.test7);

       /* Mat rgba = new Mat();
        Utils.bitmapToMat(inputImage, rgba);




        //
        Mat ImageMatin = new Mat ( inputImage.getHeight(), inputImage.getWidth(), CvType.CV_8U, new Scalar(4));
        Mat ImageMatout = new Mat ( inputImage.getHeight(), inputImage.getWidth(), CvType.CV_8U, new Scalar(4));
        Mat ImageMatBk = new Mat ( inputImage.getHeight(), inputImage.getWidth(), CvType.CV_8U, new Scalar(4));
        Mat ImageMatTopHat = new Mat ( inputImage.getHeight(), inputImage.getWidth(), CvType.CV_8U, new Scalar(4));
        Mat temp = new Mat ( inputImage.getHeight(), inputImage.getWidth(), CvType.CV_8U, new Scalar(4));
*/

        Bitmap myBitmap32 = inputImage.copy(Bitmap.Config.ARGB_8888, true);
  //      Utils.bitmapToMat(myBitmap32, ImageMatin);//myBitmap32
/*

//Converting RGB to Gray.
        Imgproc.cvtColor(ImageMatin, ImageMatBk, Imgproc.COLOR_RGB2GRAY,8); //ImageMatBk=myBitmap32

        Imgproc.dilate(ImageMatBk, temp, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(9, 9)));//temp=myBitmap32
        Imgproc.erode(temp, ImageMatTopHat, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(9,9)));//ImageMatTopHat=myBitmap32

        Core.absdiff(ImageMatTopHat, ImageMatBk, ImageMatout); //ImageMatout=myBitmap32

        //sobal operation
        Imgproc.Sobel(ImageMatout,ImageMatout,CvType.CV_8U,1,0,3,1,0.4,1);//ImageMatTopHat=myBitmap32


        //Converting GaussianBlur
        Imgproc.GaussianBlur(ImageMatout, ImageMatout, new Size(5,5),2);


        Imgproc.dilate(ImageMatout, ImageMatout, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3,3)));

        Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(17, 3));
        Imgproc.morphologyEx(ImageMatout, ImageMatout, Imgproc.MORPH_CLOSE, element);

//threshold image
        Imgproc.threshold(ImageMatout, ImageMatout, 0, 255, Imgproc.THRESH_OTSU+Imgproc.THRESH_BINARY);






        //Imgproc.cvtColor(rgba, rgba, Imgproc.COLOR_RGBA2GRAY);


        Utils.matToBitmap(ImageMatout, myBitmap32);
        imgView.setImageBitmap(myBitmap32);
*/


        //Utils.matToBitmap(ImageMatout, myBitmap32);
       // imgView.setImageBitmap(myBitmap32);


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



        Mat imgOriginalScene = new Mat(inputImage.getHeight(), inputImage.getWidth(), CvType.CV_32F, new Scalar(4));  // input image
        Utils.bitmapToMat(myBitmap32, imgOriginalScene); // image -> mat
        //imgOriginalScene.convertTo(imgOriginalScene,CvType.CV_32F);


       new DetectPlates().detectPlatesInScene(imgOriginalScene);


        Utils.matToBitmap(DetectPlates.s3, myBitmap32);
        imgView.setImageBitmap(myBitmap32);
    }

    @Override
    public void onResume() {
        super.onResume();
    }





}
