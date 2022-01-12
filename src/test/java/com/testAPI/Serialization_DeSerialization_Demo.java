package com.testAPI;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

class Test implements Serializable 

{
	
	int i =10;
	int j =20;
	
}

public class Serialization_DeSerialization_Demo  {

	public static void main(String[] args) throws IOException, ClassNotFoundException {

         Test t1 = new Test();
         
         //Achieving the Serialization using FileOuputStream and ObjectOutputStream
         
         FileOutputStream fos = new FileOutputStream("test.txt");
         ObjectOutputStream oos = new ObjectOutputStream(fos);
         
         oos.writeObject(t1);
        

         //Achieving De-Serialization using FileInputStream and ObjectInputStream
         
         FileInputStream fis = new FileInputStream("test.txt");
         ObjectInputStream ois = new ObjectInputStream(fis);
         
         Test t2 = (Test) ois.readObject();
         System.out.println("i value is: "+t2.i);
         System.out.println("j value is: "+t2.j);
	}

}
