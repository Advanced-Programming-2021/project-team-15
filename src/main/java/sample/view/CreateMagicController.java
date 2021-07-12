package sample.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;
import sample.controller.menuController.MenuController;
import sample.controller.utilizationController.DatabaseController;
import sample.controller.utilizationController.UtilityController;
import sample.model.cards.Card;
import sample.model.cards.MagicCard;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CreateMagicController implements Initializable {

    public ImageView cardImage;
    public ChoiceBox<String> icons;
    public CheckBox killMonsterEffect;
    public CheckBox killSpellEffect;
    public CheckBox increaseLPEffect;
    public CheckBox decreaseLPEffect;
    public Button calculateButton;
    public ToggleButton trapButton;
    public ToggleButton unlimitedButton;
    public CheckBox[] effects;
    public Label priceLBL;
    public Button backButton;
    public Button createButton;
    public TextField nameTextField;
    public TextArea cardDescription;
    @FXML
    private Label dragLBL;

    private Stage stage;
    private String path;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setIcons();
        cardImage.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                handleDropImage(event);
            }
        });

        cardImage.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                handleDragOverImage(event);
            }
        });

        calculateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                calculate();
            }
        });

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
            }
        });

        createButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    createCard();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void calculate() {
        effects = new CheckBox[]{killMonsterEffect, killSpellEffect, increaseLPEffect, decreaseLPEffect};
        calculatePriceOfSpellAndTrapCard(effects);
    }

    public String calculatePriceOfSpellAndTrapCard(CheckBox[] effects) {
        int price = 0;
        if (trapButton.isSelected()) {
            price += 800;
        }
        if (unlimitedButton.isSelected()) {
            price += 300;
        }
        for (CheckBox effect : effects) {
            if (effect.isSelected())
                price += 500;
        }

        priceLBL.setText(String.valueOf(price));
        return String.valueOf(price);
    }

    public void createCard() throws IOException {
        calculate();
        effects = new CheckBox[]{killMonsterEffect, killSpellEffect, increaseLPEffect, decreaseLPEffect};
        String error = errorCheck(effects);

        if (!error.equals("")) {
            UtilityController.makeAlert("Error!!", "Na Da!", error, new Image(String.valueOf(getClass().
                    getResource("/Images/AngryAnimeGirl.jpg"))));
        } else {
            calculate();
            MagicCard magicCard = new MagicCard(cardDescription.getText(), nameTextField.getText(), "0", Card.CardType.MAGIC);
            magicCard.setCardIcon(MagicCard.CardIcon.getCardIcon(icons.getValue()));
            magicCard.setPrice(Integer.parseInt(priceLBL.getText()));
            if (unlimitedButton.isSelected()) magicCard.setStatus(MagicCard.Status.UNLIMITED);
            else magicCard.setStatus(MagicCard.Status.LIMITED);
            if (trapButton.isSelected()) magicCard.setMagicType(MagicCard.MagicType.TRAP);
            else magicCard.setMagicType(MagicCard.MagicType.SPELL);
            Card.addCard(magicCard);
            DatabaseController.getInstance().serializeCard(magicCard);
            MenuController.getUser().setMoney(MenuController.getUser().getMoney()
                    - magicCard.getPrice() / 10);
            UtilityController.makeAlert("Happy!!", "Good job!", "Card created successfully", new Image(String.valueOf(getClass().
                    getResource("/Images/okAnimeGirl.png"))));
        }
    }

    public String errorCheck(CheckBox[] effects) {
        if (nameTextField.getText().equals(""))
            return "You should choose a name for your card";
        if (cardImage.getImage() == null)
            return "You should choose an image for your card";
        if (Integer.parseInt(calculatePriceOfSpellAndTrapCard(effects)) > MenuController.getUser().getMoney())
            return "You don't have enough money for create card!";
        return "";
    }

    public void handleDropImage(DragEvent dragEvent) {
        List<File> files = dragEvent.getDragboard().getFiles();
        try {
            Image image = new Image(new FileInputStream(files.get(files.size() - 1)));
            cardImage.setImage(image);
            dragLBL.setText("");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void handleDragOverImage(DragEvent dragEvent) {
        if (dragEvent.getDragboard().hasFiles())
            dragEvent.acceptTransferModes(TransferMode.ANY);
    }

    public void setIcons() {
        String[] icons1 = {"Equip", "Quick-Play", "Ritual", "Continuous", "Counter", "Normal"};
        icons.getItems().addAll(icons1);
        icons.setValue("Equip");
    }

}