<script setup>
import { ref, watch } from "vue";
import { useRoute } from "vue-router";

const route = useRoute();
const user = ref(null);

watch(
  () => route.fullPath,
  async () => {
    const session = sessionStorage.getItem("user");
    if (session) user.value = JSON.parse(session);
    else user.value = null;
  }
);
</script>

<template>
  <div>
    <b-navbar id="navbar" toggleable="lg" type="dark">
      <b-navbar-brand :to="{ name: 'main' }">
        <div style="display: flex">
          <b-img
            src="/src/assets/image/free-icon-tree-489969.png"
            fluid
            alt="Responsive image"
            style="width: 60px; margin-right: 10px"></b-img>

          <h2 class="main-title">Healrip</h2>
        </div>
      </b-navbar-brand>

      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <b-nav-item :to="{ name: 'map' }">
            <div class="b-nav-item">지역별 관광지</div>
          </b-nav-item>
          <b-nav-item :to="{ name: 'board-list' }">
            <div class="b-nav-item">소통 공간</div>
          </b-nav-item>
        </b-navbar-nav>

        <b-navbar-nav class="ml-auto" v-if="!user">
          <b-navbar-nav>
            <b-nav-item :to="{ name: 'member-login' }" class="b-nav-item">
              <div class="b-nav-item">로그인</div>
            </b-nav-item>
            <b-nav-item :to="{ name: 'member-signup' }" class="b-nav-item">
              <div class="b-nav-item">회원가입</div>
            </b-nav-item>
          </b-navbar-nav>
        </b-navbar-nav>

        <b-navbar-nav class="ml-auto" v-if="user">
          <b-navbar-nav>
            <b-nav-item class="b-nav-item">
              <div style="color: gold">
                {{ user.name }}
              </div>
            </b-nav-item>
            <b-nav-item :to="{ name: 'member-logout' }" class="b-nav-item">
              <div class="b-nav-item">로그아웃</div>
            </b-nav-item>
          </b-navbar-nav>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </div>
</template>

<style scoped>
.toolbar-title {
  flex: none;
}

.navbar {
  background-color: rgb(237, 249, 255);
}

.b-nav-item {
  font-size: 20px;
  color: black;
  font-family: "TAEBAEKfont";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2310@1.0/TAEBAEKfont.woff2")
    format("woff2");
  font-weight: 900;
  font-style: normal;
}
.main-title {
  font-family: "HSJiptokki-Black";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2302@1.0/HSJiptokki-Black.woff2")
    format("woff2");
  font-weight: 900;
  font-style: normal;
  font-size: 44px;
  color: #7cfc00;
}
</style>
