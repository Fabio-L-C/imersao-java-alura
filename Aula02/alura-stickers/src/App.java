import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {
        // fazer uma conexão HTTP e buscar os top 250 filmes
        // String url = "https://api.mocki.io/v2/549a5d8b/Top250Movies";
        String url = "https://mocki.io/v1/9a7c1ca9-29b4-4eb3-8306-1adb9d159060";
        URI endereco = URI.create(url);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // extrair só os dados que interessam (título, poster, classificação)
        JsonParser parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        GeradoraDeFigurinhas geradora = new GeradoraDeFigurinhas();
        for (Map<String, String> filme : listaDeFilmes) {

            String urlImage = filme.get("image");
            String urlImageGrande = getUrlImageGrande(urlImage);
            
            String titulo = filme.get("title");

            InputStream inputStream = new URL(urlImageGrande).openStream();
            String nomeArquivo = "saida/" + titulo + ".png";

            geradora.cria(inputStream, nomeArquivo);
        }
    }

    public static String getUrlImageGrande(String urlImage) {

        if (urlImage.contains("@@")) {
            return urlImage.split("@@")[0] + "@@.jpg";
        } else {
            return urlImage.split("@")[0] + "@.jpg";
        }

    }
}
