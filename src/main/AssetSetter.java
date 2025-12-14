package main;

import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    // Place settings of the objects.
    public void setObject(){
        // The placement of the objects are as follows:
        // Key #1 = x:30, y:6
        // Key #2 = x:7 y:37
        // Door #1 = x:18, y:27
        // Door #2 = x:8, y:29
        // Chest #1 = x:6, y:31

       // Key #1
        gp.obj[0]= new OBJ_Key();
        gp.obj[0].worldX = 30 * gp.tileSize;
        gp.obj[0].worldY = 6 * gp.tileSize;

        // Key #2
        gp.obj[1] = new OBJ_Key();
        gp.obj[1].worldX = 7 * gp.tileSize;
        gp.obj[1].worldY = 37 * gp.tileSize;

        // Door #1
        gp.obj[2]= new OBJ_Door();
        gp.obj[2].worldX = 18 * gp.tileSize;
        gp.obj[2].worldY = 27* gp.tileSize;

        // Door #2
        gp.obj[3] = new OBJ_Door();
        gp.obj[3].worldX = 8 * gp.tileSize;
        gp.obj[3].worldY = 29 * gp.tileSize;

        // Chest #1
        gp.obj[4] = new OBJ_Chest();
        gp.obj[4].worldX = 6 * gp.tileSize;
        gp.obj[4].worldY = 31 * gp.tileSize;
    }

}
