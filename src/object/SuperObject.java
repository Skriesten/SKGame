package object;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {

    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX,  worldY;
    public Rectangle solidArea = new Rectangle(0,0,48,48);
    public  int solidAreaDefaultX = 0;
    public int  solidAreaDefaultY = 0;

    public void draw(Graphics2D g2D, GamePanel gp){

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        //Limit the images drawn to the screen size plus one tile of the map
        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
              worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
              worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
              worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
            // Draw the image at the calculated screen coordinates
            g2D.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }

    }





}   // END OF CLASS
