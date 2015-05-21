public class IMOData {

	public static void main(String[] args) {

		methodCollection mc = new methodCollection();

		boolean validity = mc.checkIMO("IMO9074729");

		System.out.println(validity);
	}
}