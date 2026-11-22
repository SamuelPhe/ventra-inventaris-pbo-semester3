
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.Map;
import java.util.HashMap;
import javax.swing.DefaultComboBoxModel;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;






/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author Gunaw
 */
public class inbound extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(inbound.class.getName());

    Map<String, Integer> mapProduk = new HashMap<>();
    /**
     * Creates new form inbound
     */
    
    private String getStatusProduk(int idProduk) {
    String status = "";

    try {
        Connection conn = DatabaseConnection.getConnection();

        String sql = "SELECT status FROM produk WHERE id_produk = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, idProduk);

        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            status = rs.getString("status");
        }

        rs.close();
        pst.close();
        conn.close();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal cek status produk");
    }

    return status;
}

    public void deleteInbound(int idInbound) throws Exception {

    Connection conn = DatabaseConnection.getConnection();
    conn.setAutoCommit(false);

    try {

        
        String sqlGetDetail = """
            SELECT id_produk, qty
            FROM inbound_detail
            WHERE id_inbound = ?
        """;

        PreparedStatement pstGet = conn.prepareStatement(sqlGetDetail);
        pstGet.setInt(1, idInbound);
        ResultSet rs = pstGet.executeQuery();

        
        String sqlUpdateStock = """
            UPDATE produk
            SET stok = stok - ?
            WHERE id_produk = ?
        """;

        PreparedStatement pstStock = conn.prepareStatement(sqlUpdateStock);

        while (rs.next()) {
            int idProduk = rs.getInt("id_produk");
            int qty = rs.getInt("qty");

            pstStock.setInt(1, qty);
            pstStock.setInt(2, idProduk);
            pstStock.executeUpdate();
        }

     
        String sqlDeleteDetail = """
            DELETE FROM inbound_detail
            WHERE id_inbound = ?
        """;

        PreparedStatement pstDeleteDetail = conn.prepareStatement(sqlDeleteDetail);
        pstDeleteDetail.setInt(1, idInbound);
        pstDeleteDetail.executeUpdate();

 
        String sqlDeleteInbound = """
            DELETE FROM inbound
            WHERE id_inbound = ?
        """;

        PreparedStatement pstDeleteInbound = conn.prepareStatement(sqlDeleteInbound);
        pstDeleteInbound.setInt(1, idInbound);
        pstDeleteInbound.executeUpdate();

        conn.commit();

    } catch (Exception e) {
        conn.rollback();
        throw e;
    } finally {
        conn.close();
    }
}
    
    public class RupiahRenderer extends DefaultTableCellRenderer {

    private final NumberFormat format =
        NumberFormat.getInstance(new Locale("id", "ID"));

    @Override
    public void setValue(Object value) {
        if (value instanceof Number) {
            setText(format.format(value));
            setHorizontalAlignment(JLabel.RIGHT);
        } else {
            super.setValue(value);
        }
    }
}
    
    
    public inbound() {
        initComponents();
        DefaultTableModel model = new DefaultTableModel(
        new Object[]{"ID Produk", "Nama Produk", "Qty", "Harga", "Subtotal"}, 0
    );
    jTable1.setModel(model);
    jTable1.getColumnModel().getColumn(3)
    .setCellRenderer(new RupiahRenderer());

jTable1.getColumnModel().getColumn(4)
    .setCellRenderer(new RupiahRenderer());

    jComboBox2.removeAllItems();
    loadComboProduk();
    loadInboundHeader();
    }

    private void loadComboProduk() {
       DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        mapProduk.clear();
        try {
        Connection conn = DatabaseConnection.getConnection();

        String sql = "SELECT id_produk, nama_produk FROM produk WHERE status = 'aktif' ORDER BY nama_produk";
        PreparedStatement pst = conn.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        jComboBox2.removeAllItems(); 

        while (rs.next()) {
    int id = rs.getInt("id_produk");
    String nama = rs.getString("nama_produk");
    model.addElement(nama);
            mapProduk.put(nama, id);
}
jComboBox2.setModel(model);


        rs.close();
        pst.close();
        conn.close();

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
    }
    }
    
    


    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 153, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 36, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(0, 102, 102));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Inbound Data");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addGap(26, 26, 26))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Envelope Code");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Date");

        jTable2.setBackground(new java.awt.Color(255, 153, 0));
        jScrollPane2.setViewportView(jTable2);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Product");

        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Quantity");

        jButton2.setBackground(new java.awt.Color(0, 102, 102));
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Add");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(0, 102, 102));
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Remove");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(0, 102, 102));
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Clear All");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(jTable1);

        jButton1.setBackground(new java.awt.Color(0, 102, 102));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Create");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(0, 102, 102));
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Back");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(0, 102, 102));
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("See Detail");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(0, 102, 102));
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("Delete");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jButton5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton4))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jDateChooser2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jComboBox2, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addComponent(jButton7)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton6))
                                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 719, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(17, 17, 17))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton6)
                            .addComponent(jButton7))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jButton4))
                .addGap(27, 27, 27)
                .addComponent(jButton1)
                .addGap(3, 3, 3)
                .addComponent(jButton3)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
