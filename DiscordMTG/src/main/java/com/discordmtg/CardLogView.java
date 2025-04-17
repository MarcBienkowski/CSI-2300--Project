

package com.discordmtg;

import com.gluonhq.charm.glisten.application.MobileApplication;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.layout.Region;
import javafx.scene.control.ScrollPane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.gluonhq.charm.glisten.mvc.View;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
import javafx.application.Platform;


public class CardLogView extends View {
    public static ArrayList<Button> cardlog_button_array = new ArrayList<Button>();
    
    public static VBox cardlog_pane = new VBox();
    public static ScrollPane cardlog_scrollpane = new ScrollPane();
    public static StackPane bottom_pane = new StackPane();
    //bottom image
    public static ImageView bottom_bar = new ImageView("/bottom_bar.png");
    public static StackPane home_layout = new StackPane();
    public static BorderPane main_layout = new BorderPane();
    public static Button home_button = new Button();
    public static Label home_label = new Label();
    public static StackPane card_wrapper = new StackPane();

 

    
    public CardLogView() {

    //spacer for scrollpane/cardlog
        Region space = new Region();
        space.setPrefHeight((250 * Math.min(ResizeListener.scale_X, ResizeListener.scale_Y)) / 2);
        cardlog_pane.getChildren().addAll(space);
        
        
        

        
            //bottom bar right button (switch to card log button)

    //back button image icon
        home_button.setStyle("-fx-background-color: #4d4d4dff;"
                            + "-fx-border-color: #333333ff;"
                            + "-fx-shape: \"M 15 0 H 85 A 15 15 0 0 1 85 30 H 15 A 15 15 0 0 1 15 0 Z\";"
                            + "-fx-border-width: 4;"
                            + "-fx-border-radius: 11;"
                            + "-fx-background-radius: 15;"
                            + "-fx-min-width: 0;"
                            + "-fx-min-height: 0;"
                            + "-fx-alignment: center;"
                            + "-fx-content-display: center;");
        //back text prelimanaries
        home_button.setGraphic(home_label);
        home_label.setText("BACK");
        home_label.setStyle("-fx-text-fill: #FFFFFF");
        home_label.setEllipsisString("BACK");
        
        //functionality
        home_button.setOnAction(event -> {
            MobileApplication.getInstance().switchView(DiscordMTG.HOME_VIEW); // To go to card log
            DiscordMTG.view_state = "home";
            ResizeListener.updateUI();
            Platform.runLater(() -> { //runs a moment later to like blink the text, due to issue where text is like puffy prolly due to custom font
                ResizeListener.updateUI();
            });
        });
        
        
        
         bottom_pane.getChildren().addAll(bottom_bar, home_button);
        
//scroll pane
        cardlog_scrollpane.setPannable(false);
        cardlog_scrollpane.setPrefWidth(ResizeListener.window_width);
        cardlog_scrollpane.setPrefHeight(ResizeListener.window_height);
        
        
        cardlog_scrollpane.toBack();
        
        card_wrapper.getChildren().add(cardlog_pane);
        cardlog_scrollpane.setContent(card_wrapper);
        

        
//set border panes
        
        StackPane.setAlignment(home_button, Pos.BOTTOM_CENTER);
        StackPane.setAlignment(bottom_bar, Pos.BOTTOM_CENTER);
        StackPane.setAlignment(cardlog_scrollpane, Pos.BOTTOM_CENTER);
        
        cardlog_pane.setAlignment(Pos.CENTER); 
        card_wrapper.setAlignment(Pos.CENTER);


        
        
        main_layout.setCenter(cardlog_scrollpane);
        main_layout.setBottom(bottom_pane);        
        main_layout.setPickOnBounds(false); //allows interactibility with scroll pane

        home_layout.getChildren().addAll(cardlog_scrollpane, main_layout);

        setCenter(home_layout);
        
        
    }
    
       
     
     
}
