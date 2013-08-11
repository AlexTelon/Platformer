package main.heroState;

import main.Globals;
import main.Hero;
import main.input;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author Alex Telon
 */
public class FallingState implements IHeroState {
    private BufferedImage img = null;
    private double yFallingVelocity = 0;
    private int xFallingVelocity = 0; // speed WITH direction
    // private int fallingTime = 0;

    public FallingState() {
        try {
            img = ImageIO.read(new File("falling.png"));
        } catch (IOException e) {
            System.out.println("ERROR IN READING PICTURE");
        }
    }

    public FallingState(double yVelocity, int xVelocity) {
        try {
            img = ImageIO.read(new File("falling.png"));
        } catch (IOException e) {
            System.out.println("ERROR IN READING PICTURE");
        }
        yFallingVelocity = yVelocity;
        xFallingVelocity = xVelocity;
    }


    @Override
    public void handleInput(Hero hero, input.data in) {
        // crude version..
        if (in == input.data.PRESS_UP) {
            hero.changeStateTo(new StandingState());
        }
    }

    @Override
    public void update(Hero hero) {
        if (hero.onGround()) {
            yFallingVelocity = 0;
            System.out.println("Falling is reset!!");
            hero.changeStateTo(new StandingState());
        } else {
            updateFallingVelocity();
            hero.addyPos(velocityToInt(hero));
            hero.addxPos(xFallingVelocity);
        }
    }

    @Override
    public void enter(Hero hero, IHeroState state) {
    }

    @Override
    public BufferedImage getImg() {
        return img;
    }

    private void updateFallingVelocity() {
        //System.out.println("what we add: " + 9.81 + "*" + Globals.getTimeIntervalMS() + "*" + Globals.pixelsPerMeter());
      //  System.out.println((9.81*Globals.getTimeIntervalMS()/Globals.pixelsPerMeter()));
        yFallingVelocity += (9.81*Globals.getTimeIntervalMS()/Globals.pixelsPerMeter());
    }

    private int velocityToInt(Hero hero) {
        int temp = (int) yFallingVelocity;
         System.out.println("Falling: " + yFallingVelocity + " Y pos: " + hero.getyPos());
        return temp;
    }

}
