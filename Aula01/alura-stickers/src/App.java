import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        // fazer uma conexão HTTP e buscar os top 250 filmes
        String url = "https://alura-filmes.herokuapp.com/conteudos";
        URI endereco = URI.create(url);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // extrair só os dados que interessam (título, poster, classificação)
        JsonParser parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);
        System.out.println(listaDeFilmes.size());
        System.out.println(listaDeFilmes.get(0));

        // exibir e manipular os dados
        int pos = 0;
        for (Map<String, String> filme : listaDeFilmes) {
            pos++;
            System.out.println("["+ pos +"]-------------------------------------------------------------------");
            System.out.println("\u001b[1mTitulo:\u001b[m\t" + filme.get("title"));
            System.out.println("\u001b[1mPoster:\u001b[m\t" + filme.get("image"));
            System.out.println("\u001b[1mNota:\u001b[m \t" + filme.get("imDbRating"));
            String nota = "";
            for (int i = 0; i < (int) Float.parseFloat(filme.get("imDbRating")); i++) {
                nota += "* ";
            }
            System.out.println("\u001b[37m\u001b[44m " + nota +" \u001b[m");
            System.out.println();

        }
        System.out.println("-------------------------------------------------------------------");
        System.out.println("\u001b[1mDigite o número do filme que deseja ver:\u001b[m");
        Scanner scanner = new Scanner(System.in);
        int numero = scanner.nextInt();
        System.out.println("--------------------------------------------");
        System.out.println("\u001b[1mTitulo:\u001b[m\t\t1" + listaDeFilmes.get(numero-1).get("title"));
        System.out.println("\u001b[1mNota Atual:\u001b[m\t" + listaDeFilmes.get(numero-1).get("imDbRating"));
        System.out.println("De a sua propia avaliação para o filme: ");
        float newNota = scanner.nextFloat();

    }
}
