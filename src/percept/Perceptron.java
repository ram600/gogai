/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package percept;

import java.awt.geom.Line2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 *
 * @author admin
 */
public class Perceptron {

    Neuron[] neurons;
    int n,m;

    /**
     * Constructor
     * @param n - число нейронов
     * @param m - число входов каждого нейрона скрытого слоя
     */
    public Perceptron(int n,int m) {
        this.m = m;
        this.n = n;

        neurons = new Neuron[n];
        for (int i = 0; i < n; i++) {
            neurons[i] = new Neuron(m);
        }

    }



    /**
     * Распознавание образа
     * @param x входной вектор
     * @return выходной вектор
     */
    public int[] recognize(int[] x){
        int[] y = new int[neurons.length];

        for(int i = 0;i < neurons.length;i++){
            y[i] = neurons[i].transfer(x);
        }
        return y;
    }

    public void initWeights(){
        for (int i = 0; i < neurons.length; i++) {
                    neurons[i].initWeight(10);
        }
    }


    /**
     * Обучение перцептрона
     * @param x входной вектро
     * @param y правильный выходной вектор
     */
    public void  teach(int[] x,int[] y){

        int d;
        int v = 1;//скорость обучения

        int[] t = recognize(x);
        while (!equal(t,y)) {
            //подстройка весов каждого нейрона
            for (int j = 0; j < neurons.length; j++) {
                 d = y[j] - t[j];
                 neurons[j].changeWeight(v, d, x);

            }
            t = recognize(x);
        }
        
    }

    private boolean  equal(int[] a,int[] b){
        if(a.length != b.length) return false;
        for(int i =0;i< a.length;i++){
            if(a[i] != b[i]) return false;
        }
        return true;
    }


    public int getM(){
        return m;
    }
    public int getN(){
        return n;
    }


    public void saveState(String path){
       
        BufferedWriter fo;
        for(int i =0;i< neurons.length;i++){
            try {
                ;
                fo = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path+"/"+i+".txt"),"UTF-8"));
                int[] w = neurons[i].getWeights();
                for(int j = 0; j < w.length;j++){
                   fo.write(Integer.toString(w[j]));
                   fo.newLine();
                  
                    
                }
                fo.close();
            } catch (Exception e) {
            }
            

        }

    }
    public void loadState(String path){
        BufferedReader fo;
        for(int i =0;i< neurons.length;i++){
            try {
                
                fo = new BufferedReader(new InputStreamReader(new FileInputStream(path+"/"+i+".txt")));
                
                for(int j = 0; j < neurons[i].w.length;j++){
                    String line;
                   do{
                     line = fo.readLine();
                     neurons[i].w[i] = Integer.valueOf(line).intValue();
                   }while(line.length() > 0);
                   

                }
                fo.close();
            } catch (Exception e) {
            }


        }
    }
}
