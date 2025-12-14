package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.IOException;

public class Player extends Entity{


    KeyHandler keyH;
    public final int screenX;
    public  final int screenY;

    //  Player class constructor
    public Player(GamePanel gp, KeyHandler keyH) {

        super(gp);

        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        // Starting point of the player
        worldX = gp.tileSize * 4;
        worldY = gp.tileSize * 0;
        speed = 4;
        direction = "up";
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/player_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/player_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/player_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/player_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/player_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/player_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/player_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/player_right_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void update(){

        if(keyH.upPressed || keyH.downPressed
              || keyH.leftPressed || keyH.rightPressed) {

            if(keyH.upPressed){
                direction = "up";
            }
            else if(keyH.downPressed){
                direction = "down";
            }
            else if(keyH.leftPressed){
                direction = "left";
            }
            else   if(keyH.rightPressed){
                direction = "right";
            }

            // CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this, true);
            pickupObject(objIndex); // see method below

            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            if(!collisionOn) {
                switch (direction) {
                    case "up": worldY -= speed;  break;
                    case "down":worldY += speed;  break;
                    case "left": worldX -= speed;  break;
                    case "right": worldX += speed;  break;
                }
            }

            spriteCounter++;
            if (spriteCounter > 18) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
    }

    // PICKUP OBJECT METHOD  picks up the object when collides with it.
    public void pickupObject(int i){

        if(i != 999){
            gp.obj[i] = null;
        }

    }

    public void draw(Graphics2D g2d){
//        g2d.setColor(Color.white);
//        g2d.fillRect(x, y, gp.tileSize, gp.tileSize);

        BufferedImage image = null;

        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                } else if (spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                } else if (spriteNum == 2) {
                    image = down2;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                } else if (spriteNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                } else if (spriteNum == 2) {
                    image = right2;
                }
                break;
        }

        g2d.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null );

    }

}
