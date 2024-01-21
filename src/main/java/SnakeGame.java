import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import javax.swing.*;
import java.util.Random;
import java.util.Scanner;

public class SnakeGame extends JPanel implements ActionListener{

    private static final int kepernyoSzelesseg = 800;
    private static final int kepernyoHossz = 600;
    private static final int meret = 30;
    private static final int jatekMeret = (kepernyoSzelesseg*kepernyoHossz)/(meret*meret);
    private static final int kesleltetes = 120;
    private final int x[] = new int[jatekMeret];
    private final int y[] = new int[jatekMeret];
    private int kigyoHossz = 6;
    private int megevettAlmak;
    private int almaX;
    private int almaY;
    char irany = 'R';
    boolean fut = false;
    private Timer timer;
    Random random;
    private int rekord;

    SnakeGame(){
            random = new Random();
            this.setPreferredSize(new Dimension(kepernyoSzelesseg,kepernyoHossz));
            this.setBackground(Color.black);
            this.setFocusable(true);
            this.addKeyListener(new MyKeyAdapter());
            getRekord();
            startGame();
    }
    
    public void getRekord(){     
        try {
            File fajl = new File("rekord.txt");
            Scanner olvaso = new Scanner(fajl);
            rekord = Integer.parseInt(olvaso.nextLine());
            olvaso.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
   }
    
    public void startGame() {
            UjAlma();
            fut = true;
            timer = new Timer(kesleltetes,this);
            timer.start();
    }
    public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Rajzolas(g);
    }
    public void Rajzolas(Graphics g) {

            if(fut) {
                    /*
                    for(int i=0;i<kepernyoHossz/meret;i++) {
                            g.RajzolasLine(i*meret, 0, i*meret, kepernyoHossz);
                            g.RajzolasLine(0, i*meret, kepernyoSzelesseg, i*meret);
                    }
                    */
                    g.setColor(Color.red);
                    g.fillOval(almaX, almaY, meret, meret);

                    for(int i = 0; i< kigyoHossz;i++) {
                            if(i == 0) {
                                    g.setColor(Color.green);
                                    g.fillRect(x[i], y[i], meret, meret);
                            }
                            else {
                                    g.setColor(new Color(45,180,0));
                                    //g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                                    g.fillRect(x[i], y[i], meret, meret);
                            }			
                    }
                    g.setColor(Color.red);
                    g.setFont( new Font("Serif",Font.BOLD, 40));
                    FontMetrics metrics = getFontMetrics(g.getFont());
                    g.drawString("Pontszám: "+megevettAlmak, (kepernyoSzelesseg - metrics.stringWidth("Pontszám: "+megevettAlmak))/2, g.getFont().getSize());
            }
            else {
                    gameOver(g);
            }

    }
    public void UjAlma(){
            almaX = random.nextInt((int)(kepernyoSzelesseg/meret))*meret;
            almaY = random.nextInt((int)(kepernyoHossz/meret))*meret;
    }
    public void mozgas(){
            for(int i = kigyoHossz;i>0;i--) {
                    x[i] = x[i-1];
                    y[i] = y[i-1];
            }

            switch(irany) {
            case 'U':
                    y[0] = y[0] - meret;
                    break;
            case 'D':
                    y[0] = y[0] + meret;
                    break;
            case 'L':
                    x[0] = x[0] - meret;
                    break;
            case 'R':
                    x[0] = x[0] + meret;
                    break;
            }

    }
    public void AlmaTalalat() {
            if((x[0] == almaX) && (y[0] == almaY)) {
                    kigyoHossz++;
                    megevettAlmak++;
                    UjAlma();
            }
    }
    public void Utkozes() {
            //Ütközés önmagával
            for(int i = kigyoHossz;i>0;i--) {
                    if((x[0] == x[i])&& (y[0] == y[i])) {
                            fut = false;
                    }
            }
            //ütközés a baloldali fallal
            if(x[0] < 0) {
                    fut = false;
            }
            //ütközés a jobboldali fallal
            if(x[0] > kepernyoSzelesseg) {
                    fut = false;
            }
            //ütközés a felső fallal
            if(y[0] < 0) {
                    fut = false;
            }
            ///ütközés az alsó fallal
            if(y[0] > kepernyoHossz) {
                    fut = false;
            }

            if(!fut) {
                    timer.stop();
            }
    }
    public void gameOver(Graphics g) {
            //Pontszam
            g.setColor(Color.red);
            g.setFont( new Font("Serif",Font.BOLD, 40));
            FontMetrics metrics1 = getFontMetrics(g.getFont());
            g.drawString("Pontszám: "+megevettAlmak, (kepernyoSzelesseg - metrics1.stringWidth("Pontszám: "+megevettAlmak))/2, g.getFont().getSize());
            if(megevettAlmak <= rekord)
                g.drawString("Rekord: " + rekord, (kepernyoSzelesseg - metrics1.stringWidth("Rekord: "))/2, 370);
            else{
                g.drawString("Új Rekord: " + megevettAlmak, (kepernyoSzelesseg - metrics1.stringWidth("Új Rekord: "))/2, 370);
                File fajl = new File("rekord.txt");
                fajl.delete();
                try {
                        File ujfajl = new File("rekord.txt");
                        ujfajl.createNewFile();
                        FileWriter iro = new FileWriter("rekord.txt");
                        iro.write(String.valueOf(megevettAlmak));
                        iro.close();
                         
                }catch (IOException e) {
                        e.printStackTrace();
                }
           }
            //Game Over text
            g.setColor(Color.red);
            g.setFont( new Font("Serif",Font.BOLD, 75));
            FontMetrics metrics2 = getFontMetrics(g.getFont());
            g.drawString("Game Over", (kepernyoSzelesseg - metrics2.stringWidth("Game Over"))/2, kepernyoHossz/2);

    }
    @Override
    public void actionPerformed(ActionEvent e) {

            if(fut) {
                    mozgas();
                    AlmaTalalat();
                    Utkozes();
            }
            repaint();
    }

    private class MyKeyAdapter extends KeyAdapter{
            @Override
            public void keyPressed(KeyEvent e) {
                    switch(e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                            if(irany != 'R') {
                                    irany = 'L';
                            }
                            break;
                    case KeyEvent.VK_RIGHT:
                            if(irany != 'L') {
                                    irany = 'R';
                            }
                            break;
                    case KeyEvent.VK_UP:
                            if(irany != 'D') {
                                    irany = 'U';
                            }
                            break;
                    case KeyEvent.VK_DOWN:
                            if(irany != 'U') {
                                    irany = 'D';
                            }
                            break;
                    }
            }
    }      
}