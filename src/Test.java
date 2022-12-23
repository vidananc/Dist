import java.io.FileNotFoundException;
public class Test
{
    public static void main(String[] args)
    {
        try
        {
            Dijkstra map = new Dijkstra(args[0]);
            int[] dist = map.dijkstra();
            for(int i = 2; i < dist.length; i++)
            {
                System.out.print(dist[i] + " ");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
