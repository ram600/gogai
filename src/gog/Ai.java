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
import java.util.Random;
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
    protected IColor nullhp;

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
            //loadMemory();
            nullhp = new IColor("gray");
            nullhp.min_red = 140;
            nullhp.max_red = 145;
            nullhp.min_green = 125;
            nullhp.max_green = 130;
            nullhp.min_blue = 116;
            nullhp.max_blue = 121;
            
            
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

    protected void setHpStripe(){
            Rectangle screen = new Rectangle(board.ip.my_hp_x,board.ip.my_hp_y,560,12);
            BufferedImage image = robot.createScreenCapture(screen);
 
           BufferedImage img1 = image.getSubimage(0,0, 230, 12);
           BufferedImage img2 = image.getSubimage(325,0, 230, 12);
           hp       =  getMyHpLimit(img1);
           rivel_hp = getRivelHpLimit(img2);
           System.out.println("m "+hp+" r"+rivel_hp);

//            try {
//            ImageIO.write(img1, "jpg",new File("/tmp/save"+Math.random()+".jpg"));
//            ImageIO.write(img2, "jpg",new File("/tmp/save"+Math.random()+".jpg"));
//        } catch (Exception e) {
//        }

           


    }

    private int getMyHpLimit(BufferedImage img) {

        int pix = 0;
        int counter;
        int r = 0;
        int g = 0;
        int b = 0;

      

        for (int i = 10; i < img.getWidth(); i++) {
            counter = 0;
            for (int j = 0; j < img.getHeight(); j++) {

                //x,y
                pix = img.getRGB(i, j);
                r = (pix >> 16) & 0xff;
                g = (pix >> 8) & 0xff;
                b = (pix) & 0xff;

                if (nullhp.inColor(r, g, b)) {
                    break;
                } else {
                    if (counter > 10) {
                        
                        return (int)((230-i)/2.2);
                     
                    } else {
                        counter++;
                    }
                }


            }

        }

        return 0;
    }



    private int getRivelHpLimit(BufferedImage img) {

        int pix = 0;
        int counter;
        int r = 0;
        int g = 0;
        int b = 0;

    

        for (int i = 220; i > 0; i--) {
            counter = 0;
            for (int j = 0; j < img.getHeight(); j++) {

                //x,y
                pix = img.getRGB(i, j);
                r = (pix >> 16) & 0xff;
                g = (pix >> 8) & 0xff;
                b = (pix) & 0xff;

                if (nullhp.inColor(r, g, b)) {
                    break;
                } else {
                     if (counter > 10) {
                        return (int)(i/2.2);
                    } else {
                        counter++;
                    }
                }


            }

        }

        return 0;
    }


    public boolean isMyStep(boolean blitz){


        //take 2 random row and if mor then 2 cell = NONE COLOR // it is rivel step

        
        int count = 0;
        int max = 5;
        int add = 1;

        if(blitz){
            max = 2;
            add = 5;
        }


        for (int i = 0; i < 2 ; i++) {
            int rand_row = new Random().nextInt(max)+add;


            for(int j =0;j < 9;j++){
                System.out.println((rand_row * 9)+j);
                Cell c = board.cells.get((rand_row * 9)+j);
                if(c.color.name == IColor.UNDEFINED_COLOR){
                   count++;
                }
                
            }
            if(count >= 4){
                return false;
            }

        }


        return true;

    }


    public void makeStep2(){
        setHpStripe();

       
        if(isMyStep(false)){
             getMaxDamage();
         }








    }

    private void getMaxDamage(){
        Point max_dam = null;
        Mouse mouse1 = null;
        Mouse mouse2 = null;
        int type = 0;

        BombAnalyse tnt = null;
        if(tntExist()){
            tnt = new BombAnalyse(board);
            tnt.is_tnt = true;
            tnt.analyse();
             mouse1 = new Mouse(tnt.points, board);
            mouse1.delta_x = -60;
            mouse1.delta_y = 105;

            max_dam = tnt.points.best_min;
            if( (rivel_hp+max_dam.min) <= 0){
                 mouse1.baseBomb(max_dam);
                 return;
            }

            
        }
        BombAnalyse b = null;
        if (bombExits()) {
            b = new BombAnalyse(board);
            b.analyse();
             mouse2 = new Mouse(BombAnalyse.points, board);
            mouse2.delta_x = -60;
            mouse2.delta_y = 27;

            max_dam = b.points.best_min;
            if( (rivel_hp+max_dam.min) <= 0){
                 mouse2.baseBomb(max_dam);
                 return;
            }
            
           }

            HorizontalAnalyse Ha = new HorizontalAnalyse(this.board);
            VerticalAnalyse Va = new VerticalAnalyse(this.board);

            Ha.analyse();
            Va.analyse();


            mouse = new Mouse(VerticalAnalyse.points, this.board);
            max_dam = HorizontalAnalyse.points.best_min;
            if ((rivel_hp + max_dam.min) <= 0) {
                mouse.baseStep(max_dam);
                return;
            }

//            //*******************HP
            if(hp <=30){
                Point best_h = new Point();

                if(mouse1 != null){
                    type = 1;
                    best_h = mouse1.getBestHealth();
                }
                if(mouse2 != null){
                    Point bbh = new Point();
                    if(bbh.max >= best_h.max){
                        type = 2;
                        best_h = bbh;
                    }
                }
                Point bvhh = mouse.getBestHealth();
                if(bvhh.max >= best_h.max){
                    best_h = bvhh;
                    type = 0;
                }

                if(best_h.max > 10){
                    if(type == 1){
                        mouse1.baseBomb(best_h);
                    }
                    if(type == 2){
                        mouse2.baseBomb(best_h);
                    }
                    mouse.baseStep(best_h);
                    return;
                }

            }



            //get tnt with max damage
            Point tnt_step = new Point();

            type = 0;

            if(tnt != null){

                if(mouse1.getPoints().best_tnt.tnt > 0){
                    tnt_step = mouse1.getPoints().best_tnt;
                    type = 1;
                }


            }
            if(b != null){

                if(mouse2.getPoints().best_tnt.tnt >= tnt_step.tnt && mouse2.getPoints().best_tnt.min < tnt_step.min){
                    tnt_step = mouse2.getPoints().best_tnt;
                    type = 2;
                }
            }

            if(mouse.getPoints().best_tnt.tnt >= tnt_step.tnt && mouse.getPoints().best_tnt.min < tnt_step.min){
                tnt_step = mouse.getPoints().best_tnt;
                type = 0;

            }
              if(tnt_step.tnt > 0){
                    if(type == 1){
                        mouse1.baseBomb(tnt_step);
                    }
                    if(type == 2){
                        mouse2.baseBomb(tnt_step);
                    }
                    mouse.baseStep(tnt_step);
                    return;
                }

            //get  bomb with max damage
            Point bomb_step = new Point();

            type = 0;

            if(tnt != null){

                if(mouse1.getPoints().best_bomb.bomb > 0){
                    bomb_step = mouse1.getPoints().best_bomb;
                    type = 1;
                }


            }
            if(b != null){

                if(mouse2.getPoints().best_bomb.bomb >= bomb_step.bomb && mouse2.getPoints().best_bomb.min < bomb_step.min){
                    bomb_step = mouse2.getPoints().best_bomb;
                    type = 2;
                }
            }

            if(mouse.getPoints().best_bomb.bomb >= bomb_step.bomb && mouse.getPoints().best_bomb.min < bomb_step.min){
                bomb_step = mouse.getPoints().best_bomb;
                type = 0;

            }
              if(bomb_step.bomb > 0){
                    if(type == 1){
                        mouse1.baseBomb(bomb_step);
                    }
                    if(type == 2){
                        mouse2.baseBomb(bomb_step);
                    }
                    mouse.baseStep(bomb_step);
                    return;
                }














            Point best_damage = new Point();
            type = 0;

            if(tnt != null){
                best_damage = mouse1.getBestDamage();
                type = 1;
            }
            if(b != null){
                Point bomb_damage = mouse2.getBestDamage();
                if(bomb_damage.min < best_damage.min){
                    best_damage = bomb_damage;
                    type = 2;
                }
            }
            Point simple_damage = mouse.getBestDamage();
            if(simple_damage.min < best_damage.min){
                best_damage = simple_damage;
                    type = 0;

            }
              if(best_damage.min < 0){
                    if(type == 1){
                        mouse1.baseBomb(best_damage);
                    }
                    if(type == 2){
                        mouse2.baseBomb(best_damage);
                    }
                    mouse.baseStep(best_damage);
                    return;
                }



            //Ищем бомбы или динамиты с максимальным уроном
            //если нет лучший минус
            //makeBestStep();

            


            





       return;
    }

    public void makeStep(){
        setHpStripe();

        if(isMyStep(false)){
        
        

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



        
       

    }

    public void makeBlitz(){
        
        if(isMyStep(true)){
              HorizontalAnalyse Ha = new HorizontalAnalyse(this.board);
              VerticalAnalyse   Va = new VerticalAnalyse(this.board);

              Ha.analyse();
              Va.analyse();


              mouse = new Mouse(VerticalAnalyse.points,this.board);
              mouse.auto = 1;
              mouse.baseStep(mouse.getBestDamage());
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

//             try {
//               ImageIO.write(bi, "jpg", new File("/tmp/save"+Math.random()+".jpg"));
//             } catch (Exception e) {
//             }

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

     private boolean tntExist(){
            try {

                 Rectangle screen = new Rectangle(board.ip.mouse_x-60, board.ip.mouse_y+105,10,10);
                 BufferedImage image = robot.createScreenCapture(screen);

//                 try {
//                    ImageIO.write(image, "jpg",new File("/tmp/save"+Math.random()+".jpg"));
//                } catch (Exception e) {
//                }

                 IColor gray = new IColor("red");
                 gray.min_red = 215;
                 gray.max_red = 250;
                 gray.min_green = 38;
                 gray.max_green = 71;
                 gray.min_blue = 10;
                 gray.max_blue = 46;

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


        return false;


     }

}
