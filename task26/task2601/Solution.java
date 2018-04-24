package com.javarush.task.task26.task2601;

import java.util.Arrays;
import java.util.Comparator;

/*
Почитать в инете про медиану выборки
*/
public class Solution {

    public static void main(String[] args) {

    }

    public static Integer[] sort(Integer[] array) {
        //implement logic here
       int median;
       Arrays.sort(array);
        if(array.length%2 == 0){
            median = (array[(array.length/2)-1]+array[(array.length/2)])/2;
        }else {
            median = array[(array.length/2)];
        }
        Arrays.sort(array, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                int result1 = Math.abs((int) ((double) o1 - median));
                int result2 = Math.abs( (int)((double)o2 - median) );
                int addendum = (result1 == result2) ? ((o1 > o2) ? 1 : -1) : 0;

                int result  = (result1 - result2) * 10 + addendum;
                return result;
            }
        });
        return array;

    }
}
