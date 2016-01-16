/*
 * Copyright (c) 28.12.2015
 *  Name: Luca Corneliu Daniel
 *  Contact: luca_corneliu2003@yahoo.com
 */

package Client.GUI;

import Client.Service.SocketClient;
import Client.SocialNetwork;
import Common.domain.User;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WelcomeView extends VBox {
    private static final Log LOG = LogFactory.getLog(WelcomeView.class);

    private final SocialNetwork application;

    private TextField username;
    private PasswordField password;
    private Button authButton;
    private SocketClient client;
    // background service
    private Service<User> authService;

    public WelcomeView(SocialNetwork application, SocketClient client) {
        setMinWidth(240);
        this.application = application;
        this.client=client;

        ObservableList<Node> children = getChildren();
        //title
        children.add(new Text("Welcome!"));
        // username (label + textfield)
        children.add(new Label("Username:"));
        username = new TextField();
        children.add(username);
        // password (label + textfield)
        children.add(new Label("Password:"));
        password = new PasswordField();
        children.add(password);
        // auth button (login or cancel)
        authButton = new Button("Login");

        authButton.setOnAction(btnEvent -> onLoginButtonTriggered());
        authButton.setOnKeyPressed(event -> onLoginButtonTriggered());
        children.add(authButton);
    }

    private void onLoginButtonTriggered() {
        String authButtonText = authButton.getText();
        LOG.info(authButtonText + " button triggered");
        Dialog<Boolean> booleanDialog = AlertUtils.cancellableDialog("Logging", "Please wait until login!");
        booleanDialog.setOnCloseRequest(dialogEvent -> {
            if (authService != null) {
                authService.cancel(); // cancel the call from app thread
            }
        });
        booleanDialog.show();
        authService = this.client.authService(username.getText(), password.getText()); // just a reference to an async call/task
        authService.setOnSucceeded(e -> { // prepare what to do when the call succeeds
            User user = (User) e.getSource().getValue();
            LOG.info("auth service succeeded, " + user); // executed on app thread
            this.application.userAuthenticated(user);
            booleanDialog.close();
        });


        authService.setOnFailed(e -> { // prepare what to do when the call fails
            // executed on app thread
            Throwable exception = e.getSource().getException();
            LOG.warn("auth service failed", exception);
            booleanDialog.close();
            AlertUtils.showError(exception);
        });
        authService.setOnCancelled(e -> { // prepare what to do when the call was cancelled
            // executed on app thread
            LOG.info("auth service cancelled");
            Throwable exception = new Exception("Wrong username or password! Try again!");
            booleanDialog.close();
            AlertUtils.showError(exception);
        });
        LOG.info("starting auth service");
        authService.start(); // start the async call/task (from app thread)
        // the task is executed on background threads

    }


}
