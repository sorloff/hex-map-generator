package mapmaker.modrules; 

import mapmaker.Hex;

//TODO: change this to "cannot spawn above", make new "cannot/only spawn between"
public class CantSpawnAbove extends ModRule {
  public static final String    DESC   = "Cannot Spawn Above";
  public static final String    NAME   = "CantSpawnAbove";
  public static final boolean[] SCHEMA = {true, false};

  public CantSpawnAbove() {
    super(CantSpawnAbove.NAME);
  }

  @Override
  public boolean allowed(Hex[][] map, int[] coords) {
    return (coords[0] <= this.val);
  }
  
  
}
