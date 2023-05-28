 package apresentacao;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import dados.Funcionario;
import exceptions.LoginIncorretoException;
import exceptions.SelectException;
import negocio.Sistema;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JDialog;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.UIManager;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.border.LineBorder;

public class LoginView extends JFrame {


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tflogin;
	private static Sistema sistema;
	private JLabel lblNewLabel;
	private JPasswordField tfsenha;
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

	public LoginView() {
		try {
			sistema = new Sistema();
		} catch (ClassNotFoundException | SQLException | SelectException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/background3.png")));
		
		setTitle("Login");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 750, 522);
		this.setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tflogin = new JTextField();
		tflogin.setFont(new Font("Dialog", Font.PLAIN, 22));
		tflogin.setBorder(UIManager.getBorder("Tree.editorBorder"));
		tflogin.setBounds(253, 144, 310, 57);
		contentPane.add(tflogin);
		tflogin.setColumns(10);
		
		tfsenha = new JPasswordField();
		tfsenha.setFont(new Font("Dialog", Font.PLAIN, 22));
		tfsenha.setBorder(new LineBorder(new Color(0, 0, 0)));
		tfsenha.setBounds(253, 298, 300, 57);
		contentPane.add(tfsenha);
		
		JButton btlogin = new JButton("logar");
		btlogin.setFont(new Font("Lato Black", Font.BOLD, 20));
		btlogin.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		btlogin.setBackground(Color.WHITE);
		btlogin.setForeground(Color.BLACK);
		btlogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Funcionario funcionario = null;
				try {
					funcionario = sistema.validacaoLogin(tflogin.getText(), tfsenha.getText());
				} catch (SQLException | SelectException | LoginIncorretoException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
					
				}

				if(funcionario!=null) {
					JOptionPane meuJOPane = new JOptionPane("Bem vindo " +funcionario.getLogin()+ "!");
					final JDialog dialog = meuJOPane.createDialog("Carregando...");
					dialog.setModal(true);
					Timer timer = new Timer(600, new ActionListener() {  
						public void actionPerformed(ActionEvent ev) {  
							dialog.dispose();  
						}  
					});  
					
					timer.start();
					dialog.setVisible(true);
					timer.stop();
					TelaPrincipal frame = new TelaPrincipal();
					frame.setVisible(true);
					frame.textusuario.setText(funcionario.getLogin());
					frame.tfIdUsuario.setText(String.valueOf(funcionario.getId()));
					
					dispose();
				}
				tflogin.setText("");
				tfsenha.setText("");	
				
			}			
		
		});
		btlogin.setBounds(322, 385, 143, 45);
		contentPane.add(btlogin);
		
		JLabel lblLogin = new JLabel("LOGIN");
		lblLogin.setForeground(new Color(85, 97, 120));
		lblLogin.setFont(new Font("Lato Black", Font.BOLD, 16));
		lblLogin.setBounds(366, 127, 337, 20);
		contentPane.add(lblLogin);
		
		JLabel lblSenha = new JLabel("SENHA");
		lblSenha.setForeground(new Color(85, 97, 120));
		lblSenha.setFont(new Font("Lato Black", Font.BOLD, 16));
		lblSenha.setBounds(366, 278, 337, 20);
		contentPane.add(lblSenha);
		

		lblNewLabel.setBounds(0, -33, 771, 528);
		contentPane.add(lblNewLabel);
			

	}
}
