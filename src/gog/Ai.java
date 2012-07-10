/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gog;

import java.awt.AWTException;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;
import java.awt.image.PixelGrabber;
import java.awt.image.RGBImageFilter;
import java.io.File;
import javax.imageio.ImageIO;
import percept.Perceptron;
import percept.Teacher;

/**
 *
 * @author admin
 */
public class Ai {

    protected Board  board;
    protected RGBImageFilter filter ;
    protected int hp;
    protected int rivel_hp;
    protected Perceptron perceptron;

    Ai(Board b){
        board = b;
        init();
    }

    

       public void init(){
            teachANN();
            setHp();
        }


    protected void setHp(){

        try {

            Robot r = new Robot();
            Rectangle screen = new Rectangle(board.ip.my_hp_x,board.ip.my_hp_y,400,12);
            BufferedImage image = r.createScreenCapture(screen);

            BufferedImage img1 = image.getSubimage(0,0, 24, 12);
            BufferedImage img2 = image.getSubimage(343,0, 24, 12);

            BufferedImage img11 = img1.getSubimage(0,0,  8, 12);
            BufferedImage img12 = img1.getSubimage(8,0, 8, 12);
            BufferedImage img13 = img1.getSubimage(16,0, 8, 12);
            BufferedImage img21 = img2.getSubimage(0,0,  8, 12);
            BufferedImage img22 = img2.getSubimage(8,0,  8, 12);
            BufferedImage img23 = img2.getSubimage(16,0, 8, 12);


            filter =  new  RGBImageFilter(){
                public int filterRGB(int x, int y, int rgb) {
                    int a = (rgb >> 24) & 0xff;


                    int r = (rgb >> 16) & 0xff;
                    int g = (rgb >> 8)  & 0xff;
                    int b = (rgb >> 0)  & 0xff;

                    if(r < 250){
                        r = 0;
                        g = 0;
                        b = 0;
                    }
                    if(g < 250){
                        r = 0;
                        g = 0;
                        b = 0;
                    }
                    if(b < 250){
                        r = 0;
                        g = 0;
                        b = 0;
                    }
//                    int i = x*y;
//                    int row = (int)Math.floor(i/8);
//                    int col = i - (i - (row*8));
//                     if(row > 7 && col > 5){
//                         r = 0;
//                         g = 0;
//                         b = 0;
//                     }

                    return ((a<<24)|(r << 16)|(g << 8)|b);

                }
	    };
           int[] vec1  =  getInVector(img11,false);
           int[] vec2  =  getInVector(img12,false);
           int[] vec3  =  getInVector(img13,true);
           int[] vec4  =  getInVector(img21,false);
           int[] vec5  =  getInVector(img22,false);
           int[] vec6  =  getInVector(img23,true);

            
        
           int[] result1 = perceptron.recognize(vec1);
           int[] result2 = perceptron.recognize(vec2);
           int[] result3 = perceptron.recognize(vec3);
           int[] result4 = perceptron.recognize(vec4);
           int[] result5 = perceptron.recognize(vec5);
           int[] result6 = perceptron.recognize(vec6);


           System.out.print(Integer.valueOf(getDigit(result1)+""+getDigit(result2)+""+getDigit(result3)).intValue());
           


        } catch (AWTException e) {
            System.out.println(e.getMessage());
        }




    }


    protected void teachANN(){

            String path = "/home/admin/digits";
            perceptron = new Perceptron(10, 9*12);
            perceptron.loadState(path);
           // Teacher t = new Teacher(perceptron);
           // t.teach(path, 100);
            //perceptron.saveState(path);
            

     }

     private BufferedImage prepareHelthImage(BufferedImage b,RGBImageFilter frgb){
             ImageProducer ipro  = new FilteredImageSource(b.getSource(), frgb);
             java.awt.Image wimage = java.awt.Toolkit.getDefaultToolkit().createImage(ipro);

             BufferedImage bi = new BufferedImage(9,12, BufferedImage.TYPE_INT_RGB);
             Graphics g2 = bi.createGraphics();
             g2.drawImage(wimage, 0, 0, null);
             g2.dispose();

             try {
               ImageIO.write(bi, "jpg", new File("/tmp/save"+Math.random()+".jpg"));
             } catch (Exception e) {
             }

          return bi;
     }

     private int[] getInVector(BufferedImage bi,boolean clearlast){
         int[] p = new int[9*12];
         //prepareHelthImage(bi,filter)
         prepareHelthImage(bi, filter);
         PixelGrabber pg = new PixelGrabber(bi,0, 0, 9, 12,p,0,9);
         try {
          pg.grabPixels();
          } catch (Exception e) {
         }

        int[] x = new int[p.length];
        for (int i = 0; i < x.length; i++) {
             int r = (p[i] >> 16) & 0xff;
             int g = (p[i] >> 8) & 0xff;
             int b = (p[i]) & 0xff;

                    x[i] = 1;
                    if(r < 250){
                        x[i] = 0;
                    }
                    if(g < 250){
                        x[i] = 0;
                    }
                    if(b < 250){
                       x[i] = 0;
                    }
//             if(clearlast){
//                 int row = (int)Math.floor(i/8);
//                 int col = i - (i - (row*8));
//                 if(row > 7 && col > 5){
//                     x[i] = 0;
//                 }
//
//             }


             //if(p[i] == 0) x[i] = 0; else x[i] = 1;
        }
        return x;

     }

     protected int getDigit(int[] outVector){

         for(int i=0;i<10;i++){
            if(outVector[i] == 1){
                return i;
            }
         }
         return 0;
         
     }

}
