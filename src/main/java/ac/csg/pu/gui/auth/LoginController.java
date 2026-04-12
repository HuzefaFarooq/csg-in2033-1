package ac.csg.pu.gui.auth;

import ac.csg.pu.gui.SceneHelper;
import ac.csg.pu.gui.util.SessionManager;
import ac.csg.pu.gui.util.ShakeAnimation;
import ac.csg.pu.members.UserType;
import ac.csg.pu.members.UserDatabase;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginController {
    private final static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;

    @FXML private Label errorLabel;

    @FXML private Button loginButton;

    @FXML
    private void initialize() {
        if (SessionManager.Pending.hasMessage()) {
            errorLabel.setText(SessionManager.Pending.getMessage());
        }

        emailField.textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.matches("[^@\\s]+@[^@\\s]+\\.[^@\\s]+")) {
                errorLabel.setText("Invalid email format");
            } else {
                errorLabel.setText("");
            }
        });

        loginButton.setDefaultButton(true);
    }

    @FXML
    private void onLogin() {
        String email = emailField.getText().trim();
        String password = passwordField.getText();

        if (UserDatabase.login(email, password)) {
            errorLabel.setText("Login successful!");

            UserType userType = UserDatabase.getUserType(email);
            SessionManager.login(email, userType);

            if (UserDatabase.isFirstLogin(email)) {
                SceneHelper.switchScene("auth/change-password.fxml");
                return;
            }

            switch (userType) {
                case A -> switchToAdminDashboard();
                case NC -> switchToNonCommercialDashboard();
                case C -> switchToCommercialDashboard();
                default -> {
                    errorLabel.setText("Unknown user type: " + userType);
                    SceneHelper.switchScene("auth/login.fxml");
                    SessionManager.logout();
                }
            }

        } else {
            ShakeAnimation.shake(emailField);
            ShakeAnimation.shake(passwordField);
            errorLabel.setText("Invalid email or password");
        }
    }

    @FXML
    private void onGuestLogin() {
        switchToCommercialDashboard();
    }

    private void switchToCommercialDashboard() {
        SceneHelper.switchScene("dashboard/commercial/home.fxml");
    }

    private void switchToNonCommercialDashboard() {
    }

    private void switchToAdminDashboard() {
        SceneHelper.switchScene("dashboard/admin/promotions.fxml");
    }

    @FXML
    private void switchToRegister() {
        SceneHelper.switchScene("auth/register.fxml");
    }
}