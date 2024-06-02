import java.util.ArrayList;

public class openingDoor extends Entity {
  boolean openOrClosed;
  Tile door;
  ArrayList<MetalPlate> metalPlates;
  ArrayList<Rock> rocks;

  public openingDoor() {
    metalPlates.add(new MetalPlate());
    metalPlates.add(new MetalPlate());
    metalPlates.add(new MetalPlate());
    rocks.add(new Rock());
    rocks.add(new Rock());
    rocks.add(new Rock());
    openOrClosed = false;
    door.has_collision = true;
  }

  public void changeDoorState() {
    door.has_collision = !door.has_collision;
  }

}