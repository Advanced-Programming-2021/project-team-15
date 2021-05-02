package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Zone {
    private static HashMap<String, ArrayList<Zone>> allZones;
    private String zoneName;
    private ZoneType zoneType ;
    static {
     allZones = new HashMap<>();
    }

    public static HashMap<String, ArrayList<Zone>> getAllZones() {
        return allZones;
    }

    public static void setAllZones(HashMap<String, ArrayList<Zone>> allZones) {
        Zone.allZones = allZones;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public ZoneType getZoneType() {
        return zoneType;
    }

    public void setZoneType(ZoneType zoneType) {
        this.zoneType = zoneType;
    }

    enum ZoneType{
         HAND,
         MONSTER_CARD,
         MAGIC_CARD,
         DECK,
         FIELD,
         GRAVEYARD
     }

}
