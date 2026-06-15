import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class P1{
    public static void main(String[] args) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("dados.dat"));
        Object listaDePedidos = null;
        oos.writeObject(listaDePedidos);
        oos.close();
    }
}