
public class methodCollection {

	
		
		
	 public boolean checkIMO( String IMO){
		 
		 //check Length
		 if(IMO.length() != 10){
			 System.out.println("incorrect length");
			 return false; 
		 }
		 
		 //check IMOP prefix
		 if(IMO.startsWith("IMO")){
			 IMO = IMO.replace("IMO","");
			 int sum =0;
			 int checksum=0;
			 int IMO_num = Integer.parseInt(IMO);
//			 System.out.println(IMO_num);
			 for(int i=2;i<=7;i++){
				 if(i==2){
					 checksum= IMO_num % 10;
//					 System.out.println("checksum " +checksum);
					 IMO_num = IMO_num/10;
				 }			 
				 sum = (IMO_num%10 )* i+ sum;
				 IMO_num = IMO_num/10;
//				 System.out.println(IMO_num + "--  ");
				 
			 }
//			 System.out.println("sum "+sum);
			 
			 if(sum%10 == checksum){
				 return true;
			 }
		 }
		 else{
			 return false;
		 }
		 
		 
		 return false;
			 
		 
	 }
	
}
