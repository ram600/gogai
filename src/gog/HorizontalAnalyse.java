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
public class HorizontalAnalyse extends Paint{
    

    protected HashMap<Cell,ArrayList> tmp_cells_list = new HashMap<Cell,ArrayList>();


    public HorizontalAnalyse(Board board) {
        super(board);
    }

   

//    public void paintResult(Cell main,int[] points,Color bg_color,Color font_color) {
//
//            Graphics g = this.board.ip.getGraphics();
//            g.setFont(new Font(null, 3, 10));
//            g.setColor(bg_color);
//            g.fillRect(main.getXTop()+4,main.getYTop()+4, Cell.WIDTH/2+10, 30);
//            g.setColor(font_color);
//                //
//            g.drawString(Integer.toString(points[0]) + "+" + Integer.toString(points[1])+getRow(points[2]), main.getXTop()+4, main.getYTop() + Math.round(Cell.HEIGHT / 4));
//            g.drawString("Б "+points[3]+" Д "+points[4], main.getXTop()+4, main.getYTop() + (Math.round(Cell.HEIGHT / 4)*2));
//
//    }

   
    public void check(Cell c) {

        if (this.tmp_cells == null) {
            this.tmp_cells = this.cells;
        }

        int t = -1;



        //ArrayList<Cell> t1 = chain(c, c.row_numb + -1 * t, c.col_numb + -1 * t, t, true);
        //ArrayList<Cell>  t2 = chain(c, c.row_numb, c.col_numb + -2 * t, t, true);
        //ArrayList<Cell> t3 = chain(c, c.row_numb + 1 * t, c.col_numb + -1 * t, t, true);

        //***** если между нормальная клетка,то ищем по этому вектору
         int pos_betw = getPostition(c.row_numb+-1*t, c.col_numb);
         ArrayList<Cell> t1 = new ArrayList<Cell>();
         ArrayList<Cell> t6 = new ArrayList<Cell>();
         if(pos_betw > 0){
             Cell cell_between = this.tmp_cells.get(pos_betw);
             if(cell_between.color.name != IColor.UNDEFINED_COLOR){
               t1 = chain(c, c.row_numb + -1 * t, c.col_numb + -1 * t, t, true);
               
               t = 1;
               t6 = chain(c, c.row_numb + 1 * t, c.col_numb + -1 * t, t, true);
             }
         }



         t = -1;
         
        //***** если между нормальная клетка,то ищем по этому вектору
         pos_betw = getPostition(c.row_numb, c.col_numb + -1 * t);
         ArrayList<Cell> t2 = new ArrayList<Cell>();
         if(pos_betw > 0){
             Cell cell_between = this.tmp_cells.get(pos_betw);
             if(cell_between.color.name != IColor.UNDEFINED_COLOR){
               t2 = chain(c, c.row_numb, c.col_numb + -2 * t, t, true);
             }
         }

         
        //*****
        


        //***** если между нормальная клетка,то ищем по этому вектору
         pos_betw = getPostition(c.row_numb + 1*t, c.col_numb);
         ArrayList<Cell> t3 = new ArrayList<Cell>();
         ArrayList<Cell> t4 = new ArrayList<Cell>();
         if(pos_betw > 0){
             Cell cell_between = this.tmp_cells.get(pos_betw);
             if(cell_between.color.name != IColor.UNDEFINED_COLOR){
               t3 = chain(c, c.row_numb + 1 * t, c.col_numb + -1 * t, t, true);
               
               t = 1;
               t4 = chain(c, c.row_numb + -1 * t, c.col_numb + -1 * t, t, true);
             }
         }





        t = 1;
        //ArrayList<Cell> t4 = chain(c, c.row_numb + -1 * t, c.col_numb + -1 * t, t, true);
        // ArrayList<Cell> t5 = chain(c, c.row_numb, c.col_numb + -2 * t, t, true);
        //ArrayList<Cell> t6 = chain(c, c.row_numb + 1 * t, c.col_numb + -1 * t, t, true);

        //***** если между нормальная клетка,то ищем по этому вектору
         pos_betw = getPostition(c.row_numb, c.col_numb + -1 * t);
         ArrayList<Cell> t5 = new ArrayList<Cell>();
         if(pos_betw > 0){
             Cell cell_between = this.tmp_cells.get(pos_betw);
             if(cell_between.color.name != IColor.UNDEFINED_COLOR){
               t5 = chain(c, c.row_numb, c.col_numb + -2 * t, t, true);
             }
         }
        
        //*****



        


        t1.addAll(t6);
        t3.addAll(t4);


        ArrayList<Cell> res = new ArrayList();

        int direct = Cell.DOWN;
        res = t1;

        if (res.size() < t2.size()) {
            direct = Cell.RIGHT;
            res = t2;
        }

        if (res.size() < t3.size()) {
            direct = Cell.UP;
            res = t3;
        }
        if (res.size() < t5.size()) {
            direct = Cell.LEFT;
            res = t5;
        }

       
      
        
        Point point = calc(res, c, false);
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
    public void rivelPoint(){

         HorizontalAnalyse va = new HorizontalAnalyse(this.board);
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


}
