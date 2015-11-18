package ru.andyskvo;

/**
 * Created by Skvorcov on 17.11.2015.
 */
public class Town {
    private int id;
    private double latitude;
    private double longitude;
    private String name;

    public Town(int id, double latitude, double longitude, String country, String region, String name) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }
}
