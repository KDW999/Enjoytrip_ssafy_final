<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { registMember } from "@/api/member-api";

// Data
const router = useRouter();

const memberInfo = ref({
  id: "",
  userName: "",
  userPwd: "",
  pwdCheck: "",
});

const doRegistMember = () => {
  registMember(
    memberInfo.value.id,
    memberInfo.value.userName,
    memberInfo.value.userPwd
  ).then(
    (response) => {
      if (response.status !== 201) throw new Error("회갑 실패");

      router.push({ name: "member-login" });
    },
    (error) => {
      console.error(error);
    }
  );
};

// function doRegistMember() {
//   if (memberInfo.value.userPwd !== memberInfo.value.pwdCheck) {
//     alert("비번 달라");
//     return;
//   }

//   console.log(
//     memberInfo.value.id,
//     memberInfo.value.userName,
//     memberInfo.value.userPwd
//   );
//   registMember(
//     memberInfo.value.id,
//     memberInfo.value.userName,
//     memberInfo.value.userPwd,
//     ({ data }) => {
//       console.log(data);
//       alert("회갑 성공");
//       router.push({ name: "member-login" });
//     },
//     (error) => {
//       console.error(error);
//       return;
//     }
//   );
// }

function goLogin() {
  router.push({ name: "member-login" });
}
</script>

<template>
  <div class="container">
    <div class="row justify-content-start">
      <div class="col-lg-10 mt-5">
        <h2 style="font-weight: 600">회원가입</h2>
      </div>
      <div class="col-lg-10">
        <form>
          <div class="mt-5 mb-3 text-start">
            <input
              type="text"
              class="form-control"
              placeholder="아이디"
              v-model="memberInfo.id"
              style="width: 300px" />
          </div>
          <div class="mb-4 text-start">
            <input
              type="text"
              class="form-control"
              placeholder="닉네임"
              v-model="memberInfo.userName"
              style="width: 300px" />
          </div>
          <div class="mb-4 text-start">
            <input
              type="password"
              class="form-control"
              @keyup.enter="login"
              placeholder="비밀번호"
              v-model="memberInfo.userPwd"
              style="width: 300px" />
          </div>
          <div class="mb-4 text-start">
            <input
              type="password"
              class="form-control"
              placeholder="비밀번호 확인"
              v-model="memberInfo.pwdCheck"
              style="width: 300px" />
          </div>

          <div>
            <button
              type="button"
              class="btn btn-outline-primary mr-2"
              style="background-color: skyblue; color: black"
              @click="goLogin()">
              로그인 화면
            </button>
            <button
              type="button"
              class="btn btn-outline-success"
              style="background-color: greenyellow; color: black"
              @click="doRegistMember()">
              회원가입
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
