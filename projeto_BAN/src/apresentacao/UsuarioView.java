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
import javax.swing.JLayeredPane;
import javax.swing.UIManager;

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
	private JButton excluir_1;
	private JButton cadastrar_1;
	private JButton alterar_1;
	private JButton alterar_2;
	private JButton excluir_2;
	private JButton cadastrar_2;
	
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

	public static UsuarioView getInstance() {
        if(usuarioView==null) usuarioView=new UsuarioView();
        atualizarTabela();
        return usuarioView;
    } 
	public UsuarioView() {
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


		setTitle("Gerenciar Usuários");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(200, 2000,  1800, 1000);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		contentPane = new JPanel();
		contentPane.setEnabled(false);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblBusca = new JLabel("BUSCA DE USUÁRIOS");
		lblBusca.setFont(new Font("Dialog", Font.BOLD, 15));
		lblBusca.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBusca.setBounds(224, 0, 224, 48);
		contentPane.add(lblBusca);
		
		JLabel lblArtistasCadastrados = new JLabel("USUÁRIOS CADASTRADOS");
		lblArtistasCadastrados.setFont(new Font("Segoe UI Symbol", Font.BOLD, 15));
		lblArtistasCadastrados.setBounds(584, 154, 232, 20);
		contentPane.add(lblArtistasCadastrados);
		
		JButton sair = new JButton("Sair");
		sair.setBorder(new LineBorder(new Color(0, 0, 0)));
		sair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sair();
			}
		});
		sair.setBackground(UIManager.getColor("Button.darkShadow"));
		sair.setBounds(886, 927, 173, 20);
		contentPane.add(sair);
		
		
		JLabel lblCdigo = new JLabel("ID:");
		lblCdigo.setFont(new Font("Segoe UI Symbol", Font.BOLD, 15));
		lblCdigo.setBounds(166, 90, 70, 20);
		contentPane.add(lblCdigo);
		lblCdigo.setHorizontalAlignment(SwingConstants.RIGHT);
		
		textPCodigo = new JTextField();
		textPCodigo.setBounds(254, 91, 198, 20);
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
		textPNome.setBounds(254, 59, 198, 20);
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
		lblNome.setBounds(166, 58, 70, 20);
		contentPane.add(lblNome);
		lblNome.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JSeparator separator_2_1_1 = new JSeparator();
		separator_2_1_1.setForeground(Color.LIGHT_GRAY);
		separator_2_1_1.setBounds(12, 122, 1788, 20);
		contentPane.add(separator_2_1_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(169, 186, 1049, 308);
		contentPane.add(scrollPane);
		table.setSelectionBackground(SystemColor.activeCaption);
		table.setBackground(SystemColor.window);
		

		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Nome", "Categoria", "Turno", "Endereco", "ID endereco"
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
		table.getColumnModel().getColumn(5).setPreferredWidth(15);
		scrollPane.setViewportView(table);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(1369, 186, 232, 308);
		contentPane.add(scrollPane_1);
		
		table_1 = new JTable();
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
		
		JLabel lblTelefones = new JLabel("TELEFONES");
		lblTelefones.setFont(new Font("Dialog", Font.BOLD, 15));
		lblTelefones.setBounds(1429, 154, 232, 20);
		contentPane.add(lblTelefones);
		
		tfCodigoEndereco = new JTextField();
		tfCodigoEndereco.setVisible(false);
		tfCodigoEndereco.setEditable(false);
		tfCodigoEndereco.setColumns(10);
		tfCodigoEndereco.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		tfCodigoEndereco.setBounds(798, 534, 33, 19);
		contentPane.add(tfCodigoEndereco);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(176, 534, 640, 346);
		contentPane.add(layeredPane);
		
		tfNome = new JTextField();
		tfNome.setBounds(127, 47, 282, 19);
		layeredPane.add(tfNome);
		tfNome.setColumns(10);
		
		tfCodigo = new JTextField();
		tfCodigo.setBounds(127, 16, 33, 19);
		layeredPane.add(tfCodigo);
		tfCodigo.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		tfCodigo.setEditable(false);
		tfCodigo.setColumns(10);
		
		JLabel lblCdigo_1 = new JLabel("ID:");
		lblCdigo_1.setBounds(69, 15, 40, 20);
		layeredPane.add(lblCdigo_1);
		lblCdigo_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCdigo_1.setFont(new Font("Segoe UI Symbol", Font.BOLD, 15));
		
		JLabel lblNome_1 = new JLabel("Nome:");
		lblNome_1.setBounds(39, 46, 70, 20);
		layeredPane.add(lblNome_1);
		lblNome_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNome_1.setFont(new Font("Segoe UI Symbol", Font.BOLD, 15));
		comboBox_1.setBounds(126, 78, 283, 21);
		layeredPane.add(comboBox_1);
		
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"Selecione", "Vespertino", "Matutino", "Noturno", "Integral"}));
		comboBox_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		comboBox_1.setBackground(SystemColor.window);
		
		JLabel btnTurno = new JLabel("Turno:");
		btnTurno.setBounds(38, 78, 70, 20);
		layeredPane.add(btnTurno);
		btnTurno.setHorizontalAlignment(SwingConstants.RIGHT);
		btnTurno.setFont(new Font("Segoe UI Symbol", Font.BOLD, 15));
		comboBox.setBounds(126, 111, 283, 21);
		layeredPane.add(comboBox);
		comboBox.setBackground(SystemColor.window);
		comboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		

		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Selecione", "Aluno Graduação", "Aluno Pós-Graduação", "Professor", "Prefessor Pós-Graduação"}));
		
		JLabel lblCargo = new JLabel("Cargo:");
		lblCargo.setBounds(38, 110, 70, 20);
		layeredPane.add(lblCargo);
		lblCargo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCargo.setFont(new Font("Segoe UI Symbol", Font.BOLD, 15));
		
		JSeparator separator_2_1_1_1 = new JSeparator();
		separator_2_1_1_1.setBounds(38, 144, 371, 20);
		layeredPane.add(separator_2_1_1_1);
		separator_2_1_1_1.setForeground(Color.LIGHT_GRAY);
		comboBox_2.setBounds(126, 154, 283, 21);
		layeredPane.add(comboBox_2);
		
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"Selecione", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"}));
		comboBox_2.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		comboBox_2.setBackground(SystemColor.window);
		
		tfCidade = new JTextField();
		tfCidade.setBounds(127, 187, 282, 19);
		layeredPane.add(tfCidade);
		tfCidade.setColumns(10);
		
		tfBairro = new JTextField();
		tfBairro.setBounds(127, 218, 282, 19);
		layeredPane.add(tfBairro);
		tfBairro.setColumns(10);
		
		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setBounds(38, 154, 70, 20);
		layeredPane.add(lblEstado);
		lblEstado.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEstado.setFont(new Font("Dialog", Font.BOLD, 15));
		
		JLabel lblCidade = new JLabel("Cidade:");
		lblCidade.setBounds(49, 186, 70, 20);
		layeredPane.add(lblCidade);
		lblCidade.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCidade.setFont(new Font("Dialog", Font.BOLD, 15));
		
		JLabel lblBairro = new JLabel("Bairro:");
		lblBairro.setBounds(49, 217, 70, 20);
		layeredPane.add(lblBairro);
		lblBairro.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBairro.setFont(new Font("Dialog", Font.BOLD, 15));
		
		tfRua = new JTextField();
		tfRua.setBounds(127, 247, 282, 19);
		layeredPane.add(tfRua);
		tfRua.setColumns(10);
		
		tfNumero = new JTextField();
		tfNumero.setBounds(127, 276, 282, 19);
		layeredPane.add(tfNumero);
		tfNumero.setColumns(10);
		
		JLabel lblRua = new JLabel("Rua:");
		lblRua.setBounds(49, 247, 70, 20);
		layeredPane.add(lblRua);
		lblRua.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRua.setFont(new Font("Dialog", Font.BOLD, 15));
		
		JLabel lblNumero = new JLabel("Numero:");
		lblNumero.setBounds(43, 275, 76, 20);
		layeredPane.add(lblNumero);
		lblNumero.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNumero.setFont(new Font("Dialog", Font.BOLD, 15));
		
		JLabel lblComplemento = new JLabel("Complemento:");
		lblComplemento.setBounds(-30, 307, 149, 20);
		layeredPane.add(lblComplemento);
		lblComplemento.setHorizontalAlignment(SwingConstants.RIGHT);
		lblComplemento.setFont(new Font("Dialog", Font.BOLD, 15));
		
		tfComplemento = new JTextField();
		tfComplemento.setBounds(127, 307, 282, 19);
		layeredPane.add(tfComplemento);
		tfComplemento.setColumns(10);
		
		cadastrar_1 = new JButton("Cadastrar");
		cadastrar_1.setBounds(451, 176, 118, 21);
		layeredPane.add(cadastrar_1);
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
						} catch (InsertException | SelectException | JaCadastradoException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage());
						}
						
						atualizarTabela();
						limpar();

					}	
			}
			
		});
		cadastrar_1.setBackground(UIManager.getColor("Button.darkShadow"));
		
		JButton cadastrar_1_1_1 = new JButton("Limpar");
		cadastrar_1_1_1.setBounds(451, 111, 118, 21);
		layeredPane.add(cadastrar_1_1_1);
		cadastrar_1_1_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		cadastrar_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		cadastrar_1_1_1.setBackground(UIManager.getColor("Button.darkShadow"));
		
		alterar_1 = new JButton("Alterar");
		alterar_1.setEnabled(false);
		alterar_1.setBounds(451, 143, 118, 21);
		layeredPane.add(alterar_1);
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
								try {
									usuario.setId(Integer.parseInt(tfCodigo.getText()));	
									usuario.setNome(tfNome.getText());
									usuario.setTurno(turno);
									Categoria c = new Categoria();
									c.setId(categoria);
									
									usuario.setCategoria(c);
									
									Endereco endereco = new Endereco();
									endereco.setEstado(estado);
									endereco.setCidade(tfCidade.getText());
									endereco.setBairro(tfBairro.getText());
									endereco.setRua(tfRua.getText());
									endereco.setComplemento(tfComplemento.getText());
									endereco.setId(Integer.parseInt(String.valueOf(table.getValueAt(table.getSelectedRow(), 5))));
									endereco.setNumero(Integer.parseInt(tfNumero.getText()));
									usuario.setEndereco(endereco);
									sistema.alterarUsuario(usuario);						
								} catch (Exception e1) {
									JOptionPane.showMessageDialog(null, e1.getMessage());
								}
								atualizarTabela();
							}else JOptionPane.showMessageDialog(null, "É necessário atribuir um estado ao endereço do usuário!");
						}else JOptionPane.showMessageDialog(null, "É necessário atribuir um turno ao usuário!");
					}else JOptionPane.showMessageDialog(null, "É necessário atribuir uma categoria ao usuário!");
				}else JOptionPane.showMessageDialog(null, "Nenhuma linha selecionada");
			}
		});
		alterar_1.setBackground(UIManager.getColor("Button.darkShadow"));
		
	
		
		 excluir_1 = new JButton("Excluir");
		 excluir_1.setEnabled(false);
		excluir_1.setBounds(451, 209, 118, 21);
		layeredPane.add(excluir_1);
		excluir_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		excluir_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow()!=-1){
					try {
						sistema.excluirUsuario(Integer.parseInt(tfCodigo.getText()), Integer.parseInt(tfCodigoEndereco.getText()));
					} catch (NumberFormatException | DeleteException | SelectException | NaoCadastradoException e1) {
						JOptionPane.showMessageDialog(null,  e1.getMessage());
					}
					atualizarTabela();
					limpar();
				
				}else JOptionPane.showMessageDialog(null, "Nenhuma linha selecionada");
				
			}
		});
		excluir_1.setBackground(UIManager.getColor("Button.darkShadow"));
		
		JLayeredPane layeredPane_1 = new JLayeredPane();
		layeredPane_1.setBounds(1340, 534, 299, 150);
		contentPane.add(layeredPane_1);
		
		tfTelefone = new JTextField();
		tfTelefone.setBounds(100, 22, 183, 19);
		layeredPane_1.add(tfTelefone);
		tfTelefone.setColumns(10);
		
		JLabel lblCdigo_1_1 = new JLabel("Telefone:");
		lblCdigo_1_1.setBounds(-17, 21, 99, 20);
		layeredPane_1.add(lblCdigo_1_1);
		lblCdigo_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCdigo_1_1.setFont(new Font("Dialog", Font.BOLD, 15));
		
		alterar_2 = new JButton("Alterar");
		alterar_2.setEnabled(false);
		alterar_2.setBounds(12, 53, 118, 21);
		layeredPane_1.add(alterar_2);
		alterar_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					if(table_1.getSelectedRow()!=-1){
						try {
							sistema.alterarTelefone(Integer.parseInt(tfCodigo.getText()), tfTelefone.getText(), String.valueOf(table_1.getValueAt(table_1.getSelectedRow(), 0)));		
							tfTelefone.setText("");
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage());
						}
						atualizarTabela_1(Integer.parseInt(tfCodigo.getText()));
						tfTelefone.setText("");
						
					}else JOptionPane.showMessageDialog(null, "Nenhuma linha selecionada");
				}
				
		});
		alterar_2.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		alterar_2.setBackground(UIManager.getColor("Button.darkShadow"));
		
		cadastrar_2 = new JButton("Cadastrar");
		cadastrar_2.setEnabled(false);
		cadastrar_2.setBounds(12, 81, 118, 21);
		layeredPane_1.add(cadastrar_2);
		cadastrar_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow()!=-1){
					try {
						sistema.adicionarTelefone(Integer.parseInt(tfCodigo.getText()), tfTelefone.getText());
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}
					atualizarTabela_1(Integer.parseInt(tfCodigo.getText()));
					tfTelefone.setText("");
				}else JOptionPane.showMessageDialog(null, "Nenhuma usuário selecionado");
			
			}
		});
		cadastrar_2.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		cadastrar_2.setBackground(UIManager.getColor("Button.darkShadow"));
		
		excluir_2 = new JButton("Excluir");
		excluir_2.setEnabled(false);
		excluir_2.setBounds(12, 106, 118, 21);
		layeredPane_1.add(excluir_2);
		excluir_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table_1.getSelectedRow()!=-1){
					try {
						sistema.excluirTelefone(Integer.parseInt(tfCodigo.getText()), String.valueOf(table_1.getValueAt(table_1.getSelectedRow(), 0)));
					} catch (NumberFormatException | DeleteException | SelectException | NaoCadastradoException e1) {
						JOptionPane.showMessageDialog(null,  e1.getMessage());
					}
					atualizarTabela_1(Integer.parseInt(tfCodigo.getText()));
					tfTelefone.setText("");
				
				}else JOptionPane.showMessageDialog(null, "Nenhuma linha selecionada");
				
				
			}
		});
		excluir_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		excluir_2.setBackground(UIManager.getColor("Button.darkShadow"));
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent arg0) {
				try {
					setCamposFromTabela();
					excluir_1.setEnabled(true);
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
				}
			}
		});

	}

	public void sair() {
		dispose();
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
		excluir_1.setEnabled(false);
		alterar_1.setEnabled(false);
		excluir_2.setEnabled(false);
		alterar_2.setEnabled(false);
		cadastrar_2.setEnabled(false);
		cadastrar_1.setEnabled(true);

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
	
	public static void atualizarTabela_1(int id_usuario) {
		try {
			telefones = sistema.listarTelefones(id_usuario);
			DefaultTableModel model = (DefaultTableModel) table_1.getModel();
			model.setNumRows(0);
		for (int i=0;i!=telefones.size();i++)model.addRow((Object[]) telefones.get(i));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	public void setCamposFromTabela() throws NumberFormatException, SelectException {
		tfCodigo.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 0)));
		atualizarTabela_1(Integer.parseInt((tfCodigo.getText())));
		tfNome.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 1)));
		String categoria = String.valueOf(table.getValueAt(table.getSelectedRow(), 2));
		comboBox.setSelectedItem(categoria);
		String turno = String.valueOf(table.getValueAt(table.getSelectedRow(), 3));
		comboBox_1.setSelectedItem(turno);
		tfCodigoEndereco.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 5)));
		Endereco e = sistema.buscaEndereco(Integer.parseInt(String.valueOf(table.getValueAt(table.getSelectedRow(), 5))));
		String estado = e.getEstado();
		comboBox_2.setSelectedItem(estado);
		tfCidade.setText(e.getCidade());
		tfBairro.setText(e.getBairro());
		tfRua.setText(e.getRua());
		tfComplemento.setText(e.getComplemento());
		tfNumero.setText(String.valueOf(e.getNumero()));
	}
	
	
	public void setCamposFromTabela_1() throws NumberFormatException, SelectException {
		tfTelefone.setText(String.valueOf(table_1.getValueAt(table_1.getSelectedRow(), 0)));
	}
}