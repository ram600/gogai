/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gog;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

/**
 *
 * @author admin
 */
public class Analyser2 {

  protected Board board;
  protected int auto = 2;
  public boolean maxDamage = false;
  protected Ai ai;
    Analyser2(Board board) {
        this.board = board;
        ai = new Ai(this.board);
        ai.setHp();
    }

    public void  bestBomb(){

            BombAnalyse b = new BombAnalyse(board);
            b.analyse();
            b.points.clearSimpleCollect();
            b.show();
            b.paintSpecial();
            if(auto < 2){
                  Mouse m = new Mouse(b.points,this.board);
                  m.delta_x = -60;
                  m.delta_y =  27;
                  m.explosionBestBomb();
             }
            b.points.clear();
        
    }

    public void bestStep(){

      HorizontalAnalyse Ha = new HorizontalAnalyse(this.board);
      VerticalAnalyse   Va = new VerticalAnalyse(this.board);

      Ha.analyse();
      Ha.show();
      //Ha.rivelPoint();

      Va.analyse();
      Va.show();

      
      Va.rivelPoint();
      Va.paintSpecial();

      if(auto < 2){
          Mouse m = new Mouse(Va.points,this.board);
          if(!maxDamage){
            m.baseStep(m.getBestStep());
          }else{
             m.baseStep(m.getBestDamage());
          }
      }
      Va.points.clear();
    }

    public void autoStep(){
        if(auto < 2){
          ai.makeBlitz();
        }else{
          ai.makeStep();
        //bombExits();
        }
    

    }

     public void setAuto(int auto){
        this.auto = auto;
     }


     


}
