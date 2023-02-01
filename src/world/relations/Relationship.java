package world.relations;

import java.io.Serializable;

public enum Relationship implements Serializable {
    acquaintance, friend, crush, lover, sweetheart;

    public static Relationship getRelation(int n) {
        if(n < 30)
            return acquaintance;
        if (n < 60)
            return friend;
        if (n < 80)
            return crush;
        if (n >= 80)
            return lover;

        return acquaintance;
    }
}
