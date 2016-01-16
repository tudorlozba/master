
package Client.GUI;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;

public class AlertUtils {
    public static void showError(Throwable e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error occurred");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }
    public static Dialog<Boolean> cancellableDialog(String title, String headerText) {
        Dialog<Boolean> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText(headerText);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
        VBox vbox = new VBox();
        vbox.getChildren().add(new ProgressIndicator());
        dialog.getDialogPane().setContent(vbox);
        dialog.setResultConverter(dialogButton -> true);
        return dialog;
    }
    public static Dialog<Boolean> informationDialog(String title, String headerText) {
        Dialog<Boolean> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText(headerText);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
        VBox vbox = new VBox();
        dialog.getDialogPane().setContent(vbox);
        dialog.setResultConverter(dialogButton -> true);
        return dialog;
    }
}
