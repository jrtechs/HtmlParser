package net.jrtechs.www;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Class which retrieves contents of a website as html
 *
 * @author Jeffery Russell 5-24-17
 */
public class WebScraper
{
    /**
     * Grabs the contents of a website as a string
     *
     * @param link to open
     * @return source code of website as a single string
     */
    public static String getWebsite(String link)
    {
        try
        {
            URL url = new URL(link);

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(url.openStream())
            );
            return WebScraper.getBufferedReaderData(br);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * Gets contents of a post request sent to a web server
     *
     * @param link to open
     * @param postData to send
     * @return source code of website as a string
     */
    public static String getPostResponse(String link, String postData)
    {
        try
        {
            URL url = new URL(link);
            HttpURLConnection conn = (HttpsURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");

            OutputStream os = conn.getOutputStream();
            os.write(postData.getBytes());
            os.flush();

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );
            return WebScraper.getBufferedReaderData(br);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        return "";
    }


    /**
     * Helper method for getPostResponse() and getWebsite()
     * Slams all contents of a buffered reader into a single string.
     *
     * @param br
     * @return contents of buffered reader
     * @throws IOException -- with br.readLine()
     */
    private static String getBufferedReaderData(BufferedReader br)
            throws IOException
    {
        String html = "";
        String line;
        while((line = br.readLine()) !=null)
        {
            html += line;
        }
        return html;
    }


}
