package com.homework.nastyahomework.controller;

import com.homework.nastyahomework.GameApplication;
import com.homework.nastyahomework.model.Game;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ThemeChangeController implements Initializable {

    private static final String ruWordRegex = "[-А-я]+";
    private static final Pattern ruWordPattern = Pattern.compile(ruWordRegex);

    @FXML
    private ListView<String> themeListView;

    @FXML
    private ListView<String> wordListView;

    @FXML
    private TextField newThemeInput;

    @FXML
    private TextField newWordInput;

    @FXML
    private void changeTheme() {
        String theme = themeListView.getSelectionModel().getSelectedItem();
        ObservableList<String> words = FXCollections.observableArrayList();
        words.addAll(Game.words.get(theme));
        wordListView.setItems(words);
        wordListView.getSelectionModel().selectFirst();
    }

    @FXML
    private void removeTheme() {
        Game.words.remove(themeListView.getSelectionModel().getSelectedItem());

        reloadThemesChanger();
    }

    @FXML
    private void addTheme() {
        if(!ruWordPattern.matcher(newThemeInput.getText()).matches()) return;

        Game.words.put(newThemeInput.getText(), new ArrayList<>());

        reloadThemesChanger();
    }

    @FXML
    private void removeWord() {
        ArrayList<String> words = Game.words.get(themeListView.getSelectionModel().getSelectedItem());
        words.remove(wordListView.getSelectionModel().getSelectedItem());

        reloadThemesChanger();
    }

    @FXML
    private void addWord() {
        if(!ruWordPattern.matcher(newWordInput.getText()).matches()) return;

        ArrayList<String> words = Game.words.get(themeListView.getSelectionModel().getSelectedItem());
        words.add(newWordInput.getText());

        reloadThemesChanger();
    }

    static public void showThemeChangeScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource("theme-change-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 400);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reloadThemesChanger();
    }

    private void reloadThemesChanger() {
        ObservableList<String> themes = FXCollections.observableArrayList();
        for(Map.Entry<String, ArrayList<String>> themesAndWords: Game.words.entrySet()) {
            themes.add(themesAndWords.getKey());
        }
        themeListView.setItems(themes);
        themeListView.getSelectionModel().selectFirst();

        GameController.instance.updateNewGameMenu();
        changeTheme();
    }
}
