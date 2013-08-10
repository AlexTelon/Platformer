package main.map;

import main.Globals;

import java.awt.image.BufferedImage;

/**
 * @author Alex Telon
 * The building blocks that the world is made of.
 */
public class BoxMap {

    private Box[][] boxMap = new Box[Globals.getHeightInBoxes()][Globals.getWidthInBoxes()];

    /**
     * tries to add a Box to a boxMap
     * @param box
     * @param x position in the map (0 is at the top)
     * @param y position in the map (0 is to the left)
     * @return true if successful
     */
    public boolean addBox(Box box, int x, int y) {
        if (boxMap[y][x] == null) {
            boxMap[y][x] = box;
            return true;
        }
        return false;
    }

    public Box[][] getBoxMap() {
        return boxMap;
    }
}
