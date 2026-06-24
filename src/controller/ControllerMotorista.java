package controller;

import java.util.List;

import model.Motorista;
import repositorio.Repositorio;
import repositorio.RepositorioMotorista;

public class ControllerMotorista {
    private ControllerMotorista() {}

    private static RepositorioMotorista repMotorista = new RepositorioMotorista();

    // ==========================================
    // LOCALIZAR MOTORISTA
    // ==========================================
    public static Motorista localizarMotorista(String cnh) throws Exception {
        try {
            Repositorio.conectar();
            Motorista m = repMotorista.localizar(cnh); 
            return m;
        } catch (Exception e) {
            throw e;
        } finally {
            Repositorio.desconectar();
        }
    }

    // ==========================================
    // CRIAR MOTORISTA
    // ==========================================
    public static void criarMotorista(String cnh, String nome) throws Exception {
        try {
            Repositorio.conectar();
            Repositorio.begin();

            if (cnh == null || cnh.isBlank())
                throw new Exception("Criar motorista - CNH eh obrigatoria.");
            if (nome == null || nome.isBlank())
                throw new Exception("Criar motorista - Nome eh obrigatorio.");

            Motorista m = repMotorista.localizar(cnh);
            if (m != null) {
                System.out.println("CNH duplicada");
                throw new Exception("Criar motorista - Ja existe um motorista cadastrado com esta CNH: " + cnh);
            }
            
            m = new Motorista();
            m.setCnh(cnh);
            m.setNome(nome);
            
            repMotorista.criar(m);
            Repositorio.commit();

        } catch (Exception e) {
            Repositorio.rollback();
            throw e;
        } finally {
            Repositorio.desconectar();
        }
    }

    // ==========================================
    // ALTERAR MOTORISTA
    // ==========================================
    public static void alterarMotorista(String cnh, String novoNome) throws Exception {
        try {
            Repositorio.conectar();
            Repositorio.begin();
            
            Motorista m = repMotorista.localizar(cnh);
            if (m == null)
                throw new Exception("Alterar motorista - Motorista nao encontrado com a CNH: " + cnh);

            if (novoNome != null && !novoNome.isBlank()) {
                m.setNome(novoNome);
            }

            repMotorista.atualizar(m); 
            Repositorio.commit();

        } catch (Exception e) {
            Repositorio.rollback();
            throw e;
        } finally {
            Repositorio.desconectar();
        }
    }

    // ==========================================
    // ADICIONADO: ALTERAR FOTO DO MOTORISTA
    // ==========================================
    public static void alterarFotoMotorista(String cnh, byte[] fotoBytes) throws Exception {
        try {
            Repositorio.conectar();
            Repositorio.begin();

            Motorista m = repMotorista.localizar(cnh);
            if (m == null)
                throw new Exception("Alterar foto - Motorista nao encontrado com a CNH: " + cnh);

            // Vincula o array de bytes da foto ao objeto gerenciado pelo JPA
            m.setFoto(fotoBytes);

            repMotorista.atualizar(m);
            Repositorio.commit();
        } catch (Exception e) {
            Repositorio.rollback();
            throw e;
        } finally {
            Repositorio.desconectar();
        }
    }

    // ==========================================
    // APAGAR MOTORISTA
    // ==========================================
    public static void apagarMotorista(String cnh) throws Exception {
        try {
            Repositorio.conectar();
            Repositorio.begin();
            
            Motorista m = repMotorista.localizar(cnh);
            if (m == null)
                throw new Exception("Excluir motorista - Motorista inexistente com a CNH: " + cnh);

            repMotorista.deletar(m);   
            Repositorio.commit();
            
        } catch (Exception e) {
            Repositorio.rollback();
            throw e;
        } finally {
            Repositorio.desconectar();
        }
    }

    // ==========================================
    // LISTAGENS E FILTROS
    // ==========================================
    public static List<Motorista> listarMotoristas() {
        Repositorio.conectar();
        List<Motorista> lista = repMotorista.listar();
        Repositorio.desconectar();
        return lista;
    }

    // Buscar um unico motorista pelo nome
    public static Motorista buscarMotoristaPorNome(String nome) {
        Repositorio.conectar();
        List<Motorista> lista = repMotorista.listarPorNome(nome);
        Repositorio.desconectar();

        for (Motorista m : lista) {
            if (m.getNome().equalsIgnoreCase(nome)) {
                return m; 
            }
        }
        return null; 
    }
}