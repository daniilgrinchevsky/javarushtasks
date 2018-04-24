package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Dish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConsoleHelper {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message){
        System.out.println(message);
    }
    public static String readString() throws IOException  {
        return reader.readLine();
    }
    public static List<Dish> getAllDishesForOrder() throws IOException  {
        List<Dish> order = new ArrayList<>();
        writeMessage(Dish.allDishesToString());


        while (true) {
                String dish = readString();
                if(dish.equalsIgnoreCase("exit")) break;
            try{
                order.add(Dish.valueOf(dish));
            }
            catch (Exception e){
                writeMessage("Dish doesn't exist");
            }
        }
        return order;
    }
}
