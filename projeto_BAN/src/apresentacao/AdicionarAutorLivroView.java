
package apresentacao;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

import dados.Autor;
import dados.Exemplar;
import exceptions.DeleteException;
import exceptions.InsertException;
import exceptions.JaCadastradoException;
import exceptions.NaoCadastradoException;
import exceptions.SelectException;
import exceptions.UpdateException;
import negocio.Sistema;
import persistencia.*;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.border.MatteBorder;
import javax.swing.JLayeredPane;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

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
	private JButton excluir;
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
		} catch (ClassNotFoundException | SQLException | SelectException e) {
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
		
		JLabel lblBusca = new JLabel("BUSCA");
		lblBusca.setFont(new Font("Segoe UI Symbol", Font.BOLD, 15));
		lblBusca.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBusca.setBounds(291, 12, 69, 20);
		contentPane.add(lblBusca);
		
		JLabel lblMusicasParaAdicionae = new JLabel("AUTORES PARA ADICIONAR");
		lblMusicasParaAdicionae.setFont(new Font("Segoe UI Symbol", Font.BOLD, 15));
		lblMusicasParaAdicionae.setBounds(226, 118, 369, 20);
		contentPane.add(lblMusicasParaAdicionae);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(105, 153, 453, 189);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setBackground(SystemColor.window);
		table.setSelectionBackground(SystemColor.activeCaption);
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
					excluir.setEnabled(true);
					alterar.setEnabled(true);
					
			}
		});
		JLabel lblId = new JLabel("ID:");
		lblId.setFont(new Font("Segoe UI Symbol", Font.BOLD, 15));
		lblId.setBounds(128, 40, 69, 20);
		contentPane.add(lblId);
		lblId.setHorizontalAlignment(SwingConstants.RIGHT);
		
		textPCodigo = new JTextField();
		textPCodigo.setBounds(215, 41, 305, 20);
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
		textPNome.setBounds(215, 73, 305, 20);
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
		
		JLabel label_7 = new JLabel("Nome:");
		label_7.setFont(new Font("Segoe UI Symbol", Font.BOLD, 15));
		label_7.setBounds(138, 72, 59, 20);
		contentPane.add(label_7);
		label_7.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.LIGHT_GRAY);
		separator.setBounds(12, 105, 629, 1);
		contentPane.add(separator);
		
		JButton selecionar = new JButton("Selecionar");
		selecionar.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		selecionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectedRow()!=-1){
					int id_autor = Integer.parseInt(String.valueOf(table.getValueAt(table.getSelectedRow(), 0)));
					int id_livro = Integer.parseInt(LivroView.tfCodigo.getText());
					try {
						sistema.adicionarAutoresLivros(id_livro, id_autor);
					} catch (InsertException | SelectException |NumberFormatException | JaCadastradoException e) {
						JOptionPane.showMessageDialog(null, e.getMessage());
					}
					LivroView.atualizarTabela_1();
					dispose();  
				}else JOptionPane.showMessageDialog(null, "Nenhum autor selecionado!");
			}
		});
		selecionar.setBackground(SystemColor.window);
		selecionar.setBounds(291, 354, 99, 23);
		contentPane.add(selecionar);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(30, 414, 611, 141);
		contentPane.add(layeredPane);
		
		JLabel Aa = new JLabel("Nome:");
		Aa.setHorizontalAlignment(SwingConstants.RIGHT);
		Aa.setFont(new Font("Dialog", Font.BOLD, 15));
		Aa.setBounds(55, 63, 70, 20);
		layeredPane.add(Aa);
		
		JLabel lblCdigo_1 = new JLabel("ID:");
		lblCdigo_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCdigo_1.setFont(new Font("Dialog", Font.BOLD, 15));
		lblCdigo_1.setBounds(55, 30, 70, 20);
		layeredPane.add(lblCdigo_1);
		
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
		
		JLabel lblNome_1_1 = new JLabel("Nacionalidade:");
		lblNome_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNome_1_1.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNome_1_1.setBounds(-38, 88, 163, 20);
		layeredPane.add(lblNome_1_1);
		
		tfNacionalidade = new JTextField();
		tfNacionalidade.setColumns(10);
		tfNacionalidade.setBounds(127, 89, 282, 19);
		layeredPane.add(tfNacionalidade);
		
		tfArea = new JTextField();
		tfArea.setColumns(10);
		tfArea.setBounds(127, 119, 282, 19);
		layeredPane.add(tfArea);
		
		JLabel lblSalrio = new JLabel("Area:");
		lblSalrio.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSalrio.setFont(new Font("Dialog", Font.BOLD, 15));
		lblSalrio.setBounds(55, 118, 70, 20);
		layeredPane.add(lblSalrio);
		
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
					} catch (InsertException | SelectException | JaCadastradoException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}
					limpar();
					atualizarTabela();
				}
				
				
			}
		});
		cadastrar.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		cadastrar.setBackground(UIManager.getColor("Button.darkShadow"));
		cadastrar.setBounds(456, 89, 118, 21);
		layeredPane.add(cadastrar);
		
		alterar = new JButton("Alterar");
		alterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tfArea.getText().equals("") || tfNacionalidade.getText().equals("") || tfNome.getText().equals("")) {      
					JOptionPane.showMessageDialog(null, "Preencha todos os campos");
				}else {
					Autor autor = new Autor();
					autor.setId(Integer.parseInt(tfCodigo.getText()));
					autor.setNome(tfNome.getText());
					autor.setNacionalidade(tfNacionalidade.getText());
					autor.setArea(tfArea.getText());
					try {
						sistema.alterarAutor(autor);
					} catch (UpdateException | SelectException | NaoCadastradoException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}				
					atualizarTabela();
					limpar();
				}
				
			}
		});
		alterar.setEnabled(false);
		alterar.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		alterar.setBackground(UIManager.getColor("Button.darkShadow"));
		alterar.setBounds(456, 63, 118, 21);
		layeredPane.add(alterar);
		
		excluir = new JButton("Excluir");
		excluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					sistema.excluirAutor(Integer.parseInt(tfCodigo.getText()));
				} catch (NumberFormatException | DeleteException | SelectException | NaoCadastradoException e1) {
					JOptionPane.showMessageDialog(null,  e1.getMessage());
				}
				limpar();
				atualizarTabela();
			}
		});
		excluir.setEnabled(false);
		excluir.setBorder(new LineBorder(new Color(0, 0, 0)));
		excluir.setBackground(UIManager.getColor("Button.darkShadow"));
		excluir.setBounds(456, 118, 118, 21);
		layeredPane.add(excluir);
		
		JButton limpar = new JButton("Limpar");
		limpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();			
			}
		});
		limpar.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		limpar.setBackground(UIManager.getColor("Button.focus"));
		limpar.setBounds(456, 31, 118, 21);
		layeredPane.add(limpar);
		
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
		sair_1.setBackground(SystemColor.window);
		sair_1.setBounds(276, 567, 92, 23);
		contentPane.add(sair_1);
	}


	public static void atualizarTabela() {
		try {
			autores = sistema.listarAdiconarAutoresLivros(Integer.parseInt(LivroView.tfCodigo.getText()));
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
		excluir.setEnabled(false);
		alterar.setEnabled(false);	
	}
	
	public void setCamposFromTabela() throws NumberFormatException, SelectException {
		tfCodigo.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 0)));
		tfNome.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 1)));
		tfNacionalidade.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 2)));
		tfArea.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 3)));

	}
}