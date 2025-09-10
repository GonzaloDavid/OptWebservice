package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;


public class GenericDAO<T> {
    @PersistenceContext(unitName = "optPool")
    protected EntityManager em;

    private Class<T> instance;

    public GenericDAO() {
    }

    public GenericDAO(Class<T> instance) {
        this.instance = instance;
    }

    public void insert(T instance) {
        em.persist(instance);
        em.flush();
    }

    public void insertList(List<T> instanceList) {
        for(T item: instanceList)
        {
            em.persist(item);
        }
        em.flush();
    }

    public void remove(T instance) {
        em.remove(em.merge(instance));
        em.flush();
    }

    public void update(T instance) {
        em.merge(instance);
        em.flush();
    }

    public void updateList(List<T> instanceList) {
        for(T item: instanceList)
        {
            em.merge(item);
        }
        em.flush();
    }

    public void deleteList(List<T> instanceList) {
        for(T item: instanceList)
        {
            em.remove(em.merge(item));
        }
        em.flush();
    }

    public void flush() {
        em.flush();
    }
}
