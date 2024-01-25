import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;
import java.util.Random;
import java.util.Scanner;

public class SnakeGame extends JPanel implements ActionListener{

    private static final int kepernyoSzelesseg = 700;
    private static final int kepernyoHossz = 700;
    private static final int meret = 25;
    private static final int jatekMeret = (kepernyoSzelesseg*kepernyoHossz)/(meret*meret);
    private static int kesleltetes = 120;
    private final int x[] = new int[jatekMeret];
    private final int y[] = new int[jatekMeret];
    private int kigyoHossz = 6;
    private int megevettAlmak;
    private int almaX;
    private int almaY;
    private int akadaly1X;
    private int akadaly1Y;
    private int akadaly2X;
    private int akadaly2Y;
    private boolean speedy;
    private boolean akadaly;
    char irany = 'R';
    boolean fut = false;
    private Timer timer;
    Random random;
    private int rekord1;
    private int rekord2;
    private int rekord3;
    private int rekord4;

    SnakeGame(){
            random = new Random();
            this.setPreferredSize(new Dimension(kepernyoSzelesseg,kepernyoHossz));
            this.setBackground(Color.black);
            this.setFocusable(true);
            this.addKeyListener(new MyKeyAdapter());
            GetRekord();
            GetGamemode();
            startGame();
    }
    
