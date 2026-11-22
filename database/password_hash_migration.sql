-- Apply to a local copy of database uas3 before using the updated application.
ALTER TABLE `user`
    MODIFY COLUMN `password` VARCHAR(255) NOT NULL;

-- The application stores the unit price at the time each inbound is received.
-- Keep historical inbound prices even when the current product price changes.
ALTER TABLE `inbound_detail`
    ADD COLUMN IF NOT EXISTS `harga` DECIMAL(12,2) NOT NULL DEFAULT 0 AFTER `qty`;

-- Populate historical rows from their product's current price in this legacy dump.
UPDATE `inbound_detail` d
JOIN `produk` p ON p.`id_produk` = d.`id_produk`
SET d.`harga` = p.`harga`
WHERE d.`harga` = 0 AND p.`harga` IS NOT NULL;
