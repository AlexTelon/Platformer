package main;
import main.graphics.Camera;
import main.heroState.*;
import main.map.Box;
import main.map.BoxMap;
import main.map.MapCreator;

import java.util.Stack;

/**
 * @author Alex Telon
 */
public class Hero {
    private IHeroState state = new StandingState(this.getDirection());
    private Stack StateStack = new Stack();
    private Stack InputStack = new Stack();
    private int xPos = 0; // start pos in pixels
    private int yPos = 150; // start pos in pixels
    private int height = 30;
    private int width = 30;
    private int runnigSpeed = 10; // the quantitive speed
    private int mapProgression = 0; // which is the leftmost position on the screen
    private int xVelocity = 0;
    private double yVelocity = 0;
    private boolean isAlive = true;
    private boolean newInput = false;
    private BoxMap map = new BoxMap();
    private boolean isRunning = false;
    private Input.data runningReleasedOn; // determines how isRunning is set to false
    private Direction direction = Direction.RIGHT;
    private Camera camera;
    public static enum Direction {
        LEFT, RIGHT;
    }

    public Hero() {
        // TODO
        // this is only to "fix" a bug
        InputStack.push(Input.data.PRESS_RIGHT);
        MapCreator.createMap(this);
        camera = new Camera(this);
    }

    public void handleInput(Input.data input) {
        this.inputStackPush(input);
        if (input == runningReleasedOn) {
            isRunning = false;
        }
        state.handleInput(this, input);
    }

    public void update() {
        state.update(this);
    }
    public void changeStateTo(IHeroState state) {
        this.stateStackPush(this.state);
        this.state = state;
        state.enter(this, state);
    }

    /**
     *
     * @return Heroes x position in pixels relative to the top. (top is 0)
     */
    public int getxPos() {
        return xPos;
    }

    public void addxPos(int x) {
        int xNew = xPos + x;

        // This makes the hero be able to go above the screen
        if (this.yPos <= 0) {
            this.xPos = xNew;
            Camera.update(xNew);
            return;
        }

        if (xNew <= 0) {
            this.xPos = 0;
            return;
        }

        if (x > 0) {
            if (isSolid(this.getMap().getBoxMap()[pixelPosToBoxPos(this.yPos)][pixelPosToBoxPos(xNew+Box.getSide())])) {
                xNew = pixelPosToBoxPos(xNew)*Box.getSide(); // we have collided
            } else if (!this.onGround() &&   // if we are on ground, dont check for horizontal collisions at the hero's feet
                    isSolid(this.getMap().getBoxMap()[pixelPosToBoxPos(this.yPos+Box.getSide())][pixelPosToBoxPos(xNew+Box.getSide())])) {
                xNew = pixelPosToBoxPos(xNew)*Box.getSide();
            }
        } else if (x < 0) {
            if (isSolid(this.getMap().getBoxMap()[pixelPosToBoxPos(this.yPos)][pixelPosToBoxPos(xNew)])) {
                xNew = (pixelPosToBoxPos(xNew)+1)*Box.getSide();
            }else if (!this.onGround() &&
                    isSolid(this.getMap().getBoxMap()[pixelPosToBoxPos(this.yPos+Box.getSide())][pixelPosToBoxPos(xNew)])) {
                xNew = (pixelPosToBoxPos(xNew)+1)*Box.getSide();
            }
        }
        this.xPos = xNew;
        Camera.update(xNew);
    }

    public int getyPos() {
        return yPos;
    }

    public void addyPos(int y) {
        int yNew= yPos + y;
        int yOldFeet = this.yPos + Box.getSide();
        int yNewFeet = yNew + Box.getSide();
        int xBoxPosLeft = pixelPosToBoxPos(xPos);
        int xBoxPosRight = pixelPosToBoxPos(xPos+Box.getSide());
        int yOldBoxPosFeet = pixelPosToBoxPos(yOldFeet);

        // This makes the hero be able to go above the screen
        if (yNew <= 0) {
            this.yPos = yPos + y;
            return;
        }

        for (int i = yOldBoxPosFeet; i < Globals.getScreenHeightInBoxes(); i++) {
            if (isSolid(this.getMap().getBoxMap()[i][xBoxPosLeft]) ||
                    isSolid(this.getMap().getBoxMap()[i][xBoxPosRight]) ) {
                if (yNewFeet < i*Box.getSide()) {
                    // feet of hero will be over a box. -> OK
                    this.yPos = yPos + y;
                } else {
                    // else we would end up inside/below a box
                    // so we put it on the box
                    this.yPos = (i-1)*Box.getSide();
                }
                return;
            }
        }
    }


    public IHeroState getState() {
        return state;
    }

