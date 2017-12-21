using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System.IO;
using System;

public class FileParser
{
    float defaultHeigth;

    public FileParser(float defaultHeigth)
    {
        this.defaultHeigth = defaultHeigth;
    }

    private List<Vector3> parseLine(string line)
    {
        List<Vector3> points = new List<Vector3>();
        String[] substrings = line.Split(';');
       for(int i=0;i< substrings.Length; i=i+2)
        {
            float value = float.Parse(substrings[i]);
            float value1 = float.Parse(substrings[i+1]);
            points.Add(new Vector3(value, defaultHeigth, value1));
        }

        return points;
    }


    public List<List<Vector3>> getPoints(string filePath)
    {
        List<List<Vector3>> points = new List<List<Vector3>>();


        var lines = File.ReadAllLines(filePath);
        foreach (var line in lines)
        {         

            if (line == null)
            {
                return points;
            }

            List<Vector3> lineValues = parseLine(line);
            points.Add(lineValues);
        }
        return points;
    }

}
