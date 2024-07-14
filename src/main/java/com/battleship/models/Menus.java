package com.battleship.models;

import javafx.application.Platform;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

public class Menus {

    public MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        //  menu - new, save, print, and exit
        Menu settingsMenu = new Menu(" Settings ");
        MenuItem newMenuItem = new MenuItem("New");
        MenuItem saveMenuItem = new MenuItem("Save");
        MenuItem SetupScreenMenuItem = new MenuItem("Return to Setup Screen");
        MenuItem exitMenuItem = new MenuItem("Exit");

        settingsMenu.getItems().addAll(newMenuItem, saveMenuItem, SetupScreenMenuItem, new SeparatorMenuItem(), exitMenuItem);
        
        exitMenuItem.setOnAction(actionEvent -> Platform.exit());
        // SetupScreenMenuItem.setOnAction(actionEvent -> {
        //     try {
        //         App.switchToInitialScene(App.initialSetupRoot);
        //     } catch (IOException e) {

        //     }
        // });

        // Language menu - check menu items
        Menu languageMenu = new Menu("Language");
        CheckMenuItem langEnMenuItem = new CheckMenuItem("English");
        CheckMenuItem LangEspMenuItem = new CheckMenuItem("Espanol");
        languageMenu.getItems().addAll(langEnMenuItem, LangEspMenuItem);

        // // Tutorial menu - nested menu items
        // Menu tutorialMenu = new Menu("Tutorial");
        // tutorialMenu.getItems().addAll(
        //     new MenuItem("Buttons"),
        //     new MenuItem("Menus"),
        //     new MenuItem("Images"));

        // languageMenu.getItems().add(tutorialMenu);

        menuBar.getMenus().addAll(settingsMenu, languageMenu);
        return menuBar;
    }
}
