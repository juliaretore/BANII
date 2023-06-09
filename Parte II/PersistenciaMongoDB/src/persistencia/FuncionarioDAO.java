package persistencia;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

import dados.Endereco;
import dados.Funcionario;
import exceptions.DeleteException;
import exceptions.InsertException;
import exceptions.JaCadastradoException;
import exceptions.NaoCadastradoException;
import exceptions.SelectException;
import exceptions.UpdateException;

public class FuncionarioDAO {
//	private FuncionarioDAO() throws ClassNotFoundException, SQLException, SelectException{
//		Connection conexao = Conexao.getConexao();
//		select_new_id_funcionario = conexao.prepareStatement("select nextval('id_funcionario')");
//		insert_funcionario =  conexao.prepareStatement("insert into funcionario values (?,?,?,?,?,?,?,?,?)");
//		insert_supervisao = conexao.prepareStatement("insert into supervisao values (?,?)");
//		update_funcionario = conexao.prepareStatement("update funcionario set login=?, nome=?, salario=?, turno=?, email=? where id=?");
//		inativa_funcionario = conexao.prepareStatement("update funcionario set ativo=0 where id=?");
//		delete_supervisoes = conexao.prepareStatement("delete from supervisao where id_bibliotecario=?");
//		delete_supervisoes_a = conexao.prepareStatement("delete from supervisao where id_assistente=?");
//		delete_supervisao = conexao.prepareStatement("delete from supervisao where id_bibliotecario=? and id_assistente=?");
//		select_bibliotecarios = conexao.prepareStatement("select id, nome, login, turno, salario, email from funcionario where tipo=1 and ativo=1");
//		select_assistentes = conexao.prepareStatement("select id, nome, login, turno, salario, email from funcionario where tipo=2 and ativo=1");
//		select_assistentes_bibliotecario = conexao.prepareStatement("select id, nome, login, turno, salario, email from funcionario where tipo=2 and ativo=1 and id in (select id_assistente from supervisao where id_bibliotecario=?)");
//		select_adicionar_supervisao = conexao.prepareStatement("select id, nome, login, turno, salario, email from funcionario where tipo=2 and ativo=1 and id not in (select id_assistente from supervisao where id_bibliotecario=?)");
//	}
	
	
	private static FuncionarioDAO instance = null;
	private static MongoCollection<Document> collection;
	private static MongoDatabase connection;

	public static FuncionarioDAO getInstance() throws Exception {
		if (instance == null)  instance = new FuncionarioDAO();
		return instance;
	}
	
	private FuncionarioDAO() throws  Exception {
	connection = Conexao.getConexao();
	try {
		collection = connection.getCollection("funcionario");
	} catch (Exception e) {
		throw new SelectException("Erro ao conectar a coleção funcionario");
	}
}

	public void insert_funcionario(Funcionario funcionario) throws Exception{
			try {
				ArrayList<String> al = new ArrayList<String>();
				Document document = new Document(
						"login", funcionario.getLogin())
						.append("senha", funcionario.getSenha())
						.append("nome", funcionario.getNome())
						.append("salario", funcionario.getSalario())
						.append("turno", funcionario.getTurno())
						.append("tipo", funcionario.getTipo())
						.append("email", funcionario.getEmail())
						.append("ativo", 1)
						.append("supervisiona", al);
				collection.insertOne(document);
			}catch (Exception e) {
				throw new InsertException("Erro ao inserir funcionário.");
			}		
	}

	public void insert_supervisao(String id_assistente, ObjectId id_bibliotecario) throws Exception{
		try {
			Document funcionario = collection.find(eq("_id", id_bibliotecario)).first();
			List<String>  assistentes =  (List<String>) funcionario.get("supervisiona");
			assistentes.add(id_assistente);			
			collection.updateOne(eq("_id", id_bibliotecario), combine(set("supervisiona", assistentes)));
		}catch (Exception e) {
			throw new InsertException("Erro ao inserir supervisão.");
		}		
}

	public void update_funcionario(Funcionario funcionario) throws Exception{
		ObjectId id = new ObjectId(funcionario.getId());
		try {
			collection.updateOne(eq("_id", id), combine(
					 set("login", funcionario.getLogin()),
				 	 set("senha", funcionario.getSenha()),
				 	 set("nome", funcionario.getNome()),
				     set("salario", funcionario.getSalario()),
	                 set("turno", funcionario.getTurno()),
					 set("email", funcionario.getEmail())
	                ));
		}catch (Exception e) {
			throw new UpdateException("Erro ao atualizar funcionário.");
		}
	}
	
