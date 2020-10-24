package ru.itmo.angry_beavers.services;

import ru.itmo.angry_beavers.model.PointQ;

public class InAreaChecker {
    public static boolean isInArea(double x, double y, double r){
        return isInRightTop(x,y,r) || isInRightBottom(x,y,r)
                || isInLeftTop(x,y,r) || isInLeftBottom(x,y,r);
    }

    // later
    public static boolean isInRightTop(double x, double y, double r){
        return false;
    }

    public static boolean isInLeftTop(double x, double y, double r){
        return false;
    }

    public static boolean isInRightBottom(double x, double y, double r){
        return false;
    }

    public static boolean isInLeftBottom(double x, double y, double r){
        return false;
    }
}
