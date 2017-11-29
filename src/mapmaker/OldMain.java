package mapmaker;

//depreciated, but useful if you want to change some values by hand
public class OldMain {

  //TODO: 
  //-clean up/refactor code
  //-user input
  //-multi-hex structures with specific spawning conditions
  //	I'm picturing it generating ice on the top and bottom portions of the map
  //	only spawning settlements near water in the middle portion?
  //-generate more realistic continents
  //-resources (animal life, etc)  
  //DEPRECIATED
  public static void oldMain(String[] args) {
    int[] dims = {50, 100};
    //sea_chance, land_chance, base chance
    //calculation is: sum(neighbors_sea*sea_chance) + sum(neighbors_land*land_chance) + base_chance > 0.5
    
//    HexMap map = new HexMap(dims, 0.1, 0.1, 0.5);
//    map.makeMap();
//    map.printMap();
//    map.makeMapImage();
  }

}
