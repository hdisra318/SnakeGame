import javax.swing.JFrame;

/**
 * Game Frame
 * 
 * @author Israel Hernandez Dorantes
 * @version 1.0
 * @since July 2022
 */
public class GameFrame extends JFrame {
    
    /**
     * Constructor
     */
    public GameFrame(){

        GamePanel panel = new GamePanel();
        this.add(panel);
        this.setTitle("Snake Game");
        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

}
