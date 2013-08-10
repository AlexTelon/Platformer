package main;
import main.heroState.*;

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
    private int xPos = 150;
    private int yPos = 250;
    public static enum Direction {
        LEFT, RIGHT
    }
    private Direction direction = Direction.RIGHT;
    AffineTransform tx = AffineTransform.getTranslateInstance(0, 0);
    // Should do nothing at the start
    AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

    public void handleInput(input.data input) {
        InputStack.push(input);
        state.handleInput(this, input);
    }
    public void update() {
        state.update(this);
    }

    public void changeStateTo(IHeroState state) {
        StateStack.push(this.state);
        this.state = state;
        state.enter(this, state);
    }

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
        InputStack.push(input);
    }

    public input.data inputStackPeek() {
        return (input.data) InputStack.peek();
    }

    public input.data inputStackPop() {
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

}
