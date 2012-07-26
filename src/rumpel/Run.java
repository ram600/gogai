/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rumpel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author admin
 */
public class Run {


    public static class cc{

        
         public cc() {
             Run.sop("111111");
        }

        public  HashMap test(){
            return new HashMap();
        }
        

    }
    public static class cc1 extends cc{

      

        


    }



    static public void main(String[] args){
        new cc1();
    }



    static public void sop(String s){
         System.out.println(s);
    }

    
}
