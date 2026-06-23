/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/

package appconsole;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import model.Motorista;
import model.Viagem;
import util.Util;

public class Apagar {

    public Apagar() {
        try {
            Util.conectar();
            EntityManager manager = Util.getManager();
            
            String mtrNome = "Caue";
            System.out.println("Tarefa: Deletar motorista " + mtrNome + " e todas as suas viagens");

            manager.getTransaction().begin();

            // Localiza o motorista pelo nome
            TypedQuery<Motorista> q = manager.createQuery(
                    "select m from Motorista m where m.nome = :n", Motorista.class);
            q.setParameter("n", mtrNome);
            
            try {
                Motorista mtr = q.getSingleResult();

                // Desvincula as viagens dos veículos de forma limpa antes de deletar
                for (Viagem v : mtr.getListaViagem()) {
                    if (v.getVeiculo() != null) {
                        v.getVeiculo().removerViagem(v); // Remove a viagem da lista do veículo
                        v.setVeiculo(null);              // Zera a propriedade na viagem
                    }
                }

                // O manager.remove vai apagar o motorista E todas as viagens por causa do CascadeType.ALL + orphanRemoval
                manager.remove(mtr); 
                
                manager.getTransaction().commit();
                System.out.println("Motorista e viagens deletados com sucesso!");

            } catch (NoResultException e) {
                System.out.println("Motorista " + mtrNome + " nao encontrado.");
                manager.getTransaction().rollback();
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            if (Util.getManager().getTransaction().isActive()) 
                Util.getManager().getTransaction().rollback();
        }

        Util.desconectar();
        System.out.println("Fim do programa");
    }

    public static void main(String[] args) {
        new Apagar();
    }
}