if (jComboBox2.getSelectedItem() == null) return;

    String namaProduk = jComboBox2.getSelectedItem().toString();
    int idProduk = mapProduk.get(namaProduk);
    
String status = getStatusProduk(idProduk);
if (!status.equalsIgnoreCase("aktif")) {
    JOptionPane.showMessageDialog(this,
        "Produk tidak aktif, tidak bisa ditambahkan");
    return;
}


    int qty;
    try {
        qty = Integer.parseInt(jTextField2.getText());
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Qty harus angka");
        return;
    }
    if (qty < 1) {
        JOptionPane.showMessageDialog(this, "Quantity tidak boleh kurang dari 1");
        return;
    }
    
    
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

for (int i = 0; i < model.getRowCount(); i++) {
    int existingId = (int) model.getValueAt(i, 0);

    if (existingId == idProduk) {
        int qtyLama = (int) model.getValueAt(i, 2);
        int harga = (int) model.getValueAt(i, 3);

        int qtyBaru = qtyLama + qty;
        int subtotalBaru = qtyBaru * harga;

        model.setValueAt(qtyBaru, i, 2);
        model.setValueAt(subtotalBaru, i, 4);

        jTextField2.setText("");
        return;
    }
}

    int harga = getHargaProduk(idProduk);
int subtotal = harga * qty;

model.addRow(new Object[]{
    idProduk,
    namaProduk,
    qty,
    harga,
    subtotal
});

    jTextField2.setText("");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
