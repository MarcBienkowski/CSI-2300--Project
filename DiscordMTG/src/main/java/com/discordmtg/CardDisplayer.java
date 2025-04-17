package com.discordmtg;

import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.layout.Region;
import javafx.scene.image.Image;
import java.io.FileInputStream;
import java.io.File;
import javafx.application.Platform;


public class CardDisplayer {
    public static ArrayList<Button> displayed_buttons_array = new ArrayList<Button>();
    
    
    public static Button make_card() {
        Button button = new Button();
        button.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
       
        button.setPrefWidth(200 * ResizeListener.scale_X);
        button.setPrefHeight(280* ResizeListener.scale_Y);

        return button;
    }
    
    
    
    public static void clear_display() {
        for (Button button : displayed_buttons_array) {
            BasicView.card_pane.getChildren().remove(button);
            System.out.println("removeda button");
        }
        BasicView.card_pane.getChildren().clear();
        displayed_buttons_array.clear();
    }
    
    
    public static void endstep_trigger() {
        
        CardGrabber.available_cards = 0;
        System.out.println("available cards at start of endstep trigger is" + CardGrabber.available_cards);
        clear_display();
        System.out.println("display cleared");
        
        //generate cards
        for (int pass = 0 ; pass < CardGrabber.cards_to_gamble_on_endstep; pass++) {
        //generate image
            CardGrabber.gamble();
            String file_location = "src/main/resources/Cards/" + String.valueOf(CardGrabber.card_identifier)+ ".png";
            System.out.println("we gambled successfuly");
            
            System.out.println("pass is "+pass);
            System.out.println("card identifier is "+ CardGrabber.card_identifier);
            
        //generate card button
            Button button = make_card();
                    

            System.out.println("we made button");
            
        //load image from file
            File png_file = new File(file_location);
            Image image = new Image(png_file.toURI().toString(), false);
            ImageView card_image = new ImageView(image);
            System.out.println("made image view");
            
        //set card image to button and button size
            button.setGraphic(card_image);
            card_image.setFitWidth(250);
            card_image.setFitHeight(350);
            System.out.println("set graphic");
            
        //add button to card_pane to be displayed
            BasicView.card_pane.getChildren().addAll(button);
            System.out.println("added button to card_pane");
            
        // swipe code
            final double[] origin_X = new double[1];
            final double[] origin_Y = new double[1];
            final double[] original_cursor_X = new double[1]; // x position of where on the button was clicked

            button.setOnMousePressed(event -> {
                origin_X[0] = button.getTranslateX();
                origin_Y[0] = button.getTranslateY();
                original_cursor_X[0] = event.getSceneX();
            });

            button.setOnMouseDragged(event -> {
                double currentX = event.getSceneX();
                double deltaX = currentX - original_cursor_X[0]; // difference from start
                button.setTranslateX(origin_X[0] + deltaX); // move from original position
            });

            button.setOnMouseReleased(event -> {
                double draggedDistance = Math.abs(button.getTranslateX() - origin_X[0]);
                double halfWidth = button.getWidth() / 2;

                if (draggedDistance >= halfWidth) {
                    //no matter what card is casted

                    System.out.println("dragged distance was greater than or equal to hald the cards width");
                    
                    if (CardGrabber.discord_status == false) {
                        System.out.println("discord status is false");
                        //disable actions in card log
                        button.setOnMousePressed(null);
                        button.setOnMouseDragged(null);
                        button.setOnMouseReleased(null);
                        // Move the real button to the log
                        CardLogView.cardlog_pane.getChildren().add(0, button);
                        CardLogView.cardlog_button_array.add(button);

                        

                        // Remove from card display
                        displayed_buttons_array.remove(button);
                        BasicView.card_pane.getChildren().remove(button);

                        // Update availability
                        CardGrabber.available_cards--;
                        BasicView.card_count_label.setText(CardGrabber.available_cards + " CARDS");

                        // Reset position
                        button.setTranslateX(origin_X[0]);
                        button.setTranslateY(origin_Y[0]);

                    } else {
                        System.out.println("discord status is true");

                    // Create and log a copy of the button
                        Button cardlog_button = make_card();
                        
                        ImageView original_image = (ImageView) button.getGraphic();
                        Image image_copy = original_image.getImage();
                        ImageView imageview_copy = new ImageView(image_copy);
                        imageview_copy.setFitWidth(original_image.getFitWidth());
                        imageview_copy.setFitHeight(original_image.getFitHeight());
                        cardlog_button.setGraphic(imageview_copy);
                        
                    //add copy to cardlog
                        //disables actions in card log
                        cardlog_button.setOnMousePressed(null);
                        cardlog_button.setOnMouseDragged(null);
                        cardlog_button.setOnMouseReleased(null);
                        CardLogView.cardlog_pane.getChildren().add(0, cardlog_button);
                        CardLogView.cardlog_button_array.add(cardlog_button);
                        System.out.println("card added to cardlog_pane");

                    // Refresh the original card in card_pane
                        CardGrabber.gamble();
                        int new_card_id = CardGrabber.card_identifier;
                        String new_file_location = "src/main/resources/Cards/" + new_card_id + ".png";
                        File new_file = new File(new_file_location);
                        Image new_image = new Image(new_file.toURI().toString(), false);
                        ImageView replacement_image = new ImageView(new_image);
                        button.setGraphic(replacement_image);
                        button.setPrefWidth(250);
                        button.setPrefHeight(350);
                        replacement_image.setFitWidth(250);
                        replacement_image.setFitHeight(350);
                        ResizeListener.updateUI();
                        



                    // Reset position
                        button.setTranslateX(origin_X[0]);
                        button.setTranslateY(origin_Y[0]);
                    }


                    
                } else {
                    button.setTranslateX(origin_X[0]);
                    button.setTranslateY(origin_Y[0]);
                }
            });

            
            //this adds all the buttons being currently being displayed to an array so that if we want to remove or access one later we cando so here
            displayed_buttons_array.add(button);   
            
            
        }
    //count cards available
        for (Button button : CardDisplayer.displayed_buttons_array) {
            CardGrabber.available_cards++;
        }
        BasicView.card_count_label.setText(CardGrabber.available_cards + " CARDS");
        
    //spacer for scrollpane
        Region space = new Region();
        space.setPrefHeight((350 * Math.min(ResizeListener.scale_X, ResizeListener.scale_Y)) / 2);
        BasicView.card_pane.getChildren().addAll(space);
                
    //update UI changes
        ResizeListener.updateUI();


        
        
        
        
    

    }
}
