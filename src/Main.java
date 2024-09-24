import exceptions.EmailInvalidoException;
import exceptions.PerguntaInvalidaException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws PerguntaInvalidaException {
        Scanner sc = new Scanner(System.in);
        System.out.printf("1- Cadastrar usuário\n2- Listar todos os usuarios cadastrados\n3- Cadastrar nova pergunta no formulário\n4- Deletar pergunta do formulário\n5- Pesquisar usuário por nome ou idade ou email\n");
        int opc = sc.nextInt();
        int n = 1;
        int lineCounter;
        sc.nextLine();
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
                for (File file : files) {
                    try (BufferedReader br2 = new BufferedReader(new FileReader(file))) {
                        String emailExistente;
                        while ((emailExistente = br2.readLine()) != null) {
                            if (emailExistente.equalsIgnoreCase(pessoa.getEmail())) {
                                throw new EmailInvalidoException("Email ja esta sendo utilizado.");
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                String nomeArquivo = n + " - " + pessoa.getNome().toUpperCase() + ".txt";
                File arquivo = new File(forms.getPath() + "\\" + nomeArquivo);
                char digito = nomeArquivo.charAt(0);
                for (int i = 0; i < files.length; i++) {
                    if (i == files.length - 1) {
                        nomeArquivo = n + " - " + pessoa.getNome().toUpperCase() + ".txt";
                        arquivo = new File(forms.getPath() + "\\" + nomeArquivo);
                    } else {
                        nomeArquivo = ++n + " - " + pessoa.getNome().toUpperCase() + ".txt";
                        arquivo = new File(forms.getPath() + "\\" + nomeArquivo);
                    }
                }
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))) {
                    bw.write(pessoa.toString());
                    bw.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (opc == 2) {
            String regex = "\\d - [(a-z,A-Z)]+";
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
            lineCounter = 1;
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(forms.getPath() + "\\formulario.txt", true))) {
                BufferedReader br = new BufferedReader(new FileReader(forms.getPath() + "\\formulario.txt"));
                while (br.readLine() != null) {
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
            lineCounter = 0;
            System.out.println("Insira o digito da pergunta:");
            int excluir = sc.nextInt();
            if (excluir <= 4) {
                throw new PerguntaInvalidaException();
            } else {
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(newFile, true))) {
                    try (BufferedReader br = new BufferedReader(new FileReader(oldFile.getPath()))) {
                        while ((line = br.readLine()) != null) {
                            lineCounter++;
                            if (lineCounter != excluir) {
                                if (lineCounter > excluir) {
                                    bw.write((lineCounter - 1) + line.substring(1));
                                } else {
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
                if (isRenamed) {
                    System.out.println("Pergunta deletada.");
                }
            }
        } else if (opc == 5) {
            String regex = sc.nextLine();
            Pattern pattern = Pattern.compile(regex.toLowerCase());
            for (File file : files) {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    Matcher matcher = pattern.matcher(file.getName().toLowerCase());
                    String line;
                    if (matcher.find()) {
                        System.out.println(file.getName());
                        while ((line = br.readLine()) != null) {
                            System.out.println(line);
                        }
                        System.out.println();
                    }else{
                        try (BufferedReader br2 = new BufferedReader(new FileReader(file))) {
                            String auxLine;
                            lineCounter = 1;
                            while ((auxLine = br2.readLine()) != null){
                                matcher = pattern.matcher(auxLine.toLowerCase());
                                if (matcher.find() || lineCounter == 2){
                                    System.out.println(file.getName());
                                    while ((line = br.readLine()) != null) {
                                        System.out.println(line);
                                    }
                                    System.out.println();
                                }
                                lineCounter++;
                            }
                        }
                    }
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}