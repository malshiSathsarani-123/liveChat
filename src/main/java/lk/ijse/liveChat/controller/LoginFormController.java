package lk.ijse.liveChat.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.liveChat.bo.LoginBOImpl;

import java.io.IOException;
import java.sql.SQLException;

public class LoginFormController {

    @FXML
    private AnchorPane root;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    LoginBOImpl bo = new LoginBOImpl();
    @FXML
    void loginBtnOnAction(ActionEvent event) throws IOException {

        try {
            if (bo.exitClient(txtUserName.getText(),txtPassword.getText())){
                ClientFormController.userName=txtUserName.getText();
                AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/client_form.fxml"));
                Scene scene = new  Scene(anchorPane);
                Stage stage =new Stage();
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.setTitle(txtUserName.getText());
                stage.show();
                txtPassword.setText("");
                txtUserName.setText("");
            }else {
                new Alert(Alert.AlertType.ERROR,"password or username invalided!!!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.WARNING,"SOMETHING WENT RONG").show();
        }
    }

    @FXML
    void txtPasswordOnAction(ActionEvent event) {
        try {
            if (bo.exitClient(txtUserName.getText(),txtPassword.getText())){
                ClientFormController.userName=txtUserName.getText();
                AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/client_form.fxml"));
                Scene scene = new  Scene(anchorPane);
                Stage stage =new Stage();
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.setTitle(txtUserName.getText());
                stage.show();
                txtPassword.setText("");
                txtUserName.setText("");
            }else {
                new Alert(Alert.AlertType.ERROR,"password or username invalided!!!").show();
            }
        } catch (SQLException | IOException e) {
            new Alert(Alert.AlertType.WARNING,"SOMETHING WENT WRONG").show();
        }
    }

    @FXML
    void txtUserNameOnAction(ActionEvent event) {
        txtPassword.requestFocus();
    }

    public void btnSignOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/create_acount_form.fxml"));
        Scene scene = new  Scene(anchorPane);
        Stage stage =new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
