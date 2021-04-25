package ClientsFiles;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    public Button Signin;
    @FXML
    public Button SignUp;
    @FXML
    public TextField TxtUserName;
    @FXML
    public PasswordField TxtPassword;
    @FXML
    public RadioButton RadioButtonInterviewer;
    @FXML
    public RadioButton RadioButtonInterviewee;
    @FXML
    public TextField TxtID;
    @FXML
    public  Button btnready;

    boolean chooserdbtn=true;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //System.out.println("karan kesri");
    }

    public void SigninAction(ActionEvent event)
    {

    }

    public void SignUpAction(ActionEvent event)
    {

    }

    public void RadioButtonInterviewerAction(ActionEvent event)
    {
        chooserdbtn=true;
        TxtID.setVisible(false);

    }

    public void RadioButtonIntervieweeAction(ActionEvent event)
    {
        chooserdbtn=false;
        TxtID.setVisible(true);
    }

    public void btnreadyAction(ActionEvent event)
    {
        if(TxtUserName.getText().trim().isEmpty()||TxtPassword.getText().trim().isEmpty())
        {
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("OOPs!!!");
            alert.setContentText("Please Sign In or Sign Up");
            alert.showAndWait();
        }
        else
        {
                if(chooserdbtn)  //Interviewer->Act as Server--start the Server -> then call its UI
                {
                    System.out.println("Interviewer Selected");
                    //new Server.Main().startserver();
                    try {
                        Stage primaryStage=new Stage();
                        Parent root = FXMLLoader.load(getClass().getResource("client.fxml"));
                        primaryStage.setTitle("Hello World");
                        Scene scene=new Scene(root, 1200, 750);
                        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
                        primaryStage.setScene(scene);
                        primaryStage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
                else  //Interviewee->Act as Client--start the Socket and request it from server -> then call its UI
                {
                    System.out.println("Interviewee Selected");
                    if(TxtID.getText().trim().isEmpty())
                    {
                        Alert alert=new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("OOPs!!!");
                        alert.setContentText("Please Enter Correct ID");
                        alert.showAndWait();
                    }
                    else
                    {
                        //new Method().connect(8063);
                    }
                }
        }
    }


}
