package apresentacao;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.JSeparator;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import persistencia.*;
import dados.Categoria;
import dados.Endereco;
import dados.Funcionario;
import dados.Usuario;
import exceptions.DeleteException;
import exceptions.InsertException;
import exceptions.JaCadastradoException;
import exceptions.NaoCadastradoException;
import exceptions.SelectException;
import exceptions.UpdateException;
import negocio.Sistema;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.spi.FileSystemProvider;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Component;
import javax.swing.JPasswordField;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import java.awt.Choice;
import java.awt.Scrollbar;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.ScrollPaneConstants;
import javax.swing.JSplitPane;
import javax.swing.JLayeredPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.UIManager;

public class NovoEmprestimoReservaView extends JFrame {


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textPNome;
	private JTextField textPCodigo;

	private static List<Object> exemplares = new ArrayList<Object>();
	private static List<Object> livros = new ArrayList<Object>();
	private static Sistema sistema;
	private static NovoEmprestimoReservaView novoEmprestimoReservaView;
	private static JTable table_1;
	static JTextField tfCodigo;
	private JTextField tfLivro;
	private JTextField tfExemplar;
	static JTextField tfData;
	private JButton Cadastrar;
	private JTextField textPCodigo_1;
	private static JTable table_2;
	static JTextField tfUsuario;
	JButton Reservar;
	static JTextField textusuario;
	static JTextField tfIdUsuario;
	

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NovoEmprestimoReservaView frame = new NovoEmprestimoReservaView();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);					
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});
	}

	public static NovoEmprestimoReservaView getInstance() {
        if(novoEmprestimoReservaView==null) novoEmprestimoReservaView=new NovoEmprestimoReservaView();
        return novoEmprestimoReservaView;
    } 
	public NovoEmprestimoReservaView() {
		try {
			sistema = new Sistema();
		} catch (ClassNotFoundException | SQLException | SelectException e) {
			JOptionPane.showMessageDialog(null,  e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		
		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent arg0) {
				atualizarTabela();
			}
		});
