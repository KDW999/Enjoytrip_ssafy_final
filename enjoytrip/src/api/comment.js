import { localApiAxios } from "../utilities/http-commons";

const local = localApiAxios();

export const postComment = (comment) => {
  console.log("postComment : ", comment);
  // return local.post(`/comment/regist`, JSON.stringify(comment));
  return local.post(`/comment/regist`, comment);
};

export const getComments = (articleNo) => {
  return local.get(`/comment/${articleNo}`);
};

export const updateComment = (comment) => {
  console.log(comment);
  return local.put(`/comment/${comment.commentNo}`, JSON.stringify(comment));
};

export const deleteComment = (commentNo) => {
  return local.delete(`/comment/${commentNo}`);
}
