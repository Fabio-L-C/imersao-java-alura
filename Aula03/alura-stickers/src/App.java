import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        // fazer uma conexão HTTP e buscar os top 250 filmes

        String url = "https://mocki.io/v1/9a7c1ca9-29b4-4eb3-8306-1adb9d159060";
        ExtratorDeConteudo extrator = new ExtratorDeConteudoDoIMDB();

        // String url = "https://api.nasa.gov/planetary/apod?api_key=ua6PtTLkk9TJGYFBLQFwPuaYQVJJWeUDb5HiKwxA&start_date=2022-07-29&end_date=2022-07-31";
        // ExtratorDeConteudo extrator = new ExtratorDeConteudoDaNasa();

        var http = new ClienteHttp();
        String json = http.buscarDados(url);

        // exibir e manipular os dados
        List<Conteudo> conteudos = extrator.extraiConteudo(json);

        var geradora = new GeradoraDeFigurinhas();

        for (int i = 0; i < 3; i++) {

            Conteudo conteudo = conteudos.get(i);

            InputStream inputStream = new URL(conteudo.getUrlImage()).openStream();
            String nomeArquivo = "saida/" + conteudo.getTitulo() + ".png";

            geradora.cria(inputStream, nomeArquivo);
        }
    }

}
