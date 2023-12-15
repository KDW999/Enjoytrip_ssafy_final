<script setup>
import { ref, watch, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { registArticle, detailArticle, modifyArticle } from "@/api/board.api";
import AttractionDetail from "../../map/AttractionDetail.vue";

// Data
const route = useRoute();
const router = useRouter();

const props = defineProps({ type: String, contentid: String }); // 게시글 작성인지 수정인지 판단
const isUseId = ref(false); // 글 작성, 수정 권한이 있는지
const session = sessionStorage.getItem("user");
const id = ref(null);
if (session) id.value = JSON.parse(session);
console.log("content" + props.contentid);
console.log("content" + props);

const article = ref({
  articleNo: 0,
  userId: session ? id.value.id : "",
  subject: "",
  content: "",
  userName: "",
  attractionId: props.contentid ? props.contentid : null,
  hit: 0,
  registerTime: "",
});

// 글 수정
if (props.type === "modify") {
  let { articleNo } = route.params; // url에서 글 번호 가져오기
  console.log(articleNo + "번글 받아와서 수정");

  detailArticle(
    articleNo,
    ({ data }) => {
      article.value = data;
      isUseId.value = true;
    },
    (error) => {
      console.error(error);
    }
  );
  isUseId.value = true;
}

const subjectErrMsg = ref("");
const contentErrMsg = ref("");

watch(
  () => article.value.subject,
  (value) => {
    let len = value.length;
    if (len == 0) {
      subjectErrMsg.value = "제목은 입력해 주세요.";
    } else if (len > 30) {
      subjectErrMsg.value = "제목은 30자를 초과할 수 없습니다.";
    } else {
      subjectErrMsg.value = "";
    }
  },
  { immediate: true }
);

watch(
  () => article.value.content,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 500) {
      contentErrMsg.value = "내용을 확인해 주세요.";
    } else contentErrMsg.value = "";
  },
  { immediate: true }
);

// Method
function onSubmit() {
  if (subjectErrMsg.value) {
    alert(subjectErrMsg.value);
  } else if (contentErrMsg.value) {
    alert(contentErrMsg.value);
  } else {
    props.type === "regist" ? writeArticle() : updateArticle();
  }
}

function writeArticle() {
  console.log("글 등록하기!", article.value);
  console.log("글 등록 시 contentId", article.value.attractionId);
  registArticle(
    article.value,
    (response) => {
      let msg = "글 등록 중 문제 발생";
      if (response.status == 201) msg = "글 등록 완료";
      alert(msg);
      moveList();
    },
    (error) => {
      alert("에러 발생");
      console.error(error);
    }
  );
}

function updateArticle() {
  console.log(article.value.articleNo + "번글 수정★", article.value);
  modifyArticle(
    article.value.articleNo,

    article.value,
    (response) => {
      let msg = "글 수정시 문제 발생";
      if (response.status == 200) msg = "글 정보 수정 완료";
      alert(msg);
      moveList();
    },
    (error) => console.log(error)
  );
}

function moveList() {
  router.push({ name: "board-list" });
}

onMounted(
  () => { if(props?.contentid) window.scrollTo({ top: 0, behavior: "smooth" }); }
);
</script>

<template>
  <attraction-detail v-if="contentid" :contentid="contentid" />

  <form class="mt-5" @submit.prevent="onSubmit">
    <div class="mb-3">
      <input
        type="text"
        class="form-control"
        v-model="article.subject"
        placeholder="제목을 작성해 주세요." />
    </div>
    <br />
    <div class="mb-3">
      <textarea
        class="form-control"
        rows="20"
        v-model="article.content"
        placeholder="내용을 작성해 주세요."></textarea>
    </div>
    <div class="d-flex justify-content-end">
      <button
        type="submit"
        class="btn btn-outline-primary mb-3 mr-2"
        @click="moveList">
        취소
      </button>
      <button
        type="submit"
        class="btn btn-outline-success mb-3"
        v-if="type === 'regist'">
        글 작성
      </button>
      <button type="submit" class="btn btn-outline-success mb-3" v-else>
        글 수정
      </button>
    </div>
  </form>
</template>

<style scoped></style>
