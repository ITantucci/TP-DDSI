package Agregador.business.Solicitudes;
import java.net.http.*;
import java.net.URI;

public class DetectorDeSpam {
    private static final String API_KEY = "401e1369cc60";
    //TODO meterle URL de la pagina posta
    private static final String BLOG_URL = "https://tusitio.com";

    static boolean esSpam(String texto) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://" + API_KEY + ".rest.akismet.com/1.1/comment-check"))
            .header("Content-Type", "application/x-www-form-urlencoded")
            .POST(HttpRequest.BodyPublishers.ofString(
                "blog=" + BLOG_URL +
                    "&comment_content=" + texto
                //TODO ver si mandamos mas parametros a la API, cuales y como conseguirlos
//                    +
//                    "&comment_author=Spammer" +
//                    "&comment_author_email=spammer@example.com"
            ))
            .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return Boolean.getBoolean(response.body());
    }
}