/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gog;

import gog.Board;

/**
 *
 * @author admin
 */
public class Analyser2 {

  protected Board board;
  protected int auto = 2;
  public boolean maxDamage = false;

    Analyser2(Board board) {
        this.board = board;
    }

    public void  bestBomb(){

            BombAnalyse b = new BombAnalyse(board);
            b.analyse();
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
            m.clickBestStep();
            Ai ai = new Ai(board);
            
          }else{
              m.clickBestDamage();
          }
      }
      Va.points.clear();
    }

    public void autoStep(){
    

    }

     public void setAuto(int auto){
        this.auto = auto;
     }

    

}
