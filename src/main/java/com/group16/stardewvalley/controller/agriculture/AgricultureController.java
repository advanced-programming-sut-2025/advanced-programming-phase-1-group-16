package com.group16.stardewvalley.controller.agriculture;

import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.agriculture.*;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.items.Seed;
import com.group16.stardewvalley.model.map.Pos;
import com.group16.stardewvalley.model.map.Tile;
import com.group16.stardewvalley.model.map.TileType;
import com.group16.stardewvalley.model.time.Season;
import com.group16.stardewvalley.model.tools.WateringCan;
import com.group16.stardewvalley.model.user.Player;

import java.util.Random;

import static com.group16.stardewvalley.model.map.Direction.getDirectionOffset;


public class AgricultureController {
    private final Random random = new Random();

    public Result craftInfo(String name) {
        CropType crop = findCropTypeByName(name);
        if (crop == null) {
            return new Result(false, "Crop not found");
        }
        StringBuilder result = new StringBuilder();
        if (crop.isForaging()) {
            result.append("Foraging\n")
                    .append("Season: ").append(crop.getSeason()).append("\n")
                    .append("Base sell price: ").append(crop.getBaseSellPrice()).append("\n")
                    .append("Energy: ").append(crop.getEnergy()).append("\n");
        }
        else {
            result.append("Name: ").append(crop.getName())
                    .append("\nSource: ").append(crop.getSource().getName())
                    .append("\nStages: ");
            for (int stage : crop.getStages()) {
                result.append(stage).append("-");
            }
            result.deleteCharAt(result.length() - 1);
            result.append("\nTotal Harvest Time: ").append(crop.getHarvestTime())
                    .append("\nOne Time: ").append(crop.isOneTime())
                    .append("\nRegrowth Time: ");
            if (crop.getRegrowthTime() != -1) {
                result.append(crop.getRegrowthTime());
            }
            result.append("\nBase Sell Price: ").append(crop.getBaseSellPrice())
                    .append("\nIs Edible: ").append(crop.isEdible())
                    .append("\nBase Energy: ").append(crop.getEnergy())
                    .append("\nBase Health: ").append(crop.getBaseHealth())
                    .append("\nSeason: ").append(crop.getSeason())
                    .append("\nCan Become Giant: ").append(crop.canBecomeGiant());
        }

        return new Result(true, result.toString());
    }

    public Result treeInfo(String name) {
        TreeType tree = findTreeTypeByName(name);
        if (tree == null) {
            return new Result(false, "Tree not found");
        }
        StringBuilder result = new StringBuilder();
        result.append("Name: ").append(tree.getName())
                .append("\nSource:").append(tree.getSapling().getName())
                .append("\nStages:");
        for (int stage : tree.getGrowthStages()) {
            result.append(stage).append("-");
        }
        result.deleteCharAt(result.length() - 1);
        result.append("\nTotal Harvest Time: ").append(tree.getTotalGrowthTime())
                .append("\nOne Time: ").append("no")
                .append("\nRegrowth Time: ").append(tree.getFruitCycleDays())
                .append("\nBase Sell Price: ").append(tree.getFruitSellPrice())
                .append("\nIs Edible: ").append(tree.isEdible())
                .append("\nBase Energy: ").append(tree.getEnergy())
                .append("\nBase Health: ").append(tree.getHealth())
                .append("\nSeason: ").append(tree.getSeason())
                .append("\nCan Become Giant: ").append("no");
        return new Result(true, result.toString());
    }

