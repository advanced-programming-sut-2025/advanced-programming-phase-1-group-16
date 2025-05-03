package com.group16.stardewvalley.view.menu;



import com.group16.stardewvalley.controller.menu.LoginMenuController;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.command.LoginMenuCommands;
import com.group16.stardewvalley.model.command.Menu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu implements GameMenuInterface {

    private final LoginMenuController controller = new LoginMenuController();

    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher = null;
        //register
        if ((matcher = LoginMenuCommands.Register.getMatcher(input)) != null) {
            System.out.println(controller.register(matcher.group("username"),
                    matcher.group("password"), matcher.group("passwordConfirm"), matcher.group("nickName"),
                    matcher.group("email"), matcher.group("gender")));
            boolean success = (controller.register(matcher.group("username"),
                    matcher.group("password"), matcher.group("passwordConfirm"), matcher.group("nickName"),
                    matcher.group("email"), matcher.group("gender"))).isSuccessful();
            //random password handling

            if (success) {
                //security questions have been shown
                //get security question
                String input2 = scanner.nextLine();
                Matcher matcher2 = LoginMenuCommands.PickSecurityQuestion.getMatcher(input2);
                if (matcher2 != null) {
                    System.out.println(controller.setSecurityQuestion(matcher2.group("questionNumber"),
                            matcher2.group("answer"), matcher2.group("answerConfirm")));
                }

            }
        }else if((matcher = LoginMenuCommands.Login.getMatcher(input)) != null) {
            boolean logged_in_flag = false;
            if(input.contains("-stay-logged-in")) {
                logged_in_flag = true;
            }
            System.out.println(controller.login(matcher.group("username"), matcher.group("password"), logged_in_flag));


        }else if((matcher = LoginMenuCommands.ForgetPassword.getMatcher(input)) != null){

            System.out.println(controller.forgetPassword(matcher.group("username")));
            if(controller.forgetPassword(matcher.group("username")).isSuccessful()) {

                String input2 = scanner.nextLine();
                Matcher matcher2 = LoginMenuCommands.ForgetPasswordAnswer.getMatcher(input2);
                if(matcher2 != null) {
                    System.out.println(controller.checkSecurityAnswer(matcher.group("username"), matcher2.group("answer")));
                    if (controller.checkSecurityAnswer(matcher.group("username"), matcher2.group("answer")).isSuccessful()) {

                        String input3 = scanner.nextLine();
                        Matcher matcher3 = LoginMenuCommands.ForgetPasswordAnswer.getMatcher(input3);
                        if(matcher3 != null) {
                            System.out.println(controller.getNewPassword( matcher.group("username") ,matcher3.group("password")));
                        }
                    }
                }
            }

        }else if((matcher = LoginMenuCommands.ShowCurrentMenu.getMatcher(input)) != null ){
            System.out.println(controller.showCurrentMenu());

        }else if((matcher = LoginMenuCommands.Exit.getMatcher(input)) != null){
            App.setCurrentMenu(Menu.ExitMenu);
        }

        else{
            System.out.println("invalid command!");
        }
    }
}
