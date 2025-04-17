package com.discordmtg;

import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.layout.Region;


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
        for (int pass = 1 ; pass < CardGrabber.cards_to_gamble_on_endstep + 1; pass++) {
            CardGrabber.gamble();
            System.out.println("we gambled successfuly");
            
            System.out.println("pass is "+pass);
            System.out.println("card identifier is "+ CardGrabber.card_identifier);
            
            Button button = make_card();
            System.out.println("we made button");
            
            
            ImageView card_image = new ImageView("/Cards/" + (CardGrabber.card_identifier) + ".png");
            System.out.println("made image view");
            
            card_image.setFitWidth(250);
            card_image.setFitHeight(350);
            
            button.setGraphic(card_image);
            System.out.println("set graphic");
            
            BasicView.card_pane.getChildren().addAll(button);
            System.out.println("added button to card_pane");
            
        //swipe code
            final boolean[] triggered = {false};  // <- workaround for mutable variable

            final double[] originalTranslateX = new double[1];
            final double[] originalTranslateY = new double[1];

            button.setOnMousePressed(event -> {
                originalTranslateX[0] = button.getTranslateX();
                originalTranslateY[0] = button.getTranslateY();
            });

            button.setOnMouseDragged(event -> {
                double mouseX = event.getSceneX();
                button.setTranslateX(mouseX - button.getWidth() / 2);

                double draggedDistance = originalTranslateX[0] - button.getTranslateX();
                double halfWidth = button.getWidth() / 2;

                if (!triggered[0] && draggedDistance >= halfWidth) {
                    triggered[0] = true;

                    if (!CardGrabber.discord_status) {
                        displayed_buttons_array.remove(button);
                        BasicView.card_pane.getChildren().remove(button);
                        CardGrabber.available_cards--;
                        BasicView.card_count_label.setText(CardGrabber.available_cards + " CARDS");
                    } else {
                        CardGrabber.gamble();
                        final ImageView replacement_image = new ImageView("/Cards/" + (CardGrabber.card_identifier) + ".png");

                        double card_width = 250 * Math.min(ResizeListener.scale_X, ResizeListener.scale_Y);
                        double card_height = 350 * Math.min(ResizeListener.scale_X, ResizeListener.scale_Y);
                        replacement_image.setFitWidth(card_width);
                        replacement_image.setFitHeight(card_height);
                        button.setGraphic(replacement_image);

                        button.setTranslateX(originalTranslateX[0]);
                        button.setTranslateY(originalTranslateY[0]);
                    }
                }
            });

            button.setOnMouseReleased(event -> {
                button.setTranslateX(originalTranslateX[0]);
                button.setTranslateY(originalTranslateY[0]);
                triggered[0] = false;  // reset on release
            });
            
            
            displayed_buttons_array.add(button);   
            
            
        }
        
        for (Button button : CardDisplayer.displayed_buttons_array) {
            CardGrabber.available_cards += 1;
            System.out.println("for loop button cardsiplayer array increased executed");
        }
        
        Region space = new Region();
        space.setPrefHeight((350 * Math.min(ResizeListener.scale_X, ResizeListener.scale_Y)) / 2);
        BasicView.card_pane.getChildren().addAll(space);
                
        BasicView.card_count_label.setText(CardGrabber.available_cards + " CARDS");
        
        
        ResizeListener.updateUI();
        System.out.println("available cards at end of endstep trigger is" + CardGrabber.available_cards);

        
        
        
        
    

    }
}
