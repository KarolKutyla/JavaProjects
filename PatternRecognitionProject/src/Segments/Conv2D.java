package Segments;

import java.math.BigDecimal;

public class Conv2D {

    double rest;
    public Conv2D(int[][] arr) throws Exception
    {
        if(arr.length != 15 || arr[0].length != 15)
        {
            throw new Exception();
        }
        double rest = 0d;
        for(int i = 0; i < arr.length; i++)
        {
            for(int j = 0; j < arr.length; j++)
            {
                if(arr[i][j] != -1)
                rest+=1;
            }
        }
        rest/=(15*15);
        this.rest = rest;
    }
}
