import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.itmo.angry_beavers.database.DBStorage;
import ru.itmo.angry_beavers.model.PointQ;

import java.util.List;

public class BdTest {

    static DBStorage hibdb;

    @BeforeAll
    static void initAll() {
        hibdb = new DBStorage();
        System.out.println("hibdb initialized");
    }

    @Test
    void addOne() {
        PointQ p1 = new PointQ();
        p1.setInArea(true);
        p1.setQueryTime("xD");
        p1.setX(1.0);
        p1.setY(2.0);
        p1.setR(1.0);
        hibdb.addPoint(p1);
        // I'm lazy, it's working
    }

    @Test
    void removeOne(){
        PointQ p1 = new PointQ();
        p1.setInArea(true);
        p1.setQueryTime("xD");
        p1.setX(1.0);
        p1.setY(2.0);
        p1.setR(1.0);
        hibdb.removePoint(p1);
    }

    @Test
    void anotherTwo(){
        PointQ p1 = new PointQ();
        p1.setInArea(true);
        p1.setQueryTime("xD");
        p1.setX(1.0);
        p1.setY(2.0);
        p1.setR(1.0);
        hibdb.addPoint(p1);
        PointQ p2 = new PointQ();
        p2.setInArea(false);
        p2.setQueryTime("xDDD");
        p2.setX(3.0);
        p2.setY(4.0);
        p2.setR(2.0);
        hibdb.addPoint(p2);
    }

    @Test
    void getAll(){
        List<PointQ> list = hibdb.getAllPoints();
        if(list==null) {
            System.out.println("list is null");
        };
        list.forEach(System.out::println);
        Assertions.assertNotEquals(0, list.size());
    }

}
