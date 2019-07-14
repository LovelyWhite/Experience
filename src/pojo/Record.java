package pojo;


public class Record {

  private long recordid;
  private String username;
  private String data;
  private long experienceid;
  private long times;
  private java.sql.Timestamp time;


  public long getRecordid() {
    return recordid;
  }

  public void setRecordid(long recordid) {
    this.recordid = recordid;
  }


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }


  public long getExperienceid() {
    return experienceid;
  }

  public void setExperienceid(long experienceid) {
    this.experienceid = experienceid;
  }


  public long getTimes() {
    return times;
  }

  public void setTimes(long times) {
    this.times = times;
  }


  public java.sql.Timestamp getTime() {
    return time;
  }

  public void setTime(java.sql.Timestamp time) {
    this.time = time;
  }

}
