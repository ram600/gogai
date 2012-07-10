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
    protected boolean teach = false;
    protected  Mouse mouse;
    protected Robot robot;

    Ai(Board b){
        board = b;
        init();
        try {
          robot = new Robot();
        } catch (AWTException e) {
            System.out.println(e.getMessage());
        }
    }

    

       public void init(){
            perceptron = new Perceptron(10, 9*12);
            //teachANN();
            loadMemory();
            
            
        }


    protected void setHp(){

       

            
            Rectangle screen = new Rectangle(board.ip.my_hp_x,board.ip.my_hp_y,400,12);
            BufferedImage image = robot.createScreenCapture(screen);

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

           hp =Integer.valueOf(getDigit(result1)+""+getDigit(result2)+""+getDigit(result3)).intValue();
           rivel_hp = Integer.valueOf(getDigit(result4)+""+getDigit(result5)+""+getDigit(result6)).intValue();

           System.out.println(hp);
           System.out.println(rivel_hp);

           Run.lb_hp.setText(Integer.toString(hp));
           Run.lb_rivel_hp.setText(Integer.toString(rivel_hp));
       




    }



    public void makeStep(){
        setHp();

        BombAnalyse b = null;
        if (bombExits()) {
            b = new BombAnalyse(board);
            b.analyse();
            mouse = new Mouse(b.points, board);
            mouse.delta_x = -60;
            mouse.delta_y = 27;

            Point p = new Point();
            if (hp > 0 && rivel_hp > 0) {


                mouse.baseBomb(getBestPoint());
             }
            BombAnalyse.points.clear();
           }else{
           
              HorizontalAnalyse Ha = new HorizontalAnalyse(this.board);
              VerticalAnalyse   Va = new VerticalAnalyse(this.board);

              Ha.analyse();
              Va.analyse();


              mouse = new Mouse(VerticalAnalyse.points,this.board);
              
              mouse.baseStep(getBestPoint());
              VerticalAnalyse.points.clear();
           }




        
       

    }


    private Point getBestPoint(){
        Point p = new Point();
            if (hp > 0 && rivel_hp > 0) {

                Point dmg = mouse.getBestDamage();
                if ((rivel_hp + dmg.min) <= 0) {
                    p = dmg;
                } else {
                    if (hp > rivel_hp) {
                        p = dmg;
                    }
                    if (hp < rivel_hp || p.cell == null) {

                        if ((rivel_hp + dmg.min) <= hp && hp >= 30) {
                            p = dmg;
                        } else {
                            p = mouse.getBestHealth();
                        }

                    }
                    if (p.cell == null) {
                        p = mouse.getBestStep();
                    }

                }

             }
        return p;
    }


    private void teachANN(){

            String path = "/home/admin/digits";
            perceptron.loadState(path);
            Teacher t = new Teacher(perceptron);
            t.teach(path, 150);
            perceptron.saveState(new File(".").getAbsolutePath()+"/data/");
            

     }
    protected void loadMemory(){
        if(!teach){
            perceptron.loadState(new File(".").getAbsolutePath()+"/data/");
            teach = true;
        }
            
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

     private boolean bombExits(){

         try {
                 
                 Rectangle screen = new Rectangle(board.ip.mouse_x-60, board.ip.mouse_y+27,10,10);
                 BufferedImage image = robot.createScreenCapture(screen);


                 IColor gray = new IColor("gray");
                 gray.min_red = 100;
                 gray.max_red = 116;
                 gray.min_blue = 100;
                 gray.max_blue = 110;
                 gray.min_green = 100;
                 gray.max_green = 110;

                 int rgb;
                 int  r ;
                 int  g;
                 int  b ;

                 

                 for(int i =0; i < 10;i++){
                      rgb = image.getRGB(i, i);
                      r = (rgb >> 16) & 0xff;
                      g = (rgb >> 8) & 0xff;
                      b = rgb & 0xff;
                     if(gray.inColor(r, g, b)){
                        return true;
                     }
                 }



                 return false;



         } catch (Exception e) {
         }


        return true;
     }

}
