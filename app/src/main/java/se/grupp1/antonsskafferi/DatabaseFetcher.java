package se.grupp1.antonsskafferi;

import org.json.JSONArray;
import org.json.JSONObject;

public class DatabaseFetcher
{
    public interface Response
    {
        void processFinish();
    }

    //Ip adressen är den lokala IP-adressen till datorn, går ej att använda localhost eftersom simulatorn har egen IP
    final static private String urlString = "http://82.196.113.65:8080/dish";

    String allBookingsString;   //En sträng som visar samtliga bokningar, endast för att visa vad som funkar atm...

    public void getData(final Response delegate)
    {
        new HttpRequest(new HttpRequest.Response()
        {
            @Override
            public void processFinish(String output) {
                try
                {
                    //Parsea JSON här

                    /*JSONArray jsonArr = new JSONArray(output);

                    for(int i = 0; i < jsonArr.length(); i++)
                    {
                        JSONObject c = jsonArr.getJSONObject(i);

                        allBookingsString += "Customer:\n";
                        allBookingsString += "Customerid: " + c.getString("customerid") + "\n";
                        allBookingsString += "Date: " + c.getString("bookingdatetime") + "\n";
                        allBookingsString += "Name: " + c.getString("name") + "\n";
                        allBookingsString += "Email: " + c.getString("email") + "\n";

                        JSONObject table = c.getJSONObject("dinnertable");

                        allBookingsString += "Table: " + table.getString("dinnertableid") + "\n";
                        allBookingsString += "Descr: " + table.getString("description") + "\n";
                        allBookingsString += "sizeOfTable: " + table.getString("sizeOfTable") + "\n";
                        allBookingsString += "\n";
                    }*/

                    System.out.println(output);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                finally
                {
                    delegate.processFinish();
                }
            }
        }).execute(urlString);
    }

}