    public void GetRekord(){     
        try {
            File fajl = new File("rekord.txt");
            Scanner olvaso = new Scanner(fajl);
            if(olvaso.hasNext()) rekord1 = Integer.parseInt(olvaso.nextLine());
            if(olvaso.hasNext()) rekord2 = Integer.parseInt(olvaso.nextLine());
            if(olvaso.hasNext()) rekord3 = Integer.parseInt(olvaso.nextLine());
            if(olvaso.hasNext()) rekord4 =Integer.parseInt(olvaso.nextLine());
            
            
            olvaso.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
   }
    
   public void GetGamemode(){     
        try {
            File fajl = new File("gamemode.txt");
            Scanner olvaso = new Scanner(fajl);
            speedy = Boolean.parseBoolean(olvaso.nextLine());
            akadaly = Boolean.parseBoolean(olvaso.nextLine());
            olvaso.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
   }
    
    public void startGame() {
        if(akadaly)
            ujAkadaly();
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
                    g.drawLine(i*meret, 0, i*meret, kepernyoHossz);
                    g.drawLine(0, i*meret, kepernyoSzelesseg, i*meret);
            }
            */     
            g.setColor(Color.red);
            g.fillOval(almaX, almaY, meret, meret);

            if(akadaly){
            g.setColor(Color.GRAY);
            g.fillOval(akadaly1X, akadaly1Y, meret, meret);
            g.fillOval(akadaly2X, akadaly2Y, meret, meret);
            }

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
    
    public void ujAkadaly(){
       akadaly1X = random.nextInt((int)(kepernyoSzelesseg/meret))*meret;
       akadaly1Y = random.nextInt((int)(kepernyoHossz/meret))*meret;
       
       akadaly2X = random.nextInt((int)(kepernyoSzelesseg/meret))*meret;
       akadaly2Y = random.nextInt((int)(kepernyoHossz/meret))*meret;
    }
    
    public boolean CheckKoordinata(){
        boolean rossz = false;
        if(akadaly == true && ((akadaly1X == almaX && akadaly1Y == almaY) || (akadaly2X == almaX && akadaly2Y == almaY))){
            rossz = true;
        }
        else{
            for(int i = 0; i < x.length; i++){
            if(almaX == x[i] && almaY == y[i]){
                rossz = true;
                break;
            }
        }
        }
        
        return rossz;
    }
    public void UjAlma(){
        do{
            almaX = random.nextInt((int)(kepernyoSzelesseg/meret))*meret;
            almaY = random.nextInt((int)(kepernyoHossz/meret))*meret;
        }
        while(CheckKoordinata());      
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
                if(speedy){
                    timer.stop();
                    kesleltetes -= 4;
                    timer = new Timer(kesleltetes,this);
                    timer.start();
                }
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
        if(x[0] >= kepernyoSzelesseg) {
                fut = false;
        }
        //ütközés a felső fallal
        if(y[0] < 0) {
                fut = false;
        }
        ///ütközés az alsó fallal
        if(y[0] >= kepernyoHossz) {
                fut = false;
        }
        //Ütközés Akadállyal
        if(akadaly)
            if((x[0] == akadaly1X && y[0] == akadaly1Y) || (x[0] == akadaly2X && y[0] == akadaly2Y)) {
                    fut = false;
            }

        if(!fut) {
                timer.stop();
        }
    }
    
    public int GamemodeRekord(boolean s, boolean a){
        if(s == false && a == false) return rekord1;
        else if(s == true && a == false) return rekord2;
        else if(s == false && a == true) return rekord3;
        else return rekord4;
    }
    public void gameOver(Graphics g) {
        //Pontszam
        g.setColor(Color.red);
        g.setFont( new Font("Serif",Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Pontszám: "+megevettAlmak, (kepernyoSzelesseg - metrics1.stringWidth("Pontszám: "+megevettAlmak))/2, g.getFont().getSize());
        if(megevettAlmak <= GamemodeRekord(speedy, akadaly))
            g.drawString("Rekord: " + GamemodeRekord(speedy, akadaly), (kepernyoSzelesseg - metrics1.stringWidth("Rekord: "+ GamemodeRekord(speedy, akadaly)) )/2, 400);
        else{
            g.drawString("Új Rekord: " + megevettAlmak, (kepernyoSzelesseg - metrics1.stringWidth("Új Rekord: " + megevettAlmak))/2, 400);
            SetRekord();
       }
        //Game Over text
        g.setColor(Color.red);
        g.setFont( new Font("Serif",Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (kepernyoSzelesseg - metrics2.stringWidth("Game Over"))/2, kepernyoHossz/2);
    }
    
    public void SetRekord(){
        if(speedy == false && akadaly == false){
            File fajl = new File("rekord.txt");
            fajl.delete();
            try {
                    File ujfajl = new File("rekord.txt");
                    ujfajl.createNewFile();
                    FileWriter iro = new FileWriter("rekord.txt");
                    iro.write(String.valueOf(megevettAlmak)+ "\n" + String.valueOf(rekord2) + "\n" + String.valueOf(rekord3) + "\n" + String.valueOf(rekord4));
                    iro.close();
            }catch (IOException e) {
                    e.printStackTrace();
            }
        }
        else if(speedy == true && akadaly == false){
            File fajl = new File("rekord.txt");
            fajl.delete();
            try {
                    File ujfajl = new File("rekord.txt");
                    ujfajl.createNewFile();
                    FileWriter iro = new FileWriter("rekord.txt");
                    iro.write(String.valueOf(rekord1)+ "\n" + String.valueOf(megevettAlmak) + "\n" + String.valueOf(rekord3) + "\n" + String.valueOf(rekord4));
                    iro.close();
            }catch (IOException e) {
                    e.printStackTrace();
            }
        }
        
        else if(speedy == false && akadaly == true){
            File fajl = new File("rekord.txt");
            fajl.delete();
            try {
                    File ujfajl = new File("rekord.txt");
                    ujfajl.createNewFile();
                    FileWriter iro = new FileWriter("rekord.txt");
                    iro.write(String.valueOf(rekord1)+ "\n" + String.valueOf(rekord2) + "\n" + String.valueOf(megevettAlmak) + "\n" + String.valueOf(rekord4));
                    iro.close();
            }catch (IOException e) {
                    e.printStackTrace();
            }
        }
        
        else{
            File fajl = new File("rekord.txt");
            fajl.delete();
            try {
                    File ujfajl = new File("rekord.txt");
                    ujfajl.createNewFile();
                    FileWriter iro = new FileWriter("rekord.txt");
                    iro.write(String.valueOf(rekord1)+ "\n" + String.valueOf(rekord2) + "\n" + String.valueOf(rekord3) + "\n" + String.valueOf(megevettAlmak));
                    iro.close();
            }catch (IOException e) {
                    e.printStackTrace();
            }
        } 
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