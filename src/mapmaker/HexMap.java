package mapmaker;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/*
 * Handles the logic behind creating the hex map.
 */
public class HexMap {
  //top-left corner

  private int[] dim;
  private Hex[][] arrMap;
  private double sea;
  private double land;
  private double base; //over 0.5 means more land, less means more sea

  public HexMap(int[] dimensions, double sea_chance, double land_chance, double base_chance) {
    this.dim = new int[2];
    this.dim[0] = dimensions[0];
    this.dim[1] = dimensions[1];
    this.sea = sea_chance;
    this.land = land_chance;
    this.base = base_chance;

    //size increase is to allow for "border hexes" which simplifies later logic
    this.arrMap = new Hex[dim[0] + 2][dim[1] + 2];
  }

  public void makeMap() {
    for (int i = 0; i < arrMap.length; i++) {
      for (int j = 0; j < arrMap[i].length; j++) {
	arrMap[i][j] = new Hex();
      }
    }

    //setting border hexes to have border hex type
    for (Hex h : arrMap[0]) {
      h.setType(2);
    }
    for (Hex h : arrMap[arrMap.length - 1]) {
      h.setType(2);
    }
    for (Hex[] hA : arrMap) {
      hA[0].setType(2);
      hA[hA.length - 1].setType(2);
    }

    linkMap();
    populateMap();
  }

  //this links each hex object with its neighbors, so we can easily calculate
  //the probability equation later
  private void linkMap() {
    for (int i = 1; i <= dim[0]; i++) {
      for (int j = 1; j <= dim[1]; j++) {
	boolean odd = (i % 2 == 0); //note Hex class for neighbor positions
	Hex[] neighbors = {arrMap[i - 1][j],
			   arrMap[i + 1][j],
			   arrMap[odd ? i : i - 1][j + 1],
			   arrMap[odd ? i : i - 1][j - 1],
			   arrMap[odd ? i + 1 : i][j + 1],
			   arrMap[odd ? i + 1 : i][j - 1]};
	this.arrMap[i][j].setAllAdj(neighbors);
      }
    }
  }

  //calculate the type for each hex, in random order
  private void populateMap() {
    Random r = new Random();
    Hex[][] refArr = new Hex[dim[0] + 2][dim[1] + 2];

    System.arraycopy(arrMap, 0, refArr, 0, arrMap.length);
    Collections.shuffle(Arrays.asList(refArr));

    for (int i = 0; i < refArr.length; i++) {
      for (int j = 0; j < refArr[i].length; j++) {
	Hex h = refArr[i][j];
	if (h.getType() != 2) {
	  double chance = probHelper(h);
	  h.setType(chance > r.nextDouble() ? 1 : 0);
	}
      }
    }
  }

  //return value to compare r.nextDouble to
  private double probHelper(Hex currentHex) {
    Hex[] neighbors = currentHex.getAllAdj();
    double chance = base;

    for (Hex h : neighbors) {
      if (h.getType() == 0) {
	chance -= sea;
      } else if (h.getType() == 1) {
	chance += land;
      }
    }

    return chance;
  }

  //print the hex map into the console
  public void printMap() {
    for (int i = 0; i < arrMap.length; i++) {
      String s = "";
      for (int j = 0; j < arrMap[i].length; j++) {
	s += arrMap[i][j].getType();
      }
      System.out.println(s);
    }
  }

  public void makeMapImage() {
    MapDrawer m = new MapDrawer(arrMap, 50, 1);
    m.mapHexes();
    System.out.println("badda boom");

  }
}
