package se.grupp1.antonsskafferi;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequest extends AsyncTask<String, Integer, String>
{
    public interface Response
    {
        void processFinish(String output);
    }


    public Response delegate = null;

    private String requestMethod;
    private String payload;
    private boolean isPost;

    public HttpRequest(Response delegate)
    {
        this.delegate = delegate;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
        isPost = requestMethod.toUpperCase().equals("POST");
    }

    public void setPayload(String payload) {
        this.payload = payload;
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

            connection.setRequestMethod(requestMethod);
            if(isPost) {
                System.out.println("POSTING DATA");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                connection.setRequestProperty("Content-Length", String.valueOf(payload.length()));
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoOutput(true);

                try {
                    OutputStream os = connection.getOutputStream();
                    byte[] input = payload.getBytes("utf-8");
                    os.write(input, 0, input.length);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            int attempts = 0;

            while(connection.getResponseCode() != 200 && attempts++ != 10)
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

    protected void onProgressUpdate(Integer... progress)
    {

    }

    @Override
    protected void onPostExecute(String result)
    {
        //System.out.println("got response");
        delegate.processFinish(result);
    }
}
