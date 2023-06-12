package persistencia;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;

import dados.Exemplar;
import dados.Livro;
import exceptions.DeleteException;
import exceptions.InsertException;
import exceptions.SelectException;
import exceptions.UpdateException;

public class LivroDAO {
	private static LivroDAO instance = null;
	private static MongoCollection<Document> collection_livro;
	private static MongoCollection<Document> collection_autor;
	private static MongoCollection<Document> collection_exemplar;
	private static MongoCollection<Document> collection_usuario;
	private static MongoCollection<Document>  collection_emprestimo;
	private static MongoDatabase connection;

	public static LivroDAO getInstance() throws Exception {
		if (instance == null)  instance = new LivroDAO();
		return instance;
	}
	
	private LivroDAO() throws  Exception {
		connection = Conexao.getConexao();
		try {
			collection_livro = connection.getCollection("livro");
			collection_autor = connection.getCollection("autor");
			collection_exemplar = connection.getCollection("exemplar");
			collection_usuario = connection.getCollection("usuario");
			collection_emprestimo = connection.getCollection("emprestimo");


		} catch (Exception e) {
			throw new SelectException("Erro ao conectar a coleção");
		}
	}
	
	public void insert_livro(Livro livro) throws Exception{
			try {
				ArrayList<Object> empty_array = new ArrayList<Object>();
				Document document = new Document(
						"isbn", livro.getIsbn())
						.append("titulo", livro.getTitulo())
						.append("editora", livro.getEditora())
						.append("autores", empty_array)
						.append("reservas", empty_array);
				collection_livro.insertOne(document);
			}catch (Exception e) {
				throw new InsertException("Erro ao inserir livro.");
			}		
	}
	
	public void insert_exemplar(Exemplar exemplar) throws Exception{
		try {
			Document document = new Document(
					"prateleira", exemplar.getPrateleira())
					.append("estante", exemplar.getEstante())
					.append("colecao", exemplar.getColecao())
					.append("livro", exemplar.getId_livro())
					.append("data_reserva", null)
					.append("usuario_reserva", null);
			collection_exemplar.insertOne(document);
		}catch (Exception e) {
			throw new InsertException("Erro ao inserir exemplar.");
		}		
}
	
	public void insert_autores_livro(ObjectId id_livro, String id_autor) throws Exception{
		try {
			Document livro = collection_livro.find(eq("_id", id_livro)).first();
			List<String>  autores =  (List<String>) livro.get("autores");
			autores.add(id_autor);			
			collection_livro.updateOne(eq("_id", id_livro), combine(set("autores", autores)));
		}catch (Exception e) {
			throw new InsertException("Erro ao inserir autor ao livro.");
		}		
}

	public void update_livro(Livro livro) throws Exception{
		ObjectId id = new ObjectId(livro.getId());
		try {
				collection_livro.updateOne(eq("_id", id), combine(
						 set("isbn", livro.getIsbn()),
					 	 set("titulo", livro.getTitulo()),
					     set("editora", livro.getEditora()))
		                );
		}catch (Exception e) {
			throw new UpdateException("Erro ao atualizar livro.");
		}
	}

	public void update_exemplar(Exemplar exemplar) throws Exception{
		ObjectId id = new ObjectId(exemplar.getId());
		try {
			collection_exemplar.updateOne(eq("_id", id), combine(
					 set("prateleira", exemplar.getPrateleira()),
				 	 set("estante", exemplar.getEstante()),
				     set("colecao", exemplar.getColecao()))
	                );
		}catch (Exception e) {
			throw new UpdateException("Erro ao atualizar exemplar.");
		}
	}
	
	public void delete_autor_livro(ObjectId id_livro, String id_autor) throws Exception{
		try {	
			Document livro = collection_livro.find(eq("_id", id_livro)).first();
			List<String>  autores =  (List<String>) livro.get("autores");
			for(int i=0; i<autores.size();i++) {
				if(autores.get(i).equals(id_autor)) {
					autores.remove(i);
					break;
				}
			}
			collection_livro.updateOne(eq("_id", id_livro), combine(set("autores", autores)));
		}catch(Exception e) {
			throw new DeleteException("Erro ao deletar autor do livro");
		}
	}
	
	public List<Object> select_livros() throws Exception {
		ArrayList<Object> lista = new ArrayList<Object>();
		try {
			MongoIterable<Document> livros = collection_livro.find();
			for(Document livro : livros) {
				Object[] linha  = {livro.getObjectId("_id"), livro.getString("isbn"), livro.getString("titulo"),  livro.getString("editora")};
				lista.add(linha);
			}
		}catch(Exception e) {
			throw new SelectException("Erro ao buscar dados para preencher a tabela de livros");
		}
		return lista;
	}

