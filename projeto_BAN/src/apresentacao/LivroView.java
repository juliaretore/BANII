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
import dados.Exemplar;
import dados.Funcionario;
import dados.Livro;
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

public class LivroView extends JFrame {


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textPNome;
	private JTextField textPCodigo;

	private static List<Object> livros = new ArrayList<Object>();
	private static List<Object> exemplares = new ArrayList<Object>();
	private static List<Object> autores = new ArrayList<Object>();
	Livro livro = new Livro();	
	Exemplar exemplar = new Exemplar();
	private static Sistema sistema;
	private JTextField tfIsbn;
	static JTextField tfCodigo;
	private static JTable table = new JTable();
	private static LivroView livroView;
	private static JTable table_1;
	private JTextField tfTitulo;
	private JTextField tfEditora;
	private JTextField tfCodigo_1;
	private JTextField tfPrateleira;
	private JTextField tfEstante;
	private JButton cadastrar_2;
	private JButton cadastrar_1;
	private JButton cadatrar_autor;
	private JButton alterar;
	private JButton excluir_1;
	private JButton alterar_2;
	private JButton excluir_2;
	private JButton remover_autor;
	private JTextField textPCodigo_1;
	private static JTable table_2;
	private JComboBox comboBox;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LivroView frame = new LivroView();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);					
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});
	}

	public static LivroView getInstance() {
        if(livroView==null) livroView=new LivroView();
        return livroView;
    } 
	public LivroView() {
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


		setTitle("Gerenciar Funcionaários");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(200, 2000,  1800, 1000);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		contentPane = new JPanel();
		contentPane.setEnabled(false);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblBusca = new JLabel("BUSCA DE LIVROS");
		lblBusca.setFont(new Font("Dialog", Font.BOLD, 15));
		lblBusca.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBusca.setBounds(151, 12, 405, 48);
		contentPane.add(lblBusca);
		
		JLabel lblArtistasCadastrados = new JLabel("LIVROS CADASTRADOS");
		lblArtistasCadastrados.setFont(new Font("Segoe UI Symbol", Font.BOLD, 15));
		lblArtistasCadastrados.setBounds(385, 154, 337, 20);
		contentPane.add(lblArtistasCadastrados);
		
		JButton sair = new JButton("Sair");
		sair.setBorder(new LineBorder(new Color(0, 0, 0)));
		sair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sair();
			}
		});
		sair.setBackground(UIManager.getColor("Button.darkShadow"));
		sair.setBounds(784, 931, 173, 20);
		contentPane.add(sair);
		
		
		JLabel lblCdigo = new JLabel("Código:");
		lblCdigo.setFont(new Font("Segoe UI Symbol", Font.BOLD, 15));
		lblCdigo.setBounds(500, 58, 70, 20);
		contentPane.add(lblCdigo);
		lblCdigo.setHorizontalAlignment(SwingConstants.RIGHT);
		
		textPCodigo = new JTextField();
		textPCodigo.setBounds(582, 59, 198, 20);
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
		textPNome.setBounds(234, 60, 198, 20);
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
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Segoe UI Symbol", Font.BOLD, 15));
		lblNome.setBounds(151, 58, 70, 20);
		contentPane.add(lblNome);
		lblNome.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JSeparator separator_2_1_1 = new JSeparator();
		separator_2_1_1.setForeground(Color.LIGHT_GRAY);
		separator_2_1_1.setBounds(12, 122, 1788, 20);
		contentPane.add(separator_2_1_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(169, 186, 611, 541);
		contentPane.add(scrollPane);
		table.setSelectionBackground(SystemColor.activeCaption);
		table.setBackground(UIManager.getColor("Button.light"));
		

		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "ISBN", "T\u00EDtulo", "Editora"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(90);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		scrollPane.setViewportView(table);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(967, 186, 614, 163);
		contentPane.add(scrollPane_1);
		
		table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Nome", "Nacionalidade", "\u00C1rea"
			}
		));
		table_1.getColumnModel().getColumn(0).setPreferredWidth(20);
		table_1.getColumnModel().getColumn(1).setPreferredWidth(90);
		table_1.getColumnModel().getColumn(2).setPreferredWidth(100);
		table_1.getColumnModel().getColumn(3).setPreferredWidth(100);
		scrollPane_1.setViewportView(table_1);
		table_1.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent arg0) {
					if (table.getSelectedRow()!=-1) cadatrar_autor.setEnabled(true);
					remover_autor.setEnabled(true);
			}
		});
		

		
		JLabel lblTelefones = new JLabel("AUTORES DO LIVRO");
		lblTelefones.setFont(new Font("Dialog", Font.BOLD, 15));
		lblTelefones.setBounds(1189, 154, 203, 20);
		contentPane.add(lblTelefones);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(169, 739, 611, 156);
		contentPane.add(layeredPane);
		
		JLabel Aa = new JLabel("ISBN:");
		Aa.setBounds(22, 63, 70, 20);
		layeredPane.add(Aa);
		Aa.setHorizontalAlignment(SwingConstants.RIGHT);
		Aa.setFont(new Font("Segoe UI Symbol", Font.BOLD, 15));
		
		JLabel lblCdigo_1 = new JLabel("ID:");
		lblCdigo_1.setBounds(22, 31, 70, 20);
		layeredPane.add(lblCdigo_1);
		lblCdigo_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCdigo_1.setFont(new Font("Segoe UI Symbol", Font.BOLD, 15));
		
		tfCodigo = new JTextField();
		tfCodigo.setBounds(127, 31, 33, 19);
		layeredPane.add(tfCodigo);
		tfCodigo.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		tfCodigo.setEditable(false);
		tfCodigo.setColumns(10);
		
		tfIsbn = new JTextField();
		tfIsbn.setBounds(127, 64, 282, 19);
		layeredPane.add(tfIsbn);
		tfIsbn.setColumns(10);
		
		JLabel lblNome_1_1 = new JLabel("Titulo");
		lblNome_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNome_1_1.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNome_1_1.setBounds(22, 89, 70, 20);
		layeredPane.add(lblNome_1_1);
		
		tfTitulo = new JTextField();
		tfTitulo.setColumns(10);
		tfTitulo.setBounds(127, 89, 282, 19);
		layeredPane.add(tfTitulo);
		
		tfEditora = new JTextField();
		tfEditora.setColumns(10);
		tfEditora.setBounds(127, 119, 282, 19);
		layeredPane.add(tfEditora);
		
		JLabel lblSalrio = new JLabel("Editora:");
		lblSalrio.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSalrio.setFont(new Font("Dialog", Font.BOLD, 15));
		lblSalrio.setBounds(22, 118, 70, 20);
		layeredPane.add(lblSalrio);
		
		cadastrar_1 = new JButton("Cadastrar");
		cadastrar_1.setBounds(456, 89, 118, 21);
		layeredPane.add(cadastrar_1);
		cadastrar_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		cadastrar_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					if(tfIsbn.getText().equals("") || tfTitulo.getText().equals("") || tfEditora.getText().equals("")) {      
						JOptionPane.showMessageDialog(null, "Preencha todos os campos");
					}else {
						livro.setIsbn(tfIsbn.getText());
						livro.setTitulo(tfTitulo.getText());
						livro.setEditora(tfEditora.getText());
						try {
							sistema.adicionarLivro(livro);
						} catch (InsertException | SelectException | JaCadastradoException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage());
						}
						limpar();
						atualizarTabela();
						atualizarTabela_1();
						atualizarTabela_2();
					}	
			}
			
		});
		cadastrar_1.setBackground(UIManager.getColor("Button.darkShadow"));
		
		alterar = new JButton("Alterar");
		alterar.setEnabled(false);
		alterar.setBounds(456, 63, 118, 21);
		layeredPane.add(alterar);
		alterar.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		alterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				livro.setIsbn(tfIsbn.getText());
				livro.setTitulo(tfTitulo.getText());
				livro.setEditora(tfEditora.getText());	
				livro.setId(Integer.parseInt(tfCodigo.getText()));
					try {
						sistema.alterarLivro(livro);
					} catch (UpdateException | SelectException | NaoCadastradoException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					};						
					limpar();
					atualizarTabela();
					atualizarTabela_1();
					atualizarTabela_2();
			}
		});
		alterar.setBackground(UIManager.getColor("Button.darkShadow"));
		
	
		
		excluir_1 = new JButton("Excluir");
		excluir_1.setEnabled(false);
		excluir_1.setBounds(456, 118, 118, 21);
		layeredPane.add(excluir_1);
		excluir_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		excluir_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					try {
						sistema.excluirLivro(Integer.parseInt(tfCodigo.getText()));
					} catch (NumberFormatException | DeleteException | SelectException | NaoCadastradoException e1) {
						JOptionPane.showMessageDialog(null,  e1.getMessage());
					}
					limpar();
					limpar_1();
					atualizarTabela();
					atualizarTabela_1();
					atualizarTabela_2();
			}
		});
		excluir_1.setBackground(UIManager.getColor("Button.darkShadow"));
		
		JButton limpar = new JButton("Limpar");
		limpar.setBounds(456, 31, 118, 21);
		layeredPane.add(limpar);
		limpar.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		limpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		limpar.setBackground(UIManager.getColor("Button.focus"));
		
		JLayeredPane layeredPane_1 = new JLayeredPane();
		layeredPane_1.setBounds(970, 739, 611, 156);
		contentPane.add(layeredPane_1);
		
		JLabel lblNome_1_2 = new JLabel("Prateleira:");
		lblNome_1_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNome_1_2.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNome_1_2.setBounds(22, 63, 98, 20);
		layeredPane_1.add(lblNome_1_2);
		
		JLabel lblCdigo_1_1 = new JLabel("ID:");
		lblCdigo_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCdigo_1_1.setFont(new Font("Dialog", Font.BOLD, 15));
		lblCdigo_1_1.setBounds(50, 30, 70, 20);
		layeredPane_1.add(lblCdigo_1_1);
		
		tfCodigo_1 = new JTextField();
		tfCodigo_1.setEditable(false);
		tfCodigo_1.setColumns(10);
		tfCodigo_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		tfCodigo_1.setBounds(127, 31, 33, 19);
		layeredPane_1.add(tfCodigo_1);
		
		tfPrateleira = new JTextField();
		tfPrateleira.setColumns(10);
		tfPrateleira.setBounds(127, 64, 282, 19);
		layeredPane_1.add(tfPrateleira);
		
		JLabel lblNome_1_1_1 = new JLabel("Estante:");
		lblNome_1_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNome_1_1_1.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNome_1_1_1.setBounds(50, 88, 70, 20);
		layeredPane_1.add(lblNome_1_1_1);
		
		tfEstante = new JTextField();
		tfEstante.setColumns(10);
		tfEstante.setBounds(127, 89, 282, 19);
		layeredPane_1.add(tfEstante);
		
		JLabel lblSalrio_1 = new JLabel("Coleção:");
		lblSalrio_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSalrio_1.setFont(new Font("Dialog", Font.BOLD, 15));
		lblSalrio_1.setBounds(50, 118, 70, 20);
		layeredPane_1.add(lblSalrio_1);
		
		JButton limpar_2 = new JButton("Limpar");
		limpar_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar_1();
			}
		});
		limpar_2.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		limpar_2.setBackground(UIManager.getColor("Button.darkShadow"));
		limpar_2.setBounds(456, 31, 118, 21);
		layeredPane_1.add(limpar_2);
		
		excluir_2 = new JButton("Excluir");
		excluir_2.setEnabled(false);
		excluir_2.setBounds(456, 119, 118, 21);
		layeredPane_1.add(excluir_2);
		excluir_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					try {
						sistema.excluirExemplar(Integer.parseInt(tfCodigo_1.getText()));
					} catch (NumberFormatException | DeleteException | SelectException | NaoCadastradoException e1) {
						JOptionPane.showMessageDialog(null,  e1.getMessage());
					}
					atualizarTabela_2();
					limpar_1();		
			}
		});
		excluir_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		excluir_2.setBackground(UIManager.getColor("Button.darkShadow"));
		
		cadastrar_2 = new JButton("Cadastrar");
		cadastrar_2.setEnabled(false);
		cadastrar_2.setBounds(456, 88, 118, 21);
		layeredPane_1.add(cadastrar_2);
		cadastrar_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					try {
						if(tfPrateleira.getText().equals("") || tfEstante.getText().equals("") || comboBox.getSelectedItem().equals("Selecione")) {      
							JOptionPane.showMessageDialog(null, "Preencha todos os campos");
						}else {
							Exemplar exemplar = new Exemplar();
							exemplar.setId_livro(Integer.parseInt(tfCodigo.getText()));
							exemplar.setPrateleira(Integer.parseInt(tfPrateleira.getText()));
							exemplar.setEstante(Integer.parseInt(tfEstante.getText()));
							exemplar.setColecao(String.valueOf(comboBox.getSelectedItem()));
							try {
								sistema.adicionarExemplar(exemplar);
							} catch (InsertException | SelectException | JaCadastradoException e1) {
								JOptionPane.showMessageDialog(null, e1.getMessage());
							}
						
							atualizarTabela_2();
							limpar_1();
						}
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}	
			}
		});
		cadastrar_2.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		cadastrar_2.setBackground(UIManager.getColor("Button.darkShadow"));
		
		alterar_2 = new JButton("Alterar");
		alterar_2.setEnabled(false);
		alterar_2.setBounds(456, 55, 118, 21);
		layeredPane_1.add(alterar_2);
		alterar_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tfPrateleira.getText().equals("") || tfEstante.getText().equals("") || comboBox.getSelectedItem().equals("Selecione")) {      
					JOptionPane.showMessageDialog(null, "Preencha todos os campos");
				}else {
					Exemplar exemplar = new Exemplar();
					exemplar.setId(Integer.parseInt(tfCodigo_1.getText()));
					exemplar.setPrateleira(Integer.parseInt(tfPrateleira.getText()));
					exemplar.setEstante(Integer.parseInt(tfEstante.getText()));
					exemplar.setColecao(String.valueOf(comboBox.getSelectedItem()));
					try {
						sistema.alterarExemplar(exemplar);
					} catch (UpdateException | SelectException | NaoCadastradoException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}				
					atualizarTabela_2();
					limpar_1();
				}
			}
				
		});
		alterar_2.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		alterar_2.setBackground(UIManager.getColor("Button.darkShadow"));
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Selecione", "Emprestimo", "Permanente", "Reserva"}));
		comboBox.setBounds(127, 117, 282, 24);
		layeredPane_1.add(comboBox);
		
		cadatrar_autor = new JButton("Adicionar Autor");
		cadatrar_autor.setEnabled(false);
		cadatrar_autor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdicionarAutorLivroView frame = new AdicionarAutorLivroView();
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
			}
		});
		cadatrar_autor.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		cadatrar_autor.setBackground(UIManager.getColor("Button.darkShadow"));
		cadatrar_autor.setBounds(1040, 361, 173, 28);
		contentPane.add(cadatrar_autor);
		
		remover_autor = new JButton("Remover Autor");
		remover_autor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					sistema.excluirAutorLivro(Integer.parseInt(tfCodigo.getText()), Integer.parseInt(String.valueOf(table_1.getValueAt(table_1.getSelectedRow(), 0))));
				} catch (NumberFormatException | DeleteException | SelectException | NaoCadastradoException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
				limpar_1();
				atualizarTabela_1();
			}
		});
		remover_autor.setEnabled(false);
		remover_autor.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		remover_autor.setBackground(UIManager.getColor("Button.darkShadow"));
		remover_autor.setBounds(1351, 361, 173, 28);
		contentPane.add(remover_autor);
		
		JLabel lblBuscaDeAssistentes = new JLabel("BUSCA DE EXEMPLARES");
		lblBuscaDeAssistentes.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBuscaDeAssistentes.setFont(new Font("Dialog", Font.BOLD, 15));
		lblBuscaDeAssistentes.setBounds(997, 12, 405, 48);
		contentPane.add(lblBuscaDeAssistentes);
		
		textPCodigo_1 = new JTextField();
		textPCodigo_1.setColumns(10);
		textPCodigo_1.setBounds(1217, 59, 198, 20);
		textPCodigo_1.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				TableRowSorter<TableModel> filtro = null;  
				DefaultTableModel model = (DefaultTableModel) table_2.getModel();  
				filtro = new TableRowSorter<TableModel>(model);  
				table_2.setRowSorter(filtro);
				if (textPCodigo_1.getText().length()==0) filtro.setRowFilter(null);
				else filtro.setRowFilter(RowFilter.regexFilter(textPCodigo_1.getText(), 0));  
			}
		});
		contentPane.add(textPCodigo_1);
		
		JLabel lblCdigo_2 = new JLabel("Código:");
		lblCdigo_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCdigo_2.setFont(new Font("Dialog", Font.BOLD, 15));
		lblCdigo_2.setBounds(1143, 58, 70, 20);
		contentPane.add(lblCdigo_2);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(967, 445, 614, 282);
		contentPane.add(scrollPane_2);
		
		table_2 = new JTable();
		table_2.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Prateleira", "Estante", "Cole\u00E7\u00E3o", "Situa\u00E7\u00E3o"
			}
		));
		scrollPane_2.setViewportView(table_2);
		table_2.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent arg0) {
					try {
						setCamposFromTabela_2();
						cadastrar_2.setEnabled(false);
						excluir_2.setEnabled(true);
						alterar_2.setEnabled(true);
					} catch (NumberFormatException | SelectException e) {
						JOptionPane.showMessageDialog(null, e.getMessage());
					}

			}
		});
		
		JLabel lblExemplaresDoLivro = new JLabel("EXEMPLARES DO LIVRO");
		lblExemplaresDoLivro.setFont(new Font("Dialog", Font.BOLD, 15));
		lblExemplaresDoLivro.setBounds(1189, 413, 203, 20);
		contentPane.add(lblExemplaresDoLivro);
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent arg0) {
					try {
						setCamposFromTabela();
					} catch (NumberFormatException | SelectException e) {
						JOptionPane.showMessageDialog(null, e.getMessage());
					}
					atualizarTabela_1();
					atualizarTabela_2();
					limpar_1();
					cadatrar_autor.setEnabled(true);
					remover_autor.setEnabled(false);
					cadastrar_1.setEnabled(false);
					alterar.setEnabled(true);
					excluir_1.setEnabled(true);
					cadastrar_2.setEnabled(true);
			}
		});

	}

	public void sair() {
		dispose();
	}

	public void limpar() {
		tfIsbn.setText("");
		tfCodigo.setText("");
		tfEditora.setText("");
		tfTitulo.setText("");
		table.clearSelection();
		table_1.clearSelection();
		table_2.clearSelection();
		atualizarTabela_1();
		atualizarTabela_2();
		cadatrar_autor.setEnabled(false);
		remover_autor.setEnabled(false);
		cadastrar_1.setEnabled(true);
		alterar.setEnabled(false);
		excluir_1.setEnabled(false);
		cadastrar_2.setEnabled(false);

	}
	
	public void limpar_1() {
		tfPrateleira.setText("");
		tfCodigo_1.setText("");
		tfEstante.setText("");
		table_2.clearSelection();
		cadastrar_2.setEnabled(true);
		excluir_2.setEnabled(false);
		alterar_2.setEnabled(false);
		comboBox.setSelectedIndex(0);
	}	

	public static void atualizarTabela() {
		try {
			livros = sistema.listarLivros();
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			model.setNumRows(0);
		for (int i=0;i!=livros.size();i++)model.addRow((Object[]) livros.get(i));
		
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	public static void atualizarTabela_1() {
		try {
			if(table.getSelectedRow()!=-1) {
				autores = sistema.listarAutoresLivros(Integer.parseInt(String.valueOf(table.getValueAt(table.getSelectedRow(), 0))));
			}else autores.clear();
			DefaultTableModel model = (DefaultTableModel) table_1.getModel();
			model.setNumRows(0);
		for (int i=0;i!=autores.size();i++)model.addRow((Object[]) autores.get(i));
		
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

public static void atualizarTabela_2() {
		try {
			if(table.getSelectedRow()!=-1) {
				exemplares = sistema.listarExemplaresLivros(Integer.parseInt(String.valueOf(table.getValueAt(table.getSelectedRow(), 0))));
			}else exemplares.clear();
			DefaultTableModel model = (DefaultTableModel) table_2.getModel();
			model.setNumRows(0);
		for (int i=0;i<exemplares.size();i++) model.addRow((Object[]) exemplares.get(i));
		
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	public void setCamposFromTabela() throws NumberFormatException, SelectException {
		tfCodigo.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 0)));
		tfIsbn.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 1)));
		tfTitulo.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 2)));
		tfEditora.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 3)));

	}
	
	public void setCamposFromTabela_2() throws NumberFormatException, SelectException {
		tfCodigo_1.setText(String.valueOf(table_2.getValueAt(table_2.getSelectedRow(), 0)));
		tfPrateleira.setText(String.valueOf(table_2.getValueAt(table_2.getSelectedRow(), 1)));
		tfEstante.setText(String.valueOf(table_2.getValueAt(table_2.getSelectedRow(), 2)));
		String colecao = String.valueOf(table_2.getValueAt(table_2.getSelectedRow(), 3));
		comboBox.setSelectedItem(colecao);
	}
}
