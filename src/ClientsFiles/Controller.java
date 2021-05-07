package ClientsFiles;

import CommonFiles_With_BigServerAndClients.Message;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Button logoutButton,runButton,sendButton,compileButton;
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
    private TextArea codingTextArea,outputTextArea,inputTextArea;
    @FXML
    private TextArea chatInputTextArea,chatDisplayTextArea;
    @FXML
    ImageView cloud1,cloud3,cloud2;

    Socket clientSocket;
    ServerSocket serverSocket;
    ObjectOutputStream clientoos;
    ObjectInputStream clientois;

    final private String[] languages = {"C","C++","Java"};
    private HashMap<String,TextArea> nameToObject = new HashMap<String,TextArea>();
    private String sender = "Rajat";  //By default the user is Rajat
    private Boolean isConnected = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Populating the choice box and setting its onAction method to chooseLanguage
        langChoiceBox.getItems().addAll(languages);
        langChoiceBox.setOnAction(this::chooseLanguage);

        //Populating the HashMap
        nameToObject.put("codingTextArea",codingTextArea);
        nameToObject.put("inputTextArea",inputTextArea);
        nameToObject.put("outputTextArea",outputTextArea);
        nameToObject.put("chatInputTextArea",chatInputTextArea);
        nameToObject.put("chatDisplayTextArea",chatDisplayTextArea);

        //nameToObject.put("",)

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
        //starting the connection
        try {
            initiateConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            clientoos = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
                try {
                    clientois = new ObjectInputStream(clientSocket.getInputStream());
                } catch (IOException e) {
                    isConnected = false;
                }
                sendInfo object;
                while(isConnected){
                    try {
                        object = (sendInfo) clientois.readObject();
                        if(object.getTypeOfInfo().equals("SharedContent") && isConnected){
                            nameToObject.get(object.getTargetedArea()).setText(object.getContent());
                        }
                        else if(object.getTypeOfInfo().equals("Message") && isConnected ){
                            chatDisplayTextArea.appendText("\n");
                            chatDisplayTextArea.appendText(object.getSender()+ ": "+ object.getContent());
                            chatDisplayTextArea.appendText("\n-----------------------------------------");
                        }
                        else if(object.getTypeOfInfo().equals("ClosingMessage") && isConnected){
//                            System.out.println("Connection is Closed");
                            isConnected = false;
                            break;
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        //Do Nothing
                    }
                }
                terminateConnection();
            }
        });
                thread.start();

        ConnectionWithBigServe();  //Coneection with BigServer
        StartListeningfromBigserver();

        //Background animation
        helpWithTranslation(cloud1,20);
        helpWithTranslation(cloud2,25);
        helpWithTranslation(cloud3,30);

