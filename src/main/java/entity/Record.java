package entity;

public class Record {
    private String recordName;
    private String location;

    public Record(String recordName, String location) {
        this.recordName = recordName;
        this.location = location;
    }

    public String getRecordName() {
        return recordName;
    }

    public void setRecordName(String recordName) {
        this.recordName = recordName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
