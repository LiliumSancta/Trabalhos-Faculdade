package musiclib.Model;

import javafx.scene.control.Alert;
import javafx.scene.paint.Color;

public class Utils {
    public static void printMsg(String msg, String title, Alert.AlertType type){

        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
    public static String toRgbString(Color c) {
        return "rgb("
                + to255Int(c.getRed())
                + "," + to255Int(c.getGreen())
                + "," + to255Int(c.getBlue())
                + ")";
    }

    private static int to255Int(double d) {
        return (int) (d * 255);
    }
}
