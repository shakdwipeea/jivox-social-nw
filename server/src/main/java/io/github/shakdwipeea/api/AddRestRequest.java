package io.github.shakdwipeea.api;

/**
 * Created by akash on 6/20/16.
 */
public class AddRestRequest {
    private String name;
    private String latitude;
    private String longitude;

    private String from;
    private String fromDistance;

    private String to;
    private String toDistance;

    public AddRestRequest() {
    }

    public String getName() {
        return name;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getFrom() {
        return from;
    }

    public String getFromDistance() {
        return fromDistance;
    }

    public String getTo() {
        return to;
    }

    public String getToDistance() {
        return toDistance;
    }
}
