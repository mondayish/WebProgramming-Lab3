package ru.itmo.angry_beavers.beans;

import lombok.Getter;
import lombok.Setter;
import ru.itmo.angry_beavers.database.DBStorage;
import ru.itmo.angry_beavers.model.PointQ;
import ru.itmo.angry_beavers.services.InAreaChecker;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@ManagedBean(name = "pointsBean")
@ApplicationScoped
@Setter
public class PointsBean implements Serializable {

    @Getter
    // -5 -4 -3 -2 -1 0 1
    private boolean[] x = new boolean[7];

    @ManagedProperty("#{dao}")
    private DBStorage dbStorage;

    @Getter
    // 1 2 3 4 5
    private boolean[] r = new boolean[5];


    public void init() {
        allPoints = dbStorage.getAllPoints();
    }

    @Getter
    private double y;

    @Getter
    private List<PointQ> allPoints;

    public void clearTable() {
        for (PointQ p : dbStorage.getAllPoints()) {
            dbStorage.removePoint(p);
        }
        allPoints.clear();
    }

    @Getter
    private double xx, yy, rr;

    public String addPointSuper() {
        addPoint(xx, yy, rr);
        return "main?faces-redirect=true";
    }

    public void addPoint(double x, double y, double r) {
        PointQ currentPoint = new PointQ();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy");
        currentPoint.setQueryTime(dateFormat
                .format(new Date(System.currentTimeMillis())));
        currentPoint.setX(x);
        currentPoint.setR(r);
        currentPoint.setY(y);
        currentPoint.setInArea(InAreaChecker.isInArea(x, y, r));
        allPoints.add(currentPoint);
        dbStorage.addPoint(currentPoint);
    }

    public String addPointsFromFields() {
        for (int i = 0; i < x.length; i++) {
            if (!x[i]) continue;

            for (int j = 0; j < r.length; j++) {
                if (!r[j]) continue;

                addPoint(i - 5.0, y, j + 1.0);
            }
        }
        return "main?faces-redirect=true";
    }
}