package main;

import main.graphics.*;

import java.util.TimerTask;
import java.util.Timer;


public class Main {

    static double refreshrate = 50;
    static int refRate = (int) refreshrate;
    static GameFrame gameFrame;
    public Main() {

        Timer timer = new Timer();
        gameFrame = new GameFrame();

        timer.schedule( new TimerTask() {
            public void run() {
                gameFrame.update();
            }
        }, 0, refRate);
    }

    public static void main(String[] args) {
        new Main();
    }

}