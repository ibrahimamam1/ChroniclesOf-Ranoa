package Object;

import entity.Entity;
import game.GamePanel;

public class OBJ_Mana_Crystal extends Entity{

  public OBJ_Mana_Crystal(GamePanel gp) {
    super(gp);
    name = "Mana Crystal";
    image = setup("/assets/Object/manacrystal_blank", gp.tileSize, gp.tileSize);
    image2 = setup("/assets/Object/manacrystal_full", gp.tileSize, gp.tileSize);
    description = "[" + name + "]\n SlightLy Restore Mana";
    direction = "down";
    type = entityType.CONSUMABLE;
    value = 2;
  }

  public void use(Entity entity) {
    entity.mana += value;
    if(entity.mana > entity.maxMana) {
      entity.mana = entity.maxMana;
    }
    gp.playSoundEffect(2);
  }
  
}
