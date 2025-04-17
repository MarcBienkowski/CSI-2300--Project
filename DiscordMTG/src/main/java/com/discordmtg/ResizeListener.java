package com.discordmtg;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.scene.text.Font;

import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.application.MobileApplication;
import javafx.application.Platform;
import javafx.scene.control.Button;

import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.layout.Region;
import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;
import javafx.scene.Node;




 
public class ResizeListener {
    
    public static double scale_X;
    public static double scale_Y;
    public static double window_width;
    public static double window_height;
    static double card_count_label_border;
    
    private BasicView basic_view;
    
    public static void updateScale(Scene scene) {
       
        window_width = scene.getWidth();
        window_height = scene.getHeight();
        System.out.println("window width is "+ window_width);
        System.out.println("window height is "+ window_height);

        // Base Values
        double baseWidth = 270;
        double baseHeight = 600;

        // Calculate the scale as the ratio of the current window size to the base size
        scale_X = window_width / baseWidth ;
        scale_Y = window_height / baseHeight;

        // Print out the updated scale for debugging
        System.out.println("Updated X scale is: " + scale_X);
        System.out.println("Updated Y scale is: " + scale_Y);
   }
    
    
    
    public static void updateUI() {
        switch(DiscordMTG.view_state) {
            case "home":
                            //Topbar shenanigans
                //debug information
                    double appBarHeight = 85 * ResizeListener.scale_Y;
                    System.out.println("Updated top bar height is: " + appBarHeight);
                // condition check for if the top bar is too close to left/right scene borders
                if (window_width == BasicView.card_count_label.getWidth()) {
                        card_count_label_border = window_width;
                    }
                //top bar
                    if (window_width <= card_count_label_border) {
                        //what happens if label is touching the border
                        BasicView.top_rectangle.setWidth(window_width );
                        BasicView.top_rectangle.setHeight(94 * scale_X);

                    } else {
                        //what normally happens
                        BasicView.top_rectangle.setWidth(window_width );
                        BasicView.top_rectangle.setHeight(80 * scale_Y);
                    }
                //updating card_count_label size and text

                    // condition for when header label touches scene edges
                    System.out.println("the window wdith is " + window_width);
                    System.out.println("the top label width is" + BasicView.card_count_label.getWidth());

                    //update available_cards_test



                    BasicView.card_count_label.setEllipsisString(CardGrabber.available_cards + " CARDS");
                    BasicView.card_count_label.setText(CardGrabber.available_cards + " CARDS");
                    //set label size
                    if (window_width <= card_count_label_border) {
                        //what happens if label is touching the border
                        BasicView.card_count_label.setFont(Font.font("Equestria", (75 * scale_X))); //slightly larger font size accounts for dramatic change         
                    } else {
                        //what normally happens           
                        BasicView.card_count_label.setFont(Font.font("Equestria", (65 * scale_Y)));
                    }
                    //runs a moment later to like blink the text, due to issue where text is like puffy prolly due to custom font
                    Platform.runLater(() -> {
                        if (window_width <= card_count_label_border) {
                            BasicView.card_count_label.setFont(Font.font("Equestria", (75 * scale_X))); //slightly larger font size accounts for dramatic change                
                        } else {
                            BasicView.card_count_label.setFont(Font.font("Equestria", (65 * scale_Y)));
                        }
                    });





            //bottom bar implementation
                //actual bottom bar

                    BasicView.bottom_bar.setFitWidth(window_width );
                    BasicView.bottom_bar.setFitHeight(70 * scale_Y);

                //prefacial bottom button scaling for border and width
                    double base_width = 50.0;
                    double base_height = 60.0;

                    double bottom_button_width = BasicView.gamble_number_button.getWidth();
                    double bottom_button_height = BasicView.gamble_number_button.getHeight();

                        // Geometric mean of current and base size
                    double current_size = Math.sqrt(bottom_button_width * bottom_button_height);
                    double base_size = Math.sqrt(base_width * base_height);

                        //actual scale
                    double bottom_button_scale = (current_size / base_size);

                //bottom left gamble number button
                    BasicView.gamble_number_label.setText("" + CardGrabber.cards_to_gamble_on_endstep);
                    BasicView.gamble_number_label.setEllipsisString("" + CardGrabber.cards_to_gamble_on_endstep);

                    //button size scaling
                    BasicView.gamble_number_button.setPrefSize(50 * scale_X, 60 * scale_Y); 
                    BasicView.gamble_number_button.setTranslateX(6* scale_X);
                    BasicView.gamble_number_button.setTranslateY(-5 * scale_Y);

                    //button padding
                    String base_padding = "-fx-padding: 0 0 " + (14 * scale_Y) +" 0;";





                //bottom left button label sizing


                    //gets # of digits then sizes accordingly by decreasing percentages
                    int digits = (CardGrabber.cards_to_gamble_on_endstep == 0) ? 1 : (int) Math.log10(CardGrabber.cards_to_gamble_on_endstep) + 1;

                    //accounts for if label size is larger than button
                    double calculated_font = (100 - (digits * 25)) * scale_X; //scale_X instead of scale_Y beacuse we want relative hieght of button as button height is already scaled down elsewhere above
                    double maxHeight = BasicView.gamble_number_button.getHeight();
                    double limited_font = Math.min(calculated_font, maxHeight);
                    System.out.println("calculated font is" + calculated_font);
                    System.out.println("max height is " + maxHeight);
                    System.out.println("limited font is" + limited_font);

                    //set text
                    BasicView.gamble_number_label.setFont(Font.font("Equestria", limited_font));
                    Platform.runLater(() -> { //runs a moment later to like blink the text, due to issue where text is like puffy prolly due to custom font
                        BasicView.gamble_number_label.setFont(Font.font("Equestria", limited_font));
                    });

                //bottom right card log button
                    //sizing
                    BasicView.card_log_button.setPrefSize(50 * scale_X, 60 * scale_Y);
                    BasicView.card_log_button.setTranslateX(-6 * scale_X);
                    BasicView.card_log_button.setTranslateY(-5* scale_Y);

                    //icon
                    BasicView.card_log_icon.setFitWidth(40 * scale_X);
                    BasicView.card_log_icon.setFitHeight(40 * scale_Y);



                //gamble number button menu buttons
                    //offset from bttom    
                    BasicView.gamble_menu_VBox.setTranslateY(-((BasicView.gamble_number_button.getHeight()) * (80/base_height)));
                    //increase
                    BasicView.increase_endstep_button.setPrefSize(50 * scale_X, 60 * scale_Y);
                    BasicView.up_arrow.setFitWidth(40 * scale_X);
                    BasicView.up_arrow.setFitHeight(40 * scale_Y);
                    BasicView.increase_endstep_button.setTranslateX(5* scale_X);

                    //decrease
                    BasicView.decrease_endstep_button.setPrefSize(50 * scale_X, 60 * scale_Y);
                    BasicView.down_arrow.setFitWidth(40 * scale_X);
                    BasicView.down_arrow.setFitHeight(40 * scale_Y);
                    BasicView.decrease_endstep_button.setTranslateX(5* scale_X);


                //end step trigger button
                    //end step trigger button size
                    BasicView.end_step_button.setPrefSize(140 * scale_X, 50 * scale_Y);
                    BasicView.end_step_button.setTranslateY(-42 * scale_Y);
                    //end step trigger label size
                    BasicView.end_step_label.setFont(Font.font("Equestria", Math.min(31 * scale_X, BasicView.end_step_button.getHeight())));
                    Platform.runLater(() -> { //runs a moment later to like blink the text, due to issue where text is like puffy prolly due to custom font
                        BasicView.end_step_label.setFont(Font.font("Equestria", Math.min(31 * scale_X, BasicView.end_step_button.getHeight())));
                    });
                    //end step padding
                    String end_step_padding = "-fx-padding: 0 0 " + (8 * scale_Y) +" 0;";
                    double end_step_button_width_scale =  (BasicView.end_step_button.getWidth()/ 140);
                    String end_step_pill_scale = "-fx-shape: \"M 15 0 H "+(85 * end_step_button_width_scale)+" A 8 8 0 0 1 "+(85 * end_step_button_width_scale)+" 30 H 15 A 8 8 0 0 1 15 0 Z\";";

                //options button
                    double options_scale = Math.min(scale_X,scale_Y);
                    BasicView.options_button.setPrefSize(50, 50);
                    BasicView.options_button.setScaleX(options_scale);
                    BasicView.options_button.setScaleY(options_scale);


                    BasicView.options_button.setTranslateY(-90 * scale_Y);
                    BasicView.options_button.setTranslateX(-(((BasicView.card_log_button.getWidth() / 2) - (BasicView.options_button.getWidth() / 2))) + (-6* scale_X));
                    //image icon size limiting
                    BasicView.options.setFitWidth(25);
                    BasicView.options.setFitHeight(25);


                    //options border code
                    double options_button_width = BasicView.options_button.getWidth();
                    double options_button_height = BasicView.options_button.getHeight();
                    double option_button_scale = ((Math.sqrt(options_button_width * options_button_height))/(Math.sqrt( 50 * 50)));
                    String ob_border_radius ="-fx-border-radius: " + (11 * option_button_scale) + ";";
                    String ob_background_radius = "-fx-background-radius:" + (15 * option_button_scale) +";";
                    String ob_border_width = "-fx-border-width:" + (4 * (option_button_scale)) + ";";



                //bottom buttons border width scaling 
                    String bb_border_radius ="-fx-border-radius: " + (11 * bottom_button_scale) + ";";
                    String bb_background_radius = "-fx-background-radius:" + (15 * bottom_button_scale) +";";
                    String bb_border_width = "-fx-border-width:" + (4 * (bottom_button_scale)) + ";";
                            //adding padding and border scales
                    //left button (includes padding)
                    BasicView.gamble_number_button.setStyle((BasicView.gamble_number_button.getStyle()) + base_padding  + bb_border_radius + bb_background_radius + bb_border_width);
                    //right button
                    BasicView.card_log_button.setStyle((BasicView.card_log_button.getStyle()) + bb_border_radius + bb_background_radius + bb_border_width);

                    //gamble number menu buttons
                    BasicView.increase_endstep_button.setStyle((BasicView.increase_endstep_button.getStyle()) + bb_border_radius + bb_background_radius + bb_border_width);
                    BasicView.decrease_endstep_button.setStyle((BasicView.decrease_endstep_button.getStyle()) + bb_border_radius + bb_background_radius + bb_border_width);

                    //end step button
                    BasicView.end_step_button.setStyle((BasicView.end_step_button.getStyle()) + end_step_padding + bb_border_radius + bb_background_radius + bb_border_width + end_step_pill_scale);

                    //options button
                    BasicView.options_button.setStyle((BasicView.options_button.getStyle()) + ob_border_radius + ob_background_radius + ob_border_width);
            //scroll pane
                    BasicView.scrollpane.setTranslateY(BasicView.top_rectangle.getHeight());
            //card pane stuff

                    for (Button button : CardDisplayer.displayed_buttons_array) {
                        double card_width = 250 * Math.min(scale_X, scale_Y);
                        double card_height = 350 * Math.min(scale_X, scale_Y);
                        button.setPrefSize(card_width, card_height);  
                        button.setMinSize(card_width, card_height);
                        button.setMaxSize(card_width, card_height);

                        ImageView card_image =  (ImageView) button.getGraphic();
                        card_image.setFitWidth(card_width);
                        card_image.setFitHeight(card_height);

                    }


                    
                    Platform.runLater(() -> { //runs a moment later to like blink the text, due to issue where text is like puffy prolly due to custom font
                        BasicView.card_pane.setTranslateX((((window_width/ 2) - ( (CardDisplayer.displayed_buttons_array.get(0).getWidth()) / 2))));
                    }); 
                
                break; //end of home case
            case "cardlog":
        //card pane
                    Platform.runLater(() -> { //runs a moment later to like blink the text, due to issue where text is like puffy prolly due to custom font
                        CardLogView.cardlog_pane.setTranslateX((((window_width/ 2) - ( (CardLogView.cardlog_button_array.get(0).getWidth()) / 2))));
                    }); 
        //card log sizing
                for (Button button : CardLogView.cardlog_button_array ) {
                    double card_width = 250 * Math.min(scale_X, scale_Y);
                    double card_height = 350 * Math.min(scale_X, scale_Y);
                    button.setPrefSize(card_width, card_height);  
                    button.setMinSize(card_width, card_height);
                    button.setMaxSize(card_width, card_height);

                    ImageView card_image =  (ImageView) button.getGraphic();
                    card_image.setFitWidth(card_width);
                    card_image.setFitHeight(card_height);

                }
        //bottom bar
            //bottom bar image
                CardLogView.bottom_bar.setFitWidth(window_width );
                CardLogView.bottom_bar.setFitHeight(70 * scale_Y);
                
                
            //back button
                double home_base_width = 140.0;
                double home_base_height = 50.0;

                double home_button_width = CardLogView.home_button.getWidth();
                double home_button_height = CardLogView.home_button.getHeight();

                    // Geometric mean of current and base size
                double hb_current_size = Math.sqrt(home_button_width * home_button_height);
                double hb_base_size = Math.sqrt(home_base_width * home_base_height);

                    //actual scale
                double home_button_scale = (hb_current_size / hb_base_size);
                
                
                CardLogView.home_button.setPrefSize(140 * scale_X, 50 * scale_Y);
                CardLogView.home_button.setTranslateY(-42 * scale_Y);
                //end step trigger label size
                CardLogView.home_label.setFont(Font.font("Equestria", Math.min(31 * scale_X, CardLogView.home_button.getHeight())));
                Platform.runLater(() -> { //runs a moment later to like blink the text, due to issue where text is like puffy prolly due to custom font
                    CardLogView.home_label.setFont(Font.font("Equestria", Math.min(31 * scale_X, CardLogView.home_button.getHeight())));
                });
                //end step padding                
                String home_button_padding = "-fx-padding: 0 0 " + (8 * scale_Y) +" 0;";
                double home_button_button_width_scale =  (CardLogView.home_button.getWidth()/ 140);
                String home_button_pill_scale = "-fx-shape: \"M 15 0 H "+(85 * home_button_button_width_scale)+" A 8 8 0 0 1 "+(85 * home_button_button_width_scale)+" 30 H 15 A 8 8 0 0 1 15 0 Z\";";

                String hb_border_radius ="-fx-border-radius: " + (11 * home_button_scale) + ";";
                String hb_background_radius = "-fx-background-radius:" + (15 * home_button_scale) +";";
                String hb_border_width = "-fx-border-width:" + (4 * (home_button_scale)) + ";";
                
                
                CardLogView.home_button.setStyle((CardLogView.home_button.getStyle()) + home_button_padding + hb_border_radius + hb_background_radius + hb_border_width + home_button_pill_scale);

                
                break;
        }
    }




    
    public ResizeListener(Stage primaryStage) {
        Scene scene = primaryStage.getScene();

        // Use scene dimensions instead of stage
        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            updateScale(scene); // overload this method
            updateUI();
        });

        scene.heightProperty().addListener((obs, oldVal, newVal) -> {
            updateScale(scene);
            updateUI();
        });

        // Initial call
        updateScale(scene);
        updateUI();
    }

   
   
   
}
