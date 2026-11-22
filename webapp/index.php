<?php
declare(strict_types=1);
require __DIR__ . '/config.php';

$error = '';
$notice = '';
$user = $_SESSION['ventra_user'] ?? null;

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    if (!hash_equals($_SESSION['csrf_token'] ?? '', (string)($_POST['csrf_token'] ?? ''))) {
        $error = 'Sesi formulir tidak valid. Muat ulang halaman dan coba lagi.';
    } elseif (isset($_POST['logout'])) {
        $_SESSION = [];
        session_regenerate_id(true);
        $_SESSION['csrf_token'] = bin2hex(random_bytes(32));
        header('Location: index.php');
        exit;
    } else {
        $username = trim((string)($_POST['username'] ?? ''));
        $password = (string)($_POST['password'] ?? '');
        if ($username === '' || $password === '') {
            $error = 'Username dan password wajib diisi.';
        } else {
            try {
                $pdo = ventra_db();
                $stmt = $pdo->prepare('SELECT id, username, password FROM user WHERE username = ? LIMIT 1');
                $stmt->execute([$username]);
                $account = $stmt->fetch();
                if (!$account || !ventra_password_verify($password, (string)$account['password'])) {
                    $error = 'Username atau password salah.';
                } else {
                    $stored = (string)$account['password'];
                    if (!str_starts_with($stored, 'pbkdf2-sha256$')) {
                        $upgrade = $pdo->prepare('UPDATE user SET password = ? WHERE id = ?');
                        $upgrade->execute([ventra_password_hash($password), (int)$account['id']]);
                    }
                    session_regenerate_id(true);
                    $_SESSION['ventra_user'] = [
                        'id' => (int)$account['id'],
                        'username' => (string)$account['username'],
                    ];
                    $_SESSION['csrf_token'] = bin2hex(random_bytes(32));
                    header('Location: index.php');
                    exit;
                }
            } catch (Throwable $exception) {
                error_log('Ventra login error: ' . $exception->getMessage());
                $error = 'Koneksi database belum siap. Periksa konfigurasi hosting.';
            }
        }
    }
}

