package mapmaker.modrules; 

import mapmaker.Hex;

public class CantSpawnBeneath extends ModRule {
  public static final String    DESC   = "Cannot Spawn Beneath";
  public static final String    NAME   = "CantSpawnBeneath";
  public static final boolean[] SCHEMA = {true, false};

  public CantSpawnBeneath() {
    super(CantSpawnBeneath.NAME);
  }

  @Override
  public boolean allowed(Hex[][] map, int[] coords) {
    return (coords[0] >= this.val);
  }
}
