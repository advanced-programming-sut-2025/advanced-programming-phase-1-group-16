package com.group16.stardewvalley.controller.agriculture;

import com.group16.stardewvalley.model.Result;
import netscape.javascript.JSObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;


public class AgricultureController {
    public Result craftInfo(String name) {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("crops.json");
            if (inputStream == null) {
                return new Result(false,"no craft found"); // یا هر کاری که می‌خوای برای هندل خطا انجام بده
            }
            Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            Gson gson = new Gson();
            Type treeListType = new TypeToken<List<Tree>>() {}.getType();
            List<Tree> trees = gson.fromJson(reader, treeListType);

            // حالا مثلاً یکی از درخت‌ها رو برگردون
            for (Tree tree : trees) {
                if (tree.getName().equalsIgnoreCase(name)) {
                    return Results.ok(tree.toString()); // یا هر مدل نمایش دیگه
                }
            }
            return Results.notFound();

        } catch (Exception e) {
            e.printStackTrace();
            return Results.internalServerError();
        }
    }
}
