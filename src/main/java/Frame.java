
import javax.swing.JFrame;

public class Frame extends JFrame {
    Frame(){
        this.add(new Game());
        this.setTitle("Snake_EVP");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