//		ImageIcon imagemTituloJanela = new javax.swing.ImageIcon(getClass().getResource("/img/logo.jpg"));
//		setIconImage(imagemTituloJanela.getImage());


		setTitle("Gerênciar Empréstimos e Reservas");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0, 0,  1930, 1080);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		contentPane = new JPanel();
		contentPane.setEnabled(false);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/background2.png")));
		
		JButton sair = new JButton("Sair");
		sair.setBorder(new LineBorder(new Color(0, 0, 0)));
		sair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sair();
			}
		});
		sair.setBackground(Color.WHITE);
		sair.setBounds(882, 925, 173, 20);
		contentPane.add(sair);
		
		textPCodigo = new JTextField();
		textPCodigo.setBounds(644, 69, 198, 20);
		contentPane.add(textPCodigo);
		textPCodigo.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				TableRowSorter<TableModel> filtro = null;  
				DefaultTableModel model = (DefaultTableModel) table_2.getModel();  
				filtro = new TableRowSorter<TableModel>(model);  
				table_2.setRowSorter(filtro);
				if (textPCodigo.getText().length()==0) filtro.setRowFilter(null);
				else filtro.setRowFilter(RowFilter.regexFilter(textPCodigo.getText(), 1));  
			}
		});
		textPCodigo.setColumns(10);
		
		textPNome = new JTextField();
		textPNome.setBounds(422, 69, 164, 20);
		contentPane.add(textPNome);
		textPNome.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				TableRowSorter<TableModel> filtro = null;  
				DefaultTableModel model = (DefaultTableModel) table_2.getModel();  
				filtro = new TableRowSorter<TableModel>(model);  
				table_2.setRowSorter(filtro); 
				if (textPNome.getText().length()==0) filtro.setRowFilter(null);
				else filtro.setRowFilter(RowFilter.regexFilter("(?i)" + textPNome.getText(), 2));  
				
			}
		});
		textPNome.setColumns(10);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(1061, 191, 530, 477);
		contentPane.add(scrollPane_1);
		
		table_1 = new JTable();
		table_1.setSelectionBackground(new Color(212, 226, 250));
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Prateleira", "Estante", "Colecao"
			}
		));
		table_1.getColumnModel().getColumn(0).setPreferredWidth(20);
		table_1.getColumnModel().getColumn(1).setPreferredWidth(90);
		table_1.getColumnModel().getColumn(2).setPreferredWidth(100);
		table_1.getColumnModel().getColumn(3).setPreferredWidth(100);
		scrollPane_1.setViewportView(table_1);
		table_1.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent arg0) {
				tfExemplar.setText(String.valueOf(table_1.getValueAt(table_1.getSelectedRow(), 0)));
//				try {
//					setCamposFromTabela_1();
//					cadastrar_2.setEnabled(false);
//					if (table.getSelectedRow()!=-1) cadatrar_asisstente.setEnabled(true);
//						
//					remover_assistente.setEnabled(true);
//					excluir_2.setEnabled(true);
//					alterar_2.setEnabled(true);
//				} catch (NumberFormatException e) {
//					e.printStackTrace();
//				} catch (SelectException e) {
//					e.printStackTrace();
//				}
			}
		});
		
		JLayeredPane layeredPane_1 = new JLayeredPane();
		layeredPane_1.setBounds(671, 703, 611, 193);
		contentPane.add(layeredPane_1);
		
		JButton selecionar_usuario = new JButton("Buscar Usuário");
		selecionar_usuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdicionarUsuarioEmprestimoView frame = new AdicionarUsuarioEmprestimoView();
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
			}
		});
		selecionar_usuario.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		selecionar_usuario.setBackground(Color.WHITE);
		selecionar_usuario.setBounds(467, 30, 118, 21);
		layeredPane_1.add(selecionar_usuario);
		
		tfCodigo = new JTextField();
		tfCodigo.setVisible(false);
		tfCodigo.setEditable(false);
		tfCodigo.setColumns(10);
		tfCodigo.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		tfCodigo.setBounds(467, 31, 38, 19);
		layeredPane_1.add(tfCodigo);
		
		tfLivro = new JTextField();
		tfLivro.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		tfLivro.setEditable(false);
		tfLivro.setColumns(10);
		tfLivro.setBounds(172, 64, 282, 19);
		layeredPane_1.add(tfLivro);
		
		tfExemplar = new JTextField();
		tfExemplar.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		tfExemplar.setEditable(false);
		tfExemplar.setColumns(10);
		tfExemplar.setBounds(172, 95, 282, 19);
		layeredPane_1.add(tfExemplar);
		
		tfData = new JTextField();
		tfData.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		tfData.setEditable(false);
		tfData.setColumns(10);
		tfData.setBounds(172, 121, 282, 19);
		layeredPane_1.add(tfData);
		
		JButton limpar_2 = new JButton("Limpar");
		limpar_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		limpar_2.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		limpar_2.setBackground(Color.WHITE);
		limpar_2.setBounds(490, 62, 83, 21);
		layeredPane_1.add(limpar_2);
		
		Cadastrar = new JButton("Cadastrar");
		Cadastrar.setBounds(172, 152, 118, 21);
		layeredPane_1.add(Cadastrar);
		Cadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					try {
						if(tfLivro.getText().equals("") || tfExemplar.getText().equals("") || tfUsuario.getText().equals("")) {      
							JOptionPane.showMessageDialog(null, "Preencha todos os campos");
						}else {					
							try {
								int usuario = Integer.parseInt(tfCodigo.getText());
								int exemplar = Integer.parseInt(String.valueOf((table_1.getValueAt(table_1.getSelectedRow(), 0))));
								int funcionario = Integer.parseInt(TelaPrincipal.tfIdUsuario.getText());
								sistema.inserirEmprestimo(exemplar, usuario, funcionario);
							} catch (InsertException | SelectException | JaCadastradoException e1) {
								JOptionPane.showMessageDialog(null, e1.getMessage());
							}
						
							atualizarTabela_1();
							limpar();
						}
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}	
			}
		});
		Cadastrar.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		Cadastrar.setBackground(Color.WHITE);
		
		tfUsuario = new JTextField();
		tfUsuario.setEditable(false);
		tfUsuario.setColumns(10);
		tfUsuario.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		tfUsuario.setBounds(172, 31, 282, 19);
		layeredPane_1.add(tfUsuario);
		
		Reservar = new JButton("Reservar");
		Reservar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(tfLivro.getText().equals("") || tfExemplar.getText().equals("") || tfUsuario.getText().equals("")) {      
						JOptionPane.showMessageDialog(null, "Preencha todos os campos");
					}else {					
						try {
							int livro = Integer.parseInt(String.valueOf((table_2.getValueAt(table_2.getSelectedRow(), 0))));
							int usuario = Integer.parseInt(tfCodigo.getText());
							sistema.inserirReserva(livro, usuario);
						} catch (InsertException | SelectException | JaCadastradoException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage());
						}
					
						atualizarTabela_1();
						limpar();
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}	

			}
		});
