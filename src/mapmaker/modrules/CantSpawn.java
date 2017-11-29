package mapmaker.modrules; 

import mapmaker.Hex;

public class CantSpawn extends ModRule {
  public static final String    DESC   = "Cannot Spawn";
  public static final String    NAME   = "CantSpawn";
  public static final boolean[] SCHEMA = {false, false};

  public CantSpawn() {
    super(CantSpawn.NAME);
  }
  
  public boolean allowed(Hex[][] map, int[] coords) {
    return false;
  }
}
