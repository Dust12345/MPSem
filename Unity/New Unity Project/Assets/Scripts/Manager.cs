using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System;

public class Manager : MonoBehaviour {

    public List<GameObject> segments;   
    public List<GameObject> drones;   

    private DateTime t1;
    private double ttw = 1;

    private List<List<Vector3>> dronePoints;
    private int posIndex = 0;

	// Use this for initialization
	void Awake ()
    {
        
        string filePath = "C:/Users/Fabian/MPSem/Unity/New Unity Project/Assets/data/points.txt";
        FileParser fp = new FileParser(2);
        dronePoints = fp.getPoints(filePath);

        for (int i = 0; i < dronePoints.Count; i++)
        {
            for (int j = 0; j < dronePoints[i].Count; j++)
            {
                //Debug.Log(dronePoints[i][j]);
            }
        }
        t1 = DateTime.Now;
    }

    private void moveDrones()
    {

        if (posIndex < dronePoints.Count)
        {
            //move the drones
            for (int i = 0; i < dronePoints[posIndex].Count; i++)
            {
                drones[i].gameObject.transform.position = dronePoints[posIndex][i];

                if (posIndex > 0)
                {
                    Vector3 p1 = dronePoints[posIndex-1][i];
                    Vector3 p2 = dronePoints[posIndex][i];

                    Vector3 p3 = p2 - p1;

                    float l = p3.magnitude;

                    Vector3 center = p1 + (p3 / 2);

                    float angle = Vector3.SignedAngle(p1, p2, Vector3.up);

                    float a = Vector2.SignedAngle(new Vector2(0, 1), new Vector2(p3.x, p3.z));

                    Debug.Log("---");
                    Debug.Log(p1);
                    Debug.Log(p2);
                    Debug.Log(l);
                    Debug.Log(center);
                    Debug.Log(a);
                    Debug.Log("---");

                    GameObject inst = GameObject.Instantiate(segments[i]);
                    inst.transform.position = center;
                    inst.transform.localScale += new Vector3(0, l-1, 0);
                    inst.transform.Rotate(new Vector3(0, 0, a));

                    
                }


            }
            posIndex++;
        }
      
    }

	// Update is called once per frame
	void Update ()
    {
        DateTime t2 = DateTime.Now;
        double diffInSeconds = (t1 - t2).TotalSeconds;

        //if (diffInSeconds >= ttw)
        {
            moveDrones();
        }

	}
}