//		inserirReserva(int cid_livro, int cid_usuario)
		Reservar.setEnabled(false);
		Reservar.setActionCommand("Reservar");
		Reservar.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		Reservar.setBackground(Color.WHITE);
		Reservar.setBounds(336, 150, 118, 21);
		layeredPane_1.add(Reservar);
		
		JLabel lblUsurio = new JLabel("Usuário:");
		lblUsurio.setForeground(new Color(85, 97, 120));
		lblUsurio.setFont(new Font("Lato Black", Font.BOLD, 15));
		lblUsurio.setBounds(92, 31, 94, 20);
		layeredPane_1.add(lblUsurio);
		
		JLabel lblLivro = new JLabel("Livro:");
		lblLivro.setForeground(new Color(85, 97, 120));
		lblLivro.setFont(new Font("Lato Black", Font.BOLD, 15));
		lblLivro.setBounds(112, 64, 94, 20);
		layeredPane_1.add(lblLivro);
		
		JLabel lblExemplar = new JLabel("Exemplar:");
		lblExemplar.setForeground(new Color(85, 97, 120));
		lblExemplar.setFont(new Font("Lato Black", Font.BOLD, 15));
		lblExemplar.setBounds(78, 95, 94, 20);
		layeredPane_1.add(lblExemplar);
		
		JLabel lblDataDeEntrega = new JLabel("Data de Entrega:");
		lblDataDeEntrega.setForeground(new Color(85, 97, 120));
		lblDataDeEntrega.setFont(new Font("Lato Black", Font.BOLD, 15));
		lblDataDeEntrega.setBounds(30, 121, 160, 20);
		layeredPane_1.add(lblDataDeEntrega);
		
		textPCodigo_1 = new JTextField();
		textPCodigo_1.setColumns(10);
		textPCodigo_1.setBounds(1236, 69, 198, 20);
		textPCodigo_1.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				TableRowSorter<TableModel> filtro = null;  
				DefaultTableModel model = (DefaultTableModel) table_1.getModel();  
				filtro = new TableRowSorter<TableModel>(model);  
				table_1.setRowSorter(filtro);
				if (textPCodigo_1.getText().length()==0) filtro.setRowFilter(null);
				else filtro.setRowFilter(RowFilter.regexFilter(textPCodigo_1.getText(), 0));  
			}
		});
		contentPane.add(textPCodigo_1);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane_2.setBounds(369, 191, 503, 477);
		contentPane.add(scrollPane_2);
		
		table_2 = new JTable();
		table_2.setSelectionBackground(new Color(212, 226, 250));
		table_2.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "ISBN", "Titulo", "Editora"
			}
		));
		scrollPane_2.setViewportView(table_2);

		table_2.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent arg0) {
					atualizarTabela_1();
					tfLivro.setText(String.valueOf(table_2.getValueAt(table_2.getSelectedRow(), 2)));
					if (exemplares.isEmpty()) {
						Reservar.setEnabled(true);
						Cadastrar.setEnabled(false);
						tfExemplar.setText("Sem exemplares no momento.");
					}else {
						Reservar.setEnabled(false);
						Cadastrar.setEnabled(true);
						tfExemplar.setText("");
					}
			}
		});
		
		textusuario = new JTextField();
		textusuario.setBackground(SystemColor.window);
		textusuario.setDisabledTextColor(new Color(0, 0, 0));
		textusuario.setEditable(false);
		textusuario.setBounds(1532, 12, 86, 29);
		contentPane.add(textusuario);
		textusuario.setColumns(10);
		
		
		tfIdUsuario = new JTextField();
		tfIdUsuario.setBackground(SystemColor.window);
		tfIdUsuario.setEditable(false);
		tfIdUsuario.setColumns(10);
		tfIdUsuario.setBounds(1509, 12, 29, 29);
		contentPane.add(tfIdUsuario);
		
		JButton btnNewButton = new JButton("Deslogar");
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				LoginView frame = new LoginView();
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnNewButton.setBounds(1650, 12, 133, 31);
		contentPane.add(btnNewButton);
		
		JLabel lblLivros = new JLabel("LIVROS");
		lblLivros.setForeground(new Color(85, 97, 120));
		lblLivros.setFont(new Font("Lato Black", Font.BOLD, 16));
		lblLivros.setBounds(594, 159, 337, 20);
		contentPane.add(lblLivros);
		
		JLabel lblExemplares_1 = new JLabel("EXEMPLARES");
		lblExemplares_1.setForeground(new Color(85, 97, 120));
		lblExemplares_1.setFont(new Font("Lato Black", Font.BOLD, 16));
		lblExemplares_1.setBounds(1269, 142, 337, 20);
		contentPane.add(lblExemplares_1);
		
		JLabel lblExemplares_1_1 = new JLabel("BUSCA DE EXEMPLARES");
		lblExemplares_1_1.setForeground(new Color(85, 97, 120));
		lblExemplares_1_1.setFont(new Font("Lato Black", Font.BOLD, 16));
		lblExemplares_1_1.setBounds(1226, 34, 337, 20);
		contentPane.add(lblExemplares_1_1);
		
		JLabel lblExemplares_1_2 = new JLabel("BUSCA DE LIVROS");
		lblExemplares_1_2.setForeground(new Color(85, 97, 120));
		lblExemplares_1_2.setFont(new Font("Lato Black", Font.BOLD, 16));
		lblExemplares_1_2.setBounds(529, 34, 337, 20);
		contentPane.add(lblExemplares_1_2);
		
		JLabel lblTitulo = new JLabel("Titulo:");
		lblTitulo.setForeground(new Color(85, 97, 120));
		lblTitulo.setFont(new Font("Lato Black", Font.BOLD, 15));
		lblTitulo.setBounds(365, 69, 52, 20);
		contentPane.add(lblTitulo);
		
		JLabel lblIsbn = new JLabel("ISBN:");
		lblIsbn.setForeground(new Color(85, 97, 120));
		lblIsbn.setFont(new Font("Lato Black", Font.BOLD, 15));
		lblIsbn.setBounds(595, 71, 52, 20);
		contentPane.add(lblIsbn);
		
		JLabel lblId = new JLabel("ID:");
		lblId.setForeground(new Color(85, 97, 120));
		lblId.setFont(new Font("Lato Black", Font.BOLD, 15));
		lblId.setBounds(1200, 69, 52, 20);
		contentPane.add(lblId);
		
		lblNewLabel.setBounds(12, 0, 1898, 1047);
		contentPane.add(lblNewLabel);
		
		table_2.getTableHeader().setOpaque(false);
		table_2.getTableHeader().setBackground(new Color(225, 235, 252));
		table_2.setFillsViewportHeight(true);
		table_1.getTableHeader().setOpaque(false);
		table_1.getTableHeader().setBackground(new Color(225, 235, 252));
		table_1.setFillsViewportHeight(true);
		
	}

	public void sair() {
		dispose();
		TelaPrincipal frame = new TelaPrincipal();
		TelaPrincipal.tfIdUsuario.setText(tfIdUsuario.getText());
		TelaPrincipal.textusuario.setText(textusuario.getText());
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}

	public void limpar() {
		tfLivro.setText("");
		tfCodigo.setText("");
		tfUsuario.setText("");
		tfData.setText("");
		tfExemplar.setText("");
		table_1.clearSelection();
		table_2.clearSelection();
		Cadastrar.setEnabled(true);
		Reservar.setEnabled(false);

	}

	
	public static void atualizarTabela() {
		try {
			livros = sistema.listarLivros();
			DefaultTableModel model = (DefaultTableModel) table_2.getModel();
			model.setNumRows(0);
		for (int i=0;i!=livros.size();i++)model.addRow((Object[]) livros.get(i));
		
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

public static void atualizarTabela_1() {
		try {
			int id_livro = Integer.parseInt(String.valueOf(table_2.getValueAt(table_2.getSelectedRow(), 0)));
			exemplares = sistema.listarExemplaresLivrosDisponiveis(id_livro);
			DefaultTableModel model = (DefaultTableModel) table_1.getModel();
			model.setNumRows(0);
		for (int i=0;i<exemplares.size();i++) model.addRow((Object[]) exemplares.get(i));
		
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	public void setCamposFromTabela() throws NumberFormatException, SelectException {

	}
	
	public void setCamposFromTabela_1() throws NumberFormatException, SelectException {
		tfCodigo.setText(String.valueOf(table_1.getValueAt(table_1.getSelectedRow(), 0)));
		tfLivro.setText(String.valueOf(table_1.getValueAt(table_1.getSelectedRow(), 1)));
		tfExemplar.setText(String.valueOf(table_1.getValueAt(table_1.getSelectedRow(), 2)));
		String turno = String.valueOf(table_1.getValueAt(table_1.getSelectedRow(), 3));
		tfData.setText(String.valueOf(table_1.getValueAt(table_1.getSelectedRow(), 4)));
	}
}