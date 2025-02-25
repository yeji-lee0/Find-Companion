package yeji.DAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import yeji.DTO.CommDto;
import yeji.DTO.PostDto;

/**
 * 게시글 및 댓글에 대한 데이터 접근 객체 (DAO) 구현 클래스입니다.
 * MyBatis를 사용하여 데이터베이스와 상호작용합니다.
 * 
 * @author Yeji
 */
public class PostDaoImple implements PostDao {

	/** MyBatis SqlSession 객체 */	
	SqlSession ss;

	/** MyBatis 매퍼 네임스페이스 */
	private static final String NameSpace = "postMapper.";

	/**
	 * SqlSession 객체를 설정합니다.
	 * 
	 * @param ss MyBatis SqlSession 객체
	 */
	public void setSs(SqlSession ss) {
		this.ss = ss;
	}


	/**
	 * 게시글을 작성합니다.
	 * 
	 * @param postDto 작성할 게시글 정보를 담은 DTO 객체
	 */
	@Override
	public void writePost(PostDto postDto) {
		ss.insert(NameSpace+"writePost", postDto);
	}

	/**
	 * 게시글을 수정합니다.
	 * 
	 * @param postDto 수정할 게시글 정보를 담은 DTO 객체
	 */
	@Override
	public void updatePost(PostDto postDto) {
		ss.update(NameSpace+"updatePost", postDto);
	}

	/**
	 * 게시글을 삭제합니다.
	 * 
	 * @param postId 삭제할 게시글의 ID
	 */
	@Override
	public void deletePost(int postId) {
		ss.delete(NameSpace+"deletePost", postId);
	}
	
	
	/**
   * 댓글을 작성합니다.
   * 
   * @param commDto 작성할 댓글 정보를 담은 DTO 객체
   */
	@Override 
	public void writeComm(CommDto commDto) {
		ss.insert(NameSpace+"writeComm", commDto);
	}
	
	/**
   * 댓글을 수정합니다.
   * 
   * @param commDto 수정할 댓글 정보를 담은 DTO 객체
   */
	@Override 
	public void updateComm(CommDto commDto) {
		ss.update(NameSpace+"updateComm", commDto);
	}

	 /**
   * 댓글을 삭제합니다.
   * 
   * @param commDto 삭제할 댓글 정보를 담은 DTO 객체
   */
	@Override 
	public void deleteComm(CommDto commDto) {
		ss.delete(NameSpace+"deleteComm", commDto);
	}


	 /**
   * 게시글 ID로 특정 게시글을 가져옵니다.
   * 
   * @param postId 게시글의 ID
   * @return 해당 ID를 가진 게시글 객체
   */
	@Override	
	public PostDto getPostByPostId(int postId) {
		return ss.selectOne(NameSpace+"getPostByPostId", postId);
	}
	

	/**
   * 검색 조건에 맞는 게시글을 가져옵니다.
   * 
   * @param postDto 검색 조건을 담은 DTO 객체
   * @param startRow 가져올 게시글의 시작 행 (페이징 처리에 사용)
   * @param endRow 가져올 게시글의 종료 행 (페이징 처리에 사용)
   * @return 검색 조건에 맞는 게시글 목록을 담은 리스트
   */
	@Override 
	public List<PostDto> searchPost(PostDto postDto, int startRow, int endRow){
		Map<String, Object> params = new HashMap<>();
		params.put("all", postDto.getAll());
		params.put("title", postDto.getTitle());
		params.put("memberId", postDto.getMemberId());
		params.put("content", postDto.getContent());
		params.put("categoryId", postDto.getCategoryId());
		params.put("startRow", startRow);  // 첫 번째 페이지의 시작 행
		params.put("endRow", endRow);   // 첫 번째 페이지의 끝 행
		return ss.selectList(NameSpace+"searchPost", params);
	}

	/**
   * 검색 조건에 맞는 게시글의 총 개수를 가져옵니다.
   * 
   * @param postDto 검색 조건을 담은 DTO 객체
   * @return 검색 조건에 맞는 게시글의 총 개수
   */
	@Override 
	public int getSearchPostCount(PostDto postDto){
		Map<String, Object> params = new HashMap<>();
		params.put("all", postDto.getAll());
		params.put("title", postDto.getTitle());
		params.put("memberId", postDto.getMemberId());
		params.put("content", postDto.getContent());
		params.put("categoryId", postDto.getCategoryId());
		return ss.selectOne(NameSpace+"getSearchPostCount", params);
	}

	
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
	@Override 
	public List<PostDto> getMemberPostAll(String memberId, int startRow, int endRow){
		Map<String, Object> params = new HashMap<>();
		params.put("memberId", memberId);
		params.put("startRow", startRow);  // 첫 번째 페이지의 시작 행
		params.put("endRow", endRow);   // 첫 번째 페이지의 끝 행
		return ss.selectList(NameSpace+"getMemberPostAll", params);
	}

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
	@Override 
	public List<CommDto> getMemberCommAll(String memberId, int startRow, int endRow){
		Map<String, Object> params = new HashMap<>();
		params.put("memberId", memberId);
		params.put("startRow", startRow);  // 첫 번째 페이지의 시작 행
		params.put("endRow", endRow);   // 첫 번째 페이지의 끝 행
		return ss.selectList(NameSpace+"getMemberCommAll", params);
	}

	/**
	 * 특정 게시글에 달린 모든 댓글을 가져옵니다.
	 * 
	 * @param postId 게시글의 아이디 (해당 게시글에 달린 댓글을 조회)
	 * @return 해당 게시글에 달린 모든 댓글 목록을 담은 리스트
	 * 
	 * @see CommDto 댓글 DTO 클래스
	 */
	@Override 
	public List<CommDto> getCommOnThePost(int postId) {
		return ss.selectList(NameSpace+"getCommOnThePost", postId);
	}

	/**
   * 특정 게시글에 달린 댓글의 총 개수를 가져옵니다.
   * 
   * @param postId 게시글의 ID
   * @return 해당 게시글에 달린 댓글의 총 개수
   */
	@Override 
	public int getCommCountOnThePost(int postId) {
		return ss.selectOne(NameSpace+"getCommCountOnThePost", postId);
	}

	/**
	 * 특정 회원이 작성한 댓글의 총 개수를 가져옵니다.
	 * 
	 * @param memberId 회원의 아이디 (회원의 고유 ID)
	 * @return 해당 회원이 작성한 댓글의 총 개수 (int)
	 */
	@Override 
	public int getMemberCommCount(String memberId) {
		return ss.selectOne(NameSpace+"getMemberCommCount", memberId);
	}

	/**
	 * 특정 회원이 작성한 게시글의 총 개수를 가져옵니다.
	 * 
	 * @param memberId 회원의 아이디 (회원의 고유 ID)
	 * @return 해당 회원이 작성한 게시글의 총 개수 (int)
	 */
	@Override 
	public int getMemberPostCount(String memberId) {
		return ss.selectOne(NameSpace+"getMemberPostCount", memberId);
	}


}
