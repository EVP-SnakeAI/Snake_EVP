
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class Frame extends JFrame implements ActionListener {    
    private JPanel panel = new JPanel(new GridBagLayout());
    private JButton gombJatek = new JButton("Játék");
    private JButton gombGepJatek = new JButton("MI mód");
    private JLabel cim = new JLabel("Snake", SwingConstants.CENTER);
    
    Frame(){
        JFrame FrameA = new JFrame();
        
        cim.setForeground(Color.red);
        cim.setFont(new Font("Serif", Font.BOLD, 100));
        
        gombJatek.setPreferredSize(new Dimension(200, 80));
        gombJatek.setBackground(Color.GREEN);
        gombJatek.setFocusPainted(false);
        Border gombSzegely = new LineBorder(Color.RED, 5);
        gombJatek.setBorder(gombSzegely);
        gombJatek.setFont(new Font("Serif", Font.BOLD, 30));
        
        gombGepJatek.setPreferredSize(new Dimension(200, 80));
        gombGepJatek.setBackground(Color.GREEN);
        gombGepJatek.setFocusPainted(false);
        gombGepJatek.setBorder(gombSzegely);
        gombGepJatek.setFont(new Font("Serif", Font.BOLD, 30));
        
        GridBagConstraints gridElrendezes = new GridBagConstraints();
        gridElrendezes.gridwidth = GridBagConstraints.REMAINDER;
        gridElrendezes.fill = GridBagConstraints.HORIZONTAL;
        gridElrendezes.insets = new Insets(0, 0, 50, 0); 
        
        panel.setBackground(Color.BLACK);
        panel.add(cim, gridElrendezes);
        panel.add(gombJatek, gridElrendezes);
        panel.add(gombGepJatek, gridElrendezes);
        
        gombJatek.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) { 
                FrameA.add(new Game());
                FrameA.setTitle("Snake_EVP");
                FrameA.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                FrameA.setResizable(false);
                FrameA.pack();
                FrameA.setVisible(true);
                FrameA.setLocationRelativeTo(null);
            }
        });
 
        this.add(panel);
        this.setTitle("Snake_EVP Főmenü");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(600, 600));
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
          
    }
    @Override
    public void actionPerformed(ActionEvent e) { 

       }
    
}