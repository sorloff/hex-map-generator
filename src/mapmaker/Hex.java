package mapmaker;

/*
 * The basic Hex object.
 * Holds information about the hex type and references of neighbors.
*/
public class Hex {
  //0 for sea, 1 for land, 2 for edge, 3 for ???

  private int type;

  /* north     0
	 * south     1
	 * northEast 2
	 * northWest 3
	 * southEast 4
	 * southWest 5
   */
  private Hex[] adj;

  public Hex() {
    this.type = 3;
    this.adj = new Hex[6];
  }

  public Hex(Hex[] neighbors) {
    this.type = 3;
    this.adj = new Hex[6];
    System.arraycopy(neighbors, 0, adj, 0, neighbors.length);
  }

  public void setType(int newType) {
    type = newType;
  }

  public int getType() {
    return type;
  }

  public void setAdj(int pos, Hex neighbor) {
    adj[pos] = neighbor;
  }

  public void setAllAdj(Hex[] neighbors) {
    System.arraycopy(neighbors, 0, adj, 0, neighbors.length);
  }

  public Hex getAdj(int pos) {
    return adj[pos];
  }

  public Hex[] getAllAdj() {
    return adj;
  }

}
