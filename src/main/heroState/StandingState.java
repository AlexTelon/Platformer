package main.heroState;

import main.Hero;
import main.input;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author Alex Telon
 */
public class StandingState implements IHeroState {
    private BufferedImage img = null;

    public StandingState() {
        try {
            img = ImageIO.read(new File("right.png"));
        } catch (IOException e) {
            System.out.println("ERROR IN READING PICTURE");
        }
    }

    @Override
    public void handleInput(Hero hero, input.data in) {
        if (in == input.data.PRESS_DOWN) {
            hero.changeStateTo(new DuckingState());
        } else if (in == input.data.PRESS_UP) {
            hero.changeStateTo(new JumpingState());
        } else if ( in == input.data.PRESS_LEFT) {
            hero.changeStateTo(new RunningState());
        } else if ( in == input.data.PRESS_RIGHT) {
            hero.changeStateTo(new RunningState());
        }
    }

    @Override
    public void update(Hero hero) {
        if (!hero.onGround()) {
            hero.changeStateTo(new FallingState());
        }
    }

    @Override
    public void enter(Hero hero, IHeroState state) {
        try {
            Hero.Direction momentum = hero.getDirection();
            if (momentum == Hero.Direction.RIGHT) {
                img = ImageIO.read(new File("right.png"));
            } else if (momentum == Hero.Direction.LEFT) {
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
