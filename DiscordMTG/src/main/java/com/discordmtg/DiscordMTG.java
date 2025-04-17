package com.discordmtg;

import com.gluonhq.charm.glisten.application.MobileApplication;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.application.Platform;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class DiscordMTG extends MobileApplication {
    public static final String CARD_LOG_VIEW = "CardLogView";
    public static String view_state = "home";
    
    @Override
    public void init() {
        // Initialize view factories for the application
        addViewFactory(HOME_VIEW, BasicView::new); // Register BasicView as HOME_VIEW
        addViewFactory(CARD_LOG_VIEW, CardLogView::new);
    }

    @Override
    public void postInit(Scene scene) {
        //sets primary stage
        Stage primaryStage = (Stage) scene.getWindow();
        
        
        
        //allows ResizeListener class to pay attention to changes in window size
        ResizeListener listener = new ResizeListener(primaryStage);
        
        
        //*
        //scaled down resolution of intended platform of 1080 x 2400 for Samsung Galaxy S20 FE
        
        scene.getWindow().setWidth(270);
        scene.getWindow().setHeight(600);
        //*/
        
        
        
        //load custom font
        Font equestria = Font.loadFont(
            getClass().getResourceAsStream("/Equestria.ttf"), 20);
        
        primaryStage.setTitle("Discord MTG Ability");
        
        Platform.runLater(() -> {
            new ResizeListener(primaryStage);
            ResizeListener.updateUI();
        });
        
    }
    public static void main(String[] args) {
    //cleans cards folder on startup
        File folder = new File("src/main/resources/Cards"); // ✅ Removed extra parenthesis
        for (File file : folder.listFiles()) {
            file.delete();        
        }
        
        
        
        launch(args);
        
        

    }
}

