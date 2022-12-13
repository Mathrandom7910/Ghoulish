package me.mathrandom7910.ghoulish.client.util;

import me.mathrandom7910.ghoulish.client.misc.MCInst;
import net.minecraft.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class EntityUtil implements MCInst {

    @SuppressWarnings("unchecked")
    public static <T extends Entity> List<T> findEntities(Class<T> entityClass) {
        List<T> entities = new ArrayList<>();
        for(Entity entity : mc.world.getEntities()) {
            if(entity.equals(mc.player)) continue;
            if(entityClass.isAssignableFrom(entity.getClass())) {
                entities.add((T) entity);
            }
        }
        return entities;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Entity> T findClosest(Class<T> entityClass, float range) {
        for(Entity entity : mc.world.getEntities()) {
            if(entityClass.isAssignableFrom(entity.getClass()) && !entity.equals(mc.player) && entity.distanceTo(mc.player) <= range) {
                return (T) entity;
            }
        }
        return null;
    }
}
