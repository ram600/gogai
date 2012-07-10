/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gog;

import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.image.RGBImageFilter;

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

    protected  RGBImageFilter filter ;
    
    public Mouse(PointCollection pc,Board b) {
        this.pc = pc;
        this.board = b;
        try {
            robot = new Robot();
        } catch (Exception e) {
        }
       
    }

    public void simpleClick(int x,int y){

        robot.mouseMove(x,y); // Передвигаем мышь на координаты 100,100
        robot.delay(20);
        robot.mousePress(InputEvent.BUTTON1_MASK); //Кликаем левой кнопкой мыши.
        robot.delay(10);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        robot.delay(100); //Через одну секунду.
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





}
