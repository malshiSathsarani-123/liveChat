package lk.ijse.liveChat.controller;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import lk.ijse.liveChat.bo.CreateAccountBOImpl;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Files;
import java.util.Base64;
import java.util.ResourceBundle;

public class ClientFormController implements Initializable {

    @FXML
    private Label lblName;

    @FXML
    private TextField txtMsg;

    @FXML
    private VBox vBox;

    @FXML
    private AnchorPane emojiAnchorpane;

    @FXML
    private GridPane emojiGridpane;

    @FXML
    private AnchorPane letterAnchopane;

    @FXML
    private GridPane letterGridpane;

    private BufferedReader bufferedReader;

    private BufferedWriter bufferedWriter;

    public static String userName;

    private final String[] emojis = {
            "\uD83D\uDE00",
            "\uD83D\uDE01",
            "\uD83D\uDE02",
            "\uD83D\uDE03",
            "\uD83D\uDE04",
            "\uD83D\uDE05",
            "\uD83D\uDE06",
            "\uD83D\uDE07",
            "\uD83D\uDE08",
            "\uD83D\uDE09",
            "\uD83D\uDE0A",
            "\uD83D\uDE0B",
            "\uD83D\uDE0C",
            "\uD83D\uDE0D",
            "\uD83D\uDE0E",
            "\uD83D\uDE0F",
            "\uD83D\uDE10",
            "\uD83D\uDE11",
            "\uD83D\uDE12",
            "\uD83D\uDE13"
    };

