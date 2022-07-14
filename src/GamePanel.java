import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;

/**
 * Game Panel 
 * 
 * @author Israel Hernandez Dorantes
 * @version 1.0
 * @since July 2022
 */
public class GamePanel extends JPanel implements ActionListener {


    /** Screen width */
    private static final int SCREEN_WIDTH = 600;

    /** Screen height */
    private static final int SCREEN_HEIGHT = 600;

    /** Size of the units/squares */
    private static int UNITS_SIZE = 25;

    /** Number of units that the panel supports */
    private static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNITS_SIZE;

    /** The delay for the timer for how fast the game is going to run*/
    private static final int DELAY = 90;

    /** For the X coordenates for the body parts of the snake 
     * x[0] is the head of the snake
    */
    private final int x[] = new int[GAME_UNITS];

    /** For the Y coordenates for the body parts of the snake */
    private final int y[] = new int[GAME_UNITS];

    /** Number of body parts of the snake */
    private int bodyParts = 5;

    /** The number of apples eaten by the snake */
    private int applesEaten = 0;

    /** The X coordenates of the current apple */
    private int appleX;

    /** The Y coordenates of the current apple */
    private int appleY;

    /** The current direction of the snake:
     * R: Right
     * U: Up
     * D: Down
     * L: Left
    */
    private char direction = 'R';

    /** If the game is running */
    boolean running = false;

    /** The timer for the game */
    Timer timer;

    /** For the random numbers */
    Random random;

    
    
    /**
     * Constructor
     */
    public GamePanel(){

        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.addKeyListener(new SnakeKeyAdapter());
        this.setFocusable(true);

        startGame();

    }

    /**
     * Starts the game
     */
    public void startGame(){

        createNewApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();

    }

    /**
     * Paint a component
     * 
     * @param graphic the graphic to paint
     */
    public void paintComponent(Graphics graphic){

        super.paintComponent(graphic);
        this.draw(graphic);

    }

    /**
     * Draw
     * 
     * @param graphic the graphic to paint
     */
    public void draw(Graphics graphic){
        
        //If the game starts
        if(this.running){

            //To make a Grid for the board of the game
            /*graphic.setColor(Color.WHITE);
            for(int i = 0; i<SCREEN_HEIGHT/UNITS_SIZE; ++i){
                graphic.drawLine(i*UNITS_SIZE, 0, i*UNITS_SIZE, SCREEN_HEIGHT);
                graphic.drawLine(0, i*UNITS_SIZE, SCREEN_WIDTH, i*UNITS_SIZE);
            }*/

            graphic.setColor(Color.RED);
            //Drawing the apple in the given coordenates
            graphic.fillOval(appleX, appleY, UNITS_SIZE, UNITS_SIZE);

            //Drawing the snake
            for(int i = 0; i<bodyParts; ++i){

                //The head of the snake
                if(i == 0){

                    graphic.setColor(new Color(0, 143, 57));
                    //Drawing a rectangle
                    graphic.fillRect(x[i], y[i], UNITS_SIZE, UNITS_SIZE);

                }else {//Body of the snake

                    graphic.setColor(new Color(45, 180, 0));
                    
                    //For multiple colors in the snake
                    //graphic.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                    
                    graphic.fillRect(x[i], y[i], UNITS_SIZE, UNITS_SIZE);
                }
            }

            //Score
            graphic.setColor(Color.YELLOW);
            graphic.setFont(new Font("Ink Free", Font.BOLD,40));

            //To line up the text at the middle and top of the window
            FontMetrics fontMetrics = this.getFontMetrics(graphic.getFont());
            graphic.drawString("Score: "+applesEaten, (SCREEN_WIDTH - fontMetrics.stringWidth("Score: "+applesEaten))/2, 
            graphic.getFont().getSize());

            

        }else{

            gameOver(graphic);
        }
    }

