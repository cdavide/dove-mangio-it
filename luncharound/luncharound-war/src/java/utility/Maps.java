/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

/**
 *
 * @author Bovio Lorenzo, Bronzino Francesco e Concas Davide
 */
public class Maps {
    
    public static boolean geolocalizza(String indirizzo, double[] punto){
            
        JSONObject risposta,latlng;

        String url="http://maps.googleapis.com/maps/api/geocode/json?address=";
        url+=indirizzo.replaceAll(" ", "+");
        url+="&sensor=false";

        try {
            risposta=JSONReader.readJsonFromUrl(url);

            if(risposta.getString("status").equals("OK")){

                latlng=risposta.getJSONArray("results").getJSONObject(0).
                        getJSONObject("geometry").getJSONObject("location");
                punto[0] = latlng.getDouble("lat");
                punto[1] = latlng.getDouble("lng");   
            }
            else{
                return false;
            }
        }
        catch(Exception e){
            System.err.println(e.toString());
            return false;
        }
        return true;
    }

}
