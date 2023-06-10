
package apresentacao;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import exceptions.SelectException;
import negocio.Sistema;
import java.awt.Font;
import javax.swing.border.MatteBorder;

public class HistoricoEmprestimosLivroView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textPNome;
	private JTextField textPCodigo;
	private static JTable table;
	private static Sistema sistema;

	private static List<Object> historico = new ArrayList<Object>();
		
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HistoricoEmprestimosLivroView frame = new HistoricoEmprestimosLivroView();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});
	}

	public HistoricoEmprestimosLivroView() {
		try {
			sistema = new Sistema();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent arg0) {
				atualizarTabela();
			}
		});
		
		setTitle("Histórico de empréstimos do exemplar");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100,  1086, 685);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/background2.png")));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(29, 157, 1045, 433);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setBackground(Color.WHITE);
		table.setSelectionBackground(new Color(212, 226, 250));
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Usu\u00E1rio", "Data emprestimo", "Data estimada de entrega", "Data de entrega", "Situa\u00E7\u00E3o", "Multa", "Situa\u00E7\u00E3o multa"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(15);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(110);
		table.getColumnModel().getColumn(5).setPreferredWidth(70);
		table.getColumnModel().getColumn(6).setPreferredWidth(60);
		
		textPCodigo = new JTextField();
		textPCodigo.setBounds(397, 41, 305, 20);
		contentPane.add(textPCodigo);
		textPCodigo.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {			
				TableRowSorter<TableModel> filtro = null;  
				DefaultTableModel model = (DefaultTableModel) table.getModel();  
				filtro = new TableRowSorter<TableModel>(model);  
				table.setRowSorter(filtro);
				
				if (textPCodigo.getText().length()==0) filtro.setRowFilter(null);
				else filtro.setRowFilter(RowFilter.regexFilter(textPCodigo.getText(), 0));  
			}
		});
		textPCodigo.setColumns(10);
		
		textPNome = new JTextField();
		textPNome.setBounds(397, 63, 305, 20);
		contentPane.add(textPNome);
		textPNome.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {			
				TableRowSorter<TableModel> filtro = null;  
				DefaultTableModel model = (DefaultTableModel) table.getModel();  
				filtro = new TableRowSorter<TableModel>(model);  
				table.setRowSorter(filtro); 
				
				if (textPNome.getText().length()==0) filtro.setRowFilter(null);
				else filtro.setRowFilter(RowFilter.regexFilter("(?i)" + textPNome.getText(), 1));  		
			}
		});
		textPNome.setColumns(10);
		
		JLabel lblBusca_1 = new JLabel("BUSCA");
		lblBusca_1.setForeground(new Color(85, 97, 120));
		lblBusca_1.setFont(new Font("Lato Black", Font.BOLD, 16));
		lblBusca_1.setBounds(514, 12, 69, 20);
		contentPane.add(lblBusca_1);
		
		JLabel lblid = new JLabel("ID:");
		lblid.setForeground(new Color(85, 97, 120));
		lblid.setFont(new Font("Lato Black", Font.BOLD, 15));
		lblid.setBounds(355, 41, 52, 20);
		contentPane.add(lblid);
		
		JLabel lblUsurio = new JLabel("Usuário:");
		lblUsurio.setForeground(new Color(85, 97, 120));
		lblUsurio.setFont(new Font("Lato Black", Font.BOLD, 15));
		lblUsurio.setBounds(318, 63, 146, 20);
		contentPane.add(lblUsurio);
		
		JLabel lblBusca_1_1 = new JLabel("EMPRÉSTIMOS");
		lblBusca_1_1.setForeground(new Color(85, 97, 120));
		lblBusca_1_1.setFont(new Font("Lato Black", Font.BOLD, 16));
		lblBusca_1_1.setBounds(484, 116, 158, 20);
		contentPane.add(lblBusca_1_1);
		
		JButton sair_1 = new JButton("Sair");
		sair_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		sair_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		sair_1.setBackground(Color.WHITE);
		sair_1.setBounds(491, 613, 92, 23);
		contentPane.add(sair_1);
		
		lblNewLabel.setBounds(-53, -29, 1898, 1047);
		contentPane.add(lblNewLabel);
		
		table.getTableHeader().setOpaque(false);
		table.getTableHeader().setBackground(new Color(225, 235, 252));
		table.setFillsViewportHeight(true);
	}


	public static void atualizarTabela() {
		try {
			historico = sistema.HistoricoExemplar(Integer.parseInt(LivroView.tfCodigo_1.getText()));
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			model.setNumRows(0);
		for (int i=0;i<historico.size();i++) model.addRow((Object[]) historico.get(i));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
}