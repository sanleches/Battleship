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

        // File menu - new, save, print, and exit
        Menu fileMenu = new Menu("File");
        MenuItem newMenuItem = new MenuItem("New");
        MenuItem saveMenuItem = new MenuItem("Save");
        MenuItem printMenuItem = new MenuItem("Print");
        MenuItem exitMenuItem = new MenuItem("Exit");

        fileMenu.getItems().addAll(newMenuItem, saveMenuItem, printMenuItem, new SeparatorMenuItem(), exitMenuItem);
        
        exitMenuItem.setOnAction(actionEvent -> Platform.exit());

        // Language menu - check menu items
        Menu languageMenu = new Menu("Language");
        CheckMenuItem javaMenuItem = new CheckMenuItem("Java");
        CheckMenuItem pythonMenuItem = new CheckMenuItem("Python");
        CheckMenuItem htmlMenuItem = new CheckMenuItem("HTML");
        CheckMenuItem FXMenuItem = new CheckMenuItem("JavaFX");
        FXMenuItem.setSelected(true);
        languageMenu.getItems().addAll(javaMenuItem, pythonMenuItem, htmlMenuItem, new SeparatorMenuItem(), FXMenuItem);

        // Tutorial menu - nested menu items
        Menu tutorialMenu = new Menu("Tutorial");
        tutorialMenu.getItems().addAll(
            new MenuItem("Buttons"),
            new MenuItem("Menus"),
            new MenuItem("Images"));

        languageMenu.getItems().add(tutorialMenu);

        menuBar.getMenus().addAll(fileMenu, languageMenu);
        return menuBar;
    }
}
