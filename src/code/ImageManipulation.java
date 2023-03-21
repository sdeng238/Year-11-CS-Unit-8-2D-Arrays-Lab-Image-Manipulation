package code;

import image.APImage;
import image.Pixel;

import java.util.ArrayList;
import java.util.Collections;

public class ImageManipulation {

    /** CHALLENGE 0: Display Image
     *  Write a statement that will display the image in a window
     */
    public static void main(String[] args) {
        APImage image = new APImage("cyberpunk2077.jpg");
        image.draw();
        grayScale("cyberpunk2077.jpg");
        blackAndWhite("cyberpunk2077.jpg");
        edgeDetection("cyberpunk2077.jpg", 20);
        reflectImage("cyberpunk2077.jpg");
        rotateImage("cyberpunk2077.jpg");
    }

    /** CHALLENGE ONE: Grayscale
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: a grayscale copy of the image
     *
     * To convert a colour image to grayscale, we need to visit every pixel in the image ...
     * Calculate the average of the red, green, and blue components of the pixel.
     * Set the red, green, and blue components to this average value. */
    public static void grayScale(String pathOfFile) {
        APImage image = new APImage(pathOfFile);
        Pixel currPixel;
        int avgColour;

        for (int i = 0; i < image.getHeight(); i++)
        {
            for (int j = 0; j < image.getWidth(); j++)
            {
                currPixel = image.getPixel(j, i);
                avgColour = getAverageColour(image.getPixel(j, i));

                currPixel.setRed(avgColour);
                currPixel.setGreen(avgColour);
                currPixel.setBlue(avgColour);

                image.setPixel(j, i, currPixel);
            }
        }

        image.draw();
    }

    /** A helper method that can be used to assist you in each challenge.
     * This method simply calculates the average of the RGB values of a single pixel.
     * @param pixel
     * @return the average RGB value
     */
    private static int getAverageColour(Pixel pixel) {
        return (pixel.getRed() + pixel.getGreen() + pixel.getBlue()) / 3;
    }

    /** CHALLENGE TWO: Black and White
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: a black and white copy of the image
     *
     * To convert a colour image to black and white, we need to visit every pixel in the image ...
     * Calculate the average of the red, green, and blue components of the pixel.
     * If the average is less than 128, set the pixel to black
     * If the average is equal to or greater than 128, set the pixel to white */
    public static void blackAndWhite(String pathOfFile) {
        APImage image = new APImage(pathOfFile);
        Pixel currPixel;
        int avgColour;

        for (int i = 0; i < image.getHeight(); i++)
        {
            for (int j = 0; j < image.getWidth(); j++)
            {
                currPixel = image.getPixel(j, i);
                avgColour = getAverageColour(image.getPixel(j, i));

                if(avgColour < 128)
                {
                    currPixel.setRed(0);
                    currPixel.setGreen(0);
                    currPixel.setBlue(0);
                }
                else
                {
                    currPixel.setRed(255);
                    currPixel.setGreen(255);
                    currPixel.setBlue(255);
                }

                image.setPixel(j, i, currPixel);
            }
        }

        image.draw();
    }

    /** CHALLENGE Three: Edge Detection
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: an outline of the image. The amount of information will correspond to the threshold.
     *
     * Edge detection is an image processing technique for finding the boundaries of objects within images.
     * It works by detecting discontinuities in brightness. Edge detection is used for image segmentation
     * and data extraction in areas such as image processing, computer vision, and machine vision.
     *
     * There are many different edge detection algorithms. We will use a basic edge detection technique
     * For each pixel, we will calculate ...
     * 1. The average colour value of the current pixel
     * 2. The average colour value of the pixel to the left of the current pixel
     * 3. The average colour value of the pixel below the current pixel
     * If the difference between 1. and 2. OR if the difference between 1. and 3. is greater than some threshold value,
     * we will set the current pixel to black. This is because an absolute difference that is greater than our threshold
     * value should indicate an edge and thus, we colour the pixel black.
     * Otherwise, we will set the current pixel to white
     * NOTE: We want to be able to apply edge detection using various thresholds
     * For example, we could apply edge detection to an image using a threshold of 20 OR we could apply
     * edge detection to an image using a threshold of 35
     *  */
    public static void edgeDetection(String pathToFile, int threshold) {
        APImage image = new APImage(pathToFile);
        APImage newImage = new APImage(image.getWidth(), image.getHeight());
        Pixel currPixel;
        int avgColour;
        int avgLeftColour;
        int avgBelowColour;

        //rows
        //can just start from (1, 1) to avoid checking for corners
        for (int i = 1; i < image.getHeight(); i++)
        {
            //columns
            for (int j = 1; j < image.getWidth(); j++)
            {
                //average colour value of the current pixel
                currPixel = image.getPixel(j, i).clone();
                avgColour = getAverageColour(currPixel);

                //average colour value of the pixel to the left of the current pixel
                avgLeftColour = getAverageColour(image.getPixel(j - 1, i));

                //average colour value of the pixel below the current pixel
                //rmb (0, 0) is the bottom left corner
                avgBelowColour = getAverageColour(image.getPixel(j, i - 1));

                if((Math.abs(avgColour - avgLeftColour) > threshold) ||
                        (Math.abs(avgColour - avgBelowColour) > threshold))
                {
                    //can also choose not to create currPixel object
                    //instead, directly create a newPixel object to customise RGB values and set to newImage
                    currPixel.setRed(0);
                    currPixel.setGreen(0);
                    currPixel.setBlue(0);
                }
                else
                {
                    currPixel.setRed(255);
                    currPixel.setGreen(255);
                    currPixel.setBlue(255);
                }

                newImage.setPixel(j, i, currPixel);
            }
        }

        newImage.draw();
    }

    /** CHALLENGE Four: Reflect Image
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: the image reflected about the y-axis
     *
     */
    public static void reflectImage(String pathToFile) {
        APImage image = new APImage(pathToFile);
        ArrayList<Pixel> pixelStore = new ArrayList<>();

        //going down rows
        for (int i = 0; i < image.getHeight(); i++)
        {
            //going down columns
            for (int j = 0; j < image.getWidth(); j++)
            {
                pixelStore.add(image.getPixel(j, i));
            }
            Collections.reverse(pixelStore);

            for (int j = 0; j < pixelStore.size(); j++)
            {
                image.setPixel(j, i, pixelStore.get(j));
            }

            pixelStore.clear();
        }

        image.draw();
    }

    /** CHALLENGE Five: Rotate Image
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: the image rotated 90 degrees CLOCKWISE
     *
     *  */
    public static void rotateImage(String pathToFile) {
        APImage image = new APImage(pathToFile);
        APImage clonedImage = new APImage(image.getHeight(), image.getWidth());
        Pixel currPixel;

        //going down rows
        for (int i = 0; i < image.getHeight(); i++)
        {
            //going down columns
            for (int j = 0; j < image.getWidth(); j++)
            {
                currPixel = image.getPixel(j, i);
                clonedImage.setPixel(clonedImage.getWidth() - 1 - i, j, currPixel);
            }
        }

        clonedImage.draw();
    }

}
