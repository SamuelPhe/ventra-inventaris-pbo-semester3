# Ventra Nostalgia — GitHub Codespaces

This Codespace runs two separate interfaces against the same local MariaDB database:

- The original Java Swing application in a private virtual desktop
- A smaller PHP web login and inventory-summary dashboard

## Open the browser-based web version

1. Open this repository on GitHub.
2. Select **Code → Codespaces → Create codespace on main** (or resume the existing Ventra Codespace).
3. Wait for the Codespace setup to finish. The PHP/Apache service starts automatically.
4. Open the **Ports** tab in the Codespaces editor.
5. Find **Ventra web (private)**, port `8081`, and choose **Open in Browser**.

The browser should show the Ventra PHP login page. After login, the page shows product, stock, inbound, outbound, delivery, and recent-inbound summaries from MariaDB.

## Open the original Java desktop application

1. Open the **Ports** tab.
2. Find **Ventra desktop (private)**, port `6080`.
3. Choose **Open in Browser** to see the virtual desktop with the original Java Swing login form.

Keep both ports **Private**. Do not change them to Public.

If this Codespace was created before the web service/port was added or updated, open the Command Palette and run **Codespaces: Rebuild Container**. This applies the current Compose configuration; it does not require deleting the Codespace or its named database volume.

## Database and scope

The Codespace restores `uas3 (6).sql` into its own MariaDB container and applies the password and schema compatibility migrations. The PHP web version is a separate, limited browser interface for login and dashboard summaries; it does not yet replace the original Swing modules for inbound, outbound, stock adjustment, and master data.

The SQL dump is in a public repository and contains legacy sample credentials and records. Treat those values as exposed. This Codespace is intended for personal nostalgia/testing, not real inventory or account data.

## Stop and resume

Stop the Codespace when finished. When resumed, the Compose services and Java startup script run again. MariaDB data remains in the named volume while the Codespace and volume are retained.
