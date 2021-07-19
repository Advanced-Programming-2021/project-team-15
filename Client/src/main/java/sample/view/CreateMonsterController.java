package sample.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import sample.model.cards.MonsterCard;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CreateMonsterController implements Initializable {
    public ImageView cardImage;
    public Label cardImageLBL;
    public Button backButton;
    public ChoiceBox<String> attributesBox;
    public ChoiceBox<String> typesOfMonster;
    public Spinner<Integer> attackPower;
    public Spinner<Integer> defensePower;
    public Button calculateButton;
    public CheckBox increaseDefenseEffect;
    public CheckBox increaseAttackEffect;
    public CheckBox increaseLPEffect;
    public CheckBox decreaseLPEffect;
    public CheckBox killAllMonsterEffect;
    public CheckBox killHighestLevelMonsterEffect;
    public CheckBox[] effects;
    public Label levelLBL;
    public Label priceLBL;
    public Label cardTypeLBL;
    public TextField nameTextField;
    public TextArea descriptionOfCard;
    public Button createButton;
    private Stage stage;
    private String path;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setSpinner();
        setAttributes();
        setTypesOfMonster();

        cardImage.requestFocus();
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

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
            }
        });

        calculateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                calculate();
            }
        });

        createButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    createMonster();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void handleDropImage(DragEvent dragEvent) {
        List<File> files = dragEvent.getDragboard().getFiles();
        Image image = new Image(String.valueOf(files.get(files.size() - 1).toURI()));
        cardImage.setImage(image);
        path = cardImage.getImage().getUrl();
        cardImageLBL.setText("");
    }

    public void handleDragOverImage(DragEvent dragEvent) {
        if (dragEvent.getDragboard().hasFiles())
            dragEvent.acceptTransferModes(TransferMode.ANY);
    }

    public void setSpinner() {
        SpinnerValueFactory<Integer> valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(100, 4000);
        valueFactory1.setValue(100);
        attackPower.setValueFactory(valueFactory1);

        SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(100, 4000);
        valueFactory2.setValue(100);
        defensePower.setValueFactory(valueFactory2);
    }

    public void setAttributes() {
        String[] attributes = {"DARK", "EARTH", "FIRE", "LIGHT", "WATER", "WIND"};
        attributesBox.getItems().addAll(attributes);
        attributesBox.setValue("DARK");
    }

    public void setTypesOfMonster() {
        String[] monsterTypes = {"Beast-Warrior", "Warrior", "Fiend", "Aqua", "Beast", "Pyro", "Spellcaster", "Thunder",
                "Dragon", "Machine", "Rock", "Insect", "Cyberse", "Fairy", "Sea Serpent"};
        typesOfMonster.getItems().addAll(monsterTypes);
        typesOfMonster.setValue("Beast-Warrior");
    }

    public void calculate() {
        effects = new CheckBox[]{increaseAttackEffect, increaseDefenseEffect, increaseLPEffect, decreaseLPEffect, killAllMonsterEffect,
                killHighestLevelMonsterEffect};
        calculateLevelOfMonster(effects);
        calculatePriceOfMonster(effects);
        calculateCardTypeOfMonster(effects);
    }

    public Integer calculateLevelOfMonster(CheckBox[] effects) {
        int level = 1;
        if (attackPower.getValue() + defensePower.getValue() >= 500) {
            level += (attackPower.getValue() + defensePower.getValue()) / 500;
        }
        for (CheckBox effect : effects) {
            if (effect.isSelected()) {
                level++;
            }
        }

        levelLBL.setText(String.valueOf(level));
        return Integer.parseInt(levelLBL.getText());
    }

    public String calculateCardTypeOfMonster(CheckBox[] effects) {
        boolean effectiveCard = false;

        for (CheckBox effect : effects) {
            if (effect.isSelected()) {
                effectiveCard = true;
            }
        }
        if (effectiveCard)
            cardTypeLBL.setText("Effect");
        else
            cardTypeLBL.setText("Normal");
        return cardTypeLBL.getText();
    }

    public String calculatePriceOfMonster(CheckBox[] effects) {
        int price = 0;
        price += (attackPower.getValue() * 2 + defensePower.getValue()) / 3;

        for (CheckBox effect : effects) {
            if (effect.isSelected())
                price += 500;
        }
        priceLBL.setText(String.valueOf(price));
        return String.valueOf(price);
    }

    public void createMonster() throws IOException {
        effects = new CheckBox[]{increaseAttackEffect, increaseDefenseEffect, increaseLPEffect, decreaseLPEffect, killAllMonsterEffect,
                killHighestLevelMonsterEffect};
        String error = errorCheck(effects);

        if (!error.equals("")) {
            UtilityController.makeAlert("Error!!", "Na Da!", error, new Image(String.valueOf(getClass().
                    getResource("/Images/AngryAnimeGirl.jpg"))));
            return;
        } else
            calculate();
        MonsterCard monsterCard = new MonsterCard(descriptionOfCard.getText(), nameTextField.getText(), "0",
                MonsterCard.CardType.MONSTER);
        monsterCard.setLevel(Integer.parseInt(levelLBL.getText()));
        monsterCard.setMonsterAttribute(MonsterCard.MonsterAttribute.getAttribute(attributesBox.getValue()));
        monsterCard.setMonsterType(MonsterCard.MonsterType.getMonsterTypeByName(typesOfMonster.getValue()));
        monsterCard.setMonsterEffectType(MonsterCard.MonsterEffectType.getMonsterEffectType("Normal"));
        monsterCard.setAttackPoint(attackPower.getValue());
        monsterCard.setDefensePoint(defensePower.getValue());
        monsterCard.setPrice(Integer.parseInt(priceLBL.getText()));
        monsterCard.setGameATK(monsterCard.getAttackPoint());
        monsterCard.setGameDEF(monsterCard.getDefensePoint());
        monsterCard.setMode(MonsterCard.Mode.DEFENSE);
        Card.addCard(monsterCard);
        DatabaseController.getInstance().serializeCard(monsterCard);
        MenuController.getUser().setMoney(MenuController.getUser().getMoney()
                - monsterCard.getPrice() / 10);
        System.out.println(MonsterCard.getCardByName(nameTextField.getText()));
        UtilityController.makeAlert("Happy!!", "Good job!", "Card created successfully", new Image(String.valueOf(getClass().
                getResource("/Images/okAnimeGirl.png"))));
    }

    public String errorCheck(CheckBox[] effects) {
        if (nameTextField.getText().equals(""))
            return "You should choose a name for your card";
        if (path == null)
            return "You should choose an image for your card";
        if (Integer.parseInt(calculatePriceOfMonster(effects)) > MenuController.getUser().getMoney())
            return "You don't have enough money for create card!";
        return "";
    }

}