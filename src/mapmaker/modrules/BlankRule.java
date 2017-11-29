package mapmaker.modrules; 

import mapmaker.Hex;

public class BlankRule extends ModRule {
  public static final String    DESC   = "If you're seeing this, something went wrong.";
  public static final String    NAME   = "BlankRule";
  public static final boolean[] SCHEMA = {false, false};

  public BlankRule() {
    super(BlankRule.NAME);
  }
  
  @Override
  public boolean allowed(Hex[][] map, int[] coords) {
    throw new UnsupportedOperationException("Not supported yet.");
  }
  
}
