/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package percept;

import java.util.Random;

/**
 *
 * @author admin
 */
public class Neuron {

    public int[] w; //вес
    private int s = 50; //порог

    /**
     * Конструктор
     * @param m число входов
     * 
     */
    public Neuron(int m) {
        w = new int[m];
    }

    /**
     * Передаточная функция
     * @param x входной вектор
     * @return выходное значение нейрона
     */
    public int transfer(int[] x){
        return activator(adder(x));
    }

    /**
     * Init start weights some little int
     * @param n - 0 to n
     */
    public void initWeight(int n){
        Random rand = new Random();
        for (int i = 0; i < w.length; i++) {
              w[i] = rand.nextInt(n);
        }
    }

    /**
     *
     * @param v скорость обучения
     * @param d разница между выходом нейрона и нужным выходом
     * @param x входной вектор
     */
    public void changeWeight(int v,int d, int[] x){
        for (int i = 0; i < w.length; i++) {
            w[i] += v*d*x[i];
        }
    }

    /**
     *
     * @param x вектор
     * @return невзвешенная сумма
     */
    private int adder(int[] x){
        int nec = 0;
        for(int i =0;i<x.length;i++){
            nec +=x[i]*w[i];
        }
        return nec;
    }



    /**
     * нелинейны преобразователь
     * @param nec
     * @return
     */
    private int activator(int nec){
        if(nec >= s) return 1;
        else return 0;
    }

    public int[] getWeights(){
        return w;
    }



}
