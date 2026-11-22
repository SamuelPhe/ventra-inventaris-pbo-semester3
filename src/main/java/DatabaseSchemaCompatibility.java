import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Applies idempotent schema compatibility changes required by the original
 * Ventra desktop application and the legacy MariaDB database export.
 */
public final class DatabaseSchemaCompatibility {
    private DatabaseSchemaCompatibility() {
    }

    public static void main(String[] args) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(
                "ALTER TABLE inbound_detail " +
                "ADD COLUMN IF NOT EXISTS harga DECIMAL(12,2) NOT NULL DEFAULT 0 AFTER qty"
            );
            statement.executeUpdate(
                "ALTER TABLE outbound_detail " +
                "ADD COLUMN IF NOT EXISTS harga_jual DECIMAL(12,2) NOT NULL DEFAULT 0 AFTER qty"
            );
            statement.executeUpdate(
                "ALTER TABLE outbound_detail " +
                "ADD COLUMN IF NOT EXISTS subtotal DECIMAL(14,2) NOT NULL DEFAULT 0 AFTER harga_jual"
            );

            int inboundPrices = statement.executeUpdate(
                "UPDATE inbound_detail d " +
                "JOIN produk p ON p.id_produk = d.id_produk " +
                "SET d.harga = p.harga " +
                "WHERE d.harga = 0 AND p.harga IS NOT NULL"
            );
            int outboundPrices = statement.executeUpdate(
                "UPDATE outbound_detail d " +
                "JOIN produk p ON p.id_produk = d.id_produk " +
                "SET d.harga_jual = ROUND(p.harga * 1.08, 0), " +
                "d.subtotal = d.qty * ROUND(p.harga * 1.08, 0) " +
                "WHERE (d.harga_jual = 0 OR d.subtotal = 0) AND p.harga IS NOT NULL"
            );

            System.out.println(
                "[Ventra] Harga inbound historis diperiksa; baris diperbarui: " + inboundPrices
            );
            System.out.println(
                "[Ventra] Harga outbound historis diperiksa; baris diperbarui: " + outboundPrices
            );
        }
    }
}
