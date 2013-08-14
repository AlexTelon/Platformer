package main.heroState;

import main.Globals;
import main.Hero;
import main.Input;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author Alex Telon
 */
public class FallingState implements IHeroState {
    private BufferedImage img = null;
    // private int fallingTime = 0;

    public FallingState() {
        try {
            img = ImageIO.read(new File("falling.png"));
        } catch (IOException e) {
            System.out.println("ERROR IN READING PICTURE");
        }
    }

    @Override
    public void handleInput(Hero hero, Input.data in) {
        try {
            if (in == Input.data.PRESS_UP) {
                //  hero.changeStateTo(new StandingState());
            }  else if ( in == Input.data.PRESS_LEFT) {
                if (!hero.isRunning()) {
                    img = ImageIO.read(new File("left.png"));
                    hero.setRunning(in);
                    hero.setxVelocity(-hero.getRunnigSpeed());
                }
            } else if ( in == Input.data.PRESS_RIGHT) {
                if (!hero.isRunning()) {
                    img = ImageIO.read(new File("right.png"));
                    hero.setRunning(in);
                    hero.setxVelocity(hero.getRunnigSpeed());
                }
            }
        } catch (IOException e) {
            System.out.println("ERROR IN READING PICTURE");
        }
    }

    @Override
    public void update(Hero hero) {
        if (hero.onGround()) {
            hero.setyVelocity(0);
            System.out.println("Falling is reset!!");
            if (hero.isRunning()) {
                hero.changeStateTo(new RunningState());
            } else {
                hero.changeStateTo(new StandingState(hero.getDirection()));
            }
        } else {
            hero.addyVelocity(9.81*Globals.getTimeIntervalMS()/Globals.pixelsPerMeter());
            hero.addyPos(hero.getyVelocity());
            if (hero.isRunning()) {
                hero.addxPos(hero.getxVelocity());
            }
        }
    }

    @Override
    public void enter(Hero hero, IHeroState state) {
    }

    @Override
    public BufferedImage getImg() {
        return img;
    }
}
