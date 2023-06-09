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
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.bson.types.ObjectId;

import dados.Categoria;
import dados.Endereco;
import dados.Usuario;
import exceptions.DeleteException;
import exceptions.InsertException;
import exceptions.JaCadastradoException;
import exceptions.NaoCadastradoException;
import exceptions.SelectException;
import negocio.Sistema;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLayeredPane;

public class UsuarioView extends JFrame {


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textPNome;
	private JTextField textPCodigo;

	private static List<Object> telefones = new ArrayList<Object>();
	private static List<Object> usuarios = new ArrayList<Object>();
	Usuario usuario = new Usuario();		
	private static Sistema sistema;
	private JTextField tfNome;
	private JTextField tfCodigo;
	JComboBox comboBox = new JComboBox();
	JComboBox comboBox_1 = new JComboBox();
	JComboBox comboBox_2 = new JComboBox();
	private static JTable table = new JTable();
	private static UsuarioView usuarioView;
	private static JTable table_1;
	private JTextField tfCidade;
	private JTextField tfBairro;
	private JTextField tfRua;
	private JTextField tfNumero;
	private JTextField tfComplemento;
	private JTextField tfCodigoEndereco;
	private JTextField tfTelefone;
	private JButton cadastrar_1;
	private JButton alterar_1;
	private JButton alterar_2;
	private JButton excluir_2;
	private JButton cadastrar_2;
	private JTextField tfemail;
	static JTextField textusuario;
	static JTextField tfIdUsuario;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UsuarioView frame = new UsuarioView();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);					
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});
	}

	public static UsuarioView getInstance() throws Exception {
        if(usuarioView==null) usuarioView=new UsuarioView();
        atualizarTabela();
        return usuarioView;
    } 
	public UsuarioView() throws Exception {
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

		setTitle("Gerenciar Usuários");
		setResizable(false);
		setBounds(0, 0,  1930, 1080);
//		setExtendedState(JFrame.MAXIMIZED_BOTH);;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setEnabled(false);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
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
		sair.setBounds(899, 959, 173, 20);
		contentPane.add(sair);
		
		textPCodigo = new JTextField();
		textPCodigo.setBounds(552, 61, 182, 20);
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
		textPNome.setBounds(834, 61, 198, 20);
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
		scrollPane.setBounds(169, 186, 1194, 442);
		contentPane.add(scrollPane);
		table.setSelectionBackground(new Color(212, 226, 250));
		table.setBackground(Color.WHITE);
		

		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Nome", "Categoria", "Turno", "Endereco", "email"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(15);
		table.getColumnModel().getColumn(1).setPreferredWidth(90);
		table.getColumnModel().getColumn(2).setPreferredWidth(70);
		table.getColumnModel().getColumn(4).setPreferredWidth(400);
		table.getColumnModel().getColumn(5).setPreferredWidth(105);
		scrollPane.setViewportView(table);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(1463, 186, 232, 442);
		contentPane.add(scrollPane_1);
		
		table_1 = new JTable();
		table_1.setSelectionBackground(new Color(212, 226, 250));
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"telefone"
			}
		));
		scrollPane_1.setViewportView(table_1);
		table_1.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent arg0) {
				try {
					setCamposFromTabela_1();
					excluir_2.setEnabled(true);
					alterar_2.setEnabled(true);
					cadastrar_2.setEnabled(false);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (SelectException e) {
					e.printStackTrace();
				}
			}
		});
		
		tfCodigoEndereco = new JTextField();
		tfCodigoEndereco.setVisible(false);
		tfCodigoEndereco.setEditable(false);
		tfCodigoEndereco.setColumns(10);
		tfCodigoEndereco.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		tfCodigoEndereco.setBounds(798, 534, 33, 19);
		contentPane.add(tfCodigoEndereco);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(227, 662, 640, 268);
		contentPane.add(layeredPane);
		
		tfNome = new JTextField();
		tfNome.setBounds(127, 47, 282, 19);
		layeredPane.add(tfNome);
		tfNome.setColumns(10);
		
		tfCodigo = new JTextField();
		tfCodigo.setBounds(127, 16, 189, 19);
		layeredPane.add(tfCodigo);
		tfCodigo.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		tfCodigo.setEditable(false);
		tfCodigo.setColumns(10);
		comboBox_1.setBounds(126, 78, 283, 21);
		layeredPane.add(comboBox_1);
		
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"Selecione", "Vespertino", "Matutino", "Noturno", "Integral"}));
		comboBox_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		comboBox_1.setBackground(Color.WHITE);
		comboBox.setBounds(126, 111, 283, 21);
		layeredPane.add(comboBox);
		comboBox.setBackground(Color.WHITE);
		comboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		

		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Selecione", "Aluno Graduação", "Aluno Pós-Graduação", "Professor", "Prefessor Pós-Graduação"}));
		
		tfemail = new JTextField();
		tfemail.setBounds(127, 144, 282, 19);
		layeredPane.add(tfemail);
		tfemail.setColumns(10);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setForeground(new Color(85, 97, 120));
		lblEmail.setFont(new Font("Lato Black", Font.BOLD, 15));
		lblEmail.setBounds(69, 144, 144, 20);
		layeredPane.add(lblEmail);
		
		JLabel lblCargo = new JLabel("Cargo:");
		lblCargo.setForeground(new Color(85, 97, 120));
		lblCargo.setFont(new Font("Lato Black", Font.BOLD, 15));
		lblCargo.setBounds(69, 111, 144, 20);
		layeredPane.add(lblCargo);
		
		JLabel lblTurno = new JLabel("Turno:");
		lblTurno.setForeground(new Color(85, 97, 120));
		lblTurno.setFont(new Font("Lato Black", Font.BOLD, 15));
		lblTurno.setBounds(69, 81, 144, 20);
		layeredPane.add(lblTurno);
		
		JLabel lblNome_2 = new JLabel("Nome:");
		lblNome_2.setForeground(new Color(85, 97, 120));
		lblNome_2.setFont(new Font("Lato Black", Font.BOLD, 15));
		lblNome_2.setBounds(69, 47, 144, 20);
		layeredPane.add(lblNome_2);
		
		JLabel lblID_2 = new JLabel("ID:");
		lblID_2.setForeground(new Color(85, 97, 120));
		lblID_2.setFont(new Font("Lato Black", Font.BOLD, 15));
		lblID_2.setBounds(96, 15, 144, 20);
		layeredPane.add(lblID_2);
		
		JLayeredPane layeredPane_1 = new JLayeredPane();
		layeredPane_1.setBounds(1432, 640, 299, 150);
		contentPane.add(layeredPane_1);
		
		tfTelefone = new JTextField();
		tfTelefone.setBounds(100, 22, 183, 19);
		layeredPane_1.add(tfTelefone);
		tfTelefone.setColumns(10);
		
		alterar_2 = new JButton("Alterar");
		alterar_2.setEnabled(false);
		alterar_2.setBounds(12, 53, 118, 21);
		layeredPane_1.add(alterar_2);
		alterar_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					if(table_1.getSelectedRow()!=-1){
						try {
							ObjectId objId = new ObjectId(tfCodigo.getText());
							sistema.alterarTelefone(objId, tfTelefone.getText(), String.valueOf(table_1.getValueAt(table_1.getSelectedRow(), 0)));		
							tfTelefone.setText("");
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage());
						}
						ObjectId objId = new ObjectId(tfCodigo.getText());
						atualizarTabela_1(objId);
						tfTelefone.setText("");
						
					}else JOptionPane.showMessageDialog(null, "Nenhuma linha selecionada");
				}
				
		});
		alterar_2.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		alterar_2.setBackground(Color.WHITE);
		
		cadastrar_2 = new JButton("Cadastrar");
		cadastrar_2.setEnabled(false);
		cadastrar_2.setBounds(12, 81, 118, 21);
		layeredPane_1.add(cadastrar_2);
		cadastrar_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow()!=-1){
					try {
						ObjectId objId = new ObjectId(tfCodigo.getText());
						sistema.adicionarTelefone(objId, tfTelefone.getText());
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}
					ObjectId objId = new ObjectId(tfCodigo.getText());
					atualizarTabela_1(objId);
					tfTelefone.setText("");
				}else JOptionPane.showMessageDialog(null, "Nenhuma usuário selecionado");
			
			}
		});
		cadastrar_2.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		cadastrar_2.setBackground(Color.WHITE);
		
		excluir_2 = new JButton("Excluir");
		excluir_2.setEnabled(false);
		excluir_2.setBounds(12, 106, 118, 21);
		layeredPane_1.add(excluir_2);
		excluir_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table_1.getSelectedRow()!=-1){
					try {
						ObjectId objId = new ObjectId(tfCodigo.getText());
						sistema.excluirTelefone(objId, String.valueOf(table_1.getValueAt(table_1.getSelectedRow(), 0)));
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null,  e1.getMessage());
					}
					ObjectId objId = new ObjectId(tfCodigo.getText());
					atualizarTabela_1(objId);
					tfTelefone.setText("");
				
				}else JOptionPane.showMessageDialog(null, "Nenhuma linha selecionada");
				
				
			}
		});
		excluir_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		excluir_2.setBackground(Color.WHITE);
		
		JLabel lblTelefone = new JLabel("Telefone:");
		lblTelefone.setForeground(new Color(85, 97, 120));
		lblTelefone.setFont(new Font("Lato Black", Font.BOLD, 15));
		lblTelefone.setBounds(12, 22, 144, 20);
		layeredPane_1.add(lblTelefone);
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent arg0) {
				try {
					setCamposFromTabela();
					alterar_1.setEnabled(true);
					cadastrar_1.setEnabled(false);
					cadastrar_2.setEnabled(true);
					excluir_2.setEnabled(false);
					alterar_2.setEnabled(false);
					tfTelefone.setText("");
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SelectException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
		
		JLabel lblusuarioscadastrados = new JLabel("USUÁRIOS CADASTRADOS");
		lblusuarioscadastrados.setForeground(new Color(85, 97, 120));
		lblusuarioscadastrados.setFont(new Font("Lato Black", Font.BOLD, 16));
		lblusuarioscadastrados.setBounds(702, 154, 337, 20);
		contentPane.add(lblusuarioscadastrados);
		
		JLabel lbltelefones = new JLabel("TELEFONES");
		lbltelefones.setForeground(new Color(85, 97, 120));
		lbltelefones.setFont(new Font("Lato Black", Font.BOLD, 16));
		lbltelefones.setBounds(1520, 154, 337, 20);
		contentPane.add(lbltelefones);
		
		
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
		
		JLabel lblBUSCAUSUARIOS = new JLabel("BUSCA DE USUÁRIOS");
		lblBUSCAUSUARIOS.setForeground(new Color(85, 97, 120));
		lblBUSCAUSUARIOS.setFont(new Font("Lato Black", Font.BOLD, 16));
		lblBUSCAUSUARIOS.setBounds(722, 18, 337, 20);
		contentPane.add(lblBUSCAUSUARIOS);
		
		JLabel lblid = new JLabel("ID:");
		lblid.setForeground(new Color(85, 97, 120));
		lblid.setFont(new Font("Lato Black", Font.BOLD, 15));
		lblid.setBounds(521, 61, 52, 20);
		contentPane.add(lblid);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setForeground(new Color(85, 97, 120));
		lblNome.setFont(new Font("Lato Black", Font.BOLD, 15));
		lblNome.setBounds(779, 61, 52, 20);
		contentPane.add(lblNome);
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnNewButton.setBounds(1644, 10, 133, 31);
		contentPane.add(btnNewButton);
		
		JLayeredPane layeredPane_2 = new JLayeredPane();
		layeredPane_2.setBounds(656, 640, 619, 290);
		contentPane.add(layeredPane_2);
		
		tfComplemento = new JTextField();
		tfComplemento.setBounds(139, 198, 282, 19);
		layeredPane_2.add(tfComplemento);
		tfComplemento.setColumns(10);
		
		JLabel lblComplemento_1 = new JLabel("Complemento:");
		lblComplemento_1.setForeground(new Color(85, 97, 120));
		lblComplemento_1.setFont(new Font("Lato Black", Font.BOLD, 15));
		lblComplemento_1.setBounds(0, 198, 144, 20);
		layeredPane_2.add(lblComplemento_1);
		
		tfNumero = new JTextField();
		tfNumero.setBounds(139, 167, 282, 19);
		layeredPane_2.add(tfNumero);
		tfNumero.setColumns(10);
		
		JLabel lblNumero_1 = new JLabel("Número:");
		lblNumero_1.setForeground(new Color(85, 97, 120));
		lblNumero_1.setFont(new Font("Lato Black", Font.BOLD, 15));
		lblNumero_1.setBounds(48, 167, 144, 20);
		layeredPane_2.add(lblNumero_1);
		
		tfRua = new JTextField();
		tfRua.setBounds(139, 136, 282, 19);
		layeredPane_2.add(tfRua);
		tfRua.setColumns(10);
		
		JLabel lblBairro_1 = new JLabel("Bairro:");
		lblBairro_1.setForeground(new Color(85, 97, 120));
		lblBairro_1.setFont(new Font("Lato Black", Font.BOLD, 15));
		lblBairro_1.setBounds(67, 105, 144, 20);
		layeredPane_2.add(lblBairro_1);
		
		tfBairro = new JTextField();
		tfBairro.setBounds(139, 105, 282, 19);
		layeredPane_2.add(tfBairro);
		tfBairro.setColumns(10);
		
		tfCidade = new JTextField();
		tfCidade.setBounds(139, 74, 282, 19);
		layeredPane_2.add(tfCidade);
		tfCidade.setColumns(10);
		comboBox_2.setBounds(139, 41, 283, 21);
		layeredPane_2.add(comboBox_2);
		
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"Selecione", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"}));
		comboBox_2.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		comboBox_2.setBackground(Color.WHITE);
		
		JLabel lblRua = new JLabel("Rua:");
		lblRua.setForeground(new Color(85, 97, 120));
		lblRua.setFont(new Font("Lato Black", Font.BOLD, 15));
		lblRua.setBounds(77, 135, 144, 20);
		layeredPane_2.add(lblRua);
		
		JLabel lblCidade_1 = new JLabel("Cidade:");
		lblCidade_1.setForeground(new Color(85, 97, 120));
		lblCidade_1.setFont(new Font("Lato Black", Font.BOLD, 15));
		lblCidade_1.setBounds(67, 74, 144, 20);
		layeredPane_2.add(lblCidade_1);
		
		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setForeground(new Color(85, 97, 120));
		lblEstado.setFont(new Font("Lato Black", Font.BOLD, 15));
		lblEstado.setBounds(67, 42, 144, 20);
		layeredPane_2.add(lblEstado);
		 
		 JButton cadastrar_1_1_1 = new JButton("Limpar");
		 cadastrar_1_1_1.setBounds(463, 73, 118, 21);
		 layeredPane_2.add(cadastrar_1_1_1);
		 cadastrar_1_1_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		 cadastrar_1_1_1.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		limpar();
		 	}
		 });
		 cadastrar_1_1_1.setBackground(Color.WHITE);
		 
		 alterar_1 = new JButton("Alterar");
		 alterar_1.setBounds(463, 103, 118, 21);
		 layeredPane_2.add(alterar_1);
		 alterar_1.setEnabled(false);
		 alterar_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		 alterar_1.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		if (table.getSelectedRow()!=-1){
		 			int categoria = comboBox.getSelectedIndex();
		 			if(categoria!=0) {
		 				String turno = String.valueOf(comboBox_1.getSelectedItem());
		 				if(!(turno.equals("Selecione"))) {
		 					String estado = String.valueOf(comboBox_2.getSelectedItem());
		 					if(!(estado.equals("Selecione"))) {
		 						if(!(tfemail.getText().equals(""))) {
		 							try {
		 								usuario.setId(tfCodigo.getText());	
		 								usuario.setNome(tfNome.getText());
		 								usuario.setTurno(turno);
		 								usuario.setEmail(tfemail.getText());
		 								Categoria c = new Categoria();
		 								c.setId(categoria);
		 								usuario.setCategoria(c);
		 								
		 								Endereco endereco = new Endereco();
		 								endereco.setEstado(estado);
		 								endereco.setCidade(tfCidade.getText());
		 								endereco.setBairro(tfBairro.getText());
		 								endereco.setRua(tfRua.getText());
		 								endereco.setComplemento(tfComplemento.getText());
		 								endereco.setNumero(Integer.parseInt(tfNumero.getText()));
		 								usuario.setEndereco(endereco);
		 								sistema.alterarUsuario(usuario);						
		 							} catch (Exception e1) {
		 								JOptionPane.showMessageDialog(null, e1.getMessage());
		 							}
		 							atualizarTabela();
		 							limpar();
		 							tfTelefone.setText("");
		 						}else JOptionPane.showMessageDialog(null, "É necessário atribuir um email ao endereço do usuário!");
		 					}else JOptionPane.showMessageDialog(null, "É necessário atribuir um estado ao endereço do usuário!");
		 				}else JOptionPane.showMessageDialog(null, "É necessário atribuir um turno ao usuário!");
		 			}else JOptionPane.showMessageDialog(null, "É necessário atribuir uma categoria ao usuário!");
		 		}else JOptionPane.showMessageDialog(null, "Nenhuma linha selecionada");
		 	}
		 });
		 alterar_1.setBackground(Color.WHITE);
		 
		 cadastrar_1 = new JButton("Cadastrar");
		 cadastrar_1.setBounds(463, 134, 118, 21);
		 layeredPane_2.add(cadastrar_1);
		 cadastrar_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		 cadastrar_1.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 			if(tfNome.getText().equals("") || tfCidade.getText().equals("") || tfBairro.getText().equals("") || tfRua.getText().equals("") || tfNumero.getText().equals("") || comboBox.getSelectedItem().equals("Selecione") || comboBox_1.getSelectedItem().equals("Selecione") || comboBox_2.getSelectedItem().equals("Selecione")) {      
		 				JOptionPane.showMessageDialog(null, "Preencha todos os campos");
		 			}else {

		 				String nome = tfNome.getText();
		 				String estado = String.valueOf(comboBox_2.getSelectedItem());	
		 				String cidade = tfCidade.getText();
		 				String bairro = tfBairro.getText();
		 				String rua = tfRua.getText();
		 				String complemento = tfComplemento.getText();
		 				int numero = Integer.parseInt(tfNumero.getText());
		 				int categoria = comboBox.getSelectedIndex();
		 				String turno = String.valueOf(comboBox_1.getSelectedItem());				

		 				usuario.setNome(nome);
		 				usuario.setTurno(turno);
		 				usuario.setEmail(tfemail.getText());
		 				Categoria c = new Categoria();
		 				c.setId(categoria);
		 				usuario.setCategoria(c);
		 				Endereco endereco = new Endereco();
		 				endereco.setEstado(estado);
		 				endereco.setCidade(cidade);
		 				endereco.setBairro(bairro);
		 				endereco.setRua(rua);
		 				endereco.setComplemento(complemento);
		 				endereco.setNumero(numero);
		 				usuario.setEndereco(endereco);
		 				try {
		 					sistema.adicionarUsuario(usuario);
		 				} catch (Exception e1) {
		 					JOptionPane.showMessageDialog(null, e1.getMessage());
		 				}
		 				
		 				atualizarTabela();
		 				limpar();
		 				tfTelefone.setText("");
		 			}	
		 	}
		 	
		 });
		 cadastrar_1.setBackground(Color.WHITE);
		
		lblNewLabel.setBounds(12, 0, 1898, 1047);
		contentPane.add(lblNewLabel);
		
		table.getTableHeader().setOpaque(false);
		table.getTableHeader().setBackground(new Color(225, 235, 252));
		table.setFillsViewportHeight(true);
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
		tfNome.setText(null);
		tfCodigo.setText(null);
		comboBox.setSelectedIndex(0);
		comboBox_1.setSelectedIndex(0);
		comboBox_2.setSelectedIndex(0);
		tfCidade.setText("");
		tfBairro.setText("");
		tfRua.setText("");
		tfComplemento.setText("");
		tfNumero.setText("");
		tfCodigoEndereco.setText("");
		alterar_1.setEnabled(false);
		excluir_2.setEnabled(false);
		alterar_2.setEnabled(false);
		cadastrar_2.setEnabled(false);
		cadastrar_1.setEnabled(true);
		tfemail.setText("");
		table.clearSelection();
	}
	

	public static void atualizarTabela() {
		try {
			usuarios = sistema.listarUsuarios();
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			model.setNumRows(0);
		for (int i=0;i!=usuarios.size();i++)model.addRow((Object[]) usuarios.get(i));
		
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	public static void atualizarTabela_1(ObjectId id_usuario) {
		try {
			telefones = sistema.listarTelefones(id_usuario);
			DefaultTableModel model = (DefaultTableModel) table_1.getModel();
			model.setNumRows(0);
		for (int i=0;i!=telefones.size();i++)model.addRow((Object[]) telefones.get(i));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	public void setCamposFromTabela() throws Exception {
		tfCodigo.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 0)));
		ObjectId objId = new ObjectId(tfCodigo.getText());
		atualizarTabela_1(objId);
		tfNome.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 1)));
		String categoria = String.valueOf(table.getValueAt(table.getSelectedRow(), 2));
		comboBox.setSelectedItem(categoria);
		String turno = String.valueOf(table.getValueAt(table.getSelectedRow(), 3));
		comboBox_1.setSelectedItem(turno);
		Endereco endereco = sistema.buscaEndereco(objId);
		tfRua.setText(endereco.getRua());
		tfComplemento.setText(endereco.getComplemento());
		tfBairro.setText(endereco.getBairro());
		tfCidade.setText(endereco.getCidade());
		tfNumero.setText(String.valueOf(endereco.getNumero()));
		String estado = endereco.getEstado();
		comboBox_2.setSelectedItem(estado);
		tfemail.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 5)));		
	}
	
	
	public void setCamposFromTabela_1() throws NumberFormatException, SelectException {
		tfTelefone.setText(String.valueOf(table_1.getValueAt(table_1.getSelectedRow(), 0)));
	}
}