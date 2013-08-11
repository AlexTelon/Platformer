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

    private int height = Globals.getHeightInBoxes() * Box.getSide();
    private int width = Globals.getWidthInBoxes() * Box.getSide();
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
        paintBackground(g2);
        paintBoxes(g2);

        // Debug info
        g2.setColor(Color.black);
        g2.drawString(hero.getState().toString(), 100, 25);


    }

    private void paintBoxes(Graphics2D g2) {
        int leftSide = hero.getMapProgression();
        int rightSide = leftSide + Globals.getWidthInBoxes();
        Box box;
        for (int x = leftSide; x < rightSide; x++) {
            for (int y = 0; y < Globals.getHeightInBoxes(); y++) {
                box = hero.getMap().getBoxMap()[y][x];
                if (box != null) {
                    g2.drawImage(box.getImg(),null,x*Box.getSide(), y*Box.getSide());
                }
            }
        }
    }

    private void paintBackground(Graphics2D g2) {
       // g2.setColor(SystemColor.green);
    //    g2.fillRect(0, 280, 500, 120);
    }



    private void paintHero(Graphics2D g2) {

        // Drawing the rotated image at the required drawing locations
        //     g2.drawImage(hero.getOp().filter(img, null), hero.getxPos(), hero.getyPos(), null);
        img = hero.getState().getImg();
        g2.drawImage(img, hero.getxPos(), hero.getyPos(), null);
    }


    public void setHero(Hero hero) {
        this.hero = hero;
    }



}


