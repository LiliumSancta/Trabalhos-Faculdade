using System;
using System.Diagnostics;
using System.Reflection;
using Gtk;
using conversordebase;

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

	protected void OnButton1Clicked (object sender, EventArgs e)
	{

		if (this.num1.Text == null || this.num1.Text == "")
		{
			textview4.Buffer.Text += "ERRO: Nenhum numero digitado.\n";
		}
		else if (this.combobox1.Active == 0)
		{
			textview4.Buffer.Text += "ERRO: Base de origen nao foi selecionada.\n";
		}
		else if (this.combobox2.Active == 0)
		{
			textview4.Buffer.Text += "ERRO: Base final nao foi selecionada.\n";
		}
		else {
			try{
				textview4.Buffer.Text = ConvertClass.conver(this.num1.Text, this.combobox1.Active, this.combobox2.Active);
			}
			catch (Exception ex){
				MessageDialog md = new MessageDialog(this, DialogFlags.DestroyWithParent, MessageType.Error, ButtonsType.Close, "Error: Tentativa de conversao de numero maior que 64 bits. \n"+ ex.ToString());
				md.Run();
				md.Destroy();
			}
		}
		textview4.ScrollToIter(textview4.Buffer.EndIter, 0, true, 0, 0);
	}


	protected void OnNum1Changed (object sender, EventArgs e)
	{
		this.num1.Text = this.num1.Text.ToUpper();
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
}
