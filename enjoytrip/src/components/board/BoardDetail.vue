<script setup>
import CommentItem from "./item/CommentItem.vue";
import CommentWriteItem from "./item/CommentWriteItem.vue";
import { useRoute, useRouter } from "vue-router";
import { onMounted, ref } from "vue";
import { deleteArticle, detailArticle } from "../../api/board.api";
import AttractionDetail from "../map/AttractionDetail.vue";
import { getComments } from "../../api/comment";
import { storeToRefs } from "pinia";
import { useCommentStore } from "@/stores/comment";

const route = useRoute();
const router = useRouter();

const storeComment = useCommentStore();
const { comments } = storeToRefs(storeComment);
const { articleNo } = route.params;

const article = ref({});
let contentId = ref(null);
let commentLength = ref(0);

onMounted(() => {
  console.log(articleNo + "번글 가져옴");

  getArticle();
  getCommentsList();
});

const getArticle = () => {
  console.log(articleNo + "번글 가져옴");

  detailArticle(
    articleNo,
    ({ data }) => {
      article.value = data;
      console.log("이거 data: " + article.value);
      contentId.value = data.attractionId;
    },
    (error) => {
      console.error(error);
    }
  );
};

const getCommentsList = async () => {
  try {
    const { status, data } = await getComments(articleNo);
    console.log(data);
    if (status === 200) {
      comments.value = data;
      commentLength.value = data.length;
      console.log("comments : ", comments);
      return "success";
    } else {
      throw new Error(status);
    }
  } catch (error) {
    return "fail";
  }
};

function moveList() {
  router.push({ name: "board-list" });
}

function moveModify() {
  router.push({ name: "board-modify" });
}

function onDeleteArticle() {
  console.log("글 삭제");
  deleteArticle(
    articleNo,
    (response) => {
      if (response.status == 200) moveList();
    },
    (error) => {
      console.error(error);
    }
  );
}
</script>

<template>
  <b-container>
    <b-row class="mt-5 mb-3">
      <b-col class="text-left">
        <b-button class="btn-width" variant="outline-primary" @click="moveList">
          목록
        </b-button>
      </b-col>

      <b-col class="text-right">
        <b-button
          class="btn-width mr-2"
          variant="outline-info"
          @click="moveModify">
          수정
        </b-button>
        <b-button
          class="btn-width"
          variant="outline-danger"
          @click="onDeleteArticle"
          >삭제</b-button
        >
      </b-col>
    </b-row>

    <b-row>
      <b-col>
        <b-card border-variant="dark" no-body>
          <b-card-header class="d-flex justify-content-between">
            <div class="mt-2 ml-5 mr-5">
              <h3>{{ article.subject }}</h3>
            </div>
            <div class="mt-2 ml-5">
              <h6>{{ article.name }}</h6>
            </div>
            <div class="mt-2">
              <h6>{{ article.registerTime }}</h6>
            </div>
            <div class="mt-2">
              <h6>댓글 : {{ commentLength }}</h6>
            </div>
          </b-card-header>

          <b-card-body class="text-left">
            <attraction-detail v-if="contentId" :contentid="contentId" />
            <div class="mt-5">{{ article.content }}</div>
          </b-card-body>
        </b-card>
      </b-col>
    </b-row>

    <CommentWriteItem :articleNo="articleNo" />

    <!-- <CommentItem /> -->
    <CommentItem
      v-for="comment in comments"
      :articleNo="articleNo"
      :comment="comment"
      :key="comment.commentNo" />
  </b-container>
</template>

<style scoped>
.content {
  background-color: aqua;
  width: 100px;
  height: 100px;
}
</style>
