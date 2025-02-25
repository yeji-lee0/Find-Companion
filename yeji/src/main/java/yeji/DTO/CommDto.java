package yeji.DTO;

import java.util.Date;

import lombok.Data;

/**
 * 게시글에 달린 댓글을 나타내는 데이터 전송 객체 (DTO) 클래스입니다.
 * 이 객체는 댓글의 ID, 게시글 ID, 작성자 ID, 댓글 내용, 작성 시간을 담고 있습니다.
 * 
 * @author Yeji
 */
@Data
public class CommDto {
	/**
   * 댓글 ID
   * 댓글의 고유 식별자입니다.
   */
  private int commId;

  /**
   * 게시글 ID
   * 댓글이 달린 게시글의 고유 식별자입니다.
   */
  private int postId;

  /**
   * 회원 ID
   * 댓글을 작성한 회원의 고유 ID입니다.
   */
  private String memberId;

  /**
   * 댓글 내용
   * 댓글의 본문 내용을 저장합니다.
   */
  private String content;

  /**
   * 댓글 작성 시간
   * 댓글이 작성된 시간을 나타냅니다.
   */
  private Date uploadTime;
}
