import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class Tampilan {
  private Scanner input = new Scanner(System.in);
  private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

  public void tampilkanHeader() {
    System.out.println("==============================================");
    System.out.println("SISTEM PENCATATAN PEMINJAMAN BARANG JTK");
    System.out.println("==============================================");
  }

  public String mintaInputString(String pesan) {
    System.out.print(pesan);
    return input.nextLine();
  }

  public Date mintaInputTanggal(String pesan) {
    Date tanggal = null;
    while (tanggal == null) {
      System.out.print(pesan);
      String tanggalStr = input.nextLine();
      try {
        tanggal = dateFormat.parse(tanggalStr);
      } catch (ParseException e) {
        System.out.println("❌ Format tanggal salah! Harap gunakan format YYYY-MM-DD.");
      }
    }
    return tanggal;
  }

  public void tampilkanInfoPeminjaman(Peminjaman peminjaman) {
    SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
    String tglPinjamStr = formatter.format(peminjaman.getTanggalPinjam());
    String tglKembaliStr = (peminjaman.getTanggalKembali() != null) ? formatter.format(peminjaman.getTanggalKembali()) : "-";

    String namaBarangStr = peminjaman.getBarang().getNama();

    System.out.println("--- Rincian Peminjaman ---");
    System.out.println("ID Peminjaman  : " + peminjaman.getIdPeminjaman());
    System.out.println("Nama Barang    : " + namaBarangStr);
    System.out.println("Peminjam       : " + peminjaman.getNamaPeminjam());
    System.out.println("Tgl. Pinjam    : " + tglPinjamStr);
    System.out.println("Tgl. Kembali   : " + tglKembaliStr);
    System.out.println("Status         : " + peminjaman.getStatus());
    System.out.println("--------------------------");
  }

  public void tampilkanPesan(String pesan) {
    System.out.println(pesan);
  }

  public void tutupScanner() {
    this.input.close();
  }
}
