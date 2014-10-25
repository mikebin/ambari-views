package view;

import java.util.Date;

public class SnapshotInfo {
  private String owner;
  private String path;
  private Date date;

  public SnapshotInfo() {
  }

  public SnapshotInfo(String owner, String path, Date date) {
    this.owner = owner;
    this.path = path;
    this.date = date;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getOwner() {
    return owner;
  }

  public String getPath() {
    return path;
  }
}
