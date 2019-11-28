package se.grupp1.antonsskafferi;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequest extends AsyncTask<String, Integer, String>
{
    public interface Response
    {
        void processFinish(String output);
    }


    public Response delegate = null;

    public HttpRequest(Response delegate)
    {
        this.delegate = delegate;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    protected String doInBackground(String... urls) {

        String content = "", line;


        try
        {
            URL url = new URL(urls[0]);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            int i = 0;

            while(connection.getResponseCode() != 200 && i++ != 10)
            {
                connection = (HttpURLConnection) url.openConnection();
                System.out.println("Failed to connect, trying again");
            }

            try
            {
                BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                while ((line = rd.readLine()) != null)
                {
                    content += line + "\n";
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


        return content;
    }

    protected void onProgressUpdate(Integer... progress) {
    }

    @Override
    protected void onPostExecute(String result)
    {
        //System.out.println("got response");
        delegate.processFinish(result);
    }
}
