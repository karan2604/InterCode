package ClientsFiles;

import CommonFiles_With_BigServerAndClients.Message;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

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
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;
    @FXML
    private Label LabelStatus;
    boolean chooserdbtn=true;
    int port = 9191;

    Socket bigserversocket;
    ObjectOutputStream bigserverout;
    ObjectInputStream bigserverin;
    Boolean SignInBtn=false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ConnectionWithBigServe();
    }

    public void SigninAction(ActionEvent event)
    {
        if(TxtUserName.getText().isEmpty()||TxtPassword.getText().isEmpty())
        {
            status("Fill all the Details");
            return;
        }

        Message m=new Message();
        m.setOption("Authentication");
        m.setUserName(TxtUserName.getText());
        m.setPassword(TxtPassword.getText());
        try {
            bigserverout.writeObject(m);
            bigserverout.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            m=(Message) bigserverin.readObject();
            //System.out.println(m.getOption());
            if(m.getOption().equals("AuthenticationNotCorrect")) {
                LabelStatus.setText("Either UserName or Password is Incorrect");
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        LabelStatus.setText("Successfully Sign");
        Signin.setStyle("-fx-border-color:#FF0000; -fx-background-color:#7FFFD4;");
        SignInBtn=true;

    }

    public void SignUpAction(ActionEvent event) throws IOException {

        Parent root= FXMLLoader.load(getClass().getResource("SignUp.fxml"));
        Stage stage=new Stage();
        stage.setTitle("SignUp");
        Scene scene = new Scene(root,306,249);
        stage.setScene(scene);
        stage.show();
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
            //Alert alert=new Alert(Alert.AlertType.WARNING);
            //alert.setTitle("OOPs!!!");
            //alert.setContentText("Please Sign In or Sign Up");
            //alert.showAndWait();
            status("Please Sign In or Sign Up");
        }
        else if(!SignInBtn)
            status("Please Sign In");
        else
        {
                if(chooserdbtn)  //Interviewer->Act as Server--start the Server -> then call its UI
                {
                    roleSelector.setRole(1);
                    //System.out.println("Interviewer Selected");
                    //new Server.Main().startserver();

                }
                else  //Interviewee->Act as Client--start the Socket and request it from server -> then call its UI
                {
                    //System.out.println("Interviewee Selected");
                    roleSelector.setRole(2);
                    if(TxtID.getText().trim().isEmpty())
                    {
                        //Alert alert=new Alert(Alert.AlertType.WARNING);
                        //alert.setTitle("OOPS!!!");
                        //alert.setContentText("Please Enter Correct ID");
                        //alert.showAndWait();
                        status("Please Enter Correct ID");
                        return;
                    }
                    else
                    {
                        //new Method().connect(8063);
                    }
                }
            try {
                root = FXMLLoader.load(getClass().getResource("client.fxml"));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("InterCode");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void ConnectionWithBigServe()
    {

        try {
            bigserversocket=new Socket("127.0.0.1",8063);
            bigserverout=new ObjectOutputStream(bigserversocket.getOutputStream());
            bigserverin=new ObjectInputStream(bigserversocket.getInputStream());
            BigServerSocketDetails.setSocket(bigserversocket);
            BigServerSocketDetails.setOut(bigserverout);
            BigServerSocketDetails.setIn(bigserverin);
            //System.out.println("client Connected");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void status(String s)
    {
        LabelStatus.setText(s);
    }







}
