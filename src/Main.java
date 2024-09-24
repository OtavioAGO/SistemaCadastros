import exceptions.PerguntaInvalidaException;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws PerguntaInvalidaException {
        Scanner sc = new Scanner(System.in);
        System.out.printf("1- Cadastrar usuário\n2- Listar todos os usuarios cadastrados\n3- Cadastrar nova pergunta no formulário\n4- Deletar pergunta do formulário\n5- Pesquisar usuário por nome ou idade ou email\n");
        int opc = sc.nextInt();
        sc.nextLine();
        int n = 1;
        File forms = new File("src\\formularios");
        File[] files = forms.listFiles();
        if (opc == 1) {
            try (BufferedReader br = new BufferedReader(new FileReader(forms.getPath() + "\\formulario.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
                String nome = sc.nextLine();
                String email = sc.nextLine();
                int idade = sc.nextInt();
                sc.nextLine();
                double altura = sc.nextDouble();
                sc.nextLine();
                Pessoa pessoa = new Pessoa(nome, email, idade, altura);
                String nomeArquivo = n + "-" + pessoa.getNome().toUpperCase() + ".txt";
                File arquivo = new File(forms + "\\" + nomeArquivo);
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))) {
                    while (arquivo.exists()) {
                        nomeArquivo = ++n + "-" + pessoa.getNome().toUpperCase() + ".txt";
                        arquivo = new File(forms + "\\" + nomeArquivo);
                    }
                    bw.write(pessoa.toString());
                    bw.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (opc == 2) {
            String regex = "\\d-[(a-z,A-Z)]+";
            Pattern pattern = Pattern.compile(regex);
            for (int i = 0; i < files.length; i++) {
                Matcher matcher = pattern.matcher(files[i].getName());
                try (BufferedReader br = new BufferedReader(new FileReader(files[i]))) {
                    if (matcher.find()) {
                        System.out.println((n + i) + " - " + br.readLine());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (opc == 3) {
            int lineCounter = 1;
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(forms.getPath() + "\\formulario.txt", true))) {
                BufferedReader br = new BufferedReader(new FileReader(forms.getPath() + "\\formulario.txt"));
                while (br.readLine() != null){
                    lineCounter++;
                }
                System.out.println("Insira a pergunta:");
                String pergunta = sc.nextLine();
                bw.write(lineCounter + " - " + pergunta);
                bw.newLine();
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (opc == 4) {
            String temp = "temp.txt";
            File oldFile = new File(forms.getPath() + "\\formulario.txt");
            File newFile = new File(forms.getPath() + "\\" + temp);
            String line;
            int lineCounter = 0;
            System.out.println("Insira o digito da pergunta:");
            int excluir = sc.nextInt();
            if (excluir <= 4) {
                throw new PerguntaInvalidaException();
            } else {
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(newFile, true))) {
                    try (BufferedReader br = new BufferedReader(new FileReader(oldFile.getPath()))) {
                        while ((line = br.readLine()) != null) {
                            lineCounter++;
                            if (lineCounter != excluir){
                                if (lineCounter > excluir){
                                    bw.write((lineCounter-1)+line.substring(1));
                                }else{
                                    bw.write(line);
                                    bw.newLine();
                                }
                            }
                        }
                        bw.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                oldFile.delete();
                File dump = new File(forms.getPath() + "\\formulario.txt");
                boolean isRenamed = newFile.renameTo(dump);
                if (isRenamed){
                    System.out.println("Pergunta deletada.");
                }
            }
        } else if (opc == 5) {
            String regex = sc.nextLine();
            Pattern pattern = Pattern.compile(regex);
            for (File file : files) {
                Matcher matcher = pattern.matcher(file.getName());
                if (matcher.find()){
                    try(BufferedReader br = new BufferedReader(new FileReader(file))){
                        String line;
                        while((line = br.readLine())!= null){
                            System.out.println(line);
                        }
                        System.out.println();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }

        }
    }
}