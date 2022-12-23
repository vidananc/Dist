
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Dijkstra
{
    private Dist[] heap;
    private int loc;
    private int[][] d;
    private int[] dist;
    int n;
    public Dijkstra(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        Scanner input = new Scanner(file);
        Scanner checkInput;
        int n = Integer.parseInt(input.nextLine());
        this.n = n;
        d = new int[n + 1][n + 1];
        dist = new int[n + 1];
        int i = 1, j = 1;
        while(input.hasNext())
        {
            checkInput = new Scanner(input.nextLine());
            while(checkInput.hasNext())
            {
                d[i][j] = checkInput.nextInt();
                j++;
            }
            i++;
            j = 1;
        }
        initHeap();
    }
    private void initHeap()
    {
        heap = new Dist[n - 1];
        for(int i = 2; i <= n; i++)
        {
            heap[i - 2] = new Dist(i);
            heap[i - 2].setDict(d[1][i] == -1 ? Integer.MAX_VALUE : d[1][i]);
        }
        loc = n - 2;
    }
    private void heapSort()
    {
        int currentLoc, bigLoc;
        boolean isChanged;
        Dist temp;
        for(int i = (loc + 1) / 2 - 1; i >= 0; i--)
        {
            currentLoc = i;
            isChanged = true;
            while((currentLoc + 1) * 2 - 1 <= loc && isChanged)
            {
                bigLoc = (currentLoc + 1) * 2 - 1;
                if((currentLoc + 1) * 2 <= loc)
                {
                    bigLoc = heap[(currentLoc + 1) * 2].compareTo(heap[(currentLoc + 1) * 2 - 1]) > 0 ? (currentLoc + 1) * 2 : (currentLoc + 1) * 2 - 1;
                }
                isChanged = false;
                if(heap[currentLoc].compareTo(heap[bigLoc]) < 0)
                {
                    temp = heap[currentLoc];
                    heap[currentLoc] = heap[bigLoc];
                    heap[bigLoc] = temp;
                    isChanged = true;
                    currentLoc = bigLoc;
                }
            }
        }
        temp = heap[0];
        heap[0] = heap[loc];
        heap[loc] = temp;
        loc--;
    }
    private void updateDist()
    {
        int u = heap[loc + 1].getId();
        int length = heap[loc + 1].getDist();
        int dist;
        for(int i = 0; i <= loc; i++)
        {
            dist = heap[i].getDist();
            if(d[heap[i].getId()][u] != -1 && dist > length + d[heap[i].getId()][u])
            {
                heap[i].setDict(length + d[heap[i].getId()][u]);
            }
        }
    }
    public int[] dijkstra()
    {
        for(int i = 2; i <= n; i++)
        {
            heapSort();
            dist[heap[loc + 1].getId()] = heap[loc + 1].getDist();
            updateDist();
        }
        return dist;
    }
    static class Dist implements Comparable<Dist>
    {
        private int id;
        private int dist;
        public Dist(int id)
        {
            this.id = id;
        }
        public void setDict(int dist)
        {
            this.dist = dist;
        }
        public int getId()
        {
            return id;
        }
        public int getDist()
        {
            return dist;
        }

        @Override
        public int compareTo(Dist o) {
            return o.dist - dist;//小根堆
        }
    }
}
