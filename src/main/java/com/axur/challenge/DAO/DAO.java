package com.axur.challenge.DAO;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface DAO<T> {
    
    Optional<T> get(long id);
     
    Collection<T> getAll();
    
    List<T> getList(String[] params);
    
    T getSpecific(T t);
    
    boolean save(T t);
     
    void update(T t, String[] params);
     
    boolean delete(T t);
}
