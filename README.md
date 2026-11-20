# Ventra — Inventory Management Application

Proyek aplikasi desktop pengelolaan inventaris yang dibuat sebagai tugas akademik Pemrograman Berorientasi Objek (PBO) Semester 3.

## Fitur yang terlihat pada source
- Login dan pengelolaan akun pengguna.
- Master data produk dan pengguna.
- Pencatatan stok dan penyesuaian stok.
- Pencatatan barang masuk (inbound).
- Pencatatan barang keluar (outbound).
- Data pengiriman dan detail transaksi inventaris.

## Teknologi
- Java 17
- Java Swing (form GUI NetBeans)
- Maven
- MySQL / JDBC
- JCalendar

## Struktur proyek
- `pom.xml`: konfigurasi Maven.
- `src/main/java`: source code Java dan form NetBeans.
- `src/main/resources/images`: dua aset logo yang tersedia. Beberapa referensi gambar antarmuka pada form mungkin belum ikut terarsip, sehingga tampilan tertentu perlu diperiksa setelah proyek dijalankan.
- `database/`: catatan persiapan database dan migrasi kolom password.

## Persiapan menjalankan
1. Instal JDK 17, Maven, dan MySQL/MariaDB.
2. Siapkan database bernama `uas3` beserta tabel yang dipakai aplikasi.
3. Atur environment variable sebelum menjalankan aplikasi:
   - `DB_URL` (opsional; default `jdbc:mysql://localhost:3306/uas3?serverTimezone=UTC`)
   - `DB_USER` (wajib)
   - `DB_PASSWORD` (wajib)
4. Jalankan `mvn clean compile` untuk kompilasi.
5. Jalankan `mvn exec:java` untuk membuka form login.

**Catatan database:** arsip proyek yang tersedia tidak menyertakan dump SQL atau dokumentasi skema database. Repository ini belum dapat membuat database dari nol. Pulihkan skema database asli terlebih dahulu. Kolom `user.password` harus berukuran minimal `VARCHAR(255)`; gunakan skrip `database/password_hash_migration.sql` pada database salinan.

## Keamanan dan batasan
- Koneksi database dipusatkan dan membaca kredensial dari environment variable.
- Password baru dan password yang diubah disimpan menggunakan PBKDF2-HMAC-SHA256 dengan salt acak.
- Password lama dalam format teks biasa hanya diterima untuk migrasi ketika login berhasil, lalu otomatis diganti dengan hash. Pastikan database telah dimigrasikan sebelum menggunakan versi ini.
- Proyek ini tugas akademik dan belum diuji sebagai aplikasi produksi. Lakukan pengujian fungsional pada database salinan sebelum digunakan.
