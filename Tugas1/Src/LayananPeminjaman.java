import java.util.Date;

public class LayananPeminjaman {
  private Tampilan tampilan;

  public LayananPeminjaman() {
    this.tampilan = new Tampilan();
  }

  public void mulai() {
    tampilan.tampilkanHeader();

    String namaPeminjam = tampilan.mintaInputString("Masukkan Nama Peminjam : ");
    String namaBarangStr = tampilan.mintaInputString("Masukkan Nama Barang   : "); // Dapat input sbg String
    Date tglPinjam = tampilan.mintaInputTanggal("Tanggal Pinjam (YYYY-MM-DD): ");

    // objek nama barang dari input String
    NamaBarang barang = new NamaBarang(namaBarangStr);

    String idPinjam = "JTK-" + (int)(Math.random() * 1000);

    //object peminjaman dengan memasukkan objek nama barang
    Peminjaman peminjaman = new Peminjaman(idPinjam, barang, namaPeminjam, tglPinjam);

    tampilan.tampilkanPesan("\n✔ Data Peminjaman Berhasil Dibuat!");
    tampilan.tampilkanInfoPeminjaman(peminjaman);

    //proses pengembalian
    tampilan.tampilkanPesan("\n--- Proses Pengembalian Barang ---");
    Date tglKembali = tampilan.mintaInputTanggal("Masukkan Tanggal Pengembalian (YYYY-MM-DD): ");

    peminjaman.prosesPengembalian(tglKembali);

    if(peminjaman.getStatus().equals("Dikembalikan")) {
      tampilan.tampilkanPesan("\n✅ Proses Pengembalian Berhasil!");
    }

    tampilan.tampilkanPesan("\n🧾 Status Terbaru Peminjaman:");
    tampilan.tampilkanInfoPeminjaman(peminjaman);

    tampilan.tutupScanner();
  }
}
