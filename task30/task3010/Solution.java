package com.javarush.task.task30.task3010;

/* 
Минимальное допустимое основание системы счисления
*/

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Set;
import java.util.TreeSet;

public class Solution {
    public static void main(String[] args) {
        //напишите тут ваш код
        TreeSet<Integer> set = new TreeSet<>();
            for (int i=2;i<37;i++){
                try {
                    BigDecimal bi = new BigDecimal(new BigInteger(args[0],i));
                    set.add(i);
                }
                catch (Exception e){

                }
            }
            if(set.isEmpty())
                System.out.println("incorrect");
            else
                System.out.println(set.first());
    }
}