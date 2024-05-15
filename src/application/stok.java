package application;

import java.sql.Date;

public class stok {
	private int stok_id;
	private String stok_ad;
	private int stok_adet;
	private int stok_fiyat;
	private Date stok_tarih;

	stok() {
	}

	stok(int stok_id, String stok_ad, int stok_adet, int stok_fiyat, Date stok_tarih) {
		this.stok_id = stok_id;
		this.stok_ad = stok_ad;
		this.stok_adet = stok_adet;
		this.stok_fiyat = stok_fiyat;
		this.stok_tarih = stok_tarih;
	}

	public int getStok_id() {
		return stok_id;
	}

	public void setStok_id(int stok_id) {
		this.stok_id = stok_id;
	}

	public String getStok_ad() {
		return stok_ad;
	}

	public void setStok_ad(String stok_ad) {
		this.stok_ad = stok_ad;
	}

	public int getStok_adet() {
		return stok_adet;
	}

	public void setStok_adet(int stok_adet) {
		this.stok_adet = stok_adet;
	}

	public int getStok_fiyat() {
		return stok_fiyat;
	}

	public void setStok_fiyat(int stok_fiyat) {
		this.stok_fiyat = stok_fiyat;
	}

	public Date getStok_tarih() {
		return stok_tarih;
	}

	public void setStok_tarih(Date stok_tarih) {
		this.stok_tarih = stok_tarih;
	}
}
