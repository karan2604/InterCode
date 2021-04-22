package sample;

import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    public ComboBox<String> Select_lang;
    @FXML
    public TextArea CompileTxtArea;
    @FXML
    public Button btncompile;
    @FXML
    public TextArea InputTxtArea;

    ObservableList<String> list= FXCollections.observableArrayList("C","C++","JAVA");
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Select_lang.setItems(list);

    }

    @FXML
    public  void ComboboxSelectLang(ActionEvent event)
    {
        //System.out.println(Select_lang.getValue());
        if(Select_lang.getValue().equals("JAVA"))
        {
            String s=new Pre_defined_Code().Java_Pre_Code();
            CompileTxtArea.setText(s);
        }
    }

    @FXML
    public void CompileTxtAreaKeyPressed(KeyEvent event)
    {
        //System.out.println(CompileTxtArea.getText());
    }

    @FXML
    public void btncompileaction(ActionEvent event)
    {
        System.out.println(CompileTxtArea.getText());
        //new Testing().Store(CompileTxtArea.getText());
        //new Method().connect("kesri");
    }




}
