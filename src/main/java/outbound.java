
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




public class outbound extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(inbound.class.getName());

    Map<String, Integer> mapProduk = new HashMap<>();
    /**
     * Creates new form inbound
     */
    public class OutboundDraft {
    public String envelopeCode;
    public java.util.Date tanggal;
    public int userId;
    public DefaultTableModel detailModel;
}

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
    
    
    public void deleteOutbound(int idOutbound) throws Exception {

    Connection conn = DatabaseConnection.getConnection();
    conn.setAutoCommit(false);

    try {

        String sqlDetail = """
            SELECT id_produk, qty
            FROM outbound_detail
            WHERE id_outbound = ?
        """;

        PreparedStatement pstDetail = conn.prepareStatement(sqlDetail);
        pstDetail.setInt(1, idOutbound);
        ResultSet rs = pstDetail.executeQuery();

        String sqlStock = """
            UPDATE produk
            SET stok = stok + ?
            WHERE id_produk = ?
        """;

        PreparedStatement pstStock = conn.prepareStatement(sqlStock);

        while (rs.next()) {
            int idProduk = rs.getInt("id_produk");
            int qty = rs.getInt("qty");

            pstStock.setInt(1, qty);
            pstStock.setInt(2, idProduk);
            pstStock.executeUpdate();
        }

        String sqlDeleteDelivery = """
            DELETE FROM delivery
            WHERE id_outbound = ?
        """;
        PreparedStatement pstDel = conn.prepareStatement(sqlDeleteDelivery);
        pstDel.setInt(1, idOutbound);
        pstDel.executeUpdate();

        String sqlDeleteDetail = """
            DELETE FROM outbound_detail
            WHERE id_outbound = ?
        """;
        PreparedStatement pstDeleteDetail = conn.prepareStatement(sqlDeleteDetail);
        pstDeleteDetail.setInt(1, idOutbound);
        pstDeleteDetail.executeUpdate();

        String sqlDeleteOutbound = """
            DELETE FROM outbound
            WHERE id_outbound = ?
        """;
        PreparedStatement pstDeleteOutbound = conn.prepareStatement(sqlDeleteOutbound);
        pstDeleteOutbound.setInt(1, idOutbound);
        pstDeleteOutbound.executeUpdate();

        conn.commit();

    } catch (Exception e) {
        conn.rollback();
        throw e;
    } finally {
        conn.close();
    }
}

    private Integer getSelectedOutboundId() {

    int rowOutbound = jTable2.getSelectedRow();
    

   

    if (rowOutbound != -1) {
        return (int) jTable2.getValueAt(rowOutbound, 0);
    }



    JOptionPane.showMessageDialog(this,
        "Pilih data outbound atau delivery terlebih dahulu");
    return null;
}

    
    public outbound() {
        initComponents();
        DefaultTableModel model = new DefaultTableModel(
        new Object[]{"ID Produk", "Nama Produk", "Qty", "Harga Jual", "Subtotal"}
, 0
    );
    jTable1.setModel(model);
    
    jTable1.getColumnModel().getColumn(3).setCellRenderer(new RupiahRenderer());
    jTable1.getColumnModel().getColumn(4).setCellRenderer(new RupiahRenderer());
    
    jComboBox2.removeAllItems();
    loadComboProduk();
    loadOutboundHeader();
    loadDeliveryLog();
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
        jLabel8 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Outbound ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel8)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jTable2.setBackground(new java.awt.Color(255, 153, 0));
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Envelope Code");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Date");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Product");

        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Quantity");

        jScrollPane1.setViewportView(jTable1);

        jButton2.setBackground(new java.awt.Color(0, 102, 102));
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Add");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
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

        jButton5.setBackground(new java.awt.Color(0, 102, 102));
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Remove");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
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

        jButton1.setBackground(new java.awt.Color(0, 102, 102));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Create");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Data Outbound");

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

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Outbound Price = Inbound Price + (Inbound Price*8%)");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBox2, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jDateChooser2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                        .addComponent(jButton5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton4)
                                        .addGap(14, 14, 14))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                                .addComponent(jLabel6)
                                                .addGap(55, 55, 55)
                                                .addComponent(jLabel7)))
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addGap(18, 18, 18)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jButton7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton6))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3))
                    .addComponent(jScrollPane1))
                .addGap(18, 18, 18))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel4))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton7)
                        .addComponent(jButton6)))
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton4)
                            .addComponent(jButton5))
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
                        .addGap(23, 23, 23))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addGap(15, 15, 15))))
        );

        jPanel2.setBackground(new java.awt.Color(255, 153, 0));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
