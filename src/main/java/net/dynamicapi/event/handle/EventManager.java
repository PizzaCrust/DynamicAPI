package net.dynamicapi.event.handle;

import net.dynamicapi.event.Event;
import net.dynamicapi.meta.EventListener;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Handles all events execution and handling.
 */
public class EventManager {
    private static EventManager RUNTIME_INSTANCE = null;

    private ArrayList<Class<?>> listenerClasses = new ArrayList<Class<?>>();

    private EventManager() {
    }

    public static EventManager init() {
        if (RUNTIME_INSTANCE == null) {
            RUNTIME_INSTANCE = new EventManager();
        }
        return getManager();
    }

    public static EventManager getManager() {
        return RUNTIME_INSTANCE;
    }

    public void registerListener(Class<?> listener) {
        for (Class<?> listenerClass : listenerClasses) {
            if (listenerClass.getName().equals(listener.getName())) {
                return;
            }
        }
        if (!listener.isAnnotationPresent(EventListener.class)) {
            return;
        }
        listenerClasses.add(listener);
    }

    public void callEvent(Event event) throws Exception {
        ArrayList<Method> methodsRelated = new ArrayList<Method>();
        for (Class<?> listener : listenerClasses) {
            for (Method method : listener.getDeclaredMethods()) {
                for (Class<?> parameterType : method.getParameterTypes()) {
                    if (parameterType == event.getClass()) {
                        methodsRelated.add(method);
                    }
                }
            }
        }
        Class<?> eventClass = event.getClass();
        for (Method method : methodsRelated) {
            Object classInstance = method.getDeclaringClass().newInstance();
            method.invoke(classInstance, eventClass.cast(event));
        }
    }
}