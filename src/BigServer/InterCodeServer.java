package BigServer;

import CommonFiles_With_BigServerAndClients.Message;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class InterCodeServer {

    static Process p;
    static String line="";

    public static void storeinput(String input)
    {
        File file=new File("input.txt");
        File fileerror=new File(("error.txt"));
        File fileoutput=new File("output.txt");
        try {
            FileWriter fileWriter = new FileWriter(file);  //writing the input into input.txt file
            fileWriter.write(input);
            fileWriter.close();

            fileWriter=new FileWriter(fileerror);       //writing the absurd val in the error.txt file
            fileWriter.write("");
            fileWriter.close();

            fileWriter=new FileWriter(fileoutput);      //writing the absurd val in the output.txt file
            fileWriter.write("");
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void deletefiles()
    {
        //deleting a java class file
        File javaclass=new File("InterCode.class");
        if(javaclass.exists())
            javaclass.delete();
        File cppclass=new File("a.exe");
        if(cppclass.exists())
            cppclass.delete();
    }

    public static void checkCompile(String codetxtarea,String lang)
    {
        if(lang.equals("Java"))
            CompileJava(codetxtarea);
        else if(lang.equals("C++"))
            CompileCpp(codetxtarea);
        else if(lang.equals("C"))
            CompileC(codetxtarea);
    }

    private static void CompileJava(String codetxtarea)
    {
        line="";
        File file=new File("InterCode.java");
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(codetxtarea);
            fileWriter.close();
            p=Runtime.getRuntime().exec("cmd /c start cmd.exe /k \"javac InterCode.java 2> error.txt\"");

            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                            Thread.sleep(5000);
                            File javaclass=new File("InterCode.class");
                            if(!javaclass.exists()) {
                                while (true) {
                                    line = readFilesasString("error.txt");
                                    if (!line.isEmpty()) {
                                        break;
                                    }
                                }
                            }
                        }
                    catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //System.out.println(line);
                    writetoclient(line);  //Messaging back to client
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void CompileCpp(String codetxtarea)
    {
        line="";
        File file=new File("InterCodeCplus.cpp");
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(codetxtarea);
            fileWriter.close();
            p=Runtime.getRuntime().exec("cmd /c start cmd.exe /k \"g++ InterCodeCplus.cpp 2> error.txt\"");

            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        Thread.sleep(5000);
                        File cppclass=new File("a.exe");
                        if(!cppclass.exists()) {
                            while (true) {
                                line = readFilesasString("error.txt");
                                if (!line.isEmpty()) {
                                    break;
                                }
                            }
                        }
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //System.out.println(line);
                    writetoclient(line);  //Messaging back to client
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void CompileC(String codetxtarea)
    {
        line="";
        File file=new File("InterCodeC.cpp");
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(codetxtarea);
            fileWriter.close();
            p=Runtime.getRuntime().exec("cmd /c start cmd.exe /k \"gcc InterCodeC.cpp 2> error.txt\"");

            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        Thread.sleep(4000);
                        File cclass=new File("a.exe");
                        if(!cclass.exists()) {
                            while (true) {
                                line = readFilesasString("error.txt");
                                if (!line.isEmpty()) {
                                    break;
                                }
                            }
                        }
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //System.out.println(line);
                    writetoclient(line);  //Messaging back to client
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void checkrun(String lang)
    {
        if(lang.equals("Java"))
            RunJava();
        else if(lang.equals("C++"))
            RunCpp();
        else if(lang.equals("C"))
            RunC();
    }

    public static void RunJava()
    {
        line="";
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    p=Runtime.getRuntime().exec("cmd /c start cmd.exe /k \"java InterCode < input.txt > output.txt\"");
                    Thread.sleep(2000);
                    while (true) {
                        line = readFilesasString("output.txt");
                        if (!line.isEmpty()) {
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                writetoclient(line);  //Messaging back to client
            }
        }).start();
    }

    public static void RunCpp()
    {
        line="";
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    p=Runtime.getRuntime().exec("cmd /c start cmd.exe /k \"a.exe < input.txt > output.txt\"");
                    Thread.sleep(2000);
                    while (true) {
                        line = readFilesasString("output.txt");
                        if (!line.isEmpty()) {
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                writetoclient(line);  //Messaging back to client
            }
        }).start();
    }

    public static void RunC()
    {
        line="";
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    p=Runtime.getRuntime().exec("cmd /c start cmd.exe /k \"a.exe < input.txt > output.txt\"");
                    Thread.sleep(2000);
                    while (true) {
                        line = readFilesasString("output.txt");
                        if (!line.isEmpty()) {
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                writetoclient(line);  //Messaging back to client
            }
        }).start();
    }

    private static void writetoclient(String line)
    {
        for(ClientHandle clientHandle:ClientDetails.getClisntlist()) {
            Message m = new Message();
            m.setLang("Java");
            m.setOption("compile");
            if (line.isEmpty())
                m.setContent("Compilation Sucessfull");
            else
                m.setContent(line);

            //ObjectOutputStream out = clientHandle.getOut();
            //System.out.println(clientHandle.getOut());
            try {
               clientHandle.getOut().writeObject(m);
               clientHandle.getOut().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String readFilesasString(String filename) throws IOException {
        String data="";
        data=Files.readString(Path.of(filename));
        //System.out.println("read+="+data);
        return data;
    }
}
