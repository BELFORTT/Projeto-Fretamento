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
	private JTextField textFieldDestino;
	private JTextField textFieldMotorista;
	private JTextField textFieldPlaca;
	private JButton buttonCriar;
	private JButton buttonApagar;
	private JButton buttonAtualizar;
	private JButton buttonLimpar;
	private JButton btnCarregarFoto;
	private JButton buttonLimparFoto;
	private JPanel panel;
	private BufferedImage buffer; 
	private int idSelecionada = 0;
	private JTextField textFieldCNH;
	private JTextField textFieldData;

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
		label.setBounds(26, 371, 436, 29);
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
						idSelecionada = (int) table.getValueAt(table.getSelectedRow(), 0);
						
						Viagem v = ControllerViagem.localizarViagemComMotorista(idSelecionada);
						
						textFieldDestino.setText(v.getDestino());
						
						if (v.getData() != null) {
							java.time.format.DateTimeFormatter formatador = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
							textFieldData.setText(v.getData().format(formatador));
						} else {
							textFieldData.setText("");
						}

						if (v.getMotorista() != null) {
							textFieldMotorista.setText(v.getMotorista().getNome());
							textFieldCNH.setText(v.getMotorista().getCnh());
							
							if (v.getMotorista().getFoto() != null) {
								InputStream in = new ByteArrayInputStream(v.getMotorista().getFoto());
								buffer = ImageIO.read(in);
								ImageIcon icon = new ImageIcon(buffer.getScaledInstance(label_1.getWidth(), label_1.getHeight(), Image.SCALE_SMOOTH));
								label_1.setText("");
								label_1.setIcon(icon);
							} else {
								buffer = null;
								label_1.setText("sem foto"); 
								label_1.setIcon(null);
							}
						} else {
							textFieldMotorista.setText("Sem motorista");
							textFieldCNH.setText("");
							buffer = null;
							label_1.setText("sem foto"); 
							label_1.setIcon(null);
						}

						if (v.getVeiculo() != null) {
							textFieldPlaca.setText(v.getVeiculo().getPlaca());
						} else {
							textFieldPlaca.setText("");
						}
					}
				} catch (Exception erro) {
					label.setText(erro.getMessage());
				}
			}
		});

		textFieldDestino = new JTextField();
		textFieldDestino.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		textFieldDestino.setBounds(111, 263, 179, 25);
		frame.getContentPane().add(textFieldDestino);
		textFieldDestino.setColumns(10);

		JLabel lblNewLabel = new JLabel("Destino: ");
		lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblNewLabel.setBounds(26, 262, 75, 25);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Motorista:");
		lblNewLabel_1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(26, 299, 75, 25);
		frame.getContentPane().add(lblNewLabel_1);

		textFieldMotorista = new JTextField();
		textFieldMotorista.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		textFieldMotorista.setColumns(10);
		textFieldMotorista.setBounds(111, 300, 179, 25);
		frame.getContentPane().add(textFieldMotorista);

		JLabel lblNewLabel_1_1 = new JLabel("Placa:");
		lblNewLabel_1_1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblNewLabel_1_1.setBounds(26, 335, 75, 25);
		frame.getContentPane().add(lblNewLabel_1_1);

		textFieldPlaca = new JTextField();
		textFieldPlaca.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		textFieldPlaca.setColumns(10);
		textFieldPlaca.setBounds(111, 336, 179, 25);
		frame.getContentPane().add(textFieldPlaca);

		buttonCriar = new JButton("Criar");
		buttonCriar.setToolTipText("Cadastrar nova viagem");
		buttonCriar.addActionListener(e -> {
			TelaCadastroViagem t = new TelaCadastroViagem(null);
			t.setVisible(true);
			listagem();
		});
		buttonCriar.setBounds(21, 411, 95, 23);
		frame.getContentPane().add(buttonCriar);

		buttonAtualizar = new JButton("Atualizar");
		buttonAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textFieldMotorista.getText().isEmpty()) {
					label.setText("Nome do motorista vazio");
				} else {
					atualizarViagemSelecionada();
				}
			}
		});
		buttonAtualizar.setBounds(126, 411, 95, 23);
		frame.getContentPane().add(buttonAtualizar);

		buttonApagar = new JButton("Apagar");
		buttonApagar.setToolTipText("Apagar viagem selecionada");
		buttonApagar.addActionListener(e -> {
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
		buttonApagar.setBounds(231, 411, 95, 23);
		frame.getContentPane().add(buttonApagar);

		buttonLimpar = new JButton("Limpar");
		buttonLimpar.addActionListener(e -> limparCampos());
		buttonLimpar.setBounds(336, 411, 95, 23);
		frame.getContentPane().add(buttonLimpar);

		btnCarregarFoto = new JButton("Carregar foto");
		btnCarregarFoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textFieldPlaca.getText().isEmpty()) {
					label.setText("Selecione uma viagem primeiro.");
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

		buttonLimparFoto = new JButton("Limpar foto");
		buttonLimparFoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buffer = null;
				label_1.setIcon(null);
				label_1.setText("sem foto");
				label.setText("Precisa clicar em 'Atualizar' para salvar a alteracao da foto.");
			}
		});
		buttonLimparFoto.setBounds(639, 408, 108, 23);
		frame.getContentPane().add(buttonLimparFoto);

		textFieldCNH = new JTextField();
		textFieldCNH.setToolTipText("CNH");
		textFieldCNH.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		textFieldCNH.setColumns(10);
		textFieldCNH.setBounds(341, 300, 179, 25);
		frame.getContentPane().add(textFieldCNH);

		textFieldData = new JTextField();
		textFieldData.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		textFieldData.setColumns(10);
		textFieldData.setBounds(340, 263, 179, 25);
		frame.getContentPane().add(textFieldData);

		JLabel lblData = new JLabel("Data: ");
		lblData.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblData.setBounds(300, 262, 50, 25);
		frame.getContentPane().add(lblData);

		JLabel lblCnh = new JLabel("Cnh: ");
		lblCnh.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblCnh.setBounds(300, 299, 60, 25);
		frame.getContentPane().add(lblCnh);
	}

	public void listagem() {
		try {
			DefaultTableModel model = new DefaultTableModel();
			table.setModel(model);
			
			model.addColumn("Id");
			model.addColumn("Destino");
			model.addColumn("Data");

			List<Viagem> lista = ControllerViagem.listarViagens();
			java.time.format.DateTimeFormatter formatador = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
			
			for (Viagem v : lista) {
				String dataFormatada = v.getData() != null ? v.getData().format(formatador) : "";
				model.addRow(new Object[] { v.getId(), v.getDestino(), dataFormatada });
			}
			
			javax.swing.table.DefaultTableCellRenderer centralizado = new javax.swing.table.DefaultTableCellRenderer();
			centralizado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			
			for (int i = 0; i < table.getColumnCount(); i++) {
				table.getColumnModel().getColumn(i).setCellRenderer(centralizado);
			}
		} catch (Exception erro) {
			label.setText("Erro ao listar: " + erro.getMessage());
		}
	}

	public File selecionarArquivoFoto() {
		JFileChooser chooser = new JFileChooser();	    
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Imagens (*.jpg, *.jpeg, *.gif)", "jpg", "jpeg", "gif");
		chooser.setFileFilter(filter);
		
		try {
			File pastaFotos = new File("." + File.separator + "src" + File.separator + "fotos");        
			if (pastaFotos.exists() && pastaFotos.isDirectory()) {
				chooser.setCurrentDirectory(pastaFotos);
			}
		} catch (Exception e) {
			System.err.println("Erro ao definir diretorio inicial: " + e.getMessage());
		}
		
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int retorno = chooser.showOpenDialog(null);    
		if (retorno == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile();
		}    
		return null; 
	}

	private void limparCampos() {
		textFieldDestino.setText("");
		textFieldMotorista.setText("");
		textFieldPlaca.setText("");
		textFieldCNH.setText("");
		textFieldData.setText("");
		buffer = null;
		label_1.setIcon(null);
		label_1.setText("sem foto");
		label.setText("");
	}

	public void atualizarViagemSelecionada() {
		try {
			String destino = textFieldDestino.getText();
			String placa = textFieldPlaca.getText();
			String cnh = textFieldCNH.getText();	

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

			if (cnh != null && !cnh.isBlank()) {
				ControllerMotorista.alterarFotoMotorista(cnh, bytesfoto);
			}

			ControllerViagem.alterarViagem(idSelecionada, destino, cnh, placa, null);
			label.setText("Registro de viagem atualizado com sucesso.");
			listagem();
		} catch (Exception ex2) {
			label.setText(ex2.getMessage());
		}
	}
}