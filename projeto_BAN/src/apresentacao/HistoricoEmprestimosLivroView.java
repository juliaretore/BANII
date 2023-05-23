
package apresentacao;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import exceptions.InsertException;
import exceptions.JaCadastradoException;
import exceptions.NaoCadastradoException;
import exceptions.SelectException;
//import javazoom.jl.decoder.JavaLayerException;
//import javazoom.jl.player.Player;
import negocio.Sistema;
import persistencia.*;
import javax.swing.ImageIcon;
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
		} catch (ClassNotFoundException | SQLException | SelectException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent arg0) {
				atualizarTabela();
			}
		});
		JLabel lblNewLabel = new JLabel("New label");

		setTitle("Histórico de empréstimos do exemplar");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100,  1086, 685);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblBusca = new JLabel("BUSCA");
		lblBusca.setFont(new Font("Segoe UI Symbol", Font.BOLD, 15));
		lblBusca.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBusca.setBounds(497, 12, 69, 20);
		contentPane.add(lblBusca);
		
		JLabel lblMusicasParaAdicionae = new JLabel("EMPRÉSTIMOS");
		lblMusicasParaAdicionae.setFont(new Font("Segoe UI Symbol", Font.BOLD, 15));
		lblMusicasParaAdicionae.setBounds(485, 155, 369, 20);
		contentPane.add(lblMusicasParaAdicionae);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(29, 200, 1045, 390);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setBackground(SystemColor.window);
		table.setSelectionBackground(SystemColor.activeCaption);
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
		
		JLabel lblId = new JLabel("ID:");
		lblId.setFont(new Font("Segoe UI Symbol", Font.BOLD, 15));
		lblId.setBounds(65, 72, 69, 20);
		contentPane.add(lblId);
		lblId.setHorizontalAlignment(SwingConstants.RIGHT);
		
		textPCodigo = new JTextField();
		textPCodigo.setBounds(152, 73, 305, 20);
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
		textPNome.setBounds(691, 73, 305, 20);
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
		
		JLabel lblUsurio = new JLabel("Usuário:");
		lblUsurio.setFont(new Font("Segoe UI Symbol", Font.BOLD, 15));
		lblUsurio.setBounds(555, 72, 118, 20);
		contentPane.add(lblUsurio);
		lblUsurio.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.LIGHT_GRAY);
		separator.setBounds(12, 127, 1062, 20);
		contentPane.add(separator);
		
		lblNewLabel.setBounds(142, 215, 369, 490);
		contentPane.add(lblNewLabel);
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