    public Result planting(String seedName, String direction) {
        SeedType seedType = findSeedTypeByName(seedName);
        if (seedType == null) {
            return new Result(false, "Seed not found");
        }
        Pos offset = getDirectionOffset(direction);
        if (offset == null) {
            return new Result(false, "Invalid direction");
        }
        int dirX = offset.getX();
        int dirY = offset.getY();
        Pos playerPos = App.getActiveGame().getCurrentPlayer().getPosition();
        if (playerPos.getX() + dirX < 0 || playerPos.getY() + dirY < 0 || playerPos.getX() + dirX > App.getActiveGame().getMapWidth() || playerPos.getY() + dirY > App.getActiveGame().getMapHeight()) {
            return new Result(false, "Invalid direction");
        }
        int x = playerPos.getX() + dirX;
        int y = playerPos.getY() + dirY;
        Tile targetTile = App.getActiveGame().getMap()[y][x];
        if (!targetTile.getType().equals(TileType.Plowed)) {
            return new Result(false, "Shokhm nazadi dadash!");
        }
        if (!App.getActiveGame().getCurrentPlayer().getInventory().isSeedInInventory(seedType)) {
            return new Result(false, "You dont have this seed!");
        }
        if (seedType.equals(SeedType.MIXED_SEED)){
            seedType = getRandomSeed(App.getActiveGame().getTimeDate().getSeason());
        }
        if (targetTile.getType().equals(TileType.GreenHouse) && App.getActiveGame().getCurrentPlayer().getFarm().getGreenhouse() == null) {
            return new Result(false, "Greenhouse is inactive");
        }
        if (!seedType.getAvailableSeasons().contains(App.getActiveGame().getTimeDate().getCurrentSeason()) &&
                !targetTile.getType().equals(TileType.GreenHouse)) {
            return new Result(false, "This seed is not available in this season");
        }
        switch (seedType.getType()) {
            case "TREE":
                TreeType treeType = findTreeTypeBySeed(seedType);
                if (treeType == null) {
                    return new Result(false, "Tree type not found");
                }
                Tree tree = new Tree(treeType);
                if (targetTile.isFertilized()) {
                    tree.setFertilized(true);
                }
                targetTile.setTree(tree);
                if (targetTile.getType().equals(TileType.GreenHouse)) {
                    App.getActiveGame().getCurrentPlayer().getFarm().getGreenhouse().addGreenHouseTree(tree);
                } else App.getActiveGame().getCurrentPlayer().getFarm().addPlantedTree(tree);
                App.getActiveGame().getCurrentPlayer().getFarm().addPlantedTree(tree);
                Seed seed3 = App.getActiveGame().getCurrentPlayer().getInventory().findSeedByType(seedType);
                App.getActiveGame().getCurrentPlayer().getInventory().getItems().put(seed3, -1);
                break;
            case "CROP":
                CropType cropType = findCropTypeBySeed(seedType);
                if (cropType == null) {
                    return new Result(false, "Crop type not found");
                }
                Crop crop = new Crop(cropType);
                if (targetTile.isFertilized()) {
                    crop.setFertilized(true);
                }
                if (cropType.isCanBecomeGiant() && !targetTile.getType().equals(TileType.GreenHouse)) {
                    boolean sameTypeAround = isSameTypeAround(x, y, cropType);

                    if (sameTypeAround) {
                        crop.setColossal(true);
                    }
                }
                targetTile.setCrop(crop);
                crop.setPosition(new Pos(x, y));
                if (targetTile.getType().equals(TileType.GreenHouse)) {
                    App.getActiveGame().getCurrentPlayer().getFarm().getGreenhouse().addGreenHouseCrop(crop);
                } else App.getActiveGame().getCurrentPlayer().getFarm().addPlantedCrop(crop);
                Seed seed4 = App.getActiveGame().getCurrentPlayer().getInventory().findSeedByType(seedType);
                App.getActiveGame().getCurrentPlayer().getInventory().getItems().put(seed4, -1);
                break;
            default:
                return new Result(false, "Invalid seed");
        }
        return new Result(true, seedName + " is planted successfully");
    }

    private static boolean isSameTypeAround(int x, int y, CropType cropType) {
        boolean sameTypeAround = true;
        Tile[][] map = App.getActiveGame().getMap();
        // فرض بر این است که Tile مختصات دارد

        int height = App.getActiveGame().getMapHeight();
        int width = App.getActiveGame().getMapWidth();

// بالا
        if (x > 0) {
            Crop upCrop = map[x - 1][y].getCrop();
            if (upCrop == null || !upCrop.getCropType().equals(cropType)) {
                sameTypeAround = false;
            }
        } else sameTypeAround = false;

// پایین
        if (x < height - 1) {
            Crop downCrop = map[x + 1][y].getCrop();
            if (downCrop == null || !downCrop.getCropType().equals(cropType)) {
                sameTypeAround = false;
            }
        } else sameTypeAround = false;

// چپ
        if (y > 0) {
            Crop leftCrop = map[x][y - 1].getCrop();
            if (leftCrop == null || !leftCrop.getCropType().equals(cropType)) {
                sameTypeAround = false;
            }
        } else sameTypeAround = false;

// راست
        if (y < width - 1) {
            Crop rightCrop = map[x][y + 1].getCrop();
            if (rightCrop == null || !rightCrop.getCropType().equals(cropType)) {
                sameTypeAround = false;
            }
        } else sameTypeAround = false;
        return sameTypeAround;
    }

