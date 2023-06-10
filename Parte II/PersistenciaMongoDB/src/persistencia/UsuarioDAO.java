package persistencia;

import java.util.ArrayList;
import java.util.List;
import com.mongodb.client.MongoDatabase;
import dados.Endereco;
import dados.Usuario;
import exceptions.DeleteException;
import exceptions.InsertException;
import exceptions.SelectException;
import exceptions.UpdateException;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;
import org.bson.Document;
import org.bson.types.ObjectId;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoIterable;

		
public class UsuarioDAO {
		private static UsuarioDAO instance = null;
		private static MongoCollection<Document> collection;
		private static MongoDatabase connection;

		public static UsuarioDAO getInstance() throws Exception {
			if (instance == null)  instance = new UsuarioDAO();
			return instance;
		}
		
	private UsuarioDAO() throws  Exception {
		connection = Conexao.getConexao();
		try {
			collection = connection.getCollection("usuario");
		} catch (Exception e) {
			throw new SelectException("Erro ao conetar a coleção usuario");
		}
	}
		
	public void insert_usuario(Usuario usuario) throws Exception{
			try {
				ArrayList<String> al = new ArrayList<String>();
				Document document = new Document("nome", usuario.getNome())
						.append("turno", usuario.getTurno())
						.append("email", usuario.getEmail())
						.append("categoria_id", usuario.getCategoria().getId())
						.append("telefones", al)
						.append("endereço", usuario.getEndereco());
				collection.insertOne(document);
			}catch (Exception e) {
				throw new InsertException("Erro ao inserir usuário.");
			}		
	}	

	public void insert_telefone(ObjectId id_usuario, String telefone) throws Exception {
		try {
			Document usuario = collection.find(eq("_id", id_usuario)).first();
			List<String>  telefones =  (List<String>) usuario.get("telefones");
			telefones.add(telefone);			
			collection.updateOne(eq("_id", id_usuario), combine(set("telefones", telefones)));
		}catch (Exception e) {
			throw new InsertException("Erro ao inserir telefone.");
		}
	}
	
	public void update_usuario(Usuario usuario) throws Exception{
		ObjectId id = new ObjectId(usuario.getId());
		try {
			collection.updateOne(eq("_id", id), combine(
					 set("nome", usuario.getNome()),
	                 set("turno", usuario.getTurno()),
					 set("email", usuario.getEmail()),
					 set("categoria_id", usuario.getCategoria().getId()),
					 set("endereço", usuario.getEndereco())
	                ));
		}catch (Exception e) {
			throw new UpdateException("Erro ao atualizar usuário.");
		}
	}

	public void update_telefone(ObjectId id_usuario, String novo_telefone,  String antigo_telefone) throws Exception {

		try {
			Document usuario = collection.find(eq("_id", id_usuario)).first();
			List<String>  telefones =  (List<String>) usuario.get("telefones");
			for(int i=0; i<telefones.size();i++) {
				if(telefones.get(i).equals(antigo_telefone)) {
					telefones.set(i, novo_telefone);
				}
			}
			collection.updateOne(eq("_id", id_usuario), combine(set("telefones", telefones)));

		}catch (Exception e) {
			throw new UpdateException("Erro ao atualizar o telefone do usuário.");
		}
	}

	public Endereco select_endereco(ObjectId id_usuario) throws Exception  {
		Endereco end = new Endereco();
		try {
			Document usuario = collection.find(eq("_id", id_usuario)).first();
			Document endereco = (Document) usuario.get("endereço");
			end.setCidade(endereco.getString("cidade"));
			end.setBairro(endereco.getString("bairro"));
			end.setComplemento(endereco.getString("complemento"));
			end.setEstado(endereco.getString("estado"));
			end.setNumero(endereco.getInteger("numero"));
			end.setRua(endereco.getString("rua"));
		}catch(Exception e) {
			throw new Exception("Erro ao buscar endereço");
		}
		return end;
	}

	public List<Object> select_telefones(ObjectId id_usuario) throws Exception {
		List<Object> lista = new ArrayList<Object>();
		try {
			Document usuario = collection.find(eq("_id", id_usuario)).first();
			List<String>  telefones = (List<String>) usuario.get("telefones");
			for(Object t : telefones) {
				Object[] linha  = {t};
				lista.add(linha);
			}
		}catch(Exception e) {
			throw new Exception("Erro ao buscar telefone");
		}
		return lista;	
	}
	
	public ArrayList<Object> select_table() throws Exception {
		ArrayList<Object> lista = new ArrayList<Object>();
		try {
			MongoIterable<Document> usuario_atual = collection.find();
			for(Document user : usuario_atual) {
				Endereco e = new Endereco();
				Document endereco = (Document) user.get("endereço");
				e.setCidade(endereco.getString("cidade"));
				e.setBairro(endereco.getString("bairro"));
				e.setComplemento(endereco.getString("complemento"));
				e.setEstado(endereco.getString("estado"));
				e.setNumero(endereco.getInteger("numero"));
				e.setRua(endereco.getString("rua"));
				MongoCollection<Document>  col = connection.getCollection("categoria");
				Document categoria = col.find(eq("_id", user.getInteger("categoria_id"))).first();
				Object[] linha  = {user.getObjectId("_id"), user.getString("nome"), categoria.getString("nome"),  user.getString("turno"), e.endereco_completo(), user.getString("email")};
				lista.add(linha);
			}
		} catch (Exception e) {
			throw new Exception("Erro ao atualizar tabela de usuarios");
		}
		
		return lista;
	}


//	select_table_emprestimos = conexao.prepareStatement("select u.id, u.nome, c.nome, u.turno from usuario u join categoria c on u.id_categoria=c.id");
//	public List<Object> select_table_emprestimos() throws SelectException {
//		List<Object> lista = new ArrayList<Object>();
//		try {
//			ResultSet rs = select_table_emprestimos.executeQuery();
//			while(rs.next()) {
//				Object[] linha  = {rs.getInt(1), rs.getString(2), rs.getString(3),rs.getString(4)};
//				lista.add(linha);
//			}
//		}catch(SQLException e) {
//			throw new SelectException("Erro ao buscar dados para preencher a tabela");
//		}
//		return lista;
//	}
	
	public void delete_telefone(ObjectId id_usuario, String telefone) throws Exception{
		try {
			Document usuario = collection.find(eq("_id", id_usuario)).first();
			List<String>  telefones =  (List<String>) usuario.get("telefones");
			for(int i=0; i<telefones.size();i++) {
				if(telefones.get(i).equals(telefone)) {
					telefones.remove(i);
					break;
				}
			}
			collection.updateOne(eq("_id", id_usuario), combine(set("telefones", telefones)));
		}catch(Exception e) {
			throw new DeleteException("Erro ao deletar telefone");
		}
	}
	
}


	
