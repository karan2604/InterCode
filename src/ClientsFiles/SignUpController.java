package ClientsFiles;

import CommonFiles_With_BigServerAndClients.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SignUpController {

    @FXML
    private Button sumbit;
    @FXML
    private TextField FirstName,LastName,UserName,Password,Id;
    @FXML
    private Label LabelStatus;

    @FXML
    private void submitAction(ActionEvent event)
    {
        if(FirstName.getText().isEmpty()||LastName.getText().isEmpty()||UserName.getText().isEmpty()||Password.getText().isEmpty()||Id.getText().isEmpty())
        {
            LabelStatus.setText("Enter All Details");
            return;
        }

        Message m=new Message();
        m.setOption("NewClient");
        m.setFirstName(FirstName.getText());
        m.setLastName(LastName.getText());
        m.setUserName(UserName.getText());
        m.setPassword(Password.getText());
        m.setId(Id.getText());
        ObjectOutputStream out=BigServerSocketDetails.getOut();
        try {
            out.writeObject(m);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            ObjectInputStream in=BigServerSocketDetails.getIn();
            m=(Message) in.readObject();
            //System.out.println(m.getOption());
            if(m.getOption().equals("ExceptionIndatabase")) {
                LabelStatus.setText("Id Already Taken");
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        LabelStatus.setText("Successfully Registered");
    }
}