    public SeedType getRandomSeed(Season season) {
        SeedType[] seeds;
        switch (season) {
            case Spring:
                 seeds = new SeedType[]{
                         SeedType.CAULIFLOWER_SEEDS,
                         SeedType.PARSNIP_SEEDS,
                         SeedType.POTATO_SEEDS,
                         SeedType.JAZZ_SEEDS,
                         SeedType.TULIP_BULB
                 };
                break;
            case Summer:
                seeds = new SeedType[]{
                        SeedType.CORN_SEEDS,
                        SeedType.PEPPER_SEEDS,
                        SeedType.RADISH_SEEDS,
                        SeedType.WHEAT_SEEDS,
                        SeedType.POPPY_SEEDS,
                        SeedType.SUNFLOWER_SEEDS,
                        SeedType.SPANGLE_SEEDS
                };
                break;
            case Fall:
                seeds = new SeedType[]{
                        SeedType.ARTICHOKE_SEEDS,
                        SeedType.EGGPLANT_SEEDS,
                        SeedType.PUMPKIN_SEEDS,
                        SeedType.SUNFLOWER_SEEDS,
                        SeedType.FAIRY_SEEDS
                };
                break;
            case Winter:
                seeds = new SeedType[]{
                        SeedType.POWDERMELON_SEEDS
                };
                break;
            default:
                return null;

        }
        return seeds[random.nextInt(seeds.length)];
    }


    public Result showPlant(int x, int y) {
        if (x < 0 || y < 0 || x >= App.getActiveGame().getMapWidth() || y >= App.getActiveGame().getMapHeight()) {
            return new Result(false, "Invalid x or y coordinates.");
        }

        Tile tile = App.getActiveGame().getMap()[y][x];

        if (tile.getTree() == null && tile.getCrop() == null) {
            return new Result(false, "No plant is planted at this location.");
        }

        StringBuilder result = new StringBuilder();

        if (tile.getTree() != null) {
            Tree tree = tile.getTree();
            TreeType type = tree.getTreeType();

            int remainingToHarvest = Math.max(type.getTotalGrowthTime() - tree.getDayPastFromPlanting(), 0);

            result.append("Plant Type: Tree\n")
                    .append("Name: ").append(type.getName()).append("\n")
                    .append("Current Stage: ").append(tree.getStage()).append("\n")
                    .append("Days Remaining to Harvest: ").append(remainingToHarvest).append("\n")
                    .append("Irrigated Today: ").append(tree.isWatered() ? "Yes" : "No").append("\n")
                    .append("Is Fertilized: ").append(tree.isFertilized() ? "Yes" : "No").append("\n")
                    .append("Fruit Sell Price: ").append(type.getFruitSellPrice()).append("\n")
                    .append("Is Edible: ").append(type.isEdible() ? "Yes" : "No").append("\n")
                    .append("Energy: ").append(type.getEnergy()).append("\n")
                    .append("Health: ").append(type.getHealth()).append("\n");
        }

        if (tile.getCrop() != null) {
            Crop crop = tile.getCrop();
            CropType type = crop.getCropType();

            int remainingToHarvest = Math.max(type.getHarvestTime() - crop.getDaysSincePlanting(), 0);

            result.append("Plant Type: Crop\n")
                    .append("Name: ").append(type.getName()).append("\n")
                    .append("Current Stage: ").append(crop.getStage()).append(" out of ").append(crop.getFinalStage()).append("\n")
                    .append("Days Remaining to Harvest: ").append(remainingToHarvest).append("\n")
                    .append("Irrigated Today: ").append(crop.isWatered() ? "Yes" : "No").append("\n")
                    .append("Is Fertilized: ").append(crop.isFertilized() ? "Yes" : "No").append("\n")
                    .append("Base Sell Price: ").append(type.getBaseSellPrice()).append("\n")
                    .append("Is Edible: ").append(type.isEdible() ? "Yes" : "No").append("\n")
                    .append("Is colossal: ").append(crop.isColossal() ? "Yes" : "No").append("\n")
                    .append("Energy: ").append(type.getEnergy()).append("\n")
                    .append("Health: ").append(type.getBaseHealth()).append("\n");
        }

        return new Result(true, result.toString());
    }

