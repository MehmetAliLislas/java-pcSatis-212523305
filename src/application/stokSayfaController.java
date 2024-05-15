package application;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class stokSayfaController {

	public stokSayfaController() {
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
	private Button btn_stok;

	@FXML
	private Button btn_stok_guncelle;

	@FXML
	private Button btn_stok_kaydet;

	@FXML
	private Button btn_stok_sil;

	@FXML
	private Button btn_urunler;

	@FXML
	private Button btn_yonetim;

	@FXML
	private ComboBox<String> cmb_stok_ad;

	@FXML
	private TableColumn<stok, String> col_stok_ad;

	@FXML
	private TableColumn<stok, Integer> col_stok_fiyat;

	@FXML
	private TableColumn<stok, Integer> col_stok_id;

	@FXML
	private TableColumn<stok, Date> col_stok_tarih;

	@FXML
	private TableColumn<stok, Integer> col_stok_adet;

	@FXML
	private DatePicker date_stok_tarih;

	@FXML
	private TableView<stok> tableview_stok;

	@FXML
	private TextField txt_stok_id;

	@FXML
	private TextField txt_arama;

	@FXML
	private TextField txt_stok_adet;

	@FXML
	private TextField txt_stok_fiyat;

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
	void btn_stok_guncelle_Click(ActionEvent event) {
		sql = "update stok set stok_ad=?, stok_adet=?, stok_fiyat=?, stok_tarih=? where stok_id=? ";
		if (cmb_stok_ad.getValue() == "Bir Ürün Seçiniz") {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Hata!");
			alert.setHeaderText("Lütfen Geçerli Bilgi Giriniz.");
			alert.showAndWait();
		} else {
			try {
				sorguIfadesi = baglanti.prepareStatement(sql);
				sorguIfadesi.setString(1, cmb_stok_ad.getValue());
				sorguIfadesi.setString(2, txt_stok_adet.getText());
				sorguIfadesi.setString(3, txt_stok_fiyat.getText());
				if (date_stok_tarih.getValue() != null) {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					String formattedDate = date_stok_tarih.getValue().format(formatter);
					sorguIfadesi.setString(4, formattedDate);
				}
				sorguIfadesi.setString(5, txt_stok_id.getText());
				sorguIfadesi.executeUpdate();
				temizle();
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("İşlem Başarılı!");
				alert.setHeaderText("Stok güncelleme işlemi başarıyla gerçekleşti.");
				alert.showAndWait();
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
	void btn_stok_kaydet_Click(ActionEvent event) {
		sql = "insert into stok ( stok_ad, stok_adet, stok_fiyat, stok_tarih) values (?,?,?,?)";
		if (cmb_stok_ad.getValue() == "Bir Ürün Seçiniz") {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Hata!");
			alert.setHeaderText("Lütfen Geçerli Bilgi Giriniz.");
			alert.showAndWait();
		} else {
			try {
				sorguIfadesi = baglanti.prepareStatement(sql);
				sorguIfadesi.setString(1, cmb_stok_ad.getValue());
				sorguIfadesi.setString(2, txt_stok_adet.getText());
				sorguIfadesi.setString(3, txt_stok_fiyat.getText());
				if (date_stok_tarih.getValue() != null) {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					String formattedDate = date_stok_tarih.getValue().format(formatter);
					sorguIfadesi.setString(4, formattedDate);
				}
				sorguIfadesi.executeUpdate();
				temizle();
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("İşlem Başarılı!");
				alert.setHeaderText("Stok ekleme işlemi başarıyla gerçekleşti.");
				alert.showAndWait();
				degerleriGetir();
			} catch (Exception e) {
				// TODO: handle exception
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Hata!");
				alert.setHeaderText("Beklenmedik Hata Oluştu..");
				alert.showAndWait();
				System.out.println(e.getMessage().toString());
			}
		}
	}

	@FXML
	void btn_stok_sil_Click(ActionEvent event) {
		sql = "delete from stok where stok_id=? ";
		if (cmb_stok_ad.getValue() == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Hata!");
			alert.setHeaderText("Lütfen Geçerli Bilgi Giriniz.");
			alert.showAndWait();
		} else {
			try {
				sorguIfadesi = baglanti.prepareStatement(sql);
				sorguIfadesi.setString(1, txt_stok_id.getText());
				sorguIfadesi.executeUpdate();
				temizle();
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("İşlem Başarılı!");
				alert.setHeaderText("Stok silme işlemi başarıyla gerçekleşti.");
				alert.showAndWait();
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

	void temizle() {
		txt_stok_adet.setText("");
		txt_stok_adet.setText("");
		txt_stok_fiyat.setText("");
		txt_stok_id.setText("");
		cmb_stok_ad.setValue("Bir Ürün Seçiniz");
		date_stok_tarih.setValue(null);
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

	public void degerleriGetir() {
		sql = "select * from stok";
		ObservableList<stok> stokliste = FXCollections.observableArrayList();
		try {
			sorguIfadesi = baglanti.prepareStatement(sql);
			ResultSet getirilen = sorguIfadesi.executeQuery();
			while (getirilen.next()) {
				stokliste.add(new stok(getirilen.getInt("stok_id"), getirilen.getString("stok_ad"),
						getirilen.getInt("stok_adet"), getirilen.getInt("stok_fiyat"),
						getirilen.getDate("stok_tarih")));
			}
			col_stok_id.setCellValueFactory(new PropertyValueFactory<>("stok_id"));
			col_stok_ad.setCellValueFactory(new PropertyValueFactory<>("stok_ad"));
			col_stok_adet.setCellValueFactory(new PropertyValueFactory<>("stok_adet"));
			col_stok_fiyat.setCellValueFactory(new PropertyValueFactory<>("stok_fiyat"));
			col_stok_tarih.setCellValueFactory(new PropertyValueFactory<>("stok_tarih"));
			tableview_stok.setItems(stokliste);
		} catch (Exception e) {
			// TODO: handle exception
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Hata!");
			alert.setHeaderText("Tablo Boş...");
			alert.showAndWait();
		}
		sql = "select urun_ad from urunler";
		ObservableList<String> urunListesi = FXCollections.observableArrayList();
		try {
			sorguIfadesi = baglanti.prepareStatement(sql);
			ResultSet getirilen = sorguIfadesi.executeQuery();
			while (getirilen.next()) {
				urunListesi.add(getirilen.getString("urun_ad"));
			}
			cmb_stok_ad.setItems(urunListesi);
		} catch (SQLException e) {
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

	@FXML
	void tableview_stok_MouseClick(MouseEvent event) {
		stok stok = new stok();
		stok = (stok) tableview_stok.getItems().get(tableview_stok.getSelectionModel().getSelectedIndex());
		txt_stok_id.setText(String.valueOf(stok.getStok_id()));
		cmb_stok_ad.setValue(stok.getStok_ad());
		txt_stok_adet.setText(String.valueOf(stok.getStok_adet()));
		txt_stok_fiyat.setText(String.valueOf(stok.getStok_fiyat()));
	}

	@FXML
	void initialize() {
		degerleriGetir();
	}
}