	public void inativa_funcionario(ObjectId funcionario) throws Exception{
		try {
				collection.updateOne(eq("_id", funcionario), combine(set("ativo", 0)));
		}catch(Exception e) {
			throw new DeleteException("Erro ao inativar funcionário");
		}
	}
	
	public void delete_supervisao(ObjectId id_bibliotecario, String assistente) throws Exception{
		try {	
			Document bibliotecario = collection.find(eq("_id", id_bibliotecario)).first();
			List<String>  assistentes =  (List<String>) bibliotecario.get("supervisiona");
			for(int i=0; i<assistentes.size();i++) {
				if(assistentes.get(i).equals(assistente)) {
					assistentes.remove(i);
					break;
				}
			}
			collection.updateOne(eq("_id", id_bibliotecario), combine(set("supervisiona", assistentes)));
		}catch(Exception e) {
			throw new DeleteException("Erro ao deletar supervisão");
		}
	}
	
	public List<Object> select_bibliotecarios() throws Exception {
		List<Object> lista = new ArrayList<Object>();
		try {
			BasicDBObject query = new BasicDBObject();
			query.put("tipo", 1);
		    query.put("ativo", 1);
			
			MongoIterable<Document> funcionarios = collection.find(query);
			for(Document f : funcionarios) {
					Object[] linha  = {f.getObjectId("_id"), f.getString("nome"), f.getString("login"),  f.getString("turno"), f.getDouble("salario"), f.getString("email")};		
					lista.add(linha);
			}
		}catch(Exception e) {
			throw new SelectException("Erro ao buscar dados para preencher a tabela de bibliotecários");
		}
		return lista;
	}
	
	public List<Object> select_assistentes() throws SelectException {
		List<Object> lista = new ArrayList<Object>();
		try {
			BasicDBObject query = new BasicDBObject();
			query.put("tipo", 2);
		    query.put("ativo", 1);
			MongoIterable<Document> funcionarios = collection.find(query);
			for(Document f : funcionarios) {
					Object[] linha  = {f.getObjectId("_id"), f.getString("nome"), f.getString("login"),  f.getString("turno"), f.getDouble("salario"), f.getString("email")};		
					lista.add(linha);
				}	
		}catch(Exception e) {
			throw new SelectException("Erro ao buscar dados para preencher a tabela de assistentes");
		}
		return lista;
	}
	
	public List<Object> select_assistentes_bibliotecario(ObjectId id_bibliotecario) throws SelectException {
		List<Object> lista = new ArrayList<Object>();
		try {
			Document funcionario = collection.find(eq("_id", id_bibliotecario)).first();
			List<String> assistentes = (List<String>) funcionario.get("supervisiona");
			for(String a : assistentes) {
					ObjectId objId = new ObjectId(a);
					Document assistente = collection.find(eq("_id", objId)).first();
					if(assistente.getInteger("ativo")==1) {
						Object[] linha  = {assistente.getObjectId("_id"), assistente.getString("nome"), assistente.getString("login"),  assistente.getString("turno"), assistente.getDouble("salario"), assistente.getString("email")};		
						lista.add(linha);
					}
			}	
		}catch(Exception e) {
			throw new SelectException("Erro ao buscar dados para preencher a tabela de assistentes");
		}
			return lista;
		}	
					
	public List<Object> select_adicionar_supervisao(ObjectId id_bibliotecario) throws Exception {
		List<Object> lista = new ArrayList<Object>();
		try {
			Document funcionario = collection.find(eq("_id", id_bibliotecario)).first();
			List<String> assistentes_bibliotecario = (List<String>) funcionario.get("supervisiona");
			MongoIterable<Document> assistentes = collection.find(eq("tipo", 2));
			System.out.println(assistentes);
			for(Document f : assistentes) {
				if(!assistentes_bibliotecario.contains(String.valueOf(f.getObjectId("_id"))) && f.getInteger("ativo")==1) {
					Object[] linha  = {f.getObjectId("_id"), f.getString("nome"), f.getString("login"),  f.getString("turno"), f.getDouble("salario"), f.getString("email")};		
					lista.add(linha);
				}
			}
		}catch(Exception e) {
			throw new SelectException("Erro ao buscar dados para preencher a tabela de assistentes");
		}
		return lista;
	}

	

}
	
