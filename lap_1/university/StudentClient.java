package lap_1.university;

import java.rmi.Naming;
import java.util.ArrayList;

public class StudentClient {
    public static void main(String[] args) {
        try {
            MyRemoteInterface stub = (MyRemoteInterface) Naming.lookup("rmi://localhost/student");

            student s1 = new student(101, "Abebe", "SWE", "D", 2026);
            
            stub.addtoDb(s1);
            stub.showStudenttoDB();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}package lap_1.university;

import java.rmi.Naming;
import java.util.ArrayList;

public class StudentClient {
    public static void main(String[] args) {
        try {
            MyRemoteInterface stub = (MyRemoteInterface) Naming.lookup("rmi://localhost/student");

            student s1 = new student(101, "Abebe", "SWE", "D", 2026);
            
            stub.addtoDb(s1);
            stub.showStudenttoDB();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}