$user = $_SESSION['ventra_user'] ?? null;
$stats = ['products' => 0, 'stock' => 0, 'inbound' => 0, 'outbound' => 0, 'deliveries' => 0];
$recent = [];
if ($user) {
    try {
        $pdo = ventra_db();
        $stats['products'] = (int)$pdo->query('SELECT COUNT(*) FROM produk')->fetchColumn();
        $stats['stock'] = (int)$pdo->query('SELECT COALESCE(SUM(stok), 0) FROM produk')->fetchColumn();
        $stats['inbound'] = (int)$pdo->query('SELECT COUNT(*) FROM inbound')->fetchColumn();
        $stats['outbound'] = (int)$pdo->query('SELECT COUNT(*) FROM outbound')->fetchColumn();
        $stats['deliveries'] = (int)$pdo->query('SELECT COUNT(*) FROM delivery')->fetchColumn();
        $recent = $pdo->query(
            'SELECT p.nama_produk, i.qty, h.envelope_code, h.tanggal
             FROM inbound_detail i
             JOIN inbound h ON h.id_inbound = i.id_inbound
             JOIN produk p ON p.id_produk = i.id_produk
             ORDER BY h.tanggal DESC, h.id_inbound DESC LIMIT 8'
        )->fetchAll();
    } catch (Throwable $exception) {
        error_log('Ventra dashboard error: ' . $exception->getMessage());
        $notice = 'Sebagian ringkasan belum dapat dimuat dari database.';
    }
}
?>
<!doctype html>
<html lang="id">
<head>
<meta charset="utf-8"><meta name="viewport" content="width=device-width,initial-scale=1">
<title>Ventra — Inventory System</title>
<style>
*{box-sizing:border-box}body{margin:0;background:#e8eeee;color:#173a3c;font:15px/1.45 "Segoe UI",Arial,sans-serif;min-height:100vh;display:grid;place-items:center;padding:18px}
button,input{font:inherit}.window{width:min(1024px,100%);min-height:600px;background:#fff;display:grid;grid-template-columns:1fr 1fr;box-shadow:0 16px 44px #003b3e25;border:1px solid #c5d1d0}
.brand{background:#006666;color:#fff;display:flex;flex-direction:column;align-items:center;justify-content:center;padding:36px}
.brand img{width:186px;height:186px;object-fit:contain}.brand h1{margin:10px 0 0;color:#ffcc66;font:700 36px Tahoma,Arial,sans-serif;letter-spacing:1px}
.login{padding:54px 58px;display:flex;flex-direction:column;justify-content:center;background:#fff}.login h2{text-align:center;color:#006666;font:700 24px "Segoe UI",Arial;margin:0 0 40px}
label{display:block;margin:0 0 9px;color:#999;font:18px Tahoma,Arial,sans-serif}.field{width:100%;height:42px;border:1px solid #cbd3d3;padding:7px 11px;font-size:17px;margin-bottom:18px;outline-color:#006666}
.actions{display:flex;justify-content:flex-end;margin:0 0 30px}.btn{border:0;border-radius:2px;background:#006666;color:#fff;padding:10px 18px;cursor:pointer;font-weight:600}.btn:hover{background:#005455}.submit{width:100%;height:46px;font-size:18px}.error{background:#fff0f0;border:1px solid #e9b7b7;color:#982d2d;padding:10px 12px;margin:-22px 0 20px;font-size:13px}
.dashboard{grid-column:1/-1;padding:28px 34px}.bar{display:flex;justify-content:space-between;align-items:center;border-bottom:7px solid #ff9900;padding:0 0 15px;gap:16px}.bar h1{margin:0;color:#006064;font:700 26px Tahoma,Arial}.bar form{margin:0}.welcome{margin:22px 0;color:#006064}.tiles{display:grid;grid-template-columns:repeat(4,1fr);gap:14px}.tile{border:1px solid #cad6d5;border-top:5px solid #ff9900;padding:18px;background:#f8fbfa}.tile small{display:block;color:#547173}.tile strong{display:block;font-size:28px;margin-top:8px}.menu{margin-top:26px;background:#ff9900;color:white;padding:12px 16px;font:700 24px Tahoma,Arial}.groups{display:grid;grid-template-columns:repeat(3,1fr);gap:14px;margin-top:14px}.groups section{border:1px solid #cbd7d6;background:#f8fbfa;padding:12px}.groups h3{margin:-12px -12px 12px;padding:9px 12px;background:#006064;color:white;font:700 18px Tahoma,Arial}.groups a{display:block;background:#006666;color:white;text-decoration:none;padding:11px;margin:8px 0}.groups a:hover{background:#ff9900;color:#004f53}.table{width:100%;border-collapse:collapse;margin-top:20px}.table th,.table td{text-align:left;padding:10px;border-bottom:1px solid #e3e9e8}.table th{background:#edf3f2;color:#315759;font-size:12px;text-transform:uppercase}
@media(max-width:700px){.window{grid-template-columns:1fr}.brand{min-height:220px}.brand img{width:110px;height:110px}.brand h1{font-size:29px}.login{padding:32px 24px}.dashboard{padding:22px 18px}.tiles{grid-template-columns:repeat(2,1fr)}.groups{grid-template-columns:1fr}}
</style>
</head><body>
<div class="window">
<?php if (!$user): ?>
<section class="brand"><img src="../src/main/resources/images/Logo%20(1)_1.png" alt="Ventra"><h1>VENTRA</h1></section>
<section class="login">
<h2>USER LOGIN</h2>
<?php if ($error !== ''): ?><div class="error"><?=ventra_e($error)?></div><?php endif; ?>
<form method="post" autocomplete="on">
<input type="hidden" name="csrf_token" value="<?=ventra_e($_SESSION['csrf_token'])?>">
<label for="username">Username</label>
<input class="field" id="username" name="username" type="text" autocomplete="username" required>
<label for="password">Password</label>
<input class="field" id="password" name="password" type="password" autocomplete="current-password" required>
<div class="actions"><button class="btn" type="reset">Reset</button></div>
<button class="btn submit" type="submit">Log in</button>
</form>
</section>
<?php else: ?>
<section class="dashboard">
<div class="bar"><h1>VENTRA — Dashboard</h1><form method="post"><input type="hidden" name="csrf_token" value="<?=ventra_e($_SESSION['csrf_token'])?>"><button class="btn" name="logout" value="1">Log out</button></form></div>
<p class="welcome">Welcome, <b><?=ventra_e($user['username'])?></b></p>
<?php if ($notice !== ''): ?><div class="error"><?=ventra_e($notice)?></div><?php endif; ?>
<div class="tiles">
<div class="tile"><small>Total Product</small><strong><?=$stats['products']?></strong></div>
<div class="tile"><small>Total Stock</small><strong><?=$stats['stock']?></strong></div>
<div class="tile"><small>Inbound</small><strong><?=$stats['inbound']?></strong></div>
<div class="tile"><small>Outbound</small><strong><?=$stats['outbound']?></strong></div>
</div>
<div class="menu">Ventra's Menu</div>
<div class="groups">
<section><h3>Transaction</h3><a href="#inbound">InBound</a><a href="#outbound">OutBound</a><a href="#delivery">Delivery (<?=$stats['deliveries']?>)</a></section>
<section><h3>Data</h3><a href="#stock">Stock Data / Adjustment</a></section>
<section><h3>Master</h3><a href="#products">Master Product</a><a href="#users">Master User</a></section>
</div>
<h2 style="color:#006064;margin-top:28px">Recent Inbound Records</h2>
<table class="table"><thead><tr><th>Product</th><th>Qty</th><th>Envelope</th><th>Date</th></tr></thead><tbody>
<?php foreach ($recent as $row): ?><tr><td><?=ventra_e((string)$row['nama_produk'])?></td><td><?= (int)$row['qty'] ?></td><td><?=ventra_e((string)$row['envelope_code'])?></td><td><?=ventra_e((string)$row['tanggal'])?></td></tr><?php endforeach; ?>
<?php if (!$recent): ?><tr><td colspan="4">Belum ada catatan inbound.</td></tr><?php endif; ?>
</tbody></table>
</section>
<?php endif; ?>
</div></body></html>
