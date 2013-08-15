package main;

import main.map.Box;

/**
 * @author Alex Telon
 */
public class Globals {
    private static int screenHeightInBoxes = 15;
    private static int screenWidthInBoxes = 35;
    private static int  xMiddleOfBoxes = screenWidthInBoxes / 2;
    private static int refreshrate = 50;
    private static int timeInterval = 1000/refreshrate;
    private static int pixelsPerMeter = 30;
    private static int screenWidth = screenWidthInBoxes * Box.getSide();
    private static int halfScreenWidth = screenWidth / 2;



    public static int getScreenHeightInBoxes() {
        return screenHeightInBoxes;
    }

    public static int getScreenWidthInBoxes() {
        return screenWidthInBoxes;
    }

    public static int getRefreshrate() {
        return refreshrate;
    }

    /**
     *
     * @return timerinterval in ms
     */
    public static int getTimeIntervalMS() {
        return timeInterval;
    }

    public static int pixelsPerMeter() {
        return pixelsPerMeter;
    }

    public static int getxMiddleOfBoxes() {
        return xMiddleOfBoxes;
    }

    public static int getScreenWidth() {
        return screenWidth;
    }

    public static void setScreenWidth(int screenWidth) {
        Globals.screenWidth = screenWidth;
    }

    public static int getHalfScreenWidth() {
        return halfScreenWidth;
    }
}