if (jTable1.getRowCount() == 0) {
        JOptionPane.showMessageDialog(this, "Detail inbound masih kosong");
        return;
    }

    try {
        Connection conn = DatabaseConnection.getConnection();
        conn.setAutoCommit(false);
        int userId = UserSession.getUserId();
        // 1. simpan inbound (header)
        String sqlInbound = "INSERT INTO inbound (envelope_code, tanggal, created_by) VALUES (?, ?, ?)";
        PreparedStatement pstInbound = conn.prepareStatement(
            sqlInbound, PreparedStatement.RETURN_GENERATED_KEYS
        );

        pstInbound.setString(1, jTextField1.getText());
        pstInbound.setDate(2, new java.sql.Date(jDateChooser2.getDate().getTime()));
        pstInbound.setInt(3, UserSession.getUserId());
        pstInbound.executeUpdate();


        ResultSet rs = pstInbound.getGeneratedKeys();
        rs.next();
        int idInbound = rs.getInt(1);

        // 2. simpan detail + update stok
        String sqlDetail = "INSERT INTO inbound_detail (id_inbound, id_produk, qty, harga) VALUES (?, ?, ?, ?)";
        String sqlUpdateStok = "UPDATE produk SET stok = stok + ? WHERE id_produk = ?";

        PreparedStatement pstDetail = conn.prepareStatement(sqlDetail);
        PreparedStatement pstStok = conn.prepareStatement(sqlUpdateStok);

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

        for (int i = 0; i < model.getRowCount(); i++) {
            int idProduk = (int) model.getValueAt(i, 0);
            int qty = (int) model.getValueAt(i, 2);
            
            if (qty < 1) {
        JOptionPane.showMessageDialog(this, "Quantity untuk produk ID " + idProduk + " tidak boleh kurang dari 1");
        return; // hentikan proses sebelum simpan
    }

            int harga = (int) model.getValueAt(i, 3);

pstDetail.setInt(1, idInbound);
pstDetail.setInt(2, idProduk);
pstDetail.setInt(3, qty);
pstDetail.setInt(4, harga);
pstDetail.executeUpdate();


            pstStok.setInt(1, qty);
            pstStok.setInt(2, idProduk);
            pstStok.executeUpdate();
        }

        conn.commit();
        JOptionPane.showMessageDialog(this, "Inbound berhasil disimpan");

        model.setRowCount(0);
        loadInboundHeader();

        conn.close();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
        
    }   // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void loadInboundHeader() {
    DefaultTableModel model = new DefaultTableModel(
        new Object[]{"ID Inbound", "Envelope Code", "Tanggal", "User"}, 0
    );

    try {
        Connection conn = DatabaseConnection.getConnection();

        String sql = """
            SELECT i.id_inbound, i.envelope_code, i.tanggal, u.username
            FROM inbound i
            JOIN user u ON u.id = i.created_by
            ORDER BY i.id_inbound DESC
        """;

        PreparedStatement pst = conn.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getInt("id_inbound"),
                rs.getString("envelope_code"),
                rs.getDate("tanggal"),
                rs.getString("username")
            });
        }

        jTable2.setModel(model);

        rs.close();
        pst.close();
        conn.close();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
    }
}

    
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    model.setRowCount(0);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
 int selectedRow = jTable1.getSelectedRow();
    if (selectedRow >= 0) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.removeRow(selectedRow);
    } else {
        JOptionPane.showMessageDialog(this, "Silakan pilih baris yang ingin dihapus");
    }          // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
    int row = jTable2.getSelectedRow();

    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Pilih inbound dulu");
        return;
    }

    int idInbound = (int) jTable2.getValueAt(row, 0);

    new FormInboundDetail(idInbound).setVisible(true);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
 int row = jTable2.getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Pilih inbound yang mau dihapus");
        return;
    }

    int confirm = JOptionPane.showConfirmDialog(
        this,
        "Hapus inbound ini?\nStok akan dikembalikan seperti semula.",
        "Konfirmasi",
        JOptionPane.YES_NO_OPTION
    );

    if (confirm != JOptionPane.YES_OPTION) {
        return;
    }

    int idInbound = (int) jTable2.getValueAt(row, 0);
    try {
    if (inboundSudahDipakai(idInbound)) {
        JOptionPane.showMessageDialog(
            this,
            "Inbound tidak dapat dihapus karena sudah digunakan oleh outbound"
        );
        return;
    }
    
        deleteInbound(idInbound);
        JOptionPane.showMessageDialog(this, "Inbound berhasil dihapus");
        loadInboundHeader(); // reload tabel inbound
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal hapus inbound\n" + e.getMessage());
    }        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private boolean inboundSudahDipakai(int idInbound) throws Exception {
    Connection conn = DatabaseConnection.getConnection();
        String sql =
        "SELECT COUNT(*) " +
        "FROM outbound_detail od " +
        "JOIN outbound o ON o.id_outbound = od.id_outbound " +
        "JOIN inbound_detail idt ON idt.id_produk = od.id_produk " +
        "JOIN inbound i ON i.id_inbound = idt.id_inbound " +
        "WHERE i.id_inbound = ? " +
        "AND o.tanggal > i.tanggal";

    PreparedStatement ps = conn.prepareStatement(sql);
    ps.setInt(1, idInbound);
    ResultSet rs = ps.executeQuery();

    if (rs.next()) {
        return rs.getInt(1) > 0;
    }
    return false;
}
    
    private int getHargaProduk(int idProduk) {
    int harga = 0;

    try {
        Connection conn = DatabaseConnection.getConnection();

        String sql = "SELECT harga FROM produk WHERE id_produk = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, idProduk);

        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            harga = rs.getInt("harga");
        }

        rs.close();
        pst.close();
        conn.close();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal ambil harga produk");
    }

    return harga;
}

    
    
    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new inbound().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    javax.swing.JComboBox<String> jComboBox2;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}

