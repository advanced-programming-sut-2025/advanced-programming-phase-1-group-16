package com.group16.stardewvalley.view.menu;


import com.group16.stardewvalley.controller.agriculture.AgricultureController;
import com.group16.stardewvalley.controller.AnimalController;
import com.group16.stardewvalley.controller.map.MapController;
import com.group16.stardewvalley.controller.menu.GameMenuController;
import com.group16.stardewvalley.controller.menu.HomeMenuController;
import com.group16.stardewvalley.controller.shops.ShopController;
import com.group16.stardewvalley.controller.tools.GadgetController;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.App;
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
    private final TimeDate timeDate = new TimeDate(App.getActiveGame());
    private final AnimalController animalController = new AnimalController();
    private final AgricultureController agricultureController = new AgricultureController();
    private final HomeMenuController homeMenuController = new HomeMenuController();
    private final GadgetController gadgetController = new GadgetController();
    private final ShopController shopController = new ShopController();

    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();

        Matcher matcher = null;

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
        } else if ((matcher = GameMenuCommands.ForagingInfo.getMatcher(input)) != null){
            System.out.println(agricultureController.foragingInfo(matcher.group("name")));
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
        //TODO shayad be khata monjar she
        else if (App.getActiveGame() != null && mapController.isPlayerInCottage(App.getActiveGame().getCurrentPlayer())) {
            if ((matcher = GameMenuCommands.PutFood.getMatcher(input)) != null){
                System.out.println(homeMenuController.putItemInRefrigerator(matcher.group("food")));
            } else if ((matcher = GameMenuCommands.PickFood.getMatcher(input)) != null){
                System.out.println(homeMenuController.pickItemInRefrigerator(matcher.group("food")));
            } else if ((matcher = GameMenuCommands.CookingRecipes.getMatcher(input)) != null){
                System.out.println(homeMenuController.showRecipeOfFood());
            } else if ((matcher = GameMenuCommands.PrepareFood.getMatcher(input)) != null){
                System.out.println(homeMenuController.cooking(matcher.group("food")));
            } else if ((matcher = GameMenuCommands.EatFood.getMatcher(input)) != null){
                System.out.println(homeMenuController.eat(matcher.group("food")));
            }
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


        //fishing
        else if ((matcher = GameMenuCommands.Fishing.getMatcher(input)) != null){
            System.out.println(gadgetController.fishing(matcher.group("fishingPole")));
        }

        //gadget
        else if ((matcher = GadgetsCommands.EQUIP.getMatcher(input)).matches()) {
            System.out.println(gadgetController.equip(matcher));
        } else if ((matcher = GadgetsCommands.AVAILABLE_TOOLS.getMatcher(input)).matches()) {
            System.out.println(gadgetController.showAvailableTools());
        } else if ((matcher = GadgetsCommands.UPGRADE_TOOLS.getMatcher(input)).matches()) {
            System.out.println(shopController.upgradeTool(matcher));
        } else if ((matcher = GadgetsCommands.USE_TOOL.getMatcher(input)).matches()) {
            System.out.println(gadgetController.useTool(matcher));
        }









        else{
            System.out.println("invalid command!");

        }
    }
}
