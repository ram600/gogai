/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gog;

import gog.Board;
import gog.Cell;
import gog.Base;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Iterator;
/**
 *
 * @author admin
 */
public abstract class Paint extends Base {





    
    protected static PointCollection points = new PointCollection();
    
    protected static PointCollection rivel_points =  new PointCollection();

    protected Color bg_color;
    protected Color font_color;

    
    protected Point for_river;
    
    protected boolean is_rivel_point = false;
    protected boolean is_paint = true;



    Paint(Board board){
        super(board);
        this.bg_color = new Color(220,220,220);
        this.font_color = new Color(169,169,169);
    }

    public void setBgColor(Color c){
        this.bg_color = c;
    }
    public void setFontColor(Color c){
        this.font_color = c;
    }

  

    

     public void  analyse(){
        for (int i = 1; i < this.board.countCell + 1; i++) {
            check((Cell) this.cells.get(i));
        }
        int a =1;
     }
     public void show(){
         PointCollection pc = Paint.points;
         if(is_rivel_point){
             pc = Paint.rivel_points;
         }

         for (Iterator<Point> it = pc.getCollection().iterator(); it.hasNext();) {
            paintResult((Point)it.next(), bg_color, font_color);
         }
     }
      
      public void paintSpecial(){
        
          PointCollection pc = Paint.points;
          if(is_rivel_point){
              pc = Paint.rivel_points;
          }

          if(pc.best_mod.max != 0 && pc.best_mod.min != 0 ){
              paintResult(pc.best_mod, new Color(0,63,250), new Color(0,0,0));
          }
          if(pc.best_max.max != 0){
              paintResult(pc.best_max, new Color(255,255,0), new Color(0,0,0));
          }
          if(pc.best_min.min != 0 ){
              paintResult(pc.best_min, new Color(216,17,82), new Color(0,0,0));
          }
          if(pc.best_bomb.bomb != 0 ){
              paintResult(pc.best_bomb, new Color(204,255,0), new Color(0,0,0));
          }
          if(pc.best_tnt.tnt != 0){
              paintResult(pc.best_tnt, new Color(184,0,255), new Color(0,0,0));
          }
          //pc.clearBest();
      
      }

//      public void paintSpecialRivel(){
//           if(Paint.river_best_points_mod[0] != 0 && Paint.river_best_points_mod[0] != 0 ){
//              paintResult(Paint.river_best_cell_mod, Paint.river_best_points_mod, new Color(10,10,10), new Color(0,0,0));
//          }
//          if(Paint.river_best_points_max[1] != 0){
//              paintResult(Paint.river_best_cell_max, Paint.river_best_points_max, new Color(255,255,0), new Color(0,0,0));
//          }
//          if(Paint.river_best_points_min[0] != 0 ){
//              paintResult(Paint.river_best_cell_min, Paint.river_best_points_min, new Color(216,17,82), new Color(0,0,0));
//          }
//          if(Paint.river_best_points_bomb[3] != 0 ){
//              paintResult(Paint.river_best_cell_bomb, Paint.river_best_points_bomb, new Color(204,255,0), new Color(0,0,0));
//          }
//          if(Paint.river_best_points_tnt[4] != 0){
//              paintResult(Paint.river_best_cell_tnt, Paint.river_best_points_tnt, new Color(0,63,250), new Color(0,0,0));
//          }
//            Paint.river_best_points_mod  = new int[5];
//            Paint.river_best_points_min  = new int[5];
//            Paint.river_best_points_max  = new int[5];
//            Paint.river_best_points_bomb = new int[5];
//            Paint.river_best_points_tnt  = new int[5];
//      }

      public void paintResult(Point point,Color bg_color,Color font_color) {
          int top_x;
          int top_y;
          int rivel_pix =0;

              if(is_rivel_point){
                  rivel_pix = 35;
                  point.cell = point.river_point.cell;
                  point.direct = point.river_point.direct;
              }

        
              if(point.direct == Cell.UP || point.direct == Cell.DOWN){
                 top_x = point.cell.getXTop()+4+rivel_pix ;
                 top_y = point.cell.getYTop();
              }else{
                 top_x = point.cell.getXTop()+4+rivel_pix ;
                 top_y = point.cell.getYTop()+ Cell.HEIGHT-40;
              }

              if(is_rivel_point){
                  point.direct = 100;
              }

            Graphics g = this.board.ip.getGraphics();
            
            g.setFont(new Font(null, 10, 9));
            g.setColor(bg_color);
            g.fillRect(top_x,top_y, Cell.WIDTH -Math.round(Cell.WIDTH/2)+4, 20);
            g.setColor(font_color);
                //
            g.drawString(Integer.toString(point.min) + "+" + Integer.toString(point.max)+getRow(point.direct), top_x, top_y + Math.round(Cell.HEIGHT / 6));
            g.drawString("Б"+point.bomb+" Д"+point.tnt, top_x, top_y + (Math.round(Cell.HEIGHT / 6)*2));

     }



