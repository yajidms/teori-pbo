import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.util.Locale;

public class POSFrame extends JFrame {

  //produk
  private final DefaultTableModel productModel;
  private final JTable tblProducts;
  private final JLabel lblSelected;
  private final JSpinner spQty;

  //keranjang
  private final DefaultTableModel cartModel;
  private final JTable cartTable;
  private final JLabel lblTotalValue;
  private final JLabel lblPointsValue;
  private final JTextArea txtStruk;
  private final JButton btnCheckout;
  private final JButton btnCetak;
  private final JButton btnClearCart;

  //mata uang
  private final NumberFormat IDR =
          NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

  public POSFrame() {
    super("POIN Off-Sales - Java Swing");

    //tabel produk
    productModel = new DefaultTableModel(
            new Object[]{"ID", "Nama Produk", "Harga (Rp)"}, 0
    ) {
      @Override public boolean isCellEditable(int r, int c) { return false; }
      @Override public Class<?> getColumnClass(int c) { return c == 2 ? Integer.class : String.class; }
    };

    //list produk
    productModel.addRow(new Object[]{"P001", "Air Mineral 600ml", 3000});
    productModel.addRow(new Object[]{"P002", "Kopi Sachet",       5000});
    productModel.addRow(new Object[]{"P003", "Roti isi",          8000});
    productModel.addRow(new Object[]{"P004", "Snack Keripik",     6000});
    productModel.addRow(new Object[]{"P005", "Minuman Botol",    12000});

    tblProducts = new JTable(productModel);
    tblProducts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    tblProducts.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

    DefaultTableCellRenderer rightNumber = new DefaultTableCellRenderer();
    rightNumber.setHorizontalAlignment(SwingConstants.RIGHT);
    tblProducts.getColumnModel().getColumn(2).setCellRenderer(rightNumber);

    JPanel leftPanel = new JPanel(new BorderLayout(6, 6));
    leftPanel.setBorder(new TitledBorder("Produk"));
    leftPanel.add(new JScrollPane(tblProducts), BorderLayout.CENTER);

    lblSelected = new JLabel("Dipilih: -");
    spQty = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
    JButton btnAddToCart = new JButton("Add to Cart");

    JPanel leftBottomWrap = new JPanel();
    leftBottomWrap.setLayout(new BoxLayout(leftBottomWrap, BoxLayout.Y_AXIS));

    JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
    row1.add(lblSelected);

    JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
    row2.add(new JLabel("Qty:"));
    row2.add(spQty);
    row2.add(btnAddToCart);

    leftBottomWrap.add(row1);
    leftBottomWrap.add(row2);
    leftPanel.add(leftBottomWrap, BorderLayout.SOUTH);

    //select produk
    tblProducts.getSelectionModel().addListSelectionListener(e -> {
      if (e.getValueIsAdjusting()) return;
      int r = tblProducts.getSelectedRow();
      if (r >= 0) {
        String id   = String.valueOf(tblProducts.getValueAt(r, 0));
        String nama = String.valueOf(tblProducts.getValueAt(r, 1));
        int harga   = ((Number) tblProducts.getValueAt(r, 2)).intValue();
        lblSelected.setText("Dipilih: " + id + " - " + nama + " (" + IDR.format(harga) + ")");
      }
    });

    //tabel keranjang
    cartModel = new DefaultTableModel(
            new Object[]{"ID", "Nama Produk", "Qty", "Harga", "Subtotal"}, 0
    ) {
      @Override public boolean isCellEditable(int r, int c) { return false; }
      @Override public Class<?> getColumnClass(int c) {
        if (c == 2) return Integer.class;
        if (c == 3 || c == 4) return Integer.class;
        return String.class;
      }
    };

    cartTable = new JTable(cartModel);
    cartTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    cartTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    cartTable.getColumnModel().getColumn(2).setCellRenderer(rightNumber);
    cartTable.getColumnModel().getColumn(3).setCellRenderer(rightNumber);
    cartTable.getColumnModel().getColumn(4).setCellRenderer(rightNumber);

    //keranjang sama struk belanja
    JPanel rightPanel = new JPanel(new BorderLayout(6, 6));
    rightPanel.setBorder(new TitledBorder("Keranjang"));
    rightPanel.add(new JScrollPane(cartTable), BorderLayout.CENTER);

    lblTotalValue = new JLabel("Total: " + IDR.format(0));
    lblTotalValue.setFont(lblTotalValue.getFont().deriveFont(16f));
    lblPointsValue = new JLabel("Points: 0");
    txtStruk = new JTextArea(7, 42);
    txtStruk.setEditable(false);
    btnCheckout = new JButton("Checkout");
    btnCetak = new JButton("Cetak");
    btnClearCart = new JButton("Bersihkan Keranjang");

    JPanel summary = new JPanel();
    summary.setLayout(new BoxLayout(summary, BoxLayout.Y_AXIS));
    summary.add(lblTotalValue);
    summary.add(lblPointsValue);
    summary.add(Box.createVerticalStrut(8));
    summary.add(btnCheckout);
    summary.add(Box.createVerticalStrut(4));
    summary.add(btnCetak);
    summary.add(Box.createVerticalStrut(4));
    summary.add(btnClearCart);
    summary.add(Box.createVerticalStrut(8));
    summary.add(new JLabel("Struk:"));
    summary.add(new JScrollPane(txtStruk));
    rightPanel.add(summary, BorderLayout.SOUTH);

    JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
    split.setResizeWeight(0.5);
    getContentPane().add(split, BorderLayout.CENTER);

    JMenuBar mb = new JMenuBar();
    mb.add(new JMenu("File"));
    mb.add(new JMenu("Help"));
    setJMenuBar(mb);

    btnAddToCart.addActionListener(e -> addToCart());
    btnCheckout.addActionListener(e -> checkout());
    btnCetak.addActionListener(e -> printReceipt());
    btnClearCart.addActionListener(e -> clearCart());

    //button delete
    cartTable.getInputMap(JComponent.WHEN_FOCUSED)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "delRow");
    cartTable.getActionMap().put("delRow", new AbstractAction() {
      @Override public void actionPerformed(ActionEvent e) { removeSelectedCartItem(); }
    });

