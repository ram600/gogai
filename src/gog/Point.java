/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gog;
import java.util.ArrayList;
/**
 *
 * @author admin
 */
public class Point implements Comparable<Point>,Cloneable{

   public int min = 0;
   public int max = 0;
   public int bomb = 0;
   public int tnt = 0;
   public Cell cell;

   public int direct;
   public ArrayList<Cell> result_cells = new ArrayList<Cell>();

   public Point river_point;

   public int getMod(){
    return (min * -1)+max;
   }

   public void summ(Point p){
       min +=p.min;
       max +=p.max;
       bomb +=p.bomb;
       tnt +=p.tnt;
   }
   public boolean  notEmpty(){
    if(min !=0 || max !=0){
        return true;
    }
    return false;
   }

    public int compareTo(Point t) {
        if(getAbsolute() > t.getAbsolute()){
            return 1;
         }
        if(getAbsolute() < t.getAbsolute()){
            return -1;
        }
        return 0;
    }

    public int getAbsolute(){
        return tnt*15 + bomb*10 + Math.round((min/5)) + Math.round((max/5));
    }

    @Override
    protected Point clone() throws CloneNotSupportedException {
        Point p = (Point)super.clone();
        //p.cell = cell.clone();
        
        return p;
    }




}
