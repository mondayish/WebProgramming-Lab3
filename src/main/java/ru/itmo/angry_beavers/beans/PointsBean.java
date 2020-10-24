package ru.itmo.angry_beavers.beans;

import lombok.Getter;
import ru.itmo.angry_beavers.model.PointQ;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name = "pointsBean")
@ApplicationScoped
public class PointsBean implements Serializable {
    // 7 booleans
    // y, r

    @Getter
    private PointQ currentPoint;

    @Getter
    private List<PointQ> allPoints;


    public List<PointQ> getHitResultList(){
        return null;
    }

    public void clearTable(){

    }
}
