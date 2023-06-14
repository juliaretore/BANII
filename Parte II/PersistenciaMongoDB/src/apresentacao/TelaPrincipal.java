package apresentacao;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.SystemColor;

public class TelaPrincipal extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static JTextField textusuario;
	static JTextField tfIdUsuario;
	static JButton btnUsuario = new JButton("Gerenciamento de Usuários");
	private JButton btnFuncionario;
	private JButton btnEmprestimo;
	private JButton btnPagamentoDeMultas;

	public static void main(String[] args) { 
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginView frame = new LoginView();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}


	public TelaPrincipal() {
		setTitle("Menu");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0, 0,  1930, 1080);
		setExtendedState(JFrame.MAXIMIZED_BOTH);;
		this.setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/background.png")));
		
		btnUsuario.setBackground(Color.WHITE);
		btnUsuario.setForeground(Color.BLACK);
		btnUsuario.setFont(new Font("Lato Black", Font.PLAIN, 20));
		btnUsuario.setBounds(710, 204, 461, 77);
		contentPane.add(btnUsuario);
		
		
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
		tfIdUsuario.setBounds(1383, 12, 155, 29);
		contentPane.add(tfIdUsuario);
		
		JButton btnNewButton = new JButton("Deslogar");
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				LoginView frame;
				try {
					frame = new LoginView();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
				
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnNewButton.setBounds(1648, 12, 133, 31);
		contentPane.add(btnNewButton);
		
		btnUsuario.setActionCommand("Gerenciamento de Usuários");
		btnUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UsuarioView objeto;
				try {
					objeto = UsuarioView.getInstance();
					UsuarioView.tfIdUsuario.setText(tfIdUsuario.getText());
					UsuarioView.textusuario.setText(textusuario.getText());
					Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
					int height = screenSize.height;
					int width = screenSize.width;
					objeto.setSize(width, height);
					objeto.setVisible(true);
					objeto.setLocationRelativeTo(null);
					dispose();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			
			}
		});
		
		btnFuncionario = new JButton("Gerenciamento de Funcionário");
		btnFuncionario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FuncionarioView objeto = FuncionarioView.getInstance();
				FuncionarioView.tfIdUsuario.setText(tfIdUsuario.getText());
				FuncionarioView.textusuario.setText(textusuario.getText());
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				int height = screenSize.height;
				int width = screenSize.width;
				objeto.setSize(width, height);
				objeto.setVisible(true);
				objeto.setLocationRelativeTo(null);
				dispose();				
			}
		});
		btnFuncionario.setForeground(Color.BLACK);
		btnFuncionario.setFont(new Font("Lato Black", Font.PLAIN, 20));
		btnFuncionario.setBackground(Color.WHITE);
		btnFuncionario.setActionCommand("Gerenciamento de Usuários");
		btnFuncionario.setBounds(710, 370, 461, 77);
		contentPane.add(btnFuncionario);
		
		JButton btnLivro = new JButton("Gerenciamento de Livros");
		btnLivro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LivroView objeto = LivroView.getInstance();
				LivroView.tfIdUsuario.setText(tfIdUsuario.getText());
				LivroView.textusuario.setText(textusuario.getText());
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				int height = screenSize.height;
				int width = screenSize.width;
				objeto.setSize(width, height);
				objeto.setVisible(true);
				objeto.setLocationRelativeTo(null);
				dispose();

			}
		});
		btnLivro.setForeground(Color.BLACK);
		btnLivro.setFont(new Font("Lato Black", Font.PLAIN, 20));
		btnLivro.setBackground(Color.WHITE);
		btnLivro.setActionCommand("");
		btnLivro.setBounds(710, 524, 461, 77);
		contentPane.add(btnLivro);
		
		btnEmprestimo = new JButton("Gerênciar Emprestimo");
		btnEmprestimo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EmprestimoView objeto = EmprestimoView.getInstance();	
				EmprestimoView.tfIdUsuario.setText(tfIdUsuario.getText());
				EmprestimoView.textusuario.setText(textusuario.getText());
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				int height = screenSize.height;
				int width = screenSize.width;
				objeto.setSize(width, height);
				objeto.setVisible(true);
				objeto.setLocationRelativeTo(null);
				dispose();
			}
		});
		btnEmprestimo.setForeground(Color.BLACK);
		btnEmprestimo.setFont(new Font("Lato Black", Font.PLAIN, 20));
		btnEmprestimo.setBackground(Color.WHITE);
		btnEmprestimo.setActionCommand("");
		btnEmprestimo.setBounds(710, 676, 461, 77);
		contentPane.add(btnEmprestimo);
		
		btnPagamentoDeMultas = new JButton("Pagamento de Multas");
		btnPagamentoDeMultas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MultasView objeto = MultasView.getInstance();
				MultasView.tfIdUsuario.setText(tfIdUsuario.getText());
				MultasView.textusuario.setText(textusuario.getText());
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				int height = screenSize.height;
				int width = screenSize.width;
				objeto.setSize(width, height);
				objeto.setVisible(true);
				objeto.setLocationRelativeTo(null);
				dispose();	
			}
		});
		btnPagamentoDeMultas.setForeground(Color.BLACK);
		btnPagamentoDeMultas.setFont(new Font("Lato Black", Font.PLAIN, 20));
		btnPagamentoDeMultas.setBackground(Color.WHITE);
		btnPagamentoDeMultas.setActionCommand("");
		btnPagamentoDeMultas.setBounds(710, 830, 461, 77);
		contentPane.add(btnPagamentoDeMultas);
		
		lblNewLabel.setBounds(0, -17, 1846, 1021);
		contentPane.add(lblNewLabel);

	}
}
