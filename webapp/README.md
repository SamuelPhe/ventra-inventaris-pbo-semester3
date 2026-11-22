# Ventra PHP web login (database-backed)

This folder is a PHP/MySQL web entry point for the Ventra inventory database. It is separate from the GitHub Pages browser preview because GitHub Pages cannot execute PHP or connect to MySQL.

## Requirements

- PHP 8.1 or newer
- PDO MySQL extension
- MySQL or MariaDB
- HTTPS in production

## Database

1. Import `uas3 (6).sql` into a database named `uas3` (or another name configured below).
2. Run the existing `database/password_hash_migration.sql` once. It expands `user.password` to `VARCHAR(255)`, which is required for the PBKDF2 password hashes used by both this PHP login and the Java desktop client.
3. Because the original SQL dump was committed to a public repository with legacy plaintext demo passwords and user data, treat those passwords as exposed. Reset them in the database before public use.

## Environment variables

Configure these in the PHP hosting control panel. Do not commit real credentials:

- `DB_HOST` (default `127.0.0.1`)
- `DB_PORT` (default `3306`)
- `DB_DATABASE` (default `uas3`)
- `DB_USER` (required)
- `DB_PASSWORD` (required)

Point the web document root at this `webapp` directory, or copy its contents to the hosting document root.

## Login behavior

The login query uses the original `user` table and prepared statements. Existing plaintext passwords are verified once, then upgraded to the PBKDF2-SHA256 format already supported by Ventra's `PasswordUtil.java`. The database migration must be applied before the first login.

The dashboard reads product, stock, inbound, outbound, and delivery counts from the same database. It is not a complete port of every Swing form yet.

## Local check

Run `php -S 127.0.0.1:8080 -t webapp` from the repository root after configuring environment variables.
