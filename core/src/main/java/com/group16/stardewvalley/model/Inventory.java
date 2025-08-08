package com.group16.stardewvalley.model;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.group16.stardewvalley.model.items.Flower;
import com.group16.stardewvalley.model.items.Item;
import com.group16.stardewvalley.model.agriculture.*;
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
    private DragAndDrop dragAndDrop = new DragAndDrop();

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
        Flower flower = new Flower("flower", 0);
        items.put(flower, 1);

    }

    public void showTools(Stage stage, Skin skin) {
      Table table = new Table();
      table.top().left().pad(10);
      table.setFillParent(true);
      for (Gadget gadget : tools.keySet()) {
          Image icon = new Image(new Texture(Gdx.files.internal(gadget.getAssetPath())));
          table.add(icon).pad(10);
      }
      stage.clear();
      stage.addActor(table);
    }

    public void showInventory(Stage stage, Skin skin) {
        // پاک کردن Stage از Actorهای قبلی (اما DragAndDrop حفظ می‌شود)
        stage.clear();

        // مقداردهی اولیه DragAndDrop اگر null باشد
        if (dragAndDrop == null) {
            dragAndDrop = new DragAndDrop();
        } else {
            dragAndDrop.clear(); // پاک کردن Sourceها و Targetهای قبلی
        }

        // ساختار اصلی جداول
        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.center();

        Table containerTable = new Table();
        containerTable.top().left();
        containerTable.setSize(320, 250);

        Table itemTable = new Table();
        itemTable.top().left().pad(5);
        itemTable.defaults().padTop(20).padLeft(10);

        int columnCount = 4;
        int index = 0;

        // ساخت آیتم‌های موجود در Inventory
        for (Item item : new ArrayList<>(items.keySet())) {
            Texture texture = new Texture(Gdx.files.internal(item.getAssetPath()));
            Image icon = new Image(texture);
            icon.setSize(64, 64);
            icon.setTouchable(Touchable.enabled);

            // افزودن آیتم به جدول
            itemTable.add(icon).width(64).height(64).pad(5);

            // تنظیم Source برای درگ کردن
            dragAndDrop.addSource(new DragAndDrop.Source(icon) {
                @Override
                public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                    DragAndDrop.Payload payload = new DragAndDrop.Payload();
                    payload.setObject(item); // ذخیره آیتم برای استفاده در drop
                    payload.setDragActor(new Image(texture)); // نمایش تصویر هنگام درگ
                    return payload;
                }
            });

            index++;
            if (index % columnCount == 0) {
                itemTable.row(); // رفتن به سطر جدید پس از هر 4 آیتم
            }
        }

        // اسکرول پن برای آیتم‌ها
        ScrollPane scrollPane = new ScrollPane(itemTable, skin);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setForceScroll(false, true);
        scrollPane.setOverscroll(false, false);

        containerTable.add(scrollPane).width(320).height(200).top().left();
        containerTable.row();

        // ساخت آیکون سطل زباله (Trash)
        Texture trashTexture = new Texture(Gdx.files.internal("assets/Inventory/Trash.png"));
        Image trashIcon = new Image(trashTexture);
        trashIcon.setSize(48, 48);

        // افزودن سطل زباله به انتهای صفحه
        containerTable.add().expandX();
        containerTable.add(trashIcon).right().pad(10).bottom();

        // تنظیم Target برای سطل زباله (حذف آیتم)
        dragAndDrop.addTarget(new DragAndDrop.Target(trashIcon) {
            @Override
            public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                return true; // همیشه اجازه دراپ بده
            }

            @Override
            public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                Item droppedItem = (Item) payload.getObject();
                items.remove(droppedItem); // حذف آیتم از لیست
                showInventory(stage, skin); // رفرش اینونتوری
            }
        });

        // اضافه کردن جداول به Stage
        mainTable.add(containerTable);
        stage.addActor(mainTable);
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
