/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gog;

/**
 *
 * @author admin
 */
public class BombAnalyse extends Paint {


    boolean is_tnt = false;
    public BombAnalyse(Board board) {
        super(board);
    }




    @Override
    protected void check(Cell c) {

        setTmpCells();
        int pos = getPostition(c.row_numb-1, c.col_numb);
        int max = 0;
        int min = 0;
        Cell tmp;
        if(pos > 0){
            tmp = this.tmp_cells.get(pos);
            if(tmp.color.point > 0){
                max += tmp.color.point;
            }else{
                min += tmp.color.point;
            }
            downCol(tmp);
        }

       
        
        if(is_tnt){
            pos = getPostition(c.row_numb-1, c.col_numb-1);
            if(pos > 0){
            tmp = this.tmp_cells.get(pos);
            if(tmp.color.point > 0){
                max += tmp.color.point;
            }else{
                min += tmp.color.point;
            }
            downCol(tmp);
           }
            pos = getPostition(c.row_numb-1, c.col_numb+1);
            if(pos > 0){
            tmp = this.tmp_cells.get(pos);
            if(tmp.color.point > 0){
                max += tmp.color.point;
            }else{
                min += tmp.color.point;
            }
            downCol(tmp);
           }
            pos = getPostition(c.row_numb+1, c.col_numb-1);
            if(pos > 0){
            tmp = this.tmp_cells.get(pos);
            if(tmp.color.point > 0){
                max += tmp.color.point;
            }else{
                min += tmp.color.point;
            }
            downCol(tmp);
           }
            pos = getPostition(c.row_numb+1, c.col_numb+1);
            if(pos > 0){
            tmp = this.tmp_cells.get(pos);
            if(tmp.color.point > 0){
                max += tmp.color.point;
            }else{
                min += tmp.color.point;
            }
            downCol(tmp);
           }

        }



        downCol(this.tmp_cells.get(getPostition(c.row_numb, c.col_numb)));
           if(c.color.point > 0){
                max += c.color.point;
            }else{
                min += c.color.point;
            }




        pos = getPostition(c.row_numb+1, c.col_numb);
        if(pos > 0){
            tmp = this.tmp_cells.get(pos);
            if(tmp.color.point > 0){
                max += tmp.color.point;
            }else{
                min += tmp.color.point;
            }
            downCol(tmp);
        }

        pos = getPostition(c.row_numb, c.col_numb-1);
        if(pos > 0){
            tmp = this.tmp_cells.get(pos);
            if(tmp.color.point > 0){
                max += tmp.color.point;
            }else{
                min += tmp.color.point;
            }
            downCol(tmp);
        }
         pos = getPostition(c.row_numb, c.col_numb+1);
        if(pos > 0){
            tmp = this.tmp_cells.get(pos);
            if(tmp.color.point > 0){
                max += tmp.color.point;
            }else{
                min += tmp.color.point;
            }
            downCol(tmp);
        }

        


        Point p_vert;
        Point p_hor;
        p_vert = totalPoint(this.tmp_cells,true);
        p_vert.cell = c;
        
       // p_hor = totalPoint(this.tmp_cells, false);
        //p_hor.cell  = c;
        p_vert.max +=max;
        p_vert.min +=min;
        points.add(p_vert);
        
        //points.add(p_hor);
        this.tmp_cells = null;
    }

    




}
