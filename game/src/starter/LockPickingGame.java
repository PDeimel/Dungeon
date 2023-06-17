package starter;

import ecs.entities.Chest;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/** Creates a minigame in which the player has to activate a set of button in the right order,
 *  randomized and with a time limit of 5 seconds each time.
 */
public class LockPickingGame extends JFrame {
    private final int FRAME_WIDTH = 400;
    private final int FRAME_HEIGHT = 300;
    private final int PIN_COUNT = 5;
    private final int PIN_WIDTH = 40;
    private final int PIN_HEIGHT = 80;
    private final int PIN_SPACING = 10;
    private final int CORNER_RADIUS = 10;
    private final Color UNLOCKED_COLOR = Color.GREEN;
    private final Color LOCKED_COLOR = Color.RED;
    private final Color SELECTED_COLOR = Color.YELLOW;
    private final Font TIMER_FONT = new Font("Arial", Font.BOLD, 20);
    private List<Integer> CORRECT_SEQUENCE;
    private final List<Integer> playerSequence;
    private final boolean[] pinsStatus;
    private int selectedPin = -1;
    private Timer timer;
    private int timeRemaining;
    private Chest chest;
    private final Logger minigameLogger = Logger.getLogger(this.getClass().getSimpleName());

    private class LockPanel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int startX = (getWidth() - (PIN_COUNT * (PIN_WIDTH + PIN_SPACING))) / 2;
            int startY = (getHeight() - PIN_HEIGHT) / 2;

            for (int i = 0; i < PIN_COUNT; i++) {
                int currentX = startX + (i * (PIN_WIDTH + PIN_SPACING));
                int currentY = startY;
                Color pinColor = LOCKED_COLOR;
                if (pinsStatus[i]) {
                    pinColor = UNLOCKED_COLOR;
                }
                if (i == selectedPin) {
                    pinColor = SELECTED_COLOR;
                }
                if (i == getNextPinIndex()) {
                    pinColor = pinColor.darker();
                }
                g2d.setColor(pinColor);
                g2d.fillRoundRect(
                    currentX, currentY, PIN_WIDTH, PIN_HEIGHT, CORNER_RADIUS, CORNER_RADIUS);
            }
        }

        private int getNextPinIndex() {
            int nextIndex = playerSequence.size();
            if (nextIndex < CORRECT_SEQUENCE.size()) {
                return CORRECT_SEQUENCE.get(nextIndex);
            }
            return -1;
        }

        private int getSelectedPinIndex(int mouseX, int mouseY) {
            int startX = (getWidth() - (PIN_COUNT * (PIN_WIDTH + PIN_SPACING))) / 2;
            int startY = (getHeight() - PIN_HEIGHT) / 2;

            for (int i = 0; i < PIN_COUNT; i++) {
                int currentX = startX + (i * (PIN_WIDTH + PIN_SPACING));

                if (mouseX >= currentX
                    && mouseX <= currentX + PIN_WIDTH
                    && mouseY >= startY
                    && mouseY <= startY + PIN_HEIGHT) {
                    return i;
                }
            }
            return -1;
        }

        private void checkSequence() {
            if (playerSequence.size() == CORRECT_SEQUENCE.size()) {
                for (int i = 0; i < CORRECT_SEQUENCE.size(); i++) {
                    if (!Objects.equals(playerSequence.get(i), CORRECT_SEQUENCE.get(i))) {
                        resetGame();
                        minigameLogger.info("Order has been neglected, resulting in loss");
                        onPuzzleSolved(false);
                        return;
                    }
                }
                if (playerSequence.equals(CORRECT_SEQUENCE)) {
                    minigameLogger.info("Order has been kept, rewards will follow");
                    onPuzzleSolved(true);
                }
            }
        }

        private void resetGame() {
            playerSequence.clear();
            Arrays.fill(pinsStatus, false);
            selectedPin = -1;
            stopTimer();
        }

        private void stopTimer() {
            if (timer != null) {
                timer.stop();
                timer = null;
            }
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setFont(TIMER_FONT);
            g2d.setColor(Color.BLACK);
            g2d.drawString(String.valueOf(timeRemaining), getWidth() / 2 - 10, 30);
        }
    }

    /**
     * Is activated once the Lockpick has finished and either opens the chest or informs
     * the player that he failed to solve the minigame.
     *
     * @param solved Result of the SequenceCheck
     */
    private void onPuzzleSolved(boolean solved) {
        if (solved) {
            LockPickingGame.super.dispose();
            chest.dropItems(chest);
            minigameLogger.info("The chests items have been dropped");
        } else {
            LockPickingGame.super.dispose();
        }
        synchronized (LockPickingGame.this) {
            LockPickingGame.this.notify();
        }
    }

    private void startTimer() {
        timeRemaining = 5;
        timer =
            new Timer(
                1000,
                e -> {
                    timeRemaining--;
                    if (timeRemaining <= 0) {
                        synchronized (LockPickingGame.this) {
                            LockPickingGame.this.notify();
                        }
                        onPuzzleSolved(false);
                    }
                    repaint();
                });
        timer.start();
    }

    /**
     * Creates a new lockpicking-minigame with a randomized order each time
     */
    public LockPickingGame(Chest chest) {
        this.chest = chest;
        setTitle("Lockpicking Minigame");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        LockPanel lockPanel = new LockPanel();
        add(lockPanel);
        generateRandomSequence();
        playerSequence = new ArrayList<>();
        pinsStatus = new boolean[PIN_COUNT];
        lockPanel.addMouseListener(
            new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    int mouseX = e.getX();
                    int mouseY = e.getY();
                    int selectedPinIndex = lockPanel.getSelectedPinIndex(mouseX, mouseY);
                    if (selectedPinIndex != -1) {
                        selectedPin = selectedPinIndex;
                        playerSequence.add(selectedPin);
                        pinsStatus[selectedPin] = true;
                        lockPanel.checkSequence();
                        lockPanel.repaint();
                    }
                }
            });
        setVisible(true);
    }

    private void generateRandomSequence() {
        List<Integer> sequence = new ArrayList<>();
        for (int i = 0; i < PIN_COUNT; i++) sequence.add(i);
        Collections.shuffle(sequence);
        CORRECT_SEQUENCE = sequence;
    }

    /**
     * Used to activate the lockpicking-game from other classes, mainly the chest
     */
    public static void playLockPickingGameAndWait(Chest chest) {
        LockPickingGame game = new LockPickingGame(chest);
        game.startTimer();
    }
}
