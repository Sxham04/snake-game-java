package proj.snake.src;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.Font;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    // instansiasi objek snake
    Snake snake = new Snake();

    // instansiasi objek apple
    Apple apple = new Apple();

    // buat gambar kepala
    private ImageIcon snakeHead;

    private Timer timer;
    private int delay = 500;
    private ImageIcon snakeBody;

    AtomicBoolean speedUp = new AtomicBoolean(true);

    // koordinat letak kepala ular
    private int snakeHeadXPos = 379;

    // Buat gambar apple
    private ImageIcon appleImage;

    // Untuk generate random number
    private Random random = new Random();

    private int xPos = random.nextInt(100);
    private int yPos = random.nextInt(100);

    // Buat tittle game
    private ImageIcon titleImage;

    // Buat score game
    Score score = new Score();

    // Buat Highscore
    private String highScore;

    // Untuk tampilin controller
    private ImageIcon arrowImage;
    private ImageIcon shiftImage;

    public Gameplay() {
        // buat pas mulai gamenya
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {
        // cek jika game udah dimulai
        if (snake.moves == 0) {
            for (int i = 0; i < 5; i++) {
                snake.snakexLength[i] = snakeHeadXPos;
                snakeHeadXPos -= 6;
                snake.snakeyLength[i] = 355;
            }
        }

        // border judul
        g.setColor(Color.WHITE);
        g.drawRect(24, 10, 852, 55);

        // judul
        titleImage = new ImageIcon("images/title.png");
        titleImage.paintIcon(this, g, 25, 11);

        // border untuk gameplay
        g.setColor(Color.WHITE);
        g.drawRect(24, 71, 620, 614);

        // background gameplay
        g.setColor(Color.black);
        g.fillRect(25, 72, 619, 613);

        // border untuk leaderboard
        g.setColor(Color.WHITE);
        g.drawRect(653, 71, 223, 614);

        // background leaderboard
        g.setColor(Color.black);
        g.fillRect(654, 72, 221, 613);

        // Tampilin Score
        g.setColor(Color.white);
        g.setFont(new Font("Helvetica", Font.BOLD, 20));
        g.drawString("SCORE : " + score.getScore(), 720, 110);
        g.drawRect(653, 130, 221, 1);

        // Tampilin HighScore
        score.sortHighScore();
        highScore = score.getHighScore();
        g.drawString("HIGHSCORE", 705, 180);
        drawString(g, highScore, 705, 200);

        // Tampilin Controller
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

        // instansiasi gambar buat kepala ular
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

        // Jika snakeya makan apllenya
        if ((apple.applexPos[xPos]) == snake.snakexLength[0] && (apple.appleyPos[yPos] == snake.snakeyLength[0])) {
            snake.lengthOfSnake++;
            score.increaseScore();
            xPos = random.nextInt(100);
            yPos = random.nextInt(100);

            // mempercepat gerakan ular tiap kali skor mencapai kelipatan 10
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

        // Sebelum user mencet spacebar, apllenya ga keliatan
        if (snake.moves != 0) {
            appleImage.paintIcon(this, g, apple.applexPos[xPos], apple.appleyPos[yPos]);
        }

        // menampilkan tulisan "Press Spacebar to Start the Game!"
        if (snake.moves == 0) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Courier New", Font.BOLD, 26));
            g.drawString("Press Spacebar to Start the Game!", 70, 300);
        }

        // Cek jika kepala menabrak badan
        for (int i = 1; i < snake.lengthOfSnake; i++) {
            // jika tabrakan terjadi
            if (snake.snakexLength[i] == snake.snakexLength[0] && snake.snakeyLength[i] == snake.snakeyLength[0]) {
                // panggil function dead
                snake.dead();
            }
        }

        // Cek jika mati
        if (snake.death) {
            // Save Scorenya ke file highscore.dat
            score.saveNewScore();

            // menampilkan tulisan "Game Over!"
            g.setColor(Color.RED);
            g.setFont(new Font("Courier New", Font.BOLD, 50));
            g.drawString("Game Over!", 190, 340);

            // menampilkan score
            g.setColor(Color.GREEN);
            g.setFont(new Font("Courier New", Font.BOLD, 18));
            g.drawString("Your Score : " + score.getScore(), 250, 370);

            // menampilkan tulisan "Press Spacebar to restart!"
            g.setColor(Color.WHITE);
            g.setFont(new Font("Courier New", Font.BOLD, 20));
            g.drawString("Press Spacebar to restart!", 187, 400);
        }
        g.dispose();
    }

    // Void untuk menampilkan di layar string dengan \n di dalamnya
    public void drawString(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        timer.start();

        // untuk pergerakan ular
        // menggerakkan ular ke kanan
        if (snake.right) {
            // panggil fungsi pada class Snake untuk menggerakkan ular ke kanan
            snake.movementRight();
            // panggil kembali method paint secara otomatis
            repaint();
        }
        // menggerakkan ular ke kiri
        if (snake.left) {
            // panggil fungsi pada class Snake untuk menggerakkan ular ke kiri
            snake.movementLeft();
            // panggil kembali method paint secara otomatis
            repaint();
        }
        // menggerakkan ular ke atas
        if (snake.up) {
            // panggil fungsi pada class Snake untuk menggerakkan ular ke atas
            snake.movementUp();
            // panggil kembali method paint secara otomatis
            repaint();
        }
        // menggerakkan ular ke bawah
        if (snake.down) {
            // panggil fungsi pada class Snake untuk menggerakkan ular ke bawah
            snake.movementDown();
            // panggil kembali method paint secara otomatis
            repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // kondisi penekanan tombol
        switch (e.getKeyCode()) {
            // jika user tekan shift
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
            // jika user tekan spasi
            case KeyEvent.VK_SPACE:
                // Untuk mulai game
                if (snake.moves == 0) {
                    snake.moves++;
                    snake.right = true;
                }
                // Untuk restart game abis mati
                if (snake.death) {
                    snake.moves = 0;
                    snake.lengthOfSnake = 5;
                    score.resetScore();
                    repaint();
                    snake.death = false;
                }
                break;
            // jika user tekan arrow right
            case KeyEvent.VK_RIGHT:
                // panggil fungsi pada class Snake untuk gerak ke kanan
                snake.moveRight();
                break;
            // jika user tekan arrow left
            case KeyEvent.VK_LEFT:
                // panggil fungsi pada class Snake untuk gerak ke kiri
                snake.moveLeft();
                break;
            // jika user tekan arrow up
            case KeyEvent.VK_UP:
                // panggil fungsi pada class Snake untuk gerak ke atas
                snake.moveUp();
                break;
            // jika user tekan arrow down
            case KeyEvent.VK_DOWN:
                // panggil fungsi pada class Snake untuk gerak ke bawah
                snake.moveDown();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Jika user lepas shift
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            speedUp.set(true);
            timer.setDelay(delay);
        }
    }

}