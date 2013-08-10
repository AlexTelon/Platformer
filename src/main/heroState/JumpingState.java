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
public class JumpingState implements IHeroState {
    private BufferedImage img = null;

    public JumpingState() {
        try {
            img = ImageIO.read(new File("up.png"));
        } catch (IOException e) {
            System.out.println("ERROR IN READING PICTURE");
        }
    }

    @Override
    public void handleInput(Hero hero, input.data in) {
        if (in == input.data.PRESS_UP) {
        } else if( in == input.data.PRESS_DOWN) {
            hero.changeStateTo(new StandingState());
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
        hero.addyPos(-240);
        handleInput(hero, hero.inputStackPop());
    }

    @Override
    public BufferedImage getImg() {
        return img;
    }

}
