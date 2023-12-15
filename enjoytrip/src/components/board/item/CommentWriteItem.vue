<script setup>
import { onBeforeMount, onMounted, ref } from "vue";
import { useCommentStore } from "../../../stores/comment";
import { storeToRefs } from "pinia";

const prop = defineProps({ articleNo: Number });
const storeComment = useCommentStore();
// 반응성
const { comments } = storeToRefs(storeComment);

// 반응성 X
const { insertComment, selectComments } = storeComment;

const comment = ref("");
let userId = ref("");

let userInfo = ref({});

onBeforeMount(() => {
  const getData = sessionStorage.getItem("user");

  userInfo.value = JSON.parse(getData);
  userId.value = userInfo.value.id;
});

onMounted(() => {
  const getData = sessionStorage.getItem("user");

  userInfo.value = JSON.parse(getData);
  userId.value = userInfo.value.id;
});
const write = async () => {
  const payload = {
    articleNo: prop.articleNo,
    userId: userId.value,
    content: comment.value,
  };

  const resp = await insertComment(payload);

  if (resp === "success") {
    userId.value = "";
    comment.value = "";

    await selectComments(prop.articleNo);
    console.log("aaa", comments);
    alert("댓글 등록");
  } else {
    alert("댓글 등록 실패");
  }
};
</script>

<template>
  <div class="box p-3">
    <b-input-group style="width: 240px" prepend="작성자">
      <b-form-input v-model="userId" />
    </b-input-group>

    <b-input-group>
      <b-form-textarea
        placeholder="댓글 입력"
        rows="3"
        max-rows="6"
        v-model="comment" />
      <b-input-group-append>
        <b-button
          variant="secondary"
          style="width: 80px"
          class="m-1"
          @click="write">
          등록
        </b-button>
      </b-input-group-append>
    </b-input-group>
  </div>
</template>

<style scoped></style>
