package main.map;

import main.Globals;
import main.Hero;

import java.util.Random;
import java.util.Random;

/**
 * @author Alex Telon
 * A map randomizer
 */
public class MapCreator {
    private static Random rand = new Random();

    public static void createMap(Hero hero) {
        boolean temp;
        int i = 0;
        do {
            temp = hero.getMap().addBox(new Box(), rand.nextInt(Globals.getWidthInBoxes() + 1), rand.nextInt(Globals.getHeightInBoxes() + 1));
            if (temp) {
                i++;
            }
        } while (i < 6);
    }

}
