package com.discordmtg;

import com.gluonhq.charm.glisten.mvc.View;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


import javafx.scene.shape.Rectangle;

import javafx.scene.paint.Color;

import javafx.scene.image.ImageView;
import javafx.scene.control.Button;

import javafx.geometry.Pos;


import javafx.scene.layout.Region;
import com.gluonhq.charm.glisten.application.MobileApplication;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ScrollPane;
import javafx.application.Platform;


import com.gluonhq.charm.glisten.control.AppBar;


//class initalizes inital elements, everything relative is set in ResizeListener when opening the application
// as opening it counts as updating the window size (even if its the default size and never actively changes later) so all that code triggers and saves space here
public class BasicView extends View {
//layout panes
    public static StackPane home_layout = new StackPane();
    public static BorderPane main_layout = new BorderPane();
//top bar
    public static StackPane top_bar = new StackPane();
    public static Label card_count_label = new Label();
    public static Rectangle top_rectangle = new Rectangle();
//bottom bar
    //ultimate pane that holds all the bottom elements together
    public static StackPane bottom_pane = new StackPane();
    //bottom image
    public static ImageView bottom_bar = new ImageView("/bottom_bar.png");
    //bottom left gamble number button
    public static Label gamble_number_label = new Label();
    public static Button gamble_number_button = new Button();
        //menu buttons for bottom left gamble number button
        public static VBox gamble_menu_VBox = new VBox();
        public static ImageView up_arrow = new ImageView("/up_arrow.png");
        public static Button increase_endstep_button = new Button();
        public static ImageView down_arrow = new ImageView("/down_arrow.png");
        public static Button decrease_endstep_button = new Button();
    //end step trigger button
    public static Button end_step_button = new Button();
    public static Label end_step_label = new Label();
    //options button
    public static Button options_button = new Button();
    public static ImageView options = new ImageView("/options.png");
    //bottom right switch to card log scene button
    public static ImageView card_log_icon = new ImageView("/card_log.png");
    public static Button card_log_button = new Button();
    //sroll pane
    public static ScrollPane scrollpane = new ScrollPane();
    public static VBox card_pane = new VBox();
    public static StackPane card_wrapper = new StackPane();
    
    
    
