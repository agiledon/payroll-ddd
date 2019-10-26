package top.dddclub.payroll.core.gateway.persistence;

import top.dddclub.payroll.core.domain.AggregateRoot;
import top.dddclub.payroll.core.domain.Identity;
import top.dddclub.payroll.core.gateway.persistence.exception.InitializeEntityManagerException;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;

public class Repository<E extends AggregateRoot, ID extends Identity> {
    private Class<E> entityClass;
    private EntityManager entityManager;
    private TransactionScope transactionScope;

    public Repository(Class<E> entityClass, EntityManager entityManager) {
        this.entityClass = entityClass;
        this.entityManager = entityManager;
        this.transactionScope = new TransactionScope(entityManager);
    }

    public Optional<E> findById(ID id) {
        requireEntityManagerNotNull();

        E root = entityManager.find(entityClass, id);
        if (root == null) {
            return Optional.empty();
        }
        return Optional.of(root);
    }

    public List<E> findAll() {
        requireEntityManagerNotNull();

        CriteriaQuery<E> query = entityManager.getCriteriaBuilder().createQuery(entityClass);
        query.select(query.from(entityClass));
        return entityManager.createQuery(query).getResultList();
    }

    public List<E> findBy(Specification<E> specification) {
        requireEntityManagerNotNull();

        if (specification == null) {
            return findAll();
        }

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> query = criteriaBuilder.createQuery(entityClass);
        Root<E> root = query.from(entityClass);

        Predicate predicate = specification.toPredicate(criteriaBuilder, query, root);
        query.where(new Predicate[]{predicate});

        TypedQuery<E> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }

    public void saveOrUpdate(E entity) {
        requireEntityManagerNotNull();

        if (entity == null) {
            //todo: logging it
            return;
        }

        if (entityManager.contains(entity)) {
            entityManager.merge(entity);
        } else {
            entityManager.persist(entity);
        }
    }

    public void delete(E entity) {
        requireEntityManagerNotNull();

        if (entity == null) {
            //todo: logging it
            return;
        }
        if (!entityManager.contains(entity)) {
            return;
        }

        entityManager.remove(entity);
    }

    private void requireEntityManagerNotNull() {
        if (entityManager == null) {
            throw new InitializeEntityManagerException();
        }
    }

    public void finalize() {
        entityManager.close();
    }
}
