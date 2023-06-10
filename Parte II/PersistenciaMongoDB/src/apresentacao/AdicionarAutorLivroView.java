package apresentacao;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.bson.types.ObjectId;

//import dados.Autor;
import dados.Autor;
import exceptions.DeleteException;
import exceptions.InsertException;
import exceptions.JaCadastradoException;
import exceptions.NaoCadastradoException;
import exceptions.SelectException;
import exceptions.UpdateException;
import negocio.Sistema;

public class AdicionarAutorLivroView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textPNome;
	private JTextField textPCodigo;
	private static JTable table;
	private static Sistema sistema;

	private static List<Object> autores = new ArrayList<Object>();
	private JTextField tfCodigo;
	private JTextField tfNome;
	private JTextField tfNacionalidade;
	private JTextField tfArea;
	private JButton cadastrar;
	private JButton alterar;
		
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdicionarAutorLivroView frame = new AdicionarAutorLivroView();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});
	}

	public AdicionarAutorLivroView() {
		try {
			sistema = new Sistema();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent arg0) {
				atualizarTabela();
				if(table.getRowCount()==0) {
					dispose();
					JOptionPane.showMessageDialog(null, "Sem autores disponíveis para adicionar!");
				}
			}
		});

		setTitle("Adicionar Autores");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100,  667, 639);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/background2.png")));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(105, 153, 453, 189);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setBackground(Color.WHITE);
		table.setSelectionBackground(new Color(212, 226, 250));
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Nome", "Nacionalidade", "Area"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(90);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent arg0) {
					try {
						setCamposFromTabela();
					} catch (NumberFormatException | SelectException e) {
						JOptionPane.showMessageDialog(null, e.getMessage());
					}
					cadastrar.setEnabled(false);
					alterar.setEnabled(true);
					
			}
		});
		
		textPCodigo = new JTextField();
		textPCodigo.setBounds(180, 27, 305, 20);
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
		textPNome.setBounds(180, 63, 305, 20);
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
					String id_autor = String.valueOf(table.getValueAt(table.getSelectedRow(), 0));
					ObjectId id_livro = new ObjectId(LivroView.tfCodigo.getText());
					try {
						sistema.adicionarAutoresLivros(id_livro, id_autor);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, e.getMessage());
					}
					LivroView.atualizarTabela_1();
					dispose();  
				}else JOptionPane.showMessageDialog(null, "Nenhum autor selecionado!");
			}
		});
		selecionar.setBackground(Color.WHITE);
		selecionar.setBounds(291, 354, 99, 23);
		contentPane.add(selecionar);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(30, 414, 611, 141);
		contentPane.add(layeredPane);
		
		tfCodigo = new JTextField();
		tfCodigo.setEditable(false);
		tfCodigo.setColumns(10);
		tfCodigo.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		tfCodigo.setBounds(127, 31, 33, 19);
		layeredPane.add(tfCodigo);
		
		tfNome = new JTextField();
		tfNome.setColumns(10);
		tfNome.setBounds(127, 64, 282, 19);
		layeredPane.add(tfNome);
		
		tfNacionalidade = new JTextField();
		tfNacionalidade.setColumns(10);
		tfNacionalidade.setBounds(127, 89, 282, 19);
		layeredPane.add(tfNacionalidade);
		
		tfArea = new JTextField();
		tfArea.setColumns(10);
		tfArea.setBounds(127, 119, 282, 19);
		layeredPane.add(tfArea);
		
		cadastrar = new JButton("Cadastrar");
		cadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tfArea.getText().equals("") || tfNacionalidade.getText().equals("") || tfNome.getText().equals("")) {      
					JOptionPane.showMessageDialog(null, "Preencha todos os campos");
				}else {
					Autor autor = new Autor();
					autor.setNome(tfNome.getText());
					autor.setNacionalidade(tfNacionalidade.getText());
					autor.setArea(tfArea.getText());
					try {
						sistema.adicionarAutor(autor);
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}
					limpar();
					atualizarTabela();
				}
				
				
			}
		});
		cadastrar.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		cadastrar.setBackground(Color.WHITE);
		cadastrar.setBounds(456, 89, 118, 21);
		layeredPane.add(cadastrar);
		
		alterar = new JButton("Alterar");
		alterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tfArea.getText().equals("") || tfNacionalidade.getText().equals("") || tfNome.getText().equals("")) {      
					JOptionPane.showMessageDialog(null, "Preencha todos os campos");
				}else {
					Autor autor = new Autor();
					autor.setId(tfCodigo.getText());
					autor.setNome(tfNome.getText());
					autor.setNacionalidade(tfNacionalidade.getText());
					autor.setArea(tfArea.getText());
					try {
						sistema.alterarAutor(autor);
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}				
					atualizarTabela();
					limpar();
				}
				
			}
		});
		alterar.setEnabled(false);
		alterar.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		alterar.setBackground(Color.WHITE);
		alterar.setBounds(456, 63, 118, 21);
		layeredPane.add(alterar);
		
		JButton limpar = new JButton("Limpar");
		limpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();			
			}
		});
		limpar.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		limpar.setBackground(Color.WHITE);
		limpar.setBounds(456, 31, 118, 21);
		layeredPane.add(limpar);
		
		JLabel lblid_1 = new JLabel("ID:");
		lblid_1.setForeground(new Color(85, 97, 120));
		lblid_1.setFont(new Font("Lato Black", Font.BOLD, 15));
		lblid_1.setBounds(91, 31, 52, 20);
		layeredPane.add(lblid_1);
		
		JLabel lblNome_1 = new JLabel("Nome:");
		lblNome_1.setForeground(new Color(85, 97, 120));
		lblNome_1.setFont(new Font("Lato Black", Font.BOLD, 15));
		lblNome_1.setBounds(73, 64, 52, 20);
		layeredPane.add(lblNome_1);
		
		JLabel lblNome_1_2 = new JLabel("Nacionalidade:");
		lblNome_1_2.setForeground(new Color(85, 97, 120));
		lblNome_1_2.setFont(new Font("Lato Black", Font.BOLD, 15));
		lblNome_1_2.setBounds(12, 89, 113, 20);
		layeredPane.add(lblNome_1_2);
		
		JLabel lblNome_1_2_1 = new JLabel("Área:");
		lblNome_1_2_1.setForeground(new Color(85, 97, 120));
		lblNome_1_2_1.setFont(new Font("Lato Black", Font.BOLD, 15));
		lblNome_1_2_1.setBounds(81, 119, 113, 20);
		layeredPane.add(lblNome_1_2_1);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.LIGHT_GRAY);
		separator_1.setBounds(12, 389, 629, 1);
		contentPane.add(separator_1);
		
		JButton sair_1 = new JButton("Sair");
		sair_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		sair_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		sair_1.setBackground(Color.WHITE);
		sair_1.setBounds(276, 567, 92, 23);
		contentPane.add(sair_1);
		
		JLabel lblBusca_1 = new JLabel("BUSCA");
		lblBusca_1.setForeground(new Color(85, 97, 120));
		lblBusca_1.setFont(new Font("Lato Black", Font.BOLD, 16));
		lblBusca_1.setBounds(301, 9, 69, 20);
		contentPane.add(lblBusca_1);
		
		JLabel lblBusca_1_1 = new JLabel("AUTORES PARA ADICIONAR");
		lblBusca_1_1.setForeground(new Color(85, 97, 120));
		lblBusca_1_1.setFont(new Font("Lato Black", Font.BOLD, 16));
		lblBusca_1_1.setBounds(215, 120, 318, 20);
		contentPane.add(lblBusca_1_1);
		
		JLabel lblid = new JLabel("ID:");
		lblid.setForeground(new Color(85, 97, 120));
		lblid.setFont(new Font("Lato Black", Font.BOLD, 15));
		lblid.setBounds(150, 27, 52, 20);
		contentPane.add(lblid);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setForeground(new Color(85, 97, 120));
		lblNome.setFont(new Font("Lato Black", Font.BOLD, 15));
		lblNome.setBounds(127, 63, 52, 20);
		contentPane.add(lblNome);
		
		
		lblNewLabel.setBounds(-53, -29, 1898, 1047);
		contentPane.add(lblNewLabel);
		
		table.getTableHeader().setOpaque(false);
		table.getTableHeader().setBackground(new Color(225, 235, 252));
		table.setFillsViewportHeight(true);
	}


	public static void atualizarTabela() {
		try {
			ObjectId objId = new ObjectId(LivroView.tfCodigo.getText());
			autores = sistema.listarAdiconarAutoresLivros(objId);
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			model.setNumRows(0);
		for (int i=0;i<autores.size();i++) model.addRow((Object[]) autores.get(i));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	public void limpar() {
		tfNome.setText("");
		tfCodigo.setText("");
		tfNacionalidade.setText("");
		tfArea.setText("");
		table.clearSelection();
		cadastrar.setEnabled(true);
		alterar.setEnabled(false);	
	}
	
	public void setCamposFromTabela() throws NumberFormatException, SelectException {
		tfCodigo.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 0)));
		tfNome.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 1)));
		tfNacionalidade.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 2)));
		tfArea.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 3)));

	}
}