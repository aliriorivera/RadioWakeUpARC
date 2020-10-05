package dev.alirio.radiowakeuparc.restServices;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.net.InetAddress;
import java.util.Random;
import java.util.Vector;

/**
 * author: Alirio Rivera
 * RadioDNSResolver  Do a DNS-lookup of 'all.api.radio-browser.info'. This gives you a list of all
 * available servers from the Radio API streaming service.
 * This Resolver complies with the streaming radio API rules.
 */
public class RadioDNSResolver extends AsyncTask<Void, Void, String[]> {

    private static String[] radioDNSServersList = null;
    private static String radioServer = null;
    private String dnsHost = "all.api.radio-browser.info";

    public RadioDNSResolver(){}

    @Override
    protected String[] doInBackground(Void... voids) {
        Vector<String> listResult = new Vector<String>();
        try {
            // add all round robin servers one by one to select them separately
            InetAddress[] dnsHosts = InetAddress.getAllByName(dnsHost);
            for (InetAddress item : dnsHosts) {
                listResult.add(item.getCanonicalHostName());
            }
            radioDNSServersList = listResult.toArray(new String[0]);
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            radioDNSServersList = null;
            Toast.makeText(null, "Error Getting Radio Servers from API service. Please restart the app.",
                    Toast.LENGTH_LONG).show();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String[] result) {
        super.onPostExecute(result);

        // randomize the list of servers gotten from the dns resolver
        if (radioDNSServersList != null && radioDNSServersList.length > 0){
            Random rand = new Random();
            int dnsRadioServer = rand.nextInt(radioDNSServersList.length);
            radioServer = radioDNSServersList[dnsRadioServer];
            return ;
        }
        Toast.makeText(null, "Error Getting single DNS Radio Server to get Radio Streaming services. Please restart the app.",
                Toast.LENGTH_LONG).show();
    }

    public static String getRadioServer(){
        if (radioServer != null) {
            return "https://" + radioServer;
        }
        return radioServer;
    }
}
