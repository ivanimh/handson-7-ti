import java.util.Scanner;

public class IntroToOOPPractice {
    public static void main(String[] args) {
        /*
         * PRAKTIK HANDS-ON: Introduction to OOP
         *
         * Instruksi: Lengkapi semua latihan di bawah ini untuk memahami
         * perbedaan antara pemrograman prosedural dan object-oriented.
         */

        // ===== SEBELUM OOP: CARA PROSEDURAL =====
        System.out.println("=== SEBELUM OOP: CARA PROSEDURAL ===");

        // Latihan 1: Data mahasiswa dengan variabel terpisah (cara lama)
        // Buat variabel untuk menyimpan data 3 mahasiswa
        // Setiap mahasiswa memiliki: nama, nim, jurusan, ipk

        String namaMhs1 = "Alya Putri"; // Isi dengan data mahasiswa 1
        String nimMhs1 = "2101001";
        String jurusanMhs1 = "Teknik Informatika";
        double ipkMhs1 = 3.50;

        String namaMhs2 = "Budi Santoso"; // Isi dengan data mahasiswa 2
        String nimMhs2 = "2101002";
        String jurusanMhs2 = "Sistem Informasi";
        double ipkMhs2 = 2.60;

        String namaMhs3 = "Citra Dewi"; // Isi dengan data mahasiswa 3
        String nimMhs3 = "2101003";
        String jurusanMhs3 = "Teknik Komputer";
        double ipkMhs3 = 3.00;

        // Print semua data mahasiswa menggunakan cara prosedural
        // Implementasikan printing untuk semua mahasiswa
        System.out.println("\nData Mahasiswa (Prosedural):");
        System.out.println("1. Nama: " + namaMhs1 + " | NIM: " + nimMhs1 + " | Jurusan: " + jurusanMhs1 + " | IPK: " + ipkMhs1);
        System.out.println("2. Nama: " + namaMhs2 + " | NIM: " + nimMhs2 + " | Jurusan: " + jurusanMhs2 + " | IPK: " + ipkMhs2);
        System.out.println("3. Nama: " + namaMhs3 + " | NIM: " + nimMhs3 + " | Jurusan: " + jurusanMhs3 + " | IPK: " + ipkMhs3);

        System.out.println("\n=== MASALAH DENGAN CARA PROSEDURAL ===");
        // Tuliskan dalam komentar 3 masalah yang Anda lihat dari cara di atas
        // 1) Banyak variabel terpisah -> rawan kesalahan dan sulit dikelola.
        // 2) Sulit menambah behaviour (mis. cek lulus/predikat) tanpa membuat fungsi terpisah.
        // 3) Skalabilitas buruk: kalau banyak data, kode menjadi sangat repetitif.

        // ===== DENGAN OOP: CARA OBJECT-ORIENTED =====
        System.out.println("\n=== DENGAN OOP: CARA OBJECT-ORIENTED ===");

        // Latihan 2: Menggunakan class Mahasiswa (akan dibuat di bawah)
        // Buat 3 object Mahasiswa
        // Set data untuk setiap mahasiswa
        // Print menggunakan method dari class

        // Buat object mahasiswa dan isi datanya
        Mahasiswa m1 = new Mahasiswa("Alya Putri", "2101001", "Teknik Informatika", 3.50);
        Mahasiswa m2 = new Mahasiswa("Budi Santoso", "2101002", "Sistem Informasi", 2.60);
        Mahasiswa m3 = new Mahasiswa("Citra Dewi", "2101003", "Teknik Komputer", 3.00);

        System.out.println("\nData Mahasiswa (OOP):");
        m1.tampilkanInfo();
        System.out.println("-> Lulus? " + (m1.isLulus() ? "Ya" : "Tidak") + " | Predikat: " + m1.getPredikat() + "\n");
        m2.tampilkanInfo();
        System.out.println("-> Lulus? " + (m2.isLulus() ? "Ya" : "Tidak") + " | Predikat: " + m2.getPredikat() + "\n");
        m3.tampilkanInfo();
        System.out.println("-> Lulus? " + (m3.isLulus() ? "Ya" : "Tidak") + " | Predikat: " + m3.getPredikat() + "\n");

        // ===== ANALOGI DUNIA NYATA =====
        System.out.println("\n=== ANALOGI DUNIA NYATA ===");

        // Latihan 3: Implementasi analogi perpustakaan
        // Buat beberapa object Buku
        // Buat object Mahasiswa yang meminjam buku
        // Simulasikan proses peminjaman

        // Implementasikan analogi perpustakaan
        Buku b1 = new Buku("Pemrograman Java", "Andi", "978-1111", 2019, true);
        Buku b2 = new Buku("Struktur Data", "Siti", "978-2222", 2018, true);
        Buku b3 = new Buku("Basis Data", "Joko", "978-3333", 2020, true);

        Perpustakaan perpustakaan = new Perpustakaan(100);
        perpustakaan.tambahBuku(b1);
        perpustakaan.tambahBuku(b2);
        perpustakaan.tambahBuku(b3);

        System.out.println("\nDaftar buku awal di perpustakaan:");
        perpustakaan.tampilkanSemuaBuku();

        System.out.println("\nSimulasi: Alya meminjam 'Pemrograman Java' (ISBN 978-1111)");
        boolean pinjam1 = perpustakaan.pinjamBukuByISBN("978-1111");
        System.out.println("Hasil peminjaman: " + (pinjam1 ? "Berhasil" : "Gagal (tidak tersedia)"));

        System.out.println("\nStatus buku setelah peminjaman:");
        perpustakaan.tampilkanSemuaBuku();

        System.out.println("\nSimulasi: Budi mencoba meminjam 'Pemrograman Java' lagi (ISBN 978-1111)");
        boolean pinjam2 = perpustakaan.pinjamBukuByISBN("978-1111");
        System.out.println("Hasil peminjaman: " + (pinjam2 ? "Berhasil" : "Gagal (tidak tersedia)"));

        System.out.println("\nAlya mengembalikan 'Pemrograman Java' (ISBN 978-1111)");
        boolean kembali1 = perpustakaan.kembalikanBukuByISBN("978-1111");
        System.out.println("Hasil pengembalian: " + (kembali1 ? "Berhasil" : "Gagal (ISBN tidak ditemukan)"));

        System.out.println("\nStatus buku akhir di perpustakaan:");
        perpustakaan.tampilkanSemuaBuku();

        // ===== KEUNTUNGAN OOP =====
        System.out.println("\n=== KEUNTUNGAN OOP ===");
        // Tuliskan dalam komentar 5 keuntungan OOP yang Anda rasakan
        // 1) Encapsulation: data dan behaviour digabung dalam satu class.
        // 2) Reusability: class bisa dipakai kembali untuk banyak object.
        // 3) Maintainability: perbaikan cukup di class terkait, tidak di seluruh kode.
        // 4) Abstraction: menyembunyikan detail implementasi lewat method.
        // 5) Scalability: mudah menambah fitur tanpa merombak keseluruhan program.

        // (Scanner ada sebagai import sesuai template; kita tidak menggunakan input interaktif di contoh ini)
    }
}

