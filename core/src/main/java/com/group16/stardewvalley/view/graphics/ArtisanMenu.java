package com.group16.stardewvalley.view.graphics;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.group16.stardewvalley.model.crafting.CraftItem;

public class ArtisanMenu extends Window {

    public ArtisanMenu(Skin skin, CraftItem craftItem) {
        super("Artisan Menu", skin); // Window title

        // Add a welcome message
        Label welcomeLabel = new Label("Welcome to Artisan", skin);

        // Layout
        Table table = new Table();
        table.pad(10);
        table.add(welcomeLabel).center();

        this.add(table).expand().fill();

        // Set size (adjust as needed)
        this.setSize(300, 200);

        // Optional: disable dragging if you want it fixed in place
        this.setMovable(true);
    }
}
