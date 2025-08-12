package com.group16.stardewvalley.view.graphics;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.group16.stardewvalley.model.animal.Animal;
import com.group16.stardewvalley.controller.AnimalController;
import com.group16.stardewvalley.model.Result;

public class AnimalMenu extends Window {

    private final AnimalController controller = new AnimalController();
    private final Label feedbackLabel;

    public AnimalMenu(Skin skin, final Animal animal) {
        super(animal.getName(), skin);
        pad(20);
        setModal(true);
        setMovable(true);

        // Info labels
        Label typeLabel = new Label("Type: " + animal.getAnimalType().getName(), skin);
        Label friendshipLabel = new Label("Friendship: " + animal.getFriendship(), skin);
        Label fedLabel = new Label("Fed today: " + (animal.isFeed() ? "Yes" : "No"), skin);
        feedbackLabel = new Label("", skin); // shows messages from controller

        // Buttons
        TextButton feedButton = new TextButton("Feed", skin);
        feedButton.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                Result result = controller.feedHay(animal.getName());
                feedbackLabel.setText(result.toString());
                if (result.isSuccessful()) {
                    fedLabel.setText("Fed today: Yes");
                }
            }
        });

        TextButton petButton = new TextButton("Pet", skin);
        petButton.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                Result result = controller.pet(animal.getName());
                feedbackLabel.setText(result.toString());
                if (result.isSuccessful()) {
                    friendshipLabel.setText("Friendship: " + animal.getFriendship());
                }
            }
        });

        TextButton takeOutButton = new TextButton("Take Out", skin);
        takeOutButton.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                Result result = controller.shepherdAnimals(animal.getName(),
                    (int) animal.getAnimalPos().getX(),
                    (int) animal.getAnimalPos().getY());
                feedbackLabel.setText(result.toString());
            }
        });

        TextButton sellButton = new TextButton("Sell", skin);
        sellButton.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                Result result = controller.sellAnimal(animal.getName());
                feedbackLabel.setText(result.toString());
                if (result.isSuccessful()) {
                    remove(); // Close menu
                }
            }
        });

        TextButton getProductsButton = new TextButton("Get Products", skin);
        getProductsButton.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                Result result = controller.animalProduces();
                feedbackLabel.setText(result.toString());
            }
        });

        // Close button
        TextButton closeButton = new TextButton("Close", skin);
        closeButton.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                remove(); // Just closes the menu
            }
        });

        // Layout
        defaults().pad(10).fillX();
        add(typeLabel).row();
        add(friendshipLabel).row();
        add(fedLabel).row();
        add(feedButton).row();
        add(petButton).row();
        add(takeOutButton).row();
        add(sellButton).row();
        add(getProductsButton).row();
        add(closeButton).row();
        add(feedbackLabel).row();

        pack();
    }
}
