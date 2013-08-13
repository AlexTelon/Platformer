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
public class JumpingState implements IHeroState {
    private BufferedImage img = null;
    private double yJumpingVelocity = -40;
    private int xJumpingVelocity = 0;
    private int nrOfJumpsInAir = 0;
    private int maxNrOfJumpsInAir = 2;



    public JumpingState() {
        try {
            img = ImageIO.read(new File("up.png"));
        } catch (IOException e) {
            System.out.println("ERROR IN READING PICTURE");
        }
    }

    public JumpingState(int runnigVelocity, int nrOfJumpsInAir) {
        try {
            img = ImageIO.read(new File("up.png"));
        } catch (IOException e) {
            System.out.println("ERROR IN READING PICTURE");
        }
        xJumpingVelocity = runnigVelocity;
        this.nrOfJumpsInAir = nrOfJumpsInAir;
    }

    @Override
    public void handleInput(Hero hero, Input.data in) {
        if (in == Input.data.PRESS_UP) {
            if ( nrOfJumpsInAir < maxNrOfJumpsInAir) {
                nrOfJumpsInAir++;
                hero.changeStateTo(new JumpingState(xJumpingVelocity, nrOfJumpsInAir));
            }
        } else if( in == Input.data.PRESS_DOWN) {
            // pressing down should end the jump
            hero.changeStateTo(new FallingState(yJumpingVelocity, xJumpingVelocity));
        } else if ( in == Input.data.PRESS_LEFT) {
            hero.setRunning(in);
            xJumpingVelocity = -hero.getRunnigSpeed();
            hero.setDirection(Hero.Direction.LEFT);
        } else if ( in == Input.data.PRESS_RIGHT) {
            hero.setRunning(in);
            xJumpingVelocity = hero.getRunnigSpeed();
            hero.setDirection(Hero.Direction.RIGHT);
        }
    }

    @Override
    public void update(Hero hero) {
        if (hero.onGround()) {
            yJumpingVelocity = 0;
            hero.changeStateTo(new StandingState(hero.getDirection()));
        } else if (yJumpingVelocity > 0) {
            // we are now going downwards
            hero.changeStateTo(new FallingState(yJumpingVelocity, xJumpingVelocity));
            yJumpingVelocity = 0;
        } else {
            updateJumpingVelocity();
            hero.addyPos(velocityToInt(hero));
            hero.addxPos(xJumpingVelocity);
        }
    } 

    @Override
    public void enter(Hero hero, IHeroState state) {
        // fix so jumping is like a reverse falling so it looks neat.
        // make sure we are in jumpingState while moving upwards and
        // in falling stat when we move down.
        hero.addyPos(-1);
        handleInput(hero, hero.inputStackPeek());
    }

    @Override
    public BufferedImage getImg() {
        return img;
    }
    
    
    /**
     * jumping could be implemented by making it a fallin state asap so that it handles the slowing down of
     * the upwards movement but since we want the hero to (sometimes) jump through things on the way 
     * up (= jumpingState) while landing on the way down (=fallingState)
     * This jumping state also gives us the option to make the hero able to maybe do an airjump
     * on the way up but not the way down or something like that. 
     * 
     */
    private void updateJumpingVelocity() {
        Globals Globals;
        yJumpingVelocity += (9.81* main.Globals.getTimeIntervalMS()/ main.Globals.pixelsPerMeter());
    }
    
        private int velocityToInt(Hero hero) {
        int temp = (int) yJumpingVelocity;
    //     System.out.println("Jumping: " + yJumpingVelocity + " Y pos: " + hero.getyPos());
        return temp;
    }

}
