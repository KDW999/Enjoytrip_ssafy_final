<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { login } from "../../api/member-api";

// Data
const router = useRouter();

const loginUser = ref({
  userId: "",
  userPwd: "",
});

const loginBtn = () => {
  console.log(loginUser.value.userId, loginUser.value.userPwd);
  login(loginUser.value.userId, loginUser.value.userPwd)
    .then((response) => {
      if (response.status !== 200) throw new Error("로긴 실패");

      sessionStorage.setItem("user", JSON.stringify(response.data));
      router.push("/");
    })
    .catch(() => {
      console.log("로긴 실패");
    });
};

const goSignUp = () => {
  router.push({ name: "member-signup" });
};
</script>

<template>
  <div class="container">
    <div class="row justify-content-start">
      <div class="col-lg-10 mt-5">
        <h2 style="font-weight: 600">로그인</h2>
      </div>
      <div class="col-lg-10">
        <form>
          <div class="mt-5 mb-3 text-start">
            <input
              type="text"
              class="form-control"
              placeholder="아이디"
              v-model="loginUser.userId"
              style="width: 300px" />
          </div>
          <div class="mb-4 text-start">
            <input
              type="password"
              class="form-control"
              @keyup.enter="login"
              placeholder="비밀번호"
              v-model="loginUser.userPwd"
              style="width: 300px" />
          </div>
          <div>
            <button
              type="button"
              class="btn btn-outline-primary mr-2"
              style="background-color: skyblue; color: black"
              @click="loginBtn">
              로그인
            </button>
            <button
              type="button"
              class="btn btn-outline-success"
              style="background-color: greenyellow; color: black"
              @click="goSignUp">
              회원가입
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