    cartTable.getInputMap(JComponent.WHEN_FOCUSED)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "editQty");
    cartTable.getActionMap().put("editQty", new AbstractAction() {
      @Override public void actionPerformed(ActionEvent e) { editSelectedQty(); }
    });
    cartTable.addMouseListener(new MouseAdapter() {
      @Override public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2 && cartTable.getSelectedRow() >= 0) editSelectedQty();
      }
    });

    //frame
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setPreferredSize(new Dimension(980, 600));
    setLocationRelativeTo(null);
    pack();
  }

  //nambah item produk ke keranjang
  private void addToCart() {
    int row = tblProducts.getSelectedRow();
    int qty = (int) spQty.getValue();
    if (row < 0 || qty <= 0) {
      JOptionPane.showMessageDialog(this, "Pilih produk dan qty \u2265 1.", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    String id = String.valueOf(tblProducts.getValueAt(row, 0));
    String nama = String.valueOf(tblProducts.getValueAt(row, 1));
    int harga = ((Number) tblProducts.getValueAt(row, 2)).intValue();

    //menambahkan ke qty
    int existingRow = findCartRowById(id);
    if (existingRow >= 0) {
      int oldQty = ((Number) cartModel.getValueAt(existingRow, 2)).intValue();
      int newQty = oldQty + qty;
      cartModel.setValueAt(newQty, existingRow, 2);
      cartModel.setValueAt(harga, existingRow, 3);
      cartModel.setValueAt(newQty * harga, existingRow, 4);
    } else {
      cartModel.addRow(new Object[]{id, nama, qty, harga, qty * harga});
    }

    updateTotal();
  }

  private int findCartRowById(String id) {
    for (int i = 0; i < cartModel.getRowCount(); i++) {
      if (id.equals(cartModel.getValueAt(i, 0))) return i;
    }
    return -1;
  }

  // Hapus baris terpilih dari keranjang
  private void removeSelectedCartItem() {
    int r = cartTable.getSelectedRow();
    if (r >= 0) {
      cartModel.removeRow(r);
      updateTotal();
    }
  }

  private void editSelectedQty() {
    int r = cartTable.getSelectedRow();
    if (r < 0) return;
    int current = ((Number) cartModel.getValueAt(r, 2)).intValue();
    String input = JOptionPane.showInputDialog(this, "Qty baru:", current);
    if (input == null) return;
    try {
      int q = Integer.parseInt(input.trim());
      if (q <= 0) throw new NumberFormatException();
      int harga = ((Number) cartModel.getValueAt(r, 3)).intValue();
      cartModel.setValueAt(q, r, 2);
      cartModel.setValueAt(q * harga, r, 4);
      updateTotal();
    } catch (NumberFormatException ex) {
      JOptionPane.showMessageDialog(this, "Qty harus bilangan bulat > 0.", "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  //bersihkan keranjang untuk transaksi baru
  private void clearCart() {
    if (cartModel.getRowCount() == 0) {
      JOptionPane.showMessageDialog(this, "Keranjang sudah kosong.", "Info", JOptionPane.INFORMATION_MESSAGE);
      return;
    }

    int confirm = JOptionPane.showConfirmDialog(
            this,
            "Apakah Anda yakin ingin menghapus semua item dari keranjang?",
            "Konfirmasi Clear Cart",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
    );

    if (confirm == JOptionPane.YES_OPTION) {
      cartModel.setRowCount(0);
      txtStruk.setText("");
      updateTotal();
      JOptionPane.showMessageDialog(this, "Keranjang telah dikosongkan. Siap untuk transaksi baru.", "Info", JOptionPane.INFORMATION_MESSAGE);
    }
  }

  //hitung total & points
  private void updateTotal() {
    double total = 0.0;
    for (int i = 0; i < cartModel.getRowCount(); i++) {
      Object val = cartModel.getValueAt(i, 4); // Subtotal
      if (val instanceof Number) total += ((Number) val).doubleValue();
    }
    lblTotalValue.setText("Total: " + IDR.format(total));
    int points = (int) (total / 1000.0); // 1 point per Rp 1000
    lblPointsValue.setText("Points: " + points);
  }

  //bagian checkout ke struk
  private void checkout() {
    if (cartModel.getRowCount() == 0) {
      JOptionPane.showMessageDialog(this, "Keranjang masih kosong.", "Info", JOptionPane.INFORMATION_MESSAGE);
      return;
    }
    txtStruk.setText(generateReceipt());
  }

  private String generateReceipt() {
    double total = 0.0;
    StringBuilder sb = new StringBuilder();
    sb.append("===== POIN OFF-SALES =====\n");
    sb.append("Toko: Demo Toko\n");
    sb.append("==========================\n");
    sb.append(String.format("%-5s %-20s %3s %12s\n", "ID", "Nama", "Qty", "Subtotal"));

    for (int i = 0; i < cartModel.getRowCount(); i++) {
      String id   = String.valueOf(cartModel.getValueAt(i, 0));
      String nama = String.valueOf(cartModel.getValueAt(i, 1));
      int qty     = ((Number) cartModel.getValueAt(i, 2)).intValue();
      double sub  = ((Number) cartModel.getValueAt(i, 4)).doubleValue();
      total += sub;
      sb.append(String.format("%-5s %-20s %3d %12s\n", id, nama, qty, IDR.format(sub)));
    }

    sb.append("--------------------------\n");
    sb.append("TOTAL: ").append(IDR.format(total)).append("\n");
    sb.append("POINTS DIDAPAT: ").append((int)(total / 1000.0)).append(" (1 point per Rp 1000)\n");
    sb.append("==========================\n");
    sb.append("Terima kasih! Silakan kunjungi kembali.\n");
    return sb.toString();
  }

  //cetak struk (ke printer / Print to PDF)
  private void printReceipt() {
    try {
      boolean ok = txtStruk.print();
      if (!ok) JOptionPane.showMessageDialog(this, "Print dibatalkan.");
    } catch (Exception ex) {
      JOptionPane.showMessageDialog(this, "Gagal print: " + ex.getMessage(),
              "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception ignored) {}

    SwingUtilities.invokeLater(() -> {
      POSFrame frame = new POSFrame();
      frame.setVisible(true);
    });
  }
}