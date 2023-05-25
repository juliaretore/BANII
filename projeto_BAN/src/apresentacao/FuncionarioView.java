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

public class FuncionarioView extends JFrame {


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textPNome;
	private JTextField textPCodigo;

	private static List<Object> assistentes = new ArrayList<Object>();
	private static List<Object> assistentes2 = new ArrayList<Object>();

	private static List<Object> bibliotecarios = new ArrayList<Object>();
	Funcionario funcionario = new Funcionario();		
	private static Sistema sistema;
	private JTextField tfNome;
	static JTextField tfCodigo;
	JComboBox comboBox_1 = new JComboBox();
	JComboBox comboBox_1_1 = new JComboBox();
	private static JTable table = new JTable();
	private static FuncionarioView funcionarioView;
	private static JTable table_1;
	private JTextField tfLogin;
	private JTextField tfSalario;
	private JTextField tfCodigo_1;
	private JTextField tfNome_1;
	private JTextField tfLogin_1;
	private JTextField tfSalario_1;
	private JButton cadastrar_2;
	private JButton cadastrar_1;
	private JButton cadatrar_asisstente;
	private JButton alterar;
	private JButton excluir_1;
	private JButton alterar_2;
	private JButton excluir_2;
	private JButton remover_assistente;
	private JTextField textPNome_1;
	private JTextField textPCodigo_1;
	private JTextField tfemail;
	private JTextField tfemail_2;
	static JTextField textusuario;
	static JTextField tfIdUsuario;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FuncionarioView frame = new FuncionarioView();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);					
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});
	}

	public static FuncionarioView getInstance() {
        if(funcionarioView==null) funcionarioView=new FuncionarioView();
        return funcionarioView;
    } 
	public FuncionarioView() {
		try {
			sistema = new Sistema();
		} catch (ClassNotFoundException | SQLException | SelectException e) {
			JOptionPane.showMessageDialog(null,  e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		
		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent arg0) {
				atualizarTabela();
				atualizarTabela_1();
			}
		});
		
		
		setTitle("Gerenciar Funcionaários");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0, 0,  1842, 1080);

//		setBounds(200, 1920,  1080, 0);
//		setExtendedState(JFrame.MAXIMIZED_BOTH);
		contentPane = new JPanel();
		contentPane.setEnabled(false);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//		ImageIcon imagemTituloJanela = new javax.swing.ImageIcon(getClass().getResource("/img/logo.jpg"));
		//		setIconImage(imagemTituloJanela.getImage());
		JLabel lblNewLabel = new JLabel("New label");
