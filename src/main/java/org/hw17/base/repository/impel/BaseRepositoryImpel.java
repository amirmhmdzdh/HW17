package org.hw17.base.repository.impel;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hw17.base.entity.BaseEntity;
import org.hw17.base.repository.BaseRepository;

import java.io.Serializable;
import java.util.List;

public abstract class BaseRepositoryImpel<T extends BaseEntity<ID>, ID extends Serializable>
        implements BaseRepository<T, ID> {

    protected SessionFactory sessionFactory;

    public BaseRepositoryImpel(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public T saveOrUpdate(T entity) {
        Session session = sessionFactory.getCurrentSession();
        if (entity.getId() == null)
            session.persist(entity);
        else
            session.merge(entity);
        return entity;
    }

    @Override
    public T findById(ID id) {
        Session session = sessionFactory.getCurrentSession();

        return session.get(getEntityClass(), id);
    }

    public abstract Class<T> getEntityClass();

    @Override
    public void delete(T entity) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(entity);
    }

    @Override
    public List<T> showAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM " + getEntityClass().getSimpleName(), getEntityClass()).getResultList();
    }
}
