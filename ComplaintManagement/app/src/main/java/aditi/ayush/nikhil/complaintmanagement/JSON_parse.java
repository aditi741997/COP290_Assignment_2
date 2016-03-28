package aditi.ayush.nikhil.complaintmanagement;

/**
 * Created by Nikhil on 26/03/16.
 */
import org.json.JSONObject;

public class JSON_parse
{
    public static boolean getObject (String response)
    {try{
        JSONObject obj=new JSONObject(response) ;
        String success = obj.getString("success");
        if(success.equals("true"))
        {
            return true;
        }

    }catch (org.json.JSONException e)
    {
        e.printStackTrace();
    }
        return false;
    }

    
}
