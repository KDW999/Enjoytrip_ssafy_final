let login = function () {
  // 사용자 정보를 입력받는다.
  let userid = window.prompt("아이디 입력", "ssafy");
  if (userid.length == 0) {
    window.alert("아이디 입력!!!");
    return;
  }

  let userpass = window.prompt("비밀번호 입력", "1234");
  if (userpass.length == 0) {
    window.alert("비밀번호 입력!!!");
    return;
  }

  if (userid == "ssafy" && userpass == "1234") {
    window.alert("로그인 성공!!!");
    document.querySelector("#header_nav_confirm_off").style.display = "none";
    document.querySelector("#header_nav_confirm_on").style.display = "flex";
  } else {
    window.alert("아이디 또는 비밀번호 확인!!!");
  }
};

let logout = function () {
  window.alert("로그아웃 성공!!!");
  document.querySelector("#header_nav_confirm_on").style.display = "none";
  document.querySelector("#header_nav_confirm_off").style.display = "flex";
};

let moveToMyPage = function () {
  location.href = "mypage.html";
};
