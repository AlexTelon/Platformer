package main.graphics;

import main.Hero;
import main.Input;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.*;


/**
 * @author Alex Telon
 */
public class GameFrame extends JFrame implements KeyListener {
    private final JMenu menu = new JMenu("Menu");
    private GraphicalViewer graphicalViewer;
    private Hero hero = new Hero();
    int debug = 0;

    public GameFrame() {
        this.graphicalViewer = new GraphicalViewer();
        this.add(graphicalViewer, BorderLayout.CENTER);

        menu.add(new JMenuItem(closeAction));
        final JMenuBar bar = new JMenuBar();
        bar.add(menu);
        repaint();
        this.setJMenuBar(bar);
        this.addKeyListener(this);
        this.setLayout(new FlowLayout());
        this.pack();
        this.setVisible(true);
    }


    public void keyPressed( KeyEvent e ) {
    }
    public void keyReleased( KeyEvent e ) {
        switch (e.getKeyChar()) {
            case 'a' : hero.handleInput(Input.data.RELEASE_LEFT);
                break;
            case 'd': hero.handleInput(Input.data.RELEASE_RIGHT);
                break;
            case 'w' : hero.handleInput(Input.data.RELEASE_UP);
                break;
            case 's': hero.handleInput(Input.data.RELEASE_DOWN);
                break;
            default:
        }
    }
    public void keyTyped( KeyEvent e ) {
        switch (e.getKeyChar()) {
            case 'a' : hero.handleInput(Input.data.PRESS_LEFT);
            //    System.out.println("Left");
                break;
            case 'd': hero.handleInput(Input.data.PRESS_RIGHT);
           //     System.out.println("Right");
                break;
            case 'w' : hero.handleInput(Input.data.PRESS_UP);
            //    System.out.println("Up");
                break;
            case 's': hero.handleInput(Input.data.PRESS_DOWN);
            //    System.out.println("Down");
                break;
            default:
        }

    }

    Action closeAction = new AbstractAction("Close !") {
        {
            putValue(SHORT_DESCRIPTION, "Closes the game");
        }
        public void actionPerformed(ActionEvent evt) {
            System.out.println("close down the game??");
            System.out.println("Debug: " + debug);
            int answer = JOptionPane.showConfirmDialog
                    (menu, "Do you really want to hurt me, do you really want to make me cry?", "Quit?",
                            JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    };

    public void update() {
        graphicalViewer.setHero(hero);
        hero.update();
        repaint();
    }


}

