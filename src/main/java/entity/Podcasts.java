package entity;

import java.sql.Date;
import java.sql.Time;

public class Podcasts {
    private int p_id;
    private String podcast;
    private String celebrity;
    private Date releaseDate;
    private Time duration;
    private String location;

    public Podcasts(int p_id, String podcast, String celebrity, Date releaseDate, Time duration, String location) {
        this.p_id = p_id;
        this.podcast = podcast;
        this.celebrity = celebrity;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.location = location;
    }

    public int getP_id() {
        return p_id;
    }

    public String getPodcast() {
        return podcast;
    }

    public String getCelebrity() {
        return celebrity;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public Time getDuration() {
        return duration;
    }

    public String getLocation() {
        return location;
    }

}
