package model;

public class Reply {
    public Reply(String type, String data) {
        this.type = type;
        this.data = data;
    }
    String type;
    String data;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "type:"+type+"data:"+data;
    }
}
