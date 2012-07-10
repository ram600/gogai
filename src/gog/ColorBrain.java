package gog;

import java.awt.image.BufferedImage;
import java.lang.Math;

class ColorBrain{

    BufferedImage image;
    
    int countCells;
    int width;
    int height;
    int vertCount;
    int horCount;
    int colors[];
    

    public ColorBrain(BufferedImage bi) {
        image = bi;
        paintColorMatrix();
    }

    public void paintColorMatrix(){

        int color = image.getRGB(100, 100);

        int w = image.getWidth();
        int h = image.getHeight();
        	
        int x_field = Math.round(w/30);
        int y_field = Math.round(h/30);
        
        int x_width = Math.round(w/x_field);
        int y_width = Math.round(h/y_field);

        int x_midddle =  Math.round(x_width/2);
        int y_midddle =  Math.round(y_width/2);

        System.out.println("Ширина клетки = "+x_width);
        System.out.println("Высота клетки = "+y_width);
        System.out.println("До середины по x = "+x_midddle);
        System.out.println("До середины по y = "+y_midddle);

        for(int i =0;i < x_field;i++){
            System.out.println("Центр клетки по x #"+i+" = " +(int)((i*x_width)+x_midddle)+" \n ********** \n" );

            for(int y = 0;y < y_field;y++){
                System.out.println("Центр клетки по y #"+y+" = " +(int)((y*y_width)+y_midddle));
               
               // System.out.println("Coord "+ );

            }
            System.out.println("\n  Конец \n ********** \n");

          
          
        }
        






        int color1 = image.getRGB(100, 100);
        int  red = (color & 0x00ff0000) >> 16;
        int  green = (color & 0x0000ff00) >> 8;
        int  blue = color & 0x000000ff;

        System.out.print(red+ " " +green+ " " +blue);

    }



    






    


}