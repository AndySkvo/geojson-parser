package ru.andyskvo;

public class Town {
    private long id;
    private double latitude;
    private double longitude;
    private String nameRu;
    private String nameEn;
    private String nameUk;
    private String nameBe;


    public Town(long id, double latitude, double longitude, String nameRu, String nameEn, String nameUk, String nameBe) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.nameRu = nameRu;
        this.nameEn = nameEn;
        this.nameUk = nameUk;
        this.nameBe = nameBe;
    }

    public long getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getNameRu() {
        return nameRu;
    }

    public String getNameEn() {
        return nameEn;
    }

    public String getNameUk() {
        return nameUk;
    }

    public String getNameBe() {
        return nameBe;
    }
}
