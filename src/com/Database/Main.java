package com.Database;

import com.mongodb.client.*;
import com.mongodb.client.model.changestream.ChangeStreamDocument;
import org.bson.Document;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) {

        display();

    }

    public static void display() {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        Future<String> futureTask = threadPool.submit(Main::doUpdates);
        System.out.println("test");
            if (futureTask.isDone()) {
                System.out.println("Updated");
        }

    }

    public static String doUpdates() {
        MongoClient client = MongoClients.create("");

        MongoDatabase db = client.getDatabase("Core");


        try {


            MongoCollection<Document> col = db.getCollection("permissionsNode");

            MongoChangeStreamCursor<ChangeStreamDocument<Document>> cursor = col.watch().cursor();


            ChangeStreamDocument<Document> next = cursor.next();

            System.out.println(next);

            cursor.close();

            sleep(1000);

            display();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "true";
    }

}
