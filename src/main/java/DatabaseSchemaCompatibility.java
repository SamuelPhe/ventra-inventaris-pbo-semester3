import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Applies small, idempotent schema compatibility changes required by the
 * original Ventra desktop application and older database exports.
 */
public final class DatabaseSchemaCompatibility {
    private DatabaseSchemaCompatibility() {
    }

    public static void main(String[] args) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            try {
                statement.executeUpdate(
                    "ALTER TABLE inbound_detail " +
                    "ADD COLUMN harga DECIMAL(12,2) NOT NULL DEFAULT 0 AFTER qty"
                );
                System.out.println("[Ventra] Kolom inbound_detail.harga ditambahkan.");
            } catch (SQLException e) {
                // MariaDB/MySQL error 1060 means the column already exists.
                if (e.getErrorCode() != 1060) {
                    throw e;
                }
                System.out.println("[Ventra] Kolom inbound_detail.harga sudah tersedia.");
            }

            int updated = statement.executeUpdate(
                "UPDATE inbound_detail d " +
                "JOIN produk p ON p.id_produk = d.id_produk " +
                "SET d.harga = p.harga " +
                "WHERE d.harga = 0 AND p.harga IS NOT NULL"
            );
            System.out.println(
                "[Ventra] Harga historis inbound diperiksa; baris diperbarui: " + updated
            );
        }
    }
}
