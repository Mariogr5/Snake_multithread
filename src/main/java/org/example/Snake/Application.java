package org.example.Snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Application {

    public Snake snake;

    public ArrayList<Obstacle> obstacles = new ArrayList<>();

    public int points = 0;

    public Point apple;

    public int gameSpeed = 100;



    public JFrame gameFrame = new JFrame();
    public JPanel gamePanel  = new JPanel(){
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            draw(g2d);
        }
        public void draw(Graphics2D g2d) {
            for(Rectangle rectangle: GraphicsGenerator.generateSquares(snake.Tail)) {
                g2d.fill(rectangle);
            }
            for(Obstacle obstaclee: obstacles){
                for(Rectangle obstacle: GraphicsGenerator.generateSquares(obstaclee.obstacleParts)) {
                    g2d.fill(obstacle);
                }
            }
            g2d.fill(GraphicsGenerator.generateApple(apple));
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 20));
            g2d.drawString("Points: " + points, getWidth() - 120, 30);

        }
    };

    ArrayList<Thread> MoveThreads = new ArrayList<>();

    ArrayList<Thread> CollisionThreads = new ArrayList<>();
    private Semaphore mutex;

    volatile boolean collisions = false;

    private void thread_init(){
        MoveThreads.clear();
        CollisionThreads.clear();
        ArrayList<Runnable> movementTasks = new ArrayList<>();

        ArrayList<Runnable> collisionTasks = new ArrayList<>();

        movementTasks.add(() -> snake.move());

        collisionTasks.add(() -> {
            try {
                if (CollisionsHandler.detectSnakeCollision(snake.Tail)) {
                    collisions = true;
                }
            } catch (Exception e) {
                mutex.release();
            }
        });

        for(Obstacle obstaclee: obstacles) {
            movementTasks.add(obstaclee::move);
            collisionTasks.add(() -> {
                try {
                    if (CollisionsHandler.detectObjectsColision(snake.Tail, obstaclee.obstacleParts)) {
                        collisions = true;
                    }
                } catch (Exception e) {
                    mutex.release();
                }
            });
        }

        for (Runnable task : movementTasks) {
            MoveThreads.add(new Thread(task));
        }

        for (Runnable task : collisionTasks) {
            CollisionThreads.add(new Thread(task));
        }

    }

    public void init_window(){
        gameFrame.setTitle("Snake Game");
        gameFrame.setSize(Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.add(gamePanel);

        gamePanel.setLayout(new BorderLayout());
        gamePanel.setBackground(Color.WHITE);
        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();

        gamePanel.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_L) {
                    snake.calc_direction("R");
                }
                if (keyCode == KeyEvent.VK_K) {
                    snake.calc_direction("L");
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        gameFrame.setVisible(true);

    }


    public void initGame() throws InterruptedException {

        snake = new Snake();
        obstacles.add(new Obstacle("R",7, new Point(200,100)));
        obstacles.add(new Obstacle("U", 9, new Point(450, 175)));
        obstacles.add(new Obstacle("U", 5, new Point(100, 100)));


        apple = new Point(new Random().nextInt((Settings.WINDOW_WIDTH)), new Random().nextInt((Settings.WINDOW_WIDTH)));

        gamePanel.repaint();

        thread_init();

        GameLoop();

    }


    public void GameLoop() throws InterruptedException {
        while(true){
            thread_init();
            for(Thread thread: MoveThreads){
                thread.start();
            }
            for(Thread thread: MoveThreads){
                thread.join();
            }

            for(Thread thread: CollisionThreads){
                thread.start();
            }
            for(Thread thread: CollisionThreads){
                thread.join();
            }
            if(collisions)
                break;
            if(snake.checkifAppleEarned(apple)){
                points++;
                apple = new Point(new Random().nextInt((Settings.WINDOW_WIDTH)), new Random().nextInt((Settings.WINDOW_WIDTH)));
                snake.generateSnakePart();
            }

            gamePanel.repaint();
            try {
                TimeUnit.MILLISECONDS.sleep(gameSpeed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        showGameOverScreen();
    }

    private void showGameOverScreen() {
        gamePanel.removeAll();
        gamePanel.repaint();

        JLabel gameOverLabel = new JLabel("Game Over", SwingConstants.CENTER);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 50));
        gamePanel.add(gameOverLabel, BorderLayout.CENTER);

        JLabel pointsLabel = new JLabel("Points: " + points, SwingConstants.CENTER);
        pointsLabel.setFont(new Font("Arial", Font.BOLD, 30));
        gamePanel.add(pointsLabel, BorderLayout.SOUTH);

        gamePanel.revalidate();
        gamePanel.repaint();
    }


    }
