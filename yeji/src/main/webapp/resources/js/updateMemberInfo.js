window.addEventListener("load", function () {
  const modiButton = document.querySelector(".modi-btn");
  modiButton.disabled = true; // 초기 상태에서 수정 버튼 비활성화

  // 수정 버튼 상태 업데이트 함수
  function updateModiButtonState() {
    if (
      passwordRegex.test(passwordInput.value) &&
      passwordInput.value === passwordInput2.value &&
      emailRegex.test(emailInput.value) &&
      emailInput.value.length <= 50
    ) {
      modiButton.disabled = false;
    } else {
      modiButton.disabled = true;
    }
  }

  // 비밀번호 부분---------------------------------------------------------
  const passwordDiv = document.querySelector(".password-div");

  const passwordInput = passwordDiv.querySelector("#password-input");
  const passwordInput2 = passwordDiv.querySelector("#password-input2");

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

    updateModiButtonState(); // 상태 업데이트
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
    }

    updateModiButtonState(); // 상태 업데이트
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

    updateModiButtonState(); // 상태 업데이트
  });
});
