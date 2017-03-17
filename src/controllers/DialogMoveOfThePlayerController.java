package controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import objects.Move;
import objects.Word;

import java.net.URL;
import java.util.ResourceBundle;

public class DialogMoveOfThePlayerController implements Initializable{

    @FXML
    public ChoiceBox<String>    chbxCoordX;

    @FXML
    public ChoiceBox<String>    chbxCoordY;

    @FXML
    public ChoiceBox<String>    chbxLetter;

    @FXML
    public TextField            txtWord;

    @FXML
    public Button               btnOK;

    @FXML
    public Button               btnCancel;

    private Stage               stage = null;
    private Stage               owner = null;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        chbxCoordX.setItems(FXCollections.observableArrayList("X1", "X2", "X3", "X4", "X5"));
        chbxCoordY.setItems(FXCollections.observableArrayList("Y1", "Y2", "Y3", "Y4", "Y5"));
        chbxLetter.setItems(FXCollections.observableArrayList("а", "б", "в", "г", "д", "е", "є", "ж", "з", "и", "і", "ї",
                                                              "й", "к", "л", "м", "н", "о", "п", "р", "с", "т", "у", "ф",
                                                              "х", "ц", "ч", "ш", "щ", "ь", "ю", "я"));

    }

    private void resetAllFields()
    {
        chbxCoordX.setValue(null);
        chbxCoordY.setValue(null);
        chbxLetter.setValue(null);
        txtWord.setText("");
    }

    private void closeCurrentWindow() {

        stage = (Stage)btnCancel.getScene().getWindow();
        owner = (Stage)stage.getOwner();
        resetAllFields();
        stage.close();

    }

    private void sendDataToTheSinglePlayerGameController() {

        Move possibleMove = new Move();
        possibleMove.setCoordX(chbxCoordX.getValue());
        possibleMove.setCoordY(chbxCoordY.getValue());
        possibleMove.setLetter(chbxLetter.getValue());
        possibleMove.setWord(txtWord.getText());
        closeCurrentWindow();
        SinglePlayerGameController.tryToMakeMoveBy(possibleMove);

    }

    private void callErrorDialog(String error) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Вікно помилки");
        alert.setHeaderText("Помилка при спробі зробити хід");
        alert.setContentText(error);

        alert.showAndWait();
    }

    private void checkAllFields() {

        Word possibleWord = new Word(txtWord.getText());
        if (chbxCoordX.getValue() == null) {
            callErrorDialog("Поле 'Координата Х' -- порожнє");
        } else if (chbxCoordY.getValue() == null) {
            callErrorDialog("Поле 'Координата Y' -- порожнє");
        } else if (chbxLetter.getValue() == null) {
            callErrorDialog("Поле 'Літера' -- порожнє");
        } else if (!possibleWord.isCorrectWordInUkrainian()) {
            System.out.println(1);
            callErrorDialog("Ви ввели некоректне слово");
        } else {
            sendDataToTheSinglePlayerGameController();
        }

    }

    private void callWarningDialog(String warning) {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Діалог попередження");
        alert.setHeaderText(null);
        alert.setContentText("Careful with the next step!");

        alert.showAndWait();

    }

    public void pressedButton(ActionEvent actionEvent) {

        Button clickedButton = (Button) actionEvent.getSource();
        switch(clickedButton.getId()) {
            case "btnOK":
                checkAllFields();
                break;
            case "btnCancel":
                closeCurrentWindow();
                break;

        }

    }

}
