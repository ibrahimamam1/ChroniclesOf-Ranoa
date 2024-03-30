package Object;

import java.io.IOException;

import javax.imageio.ImageIO;

import game.GamePanel;

public class OBJ_Boots extends SuperObject{
  GamePanel gp;
  public OBJ_Boots(GamePanel gp) {
    this.gp = gp;
    name = "Boots";
    try {
      image = ImageIO.read(getClass().getResourceAsStream("/assets/Object/boots.png"));
      image = uTool.scaleImage(image, gp.tileSize , gp.tileSize);
    }
    catch(IOException e) {
      e.printStackTrace();
    }
  }
}
