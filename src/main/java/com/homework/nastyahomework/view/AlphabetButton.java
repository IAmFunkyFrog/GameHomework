package com.homework.nastyahomework.view;

import com.homework.nastyahomework.controller.GameController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import java.util.function.Function;

public class AlphabetButton extends Button {

    private final String character;

    public AlphabetButton(String character, EventHandler<ActionEvent> onClick) {
        this.setMaxWidth(Double.MAX_VALUE);
        this.setMaxHeight(Double.MAX_VALUE);
        this.character = character;
        this.setText(character);
        this.setOnAction((event) -> {
            this.setDisable(true);
            onClick.handle(event);
        });
    }

}
