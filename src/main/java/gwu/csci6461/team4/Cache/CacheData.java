package gwu.csci6461.team4.Cache;

public class CacheData {
    private int address;
    private int value;

    public CacheData(int address,int value) {
        this.address = address;
        this.value = value;
    }

    public int getAddress() {
        return address;
    }
    public void setAddress(int address) {
        this.address = address;
    }
    public int getData() {
        return value;
    }
    public void setValue(short value) {
        this.value = value;
    }
}
