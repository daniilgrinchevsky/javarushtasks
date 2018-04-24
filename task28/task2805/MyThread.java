package com.javarush.task.task28.task2805;

public class MyThread extends Thread {
    static int priority = 1;
    public MyThread() {
        this.setPriority(priority);
        if (priority < 10)
            priority++;
        else
            priority = 1;

    }

    public MyThread(Runnable target) {
        super(target);
        this.setPriority(priority);
        if (priority < 10)
            priority++;
        else
            priority = 1;
    }

    public MyThread(ThreadGroup group, Runnable target) {
        super(group, target);
        this.setPriority(priority);
        if (priority < 10)
            priority++;
        else
            priority = 1;
    }

    public MyThread(String name) {
        super(name);
        this.setPriority(priority);
        if (priority < 10)
            priority++;
        else
            priority = 1;
    }

    public MyThread(ThreadGroup group, String name) {
        super(group, name);
        this.setPriority(priority);
        if (priority < 10)
            priority++;
        else
            priority = 1;
    }

    public MyThread(Runnable target, String name) {
        super(target, name);
        this.setPriority(priority);
        if (priority < 10)
            priority++;
        else
            priority = 1;
    }

    public MyThread(ThreadGroup group, Runnable target, String name) {
        super(group, target, name);
        this.setPriority(priority);
        if (priority < 10)
            priority++;
        else
            priority = 1;
    }

    public MyThread(ThreadGroup group, Runnable target, String name, long stackSize) {
        super(group, target, name, stackSize);
        this.setPriority(priority);
        if (priority < 10)
            priority++;
        else
            priority = 1;
    }
}
