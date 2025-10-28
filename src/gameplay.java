package proj.snake.src;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    // instantiate snake object
    Snake snake = new Snake();

    // instantiate apple object
    Apple apple = new Apple();

    // create snake head image
    private ImageIcon snakeHead;

    private Timer timer;
    private int delay = 500;
    private ImageIcon snakeBody;

    AtomicBoolean speedUp = new AtomicBoolean(true);

    // snake head position coordinates
    private int snakeHeadXPos = 379;

    // Create apple image
    private ImageIcon appleImage;

    // For generating random number
    private Random random = new Random();

    private int xPos = random.nextInt(100);
    private int yPos = random.nextInt(100);

    // Create game title
    private ImageIcon titleImage;

    // Create game score
    Score score = new Score();

    // Create Highscore
    private String highScore;

    // For displaying controller
    private ImageIcon arrowImage;
    private ImageIcon shiftImage;

    public Gameplay() {
        // for when the game starts
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {
        // check if the game has started
        if (snake.moves == 0) {
            for (int i = 0; i < 5; i++) {
                snake.snakexLength[i] = snakeHeadXPos;
                snakeHeadXPos -= 6;
                snake.snakeyLength[i] = 355;
            }
        }

        // title border
        g.setColor(Color.WHITE);
        g.drawRect(24, 10, 852, 55);

        // title
        titleImage = new ImageIcon("images/title.png");
        titleImage.paintIcon(this, g, 25, 11);

        // border for gameplay
        g.setColor(Color.WHITE);
        g.drawRect(24, 71, 620, 614);

        // gameplay background
        g.setColor(Color.black);
        g.fillRect(25, 72, 619, 613);

        // border for leaderboard
        g.setColor(Color.WHITE);
        g.drawRect(653, 71, 223, 614);

        // leaderboard background
        g.setColor(Color.black);
        g.fillRect(654, 72, 221, 613);

        // Display Score
        g.setColor(Color.white);
        g.setFont(new Font("Helvetica", Font.BOLD, 20));
        g.drawString("SCORE : " + score.getScore(), 720, 110);
        g.drawRect(653, 130, 221, 1);

        // Display HighScore
        score.sortHighScore();
        highScore = score.getHighScore();
        g.drawString("HIGHSCORE", 705, 180);
        drawString(g, highScore, 705, 200);

        // Display Controller
        g.drawRect(653, 490, 221, 1);
        g.setFont(new Font("Helvetica", Font.BOLD, 25));
        g.drawString("CONTROLS", 690, 530);

        arrowImage = new ImageIcon("images/keyboardArrow.png");
        arrowImage.paintIcon(this, g, 670, 560);
        g.setFont(new Font("Helvetica", Font.PLAIN, 16));
        g.drawString("Movement", 770, 590);

        shiftImage = new ImageIcon("images/shift.png");
        shiftImage.paintIcon(this, g, 695, 625);
        g.drawString("Boost", 770, 640);

        // instantiate image for snake head
        snakeHead = new ImageIcon("images/snakeHead4.png");
        snakeHead.paintIcon(this, g, snake.snakexLength[0], snake.snakeyLength[0]);

        for (int i = 0; i < snake.lengthOfSnake; i++) {
            if (i == 0 && (snake.right || snake.left || snake.up || snake.down)) {
                snakeHead = new ImageIcon("images/snakeHead4.png");
                snakeHead.paintIcon(this, g, snake.snakexLength[i], snake.snakeyLength[i]);
            }
            if (i != 0) {
                snakeBody = new ImageIcon("images/snakeimage4.png");
                snakeBody.paintIcon(this, g, snake.snakexLength[i], snake.snakeyLength[i]);
            }
        }

        appleImage = new ImageIcon("images/apple4.png");

        // If the snake eats the apple
        if ((apple.applexPos[xPos]) == snake.snakexLength[0] && (apple.appleyPos[yPos] == snake.snakeyLength[0])) {
            snake.lengthOfSnake++;
            score.increaseScore();
            xPos = random.nextInt(100);
            yPos = random.nextInt(100);

            // speed up the snake's movement every time the score reaches a multiple of 5
            if (score.getScore() % 5 == 0 && score.getScore()!= 0){
                if(delay > 100){
                    delay = delay - 100;
                }
                else if (delay == 100){
                    delay = delay - 50;
                }
                else if (delay <= 50 && delay > 20){
                    delay = delay - 10;
                }
                else {
                    delay = 20;
                }
                timer.setDelay(delay);
            }
        }

        // Before the user presses the spacebar, the apple is not visible
        if (snake.moves != 0) {
            appleImage.paintIcon(this, g, apple.applexPos[xPos], apple.appleyPos[yPos]);
        }

        // display the text "Press Spacebar to Start the Game!"
        if (snake.moves == 0) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Courier New", Font.BOLD, 26));
            g.drawString("Press Spacebar to Start the Game!", 70, 300);
        }

        // Check if the head hits the body
        for (int i = 1; i < snake.lengthOfSnake; i++) {
            // if collision occurs
            if (snake.snakexLength[i] == snake.snakexLength[0] && snake.snakeyLength[i] == snake.snakeyLength[0]) {
                // call dead function
                snake.dead();
            }
        }

        // Check if dead
        if (snake.death) {
            // Save the Score to the highscore.dat file
            score.saveNewScore();

            // display the text "Game Over!"
            g.setColor(Color.RED);
            g.setFont(new Font("Courier New", Font.BOLD, 50));
            g.drawString("Game Over!", 190, 340);

            // display score
            g.setColor(Color.GREEN);
            g.setFont(new Font("Courier New", Font.BOLD, 18));
            g.drawString("Your Score : " + score.getScore(), 250, 370);

            // display the text "Press Spacebar to restart!"
            g.setColor(Color.WHITE);
            g.setFont(new Font("Courier New", Font.BOLD, 20));
            g.drawString("Press Spacebar to restart!", 187, 400);
        }
        g.dispose();
    }

    // Void for displaying a string with \n on the screen
    public void drawString(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        timer.start();

        // for snake movement
        // move snake to the right
        if (snake.right) {
            // call function in Snake class to move the snake to the right
            snake.movementRight();
            // automatically call the paint method again
            repaint();
        }
        // move snake to the left
        if (snake.left) {
            // call function in Snake class to move the snake to the left
            snake.movementLeft();
            // automatically call the paint method again
            repaint();
        }
        // move snake upwards
        if (snake.up) {
            // call function in Snake class to move the snake upwards
            snake.movementUp();
            // automatically call the paint method again
            repaint();
        }
        // move snake downwards
        if (snake.down) {
            // call function in Snake class to move the snake downwards
            snake.movementDown();
            // automatically call the paint method again
            repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // key press condition
        switch (e.getKeyCode()) {
            // if user presses shift
            case KeyEvent.VK_SHIFT:
                if (speedUp.compareAndSet(true, false)) {
                    if (delay > 100) {
                        timer.setDelay(delay/10);
                    }
                    else {
                        timer.setDelay(10);
                    }
                }
                break;
            // if user presses space
            case KeyEvent.VK_SPACE:
                // To start the game
                if (snake.moves == 0) {
                    snake.moves++;
                    snake.right = true;
                }
                // To restart the game after death
                if (snake.death) {
                    snake.moves = 0;
                    snake.lengthOfSnake = 5;
                    score.resetScore();
                    repaint();
                    snake.death = false;
                }
                break;
            // if user presses right arrow
            case KeyEvent.VK_RIGHT:
                // call function in Snake class to move right
                snake.moveRight();
                break;
            // if user presses left arrow
            case KeyEvent.VK_LEFT:
                // call function in Snake class to move left
                snake.moveLeft();
                break;
            // if user presses up arrow
            case KeyEvent.VK_UP:
                // call function in Snake class to move up
                snake.moveUp();
                break;
            // if user presses down arrow
            case KeyEvent.VK_DOWN:
                // call function in Snake class to move down
                snake.moveDown();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // If user releases shift
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            speedUp.set(true);
            timer.setDelay(delay);
        }
    }

}