package local.javaredes;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Renato Borges Viana
 */
public class Servidor implements Eleicao {

    private static final Map<Integer, Candidato> candidatos = new HashMap<>();

    public static void main(String[] args) {
        try {
            Registry servidorRegistro = LocateRegistry.createRegistry(1099);
            Servidor servidor = new Servidor();
            Eleicao skeleton = (Eleicao) UnicastRemoteObject.exportObject(servidor, 0);
            servidorRegistro.bind("eleicaoRmi", skeleton);
            System.out.println("Servidor iniciado...");

            System.out.println("\nEleição");
            System.out.println("-------");

            servidor.atualizarApuracao();

        } catch (RemoteException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AlreadyBoundException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void contarVotos(Map<Integer, Candidato> candidatos) throws RemoteException {
        System.out.println("\nVotos apurados:\n");
        for (Candidato candidato : candidatos.values()) {
            int numHifens = 30 - candidato.getNome().length();
            String hifens = new String(new char[numHifens]).replace("\0", "-");
            System.out.printf("%d %s %s %d votos\n", candidato.getNumeroChapa(), candidato.getNome(), hifens, candidato.getNumeroVotos());
        }
    }

    @Override
    public void atualizarApuracao() throws RemoteException {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Runnable task = () -> {
            try {
                contarVotos(candidatos);
            } catch (RemoteException ex) {
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        };
        scheduler.scheduleAtFixedRate(task, 0, 5, TimeUnit.SECONDS);
    }

    @Override
    public void enviarDadosCandidato(Candidato candidato) throws RemoteException {
        if (candidatos.containsKey(candidato.getNumeroChapa())) {
            Candidato candidatoExistente = candidatos.get(candidato.getNumeroChapa());
            candidatoExistente.setNumeroVotos(candidatoExistente.getNumeroVotos() + candidato.getNumeroVotos());
        } else {
            candidatos.put(candidato.getNumeroChapa(), candidato);
        }
    }
}
