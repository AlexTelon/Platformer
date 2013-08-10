package main.map;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author Alex Telon
 * The building blocks that the world is made of.
 */
public class Box {
    private BufferedImage img = null; // standard sized img
    private boolean active = true; // is the box active. False if for example destroyed
    private static int side = 30;

    public Box() {
        try {
            img = ImageIO.read(new File("Box.png"));
        } catch (IOException e) {
            System.out.println("ERROR IN READING PICTURE");
        }
        active = true;
    }

    public static int getSide() {
        return side;
    }

    public boolean isActive() {
        return active;
    }

    public BufferedImage getImg() {
        return img;
    }

}
