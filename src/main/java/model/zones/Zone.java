package model.zones;

import java.util.ArrayList;
import java.util.HashMap;

public class Zone {
    private static HashMap<String, ArrayList<Zone>> allZones;

    static {
        allZones = new HashMap<>();
    }

    private ZoneType zoneType;

    public Zone(ZoneType zoneType) {
        this.zoneType = zoneType;
    }

    public static HashMap<String, ArrayList<Zone>> getAllZones() {
        return allZones;
    }

    public static void setAllZones(HashMap<String, ArrayList<Zone>> allZones) {
        Zone.allZones = allZones;
    }

    public ZoneType getZoneType() {
        return zoneType;
    }

    public void setZoneType(ZoneType zoneType) {
        this.zoneType = zoneType;
    }

    public enum ZoneType {
        HAND,
        MONSTER_CARD,
        MAGIC_CARD,
        DECK,
        FIELD,
        GRAVEYARD
    }

}