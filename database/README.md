# Database setup

The provided source archive does not include an SQL schema. The Java source uses a MySQL database named `uas3` and references tables such as `user`, `produk`, `inbound`, `inbound_detail`, `outbound`, `outbound_detail`, and `delivery`.

Restore the matching schema from the original coursework environment before running the application. Do not commit real user data or database backups.

The `user.password` column must be `VARCHAR(255)` or wider for PBKDF2 hashes. Existing plain-text passwords can be upgraded after a successful login.
