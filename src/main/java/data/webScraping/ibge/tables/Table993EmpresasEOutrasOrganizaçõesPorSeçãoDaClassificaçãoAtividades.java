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
package data.webScraping.ibge.tables;

import data.webScraping.ibge.SidraIBGE;

/*
Cadastro Central de Empresas

Tabela 993 - Empresas e outras organizações, por seção da classificação de atividades (CNAE 2.0), faixas de pessoal ocupado total e ano de fundação (Vide Notas)
 */
public class Table993EmpresasEOutrasOrganizaçõesPorSeçãoDaClassificaçãoAtividades  extends SidraIBGE {
    public Table993EmpresasEOutrasOrganizaçõesPorSeçãoDaClassificaçãoAtividades(int table) {
        super(993);
    }
}
