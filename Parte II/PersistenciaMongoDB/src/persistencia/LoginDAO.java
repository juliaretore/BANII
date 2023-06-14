package persistencia;

import static com.mongodb.client.model.Filters.eq;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

import dados.Endereco;
import dados.Funcionario;
import dados.Usuario;
import exceptions.SelectException;

public class LoginDAO {
	private static LoginDAO instance = null;
	private static MongoCollection<Document> collection;
	private static MongoDatabase connection;

	public static LoginDAO getInstance() throws Exception {
		if (instance == null)  instance = new LoginDAO();
		return instance;
	}
	
	private LoginDAO() throws  Exception {
	connection = Conexao.getConexao();
	try {
		collection = connection.getCollection("funcionario");
	} catch (Exception e) {
		throw new Exception("Erro ao conectar");
	}
}


	
	public List<String> listaFuncionario() throws Exception{
		List<String> login = new ArrayList<String>();
		try {
			MongoIterable<Document> funcionarios = collection.find();
			for(Document f : funcionarios) {
				login.add(f.getString("login"));
			}
		} catch (Exception e) {
			throw new Exception("Erro ao buscar funcionários cadastrados");
		}
		return login;
	}
	

	public List<String> validarSenha(String login) throws Exception{
		List<String> senha = new ArrayList<String>();
		try {
			Document funcionario = collection.find(eq("login", login)).first();
			senha.add(funcionario.getString("senha"));
		} catch (Exception e) {
			throw new Exception("Erro ao buscar senha");
		}
		return senha;
	}
		
	public Funcionario retornarFuncionario(String login) throws Exception {	
		Funcionario funcionario = null;
		try {
			Document f = collection.find(eq("login", login)).first();
			String id = String.valueOf(f.getObjectId("_id")) ;
			String flogin = f.getString("login");
			String senha = f.getString("senha");
			String nome = f.getString("nome");
			double salario = f.getDouble("salario");
			String turno = f.getString("turno");
			int tipo = f.getInteger("tipo");
			String email = f.getString("email");
			return new Funcionario(id, nome, login, senha, salario, turno, tipo, email);
		} catch (Exception e) {
			throw new Exception("Erro ao buscar funcionário no login");
		}
	}
			
}
