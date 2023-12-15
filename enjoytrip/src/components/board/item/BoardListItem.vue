<script setup>
import { getComments } from "../../../api/comment";
import { onMounted, ref } from "vue";
const prop = defineProps({ article: Object });

let commentLength = ref(0);
onMounted(() => {
  getCommentsList();
});

const getCommentsList = async () => {
  try {
    const { status, data } = await getComments(prop.article.articleNo);
    console.log(data);
    if (status === 200) {
      commentLength.value = data.length;
      return "success";
    } else {
      throw new Error(status);
    }
  } catch (error) {
    return "fail";
  }
};
</script>

<template>
  <div class="p-3 m-3">
    <tr class="d-flex justify-content-between">
      <th scope="row">{{ article.articleNo }}</th>

      <td v-if="article.attractionId">후기</td>
      <td v-else>자유</td>
      <td>
        <RouterLink
          :to="{
            name: 'board-detail',
            params: { articleNo: article.articleNo },
          }"
          >{{ article.subject }}</RouterLink
        >
        {{ "[" + commentLength + "]" }}
      </td>

      <td>{{ article.name }}</td>
      <td>{{ article.registerTime }}</td>
    </tr>
  </div>
</template>

<style scoped></style>
