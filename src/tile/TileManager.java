package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNumber;

    final String mapFilePath = "/maps/world_map_01c.txt";
    // final String mapFilePath = "/maps/map_01.txt";
    final String lawnFilePath = "/bg/lawn_02.png";
    final String wallFilePath = "/bg/Brown_wall_tile_center.png";
    final String waterFilePath = "/bg/water.png";
    final String sandFilePath = "/bg/sand.png";
    final String treeFilePath = "/bg/tree_02.png";
    final String soilFilePath = "/bg/soil.png";

    // Tile Manager Constructor
    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[10];
        mapTileNumber = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap(mapFilePath);  // send the map into the map generator
    }

    // Define tile images method
    public void getTileImage() {
        try {

            //  The world map chart numbers correspond as follows:
            // #0 = lawn/grass
            // #1 = water
            // #2 = wall tiles
            // #3 = pathway / sand
            // #4 = trees
            // #5 = soil / solid ground

            // lawn tile #0
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(lawnFilePath)));
            // water tile #1
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(waterFilePath)));
            tile[1].collision = true;
            // wall tile #2
            tile[2] = new Tile();
            tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(wallFilePath)));
            tile[2].collision = true;
            // sand #3
            tile[3] = new Tile();
            tile[3].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(sandFilePath)));
            // tree #4
            tile[4] = new Tile();
            tile[4].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(treeFilePath)));
            tile[4].collision = true;
            // soil #5
            tile[5] = new Tile();
            tile[5].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(soilFilePath)));

            // System.out.println("Image Loaded at finished");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load map method
    public void loadMap(String mapFilePath) {
        try {

            InputStream in = getClass().getResourceAsStream(mapFilePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {

                String line = br.readLine();

                while (col < gp.maxWorldCol) {
                    String numbers[] = line.split(" "); // Make sure that spaces are used in the text file.
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNumber[col][row] = num;
                    col++;
                }

                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//      Suggested code from AI .  Found that it was the text file was using tabs instead of spaces when exporting in excel.

//        try {
//            InputStream in = getClass().getResourceAsStream(mapFilePath);
//            BufferedReader br = new BufferedReader(new InputStreamReader(in));
//
//            int col = 0;
//            int row = 0;
//
//            // Loop until all rows are processed
//            while (row < gp.maxWorldRow) {
//
//                // Read the next full line for this row
//                String line = br.readLine();
//
//                if (line == null) {
//                    // Handle case where file ends unexpectedly (optional but good practice)
//                    System.err.println("Error reading map file: End of file reached before all rows were processed.");
//                    break;
//                }
//
//                // Loop through all columns using the single line we just read
//                while (col < gp.maxWorldCol) {
//                    //String[] numbers = line.split("[\\s\\t]+");
//                    String[] numbers = line.split(" ");
//                    // CRITICAL FIX: Ensure 'numbers' array has enough elements for the current 'col' index
//                    if (col < numbers.length) {
//                        try {
//                            // This line was likely throwing the NumberFormatException
//                            int num = Integer.parseInt(numbers[col]);
//                            mapTileNumber[col][row] = num;
//                        } catch (NumberFormatException e) {
//                            System.err.println("Error parsing number at Col " + col + ", Row " + row + ": " + numbers[col]);
//                            // Handle the error, perhaps assign a default tile ID
//                            mapTileNumber[col][row] = 0;
//                        }
//                    } else {
//                        System.err.println("Error: Not enough numbers in the line for Col " + col + " Row " + row);
//                    }
//
//                    col++;
//                }
//
//                // Reset column counter and move to the next row
//                col = 0;
//                row++;
//            }
//
//            br.close(); // Important to close the reader
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    // Draw map method
    public void draw(Graphics g2D) {

        // Iterate through every single ROW of the map data array
        for (int worldRow = 0; worldRow < gp.maxWorldRow; worldRow++) {

            // Iterate through every single COLUMN of the map data array
            for (int worldCol = 0; worldCol < gp.maxWorldCol; worldCol++) {

                int tileNum = mapTileNumber[worldCol][worldRow];

                // Calculate the actual position in the game world
                int worldX = worldCol * gp.tileSize;
                int worldY = worldRow * gp.tileSize;

                // Calculate where this world position sits on the user's screen (relative to player position)
                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;

                //Limit the images drawn to the screen size plus one tile of the map
                if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                      worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                      worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                      worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                    // Draw the image at the calculated screen coordinates
                    g2D.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
                }
            }
        }
    }
}  // END OF CLASS


//      Original code of the DRAW METHOD from video.  Apparently not returning all the tiles because of While loop instead
//      of for loop as above.

//        int worldCol = 0;
//        int worldRow = 0;
//
//        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow){
//
//            int tileNum = mapTileNumber[worldCol][worldRow];
//
//            int worldX = worldCol * gp.tileSize;
//            int worldY = worldRow * gp.tileSize;
//            int screenX = worldX - gp.player.worldX + gp.player.screenX;
//            int screenY = worldY - gp.player.worldY + gp.player.screenY;
//
//            g2D.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
//            worldCol++;
//
//            if(worldCol == gp.maxScreenCol){
//                worldCol = 0;
//                worldRow++;
//            }
//
//        }
//
//    }



