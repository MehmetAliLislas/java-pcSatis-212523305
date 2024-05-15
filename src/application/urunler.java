package application;

public class urunler {
  private int urun_id;
  private String urun_ad;
  private int urun_fiyat;
  private String urun_cpu;
  private String urun_gpu;
  private String urun_ram;
  private String urun_disk;

  urunler() {}

  urunler(
    int urun_id,
    String urun_ad,
    int urun_fiyat,
    String urun_cpu,
    String urun_gpu,
    String urun_ram,
    String urun_disk
  ) {
    // TODO Auto-generated constructor stub
    this.urun_id = urun_id;
    this.urun_ad = urun_ad;
    this.urun_fiyat = urun_fiyat;
    this.urun_cpu = urun_cpu;
    this.urun_gpu = urun_gpu;
    this.urun_ram = urun_ram;
    this.urun_disk = urun_disk;
  }

  public int getUrun_id() {
    return urun_id;
  }

  public void setUrun_id(int urun_id) {
    this.urun_id = urun_id;
  }

  public String getUrun_ad() {
    return urun_ad;
  }

  public void setUrun_ad(String urun_ad) {
    this.urun_ad = urun_ad;
  }

  public int getUrun_fiyat() {
    return urun_fiyat;
  }

  public String getUrun_cpu() {
    return urun_cpu;
  }

  public String getUrun_gpu() {
    return urun_gpu;
  }

  public String getUrun_ram() {
    return urun_ram;
  }

  public String getUrun_disk() {
    return urun_disk;
  }
}
