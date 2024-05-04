package com.ayman.fightEnemies.util;

import com.ayman.fightEnemies.entity.IEntity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to parse the entities file and create the entities in the level.

 */
public class LevelEntitiesParser {

    public static List<IEntity> parseEntitiesFile(String fileName) {
        List<IEntity> entities = new ArrayList<>();
        // Parse the file and populate the entities list
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));

            // the form of the file:
            // Player 3 3
            // Chaser 3 4
            // Dummy 3 5
            // Dummy 3 5
            // HealthEffect 3 5 10
            // SpeedEffect 3 5 10
            //...

            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(" ");
                String entityName = tokens[0];
                int x = Integer.parseInt(tokens[1]);
                int y = Integer.parseInt(tokens[2]);
                // Create the entity and add it to the list
                IEntity entity = EntityFactory.createEntity(entityName, x, y);
                entities.add(entity);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return entities;
    }
}
