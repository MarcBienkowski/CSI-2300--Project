/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.discordmtg;
import java.io.File;
import java.util.ArrayList;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class CardGrabber {
    public static int available_cards = 0;
    public static int cards_to_gamble_on_endstep = 1;
    public static int card_identifier = 0;
    public static boolean discord_status = true;
            
    public static ArrayList<ArrayList<String>> card_names_array = new ArrayList<>();
    

    
    public static void write_to_card_folder(HttpResponse<InputStream> image, String file_name) {
        try {
            //file path
            File file = new File("src/main/resources/Cards/"+ file_name);
            
            //onput and output file streams
            InputStream in = image.body(); 
            FileOutputStream out = new FileOutputStream(file);
            
            //buffer
            byte[] buffer = new byte[1024];
            int bytes_read;

            //reads annd writes to file
            while ((bytes_read = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytes_read);
            }

            //close streams
            in.close();
            out.close();
        
        
        }catch(IOException e) {
            System.out.println("failed to write to folder");
        
        }
        
        
        
    }
    
    public static void gamble() {
        ArrayList<String> card = new ArrayList();
        String url_filter = "https://api.scryfall.com/cards/random?q=legal%3Acommander+-t%3Aland";
        String card_front_URI = null;
        String card_back_URI = null;
        HttpResponse<InputStream> card_front_image;
        HttpResponse<InputStream> card_back_image;
        
        
        card_identifier += 1;
        
        try {
        //get initial json file
            HttpClient http_client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .header("User-Agent", "Discord_Ability_Mobile_App")
                .header("Accept", "application/json;q=0.9,*/*;q=0.8")
                .uri(new URI(url_filter))
                .build();
            HttpResponse<String> response = http_client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("sent response");
            
        //decode file
            Gson gson = new Gson();
            JsonObject response_json = gson.fromJson(response.body(), JsonObject.class);
            System.out.println("converted to json");
            
        //open json dictionary for uri for png 
            //check if the card is double sided
            
            if (response_json.has("card_faces")) {
                System.out.println("double sided");
                //front URI
                card_front_URI = response_json
                    .getAsJsonArray("card_faces")
                    .get(0).getAsJsonObject()
                    .getAsJsonObject("image_uris")
                    .get("png").getAsString();
                //back URI
                card_back_URI = response_json
                    .getAsJsonArray("card_faces")
                    .get(1).getAsJsonObject()
                    .getAsJsonObject("image_uris")
                    .get("png").getAsString();
                
                //update request for front card image
                request = HttpRequest.newBuilder().uri(URI.create(card_front_URI)).build();
                card_front_image = http_client.send(request, HttpResponse.BodyHandlers.ofInputStream());
                //update request for back card image
                request = HttpRequest.newBuilder().uri(URI.create(card_back_URI)).build();
                card_back_image = http_client.send(request, HttpResponse.BodyHandlers.ofInputStream());
                
        //write image to card resource folder
                write_to_card_folder(card_front_image, String.valueOf(card_identifier) + ".png");
                write_to_card_folder(card_back_image, String.valueOf(card_identifier) + "b" + ".png");
                
                card.add(String.valueOf(card_identifier) +"f" + ".png");
                card.add(String.valueOf(card_identifier) + "b" + ".png");
                card_names_array.add(card);
        
                
            } else {
                System.out.println("one sided");
                
                card_front_URI = response_json
                    .getAsJsonObject("image_uris")
                    .get("png")
                    .getAsString();
                   
                //update request for front card image             
                request = HttpRequest.newBuilder().uri(URI.create(card_front_URI)).build(); 
                card_front_image = http_client.send(request, HttpResponse.BodyHandlers.ofInputStream());      
                write_to_card_folder(card_front_image, String.valueOf(card_identifier) + ".png");
                System.out.println("wrote card to folder");
                
                card.add(String.valueOf(card_identifier)+ ".png");
                card_names_array.add(card);
                
            }
    
        } catch (Exception e) {
            System.out.println("failed to gamble card");
        }  
    }
    
    
            
    
    
    
    
}