//        Platform.exit();

    }
    public void helpWithTranslation(ImageView imageView,int seconds){
        //Handles the background animation for given imageview
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(imageView);
        translate.setDuration(Duration.seconds(seconds));
        translate.setAutoReverse(true);
        translate.setCycleCount(TranslateTransition.INDEFINITE);
        translate.setByX(1100);
        translate.play();
    }

    public void initiateConnection() throws IOException {
        //This function fills the value of clientSocket depending upon the role
        if(roleSelector.role==1){
//            serverSocket = new ServerSocket(Connection.port);
//            Thread t = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        clientSocket = serverSocket.accept();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//            t.start();
            clientSocket = new Connection().serverClientConnection();
            setSender("Interviewer(Rajat)");
        }
        else{
            clientSocket = new Connection().clientSocketConnection();
            setSender("Interviewee(Karan)");
        }
        isConnected = true;
    }

    @FXML
    public  void chooseLanguage(ActionEvent event) {
        //Handles the choosing of different programming languages in the choice box
        String language = langChoiceBox.getValue();
        String preDefinedCode="";
        if(language.equals("Java")) {
            preDefinedCode = new Pre_defined_Code().Java_Pre_Code();
        }
        else if(language.equals("C")){
            preDefinedCode = new Pre_defined_Code().C_Pre_Code();
        }
        else if(language.equals("C++")){
            preDefinedCode = new Pre_defined_Code().CPP_Pre_Code();
        }
        codingTextArea.setText(preDefinedCode);
    }
    @FXML
    public void changeMode(ActionEvent e) throws IOException {
        //Handles the modeRadioButton and changes the Dark and Light Mode.
        if(modeRadioButton.isSelected()){
            modeRadioButton.setText("Light Mode");
            apane.setStyle("-fx-background-color:#444444;");
        }
        else{
            modeRadioButton.setText("Dark Mode");
            apane.setStyle("-fx-background-color:#FFFFFF;");
        }
    }

    @FXML
    public void logout(ActionEvent e) throws IOException {
        //Logout Confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You are about to logout!!");
        if(alert.showAndWait().get() == ButtonType.OK) {
            //Disconnect & Switch scene to initial scene
            if (isConnected) {
                clientoos.writeObject(new sendInfo(sender, "ClosingMessage", "", "Closing the Connection"));
                terminateConnection();
                isConnected = false;
            }
            //Changing Scene
            root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("Login.css").toExternalForm());
            stage.setScene(scene);
        }
    }

    @FXML
    public void getTextOfTextArea(KeyEvent e){
        if(isConnected) {
            TextArea sourceOfEvent = (TextArea) e.getSource();
            String text = sourceOfEvent.getText();
            sendInfo info = new sendInfo(sender, "SharedContent", sourceOfEvent.getId(), text);
            try {
                clientoos.writeObject(info);
                clientoos.flush();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
    @FXML
    public void getTextOfTextAreaMouse(MouseEvent e){
        if(isConnected) {
            TextArea sourceOfEvent = (TextArea) e.getSource();
            String text = sourceOfEvent.getText();
            sendInfo info = new sendInfo(sender, "SharedContent", sourceOfEvent.getId(), text);
            try {
                clientoos.writeObject(info);
                clientoos.flush();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    @FXML
    public void sendMessage(ActionEvent e) {
        String msg = chatInputTextArea.getText();
        if(msg!=null && isConnected){
            try {
                clientoos.writeObject(new sendInfo(sender,"Message","chatDisplayTextArea",msg));
                clientoos.flush();
                chatInputTextArea.clear();
                chatDisplayTextArea.appendText("\n");
                chatDisplayTextArea.appendText(sender+": "+msg);
                chatDisplayTextArea.appendText("\n-----------------------------------------");
            } catch (IOException ioException) {
                System.out.println("Here");
                ioException.printStackTrace();
            }
        }
    }

    private void setSender(String sender){
        this.sender = sender;
    }

    private void terminateConnection(){
        try {
            clientoos.close();
            clientois.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Boolean isConnectedBigserver=false;
    ObjectOutputStream bigserverout;
    ObjectInputStream bigserverin;
    Socket bigserversocket;

    //Compile Button clicked
    @FXML
    public void compileButtonAction(ActionEvent event)
    {
        if(langChoiceBox.getValue()==null)
        {
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("OOPs!!!");
            alert.setContentText("Please Select the language");
            alert.showAndWait();
            return;
        }

        if(isConnectedBigserver)
        {
            if(!codingTextArea.getText().isEmpty())
            {
                Message m=new Message();
                m.setContent(codingTextArea.getText());
                m.setLang(langChoiceBox.getValue());
                m.setOption("compile");
                try {
                    bigserverout.writeObject(m);
                    bigserverout.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else
        {
            //Unable to find Big Server
        }

    }

    //Run Button clicked
    @FXML
    public void runButtonAction(ActionEvent event)
    {
        if(langChoiceBox.getValue()==null)
        {
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("OOPs!!!");
            alert.setContentText("Please Select the language");
            alert.showAndWait();
            return;
        }
        if(isConnectedBigserver)
        {
            if(!codingTextArea.getText().isEmpty())
            {
                Message m=new Message();
                m.setContent(inputTextArea.getText());
                m.setLang(langChoiceBox.getValue());
                m.setOption("run");
                try {
                    bigserverout.writeObject(m);
                    bigserverout.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else
        {
            //Unable to find Big Server
        }
    }

    private void StartListeningfromBigserver()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {

                while(isConnectedBigserver)
                {
                    try {
                        Message m=(Message) bigserverin.readObject();
                        String content=m.getContent();
                        outputTextArea.setText(content);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    private void ConnectionWithBigServe()
    {

        try {
            bigserversocket=BigServerSocketDetails.getSocket();
            bigserverout=BigServerSocketDetails.getOut();
            bigserverin=BigServerSocketDetails.getIn();
            isConnectedBigserver=true;
            //System.out.println("client Connected");
        } catch (Exception e) {
            isConnectedBigserver=false;
            e.printStackTrace();
        }
    }
}