    private final String[] sinhala = {
            "\u0D85",
            "\u0D86",
            "\u0D87",
            "\u0D88",
            "\u0D89",
            "\u0D8A",
            "\u0D8B",
            "\u0D8C",
            "\u0D86",
            "\u0D8D",
            "\u0D8E",
            "\u0D8F",
            "\u0D90",
            "\u0D91",
            "\u0D92",
            "\u0D93",
            "\u0D94",
            "\u0D95",
            "\u0D96",
            "\u0D97",
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            lblName.setText(userName);
            Socket socket = new Socket("localhost", 1212);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write(userName);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        emojiAnchorpane.setVisible(false);
        int buttonIndex = 0;
        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 5; column++) {
                if (buttonIndex < emojis.length) {
                    String emoji = emojis[buttonIndex];
                    JFXButton emojiButton = createEmojiButton(emoji);
                    emojiGridpane.add(emojiButton, column, row);
                    buttonIndex++;
                } else {
                    break;
                }
            }
        }

        letterAnchopane.setVisible(false);
        int buttonIndex1 = 0;
        for (int row1 = 0; row1 < 5; row1++) {
            for (int column1 = 0; column1 < 5; column1++) {
                if (buttonIndex1 < sinhala.length) {
                    String letter = sinhala[buttonIndex1];
                    JFXButton letterButton = createLetterButton(letter);
                    letterGridpane.add(letterButton, column1, row1);
                    buttonIndex1++;
                } else {
                    break;
                }
            }
        }

        Thread messageReceiver = new Thread(()->{
            try {
                while (true) {

                    Label lblName = new Label();
                    lblName.setStyle("-fx-background-color: #82CCDD;-fx-background-radius:15;-fx-font-size: 13;-fx-text-fill: #000000;-fx-alignment: center;-fx-padding: 5px;");

                    Label lblMessage = new Label();
                    lblMessage.setStyle("-fx-background-color: #82CCDD;-fx-background-radius:15;-fx-font-size: 16;-fx-text-fill: #000000;-fx-alignment: center;-fx-padding: 5px");

                    ImageView imgReserved = new ImageView();
                    imgReserved.setFitWidth(100);
                    imgReserved.setPreserveRatio(true);

                    String[] data = bufferedReader.readLine().split("/#sendingClientName#/");
                    String clientName = data[0];
                    String message = data[1];
                    if (message != null) {
                        Platform.runLater(() -> {
                            HBox hBoxMessage = new HBox();
                            hBoxMessage.setSpacing(10);
                            hBoxMessage.setStyle("-fx-alignment: center-left;-fx-pref-width: 584;-fx-padding: 10px 0 0 0");

                            lblName.setText(clientName);
                            if (message.startsWith("Image:")) {
                                byte[] imageData = Base64.getDecoder().decode(message.substring(6));
                                imgReserved.setImage(new Image(new ByteArrayInputStream(imageData)));

                                HBox hBoxImage = new HBox(imgReserved);
                                hBoxImage.setStyle("-fx-background-color: #82CCDD;-fx-background-radius:15;-fx-alignment: center;-fx-padding: 20px 5px;");

                                hBoxMessage.getChildren().addAll(lblName, hBoxImage);
                            } else {
                                lblMessage.setText(message);
                                hBoxMessage.getChildren().addAll(lblName, lblMessage);
                            }
                            vBox.getChildren().add(hBoxMessage);
                        });
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        messageReceiver.setDaemon(true);
        messageReceiver.start();
    }

    private void sendMessage(String message, HBox hBox) throws IOException {
        hBox.setStyle("-fx-alignment: center-right;-fx-pref-width: 584;-fx-padding: 10px 0 0 0");
        vBox.getChildren().add(hBox);
        message = lblName.getText()+"/#sendingClientName#/"+message;
        bufferedWriter.write(message);
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }

    @FXML
    void sendBtnOnAction(ActionEvent event) {
        String message = txtMsg.getText();
        Label messageLabel = new Label(message);
        messageLabel.setStyle("-fx-background-color: #008080;-fx-background-radius:15;-fx-font-size: 16;-fx-text-fill: #000000;-fx-alignment: center;-fx-padding: 5px");
        if (!message.isEmpty()) {
            try {
                sendMessage(message, new HBox(messageLabel));
                txtMsg.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private JFXButton createEmojiButton(String emoji) {
        JFXButton button = new JFXButton(emoji);
        button.getStyleClass().add("emoji-button");
        button.setOnAction(this::emojiButtonAction);
        button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        GridPane.setFillWidth(button, true);
        GridPane.setFillHeight(button, true);
        button.setStyle("-fx-font-size: 15; -fx-text-fill: #FFC300 ; -fx-background-color: #F0F0F0; -fx-border-radius: 50");
        return button;
    }

    private JFXButton createLetterButton(String letter) {
        JFXButton button = new JFXButton(letter);
        button.getStyleClass().add("letter-button");
        button.setOnAction(this::letterButtonAction);
        button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        GridPane.setFillWidth(button, true);
        GridPane.setFillHeight(button, true);
        button.setStyle("-fx-font-size: 15; -fx-text-fill: #FFC300 ; -fx-background-color: #F0F0F0; -fx-border-radius: 50");
        return button;
    }

    @FXML
    private void emojiButtonAction(ActionEvent event) {
        JFXButton button = (JFXButton) event.getSource();
        txtMsg.appendText(button.getText());
    }

    public void btnEmojiOnAction() {
        emojiAnchorpane.setVisible(!emojiAnchorpane.isVisible());
    }
    public void btnLettersOnAction() {
        letterAnchopane.setVisible(!letterAnchopane.isVisible());
    }

    @FXML
    private void letterButtonAction(ActionEvent event) {
        JFXButton button1 = (JFXButton) event.getSource();
        txtMsg.appendText(button1.getText());
    }



    @FXML
    public void imageSendOnAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                byte[] imageData = Files.readAllBytes(file.toPath());
                String encodedImage = Base64.getEncoder().encodeToString(imageData);
                String message = "Image:" + encodedImage;
                ImageView imageView = new ImageView(new Image(file.getPath()));
                imageView.setFitWidth(100);
                imageView.setPreserveRatio(true);

                HBox imageHbox = new HBox(imageView);
                imageHbox.setStyle("-fx-background-color: #008080;-fx-background-radius:15;-fx-alignment: center;-fx-padding: 20px 5px;");

                sendMessage(message, new HBox(imageHbox));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
