package ClientsFiles;

import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    public Button sendButton;
    @FXML
    public TextArea inputTextArea;
    @FXML
    private Button logoutButton,runButton;
    @FXML
    private Button compileButton;
    @FXML
    private RadioButton modeRadioButton;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;
    @FXML
    private Pane apane;
    @FXML
    private ChoiceBox<String> langChoiceBox;
    @FXML
    private TextArea codingTextArea,outputTextArea;
    @FXML
    ImageView cloud1,cloud3,cloud2;

    private String[] languages = {"C","C++","Java"};
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Populating the choice box and setting its onAction method to chooseLanguage
        langChoiceBox.getItems().addAll(languages);
        langChoiceBox.setOnAction(this::chooseLanguage);

        //Background animation
        helpWithTranslation(cloud1);
        helpWithTranslation(cloud2);
        helpWithTranslation(cloud3);

//        codingTextArea.textProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
//                inputTextArea.setText(codingTextArea.getText());
//            }
//        });
    }
    public void helpWithTranslation(ImageView imageView){
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(imageView);
        translate.setDuration(Duration.seconds(20));
        translate.setAutoReverse(true);
        translate.setCycleCount(TranslateTransition.INDEFINITE);
        translate.setByX(1000);
        translate.play();
    }

    @FXML
    public  void chooseLanguage(ActionEvent event) {
        String language = langChoiceBox.getValue();
        if(language.equals("Java")) {
            String s=new Pre_defined_Code().Java_Pre_Code();
            codingTextArea.setText(s);
        }
        else{
            codingTextArea.clear();
            codingTextArea.setPromptText("Enter your "+language+" code here...");
        }
    }
    @FXML
    public void changeMode(ActionEvent e) throws IOException {
        if(modeRadioButton.isSelected()){
            modeRadioButton.setText("Light Mode");
            modeRadioButton.setStyle("-fx-font-color:#FFFFFF;");
            apane.setStyle("-fx-background-color:#444444;");
        }
        else{
            modeRadioButton.setText("Dark Mode");
            modeRadioButton.setStyle("-fx-font-color:#444444;");
            apane.setStyle("-fx-background-color:#FFFFFF;");
        }
    }

//    @FXML
//    public void codingTextAreaKeyPressed(KeyEvent event)
//    {
//        //System.out.println(codingTextArea.getText());
//        //InputTxtArea.setText(codingTextArea.getText());
//    }
//
//    @FXML
//    public void btncompileaction(ActionEvent event)
//    {
//        System.out.println(codingTextArea.getText());
//        //new Testing().Store(codingTextArea.getText());
//        //new Method().connect("kesri");
//    }




}
