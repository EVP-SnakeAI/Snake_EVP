import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class Frame extends JFrame implements ActionListener {    
    private JPanel panel = new JPanel(new GridBagLayout());
    private JButton gombJatek = new JButton("Játék");
    private JButton gombSpeed = new JButton("Játék (Speedy)");
    private JButton gombAkadaly = new JButton("Játék (Akadállyal)");
    private JButton gombVegyes = new JButton("Speddy/Akadállyal");
    private JButton gombGepJatek = new JButton("MI mód");
    private JLabel cim = new JLabel("Snake", SwingConstants.CENTER);
    
    Frame(){
        JFrame FrameA = new JFrame();
        
        cim.setForeground(Color.red);
        cim.setFont(new Font("Serif", Font.BOLD, 90));
        
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
        
        gombSpeed.setPreferredSize(new Dimension(200, 80));
        gombSpeed.setBackground(Color.GREEN);
        gombSpeed.setFocusPainted(false);
        gombSpeed.setBorder(gombSzegely);
        gombSpeed.setFont(new Font("Serif", Font.BOLD, 30));
        
        gombAkadaly.setPreferredSize(new Dimension(200, 80));
        gombAkadaly.setBackground(Color.GREEN);
        gombAkadaly.setFocusPainted(false);
        gombAkadaly.setBorder(gombSzegely);
        gombAkadaly.setFont(new Font("Serif", Font.BOLD, 30));
        
        gombVegyes.setPreferredSize(new Dimension(200, 80));
        gombVegyes.setBackground(Color.GREEN);
        gombVegyes.setFocusPainted(false);
        gombVegyes.setBorder(gombSzegely);
        gombVegyes.setFont(new Font("Serif", Font.BOLD, 30));
        
        GridBagConstraints gridElrendezes = new GridBagConstraints();
        gridElrendezes.gridwidth = GridBagConstraints.REMAINDER;
        gridElrendezes.fill = GridBagConstraints.HORIZONTAL;
        gridElrendezes.insets = new Insets(0, 0, 50, 0); 
        
        panel.setBackground(Color.BLACK);
        panel.add(cim, gridElrendezes);
        panel.add(gombJatek, gridElrendezes);
        panel.add(gombSpeed, gridElrendezes);
        panel.add(gombAkadaly, gridElrendezes);
        panel.add(gombVegyes, gridElrendezes);
        panel.add(gombGepJatek, gridElrendezes);
        
        gombJatek.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                
                try {
                        File fajl = new File("gamemode.txt");
                        fajl.delete();
                        File ujfajl = new File("gamemode.txt");
                        ujfajl.createNewFile();
                        FileWriter iro = new FileWriter("gamemode.txt");
                        iro.write("false\n");
                        iro.write("false");
                        iro.close();
                         
                }catch (IOException x) {
                        x.printStackTrace();
                }
                FrameA.add(new SnakeGame());
                FrameA.setTitle("Snake_EVP");
                FrameA.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                FrameA.setResizable(false);
                FrameA.pack();
                FrameA.setVisible(true);
                FrameA.setLocationRelativeTo(null);
            }
        });

        gombSpeed.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                
                try {
                        File fajl = new File("gamemode.txt");
                        fajl.delete();
                        File ujfajl = new File("gamemode.txt");
                        ujfajl.createNewFile();
                        FileWriter iro = new FileWriter("gamemode.txt");
                        iro.write("true\n");
                        iro.write("false");
                        iro.close();
                         
                }catch (IOException x) {
                        x.printStackTrace();
                }
                FrameA.add(new SnakeGame());
                FrameA.setTitle("Snake_EVP");
                FrameA.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                FrameA.setResizable(false);
                FrameA.pack();
                FrameA.setVisible(true);
                FrameA.setLocationRelativeTo(null);
            }
        });
        
        gombAkadaly.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                
                try {
                        File fajl = new File("gamemode.txt");
                        fajl.delete();
                        File ujfajl = new File("gamemode.txt");
                        ujfajl.createNewFile();
                        FileWriter iro = new FileWriter("gamemode.txt");
                        iro.write("false\n");
                        iro.write("true");
                        iro.close();
                         
                }catch (IOException x) {
                        x.printStackTrace();
                }
                FrameA.add(new SnakeGame());
                FrameA.setTitle("Snake_EVP");
                FrameA.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                FrameA.setResizable(false);
                FrameA.pack();
                FrameA.setVisible(true);
                FrameA.setLocationRelativeTo(null);
            }
        });
        
        gombVegyes.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                
                try {
                        File fajl = new File("gamemode.txt");
                        fajl.delete();
                        File ujfajl = new File("gamemode.txt");
                        ujfajl.createNewFile();
                        FileWriter iro = new FileWriter("gamemode.txt");
                        iro.write("true\n");
                        iro.write("true");
                        iro.close();
                         
                }catch (IOException x) {
                        x.printStackTrace();
                }
                FrameA.add(new SnakeGame());
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
        this.setPreferredSize(new Dimension(600, 700));
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
          
    }
    @Override
    public void actionPerformed(ActionEvent e) { 

       }
    
}