package yeji.DAO;

import java.util.List;

import yeji.DTO.CommDto;
import yeji.DTO.PostDto;

/**
 * 게시글 및 댓글에 대한 데이터 접근 객체 (DAO) 인터페이스입니다.
 * 이 인터페이스는 게시글 및 댓글의 CRUD 작업과 관련된 메서드를 정의합니다.
 * 
 * @author Yeji
 */
public interface PostDao {
	/**
	 * 특정 회원이 작성한 모든 게시글을 가져옵니다.
	 * 
	 * @param memberId 회원의 아이디 (회원의 고유 ID)
	 * @param startRow 가져올 게시글의 시작 행 (페이징 처리에 사용)
	 * @param endRow 가져올 게시글의 종료 행 (페이징 처리에 사용)
	 * @return 해당 회원이 작성한 게시글 목록을 담은 리스트 
	 * 
	 * @see PostDto 게시글 DTO 클래스
	 */
	List<PostDto> getMemberPostAll(String memberId, int startRow, int endRow);

	/**
	 * 특정 회원이 작성한 모든 댓글을 가져옵니다.
	 * 
	 * @param memberId 회원의 아이디 (회원의 고유 ID)
	 * @param startRow 가져올 댓글의 시작 행 (페이징 처리에 사용)
	 * @param endRow 가져올 댓글의 종료 행 (페이징 처리에 사용)
	 * @return 해당 회원이 작성한 댓글 목록을 담은 리스트 
	 * 
	 * @see CommDto 댓글 DTO 클래스
	 */
	List<CommDto> getMemberCommAll(String memberId, int startRow, int endRow);

	/**
	 * 특정 게시글에 달린 모든 댓글을 가져옵니다.
	 * 
	 * @param postId 게시글의 아이디 (해당 게시글에 달린 댓글을 조회)
	 * @return 해당 게시글에 달린 모든 댓글 목록을 담은 리스트
	 * 
	 * @see CommDto 댓글 DTO 클래스
	 */
	List<CommDto> getCommOnThePost(int postId);


	/**
	 * 특정 회원이 작성한 댓글의 총 개수를 가져옵니다.
	 * 
	 * @param memberId 회원의 아이디 (회원의 고유 ID)
	 * @return 해당 회원이 작성한 댓글의 총 개수 (int)
	 */
	int getMemberCommCount(String memberId);

	/**
	 * 특정 회원이 작성한 게시글의 총 개수를 가져옵니다.
	 * 
	 * @param memberId 회원의 아이디 (회원의 고유 ID)
	 * @return 해당 회원이 작성한 게시글의 총 개수 (int)
	 */
	int getMemberPostCount(String memberId);

	/**
   * 특정 게시글에 달린 댓글의 총 개수를 가져옵니다.
   *
   * @param postId 게시글의 아이디 (해당 게시글의 댓글 개수를 조회)
   * @return 해당 게시글에 달린 댓글의 총 개수 (int)
   */
	int getCommCountOnThePost(int postId);

	
	
	/**
   * 검색 조건에 맞는 게시글을 가져옵니다.
   *
   * @param postDto 검색 조건을 담은 DTO 객체
   * @param startRow 가져올 게시글의 시작 행 (페이징 처리에 사용)
   * @param endRow 가져올 게시글의 종료 행 (페이징 처리에 사용)
   * @return 검색 조건에 맞는 게시글 목록을 담은 리스트
   *
   * @see PostDto 게시글 DTO 클래스
   */
	List<PostDto> searchPost(PostDto postDto, int startRow, int endRow);

	/**
   * 검색 조건에 맞는 게시글의 총 개수를 가져옵니다.
   *
   * @param postDto 검색 조건을 담은 DTO 객체
   * @return 검색 조건에 맞는 게시글의 총 개수 (int)
   */
	int getSearchPostCount(PostDto postDto);

	
	
	/**
   * 특정 게시글을 게시글 ID로 가져옵니다.
   *
   * @param postId 게시글의 ID
   * @return 해당 ID를 가진 게시글 객체
   *
   * @see PostDto 게시글 DTO 클래스
   */
	PostDto getPostByPostId(int postId);

	 /**
   * 댓글을 작성합니다.
   *
   * @param commDto 작성할 댓글 정보를 담은 DTO 객체
   *
   * @see CommDto 댓글 DTO 클래스
   */
	void writeComm(CommDto commDto);

	/**
   * 댓글을 수정합니다.
   *
   * @param commDto 수정할 댓글 정보를 담은 DTO 객체
   *
   * @see CommDto 댓글 DTO 클래스
   */
	void updateComm(CommDto commDto);

	 /**
   * 댓글을 삭제합니다.
   *
   * @param commDto 삭제할 댓글 정보를 담은 DTO 객체
   *
   * @see CommDto 댓글 DTO 클래스
   */
	void deleteComm(CommDto commDto);

	
	
	 /**
   * 게시글을 작성합니다.
   *
   * @param postDto 작성할 게시글 정보를 담은 DTO 객체
   *
   * @see PostDto 게시글 DTO 클래스
   */
	void writePost(PostDto postDto);

	/**
   * 게시글을 삭제합니다.
   *
   * @param postId 삭제할 게시글의 ID
   */
	void deletePost(int postId);

	 /**
   * 게시글을 수정합니다.
   *
   * @param postDto 수정할 게시글 정보를 담은 DTO 객체
   *
   * @see PostDto 게시글 DTO 클래스
   */
	void updatePost(PostDto postDto);

	
}
