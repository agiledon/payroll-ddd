package top.dddclub.payroll.core.gateway.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import java.util.function.Consumer;

public class TransactionScope {
    private EntityManager entityManager;

    public TransactionScope(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void using(Consumer<EntityManager> consumer) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            consumer.accept(entityManager);
            entityManager.flush();
            transaction.commit();
        } catch (IllegalArgumentException | PersistenceException ex) {
            transaction.rollback();
        }
    }
}
