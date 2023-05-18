package apresentacao;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dados.Usuario;
import exceptions.SelectException;
import negocio.Sistema;

import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.border.MatteBorder;
import java.awt.SystemColor;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.SwingConstants;

public class TelaPrincipal extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JTextField textusuario;
	static JTextField tfIdUsuario;
	static JButton btnUsuario = new JButton("Gerenciamento de Usuários");
	private JButton btnFuncionario;

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
		JLabel lblNewLabel = new JLabel("New label");
//		ImageIcon imagemTituloJanela = new javax.swing.ImageIcon(getClass().getResource("/img/logo.jpg"));
//		setIconImage(imagemTituloJanela.getImage());
//		lblNewLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/menu.jpg")));
		
		
		setTitle("Menu");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100,  692, 700);
		this.setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnUsuario.setBackground(Color.WHITE);
		btnUsuario.setForeground(Color.BLACK);
		btnUsuario.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnUsuario.setBounds(211, 253, 271, 77);
		contentPane.add(btnUsuario);
		textusuario = new JTextField();
		textusuario.setBackground(SystemColor.window);
		textusuario.setDisabledTextColor(new Color(0, 0, 0));
		textusuario.setEditable(false);
		textusuario.setBounds(429, 42, 86, 29);
		contentPane.add(textusuario);
		textusuario.setColumns(10);
		
		
		tfIdUsuario = new JTextField();
		tfIdUsuario.setBackground(SystemColor.window);
		tfIdUsuario.setEditable(false);
		tfIdUsuario.setColumns(10);
		tfIdUsuario.setBounds(402, 42, 29, 29);
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
		btnNewButton.setBounds(522, 41, 133, 31);
		contentPane.add(btnNewButton);
		
		btnUsuario.setActionCommand("Gerenciamento de Usuários");
		btnUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UsuarioView objeto = UsuarioView.getInstance();
				objeto.setVisible(true);
				objeto.setLocationRelativeTo(null);
			}
		});
		
		btnFuncionario = new JButton("Gerenciamento de Funcionário");
		btnFuncionario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FuncionarioView objeto = FuncionarioView.getInstance();
				objeto.setVisible(true);
				objeto.setLocationRelativeTo(null);
				
			}
		});
		btnFuncionario.setForeground(Color.BLACK);
		btnFuncionario.setFont(new Font("Dialog", Font.PLAIN, 14));
		btnFuncionario.setBackground(Color.WHITE);
		btnFuncionario.setActionCommand("Gerenciamento de Usuários");
		btnFuncionario.setBounds(211, 342, 271, 77);
		contentPane.add(btnFuncionario);
		
		JButton btnLivro = new JButton("Gerenciamento de Livros");
		btnLivro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LivroView objeto = LivroView.getInstance();
				objeto.setVisible(true);
				objeto.setLocationRelativeTo(null);
			}
		});
		btnLivro.setForeground(Color.BLACK);
		btnLivro.setFont(new Font("Dialog", Font.PLAIN, 14));
		btnLivro.setBackground(Color.WHITE);
		btnLivro.setActionCommand("");
		btnLivro.setBounds(211, 435, 271, 77);
		contentPane.add(btnLivro);
		
		
		
		lblNewLabel.setBounds(0, 12, 676, 661);
		contentPane.add(lblNewLabel);
		

	}
}
