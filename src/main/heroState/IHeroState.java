package main.heroState;

import main.Hero;
import main.input;

import java.awt.image.BufferedImage;

/**
 * @author Alex Telon
 * A interface for all main.hero states
 * TODO create a statePool somewhere so we do not create new states all the time but reuse them.
 */
public interface IHeroState {
    public void handleInput(Hero hero, input.data in);
    public void update(Hero hero);
    public void enter(Hero hero, IHeroState state);
    public BufferedImage getImg();
}
