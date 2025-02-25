package yeji.DTO;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * 회원 정보를 나타내는 DTO 클래스입니다.
 * 이 객체는 회원의 기본 정보, 취향 목록, 게시글 목록, 댓글 목록 등을 포함합니다.
 * 또한, 회원의 취향은 카테고리별로 그룹화된 형태로 저장됩니다.
 * 
 * @author Yeji
 */
@Data
public class MemberDto {
  /** 회원의 아이디 */
  private String id;            // 회원의 아이디
  
  /** 회원의 비밀번호 */
  private String pw;            // 회원의 비밀번호
  
  /** 회원의 이름 */
  private String name;          // 회원의 이름
  
  /** 회원의 성별 ("M" 또는 "F") */
  private String gender;        // 회원의 성별
  
  /** 회원의 생년월일 */
  @DateTimeFormat(pattern = "yyyy-MM-dd") // 날짜 형식 지정
  private Date birth;           // 회원의 생년월일
  
  /** 회원의 이메일 */
  private String email;         // 회원의 이메일
  
  /** 회원 가입 일자 */
  private Date regDate;         // 회원 가입 일자
  
 
  
  /** 회원이 작성한 글 목록 */
  private List<PostDto> postList;    // 회원의 글 목록
  
  /** 회원이 작성한 글 수 */	
  private int postCount;// 회원의 글 수
  
  /** 회원이 작성한 댓글 목록 */
  private List<CommDto> commList;    // 회원의 댓글 목록
  
  /** 회원이 작성한 댓글 수 */
  private int commCount;// 회원의 댓글 수
  
  /** 카테고리별로 그룹화된 취향 목록 */
  private Map<String, Map<String, List<TasteDto>>> Groupedtaste; // 카테고리별로 그룹화된 취향
}
