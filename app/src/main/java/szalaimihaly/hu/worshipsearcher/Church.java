package szalaimihaly.hu.worshipsearcher;

import android.location.Location;

import java.text.Normalizer;

/**
 * Created by Mihaly on 2015.04.24..
 */
public class Church implements Comparable<Church> {
    private int churchid;
    private int conid;
    private String city;
    private String address;
    private double latitude;
    private double longitude;
    private String comment;
    private float distance;

    public int getConid() {
        return conid;
    }

    public float getDistance() {
        return distance;
    }

    public String getDistanceString() {
        float distanceKm = distance / 1000;
        String distanceString = "";
        if (distance == 0) {
            return distanceString = "";
        }
        if (distanceKm < 10) {
            return distanceString = ((Float) distanceKm).toString().substring(0, 3) + " km";
        }
        if (distanceKm < 100) {
            return distanceString = ((Float) distanceKm).toString().substring(0, 4) + " km";
        }
        if (distanceKm < 1000) {
            return distanceString = ((Float) distanceKm).toString().substring(0, 5) + " km";
        }
        return distanceString;
    }

    public void setDistance(Location location) {
        Location churchLocation = new Location("");
        churchLocation.setLongitude(this.longitude);
        churchLocation.setLatitude(this.latitude);
        if (location.getLatitude() == 0 || location.getLongitude() == 0) {
            this.distance = 0;
        } else {
            this.distance = location.distanceTo(churchLocation);
        }
    }

    public void setChurchid(int churchid) {
        this.churchid = churchid;
    }

    public Church(int churchid, int conid, String city, String address,
                  double latitude, double longitude, String comment, long distance) {
        this.churchid = churchid;
        this.conid = conid;
        this.city = city;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.comment = comment;
        this.distance = distance;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Church(int churchid, int conid, String city, String address,
                  double latitude, double longitude, String comment) {
        this.churchid = churchid;
        this.conid = conid;
        this.city = city;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.comment = comment;
    }

    public int getChurchid() {
        return churchid;
    }


    @Override
    public String toString() {
        return "Church [churchid=" + churchid + ", conid=" + conid
                + ", city=" + city + ", address=" + address + ", longitude="
                + longitude + ", latitude=" + latitude + ", comment=" + comment + ", distance="
                + distance + "]";
    }


    @Override
    public int compareTo(Church another) {
        if (this.distance == 0 || this.distance == another.distance) {
            return Normalizer.normalize(this.city, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").compareTo(Normalizer.normalize(another.city, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""));
        } else {
            if (this.distance < another.distance) {
                return -1;
            } else {
                return 1;
            }
        }
    }
}
