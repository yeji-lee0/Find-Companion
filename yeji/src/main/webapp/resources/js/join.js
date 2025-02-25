window.addEventListener("load", function () {
  const joinButton = document.querySelector(".join-btn");
  joinButton.disabled = true; // 초기 상태에서 회원가입 버튼 비활성화

  let isIdDuplicate = true; // 전역 변수로 아이디 중복 검사 결과를 저장할 변수

  // 회원가입 버튼 상태 업데이트 함수
  function updateJoinButtonState() {
    if (
      idRegex.test(idInput.value) &&
      !idCheckButton.disabled &&
      !isIdDuplicate && // 중복 검사 통과했는지 확인
      passwordRegex.test(passwordInput.value) &&
      passwordInput.value === passwordInput2.value &&
      nameRegex.test(nameInput.value) &&
      emailRegex.test(emailInput.value) &&
      emailInput.value.length <= 50
    ) {
      joinButton.disabled = false;
    } else {
      joinButton.disabled = true;
    }
  }

  // 아이디 부분---------------------------------------------------------
  const idDiv = document.querySelector(".id-div");
  const idCheckButton = idDiv.querySelector(".id-check-btn");
  idCheckButton.disabled = true; // 초기 상태에서 아이디 중복확인 버튼 비활성화

  const idInput = idDiv.querySelector("#id-input");
  const idMsg = idDiv.querySelector(".msg");
  const checkMsg = idDiv.querySelector(".check");
  const idRegex = /^[a-zA-Z][a-zA-Z0-9]{3,19}$/;

  // 아이디 입력하면 아이디 유효성 검사
  idInput.addEventListener("input", function () {
    const idValue = idInput.value;
    if (!idRegex.test(idValue)) {
      idMsg.textContent =
        "아이디는 알파벳으로 시작하는, 4자 이상 20자 이하의 알파벳 또는 알파벳과 숫자 조합으로 입력해 주세요.";
      idMsg.style.padding = "10px 15px";
      idMsg.style.border = "1px solid #343231";
      idMsg.style.borderRadius = "20px";
      idMsg.style.marginBottom = "10px";
      idCheckButton.disabled = true;
    } else {
      idMsg.textContent = "";
      idMsg.style.padding = "";
      idMsg.style.border = "";
      idMsg.style.borderRadius = "";
      idMsg.style.marginBottom = "";
      idCheckButton.disabled = false;
    }

    updateJoinButtonState(); // 상태 업데이트
  });

  // 아이디 중복 검사 함수
  async function idCheck(idValue) {
    try {
      const response = await fetch(`/yeji/member/checkId?id=${idValue}`);
      const data = await response.json(); // JSON 응답을 처리
      //console.log(data.isIdDuplicate);
      return data.isIdDuplicate;
    } catch (error) {
      console.error("Error:", error);
      return true;
    }
  }

  // 중복 확인 버튼 클릭하면 결과 알림창 열림
  idCheckButton.addEventListener("click", async function () {
    const idValue = idInput.value;
    let idCheckResult = idValue + "는 ";

    try {
      isIdDuplicate = await idCheck(idValue); // 아이디 중복 검사 결과 저장
      updateJoinButtonState(); // 버튼 상태 업데이트

      if (isIdDuplicate) {
        idCheckResult +=
          "이미 사용 중인 아이디입니다. \n다른 아이디를 입력해주세요.";
        idInput.focus(); // 중복된 경우 아이디 입력창에 포커스
      } else {
        idCheckResult += "사용 가능한 아이디입니다.";
        document.querySelector("#password-input").focus(); // 아이디가 사용 가능하면 비밀번호 입력창에 포커스
        checkMsg.textContent = "✔";
      }

      alert(idCheckResult); // 결과 알림창 띄움
    } catch (error) {
      console.error("아이디 중복 검사 중 오류 발생:", error);
      alert("아이디 중복 확인 중 오류가 발생했습니다.");
    }
  });

  // 비밀번호 부분---------------------------------------------------------
  const passwordDiv = document.querySelector(".password-div");

  const passwordInput = passwordDiv.querySelector("#password-input");
  const passwordInput2 = passwordDiv.querySelector("#password-input2");
  passwordInput2.disabled = true; // 초기 상태에서 비밀번호 확인 입력 비활성화

  const passwordMsg = passwordDiv.querySelector(".msg");
  const passwordcheckMsg = passwordDiv.querySelector(".check");
  const passwordMsg2 = passwordDiv.querySelectorAll(".msg")[1];
  const passwordcheckMsg2 = passwordDiv.querySelectorAll(".check")[1];
  const passwordRegex =
    /^[a-zA-Z](?=.*[a-zA-Z0-9!@#$%^&*()_+={}\[\]:;"'<>,.?\/\\|-]).{7,19}$/;

  // 패스워드 입력하면 비밀번호 유효성 검사
  passwordInput.addEventListener("input", function () {
    const passwordValue = passwordInput.value;
    if (!passwordRegex.test(passwordValue)) {
      passwordcheckMsg.textContent = " ";

      passwordMsg.textContent =
        "비밀번호는 알파벳으로 시작하는, 8자 이상 20자 이하의 알파벳, 숫자, 특수문자 조합으로 입력해 주세요.";
      passwordMsg.style.padding = "10px 15px";
      passwordMsg.style.border = "1px solid #343231";
      passwordMsg.style.borderRadius = "20px";
      passwordMsg.style.marginTop = "5px";
      passwordMsg.style.marginBottom = "5px";
      passwordInput2.disabled = true;
    } else {
      passwordMsg.textContent = "";
      passwordMsg.style.padding = "";
      passwordMsg.style.border = "";
      passwordMsg.style.borderRadius = "";
      passwordMsg.style.marginTop = "";
      passwordMsg.style.marginBottom = "";
      passwordcheckMsg.textContent = "✔";

      passwordInput2.disabled = false;
    }

    updateJoinButtonState(); // 상태 업데이트
  });

  // 비밀번호 두번 입력한 게 같은지 확인: 비밀번호 유효성 검사 통과해야 활성화.
  passwordInput2.addEventListener("input", function () {
    const passwordValue = passwordInput.value;
    const passwordValue2 = passwordInput2.value;

    if (passwordValue != passwordValue2) {
      passwordcheckMsg2.textContent = "";
      passwordMsg2.style.padding = "10px 15px";
      passwordMsg2.style.border = "1px solid #343231";
      passwordMsg2.style.borderRadius = "20px";
      passwordMsg2.style.marginTop = "5px";
      passwordMsg2.style.marginBottom = "5px";
      passwordMsg2.textContent =
        "비밀번호가 일치하지 않습니다. 다시 입력해 주세요.";
    } else {
      passwordMsg2.textContent = "";
      passwordMsg2.style.padding = "";
      passwordMsg2.style.border = "";
      passwordMsg2.style.borderRadius = "";
      passwordMsg2.style.marginTop = "";
      passwordMsg2.style.marginBottom = "";
      passwordcheckMsg2.textContent = "✔";

      document.querySelector("#name-input").focus(); // 일치하면 이름 입력창으로 포커스 이동
    }

    updateJoinButtonState(); // 상태 업데이트
  });

  // 이름 부분-------------------------------------------------------------
  const nameDiv = document.querySelector(".name-div");

  const nameInput = nameDiv.querySelector("#name-input");
  const nameCheckMsg = nameDiv.querySelector(".check");
  const nameMsg = nameDiv.querySelector(".msg");
  const nameRegex = /^[a-zA-Z가-힣\s]{2,30}$/;

  // 이름 유효성 검사:  최소 2자, 최대 30자, 특수 문자 제외
  nameInput.addEventListener("input", function () {
    const nameValue = nameInput.value;
    if (!nameRegex.test(nameValue)) {
      nameCheckMsg.textContent = " ";
      nameMsg.style.padding = "10px 15px";
      nameMsg.style.border = "1px solid #343231";
      nameMsg.style.borderRadius = "20px";
      nameMsg.style.marginBottom = "10px";
      nameMsg.textContent =
        "이름은 2자 이상 30자 이내의 한글 또는 알파벳으로 입력해야 하며, 특수문자는 사용할 수 없습니다.";
    } else {
      nameMsg.textContent = "";
      nameMsg.style.padding = "";
      nameMsg.style.border = "";
      nameMsg.style.borderRadius = "";
      nameMsg.style.marginBottom = "";
      nameCheckMsg.textContent = "✔";
    }

    updateJoinButtonState(); // 상태 업데이트
  });

  //생년월일 부분----------------------------------------------------
  const today = new Date();
  const nowYear = today.getFullYear();
  const nowMonth = today.getMonth() + 1;
  const nowDate = today.getDate();

  const yearSelect = document.querySelector("#year-select");
  const monthSelect = document.querySelector("#month-select");
  const daySelect = document.querySelector("#day-select");

  // Option을 추가하는 함수
  function addOption(select, value, textContent, disabled = false) {
    const option = document.createElement("option");
    option.value = value;
    option.textContent = textContent;
    if (disabled) option.disabled = true;
    select.appendChild(option);
  }

  // 연도 - 현재 연도부터 1900년까지
  for (let i = nowYear; i >= 1900; i--) {
    addOption(yearSelect, i, i);
  }

  // 월 - 1월부터 12월까지
  function updateMonths() {
    const selectedYear = parseInt(yearSelect.value);
    monthSelect.innerHTML = "";

    for (let i = 1; i <= 12; i++) {
      const isDisabled = selectedYear === nowYear && i > nowMonth;
      addOption(monthSelect, i, String(i).padStart(2, "0"), isDisabled);
    }

    // 월이 변경되었을 때 일 업데이트
    updateDays();
  }

  // 일 - 선택된 연도, 월에 맞는 마지막 일자 계산
  function updateDays() {
    const selectedYear = parseInt(yearSelect.value);
    const selectedMonth = parseInt(monthSelect.value);
    const lastDay = new Date(selectedYear, selectedMonth, 0).getDate();

    daySelect.innerHTML = "";

    for (let i = 1; i <= lastDay; i++) {
      const isDisabled =
        selectedYear === nowYear && selectedMonth === nowMonth && i > nowDate;
      addOption(daySelect, i, String(i).padStart(2, "0"), isDisabled);
    }
  }

  // 연도 및 월 변경 시 처리
  yearSelect.addEventListener("change", updateMonths);
  monthSelect.addEventListener("change", updateDays);

  // 초기 로드 시 월과 일 업데이트
  updateMonths();

  // 제출 시 yyyy-MM-dd 형식으로 birth 값 설정
  const joinForm = document.querySelector("#join-div form");
  joinForm.addEventListener("submit", function (e) {
    const year = yearSelect.value;
    const month = String(monthSelect.value).padStart(2, "0");
    const day = String(daySelect.value).padStart(2, "0");

    const birth = `${year}-${month}-${day}`;
    document.getElementById("birth-input").value = birth;
  });

  // 이메일 부분------------------------------------------------------
  const emailDiv = document.querySelector(".email-div");

  const emailInput = emailDiv.querySelector("#email-input");
  const emailCheckMsg = emailDiv.querySelector(".check");
  const emailMsg = emailDiv.querySelector(".msg");
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

  // 이메일 유효성 검사: 최대 50자, 특수 문자 제외
  emailInput.addEventListener("input", function () {
    const emailValue = emailInput.value;
    if (!emailRegex.test(emailValue) || emailValue.length > 50) {
      emailMsg.textContent = "50자 이내의 유효한 이메일 주소를 입력해주세요.";
      emailMsg.style.padding = "10px 15px";
      emailMsg.style.border = "1px solid #343231";
      emailMsg.style.borderRadius = "20px";
      emailMsg.style.marginBottom = "10px";
    } else {
      emailMsg.textContent = "";
      emailMsg.style.padding = "";
      emailMsg.style.border = "";
      emailMsg.style.borderRadius = "";
      emailMsg.style.marginBottom = "";
      emailCheckMsg.textContent = "✔";
    }

    updateJoinButtonState(); // 상태 업데이트
  });

  //-------------------------------------------------------------------

  const resetButton = joinForm.querySelector("input[type='reset']");
  const msgs = joinForm.querySelectorAll(".msg");
  const checks = joinForm.querySelectorAll(".check");

  // 다시쓰기 버튼 클릭 시 메시지 초기화
  resetButton.addEventListener("click", function () {
    for (let i = 0; i < msgs.length; i++) {
      msgs[i].textContent = "";
      msgs[i].style.padding = "";
      msgs[i].style.border = "";
      msgs[i].style.borderRadius = "";
      msgs[i].style.marginBottom = "";
      checks[i].textContent = "";
    }
    idInput.focus();
    joinButton.disabled = true;
  });

  // 모두 올바르게 입력했으면 회원가입 버튼 활성화
});
