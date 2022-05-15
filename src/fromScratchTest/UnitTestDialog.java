package fromScratchTest;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/***
 * Formulario de diálogo para ejecutar y lanzar las pruebas unitarias 
 * 
 * @author G1
 *
 */
@SuppressWarnings("serial")
public class UnitTestDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextArea textAreaResults;


	/**
	 * Create the dialog.
	 */
	public UnitTestDialog(String testResults) {
		
		setTitle ("Run tests");
		
		setDefaultCloseOperation (DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JScrollPane scrollPane = new JScrollPane(textAreaResults = new JTextArea());
			Font font = textAreaResults.getFont();
			textAreaResults.setFont(new Font ("MonoSpaced", font.getStyle (), font.getSize()));
			contentPanel.add(scrollPane, BorderLayout.CENTER);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose ();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		
		textAreaResults.setText(testResults);
		setVisible (true);
	}

}
