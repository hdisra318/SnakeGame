import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.FlowLayout;

/**
 * Asks to repat the game or exit
 */
public class Repeat extends JFrame {
    
    /** Button to repeat the game*/
    JButton repeat;

    /** Button to exit the game */
    JButton exit;

    /** Panel of the window */
    JPanel panel;

    /**
     * Constructor
     */
    public Repeat(){

        this.setTitle("Snake Game");
        this.setSize(new Dimension(200, 110));
        this.setLocationRelativeTo(null);
        //this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        
        panel = new JPanel();
        panel.setSize(new Dimension(200, 110));
        panel.setBackground(Color.darkGray);
        panel.setLayout(new FlowLayout());

        this.add(panel);
        repeatButton();
        exitButton();

    }


    /**
     * Creates the botton to repeat the game
     */
    public void repeatButton(){

        repeat = new JButton("Try again");
        repeat.setBackground(Color.darkGray);
        repeat.setOpaque(true);
        repeat.setFont(new Font("Arial", Font.PLAIN, 20));

        repeat.addActionListener(e -> {

            GameFrame newGame = new GameFrame();
            this.setVisible(false);

        });


        panel.add(repeat);
    }

    /**
     * Creates the exit button
     */
    public void exitButton(){

        exit = new JButton("Exit");
        exit.setBackground(Color.darkGray);
        exit.setOpaque(true);
        exit.setFont(new Font("Arial", Font.PLAIN, 20));

        exit.addActionListener(e -> {

            System.exit(0);

        });


        panel.add(exit);

    }

}
