package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dados.Funcionario;
import dados.Usuario;
import exceptions.SelectException;

public class LoginDAO {
		private static LoginDAO instance = null;
		private PreparedStatement listaFuncionario;
		private PreparedStatement validarSenha;
		private PreparedStatement retornarFuncionario;
		
		public static LoginDAO getInstance() throws ClassNotFoundException, SQLException, SelectException{
			if(instance==null) instance=new LoginDAO();
			return instance;
			
		}
		
		public LoginDAO() throws ClassNotFoundException, SQLException, SelectException{
			Connection conexao = Conexao.getConexao();
			listaFuncionario = conexao.prepareStatement("SELECT login FROM funcionario");
			validarSenha =  conexao.prepareStatement("SELECT senha FROM funcionario WHERE login=?");
			retornarFuncionario = conexao.prepareStatement("SELECT * FROM funcionario WHERE login=?");
		}	
	

	
	public List<String> listaFuncionario() throws SQLException, SelectException{
		List<String> login = new ArrayList<String>();
		try {
			ResultSet rs = listaFuncionario.executeQuery();
			while (rs.next()) {
				login.add(rs.getString(1));
			}
		} catch (SQLException e) {
			throw new SelectException("Erro ao buscar funcionários cadastrados");
		}
		return login;
	}
	

	public List<String> validarSenha(String login) throws SQLException, SelectException{
		List<String> senha = new ArrayList<String>();
		try {
			validarSenha.setString(1, login);
			ResultSet rs= validarSenha.executeQuery();		
			while (rs.next()) {
				senha.add(rs.getString(1));
			}
		} catch (SQLException e) {
			throw new SelectException("Erro ao buscar senha");
		}
		return senha;
	}
		
	public Funcionario retornarFuncionario(String login) throws SQLException, SelectException {	
		Funcionario funcionario = null;
		try {
			retornarFuncionario.setString(1, login);
			ResultSet rs= retornarFuncionario.executeQuery();		
			while (rs.next()) {
				int id=rs.getInt(1);
				String login2 = rs.getString(2);
				String senha = rs.getString(3);
				String nome = rs.getString(4);
				double salario = rs.getDouble(5);
				String turno = rs.getString(6);
				int tipo = rs.getInt(7);
				return new Funcionario(id, nome, login2,senha, salario, turno, tipo);
			}
		} catch (SQLException e) {
			throw new SelectException("Erro ao buscar funcionário no login");
		}			
		return funcionario;
	}
			
}
