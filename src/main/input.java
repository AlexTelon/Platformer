package main;

/**
 * @author Alex Telon
 */
public class Input {
    private data pressAndHold = null;

    public static enum data {
        PRESS_UP, PRESS_DOWN, PRESS_LEFT, PRESS_RIGHT,
        RELEASE_UP, RELEASE_DOWN, RELEASE_LEFT, RELEASE_RIGHT,
        NO_INPUT
    }

    public data getPressAndHold() {
        return pressAndHold;
    }

    public void setPressAndHold(data pressAndHold) {
        this.pressAndHold = pressAndHold;
    }
}
