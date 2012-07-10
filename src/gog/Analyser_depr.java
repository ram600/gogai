package gog;

import gog.Board;
import gog.Board;
import gog.Cell;
import gog.Cell;
import gog.IColor;
import gog.IColor;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Robot;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;

public class Analyser_depr {

    protected Board board;
    protected ArrayList<Cell> tmp_cells;

    protected Cell[] best_cells = new Cell[2];//vert,hor
    protected int[] best_points_vert  = {0,0,0,0,0};//vert negatice,vert pos,direct,bomb,tnt
    protected int[] best_points_hor = {0,0,0,0,0};//hor nega,hor pos,direct,bomb,tnt


    Analyser_depr(Board b) {
        this.board = b;
    }

    public void run() {
        int[] res1;
        int[] res2;

        for (int i = 1; i < this.board.countCell + 1; i++) {
            Cell c = (Cell) this.board.cells.get(i);


            res1 = checkVertical((Cell) this.board.cells.get(getPostition(c.row_numb, c.col_numb).intValue()));
            paintResult(c, res1, true,new Color(0,0,0));

            res2 = checkHorizon((Cell) this.board.cells.get(getPostition(c.row_numb, c.col_numb).intValue()));
            paintResult(c, res2, false,new Color(255,255,255));

        }

        //the best
        paintResult(best_cells[1], best_points_hor, false, Color.red);
        paintResult(best_cells[0], best_points_vert, true, Color.blue);
     
    }

    public Integer getPostition(int row_numb, int col_numb) {
        if ((row_numb <= 0) || (col_numb <= 0) || (row_numb > this.board.maxRow) || (col_numb > this.board.maxCell)) {
            return Integer.valueOf(-1);
        }
        return Integer.valueOf(row_numb * this.board.maxCell - (this.board.maxCell - col_numb));
    }

    public int[] checkVertical(Cell c) {

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
            direct = Cell.UP;
            res = t2;
          }

        if(res.size() < t3.size()){
           direct = Cell.LEFT;
          res = t3;
        }

        if(res.size() < t5.size()){
            direct = Cell.DOWN;
          res = t5;
        }


        int[] result = new int[5];

        int[] points = calc(res, c, true);
        int max = ( (points[0]*-1)+points[1]);
        if( ((best_points_vert[0]*-1)+best_points_vert[1]) < max ){
            best_points_vert[0] = points[0];
            best_points_vert[1] = points[1];
            best_points_vert[2] = direct;
            best_points_vert[3] = points[2];
            best_points_vert[4] = points[3];
            best_cells[0] = c;
        }

        result[0] = points[0];
        result[1] = points[1];
        result[3] = points[2];
        result[4] = points[3];

        result[2] = direct;

