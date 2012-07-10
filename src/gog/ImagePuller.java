package gog;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.image.BufferedImage;

//фильтры
//import java.awt.image.RescaleOp;
//import java.awt.image.LookupOp;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import java.awt.Robot;

import java.awt.Rectangle;
import javax.swing.JPanel;
import java.awt.MouseInfo;
import java.awt.Dimension;
//import java.awt.Toolkit;


class ImagePuller extends JPanel{

    int mouse_x;
    int mouse_y;

    int my_hp_x;
    int my_hp_y;
    

    public static final int IMG_WIDTH = 575;
    public static final int IMG_HEIGHT = 440;
    
    BufferedImage image;

    ImagePuller(){
            this.setPreferredSize(new Dimension(this.IMG_WIDTH,this.IMG_HEIGHT));
            mouse_x = MouseInfo.getPointerInfo().getLocation().x;
            mouse_y = MouseInfo.getPointerInfo().getLocation().y;

    }

    public void paint(Graphics g){
        BufferedImage i = getImageFromScreen(mouse_x,mouse_y); //createBase();
        Graphics2D g2 = (Graphics2D)g;
        //AffineTransform.getScaleInstance(0.7, 0.7)
        g2.drawImage(i,null ,this);
       
    }
 

    public void setLftTopCorneCoords(){
            mouse_x = MouseInfo.getPointerInfo().getLocation().x;
            mouse_y = MouseInfo.getPointerInfo().getLocation().y;
    }

    public void setLftTopCorneCoords(int x, int y){
            mouse_x = x;
            mouse_y = y;
    }
    public void setLftTopHpCoords(int x, int y){
            my_hp_x = x;
            my_hp_y = y;
    }


    private BufferedImage getImageFromFile(){
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("images.jpg"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return img;
    }


    private BufferedImage getImageFromScreen(int x,int y){
            //BufferedImage bi = null;
            //Toolkit.getDefaultToolkit().getScreenSize()
        try {
            Robot r = new Robot();
            Rectangle screen = new Rectangle(x,y,575,440);
            //BufferedImage screenCapture = new Robot().createScreenCapture(screen);
            image = r.createScreenCapture(screen);
        } catch (AWTException e) {
            System.out.println(e.getMessage());
        }
        
        return image;

    }


    public BufferedImage getImage(){
        return image;
    }


    private BufferedImage createBase(){
        BufferedImage bi = new BufferedImage(300, 300, BufferedImage.TYPE_4BYTE_ABGR_PRE);
        Graphics g = bi.getGraphics();
        g.drawString("Test", 20, 20);
        return bi;
    }

    private void save(BufferedImage i){
        try {
          File out = new File("Ex.jpeg");
          ImageIO.write(i, "jpeg", out);
        } catch (Exception e) {
        }

    }

}
