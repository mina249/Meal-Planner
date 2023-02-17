package com.example.mealplaner.Network.Interfaces;

public interface RemoteSource {
    public void enqueueCall(NetworkDelegate networkDelegate);
    public void enqueueRecommended(NetworkDelegate networkDelegate);
}