	public List<Object> select_autores_livro(ObjectId id_livro) throws Exception {
		List<Object> lista = new ArrayList<Object>();
		try {
			Document livro = collection_livro.find(eq("_id", id_livro)).first();
			List<String>  autores = (List<String>) livro.get("autores");
			for(String a : autores) {
				Document autor = collection_autor.find(eq("_id", new ObjectId(a))).first();
				Object[] linha  = {autor.getObjectId("_id"), autor.getString("nome"), autor.getString("nacionalidade"), autor.getString("area")};
				lista.add(linha);
			}
		}catch(Exception e) {
			throw new SelectException("Erro ao buscar os autores dos livros");
		}
		return lista;
	}
	
	public List<Object> select_adicionar_autores_livro(ObjectId id_livro) throws Exception {
		List<Object> lista = new ArrayList<Object>();
		try {
			Document livro = collection_livro.find(eq("_id", id_livro)).first();
			List<String>  autores_livro = (List<String>) livro.get("autores");
			MongoIterable<Document> autores = collection_autor.find();
			for (Document autor : autores) {
				if(!autores_livro.contains(String.valueOf(autor.getObjectId("_id")))) {
					Object[] linha  = {autor.getObjectId("_id"), autor.getString("nome"), autor.getString("nacionalidade"), autor.getString("area")};
					lista.add(linha);				
				}
			}
	}catch(Exception e) {
		throw new SelectException("Erro ao buscar autores para adicionar");
	}
	return lista;
}
	
	public List<Object> select_exemplares_livro(String id_livro) throws Exception {
		List<Object> lista = new ArrayList<Object>();
		try {
			MongoIterable<Document> exemplares = collection_exemplar.find(eq("livro", id_livro));
			for(Document exemplar : exemplares) {
				ObjectId id_exemplar = exemplar.getObjectId("_id");
				String id_usuario = exemplar.getString("usuario_reserva");
				String nome = "";
				String situacao="Disponível";
				
	            Bson query = Filters.and(
	                    Filters.eq("id_exemplar", String.valueOf(id_exemplar)),
	                    Filters.eq("situacao", 0)
	            );
	            Document result = (Document) collection_emprestimo.find(query).first();

	            if(result!=null) { //Está emprestado
	            	situacao = "Em empréstimo";
					Document usuario = collection_usuario.find(eq("_id", new ObjectId(result.getString("id_usuario")))).first();
					nome = usuario.getString("nome");
	            	
	            }
				if(id_usuario!=null) { //Está reservado
					situacao = "Reservado";
 					Document usuario = collection_usuario.find(eq("_id", new ObjectId(id_usuario))).first();
					nome = usuario.getString("nome");
				}
	
				Object[] linha  = {id_exemplar, exemplar.getInteger("prateleira"), exemplar.getInteger("estante"), exemplar.getString("colecao"), situacao, nome};
				lista.add(linha);
			}
		}catch(Exception e) {
			throw new SelectException("Erro ao buscar dados para preencher a tabela de exemplares do livro");
		}
		return lista;
	}

	public List<Object> select_exemplares_livro_disponiveis(String id_livro) throws Exception {
		List<Object> lista = new ArrayList<Object>();
		try {
            Bson query = Filters.and(
                    Filters.eq("livro", id_livro),
                    Filters.eq("id_usuario_reserva", null),
                    Filters.ne("colecao", "Reserva"),
                    Filters.ne("colecao", "Fora de uso")
                    );

            	FindIterable<Document> exemplares = collection_exemplar.find(query);
                FindIterable<Document> emprestimos =  collection_emprestimo.find(eq("situacao", 0));
                List<String> resultList = new ArrayList<>();
                for (Document emprestimo : emprestimos) resultList.add(emprestimo.getString("id_exemplar"));

	            for(Document exemplar : exemplares) {
	            	if(!resultList.contains(String.valueOf(exemplar.getObjectId("_id")))){
	            		Object[] linha  = {exemplar.getObjectId("_id"), exemplar.getInteger("prateleira"), exemplar.getInteger("estante"), exemplar.getString("colecao")};
	    				lista.add(linha);
	            	}   	
				}
		}catch(Exception e) {
			throw new SelectException("Erro ao buscar dados para preencher a tabela de exemplares disponiveis");
		}
		return lista;
	}

	
	
}
	
