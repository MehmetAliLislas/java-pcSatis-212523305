package application;

public class yonetim {
  private int kul_id;
  private String kul_adi;
  private String kul_sifre;

  yonetim() {}

  yonetim(int kul_id, String kul_adi, String kul_sifre) {
    // TODO Auto-generated constructor stub
    this.kul_id = kul_id;
    this.kul_adi = kul_adi;
    this.kul_sifre = kul_sifre;
  }

  public int getKul_id() {
    return kul_id;
  }

  public void setKul_id(int kul_id) {
    this.kul_id = kul_id;
  }

  public String getKul_adi() {
    return kul_adi;
  }

  public void setKul_adi(String kul_adi) {
    this.kul_adi = kul_adi;
  }

  public String getKul_sifre() {
    return kul_sifre;
  }

  public void setKul_sifre(String kul_sifre) {
    this.kul_sifre = kul_sifre;
  }
}
