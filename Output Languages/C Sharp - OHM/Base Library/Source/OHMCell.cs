// OHMCell.cs created with MonoDevelop
// User: charlie at 11:11 20/01/2009
//
// To change standard headers go to Edit->Preferences->Coding->Standard Headers
//

using System;

namespace OHM
{
	public class OHMCell
	{
		private byte[] value;
    
		private long timestamp;

		public OHMCell(byte[] value)
		{
			this.value = value;
			this.timestamp = 0;
		}
		
		public OHMCell(byte[] value, long timestamp)
		{
			this.value = value;
			this.timestamp = timestamp;
		}

		public byte[] Value
		{
			get { return value; }
			set { this.value = value; }
		}

		public long Timestamp
		{
			get { return timestamp; }
			set { this.timestamp = value; }
		}
	}
}
