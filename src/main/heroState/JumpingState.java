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
public class JumpingState implements IHeroState {
    private BufferedImage img = null;
    private int nrOfJumpsInAir = 0;
    private int maxNrOfJumpsInAir = 2;



    public JumpingState() {
        try {
            img = ImageIO.read(new File("up.png"));
        } catch (IOException e) {
            System.out.println("ERROR IN READING PICTURE");
        }
    }

    public JumpingState(int nrOfJumpsInAir) {
        try {
            img = ImageIO.read(new File("up.png"));
        } catch (IOException e) {
            System.out.println("ERROR IN READING PICTURE");
        }
        this.nrOfJumpsInAir = nrOfJumpsInAir;
    }

    @Override
    public void handleInput(Hero hero, Input.data in) {
        if (in == Input.data.PRESS_UP) {
            if ( nrOfJumpsInAir < maxNrOfJumpsInAir) {
                nrOfJumpsInAir++;
                hero.changeStateTo(new JumpingState(nrOfJumpsInAir));
            }
        } else if( in == Input.data.PRESS_DOWN) {
            // pressing down should end the jump
            hero.changeStateTo(new FallingState());
        } else if ( in == Input.data.PRESS_LEFT) {
            if (!hero.isRunning()) {
                hero.setRunning(in);
                hero.setxVelocity(-hero.getRunnigSpeed());
            }
        } else if ( in == Input.data.PRESS_RIGHT) {
            if (!hero.isRunning()) {
                hero.setRunning(in);
                hero.setxVelocity(hero.getRunnigSpeed());
            }
        }
    }

    @Override
    public void update(Hero hero) {
        if (hero.getyVelocity() > 0) {
            // we are now going downwards
            hero.setyVelocity(0);
            hero.changeStateTo(new FallingState(nrOfJumpsInAir));
        } else {
            hero.addyVelocity(9.81* main.Globals.getTimeIntervalMS()/ main.Globals.pixelsPerMeter());
            hero.addyPos(hero.getyVelocity());
         //   if (hero.isRunning()) {
                hero.addxPos(hero.getxVelocity());
         //   }
        }
    }

    @Override
    public void enter(Hero hero, IHeroState state) {
        // fix so jumping is like a reverse falling so it looks neat.
        hero.setyVelocity(-40);
        hero.addyPos(-1);
        handleInput(hero, hero.inputStackPeek());
    }

    @Override
    public BufferedImage getImg() {
        return img;
    }
}
