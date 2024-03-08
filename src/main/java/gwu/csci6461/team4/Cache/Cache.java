package gwu.csci6461.team4.Cache;

import java.util.Vector;

public class Cache {

    private Vector<CacheData> LCache;

    public Cache() {
        LCache = new Vector<CacheData>(16);
    }

    public void addElement(int addr, int value) {
        CacheData newData = new CacheData(addr, value);
        LCache.add(0,newData);
        LCache.setSize(16);
    }

    public void clear() {
        LCache.clear();
        LCache.setSize(16);
    }

    public int getElement(int addr) {
        try { //First run will have NullPoint exception error, so use try catch to avoid.
            for (CacheData cacheData : LCache) {
                if (cacheData.getAddress() == addr) {
                    System.out.println("Cache hit!");
                    return cacheData.getData();
                }
            }
            System.out.println("Cache miss!");
            return 0;
        }catch(Exception e){
            System.out.println("Cache miss!");
            return 0;
        }

    }
}
