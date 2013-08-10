package main;
import main.heroState.*;
import main.map.Box;
import main.map.BoxMap;
import main.map.MapCreator;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
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
    private int mapProgression = 0; // which is the leftmost position on the screen
    private BoxMap map = new BoxMap();
    public static enum Direction {
        LEFT, RIGHT;
    }
    private Direction direction = Direction.RIGHT;
    AffineTransform tx = AffineTransform.getTranslateInstance(0, 0);
    // Should do nothing at the start
    AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
    public Hero() {
        // TODO
        // this is only to "fix" a bug
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
        this.yPos += y;
    }

    public void rotateImg (int degrees) {
        AffineTransform temp = AffineTransform.getRotateInstance(Math.toRadians(degrees), 15, 15);
        op = new AffineTransformOp(temp, AffineTransformOp.TYPE_BILINEAR);
    }

    public AffineTransformOp getOp() {
        return op;
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
        System.out.println("input peek " + InputStack.peek().toString());
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

    /**
     * Makes input right/left into direction right/left.
     * DO NOT USE OTHER THAN PRESS_LEFT or PRESS_RIGHT
     * @param input (right/left)
     * @return a direction
     */
    public static Direction inputToDirection(input.data input) {
        if (input == main.input.data.PRESS_LEFT) {
            return Direction.LEFT;
        }
        return Direction.RIGHT;
    }

    public BoxMap getMap() {
        return map;
    }

    public int getMapProgression() {
        return mapProgression;
    }
}
