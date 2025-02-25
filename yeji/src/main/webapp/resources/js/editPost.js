window.addEventListener("load", function () {
  const updateBtn = document.querySelector("#update-btn");
  updateBtn.disabled = true; // 처음엔 등록 버튼 비활성화
  const categorySelect = document.querySelector("#category-option");
  const titleInput = document.querySelector("#title-input");
  const meetingDateInput = document.querySelector("#meeting-date-input");
  const contentInput = document.querySelector("#content-input");
  const tasteContainer = document.querySelector("#taste-container");


  //console.log(tasteContainer); // tasteContainer가 null인지 확인

  // 텍스트 영역과 입력 필드의 글자 수를 계산하여 span에 표시
  contentInput.addEventListener("input", function () {
    const msg = contentInput.nextElementSibling; // <span> 태그
    const length = contentInput.value.length;
    msg.textContent = `${length}/1000`;

    // 등록 버튼 상태 업데이트
    toggleSubmitButton();
  });

  // 카테고리 선택이 변경될 때마다 버튼 활성화 여부 확인
  categorySelect.addEventListener("change", function () {
    toggleSubmitButton();
    const categoryId = this.value;
    const postId = document.querySelector("input[name='postId']").value; // 게시글 ID

    if (categoryId === "0") {
      tasteContainer.style.display = "none";
      tasteContainer.innerHTML = "";
      return; // 서버로 요청하지 않음
    }

    // tasteContainer가 null인 경우를 방지
    if (tasteContainer) {
      // Ajax 요청으로 취향 데이터 가져오기
      fetch(
        `/yeji/post/getTastesForEditPost?categoryId=${categoryId}&postId=${postId}`
      )
        .then((response) => response.json())
        .then((data) => {
          //console.log("취향 데이터:", data); // 취향 데이터 출력
          if (data && data.length > 0) {
            const categoryName = data[0].categoryName;
            const filteredData = data.filter(
              (item) => item.tasteId && item.tasteId !== 0
            );
            const groupedData = filteredData.reduce((acc, curr) => {
              if (!acc[curr.fieldName]) {
                acc[curr.fieldName] = [];
              }
              acc[curr.fieldName].push(curr);
              return acc;
            }, {});

            tasteContainer.innerHTML = `
              <fieldset class="category-div">
                <legend><h3>${categoryName} 취향 선택</h3></legend>
                ${Object.entries(groupedData)
                  .map(
                    ([fieldName, tastes]) => `
                    <div class="field-ul">
                      <h4>${fieldName}</h4> <!-- 필드 이름 표시 -->
                      <div class="taste-ul">
                        ${tastes
                          .map(
                            (taste) => `
                          <input type="checkbox" name="selectedTastes" value="${
                            taste.tasteId
                          }" id="taste-${
                              taste.tasteId
                            }" class="taste-input hidden-checkbox" ${
                              taste.selected ? "checked" : ""
                            }>
                          <label for="taste-${
                            taste.tasteId
                          }" class="styled-checkbox">${taste.tasteName}</label>
                        `
                          )
                          .join("")}
                      </div>
                    </div>
                  `
                  )
                  .join("")}
              </fieldset>
            `;
            tasteContainer.style.display = "block";
          } else {
            tasteContainer.style.display = "none";
            tasteContainer.innerHTML = "";
          }
        })
        .catch((error) => {
          console.error("Error fetching tastes:", error);
          tasteContainer.style.display = "none";
          tasteContainer.innerHTML = "";
        });
    }
  });

  // 등록 버튼 활성화 여부를 판단하는 함수
  function toggleSubmitButton() {
    const categorySelected = categorySelect.value !== "0";
    const titleFilled = titleInput.value.trim() !== "";
    const meetingDateFilled = meetingDateInput.value.trim() !== "";
    const contentFilled = contentInput.value.trim() !== "";

    if (categorySelected && titleFilled && meetingDateFilled && contentFilled) {
      updateBtn.disabled = false;
    } else {
      updateBtn.disabled = true;
    }
  }
});
