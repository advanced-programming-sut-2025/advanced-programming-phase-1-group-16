package com.group16.stardewvalley.model.map;

import com.google.gson.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MapLoader {
    private static Map<String, TileType> tilesetNameToType = Map.of(
        "Flooring_14.tsx", TileType.Ground,
        "Flooring_28.tsx", TileType.Grass,
        "Flooring_27.tsx", TileType.NPCHouse,
        "Flooring_04.tsx", TileType.Town
    );


    public static Tile[][] loadFromJSON(String filename) {
        try {
            JsonObject root = JsonParser.parseReader(new FileReader(filename)).getAsJsonObject();

            int width = root.get("width").getAsInt();
            int height = root.get("height").getAsInt();

            JsonArray tilesets = root.getAsJsonArray("tilesets");
            Map<Integer, TileType> idToType = new HashMap<>();

            // ساخت مپ ID → TileType
            for (JsonElement tsElem : tilesets) {
                JsonObject ts = tsElem.getAsJsonObject();
                int firstgid = ts.get("firstgid").getAsInt();
                String source = ts.get("source").getAsString(); // مثلاً "Flooring_14.tsx"

                // اسم فایل رو از مسیر جدا کن
                String tilesetName = source.substring(source.lastIndexOf("/") + 1);

                TileType type = tilesetNameToType.getOrDefault(tilesetName, TileType.Ground);
                idToType.put(firstgid, type);
            }

            JsonArray layers = root.getAsJsonArray("layers");
            JsonObject layer = layers.get(0).getAsJsonObject(); // فقط لایه اول
            JsonArray data = layer.getAsJsonArray("data");

            Tile[][] tiles = new Tile[height][width];

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int index = y * width + x;
                    int id = data.get(index).getAsInt();
                    TileType tileType = idToType.getOrDefault(id, TileType.Ground);
                    tiles[height - y - 1][x] = new Tile(tileType);
                }
            }

            return tiles;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



}


