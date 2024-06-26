package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import dados.Exemplar;
import dados.Funcionario;
import dados.Livro;
import exceptions.DeleteException;
import exceptions.InsertException;
import exceptions.JaCadastradoException;
import exceptions.NaoCadastradoException;
import exceptions.SelectException;
import exceptions.UpdateException;

public class LivroDAO {
private static LivroDAO instance = null;
	
	private PreparedStatement select_new_id_livro;
	private PreparedStatement select_new_id_exemplar;
	private PreparedStatement insert_livro;
	private PreparedStatement insert_exemplar;
	private PreparedStatement insert_autores_livro;
	private PreparedStatement update_livro;
	private PreparedStatement update_exemplar;
	private PreparedStatement delete_autor_livro;
	private PreparedStatement select_livros;
	private PreparedStatement select_autores_livro;
	private PreparedStatement select_exemplares_livro;
	private PreparedStatement select_adicionar_autores_livro;
	private PreparedStatement select_exemplar_emprestado;
	private PreparedStatement select_exemplares_livro_disponiveis;
	private PreparedStatement select_usuario;

	
	

	public static LivroDAO getInstance() throws ClassNotFoundException, SQLException, SelectException{
		if(instance==null) instance=new LivroDAO();
		return instance;
	}
	
	private LivroDAO() throws ClassNotFoundException, SQLException, SelectException{
		Connection conexao = Conexao.getConexao();
		select_new_id_livro = conexao.prepareStatement("select nextval('id_livro')");
		select_new_id_exemplar = conexao.prepareStatement("select nextval('id_exemplar')");
		insert_livro =  conexao.prepareStatement("insert into livro values (?,?,?,?)");
		insert_exemplar = conexao.prepareStatement("insert into exemplar values (?,?,?,?,?)");
		insert_autores_livro = conexao.prepareStatement("insert into autores_livro values (?,?)");
		update_livro = conexao.prepareStatement("update livro set isbn=?, titulo=?, editora=? where id=?");
		update_exemplar = conexao.prepareStatement("update exemplar set prateleira=?, estante=?, colecao=? where id=?");
		delete_autor_livro = conexao.prepareStatement("delete from autores_livro where id_livro=? and id_autor=?");
		select_livros = conexao.prepareStatement("select id, isbn, titulo, editora from livro");
		select_autores_livro = conexao.prepareStatement("select a.id, a.nome, a.nacionalidade, a.area from autor a join autores_livro al on a.id=al.id_autor where id_livro=?");
		select_exemplares_livro = conexao.prepareStatement("select id, prateleira, estante, colecao, id_usuario_reserva from exemplar where id_livro=?");
		select_usuario = conexao.prepareStatement("select nome from usuario where id=?");
		select_exemplares_livro_disponiveis = conexao.prepareStatement("select id, prateleira, estante, colecao from exemplar where id_livro=? and id_usuario_reserva is null and colecao!='Reserva' and colecao!='Fora de uso' and id not in (select id_exemplar from emprestimo where situacao=0)");
		select_exemplar_emprestado = conexao.prepareStatement("select id_usuario from emprestimo where id_exemplar=? and situacao=0");
		select_adicionar_autores_livro = conexao.prepareStatement("select id, nome, nacionalidade, area from autor where id not in (select a.id a from autor a join autores_livro al on a.id=al.id_autor where al.id_livro=?)");
	}
	
	private int select_new_id_livro() throws SelectException{
		try {
			ResultSet rs = select_new_id_livro.executeQuery();
			if(rs.next()) return rs.getInt(1);
		}catch(SQLException e) {
			throw new SelectException("Erro ao buscar novo ID da tabela livro");
		}
		return 0;
	}
	
	private int select_new_id_exemplar() throws SelectException{
		try {
			ResultSet rs = select_new_id_exemplar.executeQuery();
			if(rs.next()) return rs.getInt(1);
		}catch(SQLException e) {
			throw new SelectException("Erro ao buscar novo ID da tabela exemplar");
		}
		return 0;
	}
	
	public void insert_livro(Livro livro) throws InsertException, SelectException, JaCadastradoException{
			try {
				insert_livro.setInt(1, select_new_id_livro());
				insert_livro.setString(2, livro.getIsbn());
				insert_livro.setString(3, livro.getTitulo());
				insert_livro.setString(4, livro.getEditora());
				insert_livro.executeUpdate();
			}catch (SQLException e) {
				throw new InsertException("Erro ao inserir livro.");
			}		
	}
	
	public void insert_exemplar(Exemplar exemplar) throws InsertException, SelectException, JaCadastradoException{
		try {
			insert_exemplar.setInt(1, select_new_id_exemplar());
			insert_exemplar.setInt(2, exemplar.getPrateleira());
			insert_exemplar.setInt(3, exemplar.getEstante());
			insert_exemplar.setInt(4, exemplar.getId_livro());
			insert_exemplar.setString(5, exemplar.getColecao());
			insert_exemplar.executeUpdate();
		}catch (SQLException e) {
			throw new InsertException("Erro ao inserir exemplar.");
		}		
}
	
	public void insert_autores_livro(int id_livro, int id_autor) throws InsertException, SelectException, JaCadastradoException{
		try {
			insert_autores_livro.setInt(1, id_livro);
			insert_autores_livro.setInt(2, id_autor);
			insert_autores_livro.executeUpdate();
		}catch (SQLException e) {
			throw new InsertException("Erro ao inserir autor ao livro.");
		}		
}

