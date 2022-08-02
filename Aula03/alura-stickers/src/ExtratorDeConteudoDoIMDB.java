import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExtratorDeConteudoDoIMDB implements ExtratorDeConteudo {
    public List<Conteudo> extraiConteudo(String json) {

        // extrair só os dados que interessam (título, poster, classificação)
        JsonParser parser = new JsonParser();
        List<Map<String, String>> listaDeAtributos = parser.parse(json);

        List<Conteudo> conteudos = new ArrayList<>();

        // popular a lista de conteudo
        for (Map<String, String> atributos : listaDeAtributos) {
            String titulo = atributos.get("title");

            // String urlImage = getUrlImageGrande(atributos.get("image"));
            // var conteudo = new Conteudo(titulo, urlImage);
            
            var conteudo = new Conteudo(titulo, atributos.get("image"));
            conteudos.add(conteudo);
        }

        return conteudos;
    }

    private static String getUrlImageGrande(String urlImage) {

        if (urlImage.contains("@@")) {
            return urlImage.split("@@")[0] + "@@.jpg";
        } else {
            return urlImage.split("@")[0] + "@.jpg";
        }

    }

}
