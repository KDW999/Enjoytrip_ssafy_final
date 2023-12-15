<script setup>
import BoardListItem from "./item/BoardListItem.vue";
import { useRouter } from "vue-router";
import { onMounted, ref } from "vue";
import { listArticle, searchArticle } from "../../api/board.api";

// Data
const router = useRouter();

// const selectOption = ref([
//   { text: "검색조건", value: "" },
//   { text: "제목", value: "subject" },
//   { text: "작성자아이디", value: "user_id" },
// ]);

const articles = ref([]);
const currentPage = ref(1);
const totalPage = ref(0);
const param = ref({
  pgno: currentPage.value,
  key: "",
  word: "",
});

const keyword = ref("");

onMounted(() => {
  getArticleList();
});

const getArticleList = () => {
  console.log("글 목록 가져오기");
  listArticle(
    ({ data }) => {
      console.log(data);

      articles.value = data;
      console.log(articles.value);
    },
    (error) => {
      console.error(error);
    }
  );
};

const searchArticleList = () => {
  console.log("글 검색함 ");
  console.log("검색어 : " + keyword.value);
  searchArticle(
    keyword.value,
    ({ data }) => {
      console.log(data);
      articles.value = data;
    },
    (error) => {
      console.error(error);
    }
  );
};

// Method
const moveWritePage = () => {
  router.push({
    name: "board-write",
  });
};
</script>

<template>
  <!-- 전체 -->
  <div style="background-color: rgb(250, 242, 255)">
    <!-- 본문 윗 부분 : 글 작성, 검색 조건-->
    <div class="title d-flex justify-content-center mt-4">소통 공간</div>
    <div class="d-flex justify-content-between m-5 p-5 border">
      <b-button variant="outline-primary" @click="moveWritePage"
        >글작성</b-button
      >

      <div class="d-flex">
        <div class="input-group input-group-sm">
          <input
            type="text"
            class="form-control"
            v-model="keyword"
            placeholder="검색어 입력" />
          <button class="btn btn-dark" type="button" @click="searchArticleList">
            검색
          </button>
        </div>
      </div>
    </div>

    <!-- 본문 아랫 부분 : 배너, 글 목록-->
    <div
      style="height: 800px"
      class="d-flex justify-content-between border m-5">
      <table class="table table-hover w-75 border">
        <div>
          <div class="d-flex justify-content-between border m-3 p-2">
            <div>글번호</div>
            <div>유형</div>
            <div class="mr-5">제목</div>
            <div class="mr-5">작성자</div>
            <div class="mr-5">작성일</div>
          </div>
          <BoardListItem
            v-for="article in articles"
            :key="article.atricleNo"
            :article="article"
            :to="{
              name: 'board-detail',
              params: { articleNo: article.articleNo },
            }"
            class="border m-1">
          </BoardListItem>
        </div>
      </table>
      <div class="w-25 border" style="margin: 10px">
        <b-img
          src="https://cdn.visitkorea.or.kr/img/call?cmd=VIEW&id=dcf62c25-2e16-479b-b17e-83730713bd31&mode=raw"
          fluid
          alt="Responsive image"
          style="height: 776px"></b-img>
      </div>
    </div>
  </div>
</template>

<style scoped>
.title {
  font-size: 44px;
}
</style>
