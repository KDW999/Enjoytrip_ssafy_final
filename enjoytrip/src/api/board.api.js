import { localApiAxios } from "../utilities/http-commons";

const local = localApiAxios();

function listArticle(success, fail) {
  local.get(`/board/articlelist`).then(success).catch(fail);
}

function registArticle(article, success, fail) {
  console.log("boardjs article", article);
  local
    .post(`/board/register`, JSON.stringify(article))
    .then(success)
    .catch(fail);
}

function detailArticle(articleNo, success, fail) {
  local.get(`/board/${articleNo}`).then(success).catch(fail);
}

function getModifyArticle(articleNo, success, fail) {
  local.get(`/board/modify/${articleNo}`).then(success).catch(fail);
}

function modifyArticle(articleNo, article, success, fail) {
  local
    .put(`/board/${articleNo}`, JSON.stringify(article))
    .then(success)
    .catch(fail);
}

function deleteArticle(articleNo, success, fail) {
  local.delete(`/board/${articleNo}`).then(success).catch(fail);
}

function searchArticle(query, success, fail) {
  local.get(`/board/search/${query}`).then(success).catch(fail);
}

export {
  listArticle,
  registArticle,
  detailArticle,
  getModifyArticle,
  modifyArticle,
  deleteArticle,
  searchArticle,
};