// ===== DEFINISI CLASS =====

// Buat class Mahasiswa dengan struktur berikut:
class Mahasiswa {
    // Instance variables
    // Definisikan instance variables untuk nama, nim, jurusan, ipk
    private String nama;
    private String nim;
    private String jurusan;
    private double ipk;

    // Constructor
    // Buat constructor untuk inisialisasi data mahasiswa
    public Mahasiswa(String nama, String nim, String jurusan, double ipk) {
        this.nama = nama;
        this.nim = nim;
        this.jurusan = jurusan;
        this.ipk = ipk;
    }

    // Methods
    // Buat method untuk menampilkan informasi mahasiswa
    public void tampilkanInfo() {
        System.out.println("Nama   : " + nama);
        System.out.println("NIM    : " + nim);
        System.out.println("Jurusan: " + jurusan);
        System.out.println("IPK    : " + ipk);
    }

    // Buat method untuk mengecek apakah mahasiswa lulus (IPK >= 2.75)
    public boolean isLulus() {
        return this.ipk >= 2.75;
    }

    // Buat method untuk menghitung predikat berdasarkan IPK
    public String getPredikat() {
        if (ipk >= 3.50) return "Cumlaude";
        else if (ipk >= 3.00) return "Sangat Baik";
        else if (ipk >= 2.75) return "Baik";
        else return "Tidak Lulus";
    }
}

// Buat class Buku dengan struktur berikut:
class Buku {
    // Instance variables
    // Definisikan variables untuk judul, penulis, isbn, tahunTerbit, tersedia
    private String judul;
    private String penulis;
    private String isbn;
    private int tahunTerbit;
    private boolean tersedia;

    // Constructor
    // Buat constructor
    public Buku(String judul, String penulis, String isbn, int tahunTerbit, boolean tersedia) {
        this.judul = judul;
        this.penulis = penulis;
        this.isbn = isbn;
        this.tahunTerbit = tahunTerbit;
        this.tersedia = tersedia;
    }

    // Methods
    // Buat method untuk menampilkan info buku
    public void tampilkanInfo() {
        System.out.println("Judul   : " + judul);
        System.out.println("Penulis : " + penulis);
        System.out.println("ISBN    : " + isbn);
        System.out.println("Tahun   : " + tahunTerbit);
        System.out.println("Tersedia: " + (tersedia ? "Ya" : "Tidak"));
    }

    // Buat method untuk meminjam buku
    public boolean pinjam() {
        if (tersedia) {
            tersedia = false;
            return true;
        }
        return false;
    }

    // Buat method untuk mengembalikan buku
    public boolean kembalikan() {
        if (!tersedia) {
            tersedia = true;
            return true;
        }
        return false;
    }

    // getter untuk ISBN (dipakai Perpustakaan)
    public String getIsbn() {
        return isbn;
    }
}

// Buat class Perpustakaan yang mengelola buku dan peminjaman
class Perpustakaan {
    // Implementasikan class perpustakaan
    private Buku[] koleksi;
    private int jumlah; // jumlah buku saat ini

    public Perpustakaan(int kapasitas) {
        koleksi = new Buku[kapasitas];
        jumlah = 0;
    }

    public boolean tambahBuku(Buku b) {
        if (jumlah >= koleksi.length) return false; // penuh
        koleksi[jumlah++] = b;
        return true;
    }

    public void tampilkanSemuaBuku() {
        if (jumlah == 0) {
            System.out.println("Tidak ada buku di perpustakaan.");
            return;
        }
        for (int i = 0; i < jumlah; i++) {
            System.out.println("---- Buku " + (i+1) + " ----");
            koleksi[i].tampilkanInfo();
        }
    }

    public boolean pinjamBukuByISBN(String isbn) {
        for (int i = 0; i < jumlah; i++) {
            if (koleksi[i].getIsbn().equals(isbn)) {
                return koleksi[i].pinjam();
            }
        }
        return false; // ISBN tidak ditemukan atau tidak tersedia
    }

    public boolean kembalikanBukuByISBN(String isbn) {
        for (int i = 0; i < jumlah; i++) {
            if (koleksi[i].getIsbn().equals(isbn)) {
                return koleksi[i].kembalikan();
            }
        }
        return false; // ISBN tidak ditemukan
    }
}
