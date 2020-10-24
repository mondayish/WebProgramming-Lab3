package ru.itmo.angry_beavers.database;

import ru.itmo.angry_beavers.model.PointQ;

import java.util.List;

public interface PointsRepository {
    void addPoint(PointQ pointQ);
    void removePoint(PointQ pointQ);
    void updatePoint(PointQ pointQ);

    List<PointQ> getAllPoints();
}
