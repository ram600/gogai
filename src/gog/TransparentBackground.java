package gog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class TransparentBackground extends JComponent {
   private JFrame frame;
   private Image background;
   
   

   public TransparentBackground(JFrame frame) {
       this.frame = frame;
       updateBackground();
   }

   public void updateBackground() {
       try {
           Robot rbt = new Robot();
           Toolkit tk = Toolkit.getDefaultToolkit();
           Dimension dim = tk.getScreenSize();
           background = rbt.createScreenCapture(
                   new Rectangle(0, 0, (int) dim.getWidth(),
                           (int) dim.getHeight()));
       } catch (Exception ex) {
           ex.printStackTrace();
       }
   }

   public void paintComponent(Graphics g) {
       java.awt.Point pos = this.getLocationOnScreen();
       java.awt.Point offset = new java.awt.Point(-pos.x, -pos.y);
       g.drawImage(background, offset.x, offset.y, null);
       
       g.setColor(Color.blue);
       g.drawRect(12,105,ImagePuller.IMG_WIDTH,ImagePuller.IMG_HEIGHT);

       g.drawRect(25,30,229,12);
       g.drawRect(350,30,229,12);
       
   }

   public int getboardX(){
     java.awt.Point pos = this.getLocationOnScreen();
     return pos.x+12;
   }
   public int getboardY(){
     java.awt.Point pos = this.getLocationOnScreen();
     return pos.y+105;
   }
   public int gethpX(){
     java.awt.Point pos = this.getLocationOnScreen();
     return pos.x+25;
   }
   public int gethpY(){
     java.awt.Point pos = this.getLocationOnScreen();
     return pos.y+30;
   }

   

//   public static void main(String[] args) {
//       JFrame frame = new JFrame("Transparent Window");
//       frame.addComponentListener(new TransparentComponentListener());
//       TransparentBackground bg = new TransparentBackground(frame);
//       bg.setLayout(new BorderLayout());
//       JButton button = new JButton("This is a button");
//       bg.add("North", button);
//       JLabel label = new JLabel("This is a label");
//       bg.add("South", label);
//       frame.getContentPane().add("Center", bg);
//       frame.pack();
//       frame.setSize(200, 200);
//       frame.show();
//   }
}