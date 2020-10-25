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
        //SshConnection connection = new SshConnection();
        //connection.getUrl();
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
        System.out.println("getAllPoints: session opens ");
        List<PointQ> res = session.createQuery("from PointQ").list();
        System.out.println("getAllPoints: created query");
        session.close();
        System.out.println("getAllPoints: session closed");
        return res;
    }

    private void openTransactionFor(Consumer<Session> action) {
        Session session = sessionFactory.openSession();
        System.out.println("openTransactionFor: session opened");
        Transaction transaction = session.beginTransaction();
        System.out.println("openTransactionFor: transaction begin");
        action.accept(session);
        System.out.println("openTransactionFor: consumer did smth");
        transaction.commit();
        System.out.println("openTransactionFor: transaction commited");
        session.close();
        System.out.println("openTransactionFor: session closed");
    }
}
