
import java.awt.*;
import javax.swing.*;
import java.util.Random;

public class Game extends JPanel{
    static final int kepernyoSzelesseg = 600;
    static final int kepernyoHossz = 600;
    static final int mezoMeret = 25;
    static final int PalyaMeret = (kepernyoSzelesseg *kepernyoHossz) / mezoMeret;
    final int x[] = new int[PalyaMeret];
    final int y[] = new int[PalyaMeret];
    int kigyoHossza = 6;
    int megevettAlmak;
    int almaX;
    int almaY;
    Random random;
    char irany = 'J'; //J = jobbra 
    boolean fut;
    
   Game(){
       random = new Random();
       this.setPreferredSize(new Dimension(kepernyoSzelesseg,kepernyoHossz));
       this.setBackground(Color.black);
       this.setFocusable(true);
       Start();
   }

    public void Start(){
        ujAlma();
        fut = true;
    }
   
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Rajzolas(g);
      }

    public void ujAlma(){
        almaX = random.nextInt((int)(kepernyoSzelesseg/mezoMeret))*mezoMeret;
        almaY = random.nextInt((int)(kepernyoHossz/mezoMeret))*mezoMeret;
    }
   
    public void Rajzolas(Graphics g){
        if(fut) {
            
            for(int i=0;i<kepernyoHossz/mezoMeret;i++) {
                    g.drawLine(i*mezoMeret, 0, i*mezoMeret, kepernyoHossz);
                    g.drawLine(0, i*mezoMeret, kepernyoSzelesseg, i*mezoMeret);
            }
            
            g.setColor(Color.red);
            g.fillOval(almaX, almaY, mezoMeret, mezoMeret);

            for(int i = 0; i< kigyoHossza;i++) {
                    if(i == 0) {
                            g.setColor(Color.green);
                            g.fillRect(x[i], y[i], mezoMeret, mezoMeret);
                    }
                    else {
                            g.setColor(new Color(45,180,0));
                            //g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                            g.fillRect(x[i], y[i], mezoMeret, mezoMeret);
                    }			
            }
        }
        else 
            gameOver(g);       
    }
    
    public void gameOver(Graphics g){
        g.setColor(Color.red);
        g.setFont( new Font("Ink Free",Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Pontszám: "+megevettAlmak, (kepernyoSzelesseg - metrics1.stringWidth("Pontszám: "+megevettAlmak))/2, g.getFont().getSize());
        //Game Over text
        g.setColor(Color.red);
        g.setFont( new Font("Ink Free",Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (kepernyoSzelesseg - metrics2.stringWidth("Game Over"))/2,kepernyoHossz/2);
    }
 
}
