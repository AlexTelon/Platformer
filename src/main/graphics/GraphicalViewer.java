package main.graphics;

import main.Globals;
import main.Hero;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.*;
import main.map.Box;

/**
 * The main component of GameFrame, this is what paints the game itself.
 */
public class GraphicalViewer extends JComponent {
    private Hero hero;

    private int height = Globals.getScreenHeightInBoxes() * Box.getSide();
    private int width = Globals.getScreenWidthInBoxes() * Box.getSide();
    BufferedImage img = null;

    public GraphicalViewer() {
        try {
            img = ImageIO.read(new File("right.png"));
        } catch (IOException e) {
            System.out.println("ERROR IN READING PICTURE");
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(PreferredWidth(),PreferredHeight());
    }

    private int PreferredHeight() {
        return height;
    }
    private int PreferredWidth() {
        return width;
    }

    /**
     * Paints everything
     * @param g
     */
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        paintHero(g2);
        paintBoxes(g2);

        // Debug info
        g2.setColor(Color.black);
        g2.drawString(hero.getState().toString(), 100, 25);
       if (hero.isRunning()) {
           g2.drawString("RUNNING", 500, 25);
       } else {
           g2.drawString("NOT RUNNING", 500, 25);
       }
        g2.drawString(hero.getDirection().toString(), 100, 50);
        g2.drawString("pix offset " + Camera.getPixOffset(), 100, 75);
        g2.drawString("Left side " + Camera.getLeftSide(), 100, 100);
        g2.drawString("Right side " + Camera.getRightSide(), 100, 125);
        g2.drawString("Box offset " + Camera.getBoxOffset(), 100, 150);



    }

    private void paintBoxes(Graphics2D g2) {
        int leftSide = Camera.getLeftSide();
        int rightSide = Camera.getRightSide();
        int leftBoxSide = hero.pixelPosToBoxPos(leftSide);
        int rightBoxSide = hero.pixelPosToBoxPos(rightSide);

        Box box;
        for (int x = leftBoxSide; x < rightBoxSide; x++) {
            for (int y = 0; y < hero.getMap().getMapHeight(); y++) {
                box = hero.getMap().getBoxMap()[y][x];
                if (box != null) {
                    g2.drawImage(box.getImg(),null,(x-Camera.getBoxOffset())*Box.getSide(), y*Box.getSide());
                    g2.drawString("" + x,(x-Camera.getBoxOffset())*Box.getSide()+6, y*Box.getSide()+20);
                }
            }
        }
    }

/*
    private void paintBoxes(Graphics2D g2) {
        int leftSide = hero.getMapProgression();
        int rightSide = leftSide + Globals.getScreenWidthInBoxes();
        Box box;
        for (int x = leftSide; x < rightSide; x++) {
            for (int y = 0; y < Globals.getScreenHeightInBoxes(); y++) {
                box = hero.getMap().getBoxMap()[y][x];
                if (box != null) {
                    g2.drawImage(box.getImg(),null,x*Box.getSide(), y*Box.getSide());
                }
            }
        }
    }*/

    private void paintHero(Graphics2D g2) {
        img = hero.getState().getImg();
        g2.drawImage(img, hero.getxPos(), hero.getyPos(), null);
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }



}


