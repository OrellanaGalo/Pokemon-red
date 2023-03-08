package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.ScreenWidth/2 - (gp.tileSize/2);
        screenY = gp.ScreenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle(8, 16, 32, 32); // Declaramos el tamano del área de colision.

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues(){
        worldX = gp.tileSize * 8;
        worldY = gp.tileSize * 14;
        speed = 2;
        direction = "right";
    }

    public void getPlayerImage(){
        try{
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/red_up_1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/red_up_2.png")));
            idle1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/red_idle_1.png")));
            idle2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/red_idle_2.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/red_down_1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/red_down_2.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/red_left_1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/red_left_2.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/red_right_1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/red_right_2.png")));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void update(){

        if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed){
            if(keyH.upPressed){
                direction = "up";
            } else if(keyH.downPressed){
                direction = "down";
            } else if(keyH.leftPressed){
                direction = "left";
            } else if(keyH.rightPressed){
                direction = "right";
            }

            // Check tile collision
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // If collsion is false, player can move
            if(!collisionOn){
                switch (direction) {
                    case "up": 
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left": 
                        worldX -= speed;
                        break;
                    case "right": 
                        worldX += speed;
                        break;
                }
            }

            spriteCounter++;

            if(spriteCounter > 12){
                if(spriteNumber == 1){
                    spriteNumber = 2;
                } else if(spriteNumber == 2){
                    spriteNumber = 1;
                }

                spriteCounter = 0;
            }
        }
    }

    public void draw(Graphics2D graphics2D){
        BufferedImage image = null;

        switch (direction) {
            case "up":
                if(spriteNumber == 1){
                    image = up1;
                }
                if(spriteNumber == 2){
                    image = up2;
                }
                break;
            case "down":
                if(spriteNumber == 1){
                    image = down1;
                }
                if(spriteNumber == 2){
                    image = down2;
                }
                break;
            case "left":
                if(spriteNumber == 1){
                    image = left1;
                }
                if(spriteNumber == 2){
                    image = left2;
                }
                break;
            case "right":
                if(spriteNumber == 1){
                    image = right1;
                }
                if(spriteNumber == 2){
                    image = right2;
                }
                break;
        }

        graphics2D.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}
