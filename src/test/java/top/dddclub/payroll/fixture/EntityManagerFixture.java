package top.dddclub.payroll.fixture;

import top.dddclub.payroll.core.gateway.persistence.EntityManagers;

import javax.persistence.EntityManager;

public class EntityManagerFixture {
    private static final String PERSISTENCE_UNIT_NAME = "PAYROLL_JPA";

    public static EntityManager createEntityManager() {
        return EntityManagers.from(PERSISTENCE_UNIT_NAME);
    }
}
