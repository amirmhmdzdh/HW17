package org.hw17.base.service.impel;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hw17.base.entity.BaseEntity;
import org.hw17.base.repository.BaseRepository;
import org.hw17.base.service.BaseService;
import org.hw17.utility.EntityValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.Serializable;
import java.util.List;

import java.util.Set;

public class BaseServiceImpel<T extends BaseEntity<ID>,
        ID extends Serializable,
        R extends BaseRepository<T, ID>>
        implements BaseService<T, ID> {


    protected final R repository;
    protected final SessionFactory sessionFactory;
    private final Validator validator;
    private final Logger logger;

    public BaseServiceImpel(R repository, SessionFactory sessionFactory) {
        this.repository = repository;
        this.sessionFactory = sessionFactory;
        this.validator = EntityValidator.validator;
        logger = LoggerFactory.getLogger(BaseServiceImpel.class);
    }

    @Override
    public T saveOrUpdate(T entity) {
        Transaction transaction = null;

        if (!isValid(entity)) {
            logger.warn("Invalid entity. Aborting saveOrUpdate operation.");
            return null;
        }
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();

            T t = repository.saveOrUpdate(entity);

            transaction.commit();
            logger.info("Entity saved or updated successfully.");
            session.close();
            return t;
        } catch (Exception e) {
            logger.error("Error occurred while saving or updating the entity: " + e.getMessage(), e);
            assert transaction != null;
            transaction.rollback();
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public T findById(ID id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            T entity = repository.findById(id);
            session.getTransaction().commit();
            return entity;
        } catch (Exception e) {
            logger.error("Error occurred while finding entity by ID: " + e.getMessage(), e);
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void delete(T entity) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            repository.delete(entity);
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.error("Error occurred while deleting entity: " + e.getMessage(), e);
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<T> findAll() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            List<T> findAll = repository.showAll();
            session.getTransaction().commit();
            session.close();
            return findAll;
        } catch (Exception e) {
            logger.error("Error occurred while retrieving all entities: " + e.getMessage(), e);
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean isValid(T t) {
        Set<ConstraintViolation<T>> violations = validator.validate(t);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<T> violation : violations) {
                logger.warn("Validation error: " + violation.getMessage());
                System.out.println(violation.getMessage());
            }
            return false;
        }
        return true;
    }
}
