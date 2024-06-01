public class  openingDoor extends Entity{
boolean openOrClosed;
Tile door;
MetalPlate a; MetalPlate b;MetalPlate c;
Rock one; Rock two; Rock three;

public openingDoor(){
    openOrClosed = false;
    door.has_collision = true;
}
public void changeDoorState(){
    door.has_collision = !door.has_collision;
}

}