    /***
     * the stacks....
     */
    public void stateStackPush(IHeroState state) {
        StateStack.push(state);
    }

    public IHeroState stateStackPeek() {
        return (IHeroState)  StateStack.peek();
    }

    public IHeroState stateStackPop() {
        return (IHeroState) StateStack.pop();
    }

    //TODO change the way we handle input?
    public void inputStackPush(Input.data input) {
        //    System.out.println("Input push " + input.toString());
        //  InputStack.clear();
        this.setNewInput(true);
        InputStack.push(input);
    }

    public Input.data inputStackPeek() {

        if (newInput) {
            this.setNewInput(false);
            //     System.out.println("input peek " + InputStack.peek().toString());
            return (Input.data) InputStack.peek();
        }
        this.setNewInput(false);
        //    System.out.println("input peek " + Input.data.NO_INPUT.toString());
        InputStack.push(Input.data.NO_INPUT);
        return Input.data.NO_INPUT;
    }

    /**
     * Which is the heroes direction?
     */
    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public BoxMap getMap() {
        return map;
    }

    public int getMapProgression() {
        return mapProgression;
    }

    public boolean onGround() {
        int yHeroFeetPos = this.getyPos()+this.height;
        int xHeroFeetPosLeft = this.getxPos();
        int xHeroFeetPosRight = xHeroFeetPosLeft + this.width;

        if (pixelPosToBoxPos(yHeroFeetPos) <= 0) {
            return false;
        }
        // The hero can stand on boxes in 3 ways.
        // 1 - perfectly on one box only
        // 2 - left side is on a box
        // 3 - right side is on a box
        // The below if statement sees if any of the above is the case, otherwise we should fall
        if (isSolid(this.getMap().getBoxMap()[pixelPosToBoxPos(yHeroFeetPos)][pixelPosToBoxPos(xHeroFeetPosLeft)]) ||
                isSolid(this.getMap().getBoxMap()[pixelPosToBoxPos(yHeroFeetPos)][pixelPosToBoxPos(xHeroFeetPosRight)])) {
            return true;
        }
        System.out.println(pixelPosToBoxPos(xHeroFeetPosLeft) + " " + pixelPosToBoxPos(xHeroFeetPosRight));
        System.out.println(pixelPosToBoxPos(yHeroFeetPos));
        return false;
    }

    /**
     * solid box checks if it is null or if the box is there but inactive in some way
     * @param box
     * @return
     */
    private boolean isSolid(Box box) {
        if (box == null || !box.isActive()) {
            return false;
        }
        return true;
    }

    /**
     *
     * @param pixelPos in pixels
     * @return pos in "boxes"
     */
    public int pixelPosToBoxPos(int pixelPos) {
        return pixelPos / Box.getSide();
    }

    public int getRunnigSpeed() {
        return runnigSpeed;
    }

    public boolean getNewInput() {
        return newInput;
    }

    public void setNewInput(boolean newInput) {
        this.newInput = newInput;
    }

    /**
     * Tells the hero it is running, also decleares which key
     * has to be released before running can be set to false.
     * This way only a release left can make a hero running to
     * the left stop running. For the player this means that
     * while holding down one of LEFT/RIGHT the other will not
     * do any difference.
     *
     * If the hero already is running this does nothing. This so that
     * if a player presses RIGHT while he is already holding LEFT
     * the input RIGHT will be ignored.
     * @param input
     */
    public void setRunning(Input.data input) {
        if (!isRunning) {
            isRunning = true;
            if (input == Input.data.PRESS_LEFT) {
                runningReleasedOn = Input.data.RELEASE_LEFT;
            } else if (input == Input.data.PRESS_RIGHT) {
                runningReleasedOn = Input.data.RELEASE_RIGHT;
            }
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    public int getxVelocity() {
        return xVelocity;
    }

    public void addxVelocity(int xVelocity) {
        if (xVelocity > 0) {
            setDirection(Direction.RIGHT);
        } else if (xVelocity < 0) {
            setDirection(Direction.LEFT);
        }
        this.xVelocity += xVelocity;
    }

    public void setxVelocity(int xVelocity) {
        if (xVelocity > 0) {
            setDirection(Direction.RIGHT);
        } else if (xVelocity < 0) {
            this.direction = Direction.LEFT;
        }
        System.out.println(xVelocity);
        this.xVelocity = xVelocity;
    }

    public int getyVelocity() {
        return (int) yVelocity;
    }

    public void addyVelocity(double yVelocity) {
        this.yVelocity += yVelocity;
    }

    public void setyVelocity(double yVelocity) {
        this.yVelocity = yVelocity;
    }

    public int getxStartingPosition() {
        return xPos;
    }

}
