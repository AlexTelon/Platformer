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
public class StandingState implements IHeroState {
    private BufferedImage img = null;

    public StandingState(Hero.Direction direction) {
        try {
            if (direction == Hero.Direction.RIGHT) {
                img = ImageIO.read(new File("right.png"));
            } else if (direction == Hero.Direction.LEFT) {
                img = ImageIO.read(new File("right.png"));
            }
        } catch (IOException e) {
            System.out.println("ERROR IN READING PICTURE");
        }
    }

    @Override
    public void handleInput(Hero hero, Input.data in) {
        if (in == Input.data.PRESS_DOWN) {
            hero.changeStateTo(new DuckingState());
        } else if (in == Input.data.PRESS_UP) {
            hero.changeStateTo(new JumpingState());
        } else if ( in == Input.data.PRESS_LEFT) {
            hero.setRunning(in);
            hero.setxVelocity(-hero.getRunnigSpeed());
            hero.changeStateTo(new RunningState());
        } else if ( in == Input.data.PRESS_RIGHT) {
            hero.setRunning(in);
            hero.setxVelocity(hero.getRunnigSpeed());
            hero.changeStateTo(new RunningState());
        }
    }

    @Override
    public void update(Hero hero) {
        if (!hero.onGround()) {
            hero.changeStateTo(new FallingState());
        } else if (hero.isRunning()) {
            hero.changeStateTo(new RunningState());
        }
    }

    @Override
    public void enter(Hero hero, IHeroState state) {
        try {
            hero.setxVelocity(0);
            Hero.Direction direction = hero.getDirection();
            if (direction == Hero.Direction.RIGHT) {
                img = ImageIO.read(new File("right.png"));
            } else if (direction == Hero.Direction.LEFT) {
                img = ImageIO.read(new File("left.png"));
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
