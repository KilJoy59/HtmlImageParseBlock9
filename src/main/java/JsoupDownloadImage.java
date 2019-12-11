import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

public class JsoupDownloadImage {
    private static String IMAGE_SAVE_PATH = "src/data";

    public static void main(String[] args) {
        String strURL = "https://lenta.ru/";
        try {
            Document document = Jsoup
                    .connect(strURL)
                    .userAgent("Mozilla/5.0")
                    .timeout(10 * 1000)
                    .get();
            Elements imageElements = document.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
            for (Element imageElement : imageElements) {
                String strImageURL = imageElement.attr("abs:src");
                downloadImage(strImageURL);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void downloadImage (String strImageURL) {
        System.out.println(strImageURL);
        String strImageName = strImageURL.substring(strImageURL.lastIndexOf("/") + 1);
        System.out.println(strImageName);

        try {
            URL urlImage = new URL(strImageURL);
            InputStream in = urlImage.openStream();

            byte[] buffer = new byte[4096];
            int n = -1;

            OutputStream os = new FileOutputStream(IMAGE_SAVE_PATH + "/" + strImageName );
            while ((n = in.read(buffer)) != -1) {
                os.write(buffer,0, n);
            }
            os.close();
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
