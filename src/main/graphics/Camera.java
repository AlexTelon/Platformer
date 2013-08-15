package main.graphics;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import main.Globals;
import main.Hero;
import main.map.Box;

/**
 * The main component of GameFrame, this is what paints the game itself.
 */
public class Camera {
        private static int xHeroPosition;
        private static int leftSide;
        private static int rightSide;
        private static int widthOfScreen = Globals.getScreenWidth();
        private static int halfScreen = Globals.getHalfScreenWidth();
        private static int pixOffset = 0; // offset from the start of the hero
        private static int boxOffset = 0; // offset from the start position
        private static Hero hero;

    public Camera(Hero hero) {
        this.hero = hero;
        this.xHeroPosition = hero.getxPos();
        xHeroPosition = hero.getxStartingPosition();
        //boxOffset = pixOffset / Box.getSide();
        setSides();
    }


    public static void update(int xPos) {
        pixOffset += (xPos - xHeroPosition);
        if (pixOffset != 0) {
            System.out.println("");
        }
        System.out.println((xPos - xHeroPosition));
        boxOffset = pixOffset / Box.getSide();
        xHeroPosition = xPos;
        setSides();
    }

    private static void setSides() {
        leftSide = xHeroPosition - halfScreen;
        rightSide = xHeroPosition + halfScreen;
        if (leftSide < 0) {
            leftSide = 0;
            rightSide = widthOfScreen;
        }
    }

    public static int getLeftSide() {
        return leftSide;
    }

    public static int getRightSide() {
        return rightSide;
    }

    public static int getPixOffset() {
        return pixOffset;
    }

    public static int getBoxOffset() {
        return boxOffset;
    }
}


