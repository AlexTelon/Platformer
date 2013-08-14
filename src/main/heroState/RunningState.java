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


    @Override
    public void handleInput(Hero hero, Input.data in) {
        try {
            if (in == Input.data.PRESS_DOWN) {
                hero.changeStateTo(new DuckingState());
            } else if (in == Input.data.PRESS_UP) {
                hero.changeStateTo(new JumpingState());
            } else if ( in == Input.data.PRESS_LEFT) {
                if (!hero.isRunning()) {
                    img = ImageIO.read(new File("left.png"));
                    hero.setRunning(in);
                    hero.setxVelocity(-hero.getRunnigSpeed());
                    System.out.println("Speed: " + -hero.getRunnigSpeed());
                }
            } else if ( in == Input.data.PRESS_RIGHT) {
                if (!hero.isRunning()) {
                    img = ImageIO.read(new File("right.png"));
                    hero.setRunning(in);
                    hero.setxVelocity(hero.getRunnigSpeed());
                }
            } else if ( in == Input.data.NO_INPUT) {
                if (!hero.isRunning()) {
                    hero.setxVelocity(0);
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
                hero.addxPos(hero.getxVelocity());
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
