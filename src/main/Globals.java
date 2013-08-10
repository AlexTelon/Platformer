package main;

/**
 * @author Alex Telon
 */
public class Globals {
    private static int HeightInBoxes = 15;
    private static int  WidthInBoxes = 35;
    private static int refreshrate = 50;
    private static int timeInterval = 1000/refreshrate;
    private static int pixelsPerMeter = 30;

    public static int getHeightInBoxes() {
        return HeightInBoxes;
    }

    public static int getWidthInBoxes() {
        return WidthInBoxes;
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
}
