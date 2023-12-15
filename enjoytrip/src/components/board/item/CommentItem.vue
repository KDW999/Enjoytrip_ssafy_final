<script setup>
import { ref } from "vue";
import {
  deleteComment,
  getComments,
  updateComment,
} from "../../../api/comment";
import { storeToRefs } from "pinia";
import { useCommentStore } from "@/stores/comment";

const storeComment = useCommentStore();
const { comments } = storeToRefs(storeComment);
const { selectComments } = storeComment;

const prop = defineProps({ articleNo: Number, comment: Object });
console.log(prop.comment);
console.log(prop.articleNo);

const input = ref({ ...prop.comment });

const r = {};
r[`comment${prop.comment.commentNo}`] = ref(null);

const showModalModify = () => {
  r[`comment${prop.comment.commentNo}`].value.show();
};
const hideModalModify = () => {
  r[`comment${prop.comment.commentNo}`].value.hide();
};

const modify = async () => {
  const payload = {
    commentNo: prop.comment.commentNo,
    content: input.value.content,
  };
  try {
    const { status } = await updateComment(payload);
    console.log(status);
    if (status === 200) {
      input.value.content = "";

      await selectComments(prop.articleNo);

      alert("댓글 수정");
      hideModalModify();
    } else {
      throw new Error(status);
    }
  } catch (error) {
    console.log(error);
  }
};

const del = async () => {
  const commentNo = prop.comment.commentNo;

  try {
    const { status } = await deleteComment(commentNo);
    if (status === 200) {
      alert("댓글 삭제 완료");
      await selectComments(prop.articleNo);
    } else {
      alert("삭제 실패");
      throw new Error(status);
    }
  } catch (error) {
    console.log(error);
  }
};
</script>

<template>
  <b-list-group-item class="flex-column align-items-start border">
    <div class="d-flex w-100 justify-content-between">
      <h5 class="mb-1">{{ comment.userId }}</h5>
      <small class="text-muted">{{ comment.registerTime }}</small>
    </div>

    <p>{{ comment.content }}</p>

    <div class="text-right">
      <b-button-group>
        <b-button variant="secondary" @click="showModalModify">수정</b-button>
        <b-button variant="outline-secondary" @click="del">삭제</b-button>
      </b-button-group>
    </div>

    <!-- 수정 모달 창 작성 -->
    <b-modal
      :ref="r[`comment${prop.comment.commentNo}`]"
      title="댓글 수정"
      header-bg-variant="dark"
      header-text-variant="light"
      centered
      hide-footer>
      <!-- 수정 모달 창 Body 작성-->
      <div>
        <b-input-group style="width: 240px" prepend="작성자">
          <b-form-input
            v-model="input.userId"
            placeholder="작성자 입력"
            readonly></b-form-input>
        </b-input-group>
        <b-form-textarea
          v-model="input.content"
          placeholder="댓글 입력"
          rows="3"
          max-rows="6">
        </b-form-textarea>
      </div>

      <!-- 수정 모달 창 Footer 작성 -->
      <div class="text-right">
        <b-button-group>
          <b-button variant="outline-secondary" @click="hideModalModify">
            취소
          </b-button>
          <b-button variant="secondary" @click="modify">수정</b-button>
        </b-button-group>
      </div>
    </b-modal>
  </b-list-group-item>
</template>

<style scoped></style>
