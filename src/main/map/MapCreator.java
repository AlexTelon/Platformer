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
        createFloor(hero);
        do {
            temp = hero.getMap().addBox(new Box(), rand.nextInt(Globals.getWidthInBoxes()), rand.nextInt(Globals.getHeightInBoxes()));
            if (temp) {
                i++;
            }
        } while (i < 26);
    }

    /**
     * Creates a floor.
     */
    private static void createFloor(Hero hero) {
        for (int x = 0; x < Globals.getWidthInBoxes(); x++) {
            hero.getMap().addBox(new Box(), x, Globals.getHeightInBoxes()-1);
        }
    }


}
