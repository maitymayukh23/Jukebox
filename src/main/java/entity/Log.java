package entity;

public class Log {
    private int logId;
    private Tracks tracks;
    private Podcasts pod;


    public Log(int logId, Tracks tracks) {
        this.logId = logId;
        this.tracks = tracks;
    }

    public Log(int logId, Podcasts pod) {
        this.logId = logId;
        this.pod = pod;
    }

    public int getLogId() {
        return logId;
    }

    public Tracks getTracks() {
        return tracks;
    }

    public Podcasts getPod() {
        return pod;
    }


}
