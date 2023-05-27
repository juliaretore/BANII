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
	static JTextField tfCodigo_1;
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
	private JButton historico;
	private JTextField tfPTitulo;
	static JTextField textusuario;
	static JTextField tfIdUsuario;
	

	
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


		setTitle("Gerenciar Livros");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
		sair.setBounds(850, 964, 173, 20);
		contentPane.add(sair);
		
		textPCodigo = new JTextField();
		textPCodigo.setBounds(319, 59, 181, 20);
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
		textPNome.setBounds(582, 59, 198, 20);
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
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(243, 173, 611, 528);
		contentPane.add(scrollPane);
		table.setSelectionBackground(new Color(212, 226, 250));
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
		scrollPane_1.setBounds(1017, 173, 614, 163);
		contentPane.add(scrollPane_1);
		
		table_1 = new JTable();
		table_1.setSelectionBackground(new Color(212, 226, 250));
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
					historico.setEnabled(false);

			}
		});
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(243, 750, 611, 156);
		contentPane.add(layeredPane);
		
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
		
		tfTitulo = new JTextField();
		tfTitulo.setColumns(10);
		tfTitulo.setBounds(127, 89, 282, 19);
		layeredPane.add(tfTitulo);
		
		tfEditora = new JTextField();
		tfEditora.setColumns(10);
		tfEditora.setBounds(127, 119, 282, 19);
		layeredPane.add(tfEditora);
		
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
		cadastrar_1.setBackground(Color.WHITE);
		
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
		alterar.setBackground(Color.WHITE);
		
	
		
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
		excluir_1.setBackground(Color.WHITE);
		
		JButton limpar = new JButton("Limpar");
		limpar.setBounds(456, 31, 118, 21);
		layeredPane.add(limpar);
		limpar.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		limpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		limpar.setBackground(Color.WHITE);
		
		JLabel lblid_2 = new JLabel("ID:");
		lblid_2.setForeground(new Color(85, 97, 120));
		lblid_2.setFont(new Font("Lato Black", Font.BOLD, 15));
		lblid_2.setBounds(64, 31, 52, 20);
		layeredPane.add(lblid_2);
		
		JLabel lblisbn_1 = new JLabel("ISBN:");
		lblisbn_1.setBounds(46, 64, 52, 20);
		layeredPane.add(lblisbn_1);
		lblisbn_1.setForeground(new Color(85, 97, 120));
		lblisbn_1.setFont(new Font("Lato Black", Font.BOLD, 15));
		
		JLabel lbltitulo_1 = new JLabel("Titulo:");
		lbltitulo_1.setBounds(40, 89, 52, 20);
		layeredPane.add(lbltitulo_1);
		lbltitulo_1.setForeground(new Color(85, 97, 120));
		lbltitulo_1.setFont(new Font("Lato Black", Font.BOLD, 15));
		
		JLabel lbltitulo_1_1 = new JLabel("Editora:");
		lbltitulo_1_1.setBounds(33, 119, 76, 20);
		layeredPane.add(lbltitulo_1_1);
		lbltitulo_1_1.setForeground(new Color(85, 97, 120));
		lbltitulo_1_1.setFont(new Font("Lato Black", Font.BOLD, 15));
		
		JLayeredPane layeredPane_1 = new JLayeredPane();
		layeredPane_1.setBounds(1020, 750, 611, 156);
		contentPane.add(layeredPane_1);
		
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
		
		tfEstante = new JTextField();
		tfEstante.setColumns(10);
		tfEstante.setBounds(127, 89, 282, 19);
		layeredPane_1.add(tfEstante);
		
		JButton limpar_2 = new JButton("Limpar");
		limpar_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar_1();
			}
		});
		limpar_2.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		limpar_2.setBackground(Color.WHITE);
		limpar_2.setBounds(456, 30, 118, 21);
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
		excluir_2.setBackground(Color.WHITE);
		
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
		cadastrar_2.setBackground(Color.WHITE);
		
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
		alterar_2.setBackground(Color.WHITE);
		
		comboBox = new JComboBox();
		comboBox.setBackground(Color.WHITE);
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Selecione", "Emprestimo", "Permanente", "Reserva"}));
		comboBox.setBounds(127, 117, 282, 24);
		layeredPane_1.add(comboBox);
		
		JLabel lblid_3 = new JLabel("ID:");
		lblid_3.setForeground(new Color(85, 97, 120));
		lblid_3.setFont(new Font("Lato Black", Font.BOLD, 15));
		lblid_3.setBounds(93, 31, 52, 20);
		layeredPane_1.add(lblid_3);
		
		JLabel lblprateleira = new JLabel("Prateleira:");
		lblprateleira.setBounds(37, 62, 116, 20);
		layeredPane_1.add(lblprateleira);
		lblprateleira.setForeground(new Color(85, 97, 120));
		lblprateleira.setFont(new Font("Lato Black", Font.BOLD, 15));
		
		JLabel lblestante = new JLabel("Estante:");
		lblestante.setBounds(50, 88, 116, 20);
		layeredPane_1.add(lblestante);
		lblestante.setForeground(new Color(85, 97, 120));
		lblestante.setFont(new Font("Lato Black", Font.BOLD, 15));
		
		JLabel lblcolecao = new JLabel("Coleção:");
		lblcolecao.setBounds(50, 120, 116, 20);
		layeredPane_1.add(lblcolecao);
		lblcolecao.setForeground(new Color(85, 97, 120));
		lblcolecao.setFont(new Font("Lato Black", Font.BOLD, 15));
		
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
		cadatrar_autor.setBackground(Color.WHITE);
		cadatrar_autor.setBounds(1057, 361, 173, 28);
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
		remover_autor.setBackground(Color.WHITE);
		remover_autor.setBounds(1424, 361, 173, 28);
		contentPane.add(remover_autor);
		
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
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(1017, 445, 614, 256);
		contentPane.add(scrollPane_2);
		
		table_2 = new JTable();
		table_2.setSelectionBackground(new Color(212, 226, 250));
		table_2.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Prateleira", "Estante", "Cole\u00E7\u00E3o", "Situa\u00E7\u00E3o", "Usuario"
			}
		));
		table_2.getColumnModel().getColumn(0).setPreferredWidth(15);
		table_2.getColumnModel().getColumn(0).setMinWidth(10);
		table_2.getColumnModel().getColumn(1).setPreferredWidth(20);
		table_2.getColumnModel().getColumn(2).setPreferredWidth(20);
		scrollPane_2.setViewportView(table_2);
		table_2.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent arg0) {
					try {
						setCamposFromTabela_2();
						cadastrar_2.setEnabled(false);
						excluir_2.setEnabled(true);
						alterar_2.setEnabled(true);
						historico.setEnabled(true);

					} catch (NumberFormatException | SelectException e) {
						JOptionPane.showMessageDialog(null, e.getMessage());
					}

			}
		});
		
		historico = new JButton("Ver histórico de empréstimo");
		historico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HistoricoEmprestimosLivroView frame = new HistoricoEmprestimosLivroView();
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
				
				
			}
		});
		historico.setEnabled(false);
		historico.setBorder(new LineBorder(new Color(0, 0, 0)));
		historico.setBackground(Color.WHITE);
		historico.setBounds(1204, 713, 246, 20);
		contentPane.add(historico);
		
		tfPTitulo = new JTextField();
		tfPTitulo.setColumns(10);
		tfPTitulo.setBounds(319, 91, 181, 20);
		contentPane.add(tfPTitulo);
		tfPTitulo.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				TableRowSorter<TableModel> filtro = null;  
				DefaultTableModel model = (DefaultTableModel) table.getModel();  
				filtro = new TableRowSorter<TableModel>(model);  
				table.setRowSorter(filtro);
				if (tfPTitulo.getText().length()==0) filtro.setRowFilter(null);
				else filtro.setRowFilter(RowFilter.regexFilter(tfPTitulo.getText(), 2));  
			}
		});
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
		
		textusuario = new JTextField();
		textusuario.setBackground(SystemColor.window);
		textusuario.setDisabledTextColor(new Color(0, 0, 0));
		textusuario.setEditable(false);
		textusuario.setBounds(1545, 12, 86, 29);
		contentPane.add(textusuario);
		textusuario.setColumns(10);
		
		
		tfIdUsuario = new JTextField();
		tfIdUsuario.setBackground(SystemColor.window);
		tfIdUsuario.setEditable(false);
		tfIdUsuario.setColumns(10);
		tfIdUsuario.setBounds(1518, 12, 29, 29);
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
		btnNewButton.setBounds(1641, 12, 133, 31);
		contentPane.add(btnNewButton);
		
		JLabel lblBuscaDeExemplares = new JLabel("BUSCA DE EXEMPLARES ");
		lblBuscaDeExemplares.setForeground(new Color(85, 97, 120));
		lblBuscaDeExemplares.setFont(new Font("Lato Black", Font.BOLD, 16));
		lblBuscaDeExemplares.setBounds(1217, 28, 337, 20);
		contentPane.add(lblBuscaDeExemplares);
		
		JLabel lblAutoresDoLivro = new JLabel("AUTORES DO LIVRO");
		lblAutoresDoLivro.setForeground(new Color(85, 97, 120));
		lblAutoresDoLivro.setFont(new Font("Lato Black", Font.BOLD, 16));
		lblAutoresDoLivro.setBounds(1245, 142, 337, 20);
		contentPane.add(lblAutoresDoLivro);
		
		JLabel lblExemplaresDoLivro_1 = new JLabel("EXEMPLARES DO LIVRO");
		lblExemplaresDoLivro_1.setForeground(new Color(85, 97, 120));
		lblExemplaresDoLivro_1.setFont(new Font("Lato Black", Font.BOLD, 16));
		lblExemplaresDoLivro_1.setBounds(1230, 413, 337, 20);
		contentPane.add(lblExemplaresDoLivro_1);
		
		JLabel lblBuscaDoLivro = new JLabel("BUSCA DE LIVROS");
		lblBuscaDoLivro.setForeground(new Color(85, 97, 120));
		lblBuscaDoLivro.setFont(new Font("Lato Black", Font.BOLD, 16));
		lblBuscaDoLivro.setBounds(462, 18, 337, 20);
		contentPane.add(lblBuscaDoLivro);
		
		JLabel lblLivrosCadastrados = new JLabel("LIVROS CADASTRADOS");
		lblLivrosCadastrados.setForeground(new Color(85, 97, 120));
		lblLivrosCadastrados.setFont(new Font("Lato Black", Font.BOLD, 16));
		lblLivrosCadastrados.setBounds(446, 142, 337, 20);
		contentPane.add(lblLivrosCadastrados);
		
		JLabel lblid_1 = new JLabel("ID:");
		lblid_1.setForeground(new Color(85, 97, 120));
		lblid_1.setFont(new Font("Lato Black", Font.BOLD, 15));
		lblid_1.setBounds(1178, 59, 52, 20);
		contentPane.add(lblid_1);
		
		JLabel lblid = new JLabel("ID:");
		lblid.setForeground(new Color(85, 97, 120));
		lblid.setFont(new Font("Lato Black", Font.BOLD, 15));
		lblid.setBounds(285, 59, 52, 20);
		contentPane.add(lblid);
		
		JLabel lblisbn = new JLabel("ISBN:");
		lblisbn.setForeground(new Color(85, 97, 120));
		lblisbn.setFont(new Font("Lato Black", Font.BOLD, 15));
		lblisbn.setBounds(529, 59, 52, 20);
		contentPane.add(lblisbn);
		
		JLabel lbltitulo = new JLabel("Titulo:");
		lbltitulo.setForeground(new Color(85, 97, 120));
		lbltitulo.setFont(new Font("Lato Black", Font.BOLD, 15));
		lbltitulo.setBounds(258, 91, 52, 20);
		contentPane.add(lbltitulo);
		
		lblNewLabel.setBounds(12, 0, 1898, 1047);
		contentPane.add(lblNewLabel);
		
		table.getTableHeader().setOpaque(false);
		table.getTableHeader().setBackground(new Color(225, 235, 252));
		table.setFillsViewportHeight(true);
		table_1.getTableHeader().setOpaque(false);
		table_1.getTableHeader().setBackground(new Color(225, 235, 252));
		table_1.setFillsViewportHeight(true);
		table_2.getTableHeader().setOpaque(false);
		table_2.getTableHeader().setBackground(new Color(225, 235, 252));
		table_2.setFillsViewportHeight(true);

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
		historico.setEnabled(false);

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
		historico.setEnabled(false);

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
