package gog;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Board {

    public static final byte CELL_POS_NW = 1;
    public static final byte CELL_POS_NE = 2;
    public static final byte CELL_POS_SW = 3;
    public static final byte CELL_POS_SE = 4;
    public static final byte CELL_POS_N = 5;
    public static final byte CELL_POS_E = 6;
    public static final byte CELL_POS_W = 7;
    public static final byte CELL_POS_S = 8;
    public static final byte CELL_POS_SURROUND = 9;
    public int width;
    public int height;
    public int countCell;
    public int maxCell;
    public int maxRow;
    public ArrayList<Cell> cells;
    public ImagePuller ip;
    public BufferedImage bi;
    
    private ArrayList colors = new ArrayList();
    protected Analyser2 analyser2;
    boolean maxDamage = false;
   
    protected int auto = 2;

    Board(ImagePuller ip, int maxCell, int maxRow) {
        
        this.bi = ip.image;
        this.width = this.bi.getWidth();
        this.height = this.bi.getHeight();

        this.maxCell = maxCell;
        this.maxRow = maxRow;
        this.countCell = (maxCell * maxRow);

        this.cells = new ArrayList(this.countCell + 1);

        //this.cells.add(new Cell(0, 0, 0));

        Cell.setWidth(Math.round(this.width / this.maxCell));
        Cell.setHeight(Math.round(this.height / this.maxRow));

        this.ip = ip;
        initColors();
        this.analyser2 = new Analyser2(this);
    }


    public void setIP(ImagePuller ip){
        this.bi = ip.image;
        this.ip = ip;
    }


    public void upImg(ImagePuller ip){
        this.bi = ip.image;
        this.ip = ip;
    }


    public void setAuto(int auto){
        this.auto = auto;
        analyser2.setAuto(auto);
    }

    private void initColors() {
        IColor pink = new IColor("pink");
        pink.min_red = 225;
        pink.max_red = 255;
        pink.min_green = 70;
        pink.max_green = 180;
        pink.min_blue = 160;
        pink.max_blue = 250;
        pink.point = 4;

        IColor red = new IColor("red");
        red.min_red = 150;
        red.max_red = 217;
        red.min_green = 20;
        red.max_green = 80;
        red.min_blue = 10;
        red.max_blue = 100;
        red.point = -5;

        IColor yellow = new IColor("yellow");
        yellow.min_red = 100;
        yellow.max_red = 220;
        yellow.min_green = 140;
        yellow.max_green = 240;
        yellow.min_blue = 0;
        yellow.max_blue = 75;
        yellow.point = -2;

        IColor green = new IColor("green");
        green.min_red = 25;
        green.max_red = 180;
        green.min_green = 170;
        green.max_green = 240;
        green.min_blue = 76;
        green.max_blue = 160;
        green.point = -3;

        IColor purple = new IColor("purple");
        purple.min_red = 180;
        purple.max_red = 224;
        purple.min_green = 120;
        purple.max_green = 190;
        purple.min_blue = 190;
        purple.max_blue = 241;
        purple.point = -1;

        IColor blue = new IColor("blue");
        blue.min_red = 40;
        blue.max_red = 120;
        blue.min_green = 80;
        blue.max_green = 145;
        blue.min_blue = 200;
        blue.max_blue = 255;
        blue.point = -4;

        this.colors.add(red);
        this.colors.add(pink);
        this.colors.add(yellow);
        this.colors.add(green);
        this.colors.add(purple);
        this.colors.add(blue);
    }

    public void setCells() {
        this.cells.clear();
        this.cells.add(new Cell(0, 0, 0));
        
        int i = 0;
        Graphics g = this.ip.getGraphics();

        for (int row = 1; row <= this.maxRow; row++) {
            for (int cell = 1; cell <= this.maxCell; cell++) {
                i++;
                Cell c = new Cell(row, cell, getTypePos(row, cell));
                c.absolute_x = ip.mouse_x+(cell*Cell.WIDTH) - (Cell.WIDTH/2);
                c.absolute_y = ip.mouse_y+(row*Cell.HEIGHT) - (Cell.HEIGHT/2);
                setColor(c);
                g.setColor(Color.black);
                g.drawOval(cell * Cell.WIDTH - Cell.WIDTH / 2 - 3, row * Cell.HEIGHT - Cell.HEIGHT / 2 - 3, 6, 6);
                this.cells.add(c);
            }

        }
        
        analyser2.maxDamage = maxDamage;
        analyser2.setAuto(auto);
        
    }

    public void bestStep(){
       
        analyser2.bestStep();
    }
    public void bestBomb(){
        
        analyser2.bestBomb();
    }

    public void autoStep(){

        analyser2.autoStep();
        
    }


    protected boolean setColorXX(Cell o) {
        System.out.println("Позиция " + o.row_numb + " " + o.col_numb);

        int[] colorlist = new int[64];

        int center_x = o.getXTop() + Cell.WIDTH / 2;
        int center_y = o.getYTop() + Cell.HEIGHT / 2;
        int point = 0;

        ArrayList variants = new ArrayList();

        for (Iterator it = this.colors.iterator(); it.hasNext();) {
            IColor ic = (IColor) it.next();
            this.bi.getRGB(center_x - 6, center_y - 6, 8, 8, colorlist, 0, 6);
            if (ic.name.equals("blue")) {
                System.out.println("Определяем цвета для " + ic.name);
                for (int i = 0; i < colorlist.length; i++) {
                    int r = colorlist[i] & 0xFF;
                    variants.add(Integer.valueOf(r));
                }
            }

        }

        int max = ((Integer) Collections.max(variants)).intValue();
        int min = ((Integer) Collections.min(variants)).intValue();

        System.out.println("Counts -" + variants.size() + " MIN " + min + " MAX" + max);

        int interval = Math.round((max - min) / 10);

        for (int j = 0; j < 10; j++) {
            int mi = min + interval * j;
            int ma = mi + interval;
            point = 0;
            for (Iterator it = variants.iterator(); it.hasNext();) {
                int rr = ((Integer) it.next()).intValue();

                if ((rr >= mi) && (rr <= ma)) {
                    point++;
                }

            }

            System.out.println("Первый интервал " + mi + "-" + ma + " Попаданий " + point);
        }

        return true;
    }

    protected boolean setColor(Cell o) {
        int[] colorlist = new int[64];

        int center_x = o.getXTop() + Cell.WIDTH / 2;
        int center_y = o.getYTop() + Cell.HEIGHT / 2;
        Graphics gg = this.ip.getGraphics();
        for (Iterator it = this.colors.iterator(); it.hasNext();) {
            IColor ic = (IColor) it.next();
            this.bi.getRGB(center_x - 6, center_y - 6, 8, 8, colorlist, 0, 6);
            int point = 0;
            for (int i = 0; i < colorlist.length; i++) {
                int r = colorlist[i] >> 16 & 0xFF;
                int g = colorlist[i] >> 8 & 0xFF;
                int b = colorlist[i] & 0xFF;

                if (!ic.inColor(r, g, b)) {
                    continue;
                }
                if (point == 2) {
                    gg.setColor(new Color(ic.max_red, ic.max_green,ic.max_blue));
                    gg.fillOval(o.col_numb * Cell.WIDTH - Cell.WIDTH / 2 - 3, o.row_numb * Cell.HEIGHT - Cell.HEIGHT / 2 - 3, 20, 20);
                    ic.color = colorlist[i];
                    o.setColor(ic);
                    return true;
                }
                point++;
            }

        }

        IColor non = new IColor("NONE");
        non.color = this.bi.getRGB(center_x, center_y);
        o.setColor(non);
        gg.setColor(new Color(0, 0, 0));
        gg.fillOval(o.col_numb * Cell.WIDTH - Cell.WIDTH / 2 - 3, o.row_numb * Cell.HEIGHT - Cell.HEIGHT / 2 - 3, 20, 20);


        return false;
    }

    protected int getTypePos(int row, int cell) {
        if ((row != 1) && (row != this.maxRow) && (cell != 1) && (cell != this.maxCell)) {
            return 9;
        }

        if ((cell == 1) && (row > 1) && (row < this.maxRow)) {
            return 7;
        }

        if ((cell == this.maxCell) && (row > 1) && (row < this.maxRow)) {
            return 6;
        }

        if ((row == 1) && (cell > 1) && (cell < this.maxCell)) {
            return 5;
        }
        if ((row == this.maxRow) && (cell > 1) && (cell < this.maxCell)) {
            return 8;
        }

        if ((row == 1) && (cell == 1)) {
            return 1;
        }
        if ((row == this.maxRow) && (cell == 1)) {
            return 3;
        }
        if ((row == 1) && (cell == this.maxCell)) {
            return 2;
        }
        if ((row == this.maxRow) && (cell == this.maxCell)) {
            return 4;
        }
        System.out.println("Maybe getTypePos ERROR");
        return 9;
    }
}
