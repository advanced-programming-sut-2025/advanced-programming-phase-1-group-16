package com.group16.stardewvalley.view.menu;


import com.group16.stardewvalley.controller.CheatCodeController;
import com.group16.stardewvalley.controller.agriculture.AgricultureController;
import com.group16.stardewvalley.controller.AnimalController;
import com.group16.stardewvalley.controller.energy.EnergyController;
import com.group16.stardewvalley.controller.map.MapController;
import com.group16.stardewvalley.controller.menu.GameMenuController;
import com.group16.stardewvalley.controller.menu.HomeMenuController;
import com.group16.stardewvalley.controller.shops.ShopController;
import com.group16.stardewvalley.controller.tools.GadgetController;
import com.group16.stardewvalley.controller.weather.WeatherController;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.energy.EnergyCommands;
import com.group16.stardewvalley.model.menu.GameMenuCheatCodeCommands;
import com.group16.stardewvalley.model.menu.GameMenuCommands;
import com.group16.stardewvalley.model.menu.LoginMenuCommands;
import com.group16.stardewvalley.model.menu.ProfileMenuCommands;
import com.group16.stardewvalley.model.shops.CarpentersShop;
import com.group16.stardewvalley.model.time.TimeDate;
import com.group16.stardewvalley.model.tools.GadgetsCommands;
import com.group16.stardewvalley.model.user.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu implements MenuInterface {
    private final GameMenuController controller = new GameMenuController();
    private final MapController mapController = new MapController();
    private final TimeDate timeDate = new TimeDate();
    private final AnimalController animalController = new AnimalController();
    private final AgricultureController agricultureController = new AgricultureController();
    private final CheatCodeController cheatCodeController = new CheatCodeController();
    private final HomeMenuController homeMenuController = new HomeMenuController();
    private final EnergyController energyController = new EnergyController();
    private final GadgetController gadgetController = new GadgetController();
    private final ShopController shopController = new ShopController();
    private final WeatherController weatherController = new WeatherController();


    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();

        Matcher matcher = null;
        boolean isAtHome = App.getActiveGame() != null && MapController.isPlayerInCottage(App.getActiveGame().getCurrentPlayer());


        //new game
        if((matcher = GameMenuCommands.NewGame.getMatcher(input)) != null){ //after new game, player must choose farm and cant do anything else
            Result result = controller.newGame(matcher.group("usernames"));
            System.out.println(result);

            if(result.isSuccessful()){
                Matcher matcher2 = null;
                for (Player player : App.getActiveGame().getPlayers()) {
                    while (player.getFarm() == null) {
                        String input2 = scanner.nextLine();
                        if ((matcher2 = GameMenuCommands.ChooseMap.getMatcher(input2)) != null) {
                            System.out.println(controller.chooseFarm(player, matcher2.group("mapNumber")));
                        }
                        else System.out.println("now you must choose a map");
                    }
                }
                mapController.createMap();
            }

        } else if( (matcher = GameMenuCommands.LoadGame.getMatcher(input)) != null){
            System.out.println(controller.loadGame());
            //load game

        }else if((matcher = GameMenuCommands.Exit.getMatcher(input)) != null){
            System.out.println(controller.exit());

        }
        else if(App.getActiveGame() != null && (matcher = GameMenuCommands.ForceTerminateVote.getMatcher(input)) != null){
            Map<Player, Boolean> votes = new HashMap<Player, Boolean>();
            votes.put(App.getActiveGame().getCurrentPlayer(), true);
            System.out.println("vote in turn! (true/false)");
            for (int i = 0; i < 3; i++) {
                //TODO next turn
                System.out.println("player " + (i + 2) + " please vote.");
                String input2 = scanner.nextLine();
                if (input2.equals("true")) {
                    votes.put(App.getActiveGame().getCurrentPlayer(), true);
                }else if (input2.equals("false")) {
                    votes.put(App.getActiveGame().getCurrentPlayer(), false);
                }
            }
            System.out.println(controller.forceTerminateGame(votes));

        }
        else if((matcher = GameMenuCommands.NextTurn.getMatcher(input)) != null){
            App.getActiveGame().nextTurn();

        } else if ((matcher = GameMenuCommands.ChangeMenu.getMatcher(input)) != null) {
            System.out.println(controller.changeMenu(matcher.group("MenuName")));
            System.out.println(controller.showHomeMenus());

        }else if(( matcher = ProfileMenuCommands.ExitMenu.getMatcher(input)) != null ) {
            //back to main menu
            System.out.println(controller.exitMenu());
        }else if((matcher = LoginMenuCommands.ShowCurrentMenu.getMatcher(input)) != null ) {
            System.out.println(controller.showCurrentMenu());

        } else if ((matcher = GameMenuCommands.Walk.getMatcher(input)) != null){
        Result result = mapController.askWalking(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y")));
        System.out.println(result);
        if (result.isSuccessful()){
            String answer = scanner.nextLine();
            if (answer.equals("yes")) {
                System.out.println(mapController.walk(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y"))));
            }
        }
        } else if ((matcher = GameMenuCommands.PrintMap.getMatcher(input)) != null){
                System.out.println(mapController.printMap(Integer.parseInt(matcher.group("x")),
                        Integer.parseInt(matcher.group("y")), Integer.parseInt(matcher.group("size"))));
        } else if ((matcher = GameMenuCommands.HelpReadingMap.getMatcher(input)) != null){
            System.out.println(mapController.helpReadingMap());
        } else if ((matcher = GameMenuCommands.CraftInfo.getMatcher(input)) != null){
            System.out.println(agricultureController.craftInfo(matcher.group("name")));
        } else if ((matcher = GameMenuCommands.TreeInfo.getMatcher(input)) != null){
            System.out.println(agricultureController.treeInfo(matcher.group("name")));
        } else if ((matcher = GameMenuCommands.PlantSeed.getMatcher(input)) != null){
            System.out.println(agricultureController.planting(matcher.group("seed"), matcher.group("dir")));
        } else if ((matcher = GameMenuCommands.ShowPlant.getMatcher(input)) != null){
            System.out.println(agricultureController.showPlant(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y"))));
        } else if ((matcher = GameMenuCommands.Fertilize.getMatcher(input)) != null){
            System.out.println(agricultureController.fertilizePlant(matcher.group("fertilizer"), matcher.group("dir")));
        } else if ((matcher = GameMenuCommands.HowMuchWater.getMatcher(input)) != null){
            System.out.println(agricultureController.howMuchWater());
        }



        //Cooking
        else if (isAtHome && (matcher = GameMenuCommands.PutFood.getMatcher(input)) != null){
            System.out.println(homeMenuController.putItemInRefrigerator(matcher.group("food")));
        } else if (isAtHome && (matcher = GameMenuCommands.PickFood.getMatcher(input)) != null){
            System.out.println(homeMenuController.pickItemInRefrigerator(matcher.group("food")));
        } else if (isAtHome && (matcher = GameMenuCommands.CookingRecipes.getMatcher(input)) != null){
            System.out.println(homeMenuController.showRecipeOfFood());
        } else if (isAtHome && (matcher = GameMenuCommands.PrepareFood.getMatcher(input)) != null){
            System.out.println(homeMenuController.cooking(matcher.group("food")));
        } else if (isAtHome && (matcher = GameMenuCommands.EatFood.getMatcher(input)) != null){
            System.out.println(homeMenuController.eat(matcher.group("food")));
        }


    //Time and Date Commands
        else if ((matcher = GameMenuCommands.Time.getMatcher(input)) != null) {
            System.out.println(timeDate.getTime());

        }else if ((matcher = GameMenuCommands.Date.getMatcher(input)) != null) {
            System.out.println(timeDate.getDate());

        }else if((matcher = GameMenuCommands.DateTime.getMatcher(input)) != null ){
            System.out.println(timeDate.getDateTime());

        }else if((matcher = GameMenuCommands.DayOfWeek.getMatcher(input)) != null ){
            System.out.println(timeDate.getDayOfWeek());

        } else if ((matcher = GameMenuCommands.CheatAdvanceTime.getMatcher(input)) != null ) {
            System.out.println(timeDate.advanceTimeCheat(Integer.parseInt(matcher.group("amount"))));

        } else if ((matcher = GameMenuCommands.CheatAdvanceDate.getMatcher(input)) != null ) {
            System.out.println(timeDate.advanceDateCheat(Integer.parseInt(matcher.group("amount"))));

        }else if((matcher = GameMenuCommands.Season.getMatcher(input)) != null ){
            System.out.println(timeDate.getSeason());
        }




        //Shop
        else if ((matcher = GameMenuCommands.ShopBuildCoopBarn.getMatcher(input)) != null ){
                System.out.println(App.getActiveGame().getCarpentersShop().buildCoop_Barn(matcher.group("buildingName"), Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y"))));

        }else if ((matcher = GameMenuCommands.BuyAnimal.getMatcher(input)) != null ){
            System.out.println(App.getActiveGame().getMarniesRanch().buyAnimal(matcher.group("animal"), matcher.group("name")));
        }

        //Animal commands
        else if((matcher = GameMenuCommands.Pet.getMatcher(input)) != null ){
            System.out.println(animalController.pet(matcher.group("name")));
        }
        else if((matcher = GameMenuCommands.CheatSetAnimalFriendship.getMatcher(input)) != null ){
            System.out.println(animalController.cheatSetAnimalFriendship(matcher.group("animal"), Integer.parseInt(matcher.group("count"))));
        }
        else if((matcher = GameMenuCommands.ShowAnimalInfo.getMatcher(input)) != null ){
            System.out.println(animalController.showAnimalInfo());
        }
        else if((matcher = GameMenuCommands.ShepherdAnimals.getMatcher(input)) != null){
            System.out.println(animalController.shepherdAnimals(matcher.group("name"),
                    Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y"))));
        }
        else if((matcher = GameMenuCommands.FeedHay.getMatcher(input)) != null){
            System.out.println(animalController.feedHay(matcher.group("name")));
        }
        else if((matcher = GameMenuCommands.AnimalProduces.getMatcher(input)) != null ){
            System.out.println(animalController.animalProduces());
        }
        //TODO
        /*
        else if((matcher = GameMenuCommands.CollectProduct.getMatcher(input)) != null){
            System.out.println(animalController.collectProduct(matcher.group("name")));
        }

         */
        else if((matcher = GameMenuCommands.SellAnimal.getMatcher(input)) != null){
            System.out.println(animalController.sellAnimal(matcher.group("name")));
        }

    //Artisan commands
    else if((matcher = GameMenuCommands.ArtisanUse.getMatcher(input)) != null ){
        System.out.println();
    }
    else if((matcher = GameMenuCommands.ArtisanGet.getMatcher(input)) != null){
        System.out.println();
    }


        //fishing
        else if ((matcher = GameMenuCommands.Fishing.getMatcher(input)) != null){
            System.out.println(gadgetController.fishing(matcher.group("fishingPole")));
        }

        //gadget
        else if ((matcher = GadgetsCommands.EQUIP.getMatcher(input)) != null) {
            System.out.println(gadgetController.equip(matcher));
        } else if ((matcher = GadgetsCommands.AVAILABLE_TOOLS.getMatcher(input)) != null) {
            System.out.println(gadgetController.showAvailableTools());
        } else if ((matcher = GadgetsCommands.UPGRADE_TOOLS.getMatcher(input)) != null) {
            System.out.println(shopController.upgradeTool(matcher));
        } else if ((matcher = GadgetsCommands.USE_TOOL.getMatcher(input)) != null) {
            System.out.println(gadgetController.useTool(matcher));
        }


        //cheat code
        else if ((matcher = GameMenuCheatCodeCommands.AddSeed.getMatcher(input)) != null){
            System.out.println(agricultureController.cheatAdd(matcher.group("seed")));
        } else if ((matcher = GameMenuCheatCodeCommands.AddTool.getMatcher(input)) != null){
            System.out.println(cheatCodeController.addTool(matcher.group("tool")));
        } else if ((matcher = GameMenuCheatCodeCommands.AddFertilizer.getMatcher(input)) != null){
            System.out.println(cheatCodeController.addFertilizer(matcher.group("fertilizer")));
        } else if ((matcher = GameMenuCheatCodeCommands.ShowPosition.getMatcher(input)) != null){
            System.out.println(cheatCodeController.showPosition());
        } else if ((matcher = GameMenuCheatCodeCommands.AddIngredient.getMatcher(input)) != null){
            System.out.println(cheatCodeController.addIngredient(matcher.group("ingredient")));
        } else if ((matcher = GameMenuCheatCodeCommands.LearnRecipe.getMatcher(input)) != null){
            System.out.println(cheatCodeController.learnRecipe(matcher.group("recipe")));
        } else if ((matcher = GameMenuCheatCodeCommands.CookFood.getMatcher(input)) != null){
            System.out.println(cheatCodeController.cookFood(matcher.group("food")));
        }

        //ENERGY
        else if ((matcher = EnergyCommands.SHOW_ENERGY.getMatcher(input)) != null){
            System.out.println(energyController.show());
        } else if ((matcher = EnergyCommands.SET_ENERGY.getMatcher(input)) != null){
            System.out.println(energyController.setEnergy(matcher));
        } else if ((matcher = EnergyCommands.INVENTORY_SHOW.getMatcher(input)) != null){
            System.out.println(energyController.inventoryShow());
        } else if ((matcher = EnergyCommands.ENERGY_UNLIMITED.getMatcher(input)) != null){
            System.out.println(energyController.unlimitedEnergy());
        }

        //weather
       else if ((matcher = GameMenuCommands.Weather.getMatcher(input)) != null) {
            System.out.println(weatherController.showWeather());
        } else if ((matcher = GameMenuCommands.ChangeWeather.getMatcher(input)) != null) {
            System.out.println(weatherController.changeWeather(matcher));
        } else if ((matcher = GameMenuCommands.BuildGreenHouse.getMatcher(input)) != null) {
            System.out.println(weatherController.buildGreenhouse());
        } else if ((matcher = GameMenuCommands.Thor.getMatcher(input)) != null) {
            System.out.println(weatherController.applyFirelight(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y"))));
        } else if ((matcher = GameMenuCommands.WeatherForecast.getMatcher(input)) != null) {
            System.out.println(weatherController.weatherForecast());
        }








        else{
            System.out.println("invalid command!");

        }
    }
}
