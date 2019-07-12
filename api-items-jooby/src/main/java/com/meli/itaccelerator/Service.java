package com.meli.itaccelerator;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import javax.inject.Singleton;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class Service {

    private Cache<Integer,Item> service = CacheBuilder.newBuilder().maximumSize(100).build();

    public List<Item> findall(int min, int max){
        return  service
                .asMap()
                .values()
                .stream()
                .sorted(Comparator.comparing(Item::getId))
                .collect(Collectors.toList());
    }

    public Item find(int id){
        return service.getIfPresent(id);
    }

    public void save(Item item){
        service.put(item.getId(),item);
    }

    public void delete(int id){
        service.invalidate(id);
    }

}
