<script setup>
import { ref, watch, computed } from "vue";
import { storeToRefs } from "pinia";
import { useAttractionStore } from "@/stores/attraction";
import { useTripPlanStore } from "@/stores/trip-plan";
import AttractionDetail from "@/components/map/AttractionDetail.vue";

const prop = defineProps({ externalFocus: String });
const emit = defineEmits(["pageChange"]);

const attractionStore = useAttractionStore();
const planStore = useTripPlanStore();

const { attractions, totalCount, perPage, pageNo } =
  storeToRefs(attractionStore);
const { planIndex } = storeToRefs(planStore);
const { addToDraft } = planStore;

const content = ref(null);
const openContent = (contentid) => {
  if (content.value === contentid) content.value = null;
  else content.value = contentid;
};

const perPageInt = computed(() => parseInt(perPage.value));

const changePage = (page) => {
  emit("pageChange", { page: page });
};

watch(attractions, () => {
  content.value = null;
});
watch(
  () => prop.externalFocus,
  () => {
    console.log("fire");
    openContent(prop.externalFocus);
  }
);
</script>

<template>
  <!-- 관광지 정보 - 검색 결과 -->
  <b-row class="mt-3">
    <b-col>
      <b-table-simple responsive hover>
        <b-thead>
          <b-tr>
            <b-th>대표이미지</b-th>
            <b-th>관광지명</b-th>
            <b-th>주소</b-th>
            <b-th v-if="typeof planIndex === 'number'">여행계획</b-th>
          </b-tr>
        </b-thead>
        <b-tbody>
          <template v-for="item in attractions" :key="item.contentid">
            <b-tr :id="`attraction-item-${encodeURIComponent(item.contentid)}`">
              <b-td
                class="attraction-detail-expandable"
                @click="openContent(item.contentid)">
                <b-img
                  v-if="item.firstimage2"
                  class="image-list-item"
                  :src="item.firstimage2"
                  :alt="item.title" />
              </b-td>
              <b-td
                class="attraction-detail-expandable"
                @click="openContent(item.contentid)"
                >{{ item.title }}</b-td
              >
              <b-td
                class="attraction-detail-expandable"
                @click="openContent(item.contentid)"
                >{{ item.addr1 }} {{ item.addr2 }}</b-td
              >
              <b-td v-if="typeof planIndex === 'number'">
                <b-button variant="link" @click="addToDraft(item.contentid)">계획 추가</b-button>
              </b-td>
            </b-tr>
            <b-tr
              v-if="content === item.contentid"
              class="attraction-detail-row">
              <b-td colspan="4" class="attraction-detail-column">
                <attraction-detail :contentid="item.contentid">
                  <template #actions>
                    <b-link
                      :to="{
                        name: 'board-write-review',
                        params: { contentid: item.contentid },
                      }"
                      class="card-link"
                      >후기 작성</b-link
                    >
                  </template>
                </attraction-detail>
              </b-td>
            </b-tr>
          </template>
        </b-tbody>
      </b-table-simple>
    </b-col>
  </b-row>
  <b-row>
    <b-col>
      <b-pagination
        v-model="pageNo"
        :total-rows="totalCount"
        :per-page="perPageInt"
        @input="changePage" />
    </b-col>
  </b-row>
</template>

<style scoped>
.image-list-item {
  width: 8em;
}

.attraction-detail-expandable {
  cursor: pointer;
}

.attraction-detail-row:hover {
  background-color: inherit;
}
</style>
