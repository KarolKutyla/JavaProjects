package Segments;

import PerceptronPackage.Perceptron;

public class DataSegment extends Perceptron {

    public DataSegment(String name, int[][] arr)
    {
        super(name, 10);
        for(int i = 0; i < arr.length; i++)
        {
            int sum = 0;
            int sign = arr[i][0];
            int numberOfSpaces = 0;
            for(int j = 0; j < arr[i].length; j++)
            {
//                if()
            }
        }
    }
}
