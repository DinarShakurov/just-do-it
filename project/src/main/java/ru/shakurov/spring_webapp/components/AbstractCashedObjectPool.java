package ru.shakurov.spring_webapp.components;

import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractCashedObjectPool<T> implements CashedObjectPool<T> {
    /*private final List<T> cash;*/
    private final Map<T, T> cash;

    public AbstractCashedObjectPool() {
        cash = new ConcurrentHashMap<>();
        /*cash = new CopyOnWriteArrayList<>();*/
    }

    @Override
    public void dispose(T subject) {
        cash.remove(subject);
        /*for (T t : cash) {
            if (t.equals(subject)) {
                cash.remove(subject);
            }
        }*/
    }

    @Override
    public T cashedOf(T subject) {
        if (!cash.containsKey(subject))
            cash.put(subject, subject);

        return cash.get(subject);
        /*for (T t : cash) {
            if (t.equals(subject)) {
                return t;
            }
        }
        cash.add(subject);
        return subject;*/
    }

    @PreDestroy
    public void destroy() {
        cash.forEach((t, t2) -> t2.notifyAll());
        /*cash.forEach(Object::notifyAll);*/
    }
}