    public BasicView() {
//top bar preliminaries
        //text for top bar
        card_count_label.setStyle("-fx-text-fill: #FFFFFF");
        top_rectangle.setFill(Color.BLACK);
        //add all to stackpane
        top_bar.getChildren().addAll(top_rectangle, card_count_label);
    
//Bottom bar
    //make bottom image bar resiazble
        bottom_bar.setPreserveRatio(false);
        
    //bottom bar left button (number of cards that are gambled on end step trigger)
        //left button
        gamble_number_button.setStyle("-fx-background-color: #b3b3b3ff;"
                            + "-fx-border-color: #666666ff;"
                            + "-fx-border-width: 4;"
                            + "-fx-border-radius: 15;"
                            + "-fx-background-radius: 15;"
                            + "-fx-padding: 0 0 14 0;"
                            + "-fx-min-width: 0;"
                            + "-fx-min-height: 0;"
                            + "-fx-alignment: center;"
                            + "-fx-content-display: top;");
        

        //left button label for showing # of gambled 
        gamble_number_label.setStyle("-fx-text-fill: #FFFFFF");
        gamble_number_button.setGraphic(gamble_number_label);
        gamble_number_button.setContentDisplay(ContentDisplay.TOP);
        gamble_number_button.setAlignment(Pos.TOP_CENTER);
        
        //functionality of left gamble button
        gamble_number_button.setOnAction(event -> { //button function
            gamble_menu_VBox.setVisible(!gamble_menu_VBox.isVisible());
        });
        
        
        //menu for increasing/decreasing end_step #
        gamble_menu_VBox.getChildren().addAll(increase_endstep_button, decrease_endstep_button);
        gamble_menu_VBox.setVisible(false);
        StackPane.setAlignment(gamble_menu_VBox, Pos.BOTTOM_LEFT);
        
        
        
        
        
            //increase button
        increase_endstep_button.setGraphic(up_arrow);
        up_arrow.setPreserveRatio(true);
        increase_endstep_button.setStyle("-fx-background-color: #b3b3b3ff;"
                                + "-fx-border-color: #666666ff;"
                                + "-fx-border-width: 4;"
                                + "-fx-border-radius: 11;"
                                + "-fx-background-radius: 15;"
                                + "-fx-min-width: 0;"
                                + "-fx-min-height: 0;");
        increase_endstep_button.setOnAction(event -> { //button function
            CardGrabber.cards_to_gamble_on_endstep += 1;
            ResizeListener.updateUI();
        });
        

            //decrease button
        decrease_endstep_button.setGraphic(down_arrow);
        down_arrow.setPreserveRatio(true);
        decrease_endstep_button.setStyle("-fx-background-color: #b3b3b3ff;"
                                + "-fx-border-color: #666666ff;"
                                + "-fx-border-width: 4;"
                                + "-fx-border-radius: 11;"
                                + "-fx-background-radius: 15;"
                                + "-fx-min-width: 0;"
                                + "-fx-min-height: 0;");
        //right button image icon
        decrease_endstep_button.setOnAction(event -> { //button function
            if (CardGrabber.cards_to_gamble_on_endstep > 1) {
                CardGrabber.cards_to_gamble_on_endstep -= 1;
                ResizeListener.updateUI();
            }
        });
        

    //bottom bar right button (switch to card log button)
        card_log_button.setStyle("-fx-background-color: #b3b3b3ff;"
                            + "-fx-border-color: #666666ff;"
                            + "-fx-border-width: 4;"
                            + "-fx-border-radius: 11;"
                            + "-fx-background-radius: 15;"
                            + "-fx-min-width: 0;"
                            + "-fx-min-height: 0;");
        //right button image icon
        card_log_button.setGraphic(card_log_icon);
        card_log_icon.setPreserveRatio(true);
        //functionality
        card_log_button.setOnAction(event -> {
            MobileApplication.getInstance().switchView(DiscordMTG.CARD_LOG_VIEW); // To go to card log
            DiscordMTG.view_state = "cardlog";
            ResizeListener.updateUI();
            
            Platform.runLater(() -> { //runs a moment later to like blink the text, due to issue where text is like puffy prolly due to custom font
                ResizeListener.updateUI();
                
            });
            
            
        });

        
    //end step trigger button
        end_step_button.setStyle("-fx-background-color: #4d4d4dff;"
                            + "-fx-border-color: #333333ff;"
                            + "-fx-shape: \"M 15 0 H 85 A 15 15 0 0 1 85 30 H 15 A 15 15 0 0 1 15 0 Z\";"
                            + "-fx-border-width: 4;"
                            + "-fx-border-radius: 11;"
                            + "-fx-background-radius: 15;"
                            + "-fx-min-width: 0;"
                            + "-fx-min-height: 0;"
                            + "-fx-alignment: center;"
                            + "-fx-content-display: center;");
        //endstep text prelimanaries
        end_step_button.setGraphic(end_step_label);
        end_step_label.setText("ENDSTEP");
        end_step_label.setStyle("-fx-text-fill: #FFFFFF");
        end_step_label.setEllipsisString("ENDSTEP");
        
        end_step_button.setOnAction(event -> { //button function
            CardDisplayer.endstep_trigger();
        });
        
        
    //options button
        options_button.setGraphic(options);
        options.setPreserveRatio(true);
        options_button.setStyle("-fx-background-color: #4d4d4dff;"
                                + "-fx-shape: \"M 50,0 A 50,50 0 1,0 50.0001,0 Z\";"
                                + "-fx-border-color: #333333ff;"
                                + "-fx-border-width: 4;"
                                + "-fx-border-radius: 11;"
                                + "-fx-background-radius: 15;"
                                + "-fx-min-width: 0;"
                                + "-fx-min-height: 0;");
        options_button.setOnAction(event -> { //button function
            CardGrabber.discord_status = !CardGrabber.discord_status;
            
        });
    
    //setting positions of elements

        //
        StackPane.setAlignment(bottom_bar, Pos.BOTTOM_CENTER);
        StackPane.setAlignment(gamble_number_button, Pos.BOTTOM_LEFT);
        StackPane.setAlignment(card_log_button, Pos.BOTTOM_RIGHT);
        StackPane.setAlignment(end_step_button, Pos.BOTTOM_CENTER);
        StackPane.setAlignment(options_button, Pos.BOTTOM_RIGHT);
        
        
        bottom_pane.getChildren().addAll(bottom_bar,gamble_number_button, card_log_button, gamble_menu_VBox, end_step_button, options_button);
        
//scroll pane
        scrollpane.setPannable(false);
        scrollpane.setPrefWidth(ResizeListener.window_width);
        scrollpane.setPrefHeight(ResizeListener.window_height);
        
        
        scrollpane.toBack();
        
        card_wrapper.getChildren().add(card_pane);
        scrollpane.setContent(card_wrapper);
        

        
//set border panes
        
        


        
        main_layout.setTop(top_bar);
        main_layout.setCenter(scrollpane);
        main_layout.setBottom(bottom_pane);        
        main_layout.setPickOnBounds(false); //allows interactibility with scroll pane

        home_layout.getChildren().addAll(scrollpane, main_layout);
        setCenter(home_layout);
    }
    
    //for some reason I cannot disable appbar, so this is my work around lol
    @Override
    protected void updateAppBar(AppBar appBar) {
        appBar.setPrefHeight(0);
    }
    
}


