/*
 * Copyright (c) 2018 by Gerrit Grunwald
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.hansolo.fx.anchorselector;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;


/**
 * User: hansolo
 * Date: 16.07.18
 * Time: 07:19
 */
public class Demo extends Application {
    private AnchorSelector anchorSelector;
    private Label          selectedAnchorLabel;

    @Override public void init() {
        anchorSelector      = new AnchorSelector();
        selectedAnchorLabel = new Label(String.join(": ", "Selected anchor", anchorSelector.getSelectedAnchor().toString()));
        selectedAnchorLabel.setTextFill(Color.WHITE);
        registerListeners();
    }

    private void registerListeners() {
        anchorSelector.selectedAnchorProperty().addListener(o -> selectedAnchorLabel.setText(String.join(": ", "Selected anchor", anchorSelector.getSelectedAnchor().toString())));
    }

    @Override public void start(Stage stage) {
        VBox pane = new VBox(10, anchorSelector, selectedAnchorLabel);
        pane.setBackground(new Background(new BackgroundFill(Color.rgb(50, 50, 50), CornerRadii.EMPTY, Insets.EMPTY)));
        pane.setPadding(new Insets(10));

        Scene scene = new Scene(pane, 250, 100);

        stage.setTitle("AnchorSelector");
        stage.setScene(scene);
        stage.show();
    }

    @Override public void stop() {
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