	public void update_livro(Livro livro) throws UpdateException, SelectException, NaoCadastradoException{
		try {
			update_livro.setString(1, livro.getIsbn());
			update_livro.setString(2, livro.getTitulo());
			update_livro.setString(3, livro.getEditora());
			update_livro.setInt(4, livro.getId());
			update_livro.executeUpdate();
		}catch (SQLException e) {
			throw new UpdateException("Erro ao atualizar livro.");
		}
	}

	public void update_exemplar(Exemplar exemplar) throws UpdateException, SelectException, NaoCadastradoException{
		try {
			update_exemplar.setInt(1, exemplar.getPrateleira());
			update_exemplar.setInt(2, exemplar.getEstante());
			update_exemplar.setString(3, exemplar.getColecao());
			update_exemplar.setInt(4, exemplar.getId());
			update_exemplar.executeUpdate();
		}catch (SQLException e) {
			throw new UpdateException("Erro ao atualizar exemplar.");
		}
	}
	
	public void delete_autor_livro(int id_livro, int id_autor) throws DeleteException, SelectException, NaoCadastradoException{
		try {	
			delete_autor_livro.setInt(1, id_livro);
			delete_autor_livro.setInt(2, id_autor);
			delete_autor_livro.executeUpdate();
		}catch(SQLException e) {
			throw new DeleteException("Erro ao deletar autor do livro");
		}
	}
	
	public List<Object> select_livros() throws SelectException {
		List<Object> lista = new ArrayList<Object>();
		try {
			ResultSet rs = select_livros.executeQuery();
			while(rs.next()) {
				Object[] linha  = {rs.getInt(1), rs.getString(2), rs.getString(3),rs.getString(4)};
				lista.add(linha);
			}
		}catch(SQLException e) {
			throw new SelectException("Erro ao buscar dados para preencher a tabela de livros");
		}
		return lista;
	}
	
	public List<Object> select_autores_livro(int id_livro) throws SelectException {
		List<Object> lista = new ArrayList<Object>();
		try {
			select_autores_livro.setInt(1, id_livro);
			ResultSet rs = select_autores_livro.executeQuery();
			while(rs.next()) {
				Object[] linha  = {rs.getInt(1), rs.getString(2), rs.getString(3),rs.getString(4)};
				lista.add(linha);
			}
		}catch(SQLException e) {
			throw new SelectException("Erro ao buscar os autores dos livros");
		}
		return lista;
	}
	
	public String select_usuario(int id_usuario) throws SelectException {
		try {
			select_usuario.setInt(1, id_usuario);
			ResultSet rs = select_usuario.executeQuery();
			rs.next();
			return rs.getString(1);
		}catch(SQLException e) {
			throw new SelectException("Erro ao buscar usuario");
		}
	}

	public List<Object> select_exemplares_livro(int id_livro) throws SelectException {
		List<Object> lista = new ArrayList<Object>();
		try {
			select_exemplares_livro.setInt(1, id_livro);
			ResultSet rs = select_exemplares_livro.executeQuery();
			while(rs.next()) {
				int id_exemplar = rs.getInt(1);
				int usuario = rs.getInt(5);
				String nome = "";
				String situacao="Disponível"; // Disponível, Em empréstimo ou Reservado

				select_exemplar_emprestado.setInt(1, id_exemplar);
				ResultSet rs2 = select_exemplar_emprestado.executeQuery();
				 if(rs2.next()==false) { // Não está emprestado
				      if(usuario != 0) {// Está reservado
							situacao = "Reservado";
							nome = select_usuario(usuario);
				      }
				 }else { // Está emprestado
					  situacao = "Em empréstimo"; 
					  nome = select_usuario(rs2.getInt(1));
				 }
				 
				 if(rs.getString(4).equals("Fora de uso")) situacao="Fora de uso";
				
				Object[] linha  = {id_exemplar, rs.getInt(2), rs.getInt(3),rs.getString(4), situacao, nome};
				lista.add(linha);
			}
		}catch(SQLException e) {
			throw new SelectException("Erro ao buscar dados para preencher a tabela de exemplares do livro");
		}
		return lista;
	}
	
	public List<Object> select_exemplares_livro_disponiveis(int id_livro) throws SelectException {
		List<Object> lista = new ArrayList<Object>();
		try {
			select_exemplares_livro_disponiveis.setInt(1, id_livro);
			ResultSet rs = select_exemplares_livro_disponiveis.executeQuery();
			while(rs.next()) {
				Object[] linha  = { rs.getInt(1), rs.getInt(2), rs.getInt(3),rs.getString(4)};
				lista.add(linha);
			}
		}catch(SQLException e) {
			throw new SelectException("Erro ao buscar dados para preencher a tabela de exemplares disponiveis");
		}
		return lista;
	}
	
	public List<Object> select_adicionar_autores_livro(int id_livro) throws SelectException {
		List<Object> lista = new ArrayList<Object>();
		try {
			select_adicionar_autores_livro.setInt(1, id_livro);
			ResultSet rs = select_adicionar_autores_livro.executeQuery();
			while(rs.next()) {
				Object[] linha  = {rs.getInt(1), rs.getString(2), rs.getString(3),rs.getString(4)};
				lista.add(linha);
			}
		}catch(SQLException e) {
			throw new SelectException("Erro ao buscar autores para adicionar");
		}
		return lista;
	}	
	
}
	
