package gog;

/**
 *
 * @author linux
 */
public class IColor implements Cloneable{
    
    public static final String UNDEFINED_COLOR = "NONE";
    
    public String name;
    public int min_red;
    public int max_red;
    public int min_blue;
    public int max_blue;
    public int min_green;
    public int max_green;
    
    public int point;

    
    public int color;
    public String color_name;
    
    IColor(String name){
        this.name = name;
    }
    
    public String toString(){
        
        if(name != IColor.UNDEFINED_COLOR){
            return "("+name+")";
        }
        
        int r =  (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b =  (color) & 0xFF;
        return "("+r+" "+g+" "+b+")";
    }

    public IColor clone(){
        IColor result = null;
        try {
            result = (IColor)super.clone();
            result.name = new String(this.name);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
      
        return result;

    }


    public boolean inColor(int r,int g,int b){
        if (r >= min_red && r <= max_red) {
               if (g >= min_green && g <= max_green) {
                 if (b >= min_blue && b <= max_blue) {
                       return true;
                   }
              }
           }
        
        return false;
    }
    
    
    
    
}
