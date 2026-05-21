package lap_1.university;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;

interface MyRemoteInterface extends Remote {
    void addStudent(ArrayList<student> var0) throws RemoteException;
    void showStudent() throws RemoteException;
    void addtoDb(student var0) throws RemoteException;
    void showStudenttoDB() throws RemoteException;
}

public class student extends UnicastRemoteObject implements MyRemoteInterface, Serializable {
    int id;
    String name;
    String department;
    String section;
    int year;

    private static String url = "jdbc:mysql://localhost:3306/university";
    private static String user = "root";
    private static String password = "";

    public student(int var1, String var2, String var3, String var4, int var5) throws RemoteException {
        this.id = var1;
        this.name = var2;
        this.department = var3;
        this.section = var4;
        this.year = var5;
    }

    public student() throws RemoteException {
        super();
    }

    @Override
    public void addStudent(ArrayList<student> var0) throws RemoteException {
        try {
            FileOutputStream var1 = new FileOutputStream("student.ser");
            ObjectOutputStream var2 = new ObjectOutputStream(var1);
            var2.writeObject(var0);
            var1.close();
            var2.close();
        } catch (Exception var3) {
            System.out.println(var3.getMessage());
        }
    }

    @Override
    public void showStudent() throws RemoteException {
        try {
            FileInputStream var0 = new FileInputStream("student.ser");
            ObjectInputStream var1 = new ObjectInputStream(var0);
            ArrayList<student> var2 = (ArrayList<student>) var1.readObject();
            for (student var4 : var2) {
                System.out.println(var4.id + " " + var4.name);
            }
            var0.close();
            var1.close();
        } catch (Exception var5) {
            System.out.println(var5.getMessage());
        }
    }

    @Override
    public void addtoDb(student var0) throws RemoteException {
        String var1 = "INSERT INTO student(id,name,departement,section,year) VALUES(?,?,?,?,?)";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection var2 = DriverManager.getConnection(url, user, password);
            PreparedStatement var3 = var2.prepareStatement(var1);
            var3.setInt(1, var0.id);
            var3.setString(2, var0.name);
            var3.setString(3, var0.department);
            var3.setString(4, var0.section);
            var3.setInt(5, var0.year);
            var3.executeUpdate();
            var2.close();
        } catch (Exception var4) {
            System.out.println(var4.getMessage());
        }
    }

    @Override
    public void showStudenttoDB() throws RemoteException {
        String var0 = "SELECT * FROM student";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection var1 = DriverManager.getConnection(url, user, password);
            Statement var2 = var1.createStatement();
            ResultSet var3 = var2.executeQuery(var0);
            while (var3.next()) {
                System.out.println(var3.getInt("id") + " " + var3.getString("name"));
            }
            var1.close();
        } catch (Exception var7) {
            System.out.println(var7.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            student remoteObject = new student();
            Naming.rebind("rmi://localhost/student", remoteObject);
            System.out.println("Student RMI Server is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}