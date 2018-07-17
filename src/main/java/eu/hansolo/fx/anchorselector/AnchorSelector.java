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

import javafx.beans.DefaultProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.ObservableList;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleableProperty;
import javafx.css.StyleablePropertyFactory;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;


/**
 * User: hansolo
 * Date: 16.07.18
 * Time: 07:10
 */
@DefaultProperty("children")
public class AnchorSelector extends Region {
    private static final double                                   PREFERRED_WIDTH               = 64;
    private static final double                                   PREFERRED_HEIGHT              = 64;
    private static final double                                   MINIMUM_WIDTH                 = 32;
    private static final double                                   MINIMUM_HEIGHT                = 32;
    private static final double                                   MAXIMUM_WIDTH                 = 1024;
    private static final double                                   MAXIMUM_HEIGHT                = 1024;
    private static final Color                                    DEFAULT_BACKGROUND_COLOR      = Color.rgb(92, 92, 92, 0.75);
    private static final Color                                    DEFAULT_ANCHOR_COLOR          = Color.rgb(196, 196, 196);
    private static final Color                                    DEFAULT_SELECTED_ANCHOR_COLOR = Color.WHITE;
    private static final StyleablePropertyFactory<AnchorSelector> FACTORY                       = new StyleablePropertyFactory<>(Region.getClassCssMetaData());
    private static final CssMetaData<AnchorSelector, Color>       BACKGROUND_COLOR              = FACTORY.createColorCssMetaData("-background-color", s -> s.backgroundColor, DEFAULT_BACKGROUND_COLOR, false);
    private static final CssMetaData<AnchorSelector, Color>       ANCHOR_COLOR                  = FACTORY.createColorCssMetaData("-anchor-color", s -> s.anchorColor, DEFAULT_ANCHOR_COLOR, false);
    private static final CssMetaData<AnchorSelector, Color>       SELECTED_ANCHOR_COLOR         = FACTORY.createColorCssMetaData("-selected-anchor-color", s -> s.selectedAnchorColor, DEFAULT_SELECTED_ANCHOR_COLOR, false);
    private              StyleableProperty<Color>                 backgroundColor;
    private              StyleableProperty<Color>                 anchorColor;
    private              StyleableProperty<Color>                 selectedAnchorColor;
    private              double                                   size;
    private              double                                   width;
    private              double                                   height;
    private              double                                   anchorSize;
    private              double                                   selectedAnchorSize;
    private              Rectangle                                background;
    private              Anchor                                   topLeft;
    private              Anchor                                   topCenter;
    private              Anchor                                   topRight;
    private              Anchor                                   centerRight;
    private              Anchor                                   bottomRight;
    private              Anchor                                   bottomCenter;
    private              Anchor                                   bottomLeft;
    private              Anchor                                   centerLeft;
    private              Anchor                                   center;
    private              Pos                                      _selectedAnchor;
    private              ObjectProperty<Pos>                      selectedAnchor;
    private              Pane                                     pane;
    private              EventHandler<MouseEvent>                 mouseHandler;


    // ******************** Constructors **************************************
    public AnchorSelector() {
        anchorSize         = PREFERRED_WIDTH * 0.125;
        selectedAnchorSize = PREFERRED_WIDTH * 0.1875;
        _selectedAnchor    = Pos.TOP_LEFT;
        mouseHandler       = e -> selectAnchor((Anchor) e.getSource());
        initGraphics();
        registerListeners();
    }


