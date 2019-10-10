package top.dddclub.payroll.core.gateway.persistence;

import top.dddclub.payroll.core.domain.AggregateRoot;
import top.dddclub.payroll.core.domain.Identity;
import top.dddclub.payroll.core.gateway.persistence.exception.InitializeEntityManagerException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;

public class Repository<E extends AggregateRoot, ID extends Identity> {
    private Class<E> entityClass;
    private EntityManager entityManager;

    public Repository(Class<E> entityClass, EntityManager entityManager) {
        this.entityClass = entityClass;
        this.entityManager = entityManager;
    }

    public Optional<E> findById(ID id) {
        if (entityManager == null) {
            throw new InitializeEntityManagerException();
        }

        E root = entityManager.find(entityClass, id);
        if (root == null) {
            return Optional.empty();
        }
        return Optional.of(root);
    }

    public List<E> findAll() {
        CriteriaQuery<E> query = entityManager.getCriteriaBuilder().createQuery(entityClass);
        query.select(query.from(entityClass));
        return entityManager.createQuery(query).getResultList();
    }

    public List<E> findBy(Specification<E> specification) {
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
}
