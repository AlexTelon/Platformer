package main;
import main.heroState.*;
import main.map.Box;
import main.map.BoxMap;
import main.map.MapCreator;

import java.util.Stack;

/**
 * @author Alex Telon
 */
public class Hero {
    private IHeroState state = new StandingState();
    private Stack StateStack = new Stack();
    private Stack InputStack = new Stack();
    private int xPos = 150; // start pos in pixels
    private int yPos = 150; // start pos in pixels
    private int height = 30;
    private int width = 30;
    private int mapProgression = 0; // which is the leftmost position on the screen
    private boolean isAlive = true;
    private BoxMap map = new BoxMap();
    public static enum Direction {
        LEFT, RIGHT;
    }
    private Direction direction = Direction.RIGHT;

    public Hero() {
        // TODO
        // this is only to "fix" a bug
        System.out.println("DERP");
        InputStack.push(input.data.PRESS_RIGHT);
        InputStack.push(input.data.PRESS_RIGHT);
        MapCreator.createMap(this);
    }

    public void handleInput(input.data input) {
        this.inputStackPush(input);
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
        this.xPos += x;
    }

    public int getyPos() {
        return yPos;
    }

    public void addyPos(int y) {
        int yNew= yPos + y;
        int yOldFeet = this.yPos + Box.getSide();
        int yNewFeet = yNew + Box.getSide();
        int xBoxPos = pixelPosToBoxPos(xPos);
        int yBoxPosFeet = pixelPosToBoxPos(yNew+Box.getSide());
        int yOldBoxPosFeet = pixelPosToBoxPos(yOldFeet);

        if (yBoxPosFeet >= Globals.getHeightInBoxes()) {
            // hero has fallen too low!
            for (int i = yOldBoxPosFeet-1; i < Globals.getHeightInBoxes()-1; i++) {
                if (isSolid(this.getMap().getBoxMap()[i][xBoxPos])) {
                    // else we would end up inside/below a box
                    // so we put it on the box
                    this.yPos = (i-1)*Box.getSide();
                    return;
                }
            }
        } else {
            for (int i = yOldBoxPosFeet-1; i < Globals.getHeightInBoxes(); i++) {
                if (isSolid(this.getMap().getBoxMap()[i][xBoxPos])) {
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

    public void inputStackPush(input.data input) {
        System.out.println("input push " + input.toString());
        InputStack.push(input);
    }

    public input.data inputStackPeek() {
        //    System.out.println("input peek " + InputStack.peek().toString());
        return (input.data) InputStack.peek();
    }

    public input.data inputStackPop() {
        System.out.println("input pop " + InputStack.peek().toString());
        return (input.data) InputStack.pop();
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

        if (pixelPosToBoxPos(yHeroFeetPos) >= Globals.getHeightInBoxes()) {
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
    private int pixelPosToBoxPos(int pixelPos) {
        return pixelPos / Box.getSide();
    }




}
