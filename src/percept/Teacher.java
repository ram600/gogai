/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package percept;

import java.awt.Container;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.FilenameFilter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class Teacher {

    private Perceptron perceptron;

    public Teacher(Perceptron p) {
        this.perceptron = p;
    }



    public static void main(String[] args){
           String path = "/home/admin/PerceptronDigits/data";
           Perceptron p = new Perceptron(10, 64*64);
           Teacher t = new Teacher(p);
           t.teach(path, 50);
          

         
           Image im = java.awt.Toolkit.getDefaultToolkit().createImage(path+"/"+"2.7.jpg");

           int w,h;
           w = 64;
           h = 64;
                   ;
           int[] pixels = new int[w*h];
           MediaTracker mt = new MediaTracker(new Container());
           mt.addImage(im,0);

            try {
                mt.waitForAll();
            } catch (InterruptedException ex) {
                Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE, null, ex);
            }


           PixelGrabber pg = new PixelGrabber(im, 0, 0, w, h,pixels,0,w);
           try {
            pg.grabPixels();
            } catch (Exception e) {
            }

           int[] o = p.recognize(t.getInVector(pixels));
           

    }

    public void teach(String path,int n){

        


        class JPGFilter implements FilenameFilter{
            public boolean  accept(File dir,String name){
                return (name.endsWith(".jpg"));
            }
        }

        //загрузка изображений в массив
        String[] list = new File(path+"/").list(new JPGFilter());
        Image[] img = new Image[list.length];
        MediaTracker mt = new MediaTracker(new Container());
        int i =0;
        for(String s: list){
            img[i] = java.awt.Toolkit.getDefaultToolkit().createImage(path+"/"+s);
            mt.addImage(img[i], 0);
            try{
                mt.waitForAll();
            }catch(InterruptedException ex){
                Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE,null,ex);
            }
            i++;
            
        }

        perceptron.initWeights();

        //получение пиксельных массивов изображений и обучение n raz каждой выборке
        PixelGrabber pg;
        int[] pixels = null,x,y;

        int w,h,k = 0;
        while(n -- >0){
            for (int j = 0; j < img.length; j++) {
                w = img[j].getWidth(null);
                h = img[j].getHeight(null);

                if(w*h > perceptron.getM()) continue;

                pixels = new int[w*h];
                pg = new PixelGrabber(img[j], 0, 0, w, h,pixels,0,w);
                try {
                    pg.grabPixels();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE,null,ex);
                }
                x = getInVector(pixels);
                y = getOutVector(Integer.parseInt(String.valueOf(list[j].charAt(0))));
                perceptron.teach(x, y);
            }


            
            
        }

    }



    /**
     * Трансформация пикселей в вектор из 0 - 1
     * @param p пиксели изображения
     * @return вектор входа перцептрона
     */
    private int[] getInVector(int[] p){
        int[] x = new int[p.length];
        for (int i = 0; i < x.length; i++) {

             int r = (p[i] >> 16) & 0xff;
             int g = (p[i] >> 8) & 0xff;
             int b = (p[i]) & 0xff;
                    x[i] = 1;
                    if(r < 200){
                        x[i] = 0;
                    }
                    if(g < 200){
                        x[i] = 0;
                    }
                    if(b < 200){
                       x[i] = 0;
                    }
              //if(p[i] == -1) x[i] = 0; else x[i] = 1;
        }
        return x;
    }

    /**
     * Генерация правильного выходного вектора
     * @param n - цифра, в соответствии с которой
     * нужно построить вектор, другими словами:
     * на каком месте должна быть 1, остальные 0
     * @return - выходной вектор для перцептрона
     */

     private int[] getOutVector(int n){
         int[] y = new int[perceptron.getN()];
         for (int i = 0; i < y.length; i++) {
              if(i == n)y[i] = 1; else y[i] = 0;

         }
         return y;
     }
























}
