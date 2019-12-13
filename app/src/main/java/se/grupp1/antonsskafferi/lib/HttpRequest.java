package se.grupp1.antonsskafferi.lib;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * En klass för att skicka en HttpRequest till en API. Klassen använder sig av AsyncTask för att
 * requesten ska ske på en egen tråd i bakgrunden.
 */
public class HttpRequest extends AsyncTask<String, Integer, String>
{
    public static Object Response;

    /**
     * Ett interface för att skicka svaret från HttpRequesten till mainthread.
     * En instans som implementerar interfacets funktion skickas in i klassens konstruktor.
     */
    public interface Response
    {
        /**
         * Denna funktion körs när API-requesten har skickats och ett svar har mottagits och behandlats.
         *
         * @param output En sträng med det svar som mottogs från APIn.
         * @param status Statuskoden för requesten
         */
        void processFinish(String output, int status) throws JSONException;
    }

    private Response delegate;
    private String requestMethod;
    private String payload = "";
    private boolean isPost;
    private int status;


    /**
     * @param delegate En implementerad instans av interfacet Response
     */
    public HttpRequest(Response delegate)
    {
        this.delegate = delegate;
    }

    /**
     * Sätt vilken request metod som ska användas, kan vara t.ex GET, POST, DELETE, mfl.
     *
     * @param requestMethod Reuquestmetoden som ska användas.
     */
    public void setRequestMethod(String requestMethod)
    {
        this.requestMethod = requestMethod.toUpperCase();

        isPost = requestMethod.toUpperCase().equals("POST");
    }

    /**
     * Sätt data som ska skickas till APIn, används framförallt vid en POST-request
     *
     * @param payload Den data som ska skickas.
     */
    public void setPayload(String payload)
    {
        this.payload = payload;
    }

    /**
     * Denna metod körs när <code>httpRequest.execute(...);</code> körs och sker sedan i
     * bakgrunden på en egen tråd.
     *
     * @param urls URLn till APIn.
     * @return En sträng med resultatet från requesten.
     */
    @Override
    protected String doInBackground(String... urls) {
        StringBuilder content = new StringBuilder();
        String line;

        try
        {
            URL url = new URL(urls[0]);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod(requestMethod);

            if(isPost) {
                System.out.println("POSTING DATA");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

                //TODO: Check to make sure this row is not important
                //connection.setRequestProperty("Content-Length", String.valueOf(payload.length()));

                connection.setRequestProperty("Accept", "application/json");
                connection.setDoOutput(true);
                connection.setDoInput(true);

                try {
                    OutputStream os = connection.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, Charset.forName("UTF-8")));
                    writer.write(payload);
                    writer.flush();
                    writer.close();
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            int attempts = 0;

            while(connection.getResponseCode() != 200 && attempts++ != 10)
            {
                connection = (HttpURLConnection) url.openConnection();
                System.out.println("Failed to connect, trying again. Recieved error code: " + connection.getResponseCode());
            }

            status = connection.getResponseCode();

            try
            {
                BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                while ((line = rd.readLine()) != null)
                {
                    content.append(line).append("\n");
                }

                rd.close();

            }catch(Exception ex)
            {
                ex.printStackTrace();
                System.out.println("Failed to get input stream");
            }
            finally
            {
                connection.disconnect();
            }
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }


        return content.toString();
    }

    protected void onProgressUpdate(Integer... progress)
    {

    }

    /**
     * När <code> doInBackground(...) </code> är klar och har fått svar från APIn, körs denna metod.
     * Kör då <code> processFinish(...)</code> i responsinterfacet.
     *
     * @param result resultatet från <code> doInBackground(...) </code>
     */
    @Override
    protected void onPostExecute(String result)
    {
        if(delegate == null) {
            return;
        }
        //System.out.println("got response");
        try {
            delegate.processFinish(result, status);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
