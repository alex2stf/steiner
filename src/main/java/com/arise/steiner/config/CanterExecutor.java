package com.arise.steiner.config;

import com.arise.core.models.AsyncExecutor;
import com.arise.core.tools.Provider;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class CanterExecutor implements AsyncExecutor {
    Map<String, CompletableFuture> providerMap = new HashMap<>();




    @Override
    public void put(String s, Provider<Object> provider) {
        CompletableFuture completableFuture = CompletableFuture.supplyAsync(() -> provider.get());
        providerMap.put(s, completableFuture);
    }

    public Map<String, Object> results() {
        Map<String, Object> resp = new HashMap<>();
        for (Map.Entry<String,  CompletableFuture> e: providerMap.entrySet()){
            Object r = null;
            try {
                r = e.getValue().get();
            } catch (Exception ex) {
                r = null;
            }
            resp.put(e.getKey(), r);
        }
        return resp;
    }
}
