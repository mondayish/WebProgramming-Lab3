package ru.itmo.angry_beavers.database;

public class UrlGetterDirectly implements UrlGetter {
    @Override
    public String getUrl() {
        return "jdbc:postgresql://pg:5432/studs";
    }
}
