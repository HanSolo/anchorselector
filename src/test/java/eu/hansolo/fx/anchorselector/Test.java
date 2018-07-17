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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;


/**
 * User: hansolo
 * Date: 16.07.18
 * Time: 16:47
 */
public class Test extends Application {
    private AnchorSelector anchorSelector;

    @Override public void init() {
        anchorSelector = new AnchorSelector();

        registerListeners();
    }

    private void registerListeners() {
        anchorSelector.selectedAnchorProperty().addListener(o -> System.out.println(anchorSelector.getSelectedAnchor()));
    }

    @Override public void start(Stage stage) {
        StackPane pane = new StackPane(anchorSelector);
        pane.setBackground(new Background(new BackgroundFill(Color.rgb(50, 50, 50), CornerRadii.EMPTY, Insets.EMPTY)));
        pane.setPadding(new Insets(10));

        Scene scene = new Scene(pane);
        scene.getStylesheets().add(Test.class.getResource("test.css").toExternalForm());

        stage.setTitle("AnchorSelector Test");
        stage.setScene(scene);
        stage.show();

        anchorSelector.setAnchorColor(Color.PURPLE);
        anchorSelector.setSelectedAnchorColor(Color.YELLOW);
        anchorSelector.setBackgroundColor(Color.rgb(180, 180, 180, 0.4));

        //anchorSelector.setStyle("-anchor-color: rgb(0, 0, 255);");
    }

    @Override public void stop() {
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
