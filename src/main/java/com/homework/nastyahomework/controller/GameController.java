package com.homework.nastyahomework.controller;

import com.homework.nastyahomework.model.Game;
import com.homework.nastyahomework.model.RecordTable;
import com.homework.nastyahomework.view.AlphabetButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    public static GameController instance = null;

    private final RecordTable recordTable = new RecordTable();

    private Game currentGame;

    @FXML
    private GridPane alphabetPane;

    @FXML
    private GridPane wordPane;

    @FXML
    private Label score;

    @FXML
    private ListView<RecordTable.RecordPair> recordList;

    @FXML
    private Menu newGameMenu;

    @FXML
    private VBox mainPane;

    @FXML
    private void themeChangeShow() throws IOException {
        ThemeChangeController.showThemeChangeScene();
    }

    @FXML
    private void setCustomBackground() throws URISyntaxException {
        InputStream resource = Thread.currentThread().getContextClassLoader().getResourceAsStream("custom_background.jpeg");
        BackgroundImage myBI = new BackgroundImage(new Image(resource),
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        mainPane.setBackground(new Background(myBI));
    }

    @FXML
    private void setWhiteBackground() throws URISyntaxException {
        mainPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public void updateNewGameMenu() {
        newGameMenu.getItems().clear();
        for(String theme: Game.words.keySet()) {
            if(Game.words.get(theme).size() == 0) continue;
            MenuItem menuItem = new MenuItem();
            menuItem.setText(theme);
            menuItem.setOnAction((ignored) -> {
                startGameWithTheme(theme);
            });
            newGameMenu.getItems().add(menuItem);
        }
    }

    private void startGameWithTheme(String type) {
        // Инициализация алфавита
        alphabetPane.getChildren().clear();
        alphabetPane.setDisable(false);
        for(int i = 0; i < 32; i++) {
            var character = String.valueOf((char)('А' + i));
            var button = new AlphabetButton(character, (ignored) -> {
                tryCharacter(character);
            });
            alphabetPane.add(button, i % 11, i / 11);
            GridPane.setFillWidth(button, true);
            GridPane.setFillHeight(button, true);
        }

        //Инициализация слова
        wordPane.getChildren().clear();
        RowConstraints row = new RowConstraints();
        wordPane.getRowConstraints().add(row);

        currentGame = new Game(type);
        for(int i = 0; i < currentGame.getWord().length(); i++) {
            ColumnConstraints column = new ColumnConstraints();
            wordPane.getColumnConstraints().add(column);
            var characterNode = new Label();
            characterNode.setMinWidth(40);
            characterNode.setMinHeight(40);
            characterNode.setPadding(new Insets(0, 10, 0, 10));
            characterNode.setBorder(new Border(new BorderStroke(Color.BLACK,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            characterNode.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            wordPane.add(characterNode, i, 0);
        }

        score.setText(String.valueOf(currentGame.getScore()));
    }

    public void tryCharacter(String character) {
        Integer[] indices = currentGame.tryCharacter(character);
        if(indices[0] >= 0) {
            for(Integer index: indices) {
                var characterNode = (Label) wordPane.getChildren().get(index);
                characterNode.setText(String.valueOf(currentGame.getWord().charAt(index)));
            }
        }
        score.setText(String.valueOf(currentGame.getScore()));

        if(currentGame.getWonStatus()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Уведомление");
            alert.setHeaderText(currentGame.getWord());
            alert.setContentText("Вы выиграли со счетом: " + String.valueOf(currentGame.getScore()));

            alert.showAndWait();
            alphabetPane.setDisable(true);

            recordTable.addResult(currentGame.getScore(), currentGame.getWord());
            recordList.setItems(recordTable.getRecords());
        } else if(currentGame.getLoseStatus()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Уведомление");
            alert.setHeaderText(currentGame.getWord());
            alert.setContentText("Вы проиграли");

            alert.showAndWait();
            alphabetPane.setDisable(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateNewGameMenu();
        try {
            setWhiteBackground();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        GameController.instance = this;
    }
}
