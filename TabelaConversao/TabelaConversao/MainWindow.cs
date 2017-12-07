using System;
using System.Threading;
using System.Threading.Tasks;
using System.Diagnostics;
using System.Reflection;
using System.Text;
using Gtk;
using TabelaConversao;

public partial class MainWindow: Gtk.Window
{
	public MainWindow () : base (Gtk.WindowType.Toplevel)
	{
		Build ();
	}

	protected void OnDeleteEvent (object sender, DeleteEventArgs a)
	{
		Application.Quit ();
		a.RetVal = true;
	}

	void ThreadRoutine ()
	{
		Int64 i, n = Convert.ToInt64 (this.entry1.Text);
		Task t = Task.Factory.StartNew( () => {

			for (i = 0; i <= n; ++i) {
				prgMain.Fraction = ((double) i / n);
				this.textview4.Buffer.Text += new StringBuilder (TableGen.Gen (i).ToUpper ()).
					Append ("\n").ToString ();
			}
		} );
		t.Wait();

	}

	protected void OnButton1Clicked (object sender, EventArgs e)
	{

		//Thread thr = new Thread (new ThreadStart (ThreadRoutine));
		//thr.IsBackground = true;
		//thr.Start ();
		ThreadRoutine ();
	}

	protected void OnButton2Clicked (object sender, EventArgs e)
	{
		AboutDialog dialog = new AboutDialog ();

		dialog.ProgramName = FileVersionInfo.GetVersionInfo(Assembly.GetEntryAssembly().Location).ProductName;
		dialog.Version = FileVersionInfo.GetVersionInfo(Assembly.GetEntryAssembly().Location).FileVersion.Substring(0,5);
		dialog.Comments = FileVersionInfo.GetVersionInfo(Assembly.GetEntryAssembly().Location).Comments;
		dialog.Authors = new string [] {FileVersionInfo.GetVersionInfo(Assembly.GetEntryAssembly().Location).LegalCopyright, "Lucas Dias de Souza"};
		dialog.Website = "mailto:inu-kai@hotmail.com";
		dialog.Copyright = FileVersionInfo.GetVersionInfo (Assembly.GetEntryAssembly ().Location).LegalCopyright;

		dialog.Run ();
		dialog.Destroy ();
	}

	protected void OnEntry1Changed (object sender, EventArgs e)
	{
		this.entry1.Text = this.entry1.Text.Replace (" ", "");
	}
}
