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
public class DuckingState implements IHeroState {
    private int chargeTime = 0;
    private BufferedImage img = null;

    @Override
    public void handleInput(Hero hero, Input.data in) {
        if (in == Input.data.RELEASE_DOWN) {
            hero.changeStateTo(new StandingState(hero.getDirection()));
        } else if( in == Input.data.PRESS_DOWN) {
            try {
                if (chargeTime > 5) {
                    img = ImageIO.read(new File("ducking.png"));
                } else
                    img = ImageIO.read(new File("down.png"));
            } catch (IOException e) {
                System.out.println("ERROR IN READING PICTURE");
            }
        } else if ( in == Input.data.PRESS_LEFT) {
            hero.changeStateTo(new RunningState());
        } else if ( in == Input.data.PRESS_RIGHT) {
            hero.changeStateTo(new RunningState());
        } else if ( in == Input.data.PRESS_UP) {
            hero.changeStateTo(new JumpingState());
        }

        if (in != Input.data.PRESS_DOWN)
            chargeTime = 0;
    }

    @Override
    public void update(Hero hero) {
        if (hero.inputStackPeek() == Input.data.PRESS_DOWN) {
            chargeTime++;
        }
        if (!hero.onGround()) {
            hero.changeStateTo(new FallingState());
        }
    }

    @Override
    public void enter(Hero hero, IHeroState state) {
        handleInput(hero, hero.inputStackPeek());
    }

    @Override
    public BufferedImage getImg() {
        return img;
    }

}
