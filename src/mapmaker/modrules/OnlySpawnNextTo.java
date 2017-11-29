package mapmaker.modrules; 

import mapmaker.Hex;

public class OnlySpawnNextTo extends ModRule {
  public static final String    DESC   = "Only Spawn Next To";
  public static final String    NAME   = "OnlySpawnNextTo";
  public static final boolean[] SCHEMA = {false, true};

  public OnlySpawnNextTo() {
    super(OnlySpawnNextTo.NAME);
  }
  
  @Override
  public boolean allowed(Hex[][] map, int[] coords) {
    for (Hex h : map[coords[0]][coords[1]].getAllAdj())
      if (h.getType().equals(this.other))
	return true;

    return false;
  }
}
