package com.javarush.task.task27.task2712.ad;

import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public final class AdvertisementStorage {
    private static AdvertisementStorage instance = null;
    private final List<Advertisement> videos = new ArrayList<>();

    private AdvertisementStorage(){
        Object someContent = new Object();
        new Advertisement(someContent, "First Video", 5000, 100, 3 * 60);
        new Advertisement(someContent, "Second Video", 100, 10, 15 * 60);
        new Advertisement(someContent, "Third Video", 400, 2, 10 * 60);
    }

    public static synchronized AdvertisementStorage getInstance(){
        if(instance == null)
            instance = new AdvertisementStorage();
        return instance;
    }
    public List<Advertisement> list(){
        return videos;
    }
    public void add(Advertisement advertisement){
        videos.add(advertisement);
    }
}
