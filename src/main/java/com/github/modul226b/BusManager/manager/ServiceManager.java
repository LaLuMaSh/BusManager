package com.github.modul226b.BusManager.manager;

import com.github.modul226b.BusManager.service.AbstractService;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * This Manager manages the Repeating tasks for the Application.
 * Gets all Services in the Package: "com.github.modul226b.BusManager.service.services"
 */
public class ServiceManager {

    private HashMap<Class<? extends AbstractService>, AbstractService> serviceHashMap;
    private DataManager dataManager;
    private BusManager busManager;
    private TripManager tripManager;

    public ServiceManager(DataManager dataManager, BusManager busManager, TripManager tripManager) {
        this.dataManager = dataManager;
        this.busManager = busManager;
        this.tripManager = tripManager;
        serviceHashMap = new HashMap<>();

        for (AbstractService loadService : loadServices()) {
            serviceHashMap.put(loadService.getClass(), loadService);
        }
    }

    private void addService(AbstractService service) {
        serviceHashMap.put(service.getClass(), service);
    }

    /**
     * loads all the Services in the Package "com.github.modul226b.BusManager.service.services"
     * @return all Services.
     */
    private List<AbstractService> loadServices() {
        List<AbstractService> result = new ArrayList<>();

        Reflections reflections = new Reflections("com.github.modul226b.BusManager.service.services");
        Set<Class<? extends AbstractService>> classes;
        classes = reflections.getSubTypesOf(AbstractService.class);
        for (Class<? extends AbstractService> c : classes) {
            try {
                AbstractService e = c.getConstructor(DataManager.class, BusManager.class, TripManager.class).newInstance(dataManager,busManager, tripManager);
                result.add(e);

                if (e.startOnStartUp()) {
                    e.start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public AbstractService get(Class<? extends AbstractService> clazz) {
        return serviceHashMap.getOrDefault(clazz, null);
    }
}
