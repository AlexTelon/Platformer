package main.map;

import main.Hero;

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
            temp = hero.getMap().addBox(new Box(), rand.nextInt(hero.getMap().getMapWidth()), rand.nextInt(hero.getMap().getMapHeight()));
            if (temp) {
                i++;
            }
        } while (i < 25);
    }

    /**
     * Creates a floor.
     */
    private static void createFloor(Hero hero) {
        for (int x = 0; x < hero.getMap().getMapWidth(); x++) {
            hero.getMap().addBox(new Box(), x, hero.getMap().getMapHeight()-1);
        }
    }


}
