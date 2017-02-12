
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Shriram
 */
public class KNNClassifier {
    ArrayList<Integer> vectorArray;
    ArrayList<String> formVectors;
    KNNClassifier()
    {
        //vectorArray=vector;
        formVectors=new ArrayList<String>();
    }
    ArrayList<String> classify(HashMap<String,ArrayList<Integer>> Vectors,int k)
    {
        int dis=0;
         Iterator it =   Vectors.entrySet().iterator();
        HashMap<String,Integer> distForm=new HashMap<String,Integer>();
        //int dis;
        while (it.hasNext()) 
        {
            Map.Entry pair1 = (Map.Entry)it.next();
            distForm.put((String) pair1.getKey(), 0);
        }
        List<String> list=new ArrayList<String>(Vectors.keySet());
        for(int i=0;i<list.size()-1;i++)
        {
            for(int j=i+1;j<list.size();j++)
            {
      
                    dis=findDistance(Vectors.get(list.get(i)),Vectors.get(list.get(j)));
                    distForm.put( list.get(i), dis + distForm.get(list.get(i)));
                    distForm.put( list.get(j), dis + distForm.get(list.get(j)));
      
            }
            System.out.println(list.get(i) + "   " + Vectors.get(list.get(i)));
        }
        
        
                System.out.println();
                System.out.println("<  =============================================================     >");
        System.out.println("Required deep web query forms");
      
        distForm=(HashMap<String, Integer>) sortByValue(distForm);
        Iterator distIt =   distForm.entrySet().iterator();
        int i=0;
        while(distIt.hasNext() && i<k)
        {
              Map.Entry pair = (Map.Entry)distIt.next();
              formVectors.add((String) pair.getKey());
           //   System.out.println((String) pair.getKey()+ " -> " + pair.getValue());
              
              i++;
        }
          
        return formVectors;
    }
    int findDistance(ArrayList<Integer> v1,ArrayList<Integer> v2)
    {
        int e1,e2,dis=0,ind=0;
        while(ind<4)
        {
            e1=v1.get(ind);
            e2=v2.get(ind);
            dis=dis+((e2-e1)*(e2-e1));
            ind++;
        }
        dis=(int) Math.sqrt(dis);
        return dis;
    }
    public static <K, V extends Comparable<? super V>> Map<K, V> 
        sortByValue( Map<K, V> map )
    {
        List<Map.Entry<K, V>> list =
            new LinkedList<Map.Entry<K, V>>( map.entrySet() );
        Collections.sort( list, new Comparator<Map.Entry<K, V>>()
        {
            public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
            {
                return (o1.getValue()).compareTo( o2.getValue() );
            }
        } );

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list)
        {
            result.put( entry.getKey(), entry.getValue() );
        }
        return result;
    }
            
}
