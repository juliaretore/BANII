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

public class AdicionarSupervisaoView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textPNome;
	private JTextField textPCodigo;
	private static JTable table;
	private static Sistema sistema;

	private static List<Object> assistentes = new ArrayList<Object>();
		
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdicionarSupervisaoView frame = new AdicionarSupervisaoView();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});
	}

	public AdicionarSupervisaoView() {
		try {
			sistema = new Sistema();
		} catch (ClassNotFoundException | SQLException | SelectException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent arg0) {
				atualizarTabela();
				if(table.getRowCount()==0) {
					dispose();
					JOptionPane.showMessageDialog(null, "Sem assistentes disponíveis para adicionar!");
				}
			}
		});

		setTitle("Adicionar Supervisão");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100,  544, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/background2.png")));
		
		JButton sair = new JButton("Sair");
		sair.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		sair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sair();
			}
		});
		sair.setBackground(Color.WHITE);
		sair.setBounds(348, 354, 92, 23);
		contentPane.add(sair);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(45, 153, 453, 189);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setBackground(Color.WHITE);
		table.setSelectionBackground(new Color(212, 226, 250));
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Nome", "Login", "Turno", "Salario", "Email"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(90);
		table.getColumnModel().getColumn(2).setPreferredWidth(80);
		table.getColumnModel().getColumn(3).setPreferredWidth(80);
		table.getColumnModel().getColumn(4).setPreferredWidth(80);
		table.getColumnModel().getColumn(5).setPreferredWidth(140);
		
		textPCodigo = new JTextField();
		textPCodigo.setBounds(142, 30, 305, 20);
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
		textPNome.setBounds(142, 58, 305, 20);
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
		
		JButton selecionar = new JButton("Selecionar");
		selecionar.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		selecionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectedRow()!=-1){
					int assistente = Integer.parseInt(String.valueOf(table.getValueAt(table.getSelectedRow(), 0)));
					int bibliotecario = Integer.parseInt(FuncionarioView.tfCodigo.getText());
					try {
						sistema.adicionarSupervisao(assistente, bibliotecario);
					} catch (InsertException | SelectException |NumberFormatException | JaCadastradoException e) {
						JOptionPane.showMessageDialog(null, e.getMessage());
					}
					FuncionarioView.atualizarTabela_1_1();
					dispose();  
				}else JOptionPane.showMessageDialog(null, "Nenhum assistente selecionado!");
			}
		});
		selecionar.setBackground(Color.WHITE);
		selecionar.setBounds(127, 354, 99, 23);
		contentPane.add(selecionar);
		
		JLabel lblAssistentesParaAdicionar = new JLabel("ASSISTENTES PARA ADICIONAR NA SUPERVISÃO");
		lblAssistentesParaAdicionar.setForeground(new Color(85, 97, 120));
		lblAssistentesParaAdicionar.setFont(new Font("Lato Black", Font.BOLD, 16));
		lblAssistentesParaAdicionar.setBounds(67, 117, 412, 20);
		contentPane.add(lblAssistentesParaAdicionar);
		
		JLabel lblBusca_1 = new JLabel("BUSCA");
		lblBusca_1.setForeground(new Color(85, 97, 120));
		lblBusca_1.setFont(new Font("Lato Black", Font.BOLD, 16));
		lblBusca_1.setBounds(244, 12, 337, 20);
		contentPane.add(lblBusca_1);
		
		JLabel lblid = new JLabel("ID:");
		lblid.setForeground(new Color(85, 97, 120));
		lblid.setFont(new Font("Lato Black", Font.BOLD, 15));
		lblid.setBounds(108, 30, 52, 20);
		contentPane.add(lblid);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setForeground(new Color(85, 97, 120));
		lblNome.setFont(new Font("Lato Black", Font.BOLD, 15));
		lblNome.setBounds(84, 58, 52, 20);
		contentPane.add(lblNome);

		
		lblNewLabel.setBounds(-53, -29, 1898, 1047);
		contentPane.add(lblNewLabel);
		
		table.getTableHeader().setOpaque(false);
		table.getTableHeader().setBackground(new Color(225, 235, 252));
		table.setFillsViewportHeight(true);
	}

	public void sair() {
		dispose();
	}


	public static void atualizarTabela() {
		try {
			assistentes = sistema.listarAdicionarAssistentesBibliotecario(Integer.parseInt(FuncionarioView.tfCodigo.getText()));
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			model.setNumRows(0);
		for (int i=0;i<assistentes.size();i++) model.addRow((Object[]) assistentes.get(i));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
}