# Ventra Nostalgia — GitHub Codespaces

This configuration runs the original Java Swing Ventra application in a private virtual desktop, with the uploaded `uas3 (6).sql` restored into a local MariaDB service.

## Start it

1. Open this repository on GitHub.
2. Select **Code → Codespaces → Create codespace on main**.
3. Wait for the Codespace setup to finish. The original Ventra login form is started automatically.
4. Open the **Ports** tab and open **Ventra desktop (private)**, port `6080`.

The forwarded desktop port is explicitly configured as **Private**. GitHub authentication controls access; the port is not a public demo.

## Database

The Codespace starts its own MariaDB container and imports `uas3 (6).sql`, then applies `database/password_hash_migration.sql` so the password column can hold the PBKDF2 hashes used by Ventra.

The database port is not forwarded. Its credentials are only for the local Codespaces compose network; do not reuse them elsewhere.

## What opens

The original `Login_Form` Java Swing form opens inside the virtual desktop. After a successful login it opens the original `Dashboard` class. Original image assets are loaded from `src/main/resources`.

## Stop and resume

Stop the Codespace when you are finished to avoid using its running time. When you resume it, the desktop and login app are started again automatically. The MariaDB data is kept in the Codespace's named volume while that Codespace is retained.

## Notes

- This does not deploy anything to GitHub Pages or a public web host.
- The SQL dump is in a public repository and contains legacy sample credentials. Treat those credentials as exposed; this private Codespace is for personal nostalgia/testing, not real accounts or sensitive data.
