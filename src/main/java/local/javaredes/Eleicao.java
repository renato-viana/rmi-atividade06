package local.javaredes;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

/**
 *
 * @author Renato Borges Viana
 */
public interface Eleicao extends Remote {

    void contarVotos(Map<Integer, Candidato> candidatos) throws RemoteException;

    void atualizarApuracao() throws RemoteException;

    void enviarDadosCandidato(Candidato candidato) throws RemoteException;
}
