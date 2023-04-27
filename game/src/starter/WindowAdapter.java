package starter;

import com.badlogic.gdx.Gdx;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Used to check if the player has closed the GameOver Screen and if so, closes the Game.
 */
public class WindowAdapter implements WindowListener {


    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {
        Gdx.app.exit();
    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
