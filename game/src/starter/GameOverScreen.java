package starter;

import com.badlogic.gdx.Gdx;
import ecs.entities.Entity;
import ecs.entities.Hero;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Iterator;
import java.util.Set;

public class GameOverScreen extends JFrame {

    private static final int SCREEN_WIDTH = 500;
    private static final int SCREEN_HEIGHT = 150;
    private JButton exitButton = new JButton("Leave Game");
    private JButton newGameButton = new JButton("New Game");
    private JLabel lvlReached = new JLabel("Level Reached: " + Game.getLevelReached());
    private JPanel panel = new JPanel();

    /**
     * Creates a GameOver Screen where the player can select if he wants to exit the
     * game after a death or restart it.
     */
    public GameOverScreen() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.setTitle("Game Over");
        this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        panel.add(lvlReached);
        panel.add(exitButton);
        panel.add(newGameButton);
        this.add(panel);
        ActionListener listenerExit = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameOverScreen.super.dispose();
                Gdx.app.exit();
            }
        };
        exitButton.addActionListener(listenerExit);

        ActionListener listenerNewGame = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Removes all Entities from the current Level and replaces the old dead Hero with a new one.
                GameOverScreen.super.dispose();
                Hero hero = new Hero();
                Game.resetLevelReached();
                Set<Entity> allEntities = Game.getEntities();
                Iterator<Entity> entityIterator = allEntities.iterator();
                while (entityIterator.hasNext()) {
                    Game.removeEntity(entityIterator.next());
                }
                Game.togglePause();
                Game.setHero(hero);
            }
        };
        newGameButton.addActionListener(listenerNewGame);
        WindowListener windowListener = new WindowAdapter();
    }
}
