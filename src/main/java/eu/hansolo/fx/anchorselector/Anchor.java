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

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.shape.Rectangle;


public class Anchor extends Rectangle {
    private static final PseudoClass         ACTIVE_PSEUDO_CLASS = PseudoClass.getPseudoClass("active");
    private              Pos                 _position;
    private              ObjectProperty<Pos> position;
    private              boolean             _active;
    private              BooleanProperty     active;


    // ******************** Constructors **************************************
    public Anchor(final Pos POS) {
        super();
        setPos(POS);
        _active = false;
    }
    public Anchor(final Pos POS, final double WIDTH, final double HEIGHT) {
        super(WIDTH, HEIGHT);
        setPos(POS);
        _active = false;
    }
    public Anchor(final Pos POS, final double X, final double Y, final double WIDTH, final double HEIGHT) {
        super(X, Y, WIDTH, HEIGHT);
        setPos(POS);
        _active = false;
    }


    // ******************** Methods *******************************************
    public Pos getPosition() { return null == position ? _position : position.get(); }
    public void setPos(final Pos POS) {
        if (!isValidPos(POS)) { throw new IllegalArgumentException("Not possible to set given _position"); }
        if (null == position) {
            _position = POS;
        } else {
            position.set(POS);
        }
    }
    public ObjectProperty<Pos> positionProperty() {
        if (null == position) {
            position = new ObjectPropertyBase<Pos>(_position) {
                @Override public Object getBean() { return Anchor.this; }
                @Override public String getName() { return "position"; }
            };
            _position = null;
        }
        return position;
    }

    public final boolean isActive() {
        return null == active ? _active : active.get();
    }
    public final void setActive(final boolean ACTIVE) {
        if (null == active) {
            _active = ACTIVE;
            pseudoClassStateChanged(ACTIVE_PSEUDO_CLASS, ACTIVE);
        } else {
            active.set(ACTIVE);
        }
    }
    public final BooleanProperty activeProperty() {
        if (null == active) {
            active = new BooleanPropertyBase() {
                @Override protected void invalidated() {
                    pseudoClassStateChanged(ACTIVE_PSEUDO_CLASS, get());
                }
                @Override public Object getBean() { return Anchor.this; }
                @Override public String getName() { return "active"; }
            };
        }
        return active;
    }

    private boolean isValidPos(final Pos POS) {
        return (Pos.TOP_LEFT == POS || Pos.TOP_CENTER == POS || Pos.TOP_RIGHT == POS ||
                Pos.CENTER_LEFT == POS || Pos.CENTER == POS || Pos.CENTER_RIGHT == POS ||
                Pos.BOTTOM_RIGHT == POS || Pos.BOTTOM_CENTER == POS || Pos.BOTTOM_LEFT == POS);
    }
}
