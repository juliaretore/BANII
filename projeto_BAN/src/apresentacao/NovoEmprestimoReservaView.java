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


		setTitle("Cadastrar Empréstimo e Reserva");
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
		lblBusca.setBounds(420, 43, 233, 48);
		contentPane.add(lblBusca);
		
		JButton sair = new JButton("Sair");
		sair.setBorder(new LineBorder(new Color(0, 0, 0)));
		sair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sair();
			}
		});
		sair.setBackground(UIManager.getColor("Button.darkShadow"));
		sair.setBounds(882, 925, 173, 20);
		contentPane.add(sair);
		
		
		JLabel lblCdigo = new JLabel("ISBN:");
		lblCdigo.setFont(new Font("Segoe UI Symbol", Font.BOLD, 15));
		lblCdigo.setBounds(420, 132, 70, 20);
		contentPane.add(lblCdigo);
		lblCdigo.setHorizontalAlignment(SwingConstants.RIGHT);
		
		textPCodigo = new JTextField();
		textPCodigo.setBounds(508, 133, 198, 20);
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
		textPNome.setBounds(508, 103, 198, 20);
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
		
		JLabel lblNome = new JLabel("Titulo:");
		lblNome.setFont(new Font("Segoe UI Symbol", Font.BOLD, 15));
		lblNome.setBounds(420, 103, 70, 20);
		contentPane.add(lblNome);
		lblNome.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JSeparator separator_2_1_1 = new JSeparator();
		separator_2_1_1.setForeground(Color.LIGHT_GRAY);
		separator_2_1_1.setBounds(75, 163, 1788, 20);
		contentPane.add(separator_2_1_1);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(1015, 226, 530, 442);
		contentPane.add(scrollPane_1);
		
		table_1 = new JTable();
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
		
		JLabel lblTelefones = new JLabel("LIVROS");
		lblTelefones.setFont(new Font("Dialog", Font.BOLD, 15));
		lblTelefones.setBounds(549, 180, 272, 20);
		contentPane.add(lblTelefones);
		
		JLayeredPane layeredPane_1 = new JLayeredPane();
		layeredPane_1.setBounds(671, 703, 611, 193);
		contentPane.add(layeredPane_1);
		
		JLabel lblNome_1_2 = new JLabel("Livro:");
		lblNome_1_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNome_1_2.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNome_1_2.setBounds(84, 62, 70, 20);
		layeredPane_1.add(lblNome_1_2);
		
		JButton selecionar_usuario = new JButton("Buscar Usuário");
		selecionar_usuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdicionarUsuarioEmprestimoView frame = new AdicionarUsuarioEmprestimoView();
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
			}
		});
		selecionar_usuario.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		selecionar_usuario.setBackground(UIManager.getColor("Button.darkShadow"));
		selecionar_usuario.setBounds(467, 30, 118, 21);
		layeredPane_1.add(selecionar_usuario);
		
		JLabel lblCdigo_1_1 = new JLabel("Usuário:");
		lblCdigo_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCdigo_1_1.setFont(new Font("Dialog", Font.BOLD, 15));
		lblCdigo_1_1.setBounds(84, 30, 70, 20);
		layeredPane_1.add(lblCdigo_1_1);
		
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
		
		JLabel lblNome_1_1_1 = new JLabel("Exemplar:");
		lblNome_1_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNome_1_1_1.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNome_1_1_1.setBounds(51, 94, 103, 20);
		layeredPane_1.add(lblNome_1_1_1);
		
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
		
		JLabel lblSalrio_1 = new JLabel("Data de entrega:");
		lblSalrio_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSalrio_1.setFont(new Font("Dialog", Font.BOLD, 15));
		lblSalrio_1.setBounds(16, 120, 142, 20);
		layeredPane_1.add(lblSalrio_1);
		
		JButton limpar_2 = new JButton("Limpar");
		limpar_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		limpar_2.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		limpar_2.setBackground(UIManager.getColor("Button.darkShadow"));
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
		Cadastrar.setBackground(UIManager.getColor("Button.darkShadow"));
		
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
		Reservar.setBackground(UIManager.getColor("Button.darkShadow"));
		Reservar.setBounds(336, 150, 118, 21);
		layeredPane_1.add(Reservar);
		
		JLabel lblBuscaDeAssistentes = new JLabel("BUSCA DE EXEMPLARES");
		lblBuscaDeAssistentes.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBuscaDeAssistentes.setFont(new Font("Dialog", Font.BOLD, 15));
		lblBuscaDeAssistentes.setBounds(947, 43, 405, 48);
		contentPane.add(lblBuscaDeAssistentes);
		
		textPCodigo_1 = new JTextField();
		textPCodigo_1.setColumns(10);
		textPCodigo_1.setBounds(1186, 103, 198, 20);
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
		lblCdigo_2.setBounds(1102, 102, 70, 20);
		contentPane.add(lblCdigo_2);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane_2.setBounds(344, 226, 503, 431);
		contentPane.add(scrollPane_2);
		
		table_2 = new JTable();
		table_2.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "ISBN", "Titulo", "Editora"
			}
		));
		scrollPane_2.setViewportView(table_2);
		
		JLabel lblBuscaDeLivros = new JLabel("REALIZAR EMPRESTIMO");
		lblBuscaDeLivros.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBuscaDeLivros.setFont(new Font("Dialog", Font.BOLD, 20));
		lblBuscaDeLivros.setBounds(650, 12, 405, 48);
		contentPane.add(lblBuscaDeLivros);
		
		JLabel lblExemplares = new JLabel("EXEMPLARES");
		lblExemplares.setFont(new Font("Dialog", Font.BOLD, 15));
		lblExemplares.setBounds(1200, 180, 272, 20);
		contentPane.add(lblExemplares);

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
		
	}

	public void sair() {
		dispose();
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