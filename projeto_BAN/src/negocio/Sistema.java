package negocio;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
//import javazoom.jl.decoder.JavaLayerException;
//import javazoom.jl.player.Player;
import persistencia.*;
import java.util.List;
import dados.*;
import exceptions.ArquivoNaoEncontradoException;
import exceptions.DeleteException;
import exceptions.InsertException;
import exceptions.JaCadastradoException;
import exceptions.LoginIncorretoException;
import exceptions.NaoCadastradoException;
import exceptions.SelectException;
import exceptions.UpdateException;

public class Sistema {
	private static LoginDAO loginDAO;
	private static UsuarioDAO usuarioDAO;
	private static FuncionarioDAO funcionarioDAO;
	
	public Sistema() throws ClassNotFoundException, SQLException, SelectException{
		loginDAO = LoginDAO.getInstance();
		usuarioDAO = UsuarioDAO.getInstance();
		funcionarioDAO = funcionarioDAO.getInstance();
	}
	
	
	//LOGIN
	public Funcionario validacaoLogin(String login, String senha) throws SQLException, SelectException, LoginIncorretoException {
		List<String> logins = loginDAO.listaFuncionario();
		if(logins.contains(login)) {
			List<String> senhaLogin = loginDAO.validarSenha(login);
			if(senhaLogin.contains(senha)) {
				return loginDAO.retornarFuncionario(login);
			}else throw new LoginIncorretoException("Senha incorreta! Tente novamente");
			
		}else throw new LoginIncorretoException("Funcionário não encontrado! Tente novamente");
	}
	
	
	//USUARIO
	public List<Object> listarUsuarios() throws SelectException{
		return usuarioDAO.select_table();
	}
	
	public List<Object> listarTelefones(int id_usuario) throws SelectException{
		return usuarioDAO.select_telefones(id_usuario);
	}
	
	public Endereco buscaEndereco(int id_usuario) throws SelectException {
		return usuarioDAO.select_endereco(id_usuario);
	}
	
	public void adicionarUsuario(Usuario usuario) throws InsertException, SelectException, JaCadastradoException {
		usuarioDAO.insert_usuario(usuario);
	}
	
	public void adicionarTelefone(int usuario, String telefone) throws InsertException, SelectException, JaCadastradoException {
		usuarioDAO.insert_telefone(usuario,  telefone);
		
	}
	
	public void alterarUsuario(Usuario usuario) throws UpdateException, SelectException, NaoCadastradoException {
		usuarioDAO.update_usuario(usuario);
	}
	
	public void excluirUsuario(int usuario, int endereco) throws DeleteException, SelectException, NaoCadastradoException {
		usuarioDAO.delete(usuario, endereco);
	}
	
	public void excluirTelefone(int usuario, String telefone) throws DeleteException, SelectException, NaoCadastradoException {
		usuarioDAO.delete_telefone(usuario, telefone);
	}
	
	public void alterarTelefone(int usuario, String novo_telefone,  String antigo_telefone) throws UpdateException {
		usuarioDAO.update_telefone(usuario, novo_telefone, antigo_telefone);
	}

// FUNCIONARIO 
	public List<Object> listarBibliotecarios() throws SelectException{
		return funcionarioDAO.select_bibliotecarios();
	}
	
	public List<Object> listarAssistentes() throws SelectException{
		return funcionarioDAO.select_assistentes();
	}
	
	public List<Object> listarAssistentesBibliotecario(int id_bibliotecario) throws SelectException{
		return funcionarioDAO.select_assistentes_bibliotecario(id_bibliotecario);
	}
	
	public List<Object> listarAdicionarAssistentesBibliotecario(int id_bibliotecario) throws SelectException{
		return funcionarioDAO.select_adicionar_supervisao(id_bibliotecario);
	}
	
	public void adicionarFuncionario(Funcionario funcionario) throws InsertException, SelectException, JaCadastradoException {
		funcionarioDAO.insert_funcionario(funcionario);
	}
	public void alterarFuncionario(Funcionario funcionario) throws UpdateException, SelectException, NaoCadastradoException {
		funcionarioDAO.update_funcionario(funcionario);
	}
	public void excluirFuncionario(int funcionario, int op) throws DeleteException, SelectException, NaoCadastradoException {
		funcionarioDAO.delete_funcionario(funcionario, op);
	}

	
}