    /**
     * Creates a new apple on the screen
     */
    public void createNewApple(){

        //Calculating the position of a new apple
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNITS_SIZE)) * UNITS_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNITS_SIZE)) * UNITS_SIZE;
    }

    /**
     * Make the movement of the snake
     */
    public void move(){

        for(int i = bodyParts; i>0; --i){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        //For the directions
        switch(direction){
            case 'U':
                y[0] = y[0] - UNITS_SIZE;
                break;
            
            case 'D':
                y[0] = y[0] + UNITS_SIZE;
                break;

            case 'R':
                x[0] = x[0] + UNITS_SIZE;
                break;

            case 'L':
                x[0] = x[0] - UNITS_SIZE;
                break;
        }

    }

    /**
     * It executes when the snake "Eats" an apple
     */
    public void checkApple(){

        //When the head of the snake is on the apple
        if((x[0] == appleX) && (y[0] == appleY)){

            bodyParts++;//Increase in 1 the body parts
            applesEaten++; //Increase in 1 the number of apples eaten
            createNewApple(); //Creating a new apple
        }
    }

    /**
     * Checks the collisions
     */
    public void checkCollision(){

        //checks every part of the body
        for(int i = bodyParts; i > 0; --i){
            //checks if the head colides with any part of the body
            if((x[0] == x[i]) && (y[0] == y[i])){
                running = false; //To stop the movement
            }    
        }
        
        //checks if the head touches the left border
        if(x[0] < 0){
            running = false;
        }

        //checks if the head touches the right border
        if(x[0] > SCREEN_WIDTH){
            running = false;
        }

        //checks if the head touches the top of the border
        if(y[0] < 0){
            running = false;
        }

        //checks if the head touches the bottom border
        if(y[0] > SCREEN_HEIGHT)
            running = false;

        //If the game stopped, stop the timer
        if(!running)
            timer.stop();
    }

    /**
     * Finish the game
     * 
     * @param graphic
     */
    public void gameOver(Graphics graphic){

        //Score
        graphic.setColor(Color.RED);
        graphic.setFont(new Font("Ink Free", Font.BOLD,40));

        //To line up the text at the middle and top of the window
        FontMetrics metrics = this.getFontMetrics(graphic.getFont());
        graphic.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: "+applesEaten))/2, 
        graphic.getFont().getSize());


        //Game over text
        graphic.setColor(Color.RED);
        graphic.setFont(new Font("Ink Free", Font.BOLD,75));

        //To line up the text at the middle of the window
        FontMetrics fontMetrics = this.getFontMetrics(graphic.getFont());
        graphic.drawString("Game Over", (SCREEN_WIDTH - fontMetrics.stringWidth("Game Over"))/2, 
        SCREEN_HEIGHT / 2);
        
        //Try again text
        graphic.setColor(Color.YELLOW);
        FontMetrics againText = this.getFontMetrics(graphic.getFont());
        graphic.setFont(new Font("Arial", Font.BOLD, 40));
        graphic.drawString("Try again?", 200, 450);

        //Repat window
        //Repeat repeat = new Repeat();


    }


    /**
     * Key adapter customized for the game 
     */
    public class SnakeKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            switch(e.getKeyCode()){

                //If it is pressed the left arrow key
                case KeyEvent.VK_LEFT:
                    if(direction != 'R')//If the current direction is not right
                        direction = 'L';
                    break;
                    
                //If it is pressed the right arrow key
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L')
                        direction = 'R';
                    break;

                //If it is pressed the up arrow key
                case KeyEvent.VK_UP:
                    if(direction != 'D')
                        direction = 'U';
                    break;

                //If it is pressed the down arrow key
                case KeyEvent.VK_DOWN:
                    if(direction != 'U')
                        direction = 'D';
                    break;

            
            }

        }

    }


    @Override
    public void actionPerformed(ActionEvent e) {

        //If the game is running
        if(running){

            move();
            checkApple();
            checkCollision();
        
        }

        this.repaint();
        
    }

}
