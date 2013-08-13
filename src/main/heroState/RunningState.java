package main.heroState;

import main.Hero;
import main.Input;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author Alex Telon
 */
public class RunningState implements IHeroState {
    private BufferedImage img = null;
    private int runningVelocity = 0; // speed WITH direction


    @Override
    public void handleInput(Hero hero, Input.data in) {
        try {
            if (in == Input.data.PRESS_DOWN) {
                hero.changeStateTo(new DuckingState());
            } else if (in == Input.data.PRESS_UP) {
                hero.changeStateTo(new JumpingState(runningVelocity, 0));
            } else if ( in == Input.data.PRESS_LEFT) {
                if (!hero.isRunning()) {
                    img = ImageIO.read(new File("left.png"));
                    hero.setRunning(in);
                    runningVelocity = -hero.getRunnigSpeed();
                    hero.setDirection(Hero.Direction.LEFT);
                }
            } else if ( in == Input.data.PRESS_RIGHT) {
                if (!hero.isRunning()) {
                    img = ImageIO.read(new File("right.png"));
                    hero.setRunning(in);
                    runningVelocity = hero.getRunnigSpeed();
                    hero.setDirection(Hero.Direction.RIGHT);
                }
            } else if ( in == Input.data.NO_INPUT) {
                if (!hero.isRunning()) {
                    runningVelocity = 0;
                    hero.changeStateTo(new StandingState(hero.getDirection()));
                }
            }
        } catch (IOException e) {
            System.out.println("ERROR IN READING PICTURE");
        }

    }

    @Override
    public void update(Hero hero) {
        if (!hero.onGround()) {
            hero.changeStateTo(new FallingState());
        } else if (hero.isRunning()) {
            runningVelocity = hero.getRunnigSpeed();
            if (hero.getDirection() == Hero.Direction.LEFT)
                runningVelocity = -runningVelocity;
            hero.addxPos(runningVelocity);
        } else {
            hero.changeStateTo(new StandingState(hero.getDirection()));
        }
    }

    @Override
    public void enter(Hero hero, IHeroState state) {
        this.handleInput(hero, hero.inputStackPeek());
        try {
            if (hero.getDirection() == Hero.Direction.LEFT) {
                img = ImageIO.read(new File("left.png"));

            } else if (hero.getDirection() == Hero.Direction.RIGHT) {
                img = ImageIO.read(new File("right.png"));

            }
        } catch (IOException e) {
            System.out.println("ERROR IN READING PICTURE");
        }
    }


    @Override
    public BufferedImage getImg() {
        return img;
    }


}
