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
public class RunningState implements IHeroState {
    private BufferedImage img = null;
    private int runnigSpeed = 10; // the quantitive speed
    private int runnigVelocity = 10; // speed WITH direction

    @Override
    public void handleInput(Hero hero, input.data in) {
        try {
            if (in == input.data.PRESS_DOWN) {
                hero.changeStateTo(new DuckingState());
            } else if (in == input.data.PRESS_UP) {
                hero.changeStateTo(new JumpingState(runnigVelocity));
            } else if ( in == input.data.PRESS_LEFT) {
                img = ImageIO.read(new File("left.png"));
                runnigVelocity = -runnigSpeed;
                hero.addxPos(runnigVelocity);
                hero.setDirection(Hero.Direction.LEFT);
            } else if ( in == input.data.PRESS_RIGHT) {
                img = ImageIO.read(new File("right.png"));
                runnigVelocity = runnigSpeed;
                hero.addxPos(runnigVelocity);
                hero.setDirection(Hero.Direction.RIGHT);
            }
        } catch (IOException e) {
            System.out.println("ERROR IN READING PICTURE");
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
        this.handleInput(hero, hero.inputStackPop());
    }

    @Override
    public BufferedImage getImg() {
        return img;
    }
}
