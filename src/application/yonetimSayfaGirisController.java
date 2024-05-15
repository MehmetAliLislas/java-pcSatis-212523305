package application;

import java.net.URL;
import java.util.ResourceBundle;

import com.MySql.Util.VeritabaniUtil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.*;

public class yonetimSayfaGirisController {

	public yonetimSayfaGirisController() {
		baglanti = VeritabaniUtil.Baglan();
	}

	@FXML
	private ResourceBundle resources;

	@FXML
	private AnchorPane login_anc;

	@FXML
	private URL location;

	@FXML
	private Button btn_giris;

	@FXML
	private Button btn_kayit;

	@FXML
	private TextField txt_giris;

	@FXML
	private TextField txt_kayit;

	@FXML
	private TextField txt_kayitsifre;

	@FXML
	private TextField txt_sifre;

	Connection baglanti = null;
	PreparedStatement sorguIfadesi = null;
	ResultSet getirilen = null;
	String sql;

	@FXML
	void btn_giris_Click(ActionEvent event) {
		sql = "select * from yonetim where kul_adi=? and kul_sifre=?";
		try {
			sorguIfadesi = baglanti.prepareStatement(sql);
			sorguIfadesi.setString(1, txt_giris.getText().trim());
			sorguIfadesi.setString(2, txt_sifre.getText().trim());
			ResultSet getirilen = sorguIfadesi.executeQuery();

			if (!getirilen.next()) {

				if (txt_kayit.getText().length() == 0 || txt_kayitsifre.getText().length() == 0) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Hatalı Giriş!");
					alert.setHeaderText("Lütfen kullanıcı adı ve şifrenizi giriniz.");
					alert.showAndWait();
				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Hatalı Giriş!");
					alert.setHeaderText(
							"Hatalı giriş yapmaya çalıştınız. Lütfen kullanıcı adı ve şifrenizi kontrol edin.");
					alert.showAndWait();
				}
			} else {
				try {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Giriş İşlemi Başarılı!");
					alert.setHeaderText("Hoş geldiniz, " + txt_giris.getText() + ".");
					alert.showAndWait();
					Stage stage1 = new Stage();
					AnchorPane main = (AnchorPane) FXMLLoader.load(getClass().getResource("urunSayfa.fxml"));
					Scene scene = new Scene(main);
					stage1.setScene(scene);
					stage1.getIcons().add(new Image(
							"https://static.vecteezy.com/system/resources/thumbnails/008/889/015/small_2x/computer-monitor-icon-in-flat-style-isolated-on-white-background-pc-symbol-illustration-vector.jpg"));
					stage1.setResizable(false);
					stage1.setTitle("212523305-Bilgisayar Satış Otomasyonu");
					stage1.show();
					stage1.setResizable(false);
					((Node) event.getSource()).getScene().getWindow().hide();
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println(e.getMessage().toString());
				}
			}
		} catch (Exception ex) {
			// TODO: handle exception
			System.out.println(ex.getMessage().toString());
		}
	}

	@FXML
	void btn_kayit_Click(ActionEvent event) {
		sql = "insert into yonetim(kul_adi,kul_sifre) values (?,?)";

		if (txt_kayit.getText().length() < 4 || txt_kayitsifre.getText().length() < 4) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Geçersiz Bilgi!");
			alert.setHeaderText("Lütfen geçerli kullanıcı adı ve şifre girin.");
			alert.setContentText("Kullanıcı adı ve şifreniz en az 4 karakterden oluşmalıdır.");
			alert.showAndWait();
		} else {
			try {
				sorguIfadesi = baglanti.prepareStatement(sql);
				sorguIfadesi.setString(1, txt_kayit.getText().trim());
				sorguIfadesi.setString(2, txt_kayitsifre.getText().trim());
				sorguIfadesi.executeUpdate();
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Kayıt İşlemi Başarılı!");
				alert.setHeaderText("Merhaba sayın " + txt_kayit.getText() + ", başarıyla kayıt oldunuz.");
				alert.showAndWait();
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e.getMessage().toString());
			}
		}
	}

	@FXML
	void initialize() {

	}
}
