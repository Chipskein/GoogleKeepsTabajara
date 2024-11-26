
package apresentacao;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import negocio.Anotacao;
import negocio.Cores;
import persistencia.AnotacaoDAO;
import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
public class Main {
    
    public static void main(String[] args) throws SQLException,IOException  {
        Scanner scanner = new Scanner(System.in);
        AnotacaoDAO dao = new AnotacaoDAO();
        int opcao;
        do {
            System.out.println("===== Menu Principal =====");
            System.out.println("[1]Cadastrar novas anotações.");
            System.out.println("[2]Excluir anotações.");
            System.out.println("[3]Alterar anotações;");
            System.out.println("[4]Copiar uma anotação existente");
            System.out.println("[5]Listar e Ordenar por data/hora de criação");
            System.out.println("[6]Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();
            try {
            switch (opcao) {
                
                case 1 -> {
                    try{
                        System.out.println("Digite o titulo da anotacao: ");
                        String titulo = scanner.nextLine();
                        System.out.println("Digite a descricao da anotacao: ");
                        String descricao = scanner.nextLine();
                        System.out.println("Digite a cor da anotacao: ");
                        String cor = scanner.nextLine();
                        LocalDateTime data = LocalDateTime.now();
                        System.out.println("Digite o caminho da foto da anotacao: ");
                        String caminho = scanner.nextLine();
                        Path path = Paths.get(caminho);
                        byte[] foto = Files.readAllBytes(path);
                        Anotacao anotacao = new Anotacao(-1,titulo, descricao, data,Cores.fromString(cor),foto);
                        anotacao = dao.inserir(anotacao);
                        System.out.println("Anotacao inserida: "+anotacao);
                    } catch (Exception e) {
                        System.out.println("[Criar Anotacao] Erro: " + e.getMessage());
                    }
                }
                
                case 2 -> {
                    System.out.println("Digite o id da anotacao para excluir: ");
                    int id = scanner.nextInt();
                    Anotacao anotacao = dao.pegarPorId(id);
                    if(anotacao==null){
                        System.out.println("Anotação não encontrada");
                        return;
                    }
                    System.out.println("Anotacao encontrada: "+anotacao);
                    dao.deletar(id);
                    System.out.println("Anotacao excluida: "+anotacao);
                }
                
                case 3 -> {
                    try{
                        System.out.println("Digite o id da anotacao para alterar: ");
                        int id = scanner.nextInt();
                        Anotacao anotacao = dao.pegarPorId(id);
                        if(anotacao==null){
                            System.out.println("Anotação não encontrada");
                            return;
                        }
                        System.out.println("Anotacao encontrada: "+anotacao);
                        System.out.println("Digite o novo titulo da anotacao: ");
                        String titulo = scanner.nextLine();
                        System.out.println("Digite a nova descricao da anotacao: ");
                        String descricao = scanner.nextLine();
                        System.out.println("Digite a nova cor da anotacao: ");
                        String cor = scanner.nextLine();
                        LocalDateTime data = LocalDateTime.now();
                        System.out.println("Digite o caminho da nova foto da anotacao: ");
                        String caminho = scanner.nextLine();
                        Path path = Paths.get(caminho);
                        byte[] foto = Files.readAllBytes(path);
                        anotacao.setTitulo(titulo);
                        anotacao.setDescricao(descricao);
                        anotacao.setCor(Cores.fromString(cor));
                        anotacao.setData_hora(data);
                        anotacao.setFoto(foto);
                        dao.atualizar(anotacao);
                        System.out.println("Anotacao alterada: "+anotacao);
                    } catch (Exception e) {
                        System.out.println("[Alterar Anotacao] Erro: " + e.getMessage());
                    }
                    
                }
                case 4 -> {
                    System.out.println("Digite o id da anotacao para copiar: ");
                    int id = scanner.nextInt();
                    Anotacao anotacao = dao.pegarPorId(id);
                    if(anotacao==null){
                        System.out.println("Anotação não encontrada");
                        return;
                    }
                    System.out.println("Anotacao encontrada: "+anotacao);
                    anotacao=anotacao.clone();
                    anotacao=dao.inserir(anotacao);
                    System.out.println("Anotacao copiada: "+anotacao);
                }
                
                case 5 -> {
                    System.out.println("Digite a ordem de listagem (asc ou desc): ");
                    String order = scanner.next();
                    System.out.println("Listagem ordenada por "+ order);
                    ArrayList<Anotacao> anotacoes = dao.listar(order);
                    for (Anotacao anotacao : anotacoes) {
                        System.out.println(anotacao);
                    }
                }
                case 6 -> System.out.println("Saindo do sistema...");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }

        } while (opcao != 6);

        scanner.close();
    }
    
}
