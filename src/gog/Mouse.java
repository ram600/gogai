/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gog;

import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.image.RGBImageFilter;
import javax.swing.Timer;


/**
 *
 * @author admin
 */
public class Mouse {

  
    int startx;
    int starty;
    
    protected PointCollection pc;
    protected Robot robot ;
    Board board;
    int delta_x =0;
    int delta_y = 0;

    int auto = 0;
    
    protected  RGBImageFilter filter ;
    
    public Mouse(PointCollection pc,Board b) {
        try {
            this.pc = pc.clone();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        this.board = b;
        try {
            robot = new Robot();
        } catch (Exception e) {
        }
       
    }

    public void simpleClick(int x,int y){

        robot.mouseMove(x,y); // Передвигаем мышь на координаты 100,100
        robot.delay(50); //Через одну секунду.
        robot.mousePress(InputEvent.BUTTON1_MASK); //Кликаем левой кнопкой мыши.
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        robot.delay(300); //Через одну секунду.
    }


    private void setStart(){
        
                   java.awt.Point location = MouseInfo.getPointerInfo().getLocation();
                   startx = (int)location.getX();
                   starty = (int)location.getY();
                   
    }

    public Point getBestStep(){
        
        
        Point bestp = new Point();
        if(pc.best_max.max > 0){
            bestp = pc.best_max;
        }
        if(pc.best_min.min < 0){
            bestp = pc.best_min;
        }
        if(pc.best_bomb.bomb > 0){
            bestp = pc.best_bomb;
        }
        if(pc.best_tnt.tnt > 0){
            bestp = pc.best_tnt;
        }
        return bestp;
    }

    
    public Point getBestDamage(){

            Point bestp = new Point();
        
            if(pc.best_max.min < 0){
             bestp = pc.best_max;
            }
            if(pc.best_min.min < bestp.min){
                bestp = pc.best_min;
            }
            if(pc.best_bomb.min < bestp.min){
                bestp = pc.best_bomb;
            }
            if(pc.best_tnt.min < bestp.min){
                bestp = pc.best_tnt;
            }
            return bestp;
    }

     public Point getBestHealth(){

            Point bestp = new Point();

            if(pc.best_max.max > 0){
             bestp = pc.best_max;
            }
            if(pc.best_min.max > bestp.max){
                bestp = pc.best_min;
            }
            if(pc.best_bomb.max > bestp.max){
                bestp = pc.best_bomb;
            }
            if(pc.best_tnt.max > bestp.max){
                bestp = pc.best_tnt;
            }
            return bestp;
    }

    public void baseStep(Point bestp){
        try {
            if(auto == 0){
                joke(bestp);
            }

             setStart();
             simpleClick(bestp.cell.absolute_x, bestp.cell.absolute_y);

	     if(bestp.direct == Cell.UP){
                delta_y = - Cell.HEIGHT;
             }
             if(bestp.direct == Cell.DOWN){
                 delta_y =  Cell.HEIGHT;
             }
             if(bestp.direct == Cell.RIGHT){
                 delta_x =  Cell.WIDTH;
             }
             if(bestp.direct == Cell.LEFT){
                 delta_x =  -Cell.WIDTH;
             }
            
             simpleClick(bestp.cell.absolute_x+delta_x, bestp.cell.absolute_y+delta_y);
             robot.mouseMove(startx,starty); // Передвигаем мышь на координаты 100,100

        } catch (Exception e) {
        }
    }

    private void joke(Point bestp){
         if(bestp.min <= -35){
                 board.ip.setSize(0, 0);

                Timer t = new javax.swing.Timer(700,new ActionListener() {
                public void actionPerformed(ActionEvent ae) {

                    board.ip.defSize();
                    }
                });
                t.setRepeats(false);
                t.start();

             }
             if(bestp.max >= 20)
             {  Run.axe.setVisible(false);
                board.ip.setSize(0, 0);
                
                Timer t = new javax.swing.Timer(700,new ActionListener() {
                public void actionPerformed(ActionEvent ae) {

                    board.ip.defSize();
                    Run.axe.setVisible(true);
                    }
                });
                t.setRepeats(false);
                t.start();

             }
    }

    public void explosionBestBomb(){
         
         Point bestp = new Point();
        if(pc.best_max.max > 0){
            bestp = pc.best_max;
        }
        if(pc.best_min.min < 0){
            bestp = pc.best_min;
        }
        if(pc.best_bomb.bomb > 0){
            bestp = pc.best_bomb;
        }
        if(pc.best_tnt.tnt > 0){
            bestp = pc.best_tnt;
        }

       baseBomb(bestp);

    }

    public void baseBomb(Point bestp){
         try {
             setStart();
             simpleClick(board.ip.mouse_x+delta_x, board.ip.mouse_y+delta_y);
             simpleClick(bestp.cell.absolute_x, bestp.cell.absolute_y);
             robot.mouseMove(startx,starty); // Передвигаем мышь на координаты 100,100
        } catch (Exception e) {
        }
    }


    public PointCollection getPoints(){
        return pc;
    }



}
