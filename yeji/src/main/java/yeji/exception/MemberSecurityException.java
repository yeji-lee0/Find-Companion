package yeji.exception;

public class MemberSecurityException extends SecurityException {
	 public MemberSecurityException(String message) {
		 super(message);
		 //System.out.println("exception: 로그인 안 된 상태에서 접근 시도");
	   }
}