      protected String getRow(int direct){

        switch(direct){
            case Cell.LEFT:
                return "<";

            case Cell.UP:
                return "^";

            case Cell.RIGHT:
                return ">";

            case Cell.DOWN:
                return "v";

            default: return "x";

        }

    }


//      protected boolean valid(Cell old,Cell c,int direct){
//          if(old != null){
//
//
//              if(old.row_numb != c.row_numb || old.col_numb != c.col_numb){
//
//                  int row = old.row_numb;
//                  int col = old.col_numb;
//
//                  switch(direct){
//                        case Cell.LEFT:
//                            col +=1;
//                            break;
//                        case Cell.UP:
//                            row +=1;
//                            break;
//                        case Cell.RIGHT:
//                            col -=1;
//                            break;
//                        case Cell.DOWN:
//                            row -=1;
//                            break;
//                  }
//                  if(row != c.row_numb || col != c.col_numb){
//                      return true;
//                  }
//
//              }
//
//
//              }
//          return false;
//
//      }



//      protected void setRiverMax(int[] points,Cell c){
//
//
//        //if(checkMaxRiver(points, c)){
//
//                    //mod
//                int max = ( (points[0]*-1)+points[1]);
//                if( ((Paint.river_best_points_mod[0]*-1)+Paint.river_best_points_mod[1]) < max ){
//                    Paint.river_best_points_mod = points;
//                    Paint.river_best_cell_mod = c;
//                }
//                //min
//                if(Paint.river_best_points_min[0] > points[0] ){
//                        Paint.river_best_points_min = points;
//                        Paint.river_best_cell_min = c;
//                }
//
//                //max
//                 if(Paint.river_best_points_max[1] < points[1]){
//                        Paint.river_best_points_max = points;
//                        Paint.river_best_cell_max = c;
//                }
//                //bomb
//                if(0 < points[3]){
//                     if(Paint.river_best_points_bomb[3] < points[3] ){
//                         Paint.river_best_points_bomb = points;
//                         Paint.river_best_cell_bomb = c;
//                     }
//                     //если больше очков,но столько же бомб
//                     if(Paint.river_best_points_bomb[3] == points[3] && Paint.river_best_points_bomb[0] > points[0]){
//                         Paint.river_best_points_bomb = points;
//                         Paint.river_best_cell_bomb = c;
//                     }
//
//                }
//                //
//
//
//                 //tnt
//                if(0 < points[4]){
//                     if(Paint.river_best_points_tnt[3] < points[3]){
//                         Paint.river_best_points_tnt = points;
//                         Paint.river_best_cell_tnt = c;
//                     }
//                     //если больше очков,но столько же бомб
//                     if(Paint.river_best_points_tnt[3] == points[3] && Paint.river_best_points_tnt[0] > points[0]){
//                         Paint.river_best_points_tnt = points;
//                         Paint.river_best_cell_tnt = c;
//                     }
//
//                }
//         // }
//      }
//
//    protected boolean checkMaxRiver(int[] points,Cell c){
//
//        if(valid(best_cell_mod, c,points[2])     &&
//                valid(best_cell_min, c,points[2]) &&
//                valid(best_cell_max, c,points[2]) &&
//                valid(best_cell_bomb, c,points[2]) &&
//                valid(best_cell_tnt, c,points[2])
//                ){
//                     return true;
//                 }
//        return false;
//
//    }

//    protected void setMax(int[] points, Cell c) {
//
//        if (is_rivel_point) {
//            setRiverMax(points, c);
//
//        } else {
//            //mod
//            int max = ((points[0] * -1) + points[1]);
//            if (((Paint.best_points_mod[0] * -1) + Paint.best_points_mod[1]) < max) {
//                Paint.best_points_mod = points;
//                Paint.best_cell_mod = c;
//            }
//            //min
//            if (Paint.best_points_min[0] > points[0]) {
//                Paint.best_points_min = points;
//                Paint.best_cell_min = c;
//            }
//
//            //max
//            if (Paint.best_points_max[1] < points[1]) {
//                Paint.best_points_max = points;
//                Paint.best_cell_max = c;
//            }
//            //bomb
//            if (0 < points[3]) {
//                if (Paint.best_points_bomb[3] < points[3]) {
//                    Paint.best_points_bomb = points;
//                    Paint.best_cell_bomb = c;
//                }
//                //если больше очков,но столько же бомб
//                if (Paint.best_points_bomb[3] == points[3] && Paint.best_points_bomb[0] > points[0]) {
//                    Paint.best_points_bomb = points;
//                    Paint.best_cell_bomb = c;
//                }
//
//            }
//            //
//
//
//            //tnt
//            if (0 < points[4]) {
//                if (Paint.best_points_tnt[3] < points[3]) {
//                    Paint.best_points_tnt = points;
//                    Paint.best_cell_tnt = c;
//                }
//                //если больше очков,но столько же бомб
//                if (Paint.best_points_tnt[3] == points[3] && Paint.best_points_tnt[0] > points[0]) {
//                    Paint.best_points_tnt = points;
//                    Paint.best_cell_tnt = c;
//                }
//
//            }
//        }
//
//    }

      

     abstract  protected void check(Cell c);
     
    
}