DefaultTableModel model = (DefaultTableModel) jTable1.getModel();




// 1. validasi input
String namaProduk = (String) jComboBox2.getSelectedItem();
if (namaProduk == null || jTextField2.getText().isEmpty()) {
    JOptionPane.showMessageDialog(this, "Produk dan Qty wajib diisi");
    return;
}

int idProduk = mapProduk.get(namaProduk);
int qtyInput;

try {
    qtyInput = Integer.parseInt(jTextField2.getText());
} catch (NumberFormatException e) {
    JOptionPane.showMessageDialog(this, "Qty harus angka");
    return;
}

if (qtyInput <= 0) {
    JOptionPane.showMessageDialog(this, "Qty harus lebih dari 0");
    return;
}

// 2. ambil stok terbaru dari DB

int stokDB = 0;
int hargaProduk = 0;

String sql = "SELECT stok, harga FROM produk WHERE id_produk = ?";

try (Connection conn = DatabaseConnection.getConnection();
     PreparedStatement pst = conn.prepareStatement(sql)) {

    pst.setInt(1, idProduk);
    ResultSet rs = pst.executeQuery();

    if (rs.next()) {
        stokDB = rs.getInt("stok");
        hargaProduk = rs.getInt("harga");
    } else {
        JOptionPane.showMessageDialog(this, "Produk tidak ditemukan");
        return;
    }

} catch (SQLException e) {
    JOptionPane.showMessageDialog(this, e.getMessage());
    return;
}

if (stokDB <= 0) {
    JOptionPane.showMessageDialog(this, "Stok produk habis");
    return;
}

// 3. hitung qty produk yang SUDAH ADA di JTable (draft)
int qtyDraft = 0;
int rowIndex = -1;

for (int i = 0; i < model.getRowCount(); i++) {
    int idDraft = (int) model.getValueAt(i, 0);
    if (idDraft == idProduk) {
        qtyDraft += (int) model.getValueAt(i, 2);
        rowIndex = i;
    }
}

// 4. validasi total qty
if (qtyDraft + qtyInput > stokDB) {
    JOptionPane.showMessageDialog(
        this,
        "Stok tidak mencukupi\n" +
        "Stok tersedia : " + stokDB + "\n" +
        "Sudah di draft : " + qtyDraft
    );
    return;
}



int hargaJual = (int) Math.round(hargaProduk * 1.08);
int subtotal = hargaJual * qtyInput;


// 5. tambah / gabung ke JTable
if (rowIndex >= 0) {
    int qtyLama = (int) model.getValueAt(rowIndex, 2);
    int subtotalLama = (int) model.getValueAt(rowIndex, 4);

    int qtyBaru = qtyLama + qtyInput;
    int subtotalBaru = hargaJual * qtyBaru;

    model.setValueAt(qtyBaru, rowIndex, 2);
    model.setValueAt(subtotalBaru, rowIndex, 4);

} else {
    model.addRow(new Object[]{
        idProduk,
        namaProduk,
        qtyInput,
        hargaJual,
        subtotal
    });
}

