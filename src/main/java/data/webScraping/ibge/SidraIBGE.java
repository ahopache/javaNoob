/**
 * encode: utf-8
 *
 * @author Assis Henrique Oliveira Pacheco
 * @version: 1.0
 * <p>
 * # PortuguesBR
 * <p>
 * <p>
 * # English
 */
package data.webScraping.ibge;

import util.UtilWeb;

import java.util.ArrayList;
import java.util.List;

public class SidraIBGE extends UtilWeb {
    private List<String> listAno = new ArrayList<String>();
    private List<String> listAnoFundacao = new ArrayList<String>();
    private List<String> listCNAE = new ArrayList<String>();//Classificação Nacional de Atividades Econômicas (CNAE 2.0)
    private List<String> listFaixasPessoalOcupado = new ArrayList<String>();
    private List<String> listVariavel = new ArrayList<String>();
    private List<String> listUnidadeTerritorial = new ArrayList<String>();

    private int table;

    public SidraIBGE(int table){
        this.table = table;
    }

    public void checkAno(){

    }
}
