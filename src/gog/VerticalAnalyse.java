/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gog;

import gog.Board;
import gog.Cell;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
/**
 *
 * @author admin
 */
public class VerticalAnalyse extends Paint{


    protected HashMap<Cell,ArrayList> tmp_cells_list = new HashMap<Cell,ArrayList>();
   

    public VerticalAnalyse(Board board) {
        super(board);
    }

    public void rivelPoint(){
        
         VerticalAnalyse va = new VerticalAnalyse(this.board);
         va.is_rivel_point = true;
         for(Iterator<Point>  it = Paint.points.getCollection().iterator();it.hasNext();){
            Point p =  it.next();
            va.for_river = p;
            va.setCells(p.result_cells);
            va.analyse();
            
         }
         va.show();
         va.paintSpecial();
         va.rivel_points.clear();
    }

    


    public void check(Cell c) {

        int t = -1;


        ArrayList<Cell> t1 = chain(c, c.row_numb + -1 * t, c.col_numb + -1 * t, t, false);
        ArrayList<Cell> t2 = chain(c, c.row_numb + -2 * t, c.col_numb, t, false);
        ArrayList<Cell> t3 = chain(c, c.row_numb + -1 * t, c.col_numb + 1 * t, t, false);

        t = 1;

        ArrayList<Cell> t4 = chain(c, c.row_numb + -1 * t, c.col_numb + -1 * t, t, false);
        ArrayList<Cell> t5 = chain(c, c.row_numb + -2 * t, c.col_numb, t, false);
        ArrayList<Cell> t6 = chain(c, c.row_numb + -1 * t, c.col_numb + 1 * t, t, false);


        //объединяем левый низ-верх и правый низ верх,тем самым сможем найти 4 и 5 клеточные совпадения
        // объединять центр верх-низ смысла нет,там таких совпадений не будет
        t1.addAll(t6); //right
        t3.addAll(t4); //left


        ArrayList<Cell> res = new ArrayList();

        int direct = Cell.RIGHT;
        res = t1;

        if(res.size() < t2.size()){
            
            direct = Cell.DOWN;

            res = t2;
          }

        if(res.size() < t3.size()){
           direct = Cell.LEFT;
          res = t3;
        }

        if(res.size() < t5.size()){
            direct = Cell.UP;
          res = t5;
        }


        

        Point point = calc(res, c, true);
        point.direct = direct;
        if(is_rivel_point && (point.max !=0 || point.min !=0)){
            //если что-то новое
           if(Paint.points.haveNotPoint(point)){
                   point.river_point = for_river;
                   Paint.rivel_points.add(point);
            }
           
        }else{
           point.result_cells = this.tmp_cells;
           Paint.points.add(point);
        }


        this.tmp_cells = null;

    }

    


}