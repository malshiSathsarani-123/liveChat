package lk.ijse.liveChat.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lk.ijse.liveChat.bo.CreateAccountBOImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.Base64;

public class CreateAccountFormController {

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtFname;

    @FXML
    private TextField txtMail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private PasswordField txtReTypePassword;

    @FXML
    private AnchorPane root;

    CreateAccountBOImpl createAccountBO = new CreateAccountBOImpl();
    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/login_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("DASH BORD");
        stage.centerOnScreen();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String name = txtFname.getText();
        String password = txtPassword.getText();
        String contact = txtContact.getText();

            if (!txtPassword.getText().equals(txtReTypePassword.getText())){
                new Alert(Alert.AlertType.ERROR,"SORRY RECHECK YOUR PASSWORD!!!").show();
                txtPassword.setText("");
                txtReTypePassword.setText("");
            }else {
                try {
                    if (createAccountBO.saveClient(name,password,contact)){
                        new Alert(Alert.AlertType.CONFIRMATION,"SAVED!!!").show();
                        clear();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
    }

    void clear(){
        txtContact.setText("");
        txtPassword.setText("");
        txtFname.setText("");
        txtMail.setText("");
        txtReTypePassword.setText("");
    }
    @FXML
    void txtContactOnAction(ActionEvent event) {
        actionCursor(txtMail);
    }

    @FXML
    void txtFnameOnAction(ActionEvent event) {
        actionCursor(txtContact);
    }

    @FXML
    void txtMailOnAction(ActionEvent event) {
        actionCursor(txtPassword);
    }

    @FXML
    void txtPasswordOnAction(ActionEvent event) {
        actionCursor(txtReTypePassword);
    }

    @FXML
    void txtReTypePasswordOnAction(ActionEvent event) {
        if (!txtPassword.getText().equals(txtReTypePassword.getText())){
            new Alert(Alert.AlertType.ERROR,"SORRY RECHECK YOUR PASSWORD!!!").show();
            txtPassword.setText("");
            txtReTypePassword.setText("");
        }
    }

    void actionCursor(TextField txt){txt.requestFocus();}


    public void getImageOnAction(ActionEvent actionEvent) {
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

                boolean isSave = createAccountBO.saveImage(message);
//                HBox imageHbox = new HBox(imageView);
//                imageHbox.setStyle("-fx-background-color: #78E08F;-fx-background-radius:15;-fx-alignment: center;-fx-padding: 20px 5px;");
//
//                sendMessage(message, new HBox(imageHbox));
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
