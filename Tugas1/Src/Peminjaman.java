import java.util.Date;

public class Peminjaman {

  private String idPeminjaman;
  private NamaBarang barang; // Tipe data diubah dari String ke NamaBarang
  private String namaPeminjam;
  private Date tanggalPinjam;
  private Date tanggalKembali;
  private String status;

  public Peminjaman(String id, NamaBarang barang, String peminjam, Date tglPinjam) {
    this.idPeminjaman = id;
    this.barang = barang; // Disimpan sebagai objek
    this.namaPeminjam = peminjam;
    this.tanggalPinjam = tglPinjam;
    this.status = "Dipinjam";
    this.tanggalKembali = null;
  }

  public String getIdPeminjaman() { return idPeminjaman; }
  public NamaBarang getBarang() { return barang; } // Getter mengembalikan objek NamaBarang
  public String getNamaPeminjam() { return namaPeminjam; }
  public Date getTanggalPinjam() { return tanggalPinjam; }
  public Date getTanggalKembali() { return tanggalKembali; }
  public String getStatus() { return status; }


  public void prosesPengembalian(Date tglKembali) {
    if (tglKembali.before(this.tanggalPinjam)) {
      System.out.println("\n❌ ERROR: Tanggal kembali tidak boleh sebelum tanggal pinjam!");
    } else {
      this.status = "Dikembalikan";
      this.tanggalKembali = tglKembali;
    }
  }
}