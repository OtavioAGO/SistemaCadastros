import java.io.*;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opc = sc.nextInt();
        try(BufferedReader br = new BufferedReader(new FileReader("src\\formularios\\formulario.txt"))){
            String line;
            while ((line = br.readLine()) != null){
                System.out.println(line);
            }
            String nome = sc.nextLine();
            String email = sc.nextLine();
            int idade = sc.nextInt();
            double altura = sc.nextDouble();
            Pessoa pessoa = new Pessoa(nome, email, idade, altura);
            int n = 1;
            String nomeArquivo = n+"-"+pessoa.getNome().toUpperCase()+".txt";
            File arquivo = new File("src\\formularios\\"+nomeArquivo);
            try(BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))){
                while (arquivo.exists()){
                    nomeArquivo = ++n+"-"+pessoa.getNome().toUpperCase()+".txt";
                    arquivo = new File("src\\formularios\\"+nomeArquivo);
                }
                bw.write(pessoa.toString());
                bw.flush();
            }catch (IOException e){
                e.printStackTrace();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}