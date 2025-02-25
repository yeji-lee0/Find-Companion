package yeji.DAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import yeji.DTO.TasteDto;

/**
 * 취향 데이터 접근 객체 (DAO) 구현 클래스입니다.
 * MyBatis를 사용하여 데이터베이스와 상호작용하며,
 * 회원 및 게시글과 관련된 취향 데이터를 처리합니다.
 * 
 * @author Yeji
 */
public class TasteDaoImple implements TasteDao {
	SqlSession ss;
	
	/** MyBatis 매퍼 네임스페이스 */
	private static final String NameSpace = "tasteMapper.";

	public void setSs(SqlSession ss) {
		this.ss = ss;
	}
	
	 /**
   * 게시글에 특정 취향을 추가합니다.
   * 
   * @param postId 게시글 ID
   * @param tasteId 취향 ID
   */
	@Override
	public void addPostTaste(int postId, int tasteId) {
		Map<String, Object> param = new HashMap<>();
		param.put("postId", postId);
		param.put("tasteId", tasteId);
		ss.insert(NameSpace+"addPostTaste", param);
	}

	/**
   * 특정 게시글에 설정된 모든 취향을 삭제합니다.
   * 
   * @param postId 게시글 ID
   */
	@Override
	public void deletePostTasteAll(int postId) {
		ss.delete(NameSpace+"deletePostTasteAll", postId);	
	}
	
	 /**
   * 특정 게시글에 특정 취향이 선택되었는지 확인합니다.
   * 
   * @param postId 게시글 ID
   * @param tasteId 취향 ID
   * @return 선택되었으면 true, 그렇지 않으면 false
   */
	@Override 
	public boolean isPostTasteSelected(int postId, int tasteId) {
		Map<String, Object> params = new HashMap<>();
		params.put("postId", postId);
		params.put("tasteId", tasteId);
		int result = ss.selectOne(NameSpace+"isPostTasteSelected", params);
		return result > 0 ? true:false;
	}
	
	
	 /**
   * 특정 카테고리에 속하는 취향 목록을 가져옵니다.
   * 
   * @param categoryId 카테고리 ID
   * @return 해당 카테고리에 속하는 취향 목록
   */
	@Override
	public List<TasteDto> getTasteByCategoryId(int categoryId){
		return ss.selectList(NameSpace+"getTasteByCategoryId", categoryId);
	}
	
	/**
   * 특정 게시글에 해당하는 취향 목록을 가져옵니다.
   * 
   * @param postId 게시글 ID
   * @return 해당 게시글에 설정된 취향 목록
   */
	@Override
	public List<TasteDto> getPostTasteList(int postId){
		return ss.selectList(NameSpace+"getPostTasteList", postId);
	}
	
	
	/**
   * 모든 카테고리 목록을 가져옵니다.
   * 
   * @return 카테고리 목록
   */
	@Override 
	public List<TasteDto> getCategory(){
		return ss.selectList(NameSpace+"getCategory");
	}
	
	 /**
   * 카테고리 ID로 해당 카테고리의 이름을 가져옵니다.
   * 
   * @param categoryId 카테고리 ID
   * @return 해당 카테고리의 이름
   */
	@Override 
	public String getCategoryName(int categoryId){
		return ss.selectOne(NameSpace+"getCategoryName", categoryId);
	}
	
	
	/**
	 * 회원이 특정 취향을 선택했는지 확인합니다.
	 * 
	 * @param memberId 회원 ID
	 * @param tasteId 취향 ID
	 * @return 선택했으면 true, 그렇지 않으면 false
	 */
	@Override 
	public boolean isMemberTasteSelected(String memberId, int tasteId) {
		Map<String, Object> params = new HashMap<>();
		params.put("memberId", memberId);
		params.put("tasteId", tasteId);
		int result = ss.selectOne(NameSpace+"isMemberTasteSelected", params);
		return result > 0 ? true:false;
	}
	
	/**
	 * 모든 카테고리와 필드에 해당하는 취향 목록을 가져옵니다.
	 * 
	 * @return 모든 카테고리, 필드에 대한 취향 목록
	 */
	@Override 
	public List<TasteDto> getCategoryFieldTasteAll(){
		return ss.selectList(NameSpace+"getCategoryFieldTasteAll");
	}
	
	/**
	 * 특정 회원이 선택한 취향 목록을 모두 가져옵니다.
	 * 
	 * @param memberId 회원 ID
	 * @return 지정된 회원이 선택한 취향 목록
	 */
	@Override 
	public List<TasteDto> getMemberTasteAll(String memberId){
		return ss.selectList(NameSpace+"getMemberTasteAll", memberId);
	}
	
	/**
	 * 특정 회원의 취향을 모두 삭제합니다.
	 * 
	 * @param memberId 회원 ID
	 */
	@Override
	public void deleteMemberTasteAll(String memberId){
		ss.delete(NameSpace+"deleteMemberTasteAll", memberId);
	}
	
	/**
	 * 회원이 선택한 취향을 회원 취향 테이블에 추가합니다.
	 * 
	 * @param memberId 회원의 ID
	 * @param tasteId 회원이 선택한 취향의 ID
	 * 
	 * <p> 
	 * 해당 취향이 이미 존재하는지 확인하지 않고, 단순히 회원 ID와 취향 ID를 
	 * MEMBER_TASTE 테이블에 추가합니다.</p>
	 */
	@Override
	public void addMemberTaste(String memberId, int tasteId) {
		Map<String, Object> param = new HashMap<>();
		param.put("memberId", memberId);
		param.put("tasteId", tasteId);
		ss.insert(NameSpace+"addMemberTaste", param);
	}


}
