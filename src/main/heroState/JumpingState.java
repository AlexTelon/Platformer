package main.heroState;

import main.Globals;
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
    private double yJumpingVelocity = -50;
    private int xJumpingVelocity = 0;


    public JumpingState() {
        try {
            img = ImageIO.read(new File("up.png"));
        } catch (IOException e) {
            System.out.println("ERROR IN READING PICTURE");
        }
    }

    public JumpingState(int runnigVelocity) {
        try {
            img = ImageIO.read(new File("up.png"));
        } catch (IOException e) {
            System.out.println("ERROR IN READING PICTURE");
        }
        xJumpingVelocity = runnigVelocity;
    }

    @Override
    public void handleInput(Hero hero, input.data in) {
        if (in == input.data.PRESS_UP) {
        } else if( in == input.data.PRESS_DOWN) {
            // pressing down should end the jump
            hero.changeStateTo(new FallingState(yJumpingVelocity, xJumpingVelocity));
        } else if ( in == input.data.PRESS_LEFT) {
            
        } else if ( in == input.data.PRESS_RIGHT) {
            
        }
    }

    @Override
    public void update(Hero hero) {
        if (hero.onGround()) {
            yJumpingVelocity = 0;
            hero.changeStateTo(new StandingState());
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
        hero.addyPos(-5);
        handleInput(hero, hero.inputStackPop());
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
        //System.out.println("what we add: " + 9.81 + "*" + Globals.getTimeIntervalMS() + "*" + Globals.pixelsPerMeter());
      //  System.out.println((9.81*Globals.getTimeIntervalMS()/Globals.pixelsPerMeter()));
        Globals Globals;
        yJumpingVelocity += (9.81* main.Globals.getTimeIntervalMS()/ main.Globals.pixelsPerMeter());
    }
    
        private int velocityToInt(Hero hero) {
        int temp = (int) yJumpingVelocity;
         System.out.println("Jumping: " + yJumpingVelocity + " Y pos: " + hero.getyPos());
        return temp;
    }

}
