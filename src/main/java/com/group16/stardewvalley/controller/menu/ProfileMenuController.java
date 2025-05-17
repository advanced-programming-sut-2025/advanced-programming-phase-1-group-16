package com.group16.stardewvalley.controller.menu;


import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.menu.Menu;
import com.group16.stardewvalley.model.menu.ProfileMenuCommands;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.user.User;

public class ProfileMenuController {


    public Result changeUsername(String username){
        User user = App.getLoggedInUser();
        if (ProfileMenuCommands.Username.getMatcher(username) == null) {
            return new Result(false, "username format is invalid!");
        }
        if(user.getUsername().equals(username)){
            return new Result(false, "enter a new username!");
        }
        if(!doesUsernameExists(username)){
            return new Result(false, "username is already taken");
        }

        user.setUsername(username);
        return new Result(true, "username successfully changed!");
    }

    public Result changeNickName(String nickname){
        if(App.getLoggedInUser().getUsername().equals(nickname)){
            return new Result(false, "enter a new nickname!");
        }

        App.getLoggedInUser().setNickName(nickname);
        return new Result(true, "nickname successfully changed!");
    }

    public Result changeEmail(String email){
        User user = App.getLoggedInUser();
        if(ProfileMenuCommands.Email.getMatcher(email) == null) {
            return new Result(false, "email format is invalid!");
        }
        if(user.getEmail().equals(email)){
            return new Result(false, "enter a new email!");
        }
        user.setEmail(email);
        return new Result(true, "email successfully changed!");
    }

    public Result changePassword(String newPassword, String oldPassword){
        User user = App.getLoggedInUser();
        if(!user.getPassword().equals(oldPassword)){
            return new Result(false, "enter your old password correctly!");
        }
        if(ProfileMenuCommands.Password.getMatcher(newPassword) == null) {
            return new Result(false, "password format is invalid!");
        }
        if(user.getPassword().equals(newPassword)){
            return new Result(false, "enter a new password!");
        }


        user.setPassword(newPassword);
        return new Result(true, "password successfully changed!");
    }

    public Result showUserInfo(){
        User user = App.getLoggedInUser();

        String output =
                "username: " + user.getUsername() + "\nnickname: " + user.getNickName() +
                "\nmost money reached: " + user.getMoney() + "\ngame played: " + user.getGamePlayed();

        return new Result(true, output);
    }

    public Result exitMenu(){
        App.setCurrentMenu(Menu.MainMenu);
        return new Result(true, "you are in the main menu!");
    }




    //private methods


    private boolean doesUsernameExists(String username) {
        for (User user : App.users){
            if(user.getUsername().equals(username) ){
                return false;
            }
        }
        return true;
    }

    public Result showCurrentMenu(){
        return new Result(true, App.getCurrentMenu().getName());
    }


}