    // ******************** Initialization ************************************
    private void initGraphics() {
        if (Double.compare(getPrefWidth(), 0.0) <= 0 || Double.compare(getPrefHeight(), 0.0) <= 0 || Double.compare(getWidth(), 0.0) <= 0 ||
            Double.compare(getHeight(), 0.0) <= 0) {
            if (getPrefWidth() > 0 && getPrefHeight() > 0) {
                setPrefSize(getPrefWidth(), getPrefHeight());
            } else {
                setPrefSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
            }
        }

        getStyleClass().add("anchor-selector");

        background = new Rectangle(56, 56);
        background.getStyleClass().add("background");

        topLeft = new Anchor(Pos.TOP_LEFT, 8, 8);
        topLeft.getStyleClass().add("anchor");
        topLeft.setActive(true);

        topCenter = new Anchor(Pos.TOP_CENTER, 8, 8);
        topCenter.getStyleClass().add("anchor");

        topRight = new Anchor(Pos.TOP_RIGHT, 8, 8);
        topRight.getStyleClass().add("anchor");

        centerRight = new Anchor(Pos.CENTER_RIGHT, 8, 8);
        centerRight.getStyleClass().add("anchor");

        bottomRight = new Anchor(Pos.BOTTOM_RIGHT, 8, 8);
        bottomRight.getStyleClass().add("anchor");

        bottomCenter = new Anchor(Pos.BOTTOM_CENTER, 8, 8);
        bottomCenter.getStyleClass().add("anchor");

        bottomLeft = new Anchor(Pos.BOTTOM_LEFT, 8, 8);
        bottomLeft.getStyleClass().add("anchor");

        centerLeft = new Anchor(Pos.CENTER_LEFT, 8, 8);
        centerLeft.getStyleClass().add("anchor");

        center = new Anchor(Pos.CENTER, 8, 8);
        center.getStyleClass().add("anchor");

        pane = new Pane(background, topLeft, topCenter, topRight, centerRight, bottomRight, bottomCenter, bottomLeft, centerLeft, center);

        getChildren().setAll(pane);
    }

