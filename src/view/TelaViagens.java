package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import controller.ControllerMotorista;
import controller.ControllerViagem;
import model.Viagem;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class TelaViagens {
	private JLabel label;
	private JLabel label_1;
	private JDialog frame;
	private JScrollPane scrollPane;
	private JTable table;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JButton button_1;
	private JButton button_3;
	private JButton button_2;
	private JButton button_4;
	private JButton btnCarregarFoto;
	private JButton button_6;
	private JPanel panel;
	private BufferedImage buffer; 
	private int idSelecionada = 0;
	private JTextField textField_3;

	public TelaViagens() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	private void initialize() {
		frame = new JDialog();
		frame.setResizable(false);
		frame.setModal(true);
		frame.setTitle("Registro de Viagens");
		frame.setBounds(100, 100, 783, 484);
		frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				listagem();
			}
		});

		label = new JLabel("");
		label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		label.setForeground(Color.RED);
		label.setBounds(26, 371, 600, 29);
		frame.getContentPane().add(label);

		panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder("Foto"));
		panel.setBounds(639, 245, 108, 118);
		frame.getContentPane().add(panel);

		label_1 = new JLabel("sem foto");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		label_1.setBounds(10, 21, 78, 73);
		panel.add(label_1);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(26, 34, 715, 200);
		frame.getContentPane().add(scrollPane);

		table = new JTable() {
			@Override
			public boolean isCellEditable(int rowIndex, int vColIndex) {
				return false; 
			}
		};

		table.setGridColor(Color.BLACK);
		table.setRequestFocusEnabled(false);
		table.setFocusable(false);
		table.setBackground(Color.WHITE);
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(true);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane.setViewportView(table);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setShowGrid(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					label.setText("");
					if (table.getSelectedRow() >= 0) {
						int id = (int) table.getValueAt(table.getSelectedRow(), 0);
						idSelecionada = id;
						Viagem v = ControllerViagem.localizarViagem(id);
						textField.setText(v.getDestino());
						textField_1.setText(v.getMotorista().getNome());
						textField_2.setText(v.getVeiculo().getPlaca());
						textField_3.setText(v.getMotorista().getCnh());

						if (v.getMotorista().getFoto() != null) {
							InputStream in = new ByteArrayInputStream(v.getMotorista().getFoto());
							buffer = ImageIO.read(in);
							ImageIcon icon = new ImageIcon(buffer.getScaledInstance(buffer.getWidth(),
									buffer.getHeight(), Image.SCALE_DEFAULT));
							icon.setImage(
									icon.getImage().getScaledInstance(label_1.getWidth(), label_1.getHeight(), Image.SCALE_SMOOTH));
							label_1.setText("");
							label_1.setIcon(icon);
						} else {
							buffer = null;
							label_1.setText("sem foto"); 
							label_1.setIcon(null);
						}
					}
				} catch (Exception erro) {
					label.setText(erro.getMessage());
				}
			}
		});

		textField = new JTextField();
		textField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		textField.setBounds(111, 263, 179, 25);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel = new JLabel("Destino: ");
		lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblNewLabel.setBounds(26, 262, 75, 25);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Motorista:");
		lblNewLabel_1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(26, 299, 75, 25);
		frame.getContentPane().add(lblNewLabel_1);

		textField_1 = new JTextField();
		textField_1.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		textField_1.setColumns(10);
		textField_1.setBounds(111, 300, 179, 25);
		frame.getContentPane().add(textField_1);

		JLabel lblNewLabel_1_1 = new JLabel("Placa:");
		lblNewLabel_1_1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblNewLabel_1_1.setBounds(26, 335, 75, 25);
		frame.getContentPane().add(lblNewLabel_1_1);

		textField_2 = new JTextField();
		textField_2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		textField_2.setColumns(10);
		textField_2.setBounds(111, 336, 179, 25);
		frame.getContentPane().add(textField_2);

		// CORRIGIDO: Modificado para ActionListener padrão Swing
		button_1 = new JButton("Criar");
		button_1.setToolTipText("Cadastrar nova viagem");
		button_1.addActionListener(e -> {
			TelaCadastroViagem tela = new TelaCadastroViagem(null);
			tela.setVisible(true);
			listagem(); // Atualiza a tabela após fechar a janela de criação
		});
		button_1.setBounds(21, 411, 95, 23);
		frame.getContentPane().add(button_1);

		button_2 = new JButton("Atualizar");
		button_2.setToolTipText("Atualizar viagem");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField_1.getText().isEmpty()) {
					label.setText("Nome do motorista vazio");
				} else {
					atualizarViagemSelecionada();
				}
			}
		});
		button_2.setBounds(126, 411, 95, 23);
		frame.getContentPane().add(button_2);

		// CORRIGIDO: Implementação completa do botão "Apagar"
		button_3 = new JButton("Apagar");
		button_3.setToolTipText("Apagar viagem selecionada");
		button_3.addActionListener(e -> {
			if (idSelecionada == 0) {
				label.setText("Selecione uma viagem na tabela para apagar.");
				return;
			}
			int confirmacao = JOptionPane.showConfirmDialog(frame, "Deseja realmente excluir a viagem ID: " + idSelecionada + "?", "Confirmar Exclusao", JOptionPane.YES_NO_OPTION);
			if (confirmacao == JOptionPane.YES_OPTION) {
				try {
					ControllerViagem.apagarViagem(idSelecionada);
					label.setText("Viagem excluida com sucesso.");
					idSelecionada = 0;
					limparCampos();
					listagem();
				} catch (Exception ex) {
					label.setText("Erro ao apagar: " + ex.getMessage());
				}
			}
		});
		button_3.setBounds(231, 411, 95, 23);
		frame.getContentPane().add(button_3);

		button_4 = new JButton("Limpar");
		button_4.addActionListener(e -> limparCampos());
		button_4.setBounds(336, 411, 95, 23);
		frame.getContentPane().add(button_4);

		btnCarregarFoto = new JButton("Carregar foto");
		btnCarregarFoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField_3.getText().isEmpty()) {
					label.setText("Selecione uma viagem para carregar a foto do motorista");
					return;
				}
				File file = selecionarArquivoFoto();
				if (file == null) return;

				try {
					buffer = ImageIO.read(file); 
					ImageIcon icon = new ImageIcon(buffer.getScaledInstance(label_1.getWidth(), label_1.getHeight(), Image.SCALE_SMOOTH));
					label_1.setText("");
					label_1.setIcon(icon);
					label.setText("Precisa clicar em 'Atualizar' para salvar a nova foto.");
				} catch (IOException ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		btnCarregarFoto.setBounds(639, 374, 108, 23);
		frame.getContentPane().add(btnCarregarFoto);

		button_6 = new JButton("Limpar foto");
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buffer = null;
				label_1.setIcon(null);
				label_1.setText("sem foto");
				label.setText("Precisa clicar em 'Atualizar' para consolidar a exclusao da foto.");
			}
		});
		button_6.setBounds(639, 408, 108, 23);
		frame.getContentPane().add(button_6);

		textField_3 = new JTextField();
		textField_3.setToolTipText("CNH");
		textField_3.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		textField_3.setColumns(10);
		textField_3.setBounds(301, 300, 179, 25);
		frame.getContentPane().add(textField_3);
	}

	public void listagem() {
		try {
			DefaultTableModel model = new DefaultTableModel();
			table.setModel(model);

			model.addColumn("Id");
			model.addColumn("Destino");
			model.addColumn("Motorista");
			model.addColumn("Veiculo");

			List<Viagem> lista = ControllerViagem.listarViagens();
			for (Viagem v : lista) {
				model.addRow(new Object[] { 
					v.getId(), 
					v.getDestino(), 
					v.getMotorista() != null ? v.getMotorista().getNome() : "Sem Motorista",
					v.getVeiculo() != null ? v.getVeiculo().getPlaca() : "Sem Veiculo" 
				});
			}
		} catch (Exception erro) {
			label.setText("Erro ao listar: " + erro.getMessage());
		}
	}

	public File selecionarArquivoFoto() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Imagens", "jpg", "gif", "png");
		chooser.setFileFilter(filter);
		try {
			chooser.setCurrentDirectory(new File((new File(".").getCanonicalPath() + File.separator + "src" + File.separator + "fotos")));
		} catch (IOException e) {
			chooser.setCurrentDirectory(new File("."));
		}
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.showOpenDialog(null);
		return chooser.getSelectedFile();
	}

	private void limparCampos() {
		textField.setText("");
		textField_1.setText("");
		textField_2.setText("");
		textField_3.setText("");
		buffer = null;
		label_1.setIcon(null);
		label_1.setText("sem foto");
		label.setText("");
	}

	public void atualizarViagemSelecionada() {
		try {
			label.setText("");
			String destino = textField.getText();
			String motorista = textField_1.getText();
			String placa = textField_2.getText();
			String cnh = textField_3.getText();

			// 1. Atualiza os dados relacionais da viagem
			ControllerViagem.alterarViagem(idSelecionada, destino, motorista, placa, cnh);

			// CORRIGIDO: Conversão e salvamento do array binário de bytes da foto do motorista
			byte[] bytesfoto = null;
			if (buffer != null) {
				try {
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					ImageIO.write(buffer, "jpg", baos);
					bytesfoto = baos.toByteArray();
					baos.close();
				} catch (IOException ex1) {
					label.setText("Problema na conversao da imagem em bytes.");
					return;
				}
			}
			
			// Se o método existir no ControllerMotorista, salva os bytes da foto
			if (cnh != null && !cnh.isBlank()) {
				ControllerMotorista.alterarFotoMotorista(cnh, bytesfoto);
			}

			label.setText("Registro de viagem atualizado.");
			listagem();
		} catch (Exception ex2) {
			label.setText(ex2.getMessage());
		}
	}
}