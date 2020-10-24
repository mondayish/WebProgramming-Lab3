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
import java.util.Date;
import java.util.List;

@ManagedBean(name = "pointsBean")
@ApplicationScoped
public class PointsBean implements Serializable {

    @Getter
    // -5 -4 -3 -2 -1 0 1
    private boolean[] x = new boolean[7];

    private DBStorage dbStorage;

    @Getter
    // 1 2 3 4 5
    private boolean[] r  = new boolean[5];


    public void init(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        dbStorage = facesContext.getApplication()
                        .evaluateExpressionGet(facesContext, "#{dao}", DBStorage.class);
        allPoints = dbStorage.getAllPoints();
    }

    @Getter
    private String y;

    @Getter
    private List<PointQ> allPoints;

    public void clearTable(){
        for(PointQ p : dbStorage.getAllPoints()){
            dbStorage.removePoint(p);
        }
    }

    public void AddPointsFromFields() {
        for (int i = 0; i< x.length; i++){
            if(!x[i]) continue;

            for(int j = 0; j< r.length; j++){
                if(!r[j]) continue;

                PointQ currentPoint = new PointQ();
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy");
                currentPoint.setQueryTime(dateFormat
                        .format(new Date(System.currentTimeMillis())));
                currentPoint.setInArea(InAreaChecker
                        .isInArea(currentPoint.getX(),
                                currentPoint.getY(),
                                currentPoint.getR()));
                currentPoint.setX(i-5.0);
                currentPoint.setR(j+1.0);
                allPoints.add(currentPoint);
                dbStorage.addPoint(currentPoint);
            }
        }
    }
}
