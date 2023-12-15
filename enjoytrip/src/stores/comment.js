import { ref, computed } from "vue";
import { defineStore } from "pinia";
import { postComment, getComments } from "../api/comment";

const computingContent = (comment) => {
  comment.computedComment = comment.comment.replace(
    /(?:\r\n|\r|\n)/g,
    "<br />"
  );
};

export const useCommentStore = defineStore("comment", () => {
  const comments = ref([]);

  const getterComments = computed(() => {
    comments.value.filter((comment) => {
      computingContent(comment);
    });
    console.log("comments" + comments.value);
    return comments.value;
  });

  const insertComment = async (comment) => {
    console.log("insertComment", comment);
    try {
      const { status } = await postComment(comment);
      if (status === 200) {
        comments.value = comment;
        return "success";
      } else {
        throw new Error(status);
      }
    } catch (error) {
      console.log(error);
      return "fail";
    }
  };

  const selectComments = async (articleNo) => {
    console.log("selectComments" + articleNo + "번 글의 댓글");

    try {
      const { status, data } = await getComments(articleNo);
      if (status === 200) {
        comments.value = data;
        return "success";
      } else {
        throw new Error(status);
      }
    } catch (error) {
      return "fail";
    }
  };

  return {
    comments,
    getterComments,
    insertComment,
    selectComments,
  };
});
