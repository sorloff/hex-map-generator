package mapmaker.modrules; 

import mapmaker.Hex;

public class CantSpawnNextTo extends ModRule{
  public static final String    DESC   = "Cannot Spawn Next To";
  public static final String    NAME   = "CantSpawnNextTo";
  public static final boolean[] SCHEMA = {false, true};

  public CantSpawnNextTo() {
    super(CantSpawnNextTo.NAME);
  }

  @Override
  public boolean allowed(Hex[][] map, int[] coords) {
    for (Hex h : map[coords[0]][coords[1]].getAllAdj())
      if (h.getType().equals(this.other))
	return false;

    return true;
  }
  
  
}
