package Segments;

import PerceptronPackage.Perceptron;

import java.io.IOException;
import java.util.LinkedList;

public class SignSegment extends Perceptron {
    String name;
    int staticIndex = 0;
    int index = staticIndex++;


    public SignSegment(String name)
    {
        super(name, 32);
    }

}
