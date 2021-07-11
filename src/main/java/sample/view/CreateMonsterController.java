package sample.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.controller.menuController.MenuController;
import sample.model.User;
import sample.model.cards.Card;
import sample.model.cards.MonsterCard;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CreateMonsterController implements Initializable {
    public ImageView cardImage;
    public Label cardImageLBL;
    public Button back;
    public ChoiceBox<String> attributesBox;
    public ChoiceBox<String> typesOfMonster;
    public Spinner<Integer> attackPower;
    public Spinner<Integer> defensePower;
    public Button calculateBTN;
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
    public Label errorLBL;
    public TextArea descriptionOfCard;
    public Button createBTN;


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

        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) back.getScene().getWindow();
                URL url = null;
                try {
                    url = new File("src/main/java/FXMLFiles/CreateCard.fxml").toURI().toURL();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                Parent root = null;
                try {
                    assert url != null;
                    root = FXMLLoader.load(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stage.setTitle("CreateCard");
                assert root != null;
                stage.setScene(new Scene(root, 1920, 1150));
                stage.show();
            }
        });

        calculateBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                calculate();
            }
        });

        createBTN.setOnAction(new EventHandler<ActionEvent>() {
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
        try {
            Image image = new Image(new FileInputStream(files.get(files.size() - 1)));
            cardImage.setImage(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
        price += (attackPower.getValue()*2 + defensePower.getValue()) / 3;

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
            errorLBL.setText(error);
            errorLBL.setTextFill(Color.RED);
            return;
        } else
            calculate();

       // File outputFile = new File("src/main/resources/Images/Cards" + nameTextField.getText() + ".JPG");
      //  BufferedImage bImage = SwingFXUtils.fromFXImage(cardImage.getImage(), null);
        // ImageIO.write(bImage, "JPG", outputFile);

        new MonsterCard( descriptionOfCard.getText(), nameTextField.getText(),"323525737",
                MonsterCard.CardType.MONSTER);
        MenuController.getUser().setMoney(MenuController.getUser().getMoney()
                - Integer.parseInt(calculatePriceOfMonster(effects)));
        System.out.println(MonsterCard.getCardByName(nameTextField.getText()));
        errorLBL.setText("card created successfully!");
    }

    public String errorCheck(CheckBox[] effects) {
        if (nameTextField.getText().equals(""))
            return "You should choose a name for your card";
        if (cardImage.getImage() == null)
            return "You should choose an image for your card";
        if (Integer.parseInt(calculatePriceOfMonster(effects)) > MenuController.getUser().getMoney())
            return "You don't have enough money for create card!";
        return "";
    }

}