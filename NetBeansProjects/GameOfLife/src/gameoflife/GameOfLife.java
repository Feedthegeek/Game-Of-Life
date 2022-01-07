/**
 *  Game of Life program for CSCI 110
 *  Author:  Nicholas Herrick
 *  Due Date: 11/13/2021
 *  Pledged: I pledge that this program is of my own creation, using the code
 *  given to me as part of the project.
 *
 */
package gameoflife;

import acm.graphics.*;
import acm.program.*;
import java.awt.*;
import java.awt.event.*;

public class GameOfLife extends GraphicsProgram {

    /**
     * Number of villagers
     */
    private static final int NUM_VILLAGERS = 200;

    /**
     * Width of the game display (all coordinates are in pixels)
     */
    private static final int WIDTH = 1200;
    /**
     * Height of the game display
     */
    private static final int HEIGHT = 1200;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String[] sizeArgs = {"width=" + WIDTH, "height=" + HEIGHT};
        new GameOfLife().start(sizeArgs);
    }

    // Fill all but middle index in array with false
    public void initialLife(boolean[] refArray) {
        int i;
        for (i = 0; i < refArray.length; i++) {
            refArray[i] = false;
        }
        refArray[refArray.length / 2] = true;
    }

    // Check if neighboring index is true or false and assign helper[] nextLife according to algorithim.
    public void nextGen(boolean[] refArray) {
        boolean[] nextLife = new boolean[NUM_VILLAGERS];
        int i;
        for (i = 0; i < refArray.length; i++) {
            if (i != 0 && i != NUM_VILLAGERS - 1) {  // Check if edge case of array
                if (refArray[i]) {                   // If [i] == true
                    if (refArray[i - 1] && refArray[i + 1]) {
                        nextLife[i] = true;
                    } else if (refArray[i - 1] || refArray[i + 1]) {
                        nextLife[i] = false;
                    }

                } else {                            // if [i] == false
                    if (refArray[i - 1] && refArray[i + 1]) {
                        nextLife[i] = false;
                    } else if (refArray[i - 1] || refArray[i + 1]) {
                        nextLife[i] = true;
                    }

                }
                    // If edge case in array for [i] == 0
            } else {
                if (i == 0) {
                    if (refArray[i]) {
                        if (refArray[i + 1] && refArray[NUM_VILLAGERS - 1]) {
                            nextLife[i] = true;
                        }
                    } else if (refArray[i + 1] || refArray[NUM_VILLAGERS - 1]) {
                        nextLife[i] = false;
                    }
                    // If edge case in array for [i] == NUM_VILLAGERS - 1
                } else if (i == NUM_VILLAGERS - 1) {
                    if (refArray[i]) {
                        if (refArray[0] && refArray[NUM_VILLAGERS - 2]) {
                            nextLife[i] = true;
                        }
                    } else if (refArray[0] || refArray[NUM_VILLAGERS - 2]) {
                        nextLife[i] = false;
                    }
                }
            }
        }
        for (i = 0; i < nextLife.length; i++) {  // Copy values to helper array nextLife
            refArray[i] = nextLife[i];
        }
    }

    // Create squares, fill w/ color if refArray[i]==true
    public void houseGraphic(boolean[] refArray, double y) {
        int i;
        int x = 0;
        for (i = 0; i < refArray.length; i++) {
            if (refArray[i] == true) {
                GRect rect = new GRect(x, y, WIDTH / NUM_VILLAGERS, HEIGHT / NUM_VILLAGERS);
                rect.setFillColor(Color.GREEN);
                rect.setFilled(true);
                add(rect);
                x += (WIDTH / NUM_VILLAGERS);
            } else {
                GRect rect = new GRect(x, y, WIDTH / NUM_VILLAGERS, HEIGHT / NUM_VILLAGERS);
                rect.setFilled(false);
                add(rect);
                x += (WIDTH / NUM_VILLAGERS);
            }
        }

    }
    // Initialize values in refArray, call other methods and adjust y coordinate for GRect
    public void startUp(boolean[] refArray, double y) { 
        initialLife(refArray);
        houseGraphic(refArray, y);
        y += HEIGHT / NUM_VILLAGERS;
        for (int i = 0; i < refArray.length; i++) {
            nextGen(refArray);
            houseGraphic(refArray, y);
            y += HEIGHT / NUM_VILLAGERS;
        }
    }

    /**
     * Called by the system once the app is started.
     */
    @Override
    public void run() {

        boolean[] villagers = new boolean[NUM_VILLAGERS];

        startUp(villagers, HEIGHT / NUM_VILLAGERS); // startUp method called

    }

}
