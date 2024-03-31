package com.deathmotion.antihealthindicator.managers;

import com.deathmotion.antihealthindicator.AHIPlatform;
import com.deathmotion.antihealthindicator.data.EntityDataStore;
import lombok.Getter;

import java.util.concurrent.ConcurrentHashMap;

@Getter
public class CacheManager<P> {

    private final AHIPlatform<P> platform;

    private final ConcurrentHashMap<Integer, EntityDataStore> entityData = new ConcurrentHashMap<>();

    public CacheManager(AHIPlatform<P> platform) {
        this.platform = platform;
    }

    public void addEntity(int entityId, EntityDataStore entityData) {
        this.entityData.putIfAbsent(entityId, entityData);
    }

    public void removeEntity(int entityId) {
        this.entityData.remove(entityId);
    }

    public EntityDataStore getEntityDataById(int entityId) {
        return this.entityData.get(entityId);
    }
}