package com.robsutar.engine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener {

    static final int WIDTH = 600,HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNIT = (WIDTH*HEIGHT)/UNIT_SIZE;
    static final int DELAY = 75;
    final int x[] = new int[GAME_UNIT];
    final int y[] = new int[GAME_UNIT];
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;

    char direction = 's';

    boolean running = false;
    Timer timer;
    Random random;


    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame(){
        running = true;

        newApple();
        timer = new Timer(DELAY,this);
        timer.start();
    }
    public void paintComponent(Graphics g ){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g ){

        for (int i = 0;i<WIDTH/UNIT_SIZE;i++){
            g.drawLine(i*UNIT_SIZE,0,i*UNIT_SIZE,HEIGHT);
            g.drawLine(0,i*UNIT_SIZE, WIDTH , i*UNIT_SIZE);
        }
        g.setColor(Color.RED);
        g.fillOval(appleX,appleY,UNIT_SIZE,UNIT_SIZE);

        for(int i = 0;i < bodyParts ; i++){
            if(i==0){
                g.setColor(Color.BLUE);
            g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
            } else{
                g.setColor(Color.getHSBColor(255-i*5,255-i*5,i));
                g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
            }
        }
    }
    public void newApple(){
        appleX = random.nextInt((int)(WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int)(HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }
    public void move(){
        for(int i = bodyParts;i>0;i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch (direction){
            case 'w':
                y[0] = y[0]-UNIT_SIZE;
                break;
            case 's':
                y[0] = y[0]+UNIT_SIZE;
                break;
            case 'a':
                x[0] = x[0]-UNIT_SIZE;
                break;
            case 'd':
                x[0] = x[0]+UNIT_SIZE;
                break;
        }
    }
    public void checkApple(){
        
    }
    public void checkCollisions(){

        for(int i = bodyParts;i>0;i--) {
            if((x[0] == x[i])&& (y[0] == y[i])) {
                running = false;
            }
        }
        //check if head touches left border
        if(x[0] < 0) {
            running = false;
        }
        //check if head touches right border
        if(x[0] > WIDTH) {
            running = false;
        }
        //check if head touches top border
        if(y[0] < 0) {
            running = false;
        }
        //check if head touches bottom border
        if(y[0] > HEIGHT) {
            running = false;
        }

        if(!running) {
            timer.stop();
        }
    }
    public void gameOver(Graphics g){

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (running){
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e ){
            switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if(direction != 'd') {
                        direction = 'a';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'a') {
                        direction = 'd';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 's') {
                        direction = 'w';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'w') {
                        direction = 's';
                    }
                    break;
            }
        }
    }
}
