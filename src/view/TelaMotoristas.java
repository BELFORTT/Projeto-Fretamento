package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import controller.ControllerMotorista;
import model.Motorista;
import model.Viagem;

public class TelaMotoristas {
	protected static final Frame Frame = null;
	private JDialog frame;
	private JScrollPane scrollPane;
	private JTable table;
	private JButton btn_refresh;
	private JTextField textField_nome;
	private JTextField textField_cnh;
	private BufferedImage buffer; 
	private JLabel lblNewLabel;
	private JLabel lblCnh;
	private JLabel lblNewLabel_1;
	private JTable table_historico;

	public TelaMotoristas() {
		initialize();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new TelaMotoristas();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	private void initialize() {
		frame = new JDialog();
		frame.setResizable(false);
		frame.setModal(true);
		frame.setTitle("Registro de Motoristas");
		frame.setBounds(100, 100, 572, 453);
		frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				listagem();
			}
		});

		scrollPane = new JScrollPane();
		scrollPane.setBounds(28, 34, 275, 180);
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

		// Selecionar linha da tabela
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					if (table.getSelectedRow() >= 0) {
						// copiar a pessoa selecionada para formulario de edicao
						String cnh = (String) table.getValueAt(table.getSelectedRow(), 1);
						Motorista m = ControllerMotorista.localizarMotorista(cnh);
						textField_nome.setText(m.getNome());
						textField_cnh.setText(m.getCnh());

						// ---- CARREGA E EXIBE A FOTO DO MOTORISTA ----
						if (m.getFoto() != null) {
							InputStream in = new ByteArrayInputStream(m.getFoto());
							buffer = ImageIO.read(in);
							ImageIcon icon = new ImageIcon(buffer.getScaledInstance(
									lblNewLabel_1.getWidth(), lblNewLabel_1.getHeight(), Image.SCALE_SMOOTH));
							lblNewLabel_1.setText(""); 
							lblNewLabel_1.setIcon(icon);
						} else {
							buffer = null;
							lblNewLabel_1.setText("Foto vai aqui"); 
							lblNewLabel_1.setIcon(null);
						}
						// ----------------------------------------------

						DefaultTableModel modelHist = new DefaultTableModel();
						modelHist.addColumn("ID");
						modelHist.addColumn("Destino");
						table_historico.setModel(modelHist);

						if (m.getListaViagem() != null) {
							for (Viagem v : m.getListaViagem()) {
								modelHist.addRow(new Object[] { v.getId(), v.getDestino() });
							}
						}
					}
				} catch (Exception erro) {
					erro.printStackTrace();
				}
			}
		});

		JButton btnCadastrar = new JButton("Novo Motorista");
		btnCadastrar.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		btnCadastrar.setBounds(171, 356, 132, 35);
		btnCadastrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				TelaCadastroMotorista tela = new TelaCadastroMotorista(Frame);
				tela.setVisible(true);
			}
		});
		frame.getContentPane().add(btnCadastrar);

		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		btnAtualizar.setBounds(28, 356, 132, 35);
		btnAtualizar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				atualizarMotorista();
			}
		});
		frame.getContentPane().add(btnAtualizar);

		btn_refresh = new JButton("Refresh");
		btn_refresh.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		btn_refresh.setBounds(325, 356, 103, 35);
		btn_refresh.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				listagem();
			}
		});
		frame.getContentPane().add(btn_refresh);

		textField_nome = new JTextField();
		textField_nome.setBounds(28, 246, 275, 35);
		frame.getContentPane().add(textField_nome);
		textField_nome.setColumns(10);

		textField_cnh = new JTextField();
		textField_cnh.setColumns(10);
		textField_cnh.setBounds(28, 303, 275, 35);
		frame.getContentPane().add(textField_cnh);

		lblNewLabel = new JLabel("Nome");
		lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		lblNewLabel.setBounds(28, 225, 118, 25);
		frame.getContentPane().add(lblNewLabel);

		lblCnh = new JLabel("CNH");
		lblCnh.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		lblCnh.setBounds(28, 285, 118, 25);
		frame.getContentPane().add(lblCnh);

		lblNewLabel_1 = new JLabel("Foto vai aqui");
		lblNewLabel_1.setBorder(new LineBorder(Color.LIGHT_GRAY));
		lblNewLabel_1.setBounds(341, 230, 95, 85);
		frame.getContentPane().add(lblNewLabel_1);

		// ---- BOTÃO PARA CARREGAR NOVA FOTO ----
		JButton btnCarregarFoto = new JButton("Carregar foto");
		btnCarregarFoto.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnCarregarFoto.setBounds(341, 320, 110, 25);
		btnCarregarFoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField_cnh.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Selecione um motorista primeiro.");
					return;
				}
				
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Imagens", "jpg", "gif", "png");
				chooser.setFileFilter(filter);
				try {
					chooser.setCurrentDirectory(new File((new File(".").getCanonicalPath() + File.separator + "src" + File.separator + "fotos")));
				} catch (IOException ex) {
					chooser.setCurrentDirectory(new File("."));
				}
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				chooser.showOpenDialog(null);
				File file = chooser.getSelectedFile();
				
				if (file == null) return;

				try {
					buffer = ImageIO.read(file); 
					ImageIcon icon = new ImageIcon(buffer.getScaledInstance(lblNewLabel_1.getWidth(), lblNewLabel_1.getHeight(), Image.SCALE_SMOOTH));
					lblNewLabel_1.setText("");
					lblNewLabel_1.setIcon(icon);
					labelRealTimeAviso("Clique em 'Atualizar' para salvar a foto.");
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		});
		frame.getContentPane().add(btnCarregarFoto);

		JScrollPane scrollPane_historico = new JScrollPane();
		scrollPane_historico.setBounds(316, 34, 230, 180);
		frame.getContentPane().add(scrollPane_historico);
		table_historico = new JTable() {
			@Override
			public boolean isCellEditable(int rowIndex, int vColIndex) {
				return false;
			}
		};
		table_historico.setFillsViewportHeight(true);
		table_historico.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_historico.setViewportView(table_historico);

		JLabel lblNewLabel_2 = new JLabel("Motoristas");
		lblNewLabel_2.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		lblNewLabel_2.setBounds(28, 9, 103, 25);
		frame.getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_2_1 = new JLabel("Histórico");
		lblNewLabel_2_1.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		lblNewLabel_2_1.setBounds(316, 9, 103, 25);
		frame.getContentPane().add(lblNewLabel_2_1);
	}

	public void listagem() {
		try {
			DefaultTableModel model = new DefaultTableModel();
			table.setModel(model);

			model.addColumn("Nome");
			model.addColumn("CNH");

			List<Motorista> lista = ControllerMotorista.listarMotoristas();
			for (Motorista m : lista) {
				model.addRow(new Object[] { m.getNome(), m.getCnh() });
			}
		} catch (Exception erro) {
			erro.printStackTrace();
		}
	}

	private void labelRealTimeAviso(String msg) {
		System.out.println(msg); 
	}

	public void atualizarMotorista() {
		try {
			String nomeDigitado = textField_nome.getText();   
			String cnhDigitada = textField_cnh.getText();  

			// 1. Primeiro validamos se a CNH realmente existe no banco
			Motorista m = ControllerMotorista.localizarMotorista(cnhDigitada);
			if (m == null) {
				JOptionPane.showMessageDialog(frame, "Motorista não encontrado com a CNH: " + cnhDigitada);
			} else { 
				ControllerMotorista.alterarMotorista(cnhDigitada, nomeDigitado);	            
				
				// ---- CONVERTE E SALVA OS BYTES DA FOTO SE HOUVER BUFFER ----
				byte[] bytesfoto = null;
				if (buffer != null) {
					try {
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ImageIO.write(buffer, "jpg", baos);
						bytesfoto = baos.toByteArray();
						baos.close();
					} catch (IOException ex1) {
						System.out.println("Problema na conversao da imagem em bytes.");
					}
				}
				
				ControllerMotorista.alterarFotoMotorista(cnhDigitada, bytesfoto);
				// -------------------------------------------------------------
				
				JOptionPane.showMessageDialog(frame, "Motorista atualizado com sucesso!");
			}
			listagem(); 
			
		} catch (Exception ex2) {
			JOptionPane.showMessageDialog(frame, "Erro ao atualizar: " + ex2.getMessage());
			ex2.printStackTrace();
		}
	}
}