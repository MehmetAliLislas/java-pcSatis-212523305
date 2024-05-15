package application;

import java.awt.Label;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.*;

public class urunSayfaController {

	public urunSayfaController() {
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
	private Button btn_urun_guncelle;

	@FXML
	private Button btn_urun_kaydet;

	@FXML
	private Button btn_urun_sil;

	@FXML
	private Button btn_urunler;

	@FXML
	private Button btn_yonetim;

	@FXML
	private TableColumn<urunler, String> col_urun_adi;

	@FXML
	private TableColumn<urunler, String> col_urun_cpu;

	@FXML
	private TableColumn<urunler, String> col_urun_disk;

	@FXML
	private TableColumn<urunler, Integer> col_urun_fiyati;

	@FXML
	private TableColumn<urunler, String> col_urun_gpu;

	@FXML
	private TableColumn<urunler, Integer> col_urun_id;

	@FXML
	private TableColumn<urunler, String> col_urun_ram;

	@FXML
	private TableView<urunler> tableview_urunler;

	@FXML
	private TextField txt_arama;

	@FXML
	private TextField txt_urun_ad;

	@FXML
	private TextField txt_urun_id;

	@FXML
	private TextField txt_urun_cpu;

	@FXML
	private TextField txt_urun_disk;

	@FXML
	private TextField txt_urun_fiyat;

	@FXML
	private TextField txt_urun_gpu;

	@FXML
	private TextField txt_urun_ram;

	Connection baglanti = null;
	PreparedStatement sorguIfadesi = null;
	ResultSet getirilen = null;
	String sql;

	public void DegerleriGetir(TableView tablo) {
		sql = "select * from urunler";
		ObservableList<urunler> urunlerliste = FXCollections.observableArrayList();
		try {
			sorguIfadesi = baglanti.prepareStatement(sql);
			ResultSet getirilen = sorguIfadesi.executeQuery();
			while (getirilen.next()) {
				urunlerliste.add(new urunler(getirilen.getInt("urun_id"), getirilen.getString("urun_ad"),
						getirilen.getInt("urun_fiyat"), getirilen.getString("urun_cpu"),
						getirilen.getString("urun_gpu"), getirilen.getString("urun_ram"),
						getirilen.getString("urun_disk")));
			}
			col_urun_id.setCellValueFactory(new PropertyValueFactory<>("urun_id"));
			col_urun_adi.setCellValueFactory(new PropertyValueFactory<>("urun_ad"));
			col_urun_fiyati.setCellValueFactory(new PropertyValueFactory<>("urun_fiyat"));
			col_urun_cpu.setCellValueFactory(new PropertyValueFactory<>("urun_cpu"));
			col_urun_gpu.setCellValueFactory(new PropertyValueFactory<>("urun_gpu"));
			col_urun_ram.setCellValueFactory(new PropertyValueFactory<>("urun_ram"));
			col_urun_disk.setCellValueFactory(new PropertyValueFactory<>("urun_disk"));
			tableview_urunler.setItems(urunlerliste);
		} catch (Exception e) {
			// TODO: handle exception
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Hata!");
			alert.setHeaderText("Tablo Boş...");
			alert.showAndWait();
		}
	}

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
	void btn_urun_guncelle_click(ActionEvent event) {
		sql = "update urunler set urun_ad=?, urun_fiyat=?, urun_cpu=?, urun_gpu=?, urun_ram=?, urun_disk=? where urun_id=? ";
		if (txt_urun_ad.getText().length() < 5) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Hata!");
			alert.setHeaderText("Lütfen Geçerli Bilgi Giriniz.");
			alert.showAndWait();
		} else {
			try {
				sorguIfadesi = baglanti.prepareStatement(sql);
				sorguIfadesi.setString(1, txt_urun_ad.getText());
				sorguIfadesi.setString(2, txt_urun_fiyat.getText());
				sorguIfadesi.setString(3, txt_urun_cpu.getText());
				sorguIfadesi.setString(4, txt_urun_gpu.getText());
				sorguIfadesi.setString(5, txt_urun_ram.getText());
				sorguIfadesi.setString(6, txt_urun_disk.getText());
				sorguIfadesi.setString(7, txt_urun_id.getText());
				sorguIfadesi.executeUpdate();
				temizle();
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("İşlem Başarılı!");
				alert.setHeaderText("Ürün güncelleme işlemi başarıyla gerçekleşti.");
				alert.showAndWait();
				DegerleriGetir(tableview_urunler);
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
	void btn_urun_kaydet_Click(ActionEvent event) {
		sql = "insert into urunler( urun_ad, urun_fiyat, urun_cpu, urun_gpu, urun_ram, urun_disk) values (?,?,?,?,?,?)";
		if (txt_urun_ad.getText().length() < 5) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Hata!");
			alert.setHeaderText("Lütfen Geçerli Bilgi Giriniz.");
			alert.showAndWait();
		} else {
			try {
				sorguIfadesi = baglanti.prepareStatement(sql);
				sorguIfadesi.setString(1, txt_urun_ad.getText());
				sorguIfadesi.setString(2, txt_urun_fiyat.getText());
				sorguIfadesi.setString(3, txt_urun_cpu.getText());
				sorguIfadesi.setString(4, txt_urun_gpu.getText());
				sorguIfadesi.setString(5, txt_urun_ram.getText());
				sorguIfadesi.setString(6, txt_urun_disk.getText());
				sorguIfadesi.executeUpdate();
				temizle();
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("İşlem Başarılı!");
				alert.setHeaderText("Ürün ekleme işlemi başarıyla gerçekleşti.");
				alert.showAndWait();
				DegerleriGetir(tableview_urunler);
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
	void btn_urun_sil_Click(ActionEvent event) {
		sql = "delete from urunler where urun_id=? ";
		if (txt_urun_ad.getText().length() < 5) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Hata!");
			alert.setHeaderText("Lütfen Geçerli Bilgi Giriniz.");
			alert.showAndWait();
		} else {
			try {
				sorguIfadesi = baglanti.prepareStatement(sql);
				sorguIfadesi.setString(1, txt_urun_id.getText());
				sorguIfadesi.executeUpdate();
				temizle();
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("İşlem Başarılı!");
				alert.setHeaderText("Ürün silme işlemi başarıyla gerçekleşti.");
				alert.showAndWait();
				DegerleriGetir(tableview_urunler);
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
		txt_urun_ad.setText("");
		txt_urun_id.setText("");
		txt_urun_gpu.setText("");
		txt_urun_cpu.setText("");
		txt_urun_ram.setText("");
		txt_urun_disk.setText("");
		txt_urun_fiyat.setText("");
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

	@FXML
	void tableview_urunler_MouseClick(MouseEvent event) {
		urunler urun = new urunler();
		urun = (urunler) tableview_urunler.getItems().get(tableview_urunler.getSelectionModel().getSelectedIndex());
		txt_urun_id.setText(String.valueOf(urun.getUrun_id()));
		txt_urun_ad.setText(urun.getUrun_ad());
		txt_urun_fiyat.setText(String.valueOf(urun.getUrun_fiyat()));
		txt_urun_cpu.setText(urun.getUrun_cpu());
		txt_urun_gpu.setText(urun.getUrun_gpu());
		txt_urun_ram.setText(urun.getUrun_ram());
		txt_urun_disk.setText(urun.getUrun_disk());
	}

	@FXML
	void initialize() {
		DegerleriGetir(tableview_urunler);
	}
}
