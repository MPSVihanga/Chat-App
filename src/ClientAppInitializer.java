import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class ClientAppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("lk/ijse/socket/view/LoginClientUIForm.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        /*primaryStage.initStyle(StageStyle.UNDECORATED);*/
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        anableMove(scene, primaryStage);
        primaryStage.show();
    }

    private void anableMove(Scene scene, Stage primaryStage) {
        AtomicReference<Double> xOffset = new AtomicReference<>((double) 0);
        AtomicReference<Double> yOffset = new AtomicReference<>((double) 0);
        scene.setOnMousePressed(event -> {
            xOffset.set(primaryStage.getX() - event.getScreenX());
            yOffset.set(primaryStage.getY() - event.getScreenY());
        });
        //Lambda mouse event handler
        scene.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() + xOffset.get());
            primaryStage.setY(event.getScreenY() + yOffset.get());
        });
    }
}
