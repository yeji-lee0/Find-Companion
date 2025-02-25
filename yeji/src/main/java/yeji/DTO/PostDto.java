package yeji.DTO;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * 게시글을 나타내는 DTO 클래스입니다.
 * 이 객체는 게시글의 ID, 작성자 정보, 카테고리, 제목, 내용, 작성 시간, 
 * 게시글에 포함된 취향 목록 및 댓글 목록을 포함합니다.
 * 
 * @author Yeji
 */
@Data
public class PostDto {
	/** 
	 * 게시글 ID<br>
	 * 게시글의 고유 식별자입니다.
	 */
	private int postId;           // 게시글 ID

	/** 
	 * 회원 ID <br>
	 * 게시글을 작성한 회원의 고유 ID입니다.
	 */
	private String memberId;      // 작성한 회원의 ID

	/** 
	 * 카테고리 ID <br>
	 * 게시글이 속한 카테고리의 고유 ID입니다.
	 */
	private Integer categoryId;       // 카테고리 ID

	/** 
	 * 카테고리 이름 <br>
	 * 게시글이 속한 카테고리의 이름입니다.<br>
	 * 예: "여행", "식사"
	 */
	private String categoryName;  // 카테고리 이름 (여행, 식사 등)

	/** 
	 * 게시글 제목 <br>
	 * 게시글의 제목입니다.
	 */
	private String title;         // 게시글 제목

	/** 
	 * 게시글 내용 <br>
	 * 게시글의 본문 내용을 저장합니다.
	 */
	private String content;       // 게시글 내용

	/** 
	 * 게시글 작성 시간 <br>
	 * 게시글이 작성된 시간입니다.
	 */
	private Date uploadTime;      // 게시글 작성 시간



	/** 
	 * 모임 일정 <br>
	 * 모임이 예정된 날짜를 나타냅니다.<br>
	 * 날짜 형식: yyyy-MM-dd
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")  // 날짜 형식 지정
	private Date meetingDate;     // 모임 일정 (모임 날짜)

	/** 
	 * 게시글 관련 취향 목록 <br>
	 * 게시글에 포함된 취향 정보를 저장합니다.
	 */
	private List<TasteDto> tasteList; // 게시글에 관련된 취향 목록

	/** 
	 * 댓글 목록<br>
	 * 게시글에 달린 댓글을 나타냅니다.
	 */
	private List<CommDto> commList;  // 게시글에 달린 댓글 목록

	/** 
	 * 댓글 수<br>
	 * 게시글에 달린 댓글의 개수를 나타냅니다.
	 */
	private int commCount;

	/** 
	 * 게시글 검색어 <br>
	 * 전체 검색 시 사용하는 검색어를 저장합니다.
	 */
	private String all; // 게시글 전체 검색어

	/** 
	 * 그룹화된 취향 목록 <br>
	 * 카테고리별로 그룹화된 취향 정보를 저장합니다.<br>
	 * - 첫 번째 키: 카테고리 이름<br>
	 * - 두 번째 키: 필드 이름<br>
	 * - 값: 해당 필드에 속한 취향 목록
	 */
	private Map<String, Map<String, List<TasteDto>>> Groupedtaste; // 카테고리별로 그룹화된 취향
}
