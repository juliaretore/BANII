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
import javax.swing.JSplitPane;
import javax.swing.JLayeredPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

public class FuncionarioView extends JFrame {


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
	private static JTable table = new JTable();
	private static FuncionarioView funcionarioView;
	private static JTable table_1;
	private JTextField tfTelefone;
	private JTextField tfLogin;
	private JTextField tfSalario;
	
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
        atualizarTabela();
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
		
		JLabel lblBusca = new JLabel("BUSCA DE BIBLIOTECÁRIOS");
		lblBusca.setFont(new Font("Dialog", Font.BOLD, 15));
		lblBusca.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBusca.setBounds(224, 0, 405, 48);
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
		sair.setBackground(SystemColor.window);
		sair.setBounds(727, 943, 173, 20);
		contentPane.add(sair);
		
		
		JLabel lblCdigo = new JLabel("Código");
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
		
		JLabel lblNome = new JLabel("Nome");
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
		scrollPane.setBounds(169, 186, 611, 431);
		contentPane.add(scrollPane);
		table.setSelectionBackground(SystemColor.activeCaption);
		table.setBackground(SystemColor.window);
		

		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Nome", "Login", "Turno", "Sal\u00E1rio"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		table.getColumnModel().getColumn(1).setPreferredWidth(90);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		scrollPane.setViewportView(table);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(967, 186, 614, 308);
		contentPane.add(scrollPane_1);
		
		table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Nome", "Login", "Turno", "Salario"
			}
		));
		table_1.getColumnModel().getColumn(0).setPreferredWidth(20);
		table_1.getColumnModel().getColumn(1).setPreferredWidth(90);
		table_1.getColumnModel().getColumn(2).setPreferredWidth(100);
		table_1.getColumnModel().getColumn(3).setPreferredWidth(100);
		table_1.getColumnModel().getColumn(4).setPreferredWidth(100);
		scrollPane_1.setViewportView(table_1);
		table_1.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent arg0) {
				try {
					setCamposFromTabela_1();
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
		
		JButton alterar_2 = new JButton("Alterar");
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
		alterar_2.setBackground(SystemColor.window);
		alterar_2.setBounds(1369, 533, 118, 21);
		contentPane.add(alterar_2);
		
		JButton cadastrar_2 = new JButton("Cadastrar");
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
		cadastrar_2.setBackground(SystemColor.window);
		cadastrar_2.setBounds(1502, 533, 118, 21);
		contentPane.add(cadastrar_2);
		
		JButton excluir_2 = new JButton("Excluir");
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
		excluir_2.setBackground(SystemColor.window);
		excluir_2.setBounds(1436, 562, 118, 21);
		contentPane.add(excluir_2);
		
		tfTelefone = new JTextField();
		tfTelefone.setColumns(10);
		tfTelefone.setBounds(1398, 506, 183, 19);
		contentPane.add(tfTelefone);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(169, 629, 611, 276);
		contentPane.add(layeredPane);
		
		JLabel lblNome_1 = new JLabel("Nome");
		lblNome_1.setBounds(22, 63, 70, 20);
		layeredPane.add(lblNome_1);
		lblNome_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNome_1.setFont(new Font("Segoe UI Symbol", Font.BOLD, 15));
		
		JLabel lblCdigo_1 = new JLabel("ID");
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
		
		JLabel lblNome_1_1 = new JLabel("Login");
		lblNome_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNome_1_1.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNome_1_1.setBounds(22, 89, 70, 20);
		layeredPane.add(lblNome_1_1);
		
		tfLogin = new JTextField();
		tfLogin.setColumns(10);
		tfLogin.setBounds(127, 89, 282, 19);
		layeredPane.add(tfLogin);
		
		JLabel btnTurno = new JLabel("Turno");
		btnTurno.setBounds(22, 118, 70, 20);
		layeredPane.add(btnTurno);
		btnTurno.setHorizontalAlignment(SwingConstants.RIGHT);
		btnTurno.setFont(new Font("Segoe UI Symbol", Font.BOLD, 15));
		comboBox_1.setBounds(127, 118, 283, 21);
		layeredPane.add(comboBox_1);
		
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"Selecione", "Vespertino", "Matutino", "Noturno", "Integral"}));
		comboBox_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		comboBox_1.setBackground(SystemColor.window);
		
		JLabel lblCargo = new JLabel("Tipo");
		lblCargo.setBounds(22, 149, 70, 20);
		layeredPane.add(lblCargo);
		lblCargo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCargo.setFont(new Font("Segoe UI Symbol", Font.BOLD, 15));
		comboBox.setBounds(126, 149, 283, 21);
		layeredPane.add(comboBox);
		comboBox.setBackground(SystemColor.window);
		comboBox.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		

		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Selecione", "Aluno Graduação", "Aluno Pós-Graduação", "Professor", "Prefessor Pós-Graduação"}));
		
		tfSalario = new JTextField();
		tfSalario.setColumns(10);
		tfSalario.setBounds(127, 184, 282, 19);
		layeredPane.add(tfSalario);
		
		JLabel lblSalrio = new JLabel("Salário");
		lblSalrio.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSalrio.setFont(new Font("Dialog", Font.BOLD, 15));
		lblSalrio.setBounds(22, 181, 70, 20);
		layeredPane.add(lblSalrio);
		
		JButton cadastrar_1 = new JButton("Cadastrar");
		cadastrar_1.setBounds(456, 129, 118, 21);
		layeredPane.add(cadastrar_1);
		cadastrar_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		cadastrar_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//					if(tfNome.getText().equals("") || tfCidade.getText().equals("") || tfBairro.getText().equals("") || tfRua.getText().equals("") || tfNumero.getText().equals("") || comboBox.getSelectedItem().equals("Selecione") || comboBox_1.getSelectedItem().equals("Selecione") || comboBox_2.getSelectedItem().equals("Selecione")) {      
