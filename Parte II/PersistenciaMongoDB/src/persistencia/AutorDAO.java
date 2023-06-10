package persistencia;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;
import org.bson.Document;
import org.bson.types.ObjectId;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import dados.Autor;
import exceptions.InsertException;
import exceptions.SelectException;
import exceptions.UpdateException;

public class AutorDAO {
	private static AutorDAO instance = null;
	private static MongoCollection<Document> collection_livro;
	private static MongoCollection<Document> collection_autor;

	private static MongoDatabase connection;

	public static AutorDAO getInstance() throws Exception {
		if (instance == null)  instance = new AutorDAO();
		return instance;
	}
	
	private AutorDAO() throws  Exception {
		connection = Conexao.getConexao();
		try {
			collection_livro = connection.getCollection("livro");
			collection_autor = connection.getCollection("autor");
		} catch (Exception e) {
			throw new SelectException("Erro ao conectar a coleção funcionario");
		}
	}
	
	public void insert_autor(Autor autor) throws Exception{
			try {
				Document document = new Document(
						"nacionalidade", autor.getNacionalidade())
						.append("area", autor.getArea())
						.append("nome", autor.getNome());
				collection_autor.insertOne(document);
			}catch (Exception e) {
				throw new InsertException("Erro ao inserir autor.");
			}		
	}
	
	public void update_autor(Autor autor) throws Exception{
		ObjectId id = new ObjectId(autor.getId());
			try {
				collection_autor.updateOne(eq("_id", id), combine(
						 set("nacionalidade", autor.getNacionalidade()),
					 	 set("area", autor.getArea()),
					     set("nome", autor.getNome()))
		                );
		}catch (Exception e) {
			throw new UpdateException("Erro ao atualizar autor.");
		}
	}
	
}