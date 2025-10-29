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

        String namaMhs1 = "Alya Putri";
        String nimMhs1 = "2101001";
        String jurusanMhs1 = "Teknik Informatika";
        double ipkMhs1 = 3.50;

        String namaMhs2 = "Budi Santoso";
        String nimMhs2 = "2101002";
        String jurusanMhs2 = "Sistem Informasi";
        double ipkMhs2 = 2.60;

        String namaMhs3 = "Citra Dewi";
        String nimMhs3 = "2101003";
        String jurusanMhs3 = "Teknik Komputer";
        double ipkMhs3 = 3.00;

        // Print semua data mahasiswa menggunakan cara prosedural
        // Implementasikan printing untuk semua mahasiswa
        System.out.println("\nData Mahasiswa (Prosedural):");
        System.out.println("1. Nama: " + namaMhs1 + " | NIM: " + nimMhs1 + " | Jurusan: " + jurusanMhs1 + " | IPK: " + ipkMhs1);
        System.out.println("2. Nama: " + namaMhs2 + " | NIM: " + nimMhs2 + " | Jurusan: " + jurusanMhs2 + " | IPK: " + ipkMhs2);
        System.out.println("3. Nama: " + namaMhs3 + " | NIM: " + nimMhs3 + " | Jurusan: " + jurusanMhs3 + " | IPK: " + ipkMhs3);

        System.out.println("\\n=== MASALAH DENGAN CARA PROSEDURAL ===");
        // Tuliskan dalam komentar 3 masalah yang Anda lihat dari cara di atas

        // ===== DENGAN OOP: CARA OBJECT-ORIENTED =====
        System.out.println("\\n=== DENGAN OOP: CARA OBJECT-ORIENTED ===");

        // Latihan 2: Menggunakan class Mahasiswa (akan dibuat di bawah)
        // Buat 3 object Mahasiswa
        // Set data untuk setiap mahasiswa
        // Print menggunakan method dari class

        // Buat object mahasiswa dan isi datanya
        Mahasiswa m1 = new Mahasiswa("Alya Putri", "2101001", "Teknik Informatika", 3.50);
        Mahasiswa m2 = new Mahasiswa("Budi Santoso", "2101002", "Sistem Informasi", 2.60);
        Mahasiswa m3 = new Mahasiswa("Citra Dewi", "2101003", "Teknik Komputer", 3.00);

        // ===== ANALOGI DUNIA NYATA =====
        System.out.println("\\n=== ANALOGI DUNIA NYATA ===");

        // Latihan 3: Implementasi analogi perpustakaan
        // Buat beberapa object Buku
        // Buat object Mahasiswa yang meminjam buku
        // Simulasikan proses peminjaman

        // Implementasikan analogi perpustakaan

        // ===== KEUNTUNGAN OOP =====
        System.out.println("\\n=== KEUNTUNGAN OOP ===");
        // Tuliskan dalam komentar 5 keuntungan OOP yang Anda rasakan
    }
}

// ===== DEFINISI CLASS =====

// Buat class Mahasiswa dengan struktur berikut:
class Mahasiswa {
    // Instance variables
    // Definisikan instance variables untuk nama, nim, jurusan, ipk

    // Constructor
    // Buat constructor untuk inisialisasi data mahasiswa

    // Methods
    // Buat method untuk menampilkan informasi mahasiswa
    // Buat method untuk mengecek apakah mahasiswa lulus (IPK >= 2.75)
    // Buat method untuk menghitung predikat berdasarkan IPK
}

// Buat class Buku dengan struktur berikut:
class Buku {
    // Instance variables
    // Definisikan variables untuk judul, penulis, isbn, tahunTerbit, tersedia

    // Constructor
    // Buat constructor

    // Methods
    // Buat method untuk menampilkan info buku
    // Buat method untuk meminjam buku
    // Buat method untuk mengembalikan buku
}

// Buat class Perpustakaan yang mengelola buku dan peminjaman
class Perpustakaan {
    // Implementasikan class perpustakaan
}
