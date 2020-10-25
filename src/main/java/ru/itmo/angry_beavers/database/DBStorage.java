package ru.itmo.angry_beavers.database;

import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.itmo.angry_beavers.model.PointQ;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.List;
import java.util.function.Consumer;

@ManagedBean(name = "dao")
@ApplicationScoped
public class DBStorage implements PointsRepository {


    private final SessionFactory sessionFactory;

    public DBStorage() {
        Configuration configuration = new Configuration().configure();
        configuration.addAnnotatedClass(PointQ.class);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    @Override
    public void addPoint(PointQ pointQ) {
        openTransactionFor(session -> session.save(pointQ));
    }

    @Override
    public void removePoint(PointQ pointQ) {
        openTransactionFor(session -> session.delete(pointQ));
    }

    @Override
    public void updatePoint(PointQ pointQ) {
        //maybe later
    }

    @Override
    public List<PointQ> getAllPoints() {
        Session session = sessionFactory.openSession();
        List<PointQ> res = session.createQuery("from PointQ").list();
        session.close();
        return res;
    }

    private void openTransactionFor(Consumer<Session> action) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        action.accept(session);
        transaction.commit();
        session.close();
    }
}
