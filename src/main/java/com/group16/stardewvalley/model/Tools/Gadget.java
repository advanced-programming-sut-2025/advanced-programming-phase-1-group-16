package com.group16.stardewvalley.model.Tools;


import com.group16.stardewvalley.model.Items.Item;
import com.group16.stardewvalley.model.map.*;

public abstract class Gadget extends Item {
   String name;
   String material;

   public Gadget(String name) {
      super(name);
   }

   public String getName() {
      return name;
   }

   public void use(int x, int y, TileType[][] map){

   };

   public void upgrade() {
// گفته توی بخش فروشگاه توضیح میده منظورش چیه ولی پیدا نکردم=)
   }
   public String getMaterial() {
      return material;
   }

   public void setMaterial(String newMaterial) {
      this.material = material;
   }

}
