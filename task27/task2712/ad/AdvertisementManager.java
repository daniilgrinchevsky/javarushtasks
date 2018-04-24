package com.javarush.task.task27.task2712.ad;

import com.javarush.task.task27.task2712.ConsoleHelper;

import java.util.*;

public class AdvertisementManager {
    private final AdvertisementStorage storage = AdvertisementStorage.getInstance();
    private int timeSeconds;
    private List<List<Advertisement>> combinations;

    public AdvertisementManager(int timeSeconds) {
        this.timeSeconds = timeSeconds;
    }

    public void processVideos() {
        List<Advertisement> videos = new ArrayList<>();
        for(Advertisement ad : storage.list()){
            if(ad.getHits()>0&&ad.getDuration()<timeSeconds)
                videos.add(ad);
        }
        if(videos.isEmpty())
            throw new NoVideoAvailableException();
        
    }

}