jTextField2.setText("");

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
if (jTable1.getRowCount() == 0) {
        JOptionPane.showMessageDialog(this, "Detail outbound masih kosong");
        return;
    }

    int pilih = JOptionPane.showConfirmDialog(
        this,
        "Apakah outbound ini DELIVERY?",
        "Jenis Outbound",
        JOptionPane.YES_NO_OPTION
    );

    // simpan draft outbound
    OutboundDraft draft = new OutboundDraft();
    draft.envelopeCode = jTextField1.getText();
    draft.tanggal = jDateChooser2.getDate();
    draft.userId = UserSession.getUserId();
    draft.detailModel = (DefaultTableModel) jTable1.getModel();

    if (pilih == JOptionPane.YES_OPTION) {
        FormDelivery fd = new FormDelivery(this, draft);
fd.setVisible(true);
    } else {
       
        simpanOutbound(draft, null);
        
    }
    }//GEN-LAST:event_jButton1ActionPerformed

    public void loadDeliveryLog() {
    DefaultTableModel model = new DefaultTableModel(
        new Object[]{
            
            "ID Outbound",
            "Envelope Code",
            "Tanggal",
            "Nama Penerima",
            "No HP",
            "Surat Jalan"
        }, 0
    );

    try {
        Connection conn = DatabaseConnection.getConnection();

        String sql = """
            SELECT 
                
                d.id_outbound,
                o.envelope_code,
                o.tanggal,
                d.nama_penerima,
                d.no_hp,
                d.surat_jalan
            FROM delivery d
            JOIN outbound o ON o.id_outbound = d.id_outbound
            ORDER BY d.id_delivery DESC
        """;

        PreparedStatement pst = conn.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getInt("id_outbound"),
                rs.getString("envelope_code"),
                rs.getDate("tanggal"),
                rs.getString("nama_penerima"),
                rs.getString("no_hp"),
                rs.getString("surat_jalan")
            });
        }

        

        rs.close();
        pst.close();
        conn.close();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
    }
}

    
    public void simpanOutbound(OutboundDraft draft, DeliveryData delivery) {

    DefaultTableModel detailModel = draft.detailModel;

    try (Connection conn = DatabaseConnection.getConnection()) {

        conn.setAutoCommit(false);

        // 1️⃣ VALIDASI STOK FINAL
        for (int i = 0; i < detailModel.getRowCount(); i++) {
            int idProduk = (int) detailModel.getValueAt(i, 0);
            int qty = (int) detailModel.getValueAt(i, 2);

            PreparedStatement pst = conn.prepareStatement(
                "SELECT stok FROM produk WHERE id_produk = ?"
            );
            pst.setInt(1, idProduk);
            ResultSet rs = pst.executeQuery();

            if (rs.next() && rs.getInt("stok") < qty) {
                conn.rollback();
                JOptionPane.showMessageDialog(
                    this,
                    "Stok berubah, outbound dibatalkan\nProduk ID: " + idProduk,
                    "Gagal",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }
        }

        // 2️⃣ INSERT OUTBOUND (SATU KALI)
        PreparedStatement pstOutbound = conn.prepareStatement(
            """
            INSERT INTO outbound (envelope_code, tanggal, created_by, jenis_outbound)
            VALUES (?, ?, ?, ?)
            """,
            PreparedStatement.RETURN_GENERATED_KEYS
        );

        pstOutbound.setString(1, draft.envelopeCode);
        pstOutbound.setDate(2, new java.sql.Date(draft.tanggal.getTime()));
        pstOutbound.setInt(3, draft.userId);
        pstOutbound.setString(4, delivery == null ? "PICKUP" : "DELIVERY");
        pstOutbound.executeUpdate();

        ResultSet rsKey = pstOutbound.getGeneratedKeys();
        rsKey.next();
        int idOutbound = rsKey.getInt(1);

        // 3️⃣ INSERT DETAIL + UPDATE STOK
        PreparedStatement pstDetail = conn.prepareStatement(
            "INSERT INTO outbound_detail\n" +
"(id_outbound, id_produk, qty, harga_jual, subtotal)\n" +
"VALUES (?, ?, ?, ?, ?)"
        );
        PreparedStatement pstStok = conn.prepareStatement(
            "UPDATE produk SET stok = stok - ? WHERE id_produk = ?"
        );

        for (int i = 0; i < detailModel.getRowCount(); i++) {
            int idProduk = (int) detailModel.getValueAt(i, 0);
            int qty = (int) detailModel.getValueAt(i, 2);
            int hargaJual = (int) detailModel.getValueAt(i, 3);
int subtotal = (int) detailModel.getValueAt(i, 4);

pstDetail.setInt(1, idOutbound);
pstDetail.setInt(2, idProduk);
pstDetail.setInt(3, qty);
pstDetail.setInt(4, hargaJual);
pstDetail.setInt(5, subtotal);
pstDetail.executeUpdate();

            

            pstStok.setInt(1, qty);
            pstStok.setInt(2, idProduk);
            pstStok.executeUpdate();
        }

        // 4️⃣ DELIVERY (JIKA ADA)
        if (delivery != null) {
            PreparedStatement pstDel = conn.prepareStatement(
                """
                INSERT INTO delivery
                (id_outbound, nama_penerima, alamat, no_hp, surat_jalan)
                VALUES (?, ?, ?, ?, ?)
                """
            );
            pstDel.setInt(1, idOutbound);
            pstDel.setString(2, delivery.nama);
            pstDel.setString(3, delivery.alamat);
            pstDel.setString(4, delivery.noHp);
            pstDel.setString(5, delivery.suratJalan);
            pstDel.executeUpdate();
        }

        conn.commit();
        JOptionPane.showMessageDialog(this, "Outbound berhasil disimpan");

        detailModel.setRowCount(0);
        loadOutboundHeader();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
    }
}


    
    public void loadOutboundHeader() {
    DefaultTableModel model = new DefaultTableModel(
    new Object[]{"ID Outbound", "Envelope Code", "Tanggal", "Jenis Outbound", "User"}, 0
);


    try {
        Connection conn = DatabaseConnection.getConnection();

        String sql = """
            SELECT i.id_outbound,
                   i.envelope_code,
                   i.tanggal,
                   i.jenis_outbound,
                   u.username
            FROM outbound i
            JOIN user u ON u.id = i.created_by
            ORDER BY i.id_outbound DESC
        """;

        PreparedStatement pst = conn.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            model.addRow(new Object[]{
    rs.getInt("id_outbound"),
    rs.getString("envelope_code"),
    rs.getDate("tanggal"),
    rs.getString("jenis_outbound"),
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

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
 int selectedRow = jTable1.getSelectedRow();
    if (selectedRow >= 0) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.removeRow(selectedRow);
    } else {
        JOptionPane.showMessageDialog(this, "Silakan pilih baris yang ingin dihapus");
    }        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    model.setRowCount(0);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
int rowOutbound = jTable2.getSelectedRow();
    

    // tidak pilih apa-apa
    if (rowOutbound == -1 ) {
        JOptionPane.showMessageDialog(this,
            "Pilih data outbound atau delivery terlebih dahulu");
        return;
    }

    int idOutbound = 0;

    // pilih dari tabel outbound
    if (rowOutbound != -1) {
        idOutbound = (int) jTable2.getValueAt(rowOutbound, 0);
    }
    // pilih dari tabel delivery
    

    FormOutboundDetail fd = new FormOutboundDetail(idOutbound);
    fd.setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {
        
    }
});
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable2MouseClicked

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
Integer idOutbound = getSelectedOutboundId();
    if (idOutbound == null) return;

    int confirm = JOptionPane.showConfirmDialog(
        this,
        "Hapus outbound ini?\nStok akan dikembalikan.",
        "Konfirmasi",
        JOptionPane.YES_NO_OPTION
    );

    if (confirm != JOptionPane.YES_OPTION) return;

    try {
        deleteOutbound(idOutbound);
        JOptionPane.showMessageDialog(this, "Outbound berhasil dihapus");
        loadOutboundHeader();
        loadDeliveryLog();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
    }        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

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
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
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

