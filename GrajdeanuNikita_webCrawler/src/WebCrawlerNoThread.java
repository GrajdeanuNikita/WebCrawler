import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebCrawlerNoThread {
    private static final int MAX_DEPTH = 3;
    private Set<String> visitedUrls = new HashSet<>();

    // Funzione principale per avviare il crawler
    public void crawl(String seedUrl) {
        crawlPage(seedUrl, 0);
    }

    // Funzione per scaricare e analizzare una pagina
    private void crawlPage(String url, int depth) {
        if (depth > MAX_DEPTH || visitedUrls.contains(url)) {
            return;  // Stop se si supera la profondità o se la URL è già stata visitata
        }

        try {
            // Aggiungi URL alla lista dei visitati
            visitedUrls.add(url);
            System.out.println("Visiting: " + url);

            // Ottieni il contenuto della pagina HTML
            Document document = Jsoup.connect(url).get();

            // Estrai i collegamenti dalla pagina
            Elements links = document.select("a[href]");

            // Stampa tutti i collegamenti trovati
            for (Element link : links) {
                String linkHref = link.attr("abs:href");// Assicurati di ottenere URL assoluti
                if( linkHref != linkHref){
                    System.out.println("Found link: " + linkHref);
                }


                // Continua a fare crawl sulla pagina collegata
                crawlPage(linkHref, depth + 1);
            }
        } catch (IOException e) {
            System.out.println("Error crawling " + url + ": " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the seed URL: ");
        String seedUrl = scanner.nextLine();
        scanner.close();

        WebCrawlerNoThread crawler = new WebCrawlerNoThread();
        crawler.crawl(seedUrl);
    }
}
