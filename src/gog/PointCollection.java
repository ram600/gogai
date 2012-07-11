/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gog;

import java.util.ArrayList;
import java.util.Iterator;


/**
 *
 * @author admin
 */
public class PointCollection {

    
    private ArrayList<Point> collection = new ArrayList<Point>();

    protected  Point best_mod  = new Point();
    protected  Point best_min  = new Point();
    protected  Point best_max  = new Point();
    protected  Point best_bomb = new Point();
    protected  Point best_tnt  = new Point();



    public void add(Point p){

       if(p.notEmpty()){
         mbBest(p);
         collection.add(p);
        }
    }

    public void mbBest(Point p){
       if(best_mod.getMod() < p.getMod()){
           best_mod = p;
       }
       if(best_max.max < p.max){
           best_max = p;
       }
       if(best_min.min > p.min){
           best_min = p;
       }
       
       if(best_bomb.bomb < p.bomb || (p.bomb == best_bomb.bomb && p.min < best_bomb.min)){
                best_bomb = p;
       }
          
       
       if(best_tnt.tnt < p.tnt){
           best_tnt = p;
       }
    }

    public boolean haveNotPoint(Point p){
        for (Iterator<Point> it = collection.iterator(); it.hasNext();) {
            Point point = it.next();
            if(point.max == p.max && point.min == p.min && point.cell.row_numb == p.cell.row_numb && point.cell.col_numb == p.cell.col_numb){
                return false;
            }
        }
        return true;
    }

    public ArrayList getCollection(){
        return collection;
    }
    public void clear(){

      best_mod  = new Point();
      best_min  = new Point();
      best_max  = new Point();
      best_bomb = new Point();
      best_tnt  = new Point();
      collection = new ArrayList<Point>();
    }
    public void clearSimpleCollect(){
        collection = new ArrayList<Point>();
    }



}