    private void registerListeners() {
        widthProperty().addListener(o -> resize());
        heightProperty().addListener(o -> resize());
        topLeft.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseHandler);
        topCenter.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseHandler);
        topRight.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseHandler);
        centerRight.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseHandler);
        bottomRight.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseHandler);
        bottomCenter.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseHandler);
        bottomLeft.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseHandler);
        centerLeft.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseHandler);
        center.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseHandler);
    }


    // ******************** Methods *******************************************
    @Override public void layoutChildren() {
        super.layoutChildren();
    }

    @Override protected double computeMinWidth(final double HEIGHT) { return MINIMUM_WIDTH; }
    @Override protected double computeMinHeight(final double WIDTH) { return MINIMUM_HEIGHT; }
    @Override protected double computePrefWidth(final double HEIGHT) { return super.computePrefWidth(HEIGHT); }
    @Override protected double computePrefHeight(final double WIDTH) { return super.computePrefHeight(WIDTH); }
    @Override protected double computeMaxWidth(final double HEIGHT) { return MAXIMUM_WIDTH; }
    @Override protected double computeMaxHeight(final double WIDTH) { return MAXIMUM_HEIGHT; }

    @Override public ObservableList<Node> getChildren() { return super.getChildren(); }

    public Pos getSelectedAnchor() { return null == selectedAnchor ? _selectedAnchor : selectedAnchor.get(); }
    private void setSelectedAnchor(final Anchor ANCHOR) {
        if (null == selectedAnchor) {
            _selectedAnchor = ANCHOR.getPosition();
        } else {
            selectedAnchor.set(ANCHOR.getPosition());
        }
    }
    public ReadOnlyObjectProperty<Pos> selectedAnchorProperty() {
        if (null == selectedAnchor) {
            selectedAnchor = new ObjectPropertyBase<Pos>(_selectedAnchor) {
                @Override public Object getBean() { return AnchorSelector.this; }
                @Override public String getName() { return "selectedAnchor"; }
            };
            _selectedAnchor = null;
        }
        return selectedAnchor;
    }

    public Color getBackgroundColor() { return backgroundColorProperty().getValue(); }
    public void setBackgroundColor(final Color COLOR) { backgroundColorProperty().setValue(COLOR); }
    public StyleableProperty<Color> backgroundColorProperty() {
        if (null == backgroundColor) {
            backgroundColor = new StyleableObjectProperty<Color>(DEFAULT_BACKGROUND_COLOR) {
                @Override protected void invalidated() { setStyle(); }
                @Override public Object getBean() { return AnchorSelector.this; }
                @Override public String getName() { return "backgroundColor"; }
                @Override public CssMetaData<? extends Styleable, Color> getCssMetaData() { return BACKGROUND_COLOR; }
            };
        }
        return backgroundColor;
    }
    
    public Color getAnchorColor() { return anchorColorProperty().getValue(); }
    public void setAnchorColor(final Color COLOR) { anchorColorProperty().setValue(COLOR); }
    public StyleableProperty<Color> anchorColorProperty() {
        if (null == anchorColor) {
            anchorColor = new StyleableObjectProperty<Color>(DEFAULT_ANCHOR_COLOR) {
                @Override protected void invalidated() { setStyle(); }
                @Override public Object getBean() { return AnchorSelector.this; }
                @Override public String getName() { return "anchorColor"; }
                @Override public CssMetaData<? extends Styleable, Color> getCssMetaData() { return ANCHOR_COLOR; }
            };
        }
        return anchorColor;
    }

    public Color getSelectedAnchorColor() { return selectedAnchorColorProperty().getValue(); }
    public void setSelectedAnchorColor(final Color COLOR) { selectedAnchorColorProperty().setValue(COLOR); }
    public StyleableProperty<Color> selectedAnchorColorProperty() {
        if (null == selectedAnchorColor) {
            selectedAnchorColor = new StyleableObjectProperty<Color>(DEFAULT_SELECTED_ANCHOR_COLOR) {
                @Override protected void invalidated() { setStyle(); }
                @Override public Object getBean() { return AnchorSelector.this; }
                @Override public String getName() { return "selectedAnchorColor"; }
                @Override public CssMetaData<? extends Styleable, Color> getCssMetaData() { return SELECTED_ANCHOR_COLOR; }
            };
        }
        return selectedAnchorColor;
    }

    public void dispose() {
        topLeft.removeEventHandler(MouseEvent.MOUSE_PRESSED, mouseHandler);
        topCenter.removeEventHandler(MouseEvent.MOUSE_PRESSED, mouseHandler);
        topRight.removeEventHandler(MouseEvent.MOUSE_PRESSED, mouseHandler);
        centerRight.removeEventHandler(MouseEvent.MOUSE_PRESSED, mouseHandler);
        bottomRight.removeEventHandler(MouseEvent.MOUSE_PRESSED, mouseHandler);
        bottomCenter.removeEventHandler(MouseEvent.MOUSE_PRESSED, mouseHandler);
        bottomLeft.removeEventHandler(MouseEvent.MOUSE_PRESSED, mouseHandler);
        centerLeft.removeEventHandler(MouseEvent.MOUSE_PRESSED, mouseHandler);
        center.removeEventHandler(MouseEvent.MOUSE_PRESSED, mouseHandler);
    }

    private void selectAnchor(final Anchor ANCHOR) {
        topLeft.setActive(false);
        topCenter.setActive(false);
        topRight.setActive(false);
        centerRight.setActive(false);
        bottomRight.setActive(false);
        bottomCenter.setActive(false);
        bottomLeft.setActive(false);
        centerLeft.setActive(false);
        center.setActive(false);
        ANCHOR.setActive(true);
        setSelectedAnchor(ANCHOR);
        resizeAnchors();
    }

    private void setStyle() {
        StringBuilder style = new StringBuilder().append("-background-color: ").append(colorToCSS(getBackgroundColor())).append(";")
                                                 .append("-anchor-color: ").append(colorToCSS(getAnchorColor())).append(";")
                                                 .append("-selected-anchor-color: ").append(colorToCSS(getSelectedAnchorColor())).append(";");
        setStyle(style.toString());
    }

    private String colorToCSS(final Color COLOR) { return COLOR.toString().replace("0x", "#"); }


    // ******************** Style related *************************************
    @Override public String getUserAgentStylesheet() {
        return AnchorSelector.class.getResource("anchorselector.css").toExternalForm();
    }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() { return FACTORY.getCssMetaData(); }
    @Override public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() { return FACTORY.getCssMetaData(); }


    // ******************** Resizing ******************************************
    private void resize() {
        width = getWidth() - getInsets().getLeft() - getInsets().getRight();
        height = getHeight() - getInsets().getTop() - getInsets().getBottom();
        size = width < height ? width : height;

        if (width > 0 && height > 0) {
            pane.setMaxSize(size, size);
            pane.setPrefSize(size, size);
            pane.relocate((getWidth() - size) * 0.5, (getHeight() - size) * 0.5);

            background.setWidth(size * 0.84375);
            background.setHeight(size * 0.84375);
            background.relocate((size - background.getWidth()) * 0.5, (size - background.getHeight()) * 0.5);

            anchorSize         = size * 0.125;
            selectedAnchorSize = size * 0.15625;
            
            resizeAnchors();
        }
    }

    private void resizeAnchors() {
        Pos selectedAnchor = getSelectedAnchor();

        double inset1Px = size * 0.015625;
        if (Pos.TOP_LEFT == selectedAnchor) {
            topLeft.setWidth(selectedAnchorSize);
            topLeft.setHeight(selectedAnchorSize);
            topLeft.relocate(0, 0);
        } else {
            topLeft.setWidth(anchorSize);
            topLeft.setHeight(anchorSize);
            topLeft.relocate(inset1Px, inset1Px);
        }

        if (Pos.TOP_CENTER == selectedAnchor) {
            topCenter.setWidth(selectedAnchorSize);
            topCenter.setHeight(selectedAnchorSize);
            topCenter.relocate((size - selectedAnchorSize) * 0.5, 0);
        } else {
            topCenter.setWidth(anchorSize);
            topCenter.setHeight(anchorSize);
            topCenter.relocate((size - anchorSize) * 0.5, inset1Px);
        }

        if (Pos.TOP_RIGHT == selectedAnchor) {
            topRight.setWidth(selectedAnchorSize);
            topRight.setHeight(selectedAnchorSize);
            topRight.relocate((size - selectedAnchorSize), 0);
        } else {
            topRight.setWidth(anchorSize);
            topRight.setHeight(anchorSize);
            topRight.relocate((size - anchorSize - inset1Px), inset1Px);
        }

        if (Pos.CENTER_RIGHT == selectedAnchor) {
            centerRight.setWidth(selectedAnchorSize);
            centerRight.setHeight(selectedAnchorSize);
            centerRight.relocate((size - selectedAnchorSize), (size - selectedAnchorSize) * 0.5);
        } else {
            centerRight.setWidth(anchorSize);
            centerRight.setHeight(anchorSize);
            centerRight.relocate((size - anchorSize - inset1Px), (size - anchorSize) * 0.5);
        }


        if (Pos.BOTTOM_RIGHT == selectedAnchor) {
            bottomRight.setWidth(selectedAnchorSize);
            bottomRight.setHeight(selectedAnchorSize);
            bottomRight.relocate((size - selectedAnchorSize), size - selectedAnchorSize);
        } else {
            bottomRight.setWidth(anchorSize);
            bottomRight.setHeight(anchorSize);
            bottomRight.relocate((size - anchorSize - inset1Px), size - anchorSize - inset1Px);
        }

        if (Pos.BOTTOM_CENTER == selectedAnchor) {
            bottomCenter.setWidth(selectedAnchorSize);
            bottomCenter.setHeight(selectedAnchorSize);
            bottomCenter.relocate((size - selectedAnchorSize) * 0.5, size - selectedAnchorSize);
        } else {
            bottomCenter.setWidth(anchorSize);
            bottomCenter.setHeight(anchorSize);
            bottomCenter.relocate((size - anchorSize) * 0.5, size - anchorSize - inset1Px);
        }

        if (Pos.BOTTOM_LEFT == selectedAnchor) {
            bottomLeft.setWidth(selectedAnchorSize);
            bottomLeft.setHeight(selectedAnchorSize);
            bottomLeft.relocate(0, size - selectedAnchorSize);
        } else {
            bottomLeft.setWidth(anchorSize);
            bottomLeft.setHeight(anchorSize);
            bottomLeft.relocate(inset1Px, size - anchorSize - inset1Px);
        }

        if (Pos.CENTER_LEFT == selectedAnchor) {
            centerLeft.setWidth(selectedAnchorSize);
            centerLeft.setHeight(selectedAnchorSize);
            centerLeft.relocate(0, (size - selectedAnchorSize) * 0.5);
        } else {
            centerLeft.setWidth(anchorSize);
            centerLeft.setHeight(anchorSize);
            centerLeft.relocate(inset1Px, (size - anchorSize) * 0.5);
        }

        if (Pos.CENTER == selectedAnchor) {
            center.setWidth(selectedAnchorSize);
            center.setHeight(selectedAnchorSize);
            center.relocate((size - selectedAnchorSize) * 0.5, (size - selectedAnchorSize) * 0.5);
        } else {
            center.setWidth(anchorSize);
            center.setHeight(anchorSize);
            center.relocate((size - anchorSize) * 0.5, (size - anchorSize) * 0.5);
        }
    }
}
