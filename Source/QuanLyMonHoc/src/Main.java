import UI.DangNhapController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.HibernateUtil;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UI/DangNhap.fxml"));
        Parent root = loader.load();
        DangNhapController controller = loader.getController();
        controller.setStage(primaryStage);

        primaryStage.setTitle("Quản lý Điểm danh");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.centerOnScreen();
        primaryStage.setOnCloseRequest(e -> HibernateUtil.stop());

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}