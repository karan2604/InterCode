package sample;

import javafx.fxml.FXML;

import java.io.*;
import java.net.ProtocolException;
import java.util.Scanner;

public class Testing {

    File file=null;
    void Store(String s)
    {
        file=new File("InterCode.java");
        try {
            FileWriter fileWriter=new FileWriter(file);
            fileWriter.write(s);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        if(file!=null)
        {
            try {
                Process p=Runtime.getRuntime().exec("cmd /c start cmd.exe /k \"javac InterCode.java && java InterCode > output.txt\"");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        /*
        Scanner sc= null;
        try {
            sc = new Scanner(new FileReader("output.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int n=sc.nextInt();
        int a[]=new int[n];
        for(int i=0;i<n;i++)
            a[i]=sc.nextInt();

        int ans=0;
        for(int i=0;i<n;i++) {
            System.out.println(a[i]);
            ans+=a[i];
        }
        System.out.println("ans="+ans);
        */

    }
}
