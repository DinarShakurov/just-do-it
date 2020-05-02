package ru.shakurov.spring_webapp.components;

public interface CashedObjectPool<T>{
    T cashedOf(T subject);

    void dispose(T subject);
}
