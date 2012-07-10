package gog;

public class Cell implements Comparable<Cell>,Cloneable {

    public static int WIDTH = 100;
    public static int HEIGHT = 100;


    public static final byte UP    = 1;
    public static final byte DOWN  = 2;
    public static final byte LEFT  = 3;
    public static final byte RIGHT = 4;
   
    public int type_position;
    public int row_numb;
    public int col_numb;

    public int absolute_x;
    public int absolute_y;
    
    public IColor color;

    public int direct;
    
    
    public int[] getNeighborPos(){
        int[] i = new int[2];
        i[1]=1;
        i[2]=2;
        return i;
    }
    
    Cell(int row,int cell,int type_pos){
        type_position = type_pos;
        row_numb = row;
        col_numb = cell;
    }

    public Cell clone(){
        Cell result = null;
        try {
              result = (Cell)super.clone();
              //result.color = (IColor)this.color.clone();
              
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        
        return result;
    }


    public int getYTop(){
        return (row_numb*this.WIDTH)- this.WIDTH;
    }
    public int getXTop(){
        return (col_numb*this.HEIGHT)- this.HEIGHT;
    }
    
    public void setColor(IColor color){
        this.color = color;
    }
    
    public static void setWidth(int w){
        Cell.WIDTH = w;
    }
    public static void setHeight(int h){
        Cell.HEIGHT = h;
    }

    @Override
    public String toString() {
        return "Row"+row_numb+" Cell"+col_numb+" Color "+color.name;
    }

    public int compareTo(Cell c) {
        //System.out.println(c.color.name+"---"+this.color.name);
        if(c.color.name != this.color.name){
            return -1;
        }
        return 0;
   }



    


}