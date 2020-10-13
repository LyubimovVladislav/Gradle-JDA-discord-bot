package discord.test.models;

public class OSValidator {
	private static String OS = System.getProperty("os.name").toLowerCase();
	private static int userOS=0;
	
	public OSValidator() {
		defineUserOS();
	}
	
	private static boolean defineUserOS(){
		if (isWindows()) {
			return true;
		} else if (isMac()) {
			return true;
		} else if (isUnix()) {
			return true;
		} else return isSolaris();
	}
	
	private static boolean isWindows() {
		userOS=1;
		return (OS.contains("win"));
	}
	
	private static boolean isMac() {
		userOS=2;
		return (OS.contains("mac"));
	}
	
	private static boolean isUnix() {
		userOS=3;
		return (OS.contains("nix") || OS.contains("nux") || OS.indexOf("aix") > 0);
	}
	
	private static boolean isSolaris() {
		userOS=4;
		return (OS.contains("sunos"));
	}
	
	public int getUserOS(){
		return userOS;
	}
}
