using System;
using System.Text;

namespace TabelaConversao
{
	public class TableGen
	{
		public static string Gen (Int64 n)
		{
			string s = n.ToString ();
			return new StringBuilder(s).Append("\t\t").
				Append(Convert.ToString(Convert.ToInt64(s, 10), 8)).
				Append("\t\t").Append(Convert.ToString(Convert.ToInt64(s, 10), 16)).
				Append("\t\t").Append(Convert.ToString(Convert.ToInt64(s, 10), 2)).
				Append("\t\t").ToString();
		}
	}
}