        return result;
    }

    private int[] checkHorizon(Cell c) {


        


        int t = -1;
        ArrayList<Cell> t1 = chain(c, c.row_numb + -1 * t, c.col_numb + -1 * t, t, true);
        ArrayList<Cell> t2 = chain(c, c.row_numb, c.col_numb + -2 * t, t, true);
        ArrayList<Cell> t3 = chain(c, c.row_numb + 1 * t, c.col_numb + -1 * t, t, true);


        t = 1;
        ArrayList<Cell> t4 = chain(c, c.row_numb + -1 * t, c.col_numb + -1 * t, t, true);
        ArrayList<Cell> t5 = chain(c, c.row_numb, c.col_numb + -2 * t, t, true);
        ArrayList<Cell> t6 = chain(c, c.row_numb + 1 * t, c.col_numb + -1 * t, t, true);


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
        
        int[] result = new int[5];

        int[] points = calc(res, c, false);
        int max = ( (points[0]*-1)+points[1]);
        if( ((best_points_hor[0]*-1)+best_points_hor[1]) < max ){
            best_points_hor[0] = points[0];
            best_points_hor[1] = points[1];
            best_points_hor[2] = direct;
            best_points_hor[3] = points[2];
            best_points_hor[4] = points[3];
            best_cells[1] = c;
        }

        result[0] = points[0];
        result[1] = points[1];
        result[3] = points[2];
        result[4] = points[3];
        
        result[2] = direct;

        return result;

        //return Integer.valueOf(1);
    }

    private int[] calc(ArrayList<Cell> stats, Cell main, boolean vertical) {
        int[] points = {0, 0 ,0 ,0};

        if (stats.size() >= 2) {
            System.out.println("XXXXXXXXXXXXXXX" + main);

            setTmpCells();


            int main_pos = getPostition(main.row_numb, main.col_numb).intValue();
            Cell c = (Cell) this.tmp_cells.get(main_pos);




            if (vertical) {
                int direct = 1;

                if (c.col_numb > stats.get(0).col_numb) {
                    direct = -1;
                }

                int tmp_pos = getPostition(c.row_numb, c.col_numb + direct).intValue();
                if (tmp_pos > 0) {
                    c.col_numb += direct;
                    Cell tmp1 = (Cell) this.tmp_cells.get(tmp_pos);
                    tmp1.col_numb += direct * -1;
                    this.tmp_cells.set(tmp_pos, c);
                    this.tmp_cells.set(main_pos, tmp1);
                }

            } else {

                int direct = 1;

                if (c.row_numb > stats.get(0).row_numb) {
                    direct = -1;
                }

                int tmp_pos = getPostition(c.row_numb + direct, c.col_numb).intValue();
                if (tmp_pos > 0) {
                    c.row_numb += direct;
                    Cell tmp1 = (Cell) this.tmp_cells.get(tmp_pos);
                    tmp1.row_numb += direct * -1;
                    this.tmp_cells.set(tmp_pos, c);
                    this.tmp_cells.set(main_pos, tmp1);
                }

            }

            points = totalPoint(this.tmp_cells, vertical);



            this.tmp_cells = null;

        }

        //ищем самый большой

        

        return points;
    }

    protected void paintResult(Cell main, int[] points, boolean vertical,Color back_color) {
        if (points[0] != 0 || points[1] != 0) {

            Graphics g = this.board.ip.getGraphics();
            g.setFont(new Font(null, 3, 10));


            if (!vertical) {
                
                g.setColor(back_color);
                g.fillRect(main.getXTop()+4,main.getYTop()+4, Cell.WIDTH/2+10, 30);
                g.setColor(new Color(0,0,0));
                //
                g.drawString(Integer.toString(points[0]) + "+" + Integer.toString(points[1])+getRow(points[2]), main.getXTop()+4, main.getYTop() + Math.round(Cell.HEIGHT / 4));
                g.drawString("Б "+points[3]+" Д "+points[4], main.getXTop()+4, main.getYTop() + (Math.round(Cell.HEIGHT / 4)*2));

            } else {
                g.setColor(back_color);
                g.fillRect(main.getXTop()+4,main.getYTop()+ Cell.HEIGHT-30, Cell.WIDTH/2 + 10, 30);

                g.setColor(new Color(255,255,255));
                g.drawString(Integer.toString(points[0]) + "  +" + Integer.toString(points[1])+getRow(points[2]), main.getXTop()+4, main.getYTop()+ Cell.HEIGHT-20);
                g.drawString("Б "+points[3]+" Д "+points[4], main.getXTop()+4, main.getYTop()+ Cell.HEIGHT-10);
            }
        }
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

    protected void makeClicks(Cell main,int[] points){
          try {
            Robot r = new Robot();
            r.mouseMove(100, 100);
          } catch (Exception e) {
              
          }

         

    }

    private ArrayList<Cell> chain(Cell main, int row_numb, int col_numb, int direct, boolean horizontal) {
        if (this.tmp_cells == null) {
            this.tmp_cells = this.board.cells;
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

    private void setTmpCells() {
        this.tmp_cells = new ArrayList();
        this.tmp_cells.add(null);

        for (int i = 1; i < this.board.countCell + 1; i++) {
            Cell c = (Cell) this.board.cells.get(i);
            this.tmp_cells.add(c.clone());
        }
    }

    private int[] totalPoint(ArrayList<Cell> cells, boolean vertical) {

        int[] points = {0, 0 ,0 ,0};  //-points,+points,dynamit,tnt
        int[] tmp_poits = {0, 0 ,0 ,0}; //-points,+points,dynamit,tnt
        
        ArrayList<Cell> stats = new ArrayList();
        ArrayList<Cell> vert = new ArrayList();
        ArrayList<Cell> hor = new ArrayList();



        for (int i = 1; i < this.board.countCell + 1; i++) {
            stats.clear();
            Cell c = (Cell) cells.get(i);

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
                    points[3]++;
                }
                if(stats.size() == 3){
                    points[2]++;
                }

                if (c.color.point < 0) {
                    points[0] += c.color.point * (stats.size() + 1);
                } else {
                    points[1] += c.color.point * (stats.size() + 1);

                }
                for (Iterator<Cell> it = stats.iterator(); it.hasNext();) {
                    Cell cell = (Cell) it.next();
                    downCol((Cell) cells.get(getPostition(cell.row_numb, cell.col_numb)));
                }
                downCol(c);
                tmp_poits = totalPoint(cells, vertical);

                points[0] += tmp_poits[0];
                points[1] += tmp_poits[1];
                points[2] += tmp_poits[2];
                points[3] += tmp_poits[3];


                System.out.println(tmp_poits[0] + "ss" + tmp_poits[1]);
            }
        }


        return points;
    }

    private void downCol(Cell c) {
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
