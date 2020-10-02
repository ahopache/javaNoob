package util;

/**
 * @Autor: Assis Henrique Oliveira Pacheco
 * @version  0.9
 *
 * Classe para registrar todos os logs de situações ocorridas na execução do programa
 * Preparando a solução para executar como um serviço
 *
 * Na versão 0.9 ele deve apenas printar na tela as mensagens
 * Para a versão 1.0, ele deve salvar em arquivo texto
 */

public class UtilLog {
    public static void setLog(String message){
        System.out.println(message);
    }
    public static void setLog(String project, String message){
        System.out.println(message);
    }
}