//						JOptionPane.showMessageDialog(null, "Preencha todos os campos");
//					}else {
//
//						String nome = tfNome.getText();
//						String turno = String.valueOf(comboBox_1.getSelectedItem());				
//
//						try {
//							sistema.adicionarUsuario(usuario);
//						} catch (InsertException | SelectException | JaCadastradoException e1) {
//							JOptionPane.showMessageDialog(null, e1.getMessage());
//						}
//						
//						atualizarTabela();
//						limpar();
//
//					}	
			}
			
		});
		cadastrar_1.setBackground(SystemColor.window);
		
		JButton cadastrar_1_1 = new JButton("Alterar");
		cadastrar_1_1.setBounds(456, 96, 118, 21);
		layeredPane.add(cadastrar_1_1);
		cadastrar_1_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		cadastrar_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				if (table.getSelectedRow()!=-1){
//					int categoria = comboBox.getSelectedIndex();
//					if(categoria!=0) {
//						String turno = String.valueOf(comboBox_1.getSelectedItem());
//						if(!(turno.equals("Selecione"))) {
//								try {
//									sistema.alterarUsuario(usuario);						
//								} catch (Exception e1) {
//									JOptionPane.showMessageDialog(null, e1.getMessage());
//								}
//								atualizarTabela();
//						}else JOptionPane.showMessageDialog(null, "É necessário atribuir um turno ao usuário!");
//					}else JOptionPane.showMessageDialog(null, "É necessário atribuir uma categoria ao usuário!");
//				}else JOptionPane.showMessageDialog(null, "Nenhuma linha selecionada");
			}
		});
		cadastrar_1_1.setBackground(SystemColor.window);
		
	
		
		JButton excluir_1 = new JButton("Excluir");
		excluir_1.setBounds(456, 162, 118, 21);
		layeredPane.add(excluir_1);
		excluir_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		excluir_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow()!=-1){
//					try {
//						sistema.excluirUsuario(Integer.parseInt(tfCodigo.getText()), Integer.parseInt(tfCodigoEndereco.getText()));
//					} catch (NumberFormatException | DeleteException | SelectException | NaoCadastradoException e1) {
//						JOptionPane.showMessageDialog(null,  e1.getMessage());
//					}
					atualizarTabela();
					limpar();
				
				}else JOptionPane.showMessageDialog(null, "Nenhuma linha selecionada");
				
			}
		});
		excluir_1.setBackground(SystemColor.window);
		
		JButton cadastrar_1_1_1 = new JButton("Limpar");
		cadastrar_1_1_1.setBounds(456, 63, 118, 21);
		layeredPane.add(cadastrar_1_1_1);
		cadastrar_1_1_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		cadastrar_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		cadastrar_1_1_1.setBackground(SystemColor.window);
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent arg0) {
				try {
					setCamposFromTabela();
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

	}
	

	public static void atualizarTabela() {
		try {
			usuarios = sistema.listarBibliotecarios();
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
	}
	
	
	public void setCamposFromTabela_1() throws NumberFormatException, SelectException {
		tfTelefone.setText(String.valueOf(table_1.getValueAt(table_1.getSelectedRow(), 0)));
	}
}