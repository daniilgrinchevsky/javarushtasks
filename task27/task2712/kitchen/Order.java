package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.Tablet;

import java.io.IOException;
import java.util.List;

public class Order {
    private final Tablet tablet;
    protected List<Dish> dishes;

    public Order(Tablet tablet) throws IOException{
        this.tablet = tablet;
        dishes = ConsoleHelper.getAllDishesForOrder();
    }
    public String toString() {
        if (dishes.size() == 0) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (Dish dish : dishes) {
            stringBuffer.append(dish);
            stringBuffer.append(", ");
        }
        String dishesInOrder = stringBuffer.toString().substring(0, stringBuffer.toString().length() - 2);

        return "Your order: " + dishesInOrder + " of " + tablet.toString() + " , cooking time " + getTotalCookingTime() + "min";
    }
    public int getTotalCookingTime(){
        int s = 0;
        for(Dish dish : dishes){
            s = s + dish.getDuration();
        }
        return s;
    }
    public boolean isEmpty(){
        if(dishes.isEmpty())
            return true;
        else
            return false;
    }
}

