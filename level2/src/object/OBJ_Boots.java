package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Boots extends SuperObject {

    public OBJ_Boots() {
        name = "Boots"; 
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/boot.png")); // Corrected assignment syntax
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}