    public Result fertilizePlant(String fertilizerName, String direction) {
        FertilizerType fertilizerType = findFertilizerType(fertilizerName);
        if (fertilizerType == null) {
            return new Result(false, "Fertilizer type not found");
        }
        Fertilizer fertilizer = App.getActiveGame().getCurrentPlayer().getInventory().getFertilizer(fertilizerName);
        if (fertilizer == null) {
            return new Result(false, "You don't have this fertilizer");
        }
        Pos offset = getDirectionOffset(direction);
        if (offset == null) {
            return new Result(false, "Invalid direction");
        }
        int dirX = offset.getX();
        int dirY = offset.getY();
        Pos playerPos = App.getActiveGame().getCurrentPlayer().getPosition();
        if (playerPos.getX() + dirX < 0 || playerPos.getY() + dirY < 0 || playerPos.getX() + dirX > App.getActiveGame().getMapWidth() || playerPos.getY() + dirY > App.getActiveGame().getMapHeight()) {
            return new Result(false, "there is no tile");
        }
        Tile targetTile = App.getActiveGame().getMap()[playerPos.getY() + dirY][playerPos.getX() + dirX];
        if (targetTile.getType().equals(TileType.Plowed)){
            targetTile.setFertilized(true);
            targetTile.setFertilizerType(fertilizerType);
            App.getActiveGame().getCurrentPlayer().getInventory().getItems().put(fertilizer, -1);
            if (targetTile.getCrop() != null) {
                if (fertilizerType.equals(FertilizerType.SPEED_GRO)) {
                    targetTile.getCrop().advanceStage();
                }
                targetTile.getCrop().setFertilized(true);
            }
            if (targetTile.getTree() != null) {
                targetTile.getTree().setFertilized(true);
            }
            return new Result(true, "the tile is fertilized");
        }
        return new Result(false, "the tile is not plowed!");

    }

    //TODO complete this func
    public Result howMuchWater() {
        if (!(App.getActiveGame().getCurrentPlayer().getCurrentEquipment() instanceof WateringCan wateringCan) ){
            return new Result(false, "You are equipped watering can.");
        }
        int amount = wateringCan.getCapacity() - wateringCan.getUsedWaterCapacity();
        return new Result(true, amount + " units left out of " + wateringCan.getCapacity());
    }



    private FertilizerType findFertilizerType(String fertilizerName) {
        for (FertilizerType fertilizer : FertilizerType.values()) {
            if (fertilizer.getName().equals(fertilizerName)) {
                return fertilizer;
            }
        }
        return null;
    }


    public static CropType findCropTypeBySeed(SeedType seedType) {
        for (CropType crop : CropType.values()) {
            if (crop.getSource().equals(seedType)) {
                return crop;
            }
        }
        return null;
    }

    public static TreeType findTreeTypeBySeed(SeedType seedType) {
        for (TreeType tree : TreeType.values()) {
            if (tree.getSapling().equals(seedType)) {
                return tree;
            }
        }
        return null;
    }

    private TreeType findTreeTypeByName(String name) {
        for (TreeType tree : TreeType.values()) {
            if (tree.getName().equals(name) || tree.getSapling().getName().equals(name)) {
                return tree;
            }
        }
        return null;
    }

    private CropType findCropTypeByName(String name) {
        for (CropType cropType : CropType.values()) {
            if (cropType.getName().equals(name)) {
                return cropType;
            }
        }
        return null;
    }

    private SeedType findSeedTypeByName(String name) {
        for (SeedType seedType : SeedType.values()) {
            if (seedType.getName().equals(name)) {
                return seedType;
            }
        }
        return null;
    }

    public static void attackOfCrow() {
        Random rand = new Random();
        for (Player player : App.getActiveGame().getPlayers()) {
            if (player.getFarm().getPlantedCrops().size() > 16) {
                int index = rand.nextInt(player.getFarm().getPlantedCrops().size());
                if (player.getFarm().getPlantedCrops().get(index).getCropType().isOneTime()) {
                    Crop crop = player.getFarm().getPlantedCrops().get(index);
                    App.getActiveGame().getMap()[crop.getPosition().getY()][crop.getPosition().getX()].setCrop(null);
                    player.getFarm().getPlantedCrops().remove(index);
                }
                else {
                    player.getFarm().getPlantedCrops().get(index).setHarvested(true);
                }
            }
        }
    }


    //cheat code
    public Result cheatAdd(String input) {
        SeedType seedType = findSeedTypeByName(input);
        if (seedType == null) {
            return new Result(false, "Seed type not found");
        }
        Seed seed = new Seed(input, seedType);
        App.getActiveGame().getCurrentPlayer().getInventory().addItem(seed, 1);
        return new Result(true, "seed added");
    }
}
