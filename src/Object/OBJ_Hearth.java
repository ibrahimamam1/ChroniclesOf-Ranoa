package Object;

import java.io.IOException;

import javax.imageio.ImageIO;

import game.GamePanel;

public class OBJ_Hearth extends SuperObject{
  GamePanel gp;
  public OBJ_Hearth(GamePanel gp) {
    this.gp =  gp;
    name = "Key";
    try {
      image = ImageIO.read(getClass().getResourceAsStream("/assets/Object/heart_blank.png"));
      image2 = ImageIO.read(getClass().getResourceAsStream("/assets/Object/heart_half.png"));
      image3 = ImageIO.read(getClass().getResourceAsStream("/assets/Object/heart_full.png"));

      image = uTool.scaleImage(image, gp.tileSize , gp.tileSize);
      image2 = uTool.scaleImage(image2, gp.tileSize , gp.tileSize);
      image3 = uTool.scaleImage(image3, gp.tileSize , gp.tileSize);
    }
    catch(IOException e) {
      e.printStackTrace();
    }
  }
}
