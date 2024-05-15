package application;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;

import com.MySql.Util.VeritabaniUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.*;

public class yonetimSayfaController {

	public yonetimSayfaController() {
		baglanti = VeritabaniUtil.Baglan();
	}

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private AnchorPane anchor_sag;

	@FXML
	private AnchorPane anchor_sol;

	@FXML
	private AnchorPane anchor_tum;

	@FXML
	private Button btn_cikis;

	@FXML
	private Button btn_kul_ekle;

	@FXML
	private Button btn_kul_guncelle;

	@FXML
	private Button btn_kul_sil;

	@FXML
	private Button btn_stok;

	@FXML
	private Button btn_urunler;

	@FXML
	private Button btn_yonetim;

	@FXML
	private TableColumn<yonetim, String> col_kullanici_adi;

	@FXML
	private TableColumn<yonetim, Integer> col_kullanici_id;

	@FXML
	private TableColumn<yonetim, String> col_kullanici_sifre;

	@FXML
	private TableView<yonetim> tableview_kullanicilar;

	@FXML
	private TextField txt_arama;

	@FXML
	private TextField txt_kul_adi;

	@FXML
	private TextField txt_kul_id;

	@FXML
	private TextField txt_kul_sifre;

	Connection baglanti = null;
	PreparedStatement sorguIfadesi = null;
	ResultSet getirilen = null;
	String sql;

