package Object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Chest extends SuperObject{
  
  public OBJ_Chest() {
    name = "Key";
    try {
      image = ImageIO.read(getClass().getResourceAsStream("/assets/Object/chest.png"));
    }
    catch(IOException e) {
      e.printStackTrace();
    }
  }
}