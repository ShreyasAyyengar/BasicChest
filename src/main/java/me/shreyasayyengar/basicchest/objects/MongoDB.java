package me.shreyasayyengar.basicchest.objects;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import me.shreyasayyengar.basicchest.ChestPlugin;
import me.shreyasayyengar.basicchest.util.InventoryUtils;
import org.bson.Document;
import org.bson.UuidRepresentation;

import java.io.IOException;
import java.util.UUID;

public class MongoDB {

    private MongoCollection<Document> players;

    public MongoDB(String rawConnectionString) {
        openConnection(rawConnectionString);

        try {
            for (Document document : players.find()) {
                ChestPlugin.getInstance().getPlayerChests().put(
                        document.get("uuid", UUID.class),
                        new PlayerChest(InventoryUtils.deserialise(document.getString("inv1")), InventoryUtils.deserialise(document.getString("inv2")), document.get("uuid", UUID.class))
                );
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void openConnection(String rawConnectionString) {

        try {
            ConnectionString connectionString = new ConnectionString(rawConnectionString);
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .serverApi(ServerApi.builder()
                            .version(ServerApiVersion.V1)
                            .build())
                    .uuidRepresentation(UuidRepresentation.STANDARD)
                    .build();

            MongoClient client = MongoClients.create(settings);
            MongoDatabase database = client.getDatabase("basic_chest");
            players = database.getCollection("players");

            System.out.println("\n\nConnected to database!\n\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writePlayerChest(PlayerChest chest) {

        if (hasDocument(chest.getUUID())) {
            deleteDocument(chest.getUUID());
        }

        Document query = new Document("uuid", chest.getUUID());

        query.put("inv1", chest.getSerialisedInvOne());
        query.put("inv2", chest.getSerialisedInvTwo());
        players.insertOne(query);
    }

    private void deleteDocument(UUID uuid) {
        players.deleteOne(new Document("uuid", uuid));
    }

    private boolean hasDocument(UUID uuid) {
        return players.find(new Document("uuid", uuid)).first() != null;
    }
}