	@FXML
	void btn_cikis_Click(ActionEvent event) {
		try {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Çıkış yapmak üzeresiniz.");
			alert.setHeaderText("Çıkış yapmak istiyor musunuz?");
			alert.setResizable(false);
			Optional<ButtonType> result = alert.showAndWait();
			ButtonType button = result.orElse(ButtonType.CANCEL);
			if (button == ButtonType.OK) {
				Stage stage1 = new Stage();
				AnchorPane main;
				main = (AnchorPane) FXMLLoader.load(getClass().getResource("yonetimSayfaGiris.fxml"));
				Scene scene = new Scene(main);
				stage1.setScene(scene);
				stage1.getIcons().add(new Image(
						"https://static.vecteezy.com/system/resources/thumbnails/008/889/015/small_2x/computer-monitor-icon-in-flat-style-isolated-on-white-background-pc-symbol-illustration-vector.jpg"));
				stage1.setResizable(false);
				stage1.setTitle("212523305-Bilgisayar Satış Otomasyonu");
				stage1.show();
				stage1.setResizable(false);
				((Node) event.getSource()).getScene().getWindow().hide();
			} else {
				return;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void btn_kul_ekle_Click(ActionEvent event) {
		sql = "insert into yonetim( kul_adi, kul_sifre) values (?,?)";
		if (txt_kul_adi.getText().length() < 5) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Hata!");
			alert.setHeaderText("Lütfen Geçerli Bilgi Giriniz.");
			alert.showAndWait();
		} else {
			try {
				sorguIfadesi = baglanti.prepareStatement(sql);
				sorguIfadesi.setString(1, txt_kul_adi.getText());
				sorguIfadesi.setString(2, txt_kul_sifre.getText());
				sorguIfadesi.executeUpdate();
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("İşlem Başarılı!");
				alert.setHeaderText("Kullanıcı ekleme işlemi başarıyla gerçekleşti.");
				alert.showAndWait();
				temizle();
				degerleriGetir();
			} catch (Exception e) {
				// TODO: handle exception
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Hata!");
				alert.setHeaderText("Beklenmedik Hata Oluştu..");
				alert.showAndWait();
			}
		}
	}

	@FXML
	void btn_kul_guncelle_Click(ActionEvent event) {
		sql = "update yonetim set kul_adi=?, kul_sifre=? where kul_id=? ";
		if (txt_kul_adi.getText().length() < 5) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Hata!");
			alert.setHeaderText("Lütfen Geçerli Bilgi Giriniz.");
			alert.showAndWait();
		} else {
			try {
				sorguIfadesi = baglanti.prepareStatement(sql);
				sorguIfadesi.setString(1, txt_kul_adi.getText());
				sorguIfadesi.setString(2, txt_kul_sifre.getText());
				sorguIfadesi.setString(3, txt_kul_id.getText());
				sorguIfadesi.executeUpdate();
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("İşlem Başarılı!");
				alert.setHeaderText("Kullanıcı güncelleme işlemi başarıyla gerçekleşti.");
				alert.showAndWait();
				degerleriGetir();
				temizle();
			} catch (Exception e) {
				// TODO: handle exception
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Hata!");
				alert.setHeaderText("Beklenmedik Hata Oluştu..");
				alert.showAndWait();
			}
		}
	}

	@FXML
	void btn_kul_sil_Click(ActionEvent event) {
		sql = "delete from yonetim where kul_id=? ";
		try {
			sorguIfadesi = baglanti.prepareStatement(sql);
			sorguIfadesi.setString(1, txt_kul_id.getText());
			sorguIfadesi.executeUpdate();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("İşlem Başarılı!");
			alert.setHeaderText("Kullanıcı silme işlemi başarıyla gerçekleşti.");
			alert.showAndWait();
			degerleriGetir();
			temizle();
		} catch (Exception e) {
			// TODO: handle exception
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Hata!");
			alert.setHeaderText("Beklenmedik Hata Oluştu..");
			alert.showAndWait();
		}
	}

	@FXML
	void btn_stok_Click(ActionEvent event) {
		try {
			AnchorPane panel = (AnchorPane) FXMLLoader.load(getClass().getResource("stokSayfa.fxml"));
			anchor_tum.getChildren().setAll(panel);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void btn_urunler_Click(ActionEvent event) {
		try {
			AnchorPane panel = (AnchorPane) FXMLLoader.load(getClass().getResource("urunSayfa.fxml"));
			anchor_tum.getChildren().setAll(panel);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void btn_yonetim_Click(ActionEvent event) {
		try {
			AnchorPane panel = (AnchorPane) FXMLLoader.load(getClass().getResource("yonetimSayfa.fxml"));
			anchor_tum.getChildren().setAll(panel);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void degerleriGetir() {
		sql = "select * from yonetim";
		ObservableList<yonetim> yonetimliste = FXCollections.observableArrayList();
		try {
			sorguIfadesi = baglanti.prepareStatement(sql);
			ResultSet getirilen = sorguIfadesi.executeQuery();
			while (getirilen.next()) {
				yonetimliste.add(new yonetim(getirilen.getInt("kul_id"), getirilen.getString("kul_adi"),
						getirilen.getString("kul_sifre")));
			}
			col_kullanici_id.setCellValueFactory(new PropertyValueFactory<>("kul_id"));
			col_kullanici_adi.setCellValueFactory(new PropertyValueFactory<>("kul_adi"));
			col_kullanici_sifre.setCellValueFactory(new PropertyValueFactory<>("kul_sifre"));
			tableview_kullanicilar.setItems(yonetimliste);
		} catch (Exception e) {
			// TODO: handle exception
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Hata!");
			alert.setHeaderText("Tablo Boş...");
			alert.showAndWait();
		}
	}

	@FXML
	void tableview_kullanicilar_MouseClick() {
		yonetim yonetim = new yonetim();
		yonetim = (yonetim) tableview_kullanicilar.getItems()
				.get(tableview_kullanicilar.getSelectionModel().getSelectedIndex());
		txt_kul_id.setText(String.valueOf(yonetim.getKul_id()));
		txt_kul_adi.setText(yonetim.getKul_adi());
		txt_kul_sifre.setText(yonetim.getKul_sifre());
	}

	@FXML
	void txt_arama_KeyPressed(KeyEvent event) {
		if (txt_arama.getText().equals("")) {
			sql = "select * from yonetim";
		} else {
			sql = "select * from yonetim where kul_adi like '%" + txt_arama.getText() + "%' or kul_sifre like '%"
					+ txt_arama.getText() + "%' ";
		}
		degerleriGetir();
	}

	void temizle() {
		txt_kul_adi.setText("");
		txt_kul_id.setText("");
		txt_kul_sifre.setText("");
	}

	@FXML
	void initialize() {
		degerleriGetir();
	}
}
