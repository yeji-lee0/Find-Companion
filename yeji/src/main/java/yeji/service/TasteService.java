package yeji.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yeji.DAO.TasteDao;
import yeji.DTO.TasteDto;

/**
 * 취향 관련 서비스 클래스입니다.
 * DAO를 통해 취향 데이터를 가져오고, 필요한 비즈니스 로직을 처리합니다.
 * 
 * @author Yeji
 */
@Service
public class TasteService {
	@Autowired
	TasteDao tasteDao;

	/**
	 * 취향 목록을 카테고리와 필드별로 그룹화합니다.
	 *
	 * @param tasteList 그룹화할 취향 목록
	 * @return 
	 * - 카테고리명 → 필드명 → 취향 목록 형태로 그룹화된 맵<br>  
	 * - 매개변수가 비어 있거나 null이면 빈 맵을 반환
	 */
	public Map<String, Map<String, List<TasteDto>>> groupTastes(List<TasteDto> tasteList) {
		// 취향 리스트가 비어 있으면 빈 맵 반환
		if (tasteList == null || tasteList.isEmpty()) {
			return new HashMap<>();  
		}

		// 카테고리 → 필드 → 취향 목록 형태로 그룹화
		Map<String, Map<String, List<TasteDto>>> groupedTastes = new HashMap<>();
		for (TasteDto taste : tasteList) {
			String categoryName = taste.getCategoryName();  // 카테고리 이름
			String fieldName = taste.getFieldName();        // 필드 이름

			// 카테고리와 필드가 없으면 초기화하고 취향 추가
			groupedTastes
			.computeIfAbsent(categoryName, k -> new HashMap<>())
			.computeIfAbsent(fieldName, k -> new ArrayList<>())
			.add(taste);
		}
		 
		return groupedTastes;
	}

	

	/**
	 * 모든 카테고리와 필드의 취향 목록을 가져옵니다.
	 * 
	 * @return 모든 카테고리와 필드에 대한 취향 목록
	 */
	public List<TasteDto> getCategoryFieldTasteAll(){
		return tasteDao.getCategoryFieldTasteAll();
	}

	 /**
   * 모든 카테고리 목록을 가져옵니다.
   * 
   * @return 카테고리 목록
   */
	public List<TasteDto> getCategory() {
		return tasteDao.getCategory();
	}

	 /**
   * 특정 카테고리에 해당하는 취향 목록을 가져옵니다.
   * 
   * @param categoryId 카테고리 ID
   * @return 해당 카테고리에 속하는 취향 목록
   */
	public List<TasteDto> getTastesByCategoryId(int categoryId) {
		return tasteDao.getTasteByCategoryId(categoryId);
	}
}
