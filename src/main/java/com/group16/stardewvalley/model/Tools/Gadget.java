package com.group16.stardewvalley.model.Tools;


import com.group16.stardewvalley.model.map.*;

public abstract class Gadget {
   String name;
   String material;
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
}
