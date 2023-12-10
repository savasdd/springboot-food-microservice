package com.food.model.geometry;

import java.sql.Date;

public class CustomPosition {
    private Date Timestamp;
    private double Latitude;
    private double Longitude;
    private double Altitude;
    private double Accuracy;
    private double AltitudeAccuracy;
    private double Heading;
    private double Speed;

    //Getter & Setter

    public Date getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(Date timestamp) {
        Timestamp = timestamp;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public double getAltitude() {
        return Altitude;
    }

    public void setAltitude(double altitude) {
        Altitude = altitude;
    }

    public double getAccuracy() {
        return Accuracy;
    }

    public void setAccuracy(double accuracy) {
        Accuracy = accuracy;
    }

    public double getAltitudeAccuracy() {
        return AltitudeAccuracy;
    }

    public void setAltitudeAccuracy(double altitudeAccuracy) {
        AltitudeAccuracy = altitudeAccuracy;
    }

    public double getHeading() {
        return Heading;
    }

    public void setHeading(double heading) {
        Heading = heading;
    }

    public double getSpeed() {
        return Speed;
    }

    public void setSpeed(double speed) {
        Speed = speed;
    }
}
