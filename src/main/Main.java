package main;

import main.graphics.*;

import java.util.TimerTask;
import java.util.Timer;


public class Main {

    static GameFrame gameFrame;
    public Main() {

        Timer timer = new Timer();
        gameFrame = new GameFrame();

        timer.schedule( new TimerTask() {
            public void run() {
                gameFrame.update();
            }
        }, 0, Globals.getRefreshrate());
    }

    public static void main(String[] args) {
        new Main();
    }


}
