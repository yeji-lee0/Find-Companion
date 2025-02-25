package yeji.DAO;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import yeji.DTO.MemberDto;

public class MemberDaoImple implements MemberDao{

	SqlSession ss;
	private static final String NameSpace = "memberMapper.";

	public void setSs(SqlSession ss) {
		this.ss = ss;
	}
	
	/**
	 * 아이디로 회원 정보를 가져옵니다.
	 * 
	 * @param id 회원의 아이디
	 * @return 해당 아이디를 가진 회원의 MemberDto 객체
	 */
	@Override 
	public MemberDto getMember(String id) {
		return ss.selectOne(NameSpace+"getMember", id);
	}
	
	/**
	 * 회원을 탈퇴시킵니다.
	 * 
	 * @param id 탈퇴할 회원의 아이디
	 */
	@Override 
	public void deleteMember(String id) {
		ss.delete(NameSpace+"deleteMember", id);
	}
	
	/**
	 * 회원 정보를 수정합니다.
	 * 
	 * @param dto 수정할 회원 정보가 담긴 MemberDto 객체
	 */
	@Override 
	public void updateMember(MemberDto dto) {
		ss.update(NameSpace+"updateMember", dto);
	}
	
	/**
	 * 회원 아이디와 비밀번호로 로그인합니다.
	 * 
	 * @param id 회원 아이디
	 * @param pw 회원 비밀번호
	 * @return 로그인 성공 시 해당 회원의 MemberDto 객체, 실패 시 null 반환
	 */
	@Override 
	public MemberDto login(String id, String pw) {
		Map<String, String> param = new HashMap<>();
		
		
		param.put("id", id);
		param.put("pw", pw);
		
		return ss.selectOne(NameSpace+"login", param);
	}
	
	/**
	 * 아이디 중복 여부를 확인합니다.
	 * 
	 * @param id 확인할 회원 아이디
	 * @return true면 중복된 아이디, false면 중복되지 않은 아이디
	 */
	@Override 
	public boolean isIdDuplicate(String id) {
		int result = ss.selectOne(NameSpace+"checkIdDuplicate", id);
		
		return result > 0 ? true : false;
	}
	
	/**
	 * 새로운 회원을 가입시킵니다.
	 * 
	 * @param dto 회원가입에 필요한 정보를 담은 MemberDto 객체
	 */
	@Override 
	public void join(MemberDto dto) {
		ss.insert(NameSpace+"joinMember", dto);
	}
}
