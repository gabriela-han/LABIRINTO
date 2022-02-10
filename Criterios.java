/*********************************************************************************/
/**   ACH2002 - Introducao a Analise de Algoritmos                  			**/
/**   EACH-USP - Segundo Semestre de 2020                           			**/
/**   Turma: 94 - Professor: Flavio Luiz Coutinho                               **/
/**                                                                 			**/
/**   Priemiro Exercicio-Programa 2                                   			**/
/**                                                                 			**/
/**   Nome das alunas: 															**/
/** 				  Daniella Fernanda Cisterna Melo   Numero USP: 11796281    **/
/** 				  Gabriela Jie Han                  Numero USP: 11877423    **/
/**                                                                 			**/
/**   Data de entrega: 22/01/2021                                      			**/
/*********************************************************************************/

/*********************************************************************************/
/* ARQUIVO COM INTERFACES E METODOS criados para calcular os quatros             */
/* tipos de caminhos:                                                            */
/*      1) mais curto                                                            */
/*      2) mais longo                                                            */
/*      3) mais valioso                                                          */
/*      4) mais rapido                                                           */
/*********************************************************************************/
public interface Criterios{

	Object CaminhoCurto = null;

	public boolean Compara(ArmazenandoCaminhos ac1, ArmazenandoCaminhos ac2);
}

class CaminhoCurto implements Criterios{

    public boolean Compara(ArmazenandoCaminhos ac1, ArmazenandoCaminhos ac2){

        return (ac1.getACaminho().size() >= ac2.getACaminho().size());
    }
    
}

class CaminhoLongo implements Criterios{

    public boolean Compara(ArmazenandoCaminhos ac1, ArmazenandoCaminhos ac2){

        return (ac1.getACaminho().size() <= ac2.getACaminho().size());
    }
}

class CaminhoValioso implements Criterios{

    public boolean Compara(ArmazenandoCaminhos ac1, ArmazenandoCaminhos ac2){

        return (ac1.getvalorItens() <= ac2.getvalorItens());
    }
}

class CaminhoRapido implements Criterios{

    public boolean Compara(ArmazenandoCaminhos ac1, ArmazenandoCaminhos ac2){

        return (ac1.gettempoLevado() >= ac2.gettempoLevado());
    }
}