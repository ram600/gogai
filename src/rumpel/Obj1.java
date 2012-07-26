/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rumpel;

/**
 *
 * @author admin
 */
public class Obj1 {

    Integer param;

    public Obj1(int p) {
        param = p;
    }



    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Obj1)){
            return false;
        }
        Obj1 o = (Obj1)obj;
        return param == o.param;
    }

    @Override
    public int hashCode() {
        return param*17;
    }





    


}
