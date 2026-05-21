package lap_1.university;
import java.util.ArrayList;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class teacher implements Serializable {
    int id;
    String name;
    String departement;

    static private String url = "jdbc:mysql://localhost:3306/university";
    static private String user = "root";
    static private String password = "";
    
    teacher(int id,String name ,String departement){
        this.id = id;
        this.name = name;
        this.departement = departement;
    }
    public static void addteacher(ArrayList<teacher> t1){
    try{
        FileOutputStream fot = new FileOutputStream("teacher.ser");
        ObjectOutputStream oot = new ObjectOutputStream(fot);
        oot.writeObject(t1);
        oot.close();
        fot.close();

    }catch(IOException e){
        e.printStackTrace();    }
    }
    public static void showteacher(){
        try{
            FileInputStream fos = new FileInputStream("teacher.ser");
            ObjectInputStream val1 = new ObjectInputStream(fos);
            @SuppressWarnings("unchecked")

            ArrayList<teacher> teachers = (ArrayList<teacher>) val1.readObject();
            System.out.println("######### List of teacher #########");
            System.out.println("ID"+" "+"Name" +" "+ "depatement");
            for (teacher teacher:teachers){
                System.out.println(teacher.id + " " + teacher.name + " " + teacher.departement);   
            }
            val1.close();
            fos.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        }

    public static void addtodatabase(teacher t1){
        String query  = "INSERT INTO  teachers(id,name,departement) VALUES(?,?,?) ";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url,user,password);
            PreparedStatement prepare = conn.prepareStatement(query);
            prepare.setInt(1,t1.id);
            prepare.setString(2,t1.name);
            prepare.setString(3,t1.departement);
            prepare.executeUpdate();  
            conn.close();  
        }catch(Exception e){
            e.printStackTrace();          
        }    
    }



    public static void showfromdatabase(){
        String query = "SELECT * FROM teachers";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url,user,password);
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(query);

            System.out.println("ID" + " " + "name " + " " + "department");
            while (result.next()){
                int id = result.getInt("id");
                String name = result.getString("name");
                String departement = result.getString("departement");
                System.out.println(id +" " + name + " " + departement);
            }
            conn.close();
        }catch(Exception e){
            e.printStackTrace();  
        }
    }


public static void main(String[] arg){
    
    teacher t1 = new teacher(1,"Abebe","SWE");
    teacher t2 = new teacher(2,"Kebede","SWE");
    ArrayList<teacher> coll = new ArrayList<>();
    coll.add(t1);
    coll.add(t2);

    addteacher(coll);
    showteacher();
    addtodatabase(t1);
    addtodatabase(t2);   
    showfromdatabase();
    
}
}
