package com.group16.stardewvalley.model;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.group16.stardewvalley.model.items.Item;
import com.group16.stardewvalley.model.agriculture.*;
import com.group16.stardewvalley.model.agriculture.Seed;
import com.group16.stardewvalley.model.items.Wood;
import com.group16.stardewvalley.model.tools.Axe;
import com.group16.stardewvalley.model.tools.Gadget;
import com.group16.stardewvalley.model.crafting.CraftingRecipes;
import com.group16.stardewvalley.model.tools.FishingPole;
import com.group16.stardewvalley.model.user.BackPackType;
import com.group16.stardewvalley.model.food.Food;
import com.group16.stardewvalley.model.food.FoodIngredient;
import com.group16.stardewvalley.model.food.Ingredient;
import com.group16.stardewvalley.model.agriculture.Crop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Inventory {
    private Map<Gadget, Integer> tools;
    private Map<Item, Integer> items;
    private int capacity;
    private ArrayList<CraftingRecipes> craftingRecipes;
    private Map<Crop, Integer> crops;
    private BackPackType backPackType;

    public Inventory() {
        this.tools = new HashMap<>();
        this.items = new HashMap<>();
        this.crops = new HashMap<>();
        this.backPackType = BackPackType.Base_Pack;
        this.craftingRecipes = new ArrayList<>(
                List.of(CraftingRecipes.CherryBomb, CraftingRecipes.Sprinkler, CraftingRecipes.CharcoalKlin, CraftingRecipes.Furnace,
                        CraftingRecipes.Scarecrow, CraftingRecipes.BeeHouse, CraftingRecipes.MayonnaiseMachine));
        Axe newAse = new Axe("axe", 0, "base");
        tools.put(newAse, 1);
        items.put(newAse, 1);

    }

    public void showTools(Stage stage, Skin skin) {
      Table table = new Table();
      table.top().left().pad(10);
      table.setFillParent(true);
      for (Gadget gadget : tools.keySet()) {
          String assetName = gadget.getName();
          Image icon = new Image(new Texture(Gdx.files.internal("tools/axe/base_axe.png")));
          table.add(icon).pad(10);
      }
      stage.clear();
      stage.addActor(table);
    }

    public void showItems(Stage stage, Skin skin) {
        stage.clear();
        Texture inventoryTexture = new Texture(Gdx.files.internal("Inventory/Inventory_Parts.png"));
        TextureRegion emptySlotRegion = new TextureRegion(inventoryTexture, 0, 0, 64, 64);
        TextureRegionDrawable emptySlotDrawable = new TextureRegionDrawable(emptySlotRegion);
        Table inventoryTable = new Table();
        inventoryTable.top().left().pad(10);
        int columns = 5;
        int i = 0;

        for (Item item : items.keySet()) {
            Stack slot = new Stack();
            Image slotBackground = new Image(emptySlotDrawable);

            Image itemIcon;
            if (item instanceof Gadget) {
                Gadget gadget = (Gadget) item;
                itemIcon = new Image(new Texture(Gdx.files.internal(gadget.getAssetPath())));
            } else {
                String assetPath = "items/" + item.getName() + ".png";
                itemIcon = new Image(new Texture(Gdx.files.internal(assetPath)));
            }

            itemIcon.setScaling(Scaling.fit);
            itemIcon.setSize(48, 48);
            slot.add(slotBackground);
            slot.add(itemIcon);
            inventoryTable.add(slot).size(64, 64).pad(5);
            i++;
            if (i % columns == 0) {
                inventoryTable.row();
            }
        }

        ScrollPane scrollPane = new ScrollPane(inventoryTable);
        scrollPane.setScrollingDisabled(true, false);
        Table rootTable = new Table();
        rootTable.setFillParent(true);
        rootTable.add(scrollPane).expand().fill().pad(10);
        stage.addActor(rootTable);

    }

    public Map<Crop, Integer> getCrops() {
        return crops;
    }

    public void setCrops(Map<Crop, Integer> crops) {
        this.crops = crops;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setTools(Map<Gadget, Integer> tools) {
        this.tools = tools;
    }

    public void setBackPackType(BackPackType backPackType) {
        this.backPackType = backPackType;
    }

    public Map<Item, Integer> getItems() {
        return items;
    }

    public void setItems(Map<Item, Integer> items) {
        this.items = items;
    }

    public Result addTool(Gadget gadget, int count) {
        if (isFull()) {
            return new Result(false, "Oops! Your backpack is completely full ");
        }
        //
        String name = gadget.getName();
        tools.put(gadget, tools.getOrDefault(gadget, 0) + count);
        return new Result(true, gadget.getName() + " added to inventory successfully");
    }

    public Result addItem(Item item, int count) {
        if (isFull()) {
            return new Result(false, "Oops! Your backpack is completely full ");
        }
        String name = item.getName();
        items.put(item, items.getOrDefault(item, 0) + count);
        return new Result(true, item.getName() + " added to inventory successfully");
    }

    public ArrayList<CraftingRecipes> getCraftingRecipes() {
        return craftingRecipes;
    }

    public void setCraftingRecipes(ArrayList<CraftingRecipes> craftingRecipes) {
        this.craftingRecipes = craftingRecipes;
    }

    public void addCraftingRecipes(CraftingRecipes craftingRecipes) {
        this.craftingRecipes.add(craftingRecipes);
    }


    public BackPackType getBackPackType() {
        return backPackType;
    }
    public void addCrop(Crop crop, int count) {
        crops.put(crop, crops.getOrDefault(crop, 0));
    }


    public Gadget findToolByName(String name) {
        for (Map.Entry<Gadget, Integer> entry : tools.entrySet()) {
            Gadget gadget = entry.getKey();
            if (gadget.getName().equalsIgnoreCase(name)) {
                return gadget;
            }
        }
        return null;
    }

    public int getNumberOfItem(Item item) {
        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            if (entry.getKey().equals(item)) {
                return entry.getValue();
            }
        }
        return 0;
    }

    public Map<Gadget, Integer> getTools() {
        return tools;
    }

    public Result removeItem(Item item, int count) {
        if (!items.containsKey(item)) {
            return new Result(false, "You don't have it");
        }

        int currentCount = items.get(item);
        if (currentCount < count) {
            return new Result(false, "Not enough " + item.getName() + " in inventory! (Available: " + currentCount + ")");
        }

        int newCount = currentCount - count;
        if (newCount > 0) {
            items.put(item, newCount);
        } else {
            items.remove(item);
        }

        return new Result(true, count + " " + item.getName() + "(s) removed from inventory");
    }


    public boolean isFull() {
        return getTotalItemsCount() >= backPackType.getCapacity();
    }

    private int getTotalItemsCount() {
        int toolCount = tools.values().stream().mapToInt(Integer::intValue).sum();
        int itemCount = items.values().stream().mapToInt(Integer::intValue).sum();
        return toolCount+ itemCount;
    }

    public boolean isSeedInInventory(SeedType seedType) {
        for (Item item : items.keySet()) {
            if (item instanceof Seed seed) {
                if (seed.getType().equals(seedType) && items.get(item) > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public Item getItemByName(String name) {
        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            if (entry.getKey().getName().equalsIgnoreCase(name)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public Seed findSeedByType(SeedType seedType) {
        for (Item item : items.keySet()) {
            if (item instanceof Seed seed) {
                if (seed.getType().equals(seedType) && items.get(item) > 0) {
                    return seed;
                }
            }
        }
        return null;
    }

    public Food getFood(String foodName) {
        for (Item item : items.keySet()) {
            if (item instanceof Food food) {
                if (food.getName().equalsIgnoreCase(foodName) && items.get(item) > 0) {
                    return food;
                }
            }
        }
        return null;
    }

    public Fertilizer getFertilizer(String name) {
        for (Item item : items.keySet()) {
            if (item instanceof Fertilizer fertilizer) {
                if (fertilizer.getName().equalsIgnoreCase(name) && items.get(item) > 0) {
                    return fertilizer;
                }
            }
        }
        return null;
    }

    public FoodIngredient getFoodIngredient(Ingredient ingredient) {
        for (Item item : items.keySet()) {
            if (item instanceof FoodIngredient foodIngredient) {
                if (foodIngredient.getType().equals(ingredient) && items.get(item) > 0) {
                    return foodIngredient;
                }
            }
        }
        return null;
    }

    public FishingPole getFishingPole(String name) {
        for (Gadget gadget : tools.keySet()) {
            if (gadget instanceof FishingPole fishingPole) {
                if (fishingPole.getName().equalsIgnoreCase(name) && tools.get(gadget) > 0) {
                    return fishingPole;
                }
            }
        }
        return null;
    }

    public Wood findWood(String woodName) {
        for (Item item : items.keySet()) {
            if (item instanceof Wood wood) {
                if (wood.getName().equalsIgnoreCase(woodName) && items.get(item) > 0) {
                    return wood;
                }
            }
        }
        return null;
    }

    public int countWood() {
        int count = 0;
        for (Item item : items.keySet()) {
            if (item instanceof Wood) {
                count++;
            }
        }
        return count;
    }
}
