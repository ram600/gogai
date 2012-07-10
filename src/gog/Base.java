/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gog;

import gog.Board;
import gog.Cell;
import gog.IColor;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author admin
 */
public class Base {

   protected  Board board;
   protected  ArrayList<Cell> cells;
   protected  ArrayList<Cell> tmp_cells;

   Base(Board board){
       this.board = board;
       this.cells = board.cells;
   }

   public void setCells(ArrayList<Cell> cls){
    this.cells = cls;
    setTmpCells();
   }

   protected  void setTmpCells() {
       this.tmp_cells = getCloneCells(cells);
     }

   protected ArrayList getCloneCells(ArrayList<Cell> list){
        ArrayList tmp = new ArrayList();
        tmp.add(null);
        for (int i = 1; i < this.board.countCell + 1; i++) {
            Cell c = (Cell) list.get(i);
            tmp.add(c.clone());
        }
        return tmp;
   }

   protected ArrayList<Cell> chain(Cell main, int row_numb, int col_numb, int direct, boolean horizontal) {
       if (this.tmp_cells == null) {
            this.tmp_cells = this.cells;
        }

        ArrayList<Cell> stats = new ArrayList<Cell>();

        if (main.color.name != IColor.UNDEFINED_COLOR) {
            for (int count = 0; count < 5; count++) {
                int pos;
                //int pos;
                if (horizontal) {
                    pos = getPostition(row_numb, col_numb + -count * direct).intValue();
                } else {
                    pos = getPostition(row_numb + -count * direct, col_numb).intValue();
                }

                if (pos <= 0) {
                    continue;
                }
                Cell upper = (Cell) this.tmp_cells.get(pos);

                if (upper.compareTo(main) == 0) {
                    stats.add(upper);

                } else {
                    break;
                }

            }
        }



        return stats;
    }

   protected Integer getPostition(int row_numb, int col_numb) {
        if ((row_numb <= 0) || (col_numb <= 0) || (row_numb > this.board.maxRow) || (col_numb > this.board.maxCell)) {
            return Integer.valueOf(-1);
        }
        return Integer.valueOf(row_numb * this.board.maxCell - (this.board.maxCell - col_numb));
    }

   
   protected Point calc(ArrayList<Cell> stats, Cell main, boolean vertical) {
        Point point = new Point();
        
        
        if (stats.size() >= 2) {
            System.out.println("XXXXXXXXXXXXXXX" + main);

            setTmpCells();


            int main_pos = getPostition(main.row_numb, main.col_numb).intValue();
            Cell c = (Cell) this.tmp_cells.get(main_pos);




            if (vertical) {
                int direct = 1;
                int rowpos = 0;

                if (c.col_numb > stats.get(0).col_numb) {
                    direct = -1;
                }

                if(c.col_numb == stats.get(0).col_numb){
                    direct = 0;
                    rowpos = -1;
                    if(c.row_numb < stats.get(0).row_numb){
                        rowpos = 1;
                    }
                      
                }


                int tmp_pos = getPostition(c.row_numb+rowpos, c.col_numb + direct).intValue();
                if (tmp_pos > 0) {
                    c.col_numb += direct;
                    c.row_numb += rowpos;

                    Cell tmp1 = (Cell) this.tmp_cells.get(tmp_pos);
                    tmp1.col_numb += direct * -1;
                    tmp1.row_numb += rowpos * -1;
                    this.tmp_cells.set(tmp_pos, c);
                    this.tmp_cells.set(main_pos, tmp1);
                }

            } else {

                int direct = 1;
                int colpos = 0;
                
                if (c.row_numb > stats.get(0).row_numb) {
                    direct = -1;
                }

                if(c.row_numb == stats.get(0).row_numb){
                    direct = 0;
                    colpos = -1;
                    if(c.col_numb < stats.get(0).col_numb){
                        colpos = 1;
                    }

                }


                int tmp_pos = getPostition(c.row_numb + direct, c.col_numb+colpos).intValue();
                if (tmp_pos > 0) {
                    c.row_numb += direct;
                    c.col_numb +=colpos;
                    Cell tmp1 = (Cell) this.tmp_cells.get(tmp_pos);
                    tmp1.row_numb += direct * -1;
                    tmp1.col_numb += colpos * -1;
                    this.tmp_cells.set(tmp_pos, c);
                    this.tmp_cells.set(main_pos, tmp1);
                }

            }

            point = totalPoint(this.tmp_cells, vertical);
            point.cell = main;


  //its more correct  than drop it in vert byt cest la vie
//            this.tmp_cells = null;
            
            

        }

        //ищем самый большой



        return point;
    }

    protected Point totalPoint(ArrayList<Cell> cellslist, boolean vertical) {

        Point point = new Point();  //-points,+points,dynamit,tnt
        Point tmp_poits = new Point(); //-points,+points,dynamit,tnt

        ArrayList<Cell> stats = new ArrayList();
        ArrayList<Cell> vert = new ArrayList();
        ArrayList<Cell> hor = new ArrayList();



        for (int i = 1; i < this.board.countCell + 1; i++) {
            stats.clear();
            Cell c = (Cell) cellslist.get(i);

//            if (vertical) {
//                stats = chain(c, c.row_numb + 1, c.col_numb, -1, false);
//
//            } else {
//                //stats = chain(c, c.row_numb, c.col_numb + 1, -1, true);
//            }

            hor = chain(c, c.row_numb + 1, c.col_numb, -1, false);
            if(hor.size() >=2 ){
                stats.addAll(hor);
            }

            vert = chain(c, c.row_numb, c.col_numb + 1, -1, true);
            if(vert.size() >= 2){
               stats.addAll(vert);
            }


            if (stats.size() >= 2) {

                if(stats.size() > 3){
                   Cell a =  stats.get(0);
                   Cell b =  stats.get(1);
                   Cell c2 =  stats.get(2);
                   //if one line
                   if( (a.row_numb == b.row_numb && b.row_numb == c2.row_numb) || ((a.col_numb == b.col_numb && b.col_numb == c2.col_numb))){
                     point.tnt++;
                   }
                    
                }
                if(stats.size() == 3){
                    point.bomb++;
                }

                if (c.color.point < 0) {
                    point.min += c.color.point * (stats.size() + 1);
                } else {
                    point.max += c.color.point * (stats.size() + 1);

                }
                
                for (Iterator<Cell> it = stats.iterator(); it.hasNext();) {
                    Cell cell = (Cell) it.next();
                    downCol((Cell) cellslist.get(getPostition(cell.row_numb, cell.col_numb)));
                }
                downCol(c);
                tmp_poits = totalPoint(cellslist, vertical);

                point.summ(tmp_poits);


                System.out.println(tmp_poits.min + "ss" + tmp_poits.max);
            }
        }


        return point;
    }

     protected  void downCol(Cell c) {
        int count = c.row_numb - 1;

        int tmp_row = c.row_numb;
        int tmp_col = c.col_numb;

        for (int i = 0; i < count; i++) {
            int pos = getPostition(count - i, tmp_col).intValue();
            Cell down = (Cell) this.tmp_cells.get(pos);
            this.tmp_cells.set(getPostition(tmp_row, tmp_col).intValue(), down);
            tmp_row = down.row_numb;
            down.row_numb = (tmp_row + 1);
        }
        c.setColor(new IColor("NONE"));
        c.row_numb = tmp_row;
        this.tmp_cells.set(getPostition(tmp_row, tmp_col).intValue(), c);
    }

}
