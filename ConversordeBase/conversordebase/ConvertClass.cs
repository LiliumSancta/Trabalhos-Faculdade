using System;
using System.Text.RegularExpressions;
using Gtk;

namespace conversordebase
{
	public class ConvertClass
	{
		public ConvertClass ()
		{
		}
		public static string conver(string numero, int base_origen, int base_final)
		{
			int base_o = 0, base_f = 0;
			string reg = "";

			switch (base_origen)
			{
				case 1: base_o = 2;  reg = "^[ 0-1 ]+$";       break;
				case 2: base_o = 8;  reg = "^[ 0-7 ]+$";       break;
				case 3: base_o = 10; reg = "^[ 0-9 ]+$";       break;
				case 4: base_o = 16; reg = "^[ 0-9a-fA-F ]+$"; break;
			}

			switch (base_final)
			{
				case 1: base_f = 2;  break;
				case 2: base_f = 8;  break;
				case 3: base_f = 10; break;
				case 4: base_f = 16; break;
			}

			Regex myRegex = new Regex(reg);

			if (!myRegex.IsMatch(numero))
			{
				return "ERRO: O número digitado não corresponde a base selecionada.\n";
			}
			else {
				return Convert.ToString(Convert.ToInt64(numero.Replace(" ",""), base_o), base_f).ToUpper();
			}
		
		}
	}
}

