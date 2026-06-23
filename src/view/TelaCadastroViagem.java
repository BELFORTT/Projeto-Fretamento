package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import controller.ControllerViagem;
import controller.ControllerMotorista;
import controller.ControllerVeiculo;
import model.Motorista;
import model.Veiculo;

public class TelaCadastroViagem extends JDialog {

    private JTextField txtData;
    private JTextField txtDestino;
    private JComboBox<Motorista> comboMotorista;
    private JComboBox<Veiculo> comboVeiculo;
    private JButton btnSalvar;
    private JButton btnCancelar;

    public TelaCadastroViagem(Frame parent) {
        super(parent, "Cadastrar Nova Viagem", true); 
        setSize(390, 340); 
        setLocationRelativeTo(parent);
        setResizable(false);
        getContentPane().setLayout(null);

        JPanel painelFormulario = new JPanel();
        painelFormulario.setBounds(0, 0, 374, 230);
        painelFormulario.setBorder(new EmptyBorder(15, 15, 15, 15));
        painelFormulario.setLayout(null);

        JLabel label = new JLabel("Data (DD/MM/AAAA):");
        label.setBounds(15, 11, 150, 16);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        painelFormulario.add(label);
        
        txtData = new JTextField();
        txtData.setBounds(15, 31, 344, 25);
        txtData.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtData.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        painelFormulario.add(txtData);

        JLabel label_1 = new JLabel("Destino:");
        label_1.setBounds(15, 67, 80, 16);
        label_1.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        painelFormulario.add(label_1);
        
        txtDestino = new JTextField();
        txtDestino.setBounds(15, 86, 344, 25);
        txtDestino.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        painelFormulario.add(txtDestino);

        JLabel label_2 = new JLabel("Motorista:");
        label_2.setBounds(15, 122, 80, 14);
        label_2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        painelFormulario.add(label_2);
        
        comboMotorista = new JComboBox<>();
        comboMotorista.setBounds(15, 138, 344, 25);
        comboMotorista.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        carregarMotoristas();
        painelFormulario.add(comboMotorista);

        JLabel label_3 = new JLabel("Veiculo (Placa):");
        label_3.setBounds(15, 174, 100, 14);
        label_3.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        painelFormulario.add(label_3);
        
        comboVeiculo = new JComboBox<>();
        comboVeiculo.setBounds(15, 191, 344, 25);
        comboVeiculo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        carregarVeiculos();
        painelFormulario.add(comboVeiculo);

        getContentPane().add(painelFormulario);

        JPanel painelBotoes = new JPanel();
        painelBotoes.setBounds(0, 240, 374, 50);
        painelBotoes.setLayout(null);
        
        btnSalvar = new JButton("Salvar");
        btnSalvar.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnSalvar.setBounds(15, 5, 110, 28);
        btnSalvar.addActionListener(e -> salvarViagem());
        painelBotoes.add(btnSalvar);
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnCancelar.setBounds(249, 5, 110, 28); 
        btnCancelar.addActionListener(e -> dispose());
        painelBotoes.add(btnCancelar);

        getContentPane().add(painelBotoes);
    }

    private void carregarMotoristas() {
        try {
            List<Motorista> motoristas = ControllerMotorista.listarMotoristas(); 
            for (Motorista m : motoristas) {
                comboMotorista.addItem(m);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar motoristas: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarVeiculos() {
        try {
            List<Veiculo> veiculos = ControllerVeiculo.listarVeiculos(); 
            for (Veiculo v : veiculos) {
                comboVeiculo.addItem(v);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar veiculos: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvarViagem() {
        try {
            if (txtDestino.getText().trim().isEmpty() || txtData.getText().trim().isEmpty()) {
                throw new Exception("Todos os campos de texto devem ser preenchidos.");
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate data = LocalDate.parse(txtData.getText().trim(), formatter);
            String destino = txtDestino.getText().trim();

            Motorista motoristaSelecionado = (Motorista) comboMotorista.getSelectedItem();
            Veiculo veiculoSelecionado = (Veiculo) comboVeiculo.getSelectedItem();

            if (motoristaSelecionado == null || veiculoSelecionado == null) {
                throw new Exception("E necessario ter um motorista e um veiculo cadastrados e selecionados.");
            }

            // =========================================================================
            // MUDANÇA AQUI: Passando apenas o CNH e a Placa em vez dos objetos soltos
            // =========================================================================
            ControllerViagem.criarViagem(data, destino, motoristaSelecionado.getCnh(), veiculoSelecionado.getPlaca());

            JOptionPane.showMessageDialog(this, "Viagem salva com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dispose(); 

        } catch (java.time.format.DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato de data invalido! Use DD/MM/AAAA.", "Aviso", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}