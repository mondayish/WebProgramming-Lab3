package ru.itmo.angry_beavers.beans;

import lombok.Getter;
import ru.itmo.angry_beavers.database.DBStorage;
import ru.itmo.angry_beavers.model.PointQ;
import ru.itmo.angry_beavers.services.InAreaChecker;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@ManagedBean(name = "pointsBean")
@ApplicationScoped
public class PointsBean implements Serializable {

    @Getter
    private boolean x1,x2,x3,x4,x5,x6,x7;

    private DBStorage dbStorage;
    @Getter
    private boolean r1,r2,r3,r4,r5;


    public void init(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        dbStorage = facesContext.getApplication()
                        .evaluateExpressionGet(facesContext, "#{dao}", DBStorage.class);
        allPoints = dbStorage.getAllPoints();
        currentPoint = new PointQ();

        if (allPoints.size() > 0) {
            currentPoint.setX(null);
            currentPoint.setY(null);
            currentPoint.setR(allPoints.get(allPoints.size()-1).getR());
        } else {
            currentPoint.setX(null);
            currentPoint.setY(null);
            currentPoint.setR(5.0);
        }
    }


    @Getter
    private String y;
    @Getter
    private PointQ currentPoint;
    @Getter
    private List<PointQ> allPoints;


    public void clearTable(){
        for(PointQ p : dbStorage.getAllPoints()){
            dbStorage.removePoint(p);
        }
    }

    public void addPointFromFields() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy");
        currentPoint.setQueryTime(dateFormat
                .format(new Date(System.currentTimeMillis())));
        System.out.println(currentPoint.getX());
        System.out.println(currentPoint.getY());
        System.out.println(currentPoint.getR());
        System.out.println(InAreaChecker
                .isInArea(currentPoint.getX(),
                        currentPoint.getY(),
                        currentPoint.getR()));
        currentPoint.setInArea(InAreaChecker
                .isInArea(currentPoint.getX(),
                        currentPoint.getY(),
                        currentPoint.getR()));
        allPoints.add(currentPoint);

        dbStorage.addPoint(currentPoint);

        currentPoint = new PointQ();
        currentPoint.setX(null);
        currentPoint.setY(null);
        currentPoint.setR(allPoints.get(allPoints.size()-1).getR());
    }
}