//		lblNewLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/background2.png")));
								
		
		JLabel lblBusca = new JLabel("BUSCA DE BIBLIOTECÁRIOS");
		lblBusca.setFont(new Font("Dialog", Font.BOLD, 15));
		lblBusca.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBusca.setBounds(202, 12, 405, 48);
		contentPane.add(lblBusca);
		
		JLabel lblArtistasCadastrados = new JLabel("BIBLIOTECÁRIOS CADASTRADOS");
		lblArtistasCadastrados.setFont(new Font("Segoe UI Symbol", Font.BOLD, 15));
		lblArtistasCadastrados.setBounds(326, 154, 337, 20);
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
		
		
		JLabel lblCdigo = new JLabel("ID:");
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
		scrollPane.setBackground(new Color(255, 255, 255));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(169, 186, 611, 431);
		contentPane.add(scrollPane);
		table.setSelectionBackground(UIManager.getColor("Button.focus"));
		table.setBackground(Color.WHITE);
		

		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Nome", "Login", "Turno", "Sal\u00E1rio", "Email"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, true, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		table.getColumnModel().getColumn(1).setPreferredWidth(90);
		table.getColumnModel().getColumn(2).setPreferredWidth(80);
		table.getColumnModel().getColumn(3).setPreferredWidth(80);
		table.getColumnModel().getColumn(4).setPreferredWidth(80);
		table.getColumnModel().getColumn(5).setPreferredWidth(140);
		scrollPane.setViewportView(table);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(967, 186, 614, 431);
		contentPane.add(scrollPane_1);
		
		table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Nome", "Login", "Turno", "Salario", "Email"
			}
		));
		table_1.getColumnModel().getColumn(0).setPreferredWidth(20);
		table_1.getColumnModel().getColumn(1).setPreferredWidth(90);
		table_1.getColumnModel().getColumn(2).setPreferredWidth(80);
		table_1.getColumnModel().getColumn(3).setPreferredWidth(80);
		table_1.getColumnModel().getColumn(4).setPreferredWidth(80);
		table_1.getColumnModel().getColumn(5).setPreferredWidth(140);
		scrollPane_1.setViewportView(table_1);
		table_1.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent arg0) {
				try {
					setCamposFromTabela_1();
					cadastrar_2.setEnabled(false);
					if (table.getSelectedRow()!=-1) cadatrar_asisstente.setEnabled(true);
						
					remover_assistente.setEnabled(true);
					excluir_2.setEnabled(true);
					alterar_2.setEnabled(true);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (SelectException e) {
					e.printStackTrace();
				}
			}
		});
		
		JLabel lblTelefones = new JLabel("ASSISTENTES CADASTRADOS");
		lblTelefones.setFont(new Font("Dialog", Font.BOLD, 15));
		lblTelefones.setBounds(1154, 154, 272, 20);
		contentPane.add(lblTelefones);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(169, 657, 611, 221);
		contentPane.add(layeredPane);
		
		JLabel lblNome_1 = new JLabel("Nome:");
		lblNome_1.setBounds(22, 63, 70, 20);
		layeredPane.add(lblNome_1);
		lblNome_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNome_1.setFont(new Font("Segoe UI Symbol", Font.BOLD, 15));
		
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
		
		tfNome = new JTextField();
		tfNome.setBounds(127, 64, 282, 19);
		layeredPane.add(tfNome);
		tfNome.setColumns(10);
		
		JLabel lblNome_1_1 = new JLabel("Login:");
		lblNome_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNome_1_1.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNome_1_1.setBounds(22, 89, 70, 20);
		layeredPane.add(lblNome_1_1);
		
		tfLogin = new JTextField();
		tfLogin.setColumns(10);
		tfLogin.setBounds(127, 89, 282, 19);
		layeredPane.add(tfLogin);
		
		JLabel btnTurno = new JLabel("Turno:");
		btnTurno.setBounds(22, 118, 70, 20);
		layeredPane.add(btnTurno);
		btnTurno.setHorizontalAlignment(SwingConstants.RIGHT);
		btnTurno.setFont(new Font("Segoe UI Symbol", Font.BOLD, 15));
		comboBox_1.setBounds(127, 118, 283, 21);
		layeredPane.add(comboBox_1);
		
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"Selecione", "Vespertino", "Matutino", "Noturno", "Integral"}));
		comboBox_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		comboBox_1.setBackground(SystemColor.window);
		
		tfSalario = new JTextField();
		tfSalario.setColumns(10);
		tfSalario.setBounds(127, 151, 282, 19);
		layeredPane.add(tfSalario);
		
		JLabel lblSalrio = new JLabel("Salário:");
		lblSalrio.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSalrio.setFont(new Font("Dialog", Font.BOLD, 15));
		lblSalrio.setBounds(22, 150, 70, 20);
		layeredPane.add(lblSalrio);
		
		cadastrar_1 = new JButton("Cadastrar");
		cadastrar_1.setBounds(456, 118, 118, 21);
		layeredPane.add(cadastrar_1);
		cadastrar_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		cadastrar_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					if(tfNome.getText().equals("") || tfLogin.getText().equals("") || tfSalario.getText().equals("") || comboBox_1.getSelectedItem().equals("Selecione")) {      
						JOptionPane.showMessageDialog(null, "Preencha todos os campos");
					}else {
						Funcionario funcionario = new Funcionario();
						funcionario.setNome(tfNome.getText());
						funcionario.setLogin(tfLogin.getText());
						funcionario.setSalario(Double.parseDouble(tfSalario.getText()));
						funcionario.setSenha(gerarSenhaAleatoria(8));
						funcionario.setTipo(1);
						funcionario.setTurno(String.valueOf(comboBox_1.getSelectedItem()));	
						funcionario.setEmail(tfemail.getText());
						try {
							sistema.adicionarFuncionario(funcionario);
						} catch (InsertException | SelectException | JaCadastradoException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage());
						}
					
						atualizarTabela();
						limpar();
					}	
			}
			
		});
		cadastrar_1.setBackground(UIManager.getColor("Button.darkShadow"));
		
		alterar = new JButton("Alterar");
		alterar.setEnabled(false);
		alterar.setBounds(456, 89, 118, 21);
		layeredPane.add(alterar);
		alterar.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		alterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					Funcionario funcionario = new Funcionario();
					funcionario.setId(Integer.parseInt(tfCodigo.getText()));
					funcionario.setNome(tfNome.getText());
					funcionario.setLogin(tfLogin.getText());
					funcionario.setSalario(Double.parseDouble(tfSalario.getText()));
					funcionario.setTurno(String.valueOf(comboBox_1.getSelectedItem()));	
					funcionario.setEmail(tfemail.getText());
					try {
						sistema.alterarFuncionario(funcionario);
					} catch (UpdateException | SelectException | NaoCadastradoException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					};						
			
				atualizarTabela();
				limpar();
			}
		});
		alterar.setBackground(UIManager.getColor("Button.darkShadow"));
		
	
		
		excluir_1 = new JButton("Excluir");
		excluir_1.setEnabled(false);
		excluir_1.setBounds(456, 150, 118, 21);
		layeredPane.add(excluir_1);
		excluir_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		excluir_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					try {
						sistema.excluirFuncionario(Integer.parseInt(tfCodigo.getText()), 0);
					} catch (NumberFormatException | DeleteException | SelectException | NaoCadastradoException e1) {
						JOptionPane.showMessageDialog(null,  e1.getMessage());
					}
					atualizarTabela();
					atualizarTabela_1();
					limpar();
					limpar_1();				
			}
		});
		excluir_1.setBackground(UIManager.getColor("Button.darkShadow"));
		
		JButton limpar = new JButton("Limpar");
		limpar.setBounds(456, 62, 118, 21);
		layeredPane.add(limpar);
		limpar.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		limpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		limpar.setBackground(UIManager.getColor("Button.focus"));
		
		tfemail = new JTextField();
		tfemail.setColumns(10);
		tfemail.setBounds(127, 182, 282, 19);
		layeredPane.add(tfemail);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail.setFont(new Font("Dialog", Font.BOLD, 15));
		lblEmail.setBounds(22, 182, 70, 20);
		layeredPane.add(lblEmail);
		
		JLayeredPane layeredPane_1 = new JLayeredPane();
		layeredPane_1.setBounds(970, 657, 611, 221);
		contentPane.add(layeredPane_1);
		
		JLabel lblNome_1_2 = new JLabel("Nome:");
		lblNome_1_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNome_1_2.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNome_1_2.setBounds(22, 63, 70, 20);
		layeredPane_1.add(lblNome_1_2);
		
		JLabel lblCdigo_1_1 = new JLabel("ID:");
		lblCdigo_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCdigo_1_1.setFont(new Font("Dialog", Font.BOLD, 15));
		lblCdigo_1_1.setBounds(22, 31, 70, 20);
		layeredPane_1.add(lblCdigo_1_1);
		
		tfCodigo_1 = new JTextField();
		tfCodigo_1.setEditable(false);
		tfCodigo_1.setColumns(10);
		tfCodigo_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		tfCodigo_1.setBounds(127, 31, 33, 19);
		layeredPane_1.add(tfCodigo_1);
		
		tfNome_1 = new JTextField();
		tfNome_1.setColumns(10);
		tfNome_1.setBounds(127, 64, 282, 19);
		layeredPane_1.add(tfNome_1);
		
		JLabel lblNome_1_1_1 = new JLabel("Login:");
		lblNome_1_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNome_1_1_1.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNome_1_1_1.setBounds(22, 89, 70, 20);
		layeredPane_1.add(lblNome_1_1_1);
		
		tfLogin_1 = new JTextField();
		tfLogin_1.setColumns(10);
		tfLogin_1.setBounds(127, 89, 282, 19);
		layeredPane_1.add(tfLogin_1);
		
		JLabel btnTurno_1 = new JLabel("Turno:");
		btnTurno_1.setHorizontalAlignment(SwingConstants.RIGHT);
		btnTurno_1.setFont(new Font("Dialog", Font.BOLD, 15));
		btnTurno_1.setBounds(22, 118, 70, 20);
		layeredPane_1.add(btnTurno_1);
		
		comboBox_1_1.setModel(new DefaultComboBoxModel(new String[] {"Selecione", "Vespertino", "Matutino", "Noturno", "Integral"}));
		comboBox_1_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		comboBox_1_1.setBackground(SystemColor.window);
		comboBox_1_1.setBounds(127, 118, 283, 21);
		layeredPane_1.add(comboBox_1_1);
		
		tfSalario_1 = new JTextField();
		tfSalario_1.setColumns(10);
		tfSalario_1.setBounds(127, 151, 282, 19);
		layeredPane_1.add(tfSalario_1);
		
		JLabel lblSalrio_1 = new JLabel("Salário:");
		lblSalrio_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSalrio_1.setFont(new Font("Dialog", Font.BOLD, 15));
		lblSalrio_1.setBounds(32, 150, 70, 20);
		layeredPane_1.add(lblSalrio_1);
		
		JButton limpar_2 = new JButton("Limpar");
		limpar_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar_1();
			}
		});
		limpar_2.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		limpar_2.setBackground(UIManager.getColor("Button.darkShadow"));
		limpar_2.setBounds(456, 63, 118, 21);
		layeredPane_1.add(limpar_2);
		
		excluir_2 = new JButton("Excluir");
		excluir_2.setEnabled(false);
		excluir_2.setBounds(456, 150, 118, 21);
		layeredPane_1.add(excluir_2);
		excluir_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					try {
						sistema.excluirFuncionario(Integer.parseInt(tfCodigo_1.getText()),1);
					} catch (NumberFormatException | DeleteException | SelectException | NaoCadastradoException e1) {
						JOptionPane.showMessageDialog(null,  e1.getMessage());
					}
					atualizarTabela();
					atualizarTabela_1();
					limpar();
					limpar_1();				
				
			}
		});
		excluir_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		excluir_2.setBackground(UIManager.getColor("Button.darkShadow"));
		
		cadastrar_2 = new JButton("Cadastrar");
		cadastrar_2.setBounds(456, 118, 118, 21);
		layeredPane_1.add(cadastrar_2);
		cadastrar_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					try {
						if(tfNome_1.getText().equals("") || tfLogin_1.getText().equals("") || tfSalario_1.getText().equals("") || comboBox_1_1.getSelectedItem().equals("Selecione")) {      
							JOptionPane.showMessageDialog(null, "Preencha todos os campos");
						}else {
							Funcionario funcionario = new Funcionario();
							funcionario.setNome(tfNome_1.getText());
							funcionario.setLogin(tfLogin_1.getText());
							funcionario.setSalario(Double.parseDouble(tfSalario_1.getText()));
							funcionario.setSenha(gerarSenhaAleatoria(8));
							funcionario.setTipo(2);
							funcionario.setTurno(String.valueOf(comboBox_1_1.getSelectedItem()));	
							funcionario.setEmail(tfemail_2.getText());
							try {
								sistema.adicionarFuncionario(funcionario);
							} catch (InsertException | SelectException | JaCadastradoException e1) {
								JOptionPane.showMessageDialog(null, e1.getMessage());
							}
						
							atualizarTabela_1();
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
		alterar_2.setBounds(456, 89, 118, 21);
		layeredPane_1.add(alterar_2);
		alterar_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Funcionario funcionario = new Funcionario();
				funcionario.setId(Integer.parseInt(tfCodigo_1.getText()));
				funcionario.setNome(tfNome_1.getText());
				funcionario.setLogin(tfLogin_1.getText());
				funcionario.setSalario(Double.parseDouble(tfSalario_1.getText()));
				funcionario.setTurno(String.valueOf(comboBox_1_1.getSelectedItem()));	
				funcionario.setEmail(tfemail_2.getText());
				try {
					sistema.alterarFuncionario(funcionario);
				} catch (UpdateException | SelectException | NaoCadastradoException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				};						
		
			atualizarTabela();
			limpar();
			}
				
		});
		alterar_2.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		alterar_2.setBackground(UIManager.getColor("Button.darkShadow"));
		
		tfemail_2 = new JTextField();
		tfemail_2.setColumns(10);
		tfemail_2.setBounds(127, 182, 282, 19);
		layeredPane_1.add(tfemail_2);
		
		JLabel lblEmail_1 = new JLabel("Email:");
		lblEmail_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail_1.setFont(new Font("Dialog", Font.BOLD, 15));
		lblEmail_1.setBounds(22, 181, 70, 20);
		layeredPane_1.add(lblEmail_1);
		
		cadatrar_asisstente = new JButton("Adicionar Assistente");
		cadatrar_asisstente.setEnabled(false);
		cadatrar_asisstente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdicionarSupervisaoView frame = new AdicionarSupervisaoView();
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
			}
		});
		cadatrar_asisstente.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		cadatrar_asisstente.setBackground(UIManager.getColor("Button.darkShadow"));
		cadatrar_asisstente.setBounds(1032, 629, 173, 28);
		contentPane.add(cadatrar_asisstente);
		
		remover_assistente = new JButton("Remover Assistente");
		remover_assistente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					sistema.excluirSupervisao(Integer.parseInt(tfCodigo.getText()), Integer.parseInt(tfCodigo_1.getText()));
				} catch (NumberFormatException | DeleteException | SelectException | NaoCadastradoException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
				atualizarTabela_1_1();
				limpar_1();
			}
		});
		remover_assistente.setEnabled(false);
		remover_assistente.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		remover_assistente.setBackground(UIManager.getColor("Button.darkShadow"));
		remover_assistente.setBounds(1367, 629, 173, 28);
		contentPane.add(remover_assistente);
		
		JLabel lblBuscaDeAssistentes = new JLabel("BUSCA DE ASSISTENTES");
		lblBuscaDeAssistentes.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBuscaDeAssistentes.setFont(new Font("Dialog", Font.BOLD, 15));
		lblBuscaDeAssistentes.setBounds(997, 12, 405, 48);
		contentPane.add(lblBuscaDeAssistentes);
		
		JLabel lblNome_2 = new JLabel("Nome:");
		lblNome_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNome_2.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNome_2.setBounds(967, 58, 70, 20);
		contentPane.add(lblNome_2);
		
		textPNome_1 = new JTextField();
		textPNome_1.addCaretListener(new CaretListener() {
				public void caretUpdate(CaretEvent arg0) {
					TableRowSorter<TableModel> filtro = null;  
					DefaultTableModel model = (DefaultTableModel) table_1.getModel();  
					filtro = new TableRowSorter<TableModel>(model);  
					table_1.setRowSorter(filtro); 
					if (textPNome_1.getText().length()==0) filtro.setRowFilter(null);
					else filtro.setRowFilter(RowFilter.regexFilter("(?i)" + textPNome_1.getText(), 1));  
					
				}
		});
		textPNome_1.setColumns(10);
		textPNome_1.setBounds(1068, 59, 198, 20);
		contentPane.add(textPNome_1);
		
		textPCodigo_1 = new JTextField();
		textPCodigo_1.setColumns(10);
		textPCodigo_1.setBounds(1367, 59, 198, 20);
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
		
		JLabel lblCdigo_2 = new JLabel("ID:");
		lblCdigo_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCdigo_2.setFont(new Font("Dialog", Font.BOLD, 15));
		lblCdigo_2.setBounds(1289, 61, 70, 20);
		contentPane.add(lblCdigo_2);
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent arg0) {
					try {
						setCamposFromTabela();
					} catch (NumberFormatException | SelectException e) {
						JOptionPane.showMessageDialog(null, e.getMessage());
					}
					atualizarTabela_1_1();
					limpar_1();
					cadatrar_asisstente.setEnabled(true);
					remover_assistente.setEnabled(false);
					cadastrar_1.setEnabled(false);
					alterar.setEnabled(true);
					excluir_1.setEnabled(true);
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
		btnNewButton.setBounds(1630, 12, 133, 31);
		contentPane.add(btnNewButton);
		
		lblNewLabel.setBounds(12, -16, 1843, 1047);
		contentPane.add(lblNewLabel);

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
		tfNome.setText("");
		tfCodigo.setText("");
		tfSalario.setText("");
		tfLogin.setText("");
		comboBox_1.setSelectedIndex(0);
		table.clearSelection();
		atualizarTabela_1();
		cadatrar_asisstente.setEnabled(false);
		remover_assistente.setEnabled(false);
		cadastrar_1.setEnabled(true);
		alterar.setEnabled(true);
		alterar.setEnabled(false);
		excluir_1.setEnabled(false);
		tfemail.setText("");

	}
	
	public void limpar_1() {
		tfNome_1.setText("");
		tfCodigo_1.setText("");
		tfSalario_1.setText("");
		tfLogin_1.setText("");
		comboBox_1_1.setSelectedIndex(0);
		table_1.clearSelection();
		cadastrar_2.setEnabled(true);
		excluir_2.setEnabled(false);
		alterar_2.setEnabled(false);
		remover_assistente.setEnabled(false);
		tfemail_2.setText("");
	}	

	public static void atualizarTabela() {
		try {
			bibliotecarios = sistema.listarBibliotecarios();
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			model.setNumRows(0);
		for (int i=0;i!=bibliotecarios.size();i++)model.addRow((Object[]) bibliotecarios.get(i));
		
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	public static void atualizarTabela_1() {
		try {
			assistentes = sistema.listarAssistentes();
			DefaultTableModel model = (DefaultTableModel) table_1.getModel();
			model.setNumRows(0);
		for (int i=0;i!=assistentes.size();i++)model.addRow((Object[]) assistentes.get(i));
		
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

public static void atualizarTabela_1_1() {
		try {
			int id_bibliotecario = Integer.parseInt(String.valueOf(table.getValueAt(table.getSelectedRow(), 0)));
			assistentes2 = sistema.listarAssistentesBibliotecario(id_bibliotecario);
			DefaultTableModel model = (DefaultTableModel) table_1.getModel();
			model.setNumRows(0);
		for (int i=0;i<assistentes2.size();i++) model.addRow((Object[]) assistentes2.get(i));
		
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	public void setCamposFromTabela() throws NumberFormatException, SelectException {
		tfCodigo.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 0)));
		tfNome.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 1)));
		tfLogin.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 2)));
		String turno = String.valueOf(table.getValueAt(table.getSelectedRow(), 3));
		comboBox_1.setSelectedItem(turno);
		tfSalario.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 4)));
		tfemail.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 5)));

	}
	
	public void setCamposFromTabela_1() throws NumberFormatException, SelectException {
		tfCodigo_1.setText(String.valueOf(table_1.getValueAt(table_1.getSelectedRow(), 0)));
		tfNome_1.setText(String.valueOf(table_1.getValueAt(table_1.getSelectedRow(), 1)));
		tfLogin_1.setText(String.valueOf(table_1.getValueAt(table_1.getSelectedRow(), 2)));
		String turno = String.valueOf(table_1.getValueAt(table_1.getSelectedRow(), 3));
		comboBox_1_1.setSelectedItem(turno);
		tfSalario_1.setText(String.valueOf(table_1.getValueAt(table_1.getSelectedRow(), 4)));
		tfemail_2.setText(String.valueOf(table_1.getValueAt(table_1.getSelectedRow(), 5)));
	}
	
	public static String gerarSenhaAleatoria(int len){
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-*!#@$%";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++){
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }
 
        return sb.toString();
    }
}