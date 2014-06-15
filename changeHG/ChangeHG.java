package changeHG;

import java.awt.ItemSelectable;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import changeHG.DBConnet;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class ChangeHG {
	private static Table table;
	private static Text text;
	final static DBConnet db = new DBConnet();
	static ResultSet rs;
	static TableItem item;


	public static void log() {
		Handler[] arrayOfHandler;
		Logger logger = Logger.getLogger(ChangeHG.class.getName());
		logger.setLevel(Level.FINE);
		int j = (arrayOfHandler = logger.getParent().getHandlers()).length;
		for (int i = 0; i < j; ++i) {
			Handler handler = arrayOfHandler[i];
			handler.setLevel(Level.FINE);
		}
		logger.log(Level.WARNING, "WARNING ��Ϣ");
		logger.log(Level.INFO, "INFO ��Ϣ");
		logger.log(Level.CONFIG, "CONFIG ��Ϣ");
		logger.log(Level.FINE, "FINE ��Ϣ");
	}

	public static void cha() {

		
		rs = db.query("select * from hg");
		try {
			if (table.getItems().length == 0)
				while (rs.next()) {
					item = new TableItem(table, SWT.NONE);					
					item.setText(new String[] {rs.getString(1), rs.getString(2)});
				}
		} catch (SQLException e1) {
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
		}

	}

	public static void main(String[] args) {
		log();
		Display display = Display.getDefault();
		final Shell shell = new Shell(display, SWT.CLOSE | SWT.MIN);

		shell.setSize(650, 430);
		shell.setText("��������Ļ�޸����");

		table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION | SWT.H_SCROLL);
		table.setBounds(0, 0, 644, 300);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		final TableColumn id = new TableColumn(table, SWT.LEFT);
		id.setWidth(0);
		id.setResizable(false);

		final TableColumn hg = new TableColumn(table, SWT.LEFT);
		hg.setMoveable(true);
		hg.setWidth(637);
		hg.setText("\u5B57\u5E55");

		text = new Text(shell, SWT.READ_ONLY);
		text.setEditable(true);
		text.setBounds(84, 307, 550, 23);

		Label lblNewLabel = new Label(shell, SWT.NONE);
	//	lblNewLabel.setFont(SWTResourceManager.getFont("΢���ź�", 12, SWT.NORMAL));
		lblNewLabel.setBounds(10, 310, 68, 20);
		lblNewLabel.setText("\u586B\u5199\u5B57\u5E55");

		Button del = new Button(shell, SWT.NONE);
		del.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				TableItem item = table.getItem(table.getSelectionIndex());   
				String sql="delete from hg where id="+item.getText(0);
				if(db.delete(sql)>-1)
				{
					table.removeAll();
					cha();
					MessageBox messageBox = new MessageBox(shell,
							SWT.ICON_INFORMATION | SWT.YES);
					messageBox.setText("��ʾ");
					messageBox.setMessage("��Ļɾ���ɹ�");
					messageBox.open();
				}
			}
		});
		del.setBounds(318, 349, 80, 27);
		del.setText("ɾ����Ļ");

		Button add = new Button(shell, SWT.NONE);
		add.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String sql = "insert into hg values('" + text.getText() + "')";
				if (text.getText().equals("") && text.getText() == "") {
					MessageBox messageBox = new MessageBox(shell,
							SWT.ICON_ERROR | SWT.YES);
					messageBox.setText("��ʾ");
					messageBox.setMessage("��������Ļ");
					messageBox.open();
				} else {
					if (db.insert(sql) > -1) {
						table.removeAll();
						cha();
						MessageBox messageBox = new MessageBox(shell,
								SWT.ICON_INFORMATION | SWT.YES);
						messageBox.setText("��ʾ");
						messageBox.setMessage("��Ļ��ӳɹ�");
						messageBox.open();

					}
				}
			}
		});
		add.setBounds(228, 349, 80, 27);
		add.setText("�����Ļ");

		/*
		 * ��Ļ����
		 */
		int width = shell.getMonitor().getClientArea().width;
		int height = shell.getMonitor().getClientArea().height;
		int x = shell.getSize().x;
		int y = shell.getSize().y;
		if (x > width) {
			shell.getSize().x = width;
		}
		if (y > height) {
			shell.getSize().y = height;
		}
		shell.setLocation((width - x) / 2, (height - y) / 2);

		shell.open();
		shell.layout();
		cha();
		while (!(shell.isDisposed()))
			if (!(display.readAndDispatch()))
				display.sleep();
	}
}