
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class WordGenerator {
    public static String generateWord(){
        String str = "";
        File f = new File("Words.txt");
        try(Scanner sc = new Scanner(f)){
            int line = (int) ((Math.random()) * 100);
            for(int i = 0 ; i < line ; i++){
                str = sc.next();
            }
            sc.close();
        }catch(FileNotFoundException e){
            System.out.print(e.getMessage());
        }
        return str;
    }
}
