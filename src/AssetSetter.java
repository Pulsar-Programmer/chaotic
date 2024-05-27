public class AssetSetter {
    GamePanel gp;
    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }
    public void setObject(){
        gp.objectManager.objects.add(Object.key(32*5, 32*12));
        gp.objectManager.objects.add(Object.door(32*10, 32*10));
    }
}
