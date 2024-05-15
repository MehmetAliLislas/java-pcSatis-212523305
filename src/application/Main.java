package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {

			 AnchorPane root = (AnchorPane)
			FXMLLoader.load(getClass().getResource("yonetimSayfaGiris.fxml"));
		//	AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("urunSayfa.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setResizable(false);
			primaryStage.setTitle("212523305-Bilgisayar Satış Otomasyonu");
			primaryStage.getIcons().add(new Image(
					"https://static.vecteezy.com/system/resources/thumbnails/008/889/015/small_2x/computer-monitor-icon-in-flat-style-isolated-on-white-background-pc-symbol-illustration-vector.jpg"));
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
