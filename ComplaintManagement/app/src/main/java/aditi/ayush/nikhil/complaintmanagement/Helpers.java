package aditi.ayush.nikhil.complaintmanagement;

/**
 * Created by jagdish on 3/28/2016.
 */
public class Helpers
{

    public String SpaceToScore(String s)
    {
        String[] s_Arr = s.split(" ");
        int x = s_Arr.length;
        String ans = "";
        for (int i = 0; i < (x - 1); i ++)
        {
            ans += s_Arr[i] + "_";
        }
        ans += s_Arr[x-1];
        return ans;
    }

    public String ScoreToSpace(String s)
    {
        String[] s_Arr2 = s.split("_");
        int y = s_Arr2.length;
        String ans2 = "";
        for (int j = 0; j < (y-1); j ++)
        {
            ans2 += s_Arr2[j] + " ";
        }
        ans2 += s_Arr2[y-1];
        return ans2;
    }
}
